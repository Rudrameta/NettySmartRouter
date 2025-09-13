package com.newlandframework.gateway.commons;

import io.netty.util.Signal;

import java.nio.charset.Charset;

/**
 * Gateway configuration options and constants
 * 
 * @author David Yu
 * @version 1.0
 */
public class GatewayOptions {
    // Original configuration constants
    public static final Charset GATEWAY_OPTION_CHARSET = Charset.forName("GBK");
    public static final Signal GATEWAY_OPTION_SERVICE_ACCESS_ERROR = Signal.valueOf("[NettyGateway]:Access gateway fail!");
    public static final Signal GATEWAY_OPTION_TASK_POST_ERROR = Signal.valueOf("[NettyGateway]:Http post fail!");
    public static final int GATEWAY_OPTION_PARALLEL = Math.max(2, Runtime.getRuntime().availableProcessors());
    public static final int GATEWAY_OPTION_HTTP_POST = 60 * 1000;
    public static final String GATEWAY_OPTION_GATEWAY_CONFIG_FILE = "netty-gateway.properties";
    public static final String GATEWAY_OPTION_ROUTE_CONFIG_FILE = "netty-route.properties";
    public static final String GATEWAY_OPTION_KEY_WORD_SPLIT = ",";
    public static final String GATEWAY_OPTION_SERVER_SPLIT = "@";
    public static final String GATEWAY_OPTION_LOCALHOST = "http://127.10.0.1:8080/";
    public static final String GATEWAY_PROPERTIES_PREFIX_KEY_WORD = ".keyWord";
    public static final String GATEWAY_PROPERTIES_PREFIX_MATCH_ADDR = ".matchAddr";
    public static final String GATEWAY_PROPERTIES_PREFIX_DEFAULT_ADDR = ".defaultAddr";
    public static final String GATEWAY_PROPERTIES_PREFIX_SERVER_PATH = ".serverPath";
    public static final String GATEWAY_PROPERTIES_KEY_WORD = GATEWAY_PROPERTIES_PREFIX_KEY_WORD.substring(1);
    public static final String GATEWAY_PROPERTIES_MATCH_ADDR = GATEWAY_PROPERTIES_PREFIX_MATCH_ADDR.substring(1);
    public static final String GATEWAY_PROPERTIES_SERVER_PATH = GATEWAY_PROPERTIES_PREFIX_SERVER_PATH.substring(1);
    public static final String GATEWAY_PROPERTIES_DEFAULT_ADDR = GATEWAY_PROPERTIES_PREFIX_DEFAULT_ADDR.substring(1);
    
    // Load balance configuration constants
    public static final String GATEWAY_OPTION_LOAD_BALANCE_ENABLED = "gateway.loadBalance.enabled";
    public static final String GATEWAY_OPTION_LOAD_BALANCE_STRATEGY = "gateway.loadBalance.strategy";
    public static final String GATEWAY_OPTION_LOAD_BALANCE_SERVERS = "gateway.loadBalance.servers";
    public static final String GATEWAY_OPTION_LOAD_BALANCE_HEALTH_CHECK = "gateway.loadBalance.healthCheck.enabled";
    public static final String GATEWAY_OPTION_LOAD_BALANCE_HEALTH_CHECK_INTERVAL = "gateway.loadBalance.healthCheck.interval";
    
    // Default values
    public static final String DEFAULT_LOAD_BALANCE_STRATEGY = "roundRobin";
    public static final boolean DEFAULT_LOAD_BALANCE_ENABLED = true;
    public static final boolean DEFAULT_HEALTH_CHECK_ENABLED = false;
    public static final int DEFAULT_HEALTH_CHECK_INTERVAL = 30000; // 30 seconds

    private int gatewayPort = 0;
    private boolean loadBalanceEnabled = DEFAULT_LOAD_BALANCE_ENABLED;
    private String loadBalanceStrategy = DEFAULT_LOAD_BALANCE_STRATEGY;
    private String loadBalanceServers = "";
    private boolean healthCheckEnabled = DEFAULT_HEALTH_CHECK_ENABLED;
    private int healthCheckInterval = DEFAULT_HEALTH_CHECK_INTERVAL;

    public int getGatewayPort() {
        return gatewayPort;
    }

    public void setGatewayPort(int gatewayPort) {
        this.gatewayPort = gatewayPort;
    }
    
    public boolean isLoadBalanceEnabled() {
        return loadBalanceEnabled;
    }
    
    public void setLoadBalanceEnabled(boolean loadBalanceEnabled) {
        this.loadBalanceEnabled = loadBalanceEnabled;
    }
    
    public String getLoadBalanceStrategy() {
        return loadBalanceStrategy;
    }
    
    public void setLoadBalanceStrategy(String loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
    }
    
    public String getLoadBalanceServers() {
        return loadBalanceServers;
    }
    
    public void setLoadBalanceServers(String loadBalanceServers) {
        this.loadBalanceServers = loadBalanceServers;
    }
    
    public boolean isHealthCheckEnabled() {
        return healthCheckEnabled;
    }
    
    public void setHealthCheckEnabled(boolean healthCheckEnabled) {
        this.healthCheckEnabled = healthCheckEnabled;
    }
    
    public int getHealthCheckInterval() {
        return healthCheckInterval;
    }
    
    public void setHealthCheckInterval(int healthCheckInterval) {
        this.healthCheckInterval = healthCheckInterval;
    }
}
