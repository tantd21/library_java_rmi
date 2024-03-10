-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: rmi_manager_lib
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `author`
--

INSERT INTO author VALUES (1,'Author 1');
INSERT INTO author VALUES (2,'Louisa May Alcott');
INSERT INTO author VALUES (3,'PGS.TS Huỳnh Công Pháp');
INSERT INTO author VALUES (4,'Og Mandino');
INSERT INTO author VALUES (5,'Ernest Hemingway');
INSERT INTO author VALUES (6,'Tony Hsieh');
INSERT INTO author VALUES (7,'Author 7');
INSERT INTO author VALUES (8,'Author 8');
INSERT INTO author VALUES (9,'Author 9');
INSERT INTO author VALUES (10,'Author 10');
INSERT INTO author VALUES (11,'Author 10');
INSERT INTO author VALUES (12,'Dale Carnegie');
INSERT INTO author VALUES (13,'Paulo Coelho');
INSERT INTO author VALUES (14,'Andrew Matthews');
INSERT INTO author VALUES (15,'Edmondo De Amicis');
INSERT INTO author VALUES (16,'Mario Puzo');
INSERT INTO author VALUES (17,'Charlotte Bronte');
INSERT INTO author VALUES (18,'David J.Lieberman');
INSERT INTO author VALUES (19,'Mari Tamagawa');
INSERT INTO author VALUES (20,'Stephen R. Covey');

--
-- Dumping data for table `book`
--

INSERT INTO book VALUES (1,'10 vạn câu hỏi vì sao',1);
INSERT INTO book VALUES (2,'Toán Cao cấp',2);
INSERT INTO book VALUES (3,'Pháp luật đại cương',1);
INSERT INTO book VALUES (4,'Lập trình hướng đối tượng OOP',3);
INSERT INTO book VALUES (5,'Book 5',2);
INSERT INTO book VALUES (6,'Book 6',1);
INSERT INTO book VALUES (7,'Hồ sơ mật',4);
INSERT INTO book VALUES (8,'Người tình của tôi',6);
INSERT INTO book VALUES (9,'Lập trình không khó',2);
INSERT INTO book VALUES (10,'Book 10',4);
INSERT INTO book VALUES (18,'Cú sốc tuổi 20',8);
INSERT INTO book VALUES (21,'Người yêu lí tưởng của tôi',5);
INSERT INTO book VALUES (22,'Đồi thông hai mộ',3);
INSERT INTO book VALUES (23,'Lập trình hướng lung tung',1);
INSERT INTO book VALUES (24,'Nhập môn CsSGO',6);

--
-- Dumping data for table `book_author`
--

INSERT INTO book_author VALUES (2,2);
INSERT INTO book_author VALUES (9,2);
INSERT INTO book_author VALUES (23,2);
INSERT INTO book_author VALUES (3,3);
INSERT INTO book_author VALUES (4,3);
INSERT INTO book_author VALUES (22,4);
INSERT INTO book_author VALUES (24,5);
INSERT INTO book_author VALUES (7,18);
INSERT INTO book_author VALUES (8,19);
INSERT INTO book_author VALUES (18,19);
INSERT INTO book_author VALUES (1,20);
INSERT INTO book_author VALUES (21,20);

--
-- Dumping data for table `book_copy`
--

INSERT INTO book_copy VALUES (1,2000,1,1);
INSERT INTO book_copy VALUES (2,2001,2,2);
INSERT INTO book_copy VALUES (3,2002,3,1);
INSERT INTO book_copy VALUES (4,2003,4,3);
INSERT INTO book_copy VALUES (5,2004,5,2);
INSERT INTO book_copy VALUES (6,2005,6,1);
INSERT INTO book_copy VALUES (7,2006,7,4);
INSERT INTO book_copy VALUES (8,2007,8,3);
INSERT INTO book_copy VALUES (9,2008,9,2);
INSERT INTO book_copy VALUES (10,2009,10,4);

--
-- Dumping data for table `category`
--

INSERT INTO category VALUES (1,'Sách tham khảo');
INSERT INTO category VALUES (2,'Sách giáo khoa');
INSERT INTO category VALUES (3,'Hành động');
INSERT INTO category VALUES (4,'Trinh thám');
INSERT INTO category VALUES (5,'Tình cảm');
INSERT INTO category VALUES (6,'18+');
INSERT INTO category VALUES (7,'Đời sống');
INSERT INTO category VALUES (8,'Tâm lý');
INSERT INTO category VALUES (9,'Hình sự');
INSERT INTO category VALUES (10,'Trẻ em');
INSERT INTO category VALUES (11,'Truyện tranh');
INSERT INTO category VALUES (12,'Truyện chữ');

--
-- Dumping data for table `checkout`
--

INSERT INTO checkout VALUES (1,'2023-10-15 14:00:00','2023-10-16 14:00:00',1,1,1);
INSERT INTO checkout VALUES (2,'2023-10-15 14:30:00','2023-10-16 14:30:00',1,2,2);
INSERT INTO checkout VALUES (3,'2023-10-15 15:00:00','2023-10-16 15:00:00',1,3,3);
INSERT INTO checkout VALUES (4,'2023-10-15 15:30:00','2023-10-16 15:30:00',1,4,4);
INSERT INTO checkout VALUES (5,'2023-10-15 16:00:00','2023-10-16 16:00:00',0,5,5);
INSERT INTO checkout VALUES (6,'2023-10-15 16:30:00','2023-10-16 16:30:00',0,6,6);
INSERT INTO checkout VALUES (7,'2023-10-15 17:00:00','2023-10-16 17:00:00',1,7,7);
INSERT INTO checkout VALUES (8,'2023-10-15 17:30:00','2023-10-16 17:30:00',1,8,8);
INSERT INTO checkout VALUES (9,'2023-10-15 18:00:00','2023-10-16 18:00:00',0,9,9);
INSERT INTO checkout VALUES (10,'2023-10-15 18:30:00','2023-10-16 18:30:00',0,10,10);

--
-- Dumping data for table `db_log`
--


--
-- Dumping data for table `hold`
--

INSERT INTO hold VALUES (16,'2023-10-15 14:00:00','2023-10-16 14:00:00',1,1);
INSERT INTO hold VALUES (17,'2023-10-15 14:30:00','2023-10-16 14:30:00',2,2);
INSERT INTO hold VALUES (18,'2023-10-15 15:00:00','2023-10-16 15:00:00',3,3);
INSERT INTO hold VALUES (19,'2023-10-15 15:30:00','2023-10-16 15:30:00',4,4);
INSERT INTO hold VALUES (20,'2023-10-15 16:00:00','2023-10-16 16:00:00',5,5);
INSERT INTO hold VALUES (21,'2023-10-15 16:30:00','2023-10-16 16:30:00',6,6);
INSERT INTO hold VALUES (22,'2023-10-15 17:00:00','2023-10-16 17:00:00',7,7);
INSERT INTO hold VALUES (23,'2023-10-15 17:30:00','2023-10-16 17:30:00',8,8);
INSERT INTO hold VALUES (24,'2023-10-15 18:00:00','2023-10-16 18:00:00',9,9);
INSERT INTO hold VALUES (25,'2023-10-15 18:30:00','2023-10-16 18:30:00',10,10);

--
-- Dumping data for table `log`
--


--
-- Dumping data for table `notification`
--

INSERT INTO notification VALUES (1,'2023-10-15 14:00:00','Notification 1',1);
INSERT INTO notification VALUES (2,'2023-10-15 14:30:00','Notification 2',2);
INSERT INTO notification VALUES (3,'2023-10-15 15:00:00','Notification 3',3);
INSERT INTO notification VALUES (4,'2023-10-15 15:30:00','Notification 4',4);
INSERT INTO notification VALUES (5,'2023-10-15 16:00:00','Notification 5',5);
INSERT INTO notification VALUES (6,'2023-10-15 16:30:00','Notification 6',6);
INSERT INTO notification VALUES (7,'2023-10-15 17:00:00','Notification 7',7);
INSERT INTO notification VALUES (8,'2023-10-15 17:30:00','Notification 8',8);
INSERT INTO notification VALUES (9,'2023-10-15 18:00:00','Notification 9',9);
INSERT INTO notification VALUES (10,'2023-10-15 18:30:00','Notification 10',10);

--
-- Dumping data for table `patron_account`
--

INSERT INTO patron_account VALUES (1,'First 1','Last 1','1@gmail.com','1',1);
INSERT INTO patron_account VALUES (2,'First 2','Last 2','2@gmail.com','1',1);
INSERT INTO patron_account VALUES (3,'First 3','Last 3','3@gmail.com','1',1);
INSERT INTO patron_account VALUES (4,'First 4','Last 4','4@gmail.com','1',1);
INSERT INTO patron_account VALUES (5,'First 5','Last 5','5@gmail.com','1',1);
INSERT INTO patron_account VALUES (6,'First 6','Last 6','6@gmail.com','1',1);
INSERT INTO patron_account VALUES (7,'First 7','Last 7','7@gmail.com','1',1);
INSERT INTO patron_account VALUES (8,'First 8','Last 8','8@gmail.com','1',1);
INSERT INTO patron_account VALUES (9,'First 9','Last 9','9@gmail.com','1',1);
INSERT INTO patron_account VALUES (10,'First 10','Last 10','10@gmail.com','1',1);

--
-- Dumping data for table `published`
--

INSERT INTO published VALUES (1,'Publisher 1');
INSERT INTO published VALUES (2,'Publisher 2');
INSERT INTO published VALUES (3,'Publisher 3');
INSERT INTO published VALUES (4,'Publisher 4');
INSERT INTO published VALUES (5,'Publisher 5');
INSERT INTO published VALUES (6,'Publisher 6');
INSERT INTO published VALUES (7,'Publisher 7');
INSERT INTO published VALUES (8,'Publisher 8');
INSERT INTO published VALUES (9,'Publisher 9');
INSERT INTO published VALUES (10,'Publisher 10');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
