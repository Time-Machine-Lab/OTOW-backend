package com.tml.otowbackend;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.tml.otowbackend.core.ws.WsMessage;
import com.tml.otowbackend.core.ws.chat.AiGenerate;
import com.tml.otowbackend.core.ws.chat.strategy.StreamGenerate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = OtowBackendApplication.class)
//@WebAppConfiguration
//@Slf4j
public class StreamAiTest {
//    @Test
    public void streamAiTest() {

    }

    public static void main(String[] args) {
        WsMessage wsMessage = new WsMessage("test", "test", "test", 100);
        System.out.println(JSONObject.toJSONString(wsMessage));
    }
}
