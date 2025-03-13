# Spring Boot Semantic Kernel Demo

This project demonstrates the integration of Microsoft's Semantic Kernel with Spring Boot to create an AI-powered chat application.

## Prerequisites

- Java 21 or later
- Maven 3.8 or later
- OpenAI API Key and Key ID (for DIAL integration)

## Features

- Integration with Microsoft Semantic Kernel
- RESTful API endpoints for chat functionality
- OpenAPI documentation with Swagger UI
- Actuator endpoints for monitoring
- Comprehensive test coverage
- Error handling and validation

## Configuration

1. Create a `application.properties` file in `src/main/resources/` with the following structure:

```properties
# OpenAI Configuration
client-openai-key=your_api_key_here
client-openai-endpoint=https://ai-proxy.lab.epam.com
client-openai-deployment-name=gpt-3.5-turbo
client-openai-key-id=your_key_id_here

# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Logging Configuration
logging.level.root=INFO
logging.level.com.example.semantickerneldemo=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n

# Actuator Configuration
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=always

# Swagger Configuration
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
springdoc.swagger-ui.tagsSorter=alpha
```

2. For security, you can also use environment variables:
```bash
export CLIENT_OPENAI_KEY=your_api_key_here
export CLIENT_OPENAI_KEY_ID=your_key_id_here
```

## Building the Application

1. Clone the repository:
```bash
git clone https://github.com/pratt19/semantic-kernel-demo.git
cd semantic-kernel-demo
```

2. Build the project:
```bash
mvn clean install
```

## Running the Application

1. Start the application:
```bash
mvn spring-boot:run
```

2. The application will be available at:
   - Main API: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/api/swagger-ui.html
   - Actuator: http://localhost:8080/api/actuator

## API Endpoints

### Chat Endpoint

- **URL**: `/api/chat`
- **Method**: POST
- **Request Body**:
```json
{
    "message": "Your message here"
}
```
- **Response**:
```json
{
    "response": "AI generated response"
}
```

## Testing

Run the tests using:
```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── semantickerneldemo/
│   │               ├── config/
│   │               ├── controller/
│   │               ├── model/
│   │               ├── service/
│   │               └── SemanticKernelDemoApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/
            └── example/
                └── semantickerneldemo/
                    ├── controller/
                    ├── service/
                    └── integration/
```

## Dependencies

- Spring Boot 3.2.3
- Semantic Kernel 0.4.7
- SpringDoc OpenAPI 2.3.0
- Lombok
- Spring Boot Test

## Security Notes

- Never commit API keys or sensitive credentials to version control
- Use environment variables or secure configuration management for sensitive data
- Consider using a secrets management service in production environments

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 