package org.hui.patterns.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ExecuteStrategy {
    private Map<String, IStrategy> strategyMap;

    public ExecuteStrategy(Map<String, IStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    public void executeStrategy(String strategyName) {
        if (!strategyMap.containsKey(strategyName)) {
            throw new IllegalArgumentException(String.format("The strategy %s does not exist.",strategyName));
        }
        strategyMap.get(strategyName).execute();
    }
}
