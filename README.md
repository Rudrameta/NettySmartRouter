
# NettySmartRouter
<img width="3367" height="1793" alt="netty_smart_router_diagram" src="https://github.com/user-attachments/assets/e17db604-bcc3-4c2c-a419-1c36e7f7b174" />
A high-performance HTTP router built with Netty and Spring Framework, designed for intelligent content-based request routing and load balancing.

## Overview

NettySmartRouter is a lightweight, high-performance HTTP router that provides intelligent request routing capabilities based on content matching and advanced load balancing. It uses Netty for high-performance I/O operations and Spring Framework for configuration management and dependency injection.

## Features

- **High Performance**: Built on Netty 4.1.0 for excellent I/O performance
- **Intelligent Routing**: Content-based request routing with keyword matching
- **Load Balancing**: Multiple load balancing strategies (Round Robin, Random, Weighted Round Robin)
- **Spring Integration**: Full Spring Framework integration for configuration management
- **Configurable**: Flexible configuration through properties files
- **Asynchronous Processing**: Non-blocking request processing for better throughput
- **Health Check Support**: Optional health checking for backend servers

## Architecture

The router consists of several key components:

- **GatewayServer**: Main server class that initializes the Netty server
- **GatewayServerHandler**: Core request handler that processes HTTP requests with load balancing
- **LoadBalancer**: Load balancing engine with multiple strategies
- **RoutingLoader**: Spring-based configuration loader for routing rules
- **HttpClientUtils**: HTTP client utility for forwarding requests to backend services

## Dependencies

- Java 6+
- Netty 4.1.0.Final
- Spring Framework 4.1.1.RELEASE
- Maven 3.3+

## Quick Start

### 1. Build the Project

```bash
mvn clean package
```

### 2. Run the Router

```bash
java -jar target/NettyGateway-1.0-SNAPSHOT.jar
```

The router will start on port 8999 by default.

### 3. Configuration

#### Router Configuration (`netty-gateway.xml`)

Configure server settings and load balancing:

```xml
<bean id="options" class="com.newlandframework.gateway.commons.GatewayOptions">
    <property name="gatewayPort" value="8999"/>
    <!-- Load balance configuration -->
    <property name="loadBalanceEnabled" value="true"/>
    <property name="loadBalanceStrategy" value="roundRobin"/>
    <property name="healthCheckEnabled" value="false"/>
    <property name="healthCheckInterval" value="30000"/>
</bean>
```

#### Gateway Configuration (`netty-gateway.properties`)

Configure default routing rules with load balancing support:

```properties
# Router configuration format:
# netty-gateway.configX.serverPath ==> URL path keyword
# netty-gateway.configX.defaultAddr ==> Default forwarding URL (supports multiple servers comma-separated)

# Example with load balancing (multiple servers)
netty-gateway.config1.serverPath=fcgi-bin/UIG_SFC_186
netty-gateway.config1.defaultAddr=http://10.46.158.10:8088/fcgi-bin/UIG_SFC_186,http://10.46.158.11:8088/fcgi-bin/UIG_SFC_186,http://10.46.158.12:8088/fcgi-bin/UIG_SFC_186

# Single server (no load balancing)
netty-gateway.config2.serverPath=api/health
netty-gateway.config2.defaultAddr=http://10.46.158.10:8090/api/health
```

#### Route Configuration (`netty-route.properties`)

Configure specific routing rules with keyword matching and load balancing:

```properties
# Route configuration format:
# netty-gateway.configX.serverPath ==> URL path keyword
# netty-gateway.configX.keyWord ==> Content matching keywords (supports 1~N keywords, separated by commas, AND logic)
# netty-gateway.configX.matchAddr ==> Target URL when keywords match successfully (supports multiple servers comma-separated)

# Example with load balancing
netty-gateway.config1.serverPath=fcgi-bin/UIG_SFC_186
netty-gateway.config1.keyWord=1,2,3
netty-gateway.config1.matchAddr=http://10.46.158.20:8088/fcgi-bin/UIG_SFC_186,http://10.46.158.21:8088/fcgi-bin/UIG_SFC_186
```

#### Load Balance Configuration (`netty-loadbalance.properties`)

Configure load balancing behavior:

```properties
# Load balance configuration
gateway.loadBalance.enabled=true
gateway.loadBalance.strategy=roundRobin

# Health check configuration
gateway.loadBalance.healthCheck.enabled=false
gateway.loadBalance.healthCheck.interval=30000
```

## Load Balancing

### Supported Strategies

1. **Round Robin**: Distributes requests evenly across all available servers
2. **Random**: Randomly selects a server from the available server list
3. **Weighted Round Robin**: Distributes requests based on server weights (planned)

### Configuration

Load balancing is automatically applied when multiple servers are specified in the configuration (comma-separated addresses). Single server configurations bypass load balancing.

### Example Usage

```properties
# Multiple servers - load balancing applied
netty-gateway.config1.defaultAddr=http://server1:8080/api,http://server2:8080/api,http://server3:8080/api

# Single server - no load balancing
netty-gateway.config2.defaultAddr=http://server1:8080/api
```

## How It Works

1. **Request Reception**: The router receives HTTP requests on the configured port
2. **Path Matching**: The router matches the request URI against configured server paths
3. **Content Analysis**: For matched paths, the router analyzes request content for keyword matches
4. **Load Balancing**: If multiple servers are configured, applies load balancing strategy
5. **Route Selection**: 
   - If keywords match, forward to the specific target address (with load balancing)
   - If no keywords match, forward to the default address (with load balancing)
   - If no path matches, return localhost response
6. **Request Forwarding**: The router forwards the request to the selected backend service
7. **Response Handling**: The response from the backend is returned to the client

## Configuration Options

### Gateway Options

- `gatewayPort`: Port number for the router server (default: 8999)
- `loadBalanceEnabled`: Enable/disable load balancing (default: true)
- `loadBalanceStrategy`: Load balancing strategy (default: roundRobin)
- `healthCheckEnabled`: Enable health checking (default: false)
- `healthCheckInterval`: Health check interval in milliseconds (default: 30000)

### Load Balancing Strategies

- `roundRobin`: Round robin distribution
- `random`: Random server selection
- `weightedRoundRobin`: Weighted round robin (planned)

### Routing Rules

The router supports two types of routing rules:

1. **Default Routes**: Defined in `netty-gateway.properties`
   - Matches requests by URI path
   - Supports multiple servers for load balancing
   - Provides fallback destination when no specific route matches

2. **Content-based Routes**: Defined in `netty-route.properties`
   - Matches requests by URI path AND content keywords
   - Supports multiple keywords with AND logic
   - Supports multiple servers for load balancing
   - Takes precedence over default routes

## Example Usage

### Request Flow Example with Load Balancing

1. Client sends POST request to `http://localhost:8999/fcgi-bin/UIG_SFC_186`
2. Router matches the path `fcgi-bin/UIG_SFC_186`
3. Router checks request content for keywords (e.g., "1", "2", "3")
4. If keywords match, applies load balancing to `http://10.46.158.20:8088/fcgi-bin/UIG_SFC_186,http://10.46.158.21:8088/fcgi-bin/UIG_SFC_186`
5. If no keywords match, applies load balancing to default servers
6. Returns backend response to client

### Load Balancing Example

```properties
# Configuration with 3 servers
netty-gateway.config1.defaultAddr=http://server1:8080/api,http://server2:8080/api,http://server3:8080/api
```

With Round Robin strategy:
- Request 1 → server1:8080
- Request 2 → server2:8080  
- Request 3 → server3:8080
- Request 4 → server1:8080 (cycles back)

## Performance Characteristics

- **High Throughput**: Asynchronous, non-blocking I/O operations
- **Low Latency**: Direct memory access with Netty's zero-copy capabilities
- **Scalability**: Configurable thread pool based on available CPU cores
- **Memory Efficiency**: Efficient buffer management and object pooling
- **Load Distribution**: Even distribution across multiple backend servers

## Monitoring and Logging

The router provides built-in logging for:
- Request details and content
- URL matching results
- Load balancing decisions
- Error conditions and exceptions
- System information on startup

## Testing

Run the test suite to verify load balancing functionality:

```bash
mvn test
```

## License

This project is open source. Please refer to the original repository for license information.
