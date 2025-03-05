# Stock Microservice System

This project is a **stock trading microservice system** that allows users to buy and sell stocks while keeping track of their portfolios. The system consists of multiple microservices and is deployed using **Kubernetes**.

## Architecture

The system is composed of the following microservices:

1. **Market Data Service**
    - Provides real-time stock prices (currently randomized).

2. **User Service**
    - Manages user accounts and balances.

3. **Portfolio Service**
    - Tracks the stocks a user owns.

4. **Order Service** *(Core Service)*
    - Orchestrates stock purchases and sales by interacting with the **User**, **Portfolio**, and **Market Data** services.
    - Ensures users have sufficient balance before completing a purchase.

5. **Notification Service**
    - Sends email notifications for important events.
    - Uses **RabbitMQ** for asynchronous message queuing to prevent delays due to email latency.

## Service Communication

- **OpenFeign** is used for inter-service REST communication.
- **Kubernetes Service Discovery** is leveraged to dynamically resolve service names, eliminating the need for hardcoded URLs.

## Workflow

- A user places a **buy order** through the **Order Service**.
- The **Order Service**:
    1. Queries the **User Service** for available balance.
    2. Requests stock prices from the **Market Data Service**.
    3. If the user has enough balance, the **Portfolio Service** updates the user's stock holdings.
    4. A message is sent to **RabbitMQ**, which the **Notification Service** consumes to send confirmation emails.

## Deployment & Testing

- **Kubernetes** is used for deployment and service orchestration.
- **RabbitMQ** handles asynchronous email notifications.
- **K6 Load Testing** is used to simulate high traffic and ensure system reliability.

## Technologies Used

- **Java (Spring Boot)**
- **OpenFeign** for inter-service REST communication
- **Kubernetes Service Discovery**
- **Docker & Kubernetes**
- **RabbitMQ**
- **K6 for Load Testing**