-- phpMyAdmin SQL Dump
-- version 4.8.1
-- https://www.phpmyadmin.net/
--
-- Host: db
-- Erstellungszeit: 28. Sep 2018 um 08:46
-- Server-Version: 5.7.22
-- PHP-Version: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `datacrawlingkraken`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `reading`
--

CREATE TABLE `reading` (
  `id` int(11) NOT NULL,
  `vehicle_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ambiant_pressure` float DEFAULT NULL,
  `canister_controler` float DEFAULT NULL,
  `current_for_injector_opening` float DEFAULT NULL,
  `distance_km` float DEFAULT NULL,
  `lambda_adapation` float DEFAULT NULL,
  `gear_engaged` float DEFAULT NULL,
  `lambda_control_1` float DEFAULT NULL,
  `lambda_control_2` float DEFAULT NULL,
  `maf_before_compressor` float DEFAULT NULL,
  `engine_maf` float DEFAULT NULL,
  `fuel_mass_adapation` float DEFAULT NULL,
  `engine_speed` float DEFAULT NULL,
  `turbo_turbine_speed` float DEFAULT NULL,
  `injection_number` float DEFAULT NULL,
  `fuel_pressure_at_inlet_pump` float DEFAULT NULL,
  `fuel_pressure` float DEFAULT NULL,
  `turbo_waste_gate_position` float DEFAULT NULL,
  `pedal_position` float DEFAULT NULL,
  `turbo_waste_gate_controler` float DEFAULT NULL,
  `water_pump_controler` float DEFAULT NULL,
  `engine_thermostat_controler` float DEFAULT NULL,
  `engine_air_throttle_controler` float DEFAULT NULL,
  `fuel_pump_delivery` float DEFAULT NULL,
  `segment_number` float DEFAULT NULL,
  `injector_adapation1` float DEFAULT NULL,
  `injector_adapation2` float DEFAULT NULL,
  `injector_adapation3` float DEFAULT NULL,
  `injector_adapation4` float DEFAULT NULL,
  `injector_adapation5` float DEFAULT NULL,
  `injector_adapation6` float DEFAULT NULL,
  `injector_adapation7` float DEFAULT NULL,
  `injector_adapation8` float DEFAULT NULL,
  `injector_adapation9` float DEFAULT NULL,
  `injector_adapation10` float DEFAULT NULL,
  `injector_adapation11` float DEFAULT NULL,
  `injector_adapation12` float DEFAULT NULL,
  `injector_adapation13` float DEFAULT NULL,
  `injector_adapation14` float DEFAULT NULL,
  `injector_adapation15` float DEFAULT NULL,
  `injector_adapation16` float DEFAULT NULL,
  `injector_adapation17` float DEFAULT NULL,
  `injector_adapation18` float DEFAULT NULL,
  `injector_adapation19` float DEFAULT NULL,
  `injector_adapation20` float DEFAULT NULL,
  `current_time_for_injector` float DEFAULT NULL,
  `pump_adaptation_2` float DEFAULT NULL,
  `ambiant_temperature` float DEFAULT NULL,
  `engine_coolant_tempertaure` float DEFAULT NULL,
  `ecu_temperature` float DEFAULT NULL,
  `turbo_temperature_before_turbine` float DEFAULT NULL,
  `fuel_temperature_before_pump` float DEFAULT NULL,
  `fuel_temperature_at_pump_outlet` float DEFAULT NULL,
  `fuel_temperature_in_rail` float DEFAULT NULL,
  `engine_inlet_temperture` float DEFAULT NULL,
  `time_30_s` float DEFAULT NULL,
  `timestamp` float DEFAULT NULL,
  `engine_oil_temperature` float DEFAULT NULL,
  `trt` float DEFAULT NULL,
  `battery_voltage` float DEFAULT NULL,
  `voltage_boost_injector_control` float DEFAULT NULL,
  `pump_adaptation_1` float DEFAULT NULL,
  `waste_gate_adapatation_1` float DEFAULT NULL,
  `waste_gate_adapatation_2` float DEFAULT NULL,
  `vehicle_speed` float DEFAULT NULL,
  `vin` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `readingaverage`
--

CREATE TABLE `readingaverage` (
  `id` bigint(20) NOT NULL,
  `Vehicle_Number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Ambiant_Pressure` float DEFAULT NULL,
  `Canister_Controler` float DEFAULT NULL,
  `Current_for_Injector_Opening` float DEFAULT NULL,
  `Distance_Km` float DEFAULT NULL,
  `Lambda_Adapation` float DEFAULT NULL,
  `Gear_Engaged` float DEFAULT NULL,
  `Lambda_Control_1` float DEFAULT NULL,
  `Lambda_Control_2` float DEFAULT NULL,
  `MAF_Before_Compressor` float DEFAULT NULL,
  `Engine_MAF` float DEFAULT NULL,
  `Fuel_Mass_Adapation` float DEFAULT NULL,
  `Engine_Speed` float DEFAULT NULL,
  `Turbo_Turbine_Speed` float DEFAULT NULL,
  `Injection_Number` float DEFAULT NULL,
  `Fuel_Pressure_at_Inlet_Pump` float DEFAULT NULL,
  `Fuel_Pressure` float DEFAULT NULL,
  `Turbo_Waste_Gate_Position` float DEFAULT NULL,
  `Pedal_Position` float DEFAULT NULL,
  `Turbo_Waste_Gate_Controler` float DEFAULT NULL,
  `Water_Pump_Controler` float DEFAULT NULL,
  `Engine_Thermostat_Controler` float DEFAULT NULL,
  `Engine_Air_Throttle_Controler` float DEFAULT NULL,
  `Fuel_Pump_Delivery` float DEFAULT NULL,
  `Segment_Number` float DEFAULT NULL,
  `Injector_Adapation1` float DEFAULT NULL,
  `Injector_Adapation2` float DEFAULT NULL,
  `Injector_Adapation3` float DEFAULT NULL,
  `Injector_Adapation4` float DEFAULT NULL,
  `Injector_Adapation5` float DEFAULT NULL,
  `Injector_Adapation6` float DEFAULT NULL,
  `Injector_Adapation7` float DEFAULT NULL,
  `Injector_Adapation8` float DEFAULT NULL,
  `Injector_Adapation9` float DEFAULT NULL,
  `Injector_Adapation10` float DEFAULT NULL,
  `Injector_Adapation11` float DEFAULT NULL,
  `Injector_Adapation12` float DEFAULT NULL,
  `Injector_Adapation13` float DEFAULT NULL,
  `Injector_Adapation14` float DEFAULT NULL,
  `Injector_Adapation15` float DEFAULT NULL,
  `Injector_Adapation16` float DEFAULT NULL,
  `Injector_Adapation17` float DEFAULT NULL,
  `Injector_Adapation18` float DEFAULT NULL,
  `Injector_Adapation19` float DEFAULT NULL,
  `Injector_Adapation20` float DEFAULT NULL,
  `Current_Time_for_Injector` float DEFAULT NULL,
  `Pump_Adaptation_2` float DEFAULT NULL,
  `Ambiant_Temperature` float DEFAULT NULL,
  `Engine_Coolant_Tempertaure` float DEFAULT NULL,
  `ECU_Temperature` float DEFAULT NULL,
  `TUrbo_Temperature_before_Turbine` float DEFAULT NULL,
  `Fuel_Temperature_Before_Pump` float DEFAULT NULL,
  `Fuel_Temperature_at_Pump_Outlet` float DEFAULT NULL,
  `Fuel_Temperature_In_Rail` float DEFAULT NULL,
  `Engine_Inlet_Temperture` float DEFAULT NULL,
  `time_30_s` float DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
  `Engine_Oil_Temperature` float DEFAULT NULL,
  `Trt` float DEFAULT NULL,
  `Battery_Voltage` float DEFAULT NULL,
  `Voltage_Boost_Injector_control` float DEFAULT NULL,
  `Pump_Adaptation_1` float DEFAULT NULL,
  `Waste_Gate_Adapatation_1` float DEFAULT NULL,
  `Waste_Gate_Adapatation_2` float DEFAULT NULL,
  `Vehicle_Speed` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `reading`
--
ALTER TABLE `reading`
  ADD PRIMARY KEY (`id`),
  ADD KEY `vehicle_number_index` (`Vehicle_Number`),
  ADD KEY `Vehicle_Number` (`Vehicle_Number`,`timestamp`,`time_30_s`);

--
-- Indizes für die Tabelle `readingaverage`
--
ALTER TABLE `readingaverage`
  ADD PRIMARY KEY (`id`),
  ADD KEY `Vehicle_Number` (`Vehicle_Number`,`timestamp`,`time_30_s`),
  ADD KEY `vehicle_number_index` (`Vehicle_Number`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `reading`
--
ALTER TABLE `reading`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `readingaverage`
--
ALTER TABLE `readingaverage`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
