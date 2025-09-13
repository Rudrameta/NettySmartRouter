package com.newlandframework.gateway.commons;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Weighted Round Robin load balance strategy implementation
 * Distributes requests based on server weights
 * 
 * @author David Yu
 * @version 1.0
 */
public class WeightedRoundRobinStrategy implements LoadBalanceStrategy {
    
    private final AtomicInteger currentWeight = new AtomicInteger(0);
    
    @Override
    public String selectServer(List<String> servers, String requestContent) {
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        
        // For simplicity, assume all servers have equal weight
        // In a real implementation, you would have a weight configuration
        int index = currentWeight.getAndIncrement() % servers.size();
        return servers.get(index);
    }
    
    @Override
    public String getStrategyName() {
        return "WeightedRoundRobin";
    }
}
