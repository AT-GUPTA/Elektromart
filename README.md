# Elektromart Online Storefront

Welcome to the Elektromart Online Storefront - an eCommerce platform that allows customers to view and purchase products and enables the merchant’s staff to manage the products and orders.

## Getting Started

These instructions will guide you through setting up the project on your local machine for development and testing purposes.

### Prerequisites

What things you need to install the software and how to install them:

- Java JDK 21
- Maven (for building the backend)
- Node.js and npm (for running the front-end)
- SQL client (for database management)
- AWS CLI (optional, for interacting with AWS services)

### Installing

A step by step series of examples that tell you how to get a development environment running.

#### Frontend Setup

1. Clone the repository to your local machine:
    ```bash
    git clone https://github.com/AT-GUPTA/Elektromart.git
    cd Elektromart
    ```

2. Navigate to the front-end module directory and install dependencies:
    ```bash
    cd source/ui/elektromart
    npm install
    ```

3. Start the front-end application:
    ```bash
    npm start
    ```
   The application will be available at `http://localhost:3000`.

#### Backend Setup

1. Navigate to the backend module directory:
    ```bash
    cd source/api_v2/elektromart
    ```

2. Build the project using Maven:
    ```bash
    mvn clean install
    ```

3. Run the compiled `.war` file:
    - On Unix/Linux:
        ```bash
        java -jar target/elektromart.war
        ```
    - On Windows:
        ```bash
        java -jar target\elektromart.war
        ```
   The API will be accessible on `http://localhost:8080`.

### Database Configuration

Configure your Spring Boot application properties to connect to the AWS RDS MySQL database with the following details:

```properties
spring.datasource.url=jdbc:mysql://elektromart-db.commiawyevbm.us-east-2.rds.amazonaws.com:3306/elektromart.db
spring.datasource.username=admin
spring.datasource.password=elektrodevs
