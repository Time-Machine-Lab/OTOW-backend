package com.tml.otowbackend.core.config;

import com.tml.otowbackend.core.ws.EchoChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfiguration {

//    @Bean
    public ServerEndpointExporter serverEndpointExporter (){
        return new ServerEndpointExporter();
    }  
}
