CREATE DATABASE FilesCopy;
CREATE USER 'sqlUser'@'localhost' IDENTIFIED BY 'sqluser123';
GRANT All ON FilesCopy.* TO 'sqlUser'@'localhost';