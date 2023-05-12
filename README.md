# Java HTTP Server

This is a simple Java HTTP server that handles incoming HTTP requests and returns appropriate responses. The server is implemented using Java's built-in `java.net` and `java.io` libraries.

## Features

- Handles incoming HTTP requests and parses the request headers and body.
- Supports common HTTP methods such as GET, POST, PUT, etc.
- Implements basic routing to map request targets to appropriate request handlers.
- Generates HTTP responses with proper status codes, headers, and response bodies.
- Supports JSON and other common media types for request and response content.
- Provides a modular and extensible architecture for adding custom request handlers and middleware.
- Includes unit tests to verify the correctness of the server's functionality.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 11 or later

### Usage

1. Clone the repository:
    ```shell
    git clone https://github.com/akkady/SimpleHttpServer.git
    ```
2. Build the project:
    ```shell
    $ cd SimpleHttpServer/
    $ mvn clean package 
    ```

3. Run the server:
   ```shell
   $ java -jar target/SimpleHttpServer.jar
   ```

4. The server will start listening on the default port 8080. You can access it by visiting `http://localhost:8080` in your web browser.

5. Customize the server behavior by modifying the configuration files or adding custom request handlers and middleware as needed.

## Configuration

The server can be configured by modifying the `config.json` file. Some configurable options include:

- Port number: Specify the port number on which the server should listen.

## Adding Request Handlers

To add custom request handlers, follow these steps:

1. Implement the `RequestHandler` interface and override the `handle` method to define the desired logic for handling the request.

   ```java
   public class MyRequestHandler implements RequestHandler {
       @Override
       public ResponseEntity<?> handle(HttpRequest request) {
           // Implement the logic to handle the request and generate an appropriate response
           // ...
       }
   }
   ```
2. Register the request handler in the server by adding it to the appropriate routing rules or middleware.
   
- In the class `Mapper` you will find this code :
   ```
  addRoute(new Route(Pattern.compile("^/users$"), HttpMethod.GET, UserController::getUsers));
  addRoute(new Route(Pattern.compile("^/users/\\d+$"), HttpMethod.GET, UserController::getUserById));
   ```
   and here you can add your custom rotes.
## Testing
   The project includes unit tests to verify the correctness of the server's functionality. You can run the tests using the following command:
   ```shell
     mvn test
   ```

