# Spring Microservices with Kafka Communication

## Overview

This project demonstrates a microservices-based architecture using Spring Boot, Kafka, and Docker Compose. It includes four microservices—**Product**, **Customer**, **Order**, and **Notification**—designed for an e-commerce application. These services communicate with each other via Kafka to handle events such as product orders, customer notifications, and other actions across the application. The Kafka integration allows real-time event streaming between microservices, showcasing the asynchronous communication model.

## Folder Structure

The main project folder includes the following:
- **Product-service**: Manages product-related operations.
- **Customer-service**: Handles customer data and interactions.
- **Order-service**: Processes customer orders.
- **Notification-service**: Sends notifications based on order and customer actions.
- **Compose.yml**: Docker Compose configuration for orchestrating the microservices, Kafka, and Zookeeper.
- **Readme.md**: Project documentation.
- **.gitignore**: Specifies files and directories to ignore in version control.

## Prerequisites

- **Docker** and **Docker Compose** installed on your machine.
- **Java JDK 17** or above for local development

## Getting Started

Follow these steps to clone and run the project locally using Docker Compose.

### 1. Clone the Repository
```bash
git clone https://github.com/makerjose/spring-microservices-with-kafka.git
cd spring-microservices-with-kafka
```

### Step 2: Build the Microservices

Each microservice is a standalone Spring Boot project. You can build them using Maven:
```bash
cd product-service mvn clean install

cd ../customer-service mvn clean install

cd ../order-service mvn clean install

cd ../notification-service mvn clean install
```


### Step 3: Run the Services with Docker Compose

From the main project directory, use Docker Compose to start all the services, including Kafka and Zookeeper.
```bash
docker-compose up --build
```

This will:
-	Launch Kafka and Zookeeper for handling message brokering.
-	Start each microservice on its designated port as specified in the Compose.yml file.

### Step 4: Verify the Services

•	Access each microservice to confirm they are running:
  -	Product Service: http://localhost:8081
  -	Customer Service: http://localhost:8082
  -	Order Service: http://localhost:8083
  -	Notification Service: http://localhost:8084
•	Kafka will be available at localhost:9092 for any internal connections between microservices.

### Testing Kafka Communication

Each microservice has Kafka producers and consumers set up for event communication. To test this:
1.	Send a message from one microservice to a Kafka topic.
2.	Observe how other microservices that subscribe to this topic react to the event (check logs for message consumption).

## Stopping the Services

To stop all running containers, run:
```bash
docker-compose down
```

## Additional Notes

-	Each microservice can be configured independently via its application.yml file, allowing custom Kafka topics, consumer groups, etc.
-	Kafka logs can be accessed from the Docker logs if you want to monitor message flow and debug issues.

## License

This project is licensed under the MIT License.
