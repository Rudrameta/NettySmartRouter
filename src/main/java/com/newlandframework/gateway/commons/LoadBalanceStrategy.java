package com.newlandframework.gateway.commons;

import java.util.List;

/**
 * Load balance strategy interface for selecting backend servers
 * 
 * @author David Yu
 * @version 1.0
 */
public interface LoadBalanceStrategy {
    
    /**
     * Select a server from the available server list based on the strategy
     * 
     * @param servers List of available server addresses
     * @param requestContent Request content for context-aware selection
     * @return Selected server address
     */
    String selectServer(List<String> servers, String requestContent);
    
    /**
     * Get the name of the load balance strategy
     * 
     * @return Strategy name
     */
    String getStrategyName();
}
