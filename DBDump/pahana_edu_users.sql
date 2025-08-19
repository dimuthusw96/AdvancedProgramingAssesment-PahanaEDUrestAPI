CREATE DATABASE  IF NOT EXISTS `pahana_edu` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `pahana_edu`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: pahana_edu
-- ------------------------------------------------------
-- Server version	5.7.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `password` varchar(450) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=143 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (55,'dsw','123'),(56,'dsw','123'),(62,'dsw','123'),(65,'dsw','123'),(66,'dsw','3'),(67,'dsw',''),(68,'dsw',''),(69,'dsw',''),(70,'dsw',''),(71,'dsw',''),(72,'dsw','1'),(73,'dsw','1'),(74,'dsw','1'),(75,'dsw',''),(76,'dsw',''),(77,'dsw1','112'),(83,'qw','qw'),(84,'qw','qw'),(85,'qw','qw'),(86,'qw','qw'),(96,'ss','edd5d29fcc9a9457870f57914825ac1ed630b72923d77419757f99a2587c72f9'),(97,'cv','fe9d14886326e98aadc24505cbb61ea977d33804e13bb955d6703c3d605d6ca8'),(98,'cvv','aa989fcd425615b12e9753d2e3d900f1bf8fc576fc8528df33369b40dd22be01'),(100,'xz','e71919d210e997bfa0bef8109af0ac6d7fc06eee5e8a0337e522e7823a7fef80'),(101,'as','3bdeeb369ac3756516c6c362f50e2227310d5af3cf6d505b98030f4b82445b2d'),(102,'we',NULL),(103,'sd',NULL),(104,'po','66befeedf2d742a28ff44606f05c583271ebb743d18fdd34c0efd831f7f8b9ec'),(105,'Dimuthu','15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-20  0:57:04
