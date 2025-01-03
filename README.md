# Clip Application

## Overview
The Clip Application is a web application designed for storing and sharing clips. It utilizes Spring Boot for the backend and Vaadin for the frontend, providing a seamless user experience.

## Features
- Store and share clips

## Technologies Used
- Java
- Spring Boot
- Vaadin
- CSS

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven
- A compatible database (e.g., MySQL, PostgreSQL)

### Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd clip
   ```

2. Configure the properties file:
   - Locate `clip.properties` in the root directory.
   - Update the database connection details:
     ```
     databaseIP=<your-database-ip>
     databaseUser=<your-database-username>
     databasePassword=<your-database-password>
     ```

3. Build the project:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### Accessing the Application
Once the application is running, you can access it at: