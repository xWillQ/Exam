CREATE TABLE departments(
    id INT PRIMARY KEY,
    title VARCHAR(255),
    number VARCHAR(20),
    version INT DEFAULT 1
);

CREATE TABLE employees(
    id INT PRIMARY KEY,
    name VARCHAR(255),
    departmentId INT REFERENCES departments (id),
    version INT DEFAULT 1
);

CREATE TABLE numbers(
    id INT PRIMARY KEY,
    employeeId INT REFERENCES employees (id),
    type VARCHAR(255),
    number VARCHAR(20),
    version INT DEFAULT 1
);
