package com.newlandframework.gateway.commons;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Test class for LoadBalancer functionality
 * 
 * @author David Yu
 * @version 1.0
 */
public class LoadBalancerTest {
    
    @Test
    public void testRoundRobinStrategy() {
        LoadBalancer balancer = new LoadBalancer("roundRobin");
        List<String> servers = Arrays.asList("server1", "server2", "server3");
        
        // Test multiple selections to verify round robin behavior
        String result1 = balancer.selectServer(servers, "test content");
        String result2 = balancer.selectServer(servers, "test content");
        String result3 = balancer.selectServer(servers, "test content");
        String result4 = balancer.selectServer(servers, "test content");
        
        assertNotNull("First selection should not be null", result1);
        assertNotNull("Second selection should not be null", result2);
        assertNotNull("Third selection should not be null", result3);
        assertNotNull("Fourth selection should not be null", result4);
        
        // Verify round robin behavior (should cycle through servers)
        assertEquals("Fourth selection should match first (round robin)", result1, result4);
    }
    
    @Test
    public void testRandomStrategy() {
        LoadBalancer balancer = new LoadBalancer("random");
        List<String> servers = Arrays.asList("server1", "server2", "server3");
        
        // Test multiple selections
        for (int i = 0; i < 10; i++) {
            String result = balancer.selectServer(servers, "test content");
            assertNotNull("Random selection should not be null", result);
            assertTrue("Selected server should be in the list", servers.contains(result));
        }
    }
    
    @Test
    public void testCommaSeparatedServers() {
        LoadBalancer balancer = new LoadBalancer("roundRobin");
        String serverAddresses = "server1,server2,server3";
        
        String result = balancer.selectServer(serverAddresses, "test content");
        assertNotNull("Selection from comma-separated addresses should not be null", result);
        assertTrue("Selected server should be one of the addresses", 
                  result.equals("server1") || result.equals("server2") || result.equals("server3"));
    }
    
    @Test
    public void testEmptyServerList() {
        LoadBalancer balancer = new LoadBalancer("roundRobin");
        
        String result1 = balancer.selectServer((List<String>) null, "test content");
        String result2 = balancer.selectServer("", "test content");
        
        assertNull("Selection from null list should be null", result1);
        assertNull("Selection from empty string should be null", result2);
    }
    
    @Test
    public void testSingleServer() {
        LoadBalancer balancer = new LoadBalancer("roundRobin");
        List<String> servers = Arrays.asList("server1");
        
        String result1 = balancer.selectServer(servers, "test content");
        String result2 = balancer.selectServer(servers, "test content");
        
        assertEquals("Single server should always be selected", "server1", result1);
        assertEquals("Single server should always be selected", "server1", result2);
    }
    
    @Test
    public void testStrategyName() {
        LoadBalancer balancer1 = new LoadBalancer("roundRobin");
        LoadBalancer balancer2 = new LoadBalancer("random");
        
        assertEquals("Round robin strategy name should be correct", "roundRobin", balancer1.getStrategyName());
        assertEquals("Random strategy name should be correct", "random", balancer2.getStrategyName());
    }
    
    @Test
    public void testInvalidStrategy() {
        LoadBalancer balancer = new LoadBalancer("invalidStrategy");
        List<String> servers = Arrays.asList("server1", "server2");
        
        // Should fallback to round robin
        String result = balancer.selectServer(servers, "test content");
        assertNotNull("Invalid strategy should fallback to default", result);
        assertTrue("Selected server should be in the list", servers.contains(result));
    }
}
