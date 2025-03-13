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

The application uses the following configuration in `application.properties`:

```properties
# OpenAI Configuration
client-openai-key=dial-axqsgux6v4o5sjkcpdx0yz12dbx
client-openai-endpoint=https://ai-proxy.lab.epam.com
client-openai-deployment-name=gpt-3.5-turbo
client-openai-key-id=EPM-AIED-Prateek_Shukla_Java_Course_March_4_2025
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

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 