use assist;

DROP TABLE IF EXISTS storage_item;
DROP TABLE IF EXISTS storage_centre;
DROP TABLE IF EXISTS actioned_by;
DROP TABLE IF EXISTS actions;
DROP TABLE IF EXISTS user_organisation_mapping;
DROP TABLE IF EXISTS organisation;
DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS authority;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS files;
DROP TABLE IF EXISTS locations;

CREATE TABLE `files` (
  `file_id` INT NOT NULL AUTO_INCREMENT,
  `file_name` VARCHAR(256) NOT NULL,
  `file_location` VARCHAR(512) NOT NULL,
  PRIMARY KEY (`file_id`));

CREATE TABLE `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(256) NOT NULL,
  `mobile` VARCHAR(15) NULL,
  `email` VARCHAR(246) NULL,
  `passwd` VARCHAR(256) NOT NULL,
  PRIMARY KEY (`user_id`));

CREATE TABLE `authority` (
  `authority_id` INT NOT NULL AUTO_INCREMENT,
  `authority_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`authority_id`));

CREATE TABLE `user_authority` (
  `user_id` INT NOT NULL,
  `authority_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `authority_id`),
  INDEX `fk_user_authority_1_idx` (`authority_id` ASC),
  CONSTRAINT `fk_user_authority_1`
  FOREIGN KEY (`authority_id`)
  REFERENCES `authority` (`authority_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_authority_2`
  FOREIGN KEY (`user_id`)
  REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `locations` (
  `location_id` INT NOT NULL AUTO_INCREMENT,
  `location_name` VARCHAR(256) NOT NULL,
  `latitude` FLOAT NULL,
  `longitude` FLOAT NULL,
  PRIMARY KEY (`location_id`));

CREATE TABLE `organisation` (
  `organisation_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `url` VARCHAR(45) NULL,
  `location_id` INT NULL,
  `created_by` INT NOT NULL,
  `created_on` DATETIME NOT NULL DEFAULT NOW(),
  `description` TEXT NULL,
  PRIMARY KEY (`organisation_id`),
  CONSTRAINT `fk_organisation_1`
  FOREIGN KEY (`location_id`)
  REFERENCES `locations` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_organisation_2`
  FOREIGN KEY (`created_by`)
  REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `user_organisation_mapping` (
  `organisation_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `isAdmin` TINYINT NOT NULL DEFAULT 0,
  `addedOn` DATETIME NOT NULL DEFAULT NOW(),
  `added_by` INT NOT NULL,
  PRIMARY KEY (`organisation_id`, `user_id`),
  INDEX `fk_user_organisation_mapping_2_idx` (`user_id` ASC),
  INDEX `fk_user_organisation_mapping_3_idx` (`added_by` ASC),
  CONSTRAINT `fk_user_organisation_mapping_1`
  FOREIGN KEY (`organisation_id`)
  REFERENCES `organisation` (`organisation_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_organisation_mapping_2`
  FOREIGN KEY (`user_id`)
  REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_organisation_mapping_3`
  FOREIGN KEY (`added_by`)
  REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `actions` (
  `action_id` INT NOT NULL AUTO_INCREMENT,
  `action_type` VARCHAR(100) NOT NULL,
  `description` TEXT NULL,
  `item_name` VARCHAR(256) NULL,
  `item_quantity` INT NULL,
  `posted_by_user` INT NULL,
  `posted_by_organisation` INT NULL,
  `fulfilled` TINYINT NOT NULL DEFAULT 0,
  `location_id` INT NULL,
  `latitude` FLOAT NULL,
  `longitude` FLOAT NULL,
  `file_id` INT NULL,
  `created_on` DATETIME NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`action_id`),
  INDEX `fk_actions_1_idx` (`file_id` ASC),
  INDEX `fk_actions_2_idx` (`location_id` ASC),
  INDEX `fk_actions_3_idx` (`posted_by_user` ASC),
  INDEX `fk_actions_4_idx` (`posted_by_organisation` ASC),
  CONSTRAINT `fk_actions_1`
  FOREIGN KEY (`file_id`)
  REFERENCES `files` (`file_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_actions_2`
  FOREIGN KEY (`location_id`)
  REFERENCES `locations` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_actions_3`
  FOREIGN KEY (`posted_by_user`)
  REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_actions_4`
  FOREIGN KEY (`posted_by_organisation`)
  REFERENCES `organisation` (`organisation_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `actioned_by` (
  `action_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `actioned_on` DATETIME NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`action_id`, `user_id`));

CREATE TABLE `storage_centre` (
  `centre_id` INT NOT NULL AUTO_INCREMENT,
  `centre_name` VARCHAR(512) NOT NULL,
  `location_id` INT NOT NULL,
  `latitude` FLOAT NULL,
  `longitude` FLOAT NULL,
  `created_by` INT NOT NULL,
  `created_on` DATETIME NULL DEFAULT NOW(),
  PRIMARY KEY (`centre_id`),
  INDEX `fk_storage_centre_1_idx` (`location_id` ASC),
  INDEX `fk_storage_centre_2_idx` (`created_by` ASC),
  CONSTRAINT `fk_storage_centre_1`
  FOREIGN KEY (`location_id`)
  REFERENCES `locations` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_storage_centre_2`
  FOREIGN KEY (`created_by`)
  REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

CREATE TABLE `storage_item` (
  `storage_id` INT NOT NULL AUTO_INCREMENT,
  `centre_id` INT NOT NULL,
  `item_name` VARCHAR(512) NOT NULL,
  `item_quantity` INT NOT NULL,
  PRIMARY KEY (`storage_id`),
  INDEX `fk_storage_1_idx` (`centre_id` ASC),
  CONSTRAINT `fk_storage_1`
  FOREIGN KEY (`centre_id`)
  REFERENCES `storage_centre` (`centre_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

