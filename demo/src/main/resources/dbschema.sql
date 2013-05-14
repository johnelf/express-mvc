drop table if exists `authors`;
CREATE TABLE `authors` (`id` int(11) unsigned NOT NULL AUTO_INCREMENT, `name` VARCHAR(10) NOT NULL, `age` INT, `email` VARCHAR(320), PRIMARY KEY (`id`)) ENGINE=MyISAM DEFAULT CHARSET=utf8;

drop table if exists `articles`;
CREATE TABLE `articles` (`id` int (11) unsigned NOT NULL AUTO_INCREMENT, author_id INT NOT NULL, `title` TEXT, PRIMARY KEY (`id`))