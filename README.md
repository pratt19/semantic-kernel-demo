# Semantic Kernel Demo - Module 03

This module demonstrates the implementation of Semantic Kernel with multiple AI model support through the Dial service.

## Features

- Support for multiple AI models (GPT-3.5, Mixtral-8x7B)
- Configurable model settings
- Asynchronous model comparison
- RESTful API endpoints
- Comprehensive error handling

## Setup

1. Set environment variable:
```bash
export MY_KEY=your_api_key_here
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

## API Endpoints

### Compare Models
POST /api/models/compare
```json
{
    "prompt": "Your prompt text here"
}
```

## Configuration

Model configurations can be adjusted in `application.properties`:

```properties
dial.api.base-url=https://ai-proxy.lab.epam.com
dial.deployment.gpt=gpt-35-turbo
dial.deployment.mixtral=Mixtral-8x7B-Instruct-v0.1
```

## Design Patterns Used

- Interface Segregation Principle (ISP)
- Dependency Injection
- Builder Pattern
- Factory Pattern
- Facade Pattern
- DTO Pattern

## Error Handling

The application includes comprehensive error handling with custom exceptions and a global exception handler. 