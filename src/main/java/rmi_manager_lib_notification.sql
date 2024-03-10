CREATE DATABASE  IF NOT EXISTS `rmi_manager_lib` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `rmi_manager_lib`;
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
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS notification;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE notification (
  id int NOT NULL AUTO_INCREMENT,
  sent_at timestamp NOT NULL,
  message text NOT NULL,
  patron_id int NOT NULL,
  PRIMARY KEY (id),
  KEY FK_notification_patron_idx (patron_id),
  CONSTRAINT FK_notification_patron FOREIGN KEY (patron_id) REFERENCES patron_account (id)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

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

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
