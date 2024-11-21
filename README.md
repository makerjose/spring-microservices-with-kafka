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
cd Product-microservice mvn clean install

cd ../Customer-microservice mvn clean install

cd ../Order-microservice mvn clean install

cd ../Notification-microservice mvn clean install