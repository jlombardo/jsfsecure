CREATE DATABASE jdbcrealm;
USE jdbcrealm;
CREATE TABLE `jdbcrealm`.`users` (
`username` varchar(255) NOT NULL,
`password` varchar(255) DEFAULT NULL,
PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `jdbcrealm`.`groups` (
`username` varchar(255) DEFAULT NULL,
`groupname` varchar(255) DEFAULT NULL)
ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE INDEX groups_users_FK1 ON groups(username ASC);

INSERT INTO users VALUES('bob','password1');
INSERT INTO users VALUES('sally','password2');
INSERT INTO users VALUES('tom','password3');
INSERT INTO groups VALUES('bob','admin');
INSERT INTO groups VALUES('sally','user');
INSERT INTO groups VALUES('tom','user');