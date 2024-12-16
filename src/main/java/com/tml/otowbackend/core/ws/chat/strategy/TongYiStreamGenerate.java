package com.tml.otowbackend.core.ws.chat.strategy;

import com.google.gson.Gson;
import com.tml.otowbackend.core.ws.chat.tongyi.TongYiRequestBody;
import com.tml.otowbackend.core.ws.chat.tongyi.TongYiMessage;
import com.tml.otowbackend.core.ws.processor.Processor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component("TongYiStream")
public class TongYiStreamGenerate implements StreamGenerate{
    private static final String API_URL = "https://dashscope.aliyuncs.com/compatible-mode/v1/chat/completions";

    private static final URL url;

    private static final String CONTENT_REGEX = "\"content\"\\s*:\\s*\"([^\"]*)\"";

    private static final Pattern pattern = Pattern.compile(CONTENT_REGEX);

    @Resource
    private Processor<String> aiResultProcessor;

    static {
        try {
            url = new URL(API_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void generate(String key, String text, String apiKey, Integer num) {
        try {
            HttpURLConnection httpConn = send(text, apiKey, num);
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                try (BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()))) {
                    String jsonData;
                    while ((jsonData = in.readLine()) != null) {
                        // 解析每个流式的JSON对象,并通过wsManager发送出去 TODO: 需要搞清楚是否需要以特定格式打包固定
                        aiResultProcessor.process(key, parseResultExtractContent(jsonData));
                    }
                }
            } else {
                log.warn("通义千问调用失败!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private HttpURLConnection send(String text, String API_KEY, Integer num) throws IOException {
            String jsonInputString = generateRequestJson(text, num);
            HttpURLConnection httpURLConnection = getHttpURLConnection(API_KEY);
            OutputStream os = httpURLConnection.getOutputStream();
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            return httpURLConnection;
    }

    private String generateRequestJson(String text, Integer num){
        TongYiRequestBody requestBody = new TongYiRequestBody(
                "qwen-plus",
                new TongYiMessage[]{
                        new TongYiMessage("system", "You are a helpful assistant."),
                        new TongYiMessage("user", text+"，字数控制在"+num+"以内，同时以纯文本形式发送")
                },
                true
        );

        Gson gson = new Gson();
        return gson.toJson(requestBody);
    }

    private HttpURLConnection getHttpURLConnection(String apiKey) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        String auth = "Bearer " + apiKey;
        httpURLConnection.setRequestProperty("Authorization", auth);
        httpURLConnection.setDoOutput(true);
        return httpURLConnection;
    }

    private String parseResultExtractContent(String jsonData) {
        Matcher matcher = pattern.matcher(jsonData);
        // 查找所有匹配项
        if (matcher.find()) {
            // 提取匹配到的内容
            String content = matcher.group(1);
            return !content.isEmpty() ? content : "";
        }
        return "";
    }
}
