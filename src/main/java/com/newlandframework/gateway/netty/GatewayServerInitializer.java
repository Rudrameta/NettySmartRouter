package com.newlandframework.gateway.netty;

import com.newlandframework.gateway.commons.GatewayOptions;
import com.newlandframework.gateway.commons.LoadBalancer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Gateway server initializer with load balancing support
 * Initializes the channel pipeline and configures load balancer
 * 
 * @author David Yu
 * @version 1.0
 */
public class GatewayServerInitializer extends ChannelInitializer<SocketChannel> {
    
    private static ApplicationContext context;
    private static LoadBalancer loadBalancer;
    
    static {
        // Initialize Spring context
        context = new ClassPathXmlApplicationContext("classpath:netty-gateway.xml");
        
        // Initialize load balancer from configuration
        GatewayOptions options = context.getBean("options", GatewayOptions.class);
        if (options.isLoadBalanceEnabled()) {
            loadBalancer = new LoadBalancer(options.getLoadBalanceStrategy());
            System.out.println("[NETTY-GATEWAY] Load balancer initialized with strategy: " + 
                             loadBalancer.getStrategyName());
        }
    }
    
    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpRequestDecoder());
        p.addLast(new HttpResponseEncoder());
        
        // Create handler and set load balancer
        GatewayServerHandler handler = new GatewayServerHandler();
        if (loadBalancer != null) {
            handler.setLoadBalancer(loadBalancer);
        }
        
        p.addLast(handler);
    }
    
    /**
     * Get the Spring application context
     * 
     * @return Application context
     */
    public static ApplicationContext getContext() {
        return context;
    }
    
    /**
     * Get the configured load balancer
     * 
     * @return Load balancer instance
     */
    public static LoadBalancer getLoadBalancer() {
        return loadBalancer;
    }
}
