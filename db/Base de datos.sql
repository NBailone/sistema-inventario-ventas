-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-02-2025 a las 21:28:46
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyectofb`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarBono` (IN `texto` VARCHAR(50))   SELECT b.simbolo, des.descripcion, b.cotizacion, b.variacion, b.apertura, b.maximo, b.minimo, b.ultimo_cierre, b.volumen, b.fecha FROM bonos b JOIN descripcion_activos_financieros des where b.simbolo = des.simbolo AND (b.simbolo=texto)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarBonos` (IN `texto` VARCHAR(50))   SELECT b.simbolo, des.descripcion, b.cotizacion, b.variacion, b.apertura, b.maximo, b.minimo, b.ultimo_cierre, b.volumen, b.fecha FROM bonos b JOIN descripcion_activos_financieros des where b.simbolo = des.simbolo AND ((b.simbolo LIKE CONCAT('%', texto, '%')) or (des.descripcion LIKE CONCAT('%', texto, '%')))$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarCedear` (IN `texto` VARCHAR(50))   SELECT c.simbolo, des.descripcion, c.cotizacion, c.variacion, c.apertura, c.maximo, c.minimo, c.ultimo_cierre, c.cantidad_operaciones, c.fecha, i.Macd, i.PronosticoMACD, i.RSI, i.PronosticoRSI, i.CMM20, i.PronosticoCMM20, i.ROC, i.PronosticoROC, i.Estocastico, i.PronosticoESTOCASTICO, i.CCI, i.PronosticoCCI, i.DX, i.PronosticoDX, i.ATR, i.PronosticoATR, i.Pronostico, i.Total, i.fecha FROM cedears c JOIN descripcion_activos_financieros des join ind_cedears i where c.simbolo = des.simbolo AND c.simbolo = i.simbolo AND (c.simbolo=texto)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `buscarCedears` (IN `texto` VARCHAR(50))   SELECT c.simbolo, des.descripcion, c.cotizacion, c.variacion, c.apertura, c.maximo, c.minimo, c.ultimo_cierre, c.cantidad_operaciones, c.fecha, i.Macd, i.PronosticoMACD, i.RSI, i.PronosticoRSI, i.CMM20, i.PronosticoCMM20, i.ROC, i.PronosticoROC, i.Estocastico, i.PronosticoESTOCASTICO, i.CCI, i.PronosticoCCI, i.DX, i.PronosticoDX, i.ATR, i.PronosticoATR, i.Pronostico, i.Total, i.fecha FROM cedears c JOIN descripcion_activos_financieros des join ind_cedears i where c.simbolo = des.simbolo AND c.simbolo = i.simbolo AND ((c.simbolo LIKE CONCAT('%', texto, '%')) or (des.descripcion LIKE CONCAT('%', texto, '%'))or (i.Pronostico LIKE CONCAT('%', texto, '%')))$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cotizacionBonos` ()   SELECT b.simbolo, des.descripcion, b.cotizacion, b.variacion, b.apertura, b.maximo, b.minimo, b.ultimo_cierre, b.volumen, b.fecha FROM bonos b JOIN descripcion_activos_financieros des where b.simbolo = des.simbolo$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cotizacionBurcap` ()   SELECT b.simbolo, des.descripcion, b.cotizacion, b.variacion, b.apertura, b.maximo, b.minimo, b.ultimo_cierre, b.cantidad_operaciones, b.fecha, i.Macd, i.PronosticoMACD, i.RSI, i.PronosticoRSI, i.CMM20, i.PronosticoCMM20, i.ROC, i.PronosticoROC, i.Estocastico, i.PronosticoESTOCASTICO, i.CCI, i.PronosticoCCI, i.DX, i.PronosticoDX, i.ATR, i.PronosticoATR, i.Pronostico, i.Total, i.fecha FROM burcap b JOIN descripcion_activos_financieros des join ind_burcap i where b.simbolo = des.simbolo AND b.simbolo = i.simbolo$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cotizacionCedears` ()   SELECT c.simbolo, des.descripcion, c.cotizacion, c.variacion, c.apertura, c.maximo, c.minimo, c.ultimo_cierre, c.cantidad_operaciones, c.fecha, i.Macd, i.PronosticoMACD, i.RSI, i.PronosticoRSI, i.CMM20, i.PronosticoCMM20, i.ROC, i.PronosticoROC, i.Estocastico, i.PronosticoESTOCASTICO, i.CCI, i.PronosticoCCI, i.DX, i.PronosticoDX, i.ATR, i.PronosticoATR, i.Pronostico, i.Total, i.fecha FROM cedears c JOIN descripcion_activos_financieros des join ind_cedears i where c.simbolo = des.simbolo AND c.simbolo = i.simbolo$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cotizacionCriptos` ()   SELECT c.simbolo, des.descripcion, c.cotizacionArg, c.cotizacionUsd, c.cotizacionEur, c.fecha, i.Macd, i.PronosticoMACD, i.RSI, i.PronosticoRSI, i.CMM20, i.PronosticoCMM20, i.ROC, i.PronosticoROC, i.Pronostico, i.Total, i.fecha FROM criptomonedas c JOIN descripcion_activos_financieros des join ind_criptomonedas i where c.simbolo = des.simbolo AND c.simbolo = i.simbolo$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cotizacionDolar` ()   SELECT d.nombre, d.compra, d.venta, d.agencia FROM dolar d$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cotizacionMerval` ()   SELECT m.simbolo, des.descripcion, m.cotizacion, m.variacion, m.apertura, m.maximo, m.minimo, m.ultimo_cierre, m.cantidad_operaciones, m.fecha, i.Macd, i.PronosticoMACD, i.RSI, i.PronosticoRSI, i.CMM20, i.PronosticoCMM20, i.ROC, i.PronosticoROC, i.Estocastico, i.PronosticoESTOCASTICO, i.CCI, i.PronosticoCCI, i.DX, i.PronosticoDX, i.ATR, i.PronosticoATR, i.Pronostico, i.Total, i.fecha FROM merval m JOIN descripcion_activos_financieros des join ind_merval i where m.simbolo = des.simbolo AND m.simbolo = i.simbolo$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cotizacionMerval25` ()   SELECT m.simbolo, des.descripcion, m.cotizacion, m.variacion, m.apertura, m.maximo, m.minimo, m.ultimo_cierre, m.cantidad_operaciones, m.fecha, i.Macd, i.PronosticoMACD, i.RSI, i.PronosticoRSI, i.CMM20, i.PronosticoCMM20, i.ROC, i.PronosticoROC, i.Estocastico, i.PronosticoESTOCASTICO, i.CCI, i.PronosticoCCI, i.DX, i.PronosticoDX, i.ATR, i.PronosticoATR, i.Pronostico, i.Total, i.fecha FROM merval25 m JOIN descripcion_activos_financieros des join ind_merval25 i where m.simbolo = des.simbolo AND m.simbolo = i.simbolo$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cotizacionMervalArgentina` ()   SELECT m.simbolo, des.descripcion, m.cotizacion, m.variacion, m.apertura, m.maximo, m.minimo, m.ultimo_cierre, m.cantidad_operaciones, m.fecha, i.Macd, i.PronosticoMACD, i.RSI, i.PronosticoRSI, i.CMM20, i.PronosticoCMM20, i.ROC, i.PronosticoROC, i.Estocastico, i.PronosticoESTOCASTICO, i.CCI, i.PronosticoCCI, i.DX, i.PronosticoDX, i.ATR, i.PronosticoATR, i.Pronostico, i.Total, i.fecha FROM mervalargentina m JOIN descripcion_activos_financieros des join ind_mervalargentina i where m.simbolo = des.simbolo AND m.simbolo = i.simbolo$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cotizacionOpciones` ()   SELECT o.simbolo, o.cotizacion, o.variacion, o.apertura, o.maximo, o.minimo, o.ultimo_cierre, o.cantidad_operaciones, o.volumen, o.moneda, o.fecha FROM opciones o$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `cotizacionPanelGeneral` ()   SELECT m.simbolo, des.descripcion, m.cotizacion, m.variacion, m.apertura, m.maximo, m.minimo, m.ultimo_cierre, m.cantidad_operaciones, m.fecha, i.Macd, i.PronosticoMACD, i.RSI, i.PronosticoRSI, i.CMM20, i.PronosticoCMM20, i.ROC, i.PronosticoROC, i.Estocastico, i.PronosticoESTOCASTICO, i.CCI, i.PronosticoCCI, i.DX, i.PronosticoDX, i.ATR, i.PronosticoATR, i.Pronostico, i.Total, i.fecha FROM panelgeneral m JOIN descripcion_activos_financieros des join ind_panelgeneral i where m.simbolo = des.simbolo AND m.simbolo = i.simbolo$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoBonos` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT bh.apertura, bh.maximo, bh.minimo, bh.ultimo_cierre, bh.fecha FROM bonos_historico bh where bh.simbolo=simbolo and bh.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoBurcap` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT mh.apertura, mh.maximo, mh.minimo, mh.ultimo_cierre, mh.fecha FROM burcap_historico mh where mh.simbolo=simbolo and mh.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoCedears` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT mh.apertura, mh.maximo, mh.minimo, mh.ultimo_cierre, mh.fecha FROM cedears_historico mh where mh.simbolo=simbolo and mh.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoCriptomonedasDolar` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT ch.cotizacionUsd, ch.fecha FROM criptomonedas_historico ch where ch.simbolo=simbolo and ch.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoCriptomonedasEuro` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT ch.cotizacionEur, ch.fecha FROM criptomonedas_historico ch where ch.simbolo=simbolo and ch.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoCriptomonedasPeso` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT ch.cotizacionArg, ch.fecha FROM criptomonedas_historico ch where ch.simbolo=simbolo and ch.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoDolar` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT dh.compra, dh.venta, dh.fecha FROM dolar_historico dh where dh.nombre=simbolo and dh.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoMerval` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT mh.apertura, mh.maximo, mh.minimo, mh.ultimo_cierre, mh.fecha FROM merval_historico mh where mh.simbolo=simbolo and mh.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoMerval25` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT mh.apertura, mh.maximo, mh.minimo, mh.ultimo_cierre, mh.fecha FROM merval25_historico mh where mh.simbolo=simbolo and mh.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoMervalArgentina` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT mh.apertura, mh.maximo, mh.minimo, mh.ultimo_cierre, mh.fecha FROM mervalargentina_historico mh where mh.simbolo=simbolo and mh.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `historicoPanelGeneral` (IN `simbolo` VARCHAR(30) CHARSET utf8mb4, IN `dias` INT)   SELECT mh.apertura, mh.maximo, mh.minimo, mh.ultimo_cierre, mh.fecha FROM panelgeneral_historico mh where mh.simbolo=simbolo and mh.fecha BETWEEN DATE_SUB(now(),INTERVAL dias DAY) and now()$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `pronosticoGeneral` ()   SELECT c.Pronostico, COUNT(*) FROM ind_cedears c GROUP BY c.Pronostico order by c.Pronostico$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bonos`
--

CREATE TABLE `bonos` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT 0.000,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT 0,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `bonos_historico`
--

CREATE TABLE `bonos_historico` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `burcap`
--

CREATE TABLE `burcap` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `burcap_historico`
--

CREATE TABLE `burcap_historico` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Estructura de tabla para la tabla `cedears`
--

CREATE TABLE `cedears` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `cedears_historico`
--

CREATE TABLE `cedears_historico` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `criptomonedas`
--

CREATE TABLE `criptomonedas` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacionArg` decimal(15,3) DEFAULT NULL,
  `cotizacionEur` decimal(15,3) DEFAULT NULL,
  `cotizacionUsd` decimal(15,3) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `criptomonedas_historico`
--

CREATE TABLE `criptomonedas_historico` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacionArg` decimal(15,3) DEFAULT NULL,
  `cotizacionEur` decimal(15,3) DEFAULT NULL,
  `cotizacionUsd` decimal(15,3) DEFAULT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `descripcion_activos_financieros`
--

CREATE TABLE `descripcion_activos_financieros` (
  `simbolo` varchar(20) NOT NULL,
  `descripcion` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `dolar`
--

CREATE TABLE `dolar` (
  `nombre` varchar(40) NOT NULL,
  `compra` decimal(15,2) DEFAULT NULL,
  `venta` decimal(15,2) DEFAULT NULL,
  `agencia` int(5) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `dolar_historico`
--

CREATE TABLE `dolar_historico` (
  `nombre` varchar(40) NOT NULL,
  `compra` decimal(15,2) DEFAULT NULL,
  `venta` decimal(15,2) DEFAULT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `ind_burcap`
--

CREATE TABLE `ind_burcap` (
  `simbolo` varchar(20) NOT NULL,
  `Macd` decimal(15,2) DEFAULT NULL,
  `PronosticoMACD` varchar(25) DEFAULT NULL,
  `RSI` decimal(15,2) DEFAULT NULL,
  `PronosticoRSI` varchar(25) DEFAULT NULL,
  `CMM20` decimal(15,2) DEFAULT NULL,
  `PronosticoCMM20` varchar(25) DEFAULT NULL,
  `ROC` decimal(15,2) DEFAULT NULL,
  `PronosticoROC` varchar(25) DEFAULT NULL,
  `Estocastico` decimal(15,2) DEFAULT NULL,
  `PronosticoESTOCASTICO` varchar(25) DEFAULT NULL,
  `CCI` decimal(15,2) DEFAULT NULL,
  `PronosticoCCI` varchar(25) DEFAULT NULL,
  `+DI` decimal(15,2) DEFAULT NULL,
  `-DI` decimal(15,2) DEFAULT NULL,
  `DX` decimal(15,2) DEFAULT NULL,
  `PronosticoDX` varchar(25) DEFAULT NULL,
  `ATR` decimal(15,2) DEFAULT NULL,
  `PronosticoATR` varchar(25) DEFAULT NULL,
  `Total` int(2) DEFAULT NULL,
  `Pronostico` varchar(25) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `ind_cedears`
--

CREATE TABLE `ind_cedears` (
  `simbolo` varchar(20) NOT NULL,
  `Macd` decimal(15,2) DEFAULT NULL,
  `PronosticoMACD` varchar(25) DEFAULT NULL,
  `RSI` decimal(15,2) DEFAULT NULL,
  `PronosticoRSI` varchar(25) DEFAULT NULL,
  `CMM20` decimal(15,2) DEFAULT NULL,
  `PronosticoCMM20` varchar(25) DEFAULT NULL,
  `ROC` decimal(15,2) DEFAULT NULL,
  `PronosticoROC` varchar(25) DEFAULT NULL,
  `Estocastico` decimal(15,2) DEFAULT NULL,
  `PronosticoESTOCASTICO` varchar(25) DEFAULT NULL,
  `CCI` decimal(15,2) DEFAULT NULL,
  `PronosticoCCI` varchar(25) DEFAULT NULL,
  `+DI` decimal(15,2) DEFAULT NULL,
  `-DI` decimal(15,2) DEFAULT NULL,
  `DX` decimal(15,2) DEFAULT NULL,
  `PronosticoDX` varchar(25) DEFAULT NULL,
  `ATR` decimal(15,2) DEFAULT NULL,
  `PronosticoATR` varchar(25) DEFAULT NULL,
  `Total` int(2) DEFAULT NULL,
  `Pronostico` varchar(25) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `ind_criptomonedas`
--

CREATE TABLE `ind_criptomonedas` (
  `simbolo` varchar(20) NOT NULL,
  `Macd` decimal(15,2) DEFAULT NULL,
  `PronosticoMACD` varchar(25) DEFAULT NULL,
  `RSI` decimal(15,2) DEFAULT NULL,
  `PronosticoRSI` varchar(25) DEFAULT NULL,
  `CMM20` decimal(15,2) DEFAULT NULL,
  `PronosticoCMM20` varchar(25) DEFAULT NULL,
  `ROC` decimal(15,2) DEFAULT NULL,
  `PronosticoROC` varchar(25) DEFAULT NULL,
  `Total` int(2) DEFAULT NULL,
  `Pronostico` varchar(25) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;



--
-- Estructura de tabla para la tabla `ind_merval`
--

CREATE TABLE `ind_merval` (
  `simbolo` varchar(20) NOT NULL,
  `Macd` decimal(15,2) DEFAULT NULL,
  `PronosticoMACD` varchar(25) DEFAULT NULL,
  `RSI` decimal(15,2) DEFAULT NULL,
  `PronosticoRSI` varchar(25) DEFAULT NULL,
  `CMM20` decimal(15,2) DEFAULT NULL,
  `PronosticoCMM20` varchar(25) DEFAULT NULL,
  `ROC` decimal(15,2) DEFAULT NULL,
  `PronosticoROC` varchar(25) DEFAULT NULL,
  `Estocastico` decimal(15,2) DEFAULT NULL,
  `PronosticoESTOCASTICO` varchar(25) DEFAULT NULL,
  `CCI` decimal(15,2) DEFAULT NULL,
  `PronosticoCCI` varchar(25) DEFAULT NULL,
  `+DI` decimal(15,2) DEFAULT NULL,
  `-DI` decimal(15,2) DEFAULT NULL,
  `DX` decimal(15,2) DEFAULT NULL,
  `PronosticoDX` varchar(25) DEFAULT NULL,
  `ATR` decimal(15,2) DEFAULT NULL,
  `PronosticoATR` varchar(25) DEFAULT NULL,
  `Total` int(2) DEFAULT NULL,
  `Pronostico` varchar(25) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `ind_merval25`
--

CREATE TABLE `ind_merval25` (
  `simbolo` varchar(20) NOT NULL,
  `Macd` decimal(15,2) DEFAULT NULL,
  `PronosticoMACD` varchar(25) DEFAULT NULL,
  `RSI` decimal(15,2) DEFAULT NULL,
  `PronosticoRSI` varchar(25) DEFAULT NULL,
  `CMM20` decimal(15,2) DEFAULT NULL,
  `PronosticoCMM20` varchar(25) DEFAULT NULL,
  `ROC` decimal(15,2) DEFAULT NULL,
  `PronosticoROC` varchar(25) DEFAULT NULL,
  `Estocastico` decimal(15,2) DEFAULT NULL,
  `PronosticoESTOCASTICO` varchar(25) DEFAULT NULL,
  `CCI` decimal(15,2) DEFAULT NULL,
  `PronosticoCCI` varchar(25) DEFAULT NULL,
  `+DI` decimal(15,2) DEFAULT NULL,
  `-DI` decimal(15,2) DEFAULT NULL,
  `DX` decimal(15,2) DEFAULT NULL,
  `PronosticoDX` varchar(25) DEFAULT NULL,
  `ATR` decimal(15,2) DEFAULT NULL,
  `PronosticoATR` varchar(25) DEFAULT NULL,
  `Total` int(2) DEFAULT NULL,
  `Pronostico` varchar(25) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `ind_mervalargentina`
--

CREATE TABLE `ind_mervalargentina` (
  `simbolo` varchar(20) NOT NULL,
  `Macd` decimal(15,2) DEFAULT NULL,
  `PronosticoMACD` varchar(25) DEFAULT NULL,
  `RSI` decimal(15,2) DEFAULT NULL,
  `PronosticoRSI` varchar(25) DEFAULT NULL,
  `CMM20` decimal(15,2) DEFAULT NULL,
  `PronosticoCMM20` varchar(25) DEFAULT NULL,
  `ROC` decimal(15,2) DEFAULT NULL,
  `PronosticoROC` varchar(25) DEFAULT NULL,
  `Estocastico` decimal(15,2) DEFAULT NULL,
  `PronosticoESTOCASTICO` varchar(25) DEFAULT NULL,
  `CCI` decimal(15,2) DEFAULT NULL,
  `PronosticoCCI` varchar(25) DEFAULT NULL,
  `+DI` decimal(15,2) DEFAULT NULL,
  `-DI` decimal(15,2) DEFAULT NULL,
  `DX` decimal(15,2) DEFAULT NULL,
  `PronosticoDX` varchar(25) DEFAULT NULL,
  `ATR` decimal(15,2) DEFAULT NULL,
  `PronosticoATR` varchar(25) DEFAULT NULL,
  `Total` int(2) DEFAULT NULL,
  `Pronostico` varchar(25) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `ind_panelgeneral`
--

CREATE TABLE `ind_panelgeneral` (
  `simbolo` varchar(20) NOT NULL,
  `Macd` decimal(15,2) DEFAULT NULL,
  `PronosticoMACD` varchar(25) DEFAULT NULL,
  `RSI` decimal(15,2) DEFAULT NULL,
  `PronosticoRSI` varchar(25) DEFAULT NULL,
  `CMM20` decimal(15,2) DEFAULT NULL,
  `PronosticoCMM20` varchar(25) DEFAULT NULL,
  `ROC` decimal(15,2) DEFAULT NULL,
  `PronosticoROC` varchar(25) DEFAULT NULL,
  `Estocastico` decimal(15,2) DEFAULT NULL,
  `PronosticoESTOCASTICO` varchar(25) DEFAULT NULL,
  `CCI` decimal(15,2) DEFAULT NULL,
  `PronosticoCCI` varchar(25) DEFAULT NULL,
  `+DI` decimal(15,2) DEFAULT NULL,
  `-DI` decimal(15,2) DEFAULT NULL,
  `DX` decimal(15,2) DEFAULT NULL,
  `PronosticoDX` varchar(25) DEFAULT NULL,
  `ATR` decimal(15,2) DEFAULT NULL,
  `PronosticoATR` varchar(25) DEFAULT NULL,
  `Total` int(2) DEFAULT NULL,
  `Pronostico` varchar(25) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `merval`
--

CREATE TABLE `merval` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `merval25`
--

CREATE TABLE `merval25` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `merval25_historico`
--

CREATE TABLE `merval25_historico` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;




--
-- Estructura de tabla para la tabla `mervalargentina`
--

CREATE TABLE `mervalargentina` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `mervalargentina_historico`
--

CREATE TABLE `mervalargentina_historico` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;



--
-- Estructura de tabla para la tabla `merval_historico`
--

CREATE TABLE `merval_historico` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;



--
-- Estructura de tabla para la tabla `panelgeneral`
--

CREATE TABLE `panelgeneral` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Estructura de tabla para la tabla `panelgeneral_historico`
--

CREATE TABLE `panelgeneral_historico` (
  `simbolo` varchar(20) NOT NULL,
  `cotizacion` decimal(15,3) DEFAULT NULL,
  `variacion` decimal(15,3) DEFAULT NULL,
  `apertura` decimal(15,3) DEFAULT NULL,
  `maximo` decimal(15,3) DEFAULT NULL,
  `minimo` decimal(15,3) DEFAULT NULL,
  `volumen` decimal(15,3) DEFAULT NULL,
  `ultimo_cierre` decimal(15,3) DEFAULT NULL,
  `cantidad_operaciones` decimal(15,0) DEFAULT NULL,
  `fecha` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `bonos`
--
ALTER TABLE `bonos`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `bonos_historico`
--
ALTER TABLE `bonos_historico`
  ADD PRIMARY KEY (`simbolo`,`fecha`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `burcap`
--
ALTER TABLE `burcap`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `burcap_historico`
--
ALTER TABLE `burcap_historico`
  ADD PRIMARY KEY (`simbolo`,`fecha`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `cedears`
--
ALTER TABLE `cedears`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `cedears_historico`
--
ALTER TABLE `cedears_historico`
  ADD PRIMARY KEY (`simbolo`,`fecha`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `criptomonedas`
--
ALTER TABLE `criptomonedas`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `criptomonedas_historico`
--
ALTER TABLE `criptomonedas_historico`
  ADD PRIMARY KEY (`simbolo`,`fecha`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `descripcion_activos_financieros`
--
ALTER TABLE `descripcion_activos_financieros`
  ADD PRIMARY KEY (`simbolo`);

--
-- Indices de la tabla `dolar`
--
ALTER TABLE `dolar`
  ADD PRIMARY KEY (`nombre`),
  ADD KEY `nombre` (`nombre`);

--
-- Indices de la tabla `dolar_historico`
--
ALTER TABLE `dolar_historico`
  ADD PRIMARY KEY (`nombre`,`fecha`),
  ADD KEY `nombre` (`nombre`);

--
-- Indices de la tabla `ind_burcap`
--
ALTER TABLE `ind_burcap`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `ind_cedears`
--
ALTER TABLE `ind_cedears`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `ind_criptomonedas`
--
ALTER TABLE `ind_criptomonedas`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `ind_merval`
--
ALTER TABLE `ind_merval`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `ind_merval25`
--
ALTER TABLE `ind_merval25`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `ind_mervalargentina`
--
ALTER TABLE `ind_mervalargentina`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `ind_panelgeneral`
--
ALTER TABLE `ind_panelgeneral`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `merval`
--
ALTER TABLE `merval`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `merval25`
--
ALTER TABLE `merval25`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `merval25_historico`
--
ALTER TABLE `merval25_historico`
  ADD PRIMARY KEY (`simbolo`,`fecha`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `mervalargentina`
--
ALTER TABLE `mervalargentina`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `mervalargentina_historico`
--
ALTER TABLE `mervalargentina_historico`
  ADD PRIMARY KEY (`simbolo`,`fecha`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `merval_historico`
--
ALTER TABLE `merval_historico`
  ADD PRIMARY KEY (`simbolo`,`fecha`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `panelgeneral`
--
ALTER TABLE `panelgeneral`
  ADD PRIMARY KEY (`simbolo`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Indices de la tabla `panelgeneral_historico`
--
ALTER TABLE `panelgeneral_historico`
  ADD PRIMARY KEY (`simbolo`,`fecha`),
  ADD KEY `simbolo` (`simbolo`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `bonos`
--
ALTER TABLE `bonos`
  ADD CONSTRAINT `bonos_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `bonos_historico`
--
ALTER TABLE `bonos_historico`
  ADD CONSTRAINT `bonos_historico_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `burcap`
--
ALTER TABLE `burcap`
  ADD CONSTRAINT `burcap_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `burcap_historico`
--
ALTER TABLE `burcap_historico`
  ADD CONSTRAINT `burcap_historico_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `cedears`
--
ALTER TABLE `cedears`
  ADD CONSTRAINT `cedears_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `cedears_historico`
--
ALTER TABLE `cedears_historico`
  ADD CONSTRAINT `cedears_historico_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `criptomonedas`
--
ALTER TABLE `criptomonedas`
  ADD CONSTRAINT `criptomonedas_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `criptomonedas_historico`
--
ALTER TABLE `criptomonedas_historico`
  ADD CONSTRAINT `criptomonedas_historico_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `ind_burcap`
--
ALTER TABLE `ind_burcap`
  ADD CONSTRAINT `ind_burcap_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ind_cedears`
--
ALTER TABLE `ind_cedears`
  ADD CONSTRAINT `ind_cedears_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ind_criptomonedas`
--
ALTER TABLE `ind_criptomonedas`
  ADD CONSTRAINT `ind_criptomonedas_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ind_merval`
--
ALTER TABLE `ind_merval`
  ADD CONSTRAINT `ind_merval_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ind_merval25`
--
ALTER TABLE `ind_merval25`
  ADD CONSTRAINT `ind_merval25_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ind_mervalargentina`
--
ALTER TABLE `ind_mervalargentina`
  ADD CONSTRAINT `ind_mervalargentina_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `ind_panelgeneral`
--
ALTER TABLE `ind_panelgeneral`
  ADD CONSTRAINT `ind_panelgeneral_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `merval`
--
ALTER TABLE `merval`
  ADD CONSTRAINT `merval_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `merval25`
--
ALTER TABLE `merval25`
  ADD CONSTRAINT `merval25_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `merval25_historico`
--
ALTER TABLE `merval25_historico`
  ADD CONSTRAINT `merval25_historico_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `mervalargentina`
--
ALTER TABLE `mervalargentina`
  ADD CONSTRAINT `mervalargentina_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `mervalargentina_historico`
--
ALTER TABLE `mervalargentina_historico`
  ADD CONSTRAINT `mervalargentina_historico_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `merval_historico`
--
ALTER TABLE `merval_historico`
  ADD CONSTRAINT `merval_historico_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `panelgeneral`
--
ALTER TABLE `panelgeneral`
  ADD CONSTRAINT `panelgeneral_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;

--
-- Filtros para la tabla `panelgeneral_historico`
--
ALTER TABLE `panelgeneral_historico`
  ADD CONSTRAINT `panelgeneral_historico_ibfk_1` FOREIGN KEY (`simbolo`) REFERENCES `descripcion_activos_financieros` (`simbolo`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;









