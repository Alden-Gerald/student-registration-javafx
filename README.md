# Student Registration — JavaFX + MySQL

A desktop application for creating and updating student registration records through a JavaFX interface backed by MySQL.

## Features

- Create, read, update, clear, and refresh student records
- Validate student ID, name, major, email, and phone inputs
- Display records in a JavaFX `TableView`
- Provide success, warning, error, and confirmation feedback
- Separate interface behavior from database access with a DAO layer
- Use responsive JavaFX layout managers and event-driven interactions

## Technology

- Java 17
- JavaFX 21
- MySQL / MariaDB
- JDBC and MySQL Connector/J

## Structure

```text
src/application/
├── MainApp.java
├── Student.java
├── StudentDAO.java
└── Database.java
```

## Run locally

1. Create a MySQL database and import `database/schema.sql`.
2. Add JavaFX 21 and MySQL Connector/J to your Java project.
3. Optionally set `STUDENT_DB_URL`, `STUDENT_DB_USER`, and `STUDENT_DB_PASSWORD`.
4. Run `application.MainApp`.

No real student records or credentials are included in this public portfolio copy.

## Learning outcomes

This project strengthened my understanding of object-oriented design, event-driven programming, GUI validation, relational persistence, and the DAO pattern.

## Project status

Individual academic prototype presented for portfolio purposes.
