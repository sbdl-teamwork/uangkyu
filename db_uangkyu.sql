-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 19 Bulan Mei 2021 pada 11.20
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

--
-- Trigger `users`
--
DROP TRIGGER IF EXISTS `register_user_log`;
DELIMITER $$
CREATE TRIGGER `register_user_log` AFTER INSERT ON `users` FOR EACH ROW BEGIN
    INSERT INTO logs (user_id, message) VALUES (NEW.id, "Berhasil membuat akun");
END
$$
DELIMITER ;

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
