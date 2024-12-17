package com.tml.otowbackend.core.ws;

import com.tml.otowbackend.core.exception.ServerException;
import com.tml.otowbackend.core.ws.strategy.ExecuteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class ExecuteProcessor {
    protected final Map<String, ExecuteStrategy> strategies;

    @Resource
    private WebSocketManager webSocketManager;

    @Autowired
    public ExecuteProcessor(ApplicationContext context) {
        this.strategies = context.getBeansOfType(ExecuteStrategy.class);
    }

    public ExecuteStrategy getStrategy(String strategyType) {
        return strategies.get(strategyType);
    }
}
