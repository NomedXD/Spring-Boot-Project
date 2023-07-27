DROP SCHEMA IF EXISTS tmsdb;
CREATE SCHEMA IF NOT EXISTS tmsdb;
USE tmsdb;

DROP TABLE IF EXISTS 02062023_task1;
CREATE TABLE 02062023_task1(info1 VARCHAR(700) PRIMARY KEY NOT NULL, imgpath VARCHAR(700), maininfo TEXT);
INSERT INTO 02062023_task1(info1, imgpath, maininfo) VALUES ('smallTestInfo', 'images/me.png', 'BigTesstInfo');

DROP TABLE IF EXISTS categories;
CREATE TABLE categories
(id        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,name VARCHAR(45),
 imagepath VARCHAR(45),sometext VARCHAR(45));
INSERT INTO categories(name, imagepath, sometext)
VALUES
    ('Strategies','images/strategies.jpg','High rated strategies'),
    ('Shooters','images/shooters.jpeg','Best online shooters');

DROP TABLE IF EXISTS products;
CREATE TABLE products
(id        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,name VARCHAR(45),
 imagepath VARCHAR(45),description TEXT,categoryid INT, price VARCHAR(45));
INSERT INTO products(name, imagepath, description, categoryid, price)
VALUES
    ('Heroes of Might and Magic 3','images/Heroes of Might and Magic 3.jpeg','A turn-based strategy game developed by Jon Van Caneghem through New World Computing originally released for Microsoft Windows by The 3DO Company in 1999. Its ports to several computer and console systems followed in 1999–2000. It is the third installment of the Heroes of Might and Magic series.',1, '5$'),
    ('Call od Duty Modern Warfare 2','images/Call of Duty Modern Warfare 2.jpg','Call of Duty: Modern Warfare II is a first-person shooter video game developed by Infinity Ward for PlayStation 4, PlayStation 5, Xbox One, Xbox Series X/S, and Microsoft Windows. It is the nineteenth game in the Call of Duty franchise, and the sequel to Call of Duty: Modern Warfare. Its logo was revealed on April 28th, 2022 the game was released on October 28th, 2022.',2, '19$');

DROP TABLE IF EXISTS users;
CREATE TABLE users(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, mail VARCHAR(45),
                                       password VARCHAR(45), name VARCHAR(45), surname VARCHAR(45), balance VARCHAR(45));
INSERT INTO users(mail, password, name, surname, balance)
VALUES
    ('user1@gmail.com','111','Bob', 'Jenkins', '777$'),
    ('user2@gmail.com','222','Alice', 'Ahtung', '666$');