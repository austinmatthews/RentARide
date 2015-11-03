-- phpMyAdmin SQL Dump
-- version 4.2.10
-- http://www.phpmyadmin.net
--
-- Host: localhost:8889
-- Generation Time: Nov 03, 2015 at 07:49 PM
-- Server version: 5.5.38
-- PHP Version: 5.5.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Database: `RentARideDatabase`
--

-- --------------------------------------------------------

--
-- Table structure for table `Comments`
--

CREATE TABLE `Comments` (
  `customer` varchar(255) NOT NULL,
  `rental` varchar(255) NOT NULL,
  `comment` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `HourlyPrice`
--

CREATE TABLE `HourlyPrice` (
  `maxHours` int(11) NOT NULL,
  `mineHours` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Rental`
--

CREATE TABLE `Rental` (
  `customer` varchar(255) NOT NULL,
  `pickupTime` date NOT NULL,
  `returnTime` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `RentalLocations`
--

CREATE TABLE `RentalLocations` (
  `address` varchar(255) NOT NULL,
  `capacity` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `RentARideConfig`
--

CREATE TABLE `RentARideConfig` (
  `membershipPrice` int(11) NOT NULL,
  `overtimePenalty` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Reservations`
--

CREATE TABLE `Reservations` (
  `customer` varchar(255) NOT NULL,
  `pickupTime` date NOT NULL,
  `rental` varchar(255) NOT NULL,
  `rentalDuration` int(11) NOT NULL,
  `rentalLocation` varchar(255) NOT NULL,
  `vehicleType` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

CREATE TABLE `User` (
`id` int(11) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `emailAddress` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `createdDate` date NOT NULL,
  `userStatus` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Vehicle`
--

CREATE TABLE `Vehicle` (
  `registrationTag` varchar(255) NOT NULL,
  `vehicleCondition` varchar(255) NOT NULL,
  `lastService` date NOT NULL,
  `make` varchar(255) NOT NULL,
  `mileage` int(11) NOT NULL,
  `model` varchar(255) NOT NULL,
  `rentalLocation` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `vehicleType` varchar(255) NOT NULL,
  `year` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `VehicleType`
--

CREATE TABLE `VehicleType` (
  `type` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `User`
--
ALTER TABLE `User`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `Vehicle`
--
ALTER TABLE `Vehicle`
 ADD PRIMARY KEY (`registrationTag`);

--
-- Indexes for table `VehicleType`
--
ALTER TABLE `VehicleType`
 ADD PRIMARY KEY (`type`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `User`
--
ALTER TABLE `User`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
