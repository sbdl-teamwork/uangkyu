-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 23 Bulan Mei 2021 pada 17.03
-- Versi server: 10.4.11-MariaDB
-- Versi PHP: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_uangkyu`
--
CREATE DATABASE IF NOT EXISTS `db_uangkyu` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `db_uangkyu`;

DELIMITER $$
--
-- Prosedur
--
DROP PROCEDURE IF EXISTS `delete_activity`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_activity` (IN `id_param` INT, IN `user_id_param` INT)  BEGIN
	DELETE FROM activities WHERE id = id_param AND user_id = user_id_param;
END$$

DROP PROCEDURE IF EXISTS `insert_expense_activity`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_expense_activity` (IN `description` VARCHAR(250), IN `nominal` FLOAT, IN `user_id` INT, IN `date_at` DATETIME)  BEGIN
	INSERT INTO activities 
    	(description, nominal, user_id, date_at, type) 
    VALUES 
    	(description, nominal, user_id, date_at,'PLRN');
END$$

DROP PROCEDURE IF EXISTS `insert_income_activity`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insert_income_activity` (IN `description` VARCHAR(250), IN `nominal` FLOAT, IN `user_id` INT, IN `date_at` DATETIME)  BEGIN
	INSERT INTO activities 
    	(description, nominal, user_id, date_at, type) 
    VALUES 
    	(description, nominal, user_id, date_at,'PMSK');
END$$

DROP PROCEDURE IF EXISTS `register_user`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `register_user` (`name` VARCHAR(250), `email` VARCHAR(250), `password` VARCHAR(250))  BEGIN
    INSERT INTO users (name, email, password) VALUES (name, email, password);
END$$

DROP PROCEDURE IF EXISTS `update_expense_activity`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_expense_activity` (IN `id_param` INT, IN `description_param` VARCHAR(250), IN `nominal_param` FLOAT, IN `user_id_param` INT, IN `date_at_param` DATETIME)  BEGIN
	UPDATE 
    	activities 
    SET 
    	description = description_param, 
        nominal = nominal_param, 
        date_at = date_at_param,
        type = 'PLRN'
    WHERE 
    	id = id_param AND user_id = user_id_param;
END$$

DROP PROCEDURE IF EXISTS `update_income_activity`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `update_income_activity` (IN `id_param` INT, IN `description_param` VARCHAR(250), IN `nominal_param` FLOAT, IN `user_id_param` INT, IN `date_at_param` DATETIME)  BEGIN
	UPDATE 
    	activities 
    SET 
    	description = description_param, 
        nominal = nominal_param, 
        date_at = date_at_param,
        type = 'PMSK'
    WHERE 
    	id = id_param AND user_id = user_id_param;
END$$

--
-- Fungsi
--
DROP FUNCTION IF EXISTS `get_total_expense`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_total_expense` (`user_id_param` INT, `from_param` DATETIME, `to_param` DATETIME) RETURNS FLOAT BEGIN
  DECLARE total FLOAT;
  SELECT SUM(nominal) INTO total FROM activities WHERE user_id = user_id_param AND type = 'PLRN' AND date_at >= from_param AND date_at <= to_param;
  RETURN total;
END$$

DROP FUNCTION IF EXISTS `get_total_income`$$
CREATE DEFINER=`root`@`localhost` FUNCTION `get_total_income` (`user_id_param` INT, `from_param` DATETIME, `to_param` DATETIME) RETURNS FLOAT BEGIN
  DECLARE total FLOAT;
  SELECT SUM(nominal) AS total INTO total FROM activities WHERE user_id = user_id_param AND type = 'PMSK' AND date_at >= from_param AND date_at <= to_param;
  RETURN total;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Struktur dari tabel `activities`
--

DROP TABLE IF EXISTS `activities`;
CREATE TABLE `activities` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `description` varchar(250) NOT NULL,
  `nominal` float NOT NULL,
  `type` varchar(250) NOT NULL,
  `date_at` datetime NOT NULL DEFAULT current_timestamp(),
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `updated_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Trigger `activities`
--
DROP TRIGGER IF EXISTS `activity_update_at`;
DELIMITER $$
CREATE TRIGGER `activity_update_at` BEFORE UPDATE ON `activities` FOR EACH ROW BEGIN
    SET NEW.updated_at = NOW();
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Stand-in struktur untuk tampilan `transaction_activities`
-- (Lihat di bawah untuk tampilan aktual)
--
DROP VIEW IF EXISTS `transaction_activities`;
CREATE TABLE `transaction_activities` (
`id` int(11)
,`user_id` int(11)
,`description` varchar(250)
,`nominal` float
,`type` varchar(250)
,`date_at` datetime
,`created_at` datetime
,`updated_at` datetime
,`type_id` varchar(250)
,`name` varchar(250)
);

-- --------------------------------------------------------

--
-- Struktur dari tabel `types`
--

DROP TABLE IF EXISTS `types`;
CREATE TABLE `types` (
  `id` varchar(250) NOT NULL,
  `name` varchar(250) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `types`
--

INSERT INTO `types` (`id`, `name`) VALUES
('PLRN', 'pengeluaran'),
('PMSK', 'pemasukan');

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur untuk view `transaction_activities`
--
DROP TABLE IF EXISTS `transaction_activities`;

DROP VIEW IF EXISTS `transaction_activities`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `transaction_activities`  AS  select `activities`.`id` AS `id`,`activities`.`user_id` AS `user_id`,`activities`.`description` AS `description`,`activities`.`nominal` AS `nominal`,`activities`.`type` AS `type`,`activities`.`date_at` AS `date_at`,`activities`.`created_at` AS `created_at`,`activities`.`updated_at` AS `updated_at`,`types`.`id` AS `type_id`,`types`.`name` AS `name` from (`activities` join `types` on(`types`.`id` = `activities`.`type`)) ;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `activities`
--
ALTER TABLE `activities`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `type` (`type`);

--
-- Indeks untuk tabel `types`
--
ALTER TABLE `types`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `activities`
--
ALTER TABLE `activities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `activities`
--
ALTER TABLE `activities`
  ADD CONSTRAINT `activities_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `activities_ibfk_2` FOREIGN KEY (`type`) REFERENCES `types` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
