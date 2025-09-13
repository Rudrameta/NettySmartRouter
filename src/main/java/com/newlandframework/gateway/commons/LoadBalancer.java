package com.newlandframework.gateway.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Load balancer implementation that manages different load balancing strategies
 * and provides server selection based on configured strategy
 * 
 * @author David Yu
 * @version 1.0
 */
public class LoadBalancer {
    
    private static final Map<String, LoadBalanceStrategy> strategies = new HashMap<String, LoadBalanceStrategy>();
    
    static {
        // Initialize default strategies
        strategies.put("roundRobin", new RoundRobinStrategy());
        strategies.put("random", new RandomStrategy());
        strategies.put("weightedRoundRobin", new WeightedRoundRobinStrategy());
    }
    
    private LoadBalanceStrategy strategy;
    private String strategyName;
    
    /**
     * Default constructor using round robin strategy
     */
    public LoadBalancer() {
        this("roundRobin");
    }
    
    /**
     * Constructor with specified strategy
     * 
     * @param strategyName Name of the load balance strategy
     */
    public LoadBalancer(String strategyName) {
        this.strategyName = strategyName;
        this.strategy = strategies.get(strategyName);
        if (this.strategy == null) {
            this.strategy = strategies.get("roundRobin"); // fallback to default
        }
    }
    
    /**
     * Select a server from the available server list
     * 
     * @param servers List of available server addresses
     * @param requestContent Request content for context-aware selection
     * @return Selected server address
     */
    public String selectServer(List<String> servers, String requestContent) {
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        
        return strategy.selectServer(servers, requestContent);
    }
    
    /**
     * Select a server from multiple server addresses (comma-separated)
     * 
     * @param serverAddresses Comma-separated server addresses
     * @param requestContent Request content for context-aware selection
     * @return Selected server address
     */
    public String selectServer(String serverAddresses, String requestContent) {
        if (serverAddresses == null || serverAddresses.trim().isEmpty()) {
            return null;
        }
        
        String[] addresses = serverAddresses.split(",");
        List<String> servers = new ArrayList<String>();
        for (String address : addresses) {
            servers.add(address.trim());
        }
        
        return selectServer(servers, requestContent);
    }
    
    /**
     * Get the current strategy name
     * 
     * @return Strategy name
     */
    public String getStrategyName() {
        return strategyName;
    }
    
    /**
     * Set the load balance strategy
     * 
     * @param strategyName Name of the strategy
     */
    public void setStrategy(String strategyName) {
        this.strategyName = strategyName;
        this.strategy = strategies.get(strategyName);
        if (this.strategy == null) {
            this.strategy = strategies.get("roundRobin"); // fallback to default
        }
    }
    
    /**
     * Register a custom load balance strategy
     * 
     * @param name Strategy name
     * @param strategy Strategy implementation
     */
    public static void registerStrategy(String name, LoadBalanceStrategy strategy) {
        strategies.put(name, strategy);
    }
}
