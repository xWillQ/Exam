INSERT INTO departments(id, title, number)
VALUES
    (1, 'sales', '123'),
    (2, 'it', '345'),
    (3, 'hr', '876'),
    (4, 't', '3456');
INSERT INTO employees(id, name, departmentId)
VALUES
    (1, 'vasya', 1),
    (2, 'petya', 1),
    (3, 'masha', 3);
INSERT INTO numbers(id, type, number, employeeId)
VALUES
    (1, 'INTERNAL_CELL', '8-800-111-3456', 1),
    (2, 'EXTERNAL_CELL', '8-954-245-6543', 1),
    (3, 'HOME', '2345667', 2),
    (4, 'INTERNAL_CELL', '8-254-386-367', 2);
