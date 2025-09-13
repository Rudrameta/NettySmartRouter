package com.newlandframework.gateway.commons;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Round Robin load balance strategy implementation
 * Distributes requests evenly across all available servers
 * 
 * @author David Yu
 * @version 1.0
 */
public class RoundRobinStrategy implements LoadBalanceStrategy {
    
    private final AtomicInteger currentIndex = new AtomicInteger(0);
    
    @Override
    public String selectServer(List<String> servers, String requestContent) {
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        
        int index = currentIndex.getAndIncrement() % servers.size();
        return servers.get(index);
    }
    
    @Override
    public String getStrategyName() {
        return "RoundRobin";
    }
}
