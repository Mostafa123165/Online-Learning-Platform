
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE user;

CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT ,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    address VARCHAR(255),
    registration_date TIMESTAMP  
)AUTO_INCREMENT = 1;

CREATE TABLE student (
    id BIGINT PRIMARY KEY ,
    CONSTRAINT `FK_STUDENT` FOREIGN KEY (`id`) REFERENCES user (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);
DROP TABLE admin;

CREATE TABLE admin (
    id BIGINT PRIMARY KEY,
    CONSTRAINT `FK_ADMIN` FOREIGN KEY (`id`) REFERENCES user (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

DROP TABLE instructor;

CREATE TABLE instructor (
    id BIGINT PRIMARY KEY,
    CONSTRAINT `FK_INSTRUCTOR` FOREIGN KEY (`id`) REFERENCES user (`id`)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

DROP TABLE role;
	
CREATE TABLE role (
	id TINYINT PRIMARY KEY AUTO_INCREMENT,
    name ENUM('INSTRUCTOR','STUDENT','ADMIN') NOT NULL
) AUTO_INCREMENT = 1;

DROP TABLE student_role;

CREATE TABLE student_role (
	user_id BIGINT ,
	role_id TINYINT,
	PRIMARY KEY(user_id,role_id),

	CONSTRAINT `FK_STUDENT_ROLE_USER_ID` FOREIGN KEY (`user_id`) REFERENCES user (`id`) ,
    CONSTRAINT `FK_STUDENT_ROLE_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES role (`id`)
);

DROP TABLE student_course;

CREATE TABLE student_course(
	student_id BIGINT ,
	course_id BIGINT,
    finished Boolean DEFAULT FALSE,
	PRIMARY KEY(student_id,course_id),

	CONSTRAINT `FK_STUDENT_COURSE_USER_ID`   FOREIGN KEY (`student_id`)  REFERENCES student (`id`) ,
    CONSTRAINT `FK_STUDENT_COURSE_COURSE_ID` FOREIGN KEY (`course_id`)    REFERENCES course (`id`)
);


DROP TABLE course;

CREATE TABLE course (
	id BIGINT PRIMARY KEY AUTO_INCREMENT ,
    title VARCHAR(255) NOT NULL,
    description TEXT ,
    status ENUM ('Pending_Approval','Rejection','Approved'),
    type ENUM ('FREE','PAID'),
    category_id TINYINT,
    instructor_id BIGINT,
    UNIQUE(title),
    CONSTRAINT `FK_COURSE_CATEGORY_ID` FOREIGN KEY (`category_id`) REFERENCES category (`id`) , 
    CONSTRAINT `FK_COURSE_INSTRUCTOR_ID` FOREIGN KEY (`instructor_id`) REFERENCES instructor (`id`)
)AUTO_INCREMENT = 1;

DROP TABLE category;

CREATE TABLE category(
	id TINYINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    UNIQUE(name)
)AUTO_INCREMENT = 1;

DROP TABLE tag;

CREATE TABLE tag(
	id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
)AUTO_INCREMENT = 1;

DROP TABLE course_tag;

create TABLE course_tag(
	course_id BIGINT ,
    tag_id INT ,
    PRIMARY KEY (course_id,tag_id),
    
	CONSTRAINT `FK_COURSE_TAGE_COURSE_ID` FOREIGN KEY (course_id)   REFERENCES course (id),
    CONSTRAINT `FK_COURSE_TAGE_TAG_ID`    FOREIGN KEY (tag_id) 		REFERENCES tag (id)
);

drop table review ;

CREATE TABLE review(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content TEXT,
	created_at DATETIME DEFAULT NOW(),
    user_id BIGINT,
	CONSTRAINT `FK_REVIEW_USER_ID` FOREIGN KEY (user_id)   REFERENCES user (id)
) AUTO_INCREMENT = 1;

drop table rating_system ;

CREATE TABLE rating_system(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    content TEXT,
    stars TINYINT,
    user_id BIGINT,
    CONSTRAINT `UNIQUE_USER_ID` UNIQUE(user_id),
	CONSTRAINT `FK_RATING_SYSTEM_USER_ID` FOREIGN KEY (user_id)   REFERENCES user (id)
) AUTO_INCREMENT = 1;

SET FOREIGN_KEY_CHECKS = 1;


-- insert roles
INSERT INTO role (name) VALUES
('INSTRUCTOR'),
('STUDENT'),
('ADMIN');

DROP trigger prevent_role_insert

-- create triggers from prevent delete,update,insert 
DELIMITER //

CREATE TRIGGER prevent_role_insert
BEFORE INSERT ON role
FOR EACH ROW
BEGIN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Inserted are not allowed on the role table';
END//

DELIMITER ;


DELIMITER //

CREATE TRIGGER prevent_role_update
BEFORE UPDATE ON role
FOR EACH ROW
BEGIN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Updated are not allowed on the role table';
END//

DELIMITER ;

DELIMITER //

CREATE TRIGGER prevent_role_delete
BEFORE DELETE ON role
FOR EACH ROW
BEGIN
    
    SET MESSAGE_TEXT = 'Deleted are not allowed on the role table';
END//

DELIMITER ;

