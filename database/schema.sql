CREATE DATABASE IF NOT EXISTS student_registration;
USE student_registration;

CREATE TABLE IF NOT EXISTS new_student (
    student_id VARCHAR(20) PRIMARY KEY,
    name VARCHAR(120) NOT NULL,
    major VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL,
    phone VARCHAR(20) NOT NULL
);
