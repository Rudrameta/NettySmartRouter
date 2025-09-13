package com.newlandframework.gateway.commons;

import java.util.List;
import java.util.Random;

/**
 * Random load balance strategy implementation
 * Randomly selects a server from the available server list
 * 
 * @author David Yu
 * @version 1.0
 */
public class RandomStrategy implements LoadBalanceStrategy {
    
    private final Random random = new Random();
    
    @Override
    public String selectServer(List<String> servers, String requestContent) {
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        
        int index = random.nextInt(servers.size());
        return servers.get(index);
    }
    
    @Override
    public String getStrategyName() {
        return "Random";
    }
}
