# Appointment Scheduler

A full-stack appointment scheduling application built with **Angular, Spring Boot, PostgreSQL, and Docker Compose**.

The application allows users to create, view, and delete appointments while the backend handles validation and prevents double bookings.

The main purpose of this project is to demonstrate how a full-stack application can be separated into multiple Docker containers and managed as a single system using Docker Compose.

---

## Technologies

### Frontend

* Angular
* TypeScript
* HTML
* CSS

### Backend

* Java 21
* Spring Boot
* Spring Web
* Spring Data JPA
* Lombok

### Database

* PostgreSQL

### DevOps

* Docker
* Docker Compose
* Docker named volumes

### Development Tools

* IntelliJ IDEA
* Postman
* pgAdmin
* Git / GitHub

---

## Architecture

```text
                    ┌─────────────────────┐
                    │       Browser       │
                    └──────────┬──────────┘
                               │
                               │ HTTP
                               ▼
                    ┌─────────────────────┐
                    │ Angular Frontend    │
                    │     Container       │
                    └──────────┬──────────┘
                               │
                               │ REST API
                               ▼
                    ┌─────────────────────┐
                    │ Spring Boot Backend │
                    │     Container       │
                    └──────────┬──────────┘
                               │
                               │ JDBC
                               ▼
                    ┌─────────────────────┐
                    │     PostgreSQL      │
                    │     Container       │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │ appointment_data    │
                    │   Named Volume      │
                    └─────────────────────┘
```

Each part of the application runs as a separate container. Docker Compose manages the containers and allows them to communicate with each other.

---

## Features

* Create appointments
* View appointments
* Delete appointments
* Backend validation
* Prevent double bookings
* PostgreSQL database
* Persistent database storage
* Automatic database seed data
* Separate frontend, backend, and database containers
* Entire application can be started with one Docker Compose command

---

## Project Structure

```text
appointment-scheduler/
│
├── backend/
│   ├── src/
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   └── Dockerfile
│
├── frontend/
│   ├── src/
│   ├── package.json
│   └── Dockerfile
│
├── db/
│   └── init/
│       └── 01-seed.sql
│
├── docker-compose.yml
└── README.md
```

---

# Running the Application Without Docker

Docker is not required for development. The frontend and backend can be run separately.

## Backend

Open a terminal inside the `backend` directory.

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The Spring Boot backend runs on:

```text
http://localhost:8080
```

---

## Frontend

Open another terminal inside the `frontend` directory:

```powershell
ng serve
```

The Angular application runs on:

```text
http://localhost:4200
```

---

# Running the Application With Docker

## Prerequisites

Install:

* Docker Desktop
* Git

Docker Desktop must be running before starting the application.

---

## Start the Application

From the root `appointment-scheduler` directory:

```powershell
docker compose up --build
```

Docker Compose will:

1. Build the backend image
2. Build the frontend image
3. Pull the PostgreSQL image if necessary
4. Create the containers
5. Create the PostgreSQL database
6. Initialize the seed data
7. Create/use the named database volume
8. Start the entire application

The application can then be accessed through the frontend.

```text
http://localhost:4200
```

The backend API is available at:

```text
http://localhost:8080
```

---

# Stopping the Application

To stop the containers:

```powershell
docker compose down
```

This removes the containers but **does not remove the database volume**.

---

# Database Persistence

PostgreSQL uses a Docker named volume:

```yaml
volumes:
  appointment_data:
```

The volume allows database information to survive container restarts.

For example:

```text
Create appointment
       ↓
PostgreSQL
       ↓
appointment_data volume
       ↓
Container stopped
       ↓
Container started again
       ↓
Appointment still exists
```

This separates the database's persistent data from the lifecycle of the PostgreSQL container.

---

# Resetting the Database

To remove the containers **and** the named volume:

```powershell
docker compose down -v
```

The next time the application is started, PostgreSQL will create a fresh database and the seed data will be initialized again.

> **Warning:** Removing the volume deletes the persisted database data.

---

# Seed Data

The project contains initial database data in:

```text
db/init/01-seed.sql
```

The SQL script provides sample appointments when the PostgreSQL database is initialized.

Seed data is not required for the application to function, but it makes the application immediately usable for testing and demonstration.

---

# Docker Services

Docker Compose manages three services.

### Frontend

The Angular application runs inside its own container.

```text
Angular → Frontend Container
```

### Backend

The Spring Boot REST API runs inside its own Java container.

```text
Spring Boot → Backend Container
```

### Database

PostgreSQL runs inside its own database container.

```text
PostgreSQL → Database Container
```

The services communicate using Docker Compose service names rather than relying on `localhost` between containers.

---

# Dockerfiles

The project contains separate Dockerfiles for the frontend and backend.

### Backend Dockerfile

The backend Dockerfile:

* Uses Java 21
* Creates the application working directory
* Copies the compiled Spring Boot JAR
* Exposes port 8080
* Starts the Spring Boot application

### Frontend Dockerfile

The frontend Dockerfile:

* Uses Node.js
* Copies the Angular project
* Installs dependencies
* Builds/runs the Angular application
* Exposes the frontend port

Separating the Dockerfiles allows the frontend and backend to be developed, built, and deployed independently.

---

# Testing

The backend REST API was tested independently using Postman before integrating the frontend.

Testing included:

* Creating appointments
* Retrieving appointments
* Deleting appointments
* Validation
* Double-booking prevention

After the backend was verified, it was connected to the Angular frontend.

---

# pgAdmin

pgAdmin can optionally be used to inspect the PostgreSQL database running inside Docker.

Typical connection settings:

```text
Host: localhost
Port: 5432
Database: appointments_db
Username: postgres
Password: postgres
```

This can be used to verify that appointments created through the application are actually being stored in PostgreSQL.

---

# Why Docker Compose?

Without Docker Compose, each part of the application would need to be configured and started separately.

With Docker Compose:

```text
Frontend
Backend
Database
   ↓
Docker Compose
   ↓
One Application Stack
```

The entire system can be started using:

```powershell
docker compose up --build
```

This makes the application easier to reproduce on another computer and demonstrates a multi-container architecture similar to real-world applications.

---

# Learning Objectives

This project expands on the Docker concepts covered in class by moving from a single packaged application to a multi-container architecture.

The project demonstrates:

* Dockerfiles
* Docker images
* Docker containers
* Docker Compose
* Container networking
* Service-to-service communication
* Environment variables
* PostgreSQL containers
* Named volumes
* Persistent data
* Database initialization
* Multi-container application architecture

The project also builds on the Spring Boot REST API and Angular concepts covered throughout the course.

---

## Author

**Leon Lawrence**

Software Development & Network Engineering
Sheridan College
