package com.tml.otowbackend.core.ws.chat;

import com.tml.otowbackend.core.ws.chat.strategy.ReturnGenerate;
import com.tml.otowbackend.core.ws.chat.strategy.StreamGenerate;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Description
 * @Author welsir
 * @Date 2024/10/30 0:14
 */
@Component
public class AiGenerate {

    @Resource(name="${ai.strategy}")
    private ReturnGenerate aiGenerate;

    @Resource(name = "${ai.stream-strategy}")
    private StreamGenerate aiStreamGenerate;

    @Value("${ai.api-key}")
    private String apiKey;

    private final Integer DEFAULT_MAX_NUM = 100;

    public String textGenerateAndReturnContent(String resource,Integer num,String slogan){
        String content = aiGenerate.generate(resource, apiKey, num > DEFAULT_MAX_NUM ? DEFAULT_MAX_NUM : num);
        content+="\n"+ (StringUtil.isNullOrEmpty(slogan)?"":slogan);
        return content;
    }

    public void streamGenerate(String key, String text, Integer num){
        aiStreamGenerate.generate(key, text, apiKey, num);
    }

    public void test(){
        aiStreamGenerate.generate("test", "java", apiKey, 100);
    }
}
