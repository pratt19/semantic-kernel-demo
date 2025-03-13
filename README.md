# Spring Boot Semantic Kernel Demo

This project demonstrates the integration of Microsoft's Semantic Kernel with Spring Boot for AI-powered chat functionality.

## Prerequisites

- Java 17 or later
- Maven 3.6 or later
- OpenAI API key
- Azure OpenAI API key (optional)

## Configuration

1. Set up your environment variables:

```bash
export OPENAI_API_KEY=your_openai_api_key
export AZURE_OPENAI_API_KEY=your_azure_openai_api_key
export AZURE_OPENAI_ENDPOINT=your_azure_openai_endpoint
```

2. Configure the application properties in `src/main/resources/application.properties`

The application uses the following API endpoint:
- OpenAI API Endpoint: https://ai-proxy.lab.epam.com

## Building the Application

```bash
mvn clean install
```

## Running the Application

```bash
mvn spring:boot run
```

## API Usage

Send a POST request to `/api/chat` with the following JSON body:

```json
{
    "input": "I want to find top-10 books about world history"
}
```

The response will be in the following format:

```json
{
    "input": "I want to find top-10 books about world history",
    "response": "Generated response from the AI model"
}
```

## Features

- Integration with OpenAI's GPT models through EPAM AI Proxy
- Support for Azure OpenAI (optional)
- RESTful API endpoint for chat functionality
- Semantic Kernel integration for enhanced AI capabilities

## Testing

Run the tests using:

```bash
mvn test
```

## API Endpoints

- Chat API: `https://ai-proxy.lab.epam.com/api/chat`
- Swagger UI: `https://ai-proxy.lab.epam.com/api/swagger-ui.html`
- API Documentation: `https://ai-proxy.lab.epam.com/api/api-docs` 