# NettySmartRouter

A high-performance HTTP router built with Netty and Spring Framework, designed for intelligent content-based request routing and load balancing.

## Overview

NettySmartRouter is a lightweight, high-performance HTTP router that provides intelligent request routing capabilities based on content matching. It uses Netty for high-performance I/O operations and Spring Framework for configuration management and dependency injection.

## Features

- **High Performance**: Built on Netty 4.1.0 for excellent I/O performance
- **Intelligent Routing**: Content-based request routing with keyword matching
- **Spring Integration**: Full Spring Framework integration for configuration management
- **Configurable**: Flexible configuration through properties files
- **Load Balancing**: Support for multiple backend servers with fallback mechanisms
- **Asynchronous Processing**: Non-blocking request processing for better throughput

## Architecture

The router consists of several key components:

- **GatewayServer**: Main server class that initializes the Netty server
- **GatewayServerHandler**: Core request handler that processes HTTP requests
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

#### Router Configuration (`netty-gateway.properties`)

Configure default routing rules:

```properties
# Router configuration format:
# netty-gateway.configX.serverPath ==> URL path keyword
# netty-gateway.configX.defaultAddr ==> Default forwarding URL when no specific match is found

netty-gateway.config1.serverPath=fcgi-bin/UIG_SFC_186
netty-gateway.config1.defaultAddr=http://10.46.158.10:8088/fcgi-bin/UIG_SFC_186

netty-gateway.config2.serverPath=fcgi-bin/BSSP_SFC
netty-gateway.config2.defaultAddr=http://10.46.158.10:8089/fcgi-bin/BSSP_SFC
```

#### Route Configuration (`netty-route.properties`)

Configure specific routing rules with keyword matching:

```properties
# Route configuration format:
# netty-gateway.configX.serverPath ==> URL path keyword
# netty-gateway.configX.keyWord ==> Content matching keywords (supports 1~N keywords, separated by commas, AND logic)
# netty-gateway.configX.matchAddr ==> Target URL when keywords match successfully

netty-gateway.config1.serverPath=fcgi-bin/UIG_SFC_186
netty-gateway.config1.keyWord=1,2,3
netty-gateway.config1.matchAddr=http://10.46.158.20:8088/fcgi-bin/UIG_SFC_186

netty-gateway.config2.serverPath=fcgi-bin/UIG_SFC_186
netty-gateway.config2.keyWord=1,2,3,4
netty-gateway.config2.matchAddr=http://10.46.158.20:8088/fcgi-bin/UIG_SFC_186
```

#### Server Configuration (`netty-gateway.xml`)

Configure server settings:

```xml
<bean id="options" class="com.newlandframework.gateway.commons.GatewayOptions">
    <property name="gatewayPort" value="8999"/>
</bean>
```

## How It Works

1. **Request Reception**: The router receives HTTP requests on the configured port
2. **Path Matching**: The router matches the request URI against configured server paths
3. **Content Analysis**: For matched paths, the router analyzes request content for keyword matches
4. **Route Selection**: 
   - If keywords match, forward to the specific target address
   - If no keywords match, forward to the default address
   - If no path matches, return localhost response
5. **Request Forwarding**: The router forwards the request to the selected backend service
6. **Response Handling**: The response from the backend is returned to the client

## Configuration Options

### Router Options

- `gatewayPort`: Port number for the router server (default: 8999)
- `GATEWAY_OPTION_CHARSET`: Character encoding (default: GBK)
- `GATEWAY_OPTION_PARALLEL`: Number of worker threads (default: CPU cores)
- `GATEWAY_OPTION_HTTP_POST`: HTTP request timeout in milliseconds (default: 60000)

### Routing Rules

The router supports two types of routing rules:

1. **Default Routes**: Defined in `netty-gateway.properties`
   - Matches requests by URI path
   - Provides fallback destination when no specific route matches

2. **Content-based Routes**: Defined in `netty-route.properties`
   - Matches requests by URI path AND content keywords
   - Supports multiple keywords with AND logic
   - Takes precedence over default routes

## Example Usage

### Request Flow Example

1. Client sends POST request to `http://localhost:8999/fcgi-bin/UIG_SFC_186`
2. Router matches the path `fcgi-bin/UIG_SFC_186`
3. Router checks request content for keywords (e.g., "1", "2", "3")
4. If keywords match, forwards to `http://10.46.158.20:8088/fcgi-bin/UIG_SFC_186`
5. If no keywords match, forwards to `http://10.46.158.10:8088/fcgi-bin/UIG_SFC_186`
6. Returns backend response to client

## Performance Characteristics

- **High Throughput**: Asynchronous, non-blocking I/O operations
- **Low Latency**: Direct memory access with Netty's zero-copy capabilities
- **Scalability**: Configurable thread pool based on available CPU cores
- **Memory Efficiency**: Efficient buffer management and object pooling

## Monitoring and Logging

The router provides built-in logging for:
- Request details and content
- URL matching results
- Error conditions and exceptions
- System information on startup

## License

This project is open source. Please refer to the original repository for license information.


