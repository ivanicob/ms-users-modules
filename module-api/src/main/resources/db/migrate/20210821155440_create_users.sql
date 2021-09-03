CREATE DATABASE `modules` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

CREATE TABLE `modules`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `login` VARCHAR(60) NOT NULL,
  `password` TEXT NOT NULL,
  `role` ENUM('ROLE_ADMIN', 'ROLE_USER') NOT NULL,
  `createdDate` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedDate` DATETIME NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`));