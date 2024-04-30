# Fusion Plex - Catalogue Management

Fusion Plex is a comprehensive catalogue management system designed to provide a foundation for managing movie, TV series, and documentary catalogues. This project serves as a base for educational purposes, allowing others to fork and utilize it as a starting point for learning about web application development with a focus on catalogue management.

## Project Overview

The Fusion Plex system is intended to demonstrate key aspects of web application development, including but not limited to, database management, CRUD operations, and user interface design. It's built with a modern technology stack that can be adapted for teaching various software development concepts.

## Getting Started

These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

## Tutorial Overview

In this tutorial overview, we will be diving into the development of a quartz scheduler for Fusion Plex, our catalogue management system. The addition of the quartz scheduler is aimed at automating key operations such as data synchronization with external movie databases, scheduled updates of movie listings, and timely notifications for users. Through this integration, we aim to streamline repetitive tasks, ensuring that Fusion Plex remains up-to-date and efficient in delivering content. This not only enhances the user experience by providing them with the latest information but also significantly reduces manual workload, making our system more robust and reliable.   


### Prerequisites

Before you begin, ensure you have the following installed:
- JDK 21 or later
- Maven 3.9.6 or later
- Docker

Additionally, familiarity with Spring Boot and PostgreSQL is recommended to fully understand the project setup and functionality.

### TMDB Api Generator
For this tutorial you need to create a TMDB API tutorial.
1. Go to the following url https://www.themoviedb.org/
2. If you don't have an account create one.
3. After account has been created or you have logged in go to the Settings.
4. Select API
5. In the Tab select Generate or Regenerate Key
6. Copy the API REad Access Token
7. Paste the Access Token in the application.yaml file under 
    
**fusion-pex: bearer-token:**

### Technology Stack for this Tutorial
* Quartz Scheduler

### Initial Setup

To obtain the initial project setup, use the base branch of this repository:

```bash
git clone -b start git@github.com:softwarebuilding-io/quartz-scheduler.git

cd quartz-scheduler
```

### Initial Project
This branch contains the base project structure and configurations necessary to start developing your application.

```bash
git checkout start
```

### Final Project
For those interested in viewing the completed project with all tutorials features implemented, switch to the final branch:

```bash
git checkout final
```

### Database Setup
Fusion Plex uses PostgreSQL for data persistence. To create and run a PostgreSQL database using Docker, execute the following Maven command:

```bash
mvn clean compile -Pdocker
```
This command cleans the project, compiles the source code, and runs a Docker profile defined in the pom.xml that sets up a PostgreSQL container suitable for development.

### Database Connection

- hostname: localhost
- database: postgres
- username: admin
- password: admin@1234


### Running the Application
After setting up the database, you can start the application by running:

```bash
mvn spring-boot:run
```
This command will start the Spring Boot application on the default port (usually 8080). Access the application by navigating to http://localhost:8080/fusion-plex in your web browser.