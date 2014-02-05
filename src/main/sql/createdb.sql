CREATE DATABASE springjdbc;
USE springjdbc;
CREATE TABLE `users` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL DEFAULT 'ROLE_USER',
  `authorities_id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`authorities_id`),
  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

INSERT INTO users VALUES('bob@isp.com','password1',1);
INSERT INTO users VALUES('sally@isp.com','password2',1);
INSERT INTO users VALUES('tom@isp.com','password3',1);
INSERT INTO authorities VALUES('bob@isp.com','ROLE_ADMIN',1);
INSERT INTO authorities VALUES('sally@isp.com','ROLE_USER',2);
INSERT INTO authorities VALUES('tom@isp.com','ROLE_USER',3);
