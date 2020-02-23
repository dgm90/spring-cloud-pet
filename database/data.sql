DELETE from overtime;
DELETE from project;
DELETE from employee;

INSERT INTO employee ("first_name", "last_name") VALUES
	('Ivan', 'Ivanov'),
	('Sidor', 'Sidorov'),
	('Petr', 'Petrov');

INSERT INTO project ("name") VALUES 
	('Apple Project'),
	('Google Project'),
	('Amazon');

INSERT INTO overtime ("employee_id", "project_id", "start_date", "hours") VALUES
	('1', '2', '2019-09-19', '6'),
	('1', '3', '2019-09-20', '2.5'),
	('3', '1', '2019-09-19', '1'),
	('2', '1', '2019-09-21', '122');