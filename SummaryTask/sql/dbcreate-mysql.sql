
SET NAMES utf8;

DROP DATABASE IF EXISTS final_base ;
CREATE DATABASE final_base CHARACTER SET utf8 COLLATE utf8_bin;

USE final_base;
-- --------------------------------------------------------------
-- ROLES
-- users roles
-- --------------------------------------------------------------
CREATE TABLE roles(

	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);


INSERT INTO roles VALUES(0, 'admin');
INSERT INTO roles VALUES(1, 'teacher');
INSERT INTO roles VALUES(2, 'student');


CREATE TABLE users(

	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	login VARCHAR(20) NOT NULL UNIQUE,	
	password VARCHAR(20) NOT NULL,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL,
    active BOOLEAN,
	role_id INTEGER NOT NULL REFERENCES roles(id) 
		ON DELETE CASCADE 
		ON UPDATE RESTRICT
);


INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Ivan', 'Ivanov','0501112236','admin@gmail.com',true, 0);
 
INSERT INTO users VALUES(DEFAULT, 'teacher1', 'teacher1', 'Петр', 'Сидоров','0661234152','sidorov20@gmail.com',true,  1);

INSERT INTO users VALUES(DEFAULT, 'student1', 'student1', 'Иван', 'Петров', '0978529632','petrov_10@gmail.com', true,2);
INSERT INTO users VALUES(DEFAULT, 'student2', 'student2', 'Петр', 'Смирнов','0974125632','petr1@gmail.com', true, 2);

CREATE TABLE categories(
	id INTEGER NOT NULL PRIMARY KEY,
	name VARCHAR(10) NOT NULL UNIQUE
);

INSERT INTO categories VALUES(1, 'IT'); 
INSERT INTO categories VALUES(2, 'Languages'); 



CREATE TABLE courses(
	id INTEGER NOT NULL auto_increment PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	start_date DATE,
    end_date DATE,
    length INTEGER,
    category_id INTEGER NOT NULL REFERENCES categories(id),
    teacher_id INTEGER NOT NULL REFERENCES users(id),
    description VARCHAR(500)
); 

INSERT INTO courses VALUES(DEFAULT,'Java SE','2020-03-30', '2020-05-30', 24, 1, 2, 'Java is the most popular programming language in the world, existing for over 20 years and actively used in a wide variety of fields. This language is clearly not one of those that have been fashionable for five years, and then are forgotten and ceased to be in demand. This is a rigorous object-oriented language, whose experts have always been and will be needed.' ); 
INSERT INTO courses VALUES(DEFAULT,'English','2020-04-05', '2020-05-05', 16, 2, 2, 'The course consists of nine lessons - each devoted to a specific travel situation. The lesson plan includes not only the necessary words and phrases. but also real dialogues with native speakers, explanation of grammar rules, training exercises. simulator for memorizing irregular verbs. Everything. what the teacher says. the student sees on the screen, can go back, repeat as many times as necessary. The course ends with two test options for self-testing.' ); 


CREATE TABLE journal(
    rating INTEGER,
	user_id INTEGER NOT NULL REFERENCES users(id),
    course_id INTEGER NOT NULL REFERENCES courses(id)
);

INSERT INTO journal VALUES(0, 3, 2);

SELECT * FROM categories;
SELECT * FROM journal;
SELECT * FROM courses;
SELECT * FROM users;
SELECT * FROM roles;
