CREATE DATABASE FilesCopy;
USE FilesCopy;

CREATE TABLE LogOfCopy (
 FIleId INT NOT NULL AUTO_INCREMENT,
 FileName VARCHAR(255) NOT NULL,
 DateOfCopy DATETIME NOT NULL,
 PRIMARY KEY (FileId));

CREATE USER 'sqlUser'@'localhost' IDENTIFIED BY 'sqluser123';
GRANT All ON FilesCopy.LogoOfCopy TO 'sqlUser'@'localhost';