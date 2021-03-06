-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-01-2022 a las 14:40:57
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `smi`
--
CREATE DATABASE IF NOT EXISTS `smi` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `smi`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblproducts`
--

CREATE TABLE `tblproducts` (
  `activado` tinyint(1) NOT NULL,
  `code` int(10) NOT NULL,
  `codlocal` tinyint(3) NOT NULL,
  `detalle` varchar(104) CHARACTER SET utf8 NOT NULL,
  `dep` varchar(8) CHARACTER SET utf8 NOT NULL,
  `ean_13` double NOT NULL,
  `linea` double NOT NULL,
  `sucursal` varchar(32) CHARACTER SET utf8 NOT NULL,
  `stock_` decimal(16,1) NOT NULL,
  `pventa` double NOT NULL,
  `p_oferta` double NOT NULL,
  `avg_pro` decimal(16,2) NOT NULL,
  `costo_prom` double NOT NULL,
  `codBarra` double NOT NULL,
  `pcadena` double NOT NULL,
  `pedido` int(11) NOT NULL,
  `und_defect` int(11) NOT NULL,
  `responsable` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tblusers`
--

CREATE TABLE `tblusers` (
  `id` int(11) NOT NULL COMMENT 'id Primario',
  `username` varchar(50) NOT NULL COMMENT 'Nombre de Usuario',
  `password` varchar(255) NOT NULL COMMENT 'Password del Usuario',
  `name` varchar(100) NOT NULL COMMENT 'Nombre del Usuario',
  `rol` varchar(1) NOT NULL COMMENT 'Rol'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Tabla de Usuarios';

--
-- Volcado de datos para la tabla `tblusers`
--

INSERT INTO `tblusers` (`id`, `username`, `password`, `name`, `rol`) VALUES
(1, 'raynito', '$2y$10$8Czxrc4Eni6kjTB3YKB3GeRdzZvsNJqxnuHRJTiVEv.VQnjZ9v/Gi', 'Rayne Flores', '1'),
(2, 'Linda', '$2y$10$AtuJNF1yDcsec6j1WIAlqud.Qy8y99VPghtO094sUEYU/EyWisg96', 'Luzmary Arrieta', '2'),
(7, 'roxana', '$2y$10$4VTS8jPYfWAIMJtDOpgEfOLZJDu4IMEACZ6a4I4hsYVAg5H2x8KgK', 'Roxana Mulfor', '2'),
(8, 'admin', '$2y$10$y/FN5iJ2HNL8dk8JAsJjB.pH/0sXod0XtSWkAPeEFhoqzs5yXigzy', 'Administrador', '1'),
(9, 'ivandgv19', '$2y$10$nQwp2JDQpC4WATIx26/ECuq.FZwZObKORS/rJet2EyBjEmlqCDuIS', 'Iván Gutiérrez', '1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `tblproducts`
--
ALTER TABLE `tblproducts`
  ADD PRIMARY KEY (`code`);

--
-- Indices de la tabla `tblusers`
--
ALTER TABLE `tblusers`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `tblusers`
--
ALTER TABLE `tblusers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id Primario', AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
