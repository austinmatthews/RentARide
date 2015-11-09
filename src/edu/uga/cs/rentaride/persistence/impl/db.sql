
DROP DATABASE IF EXISTS `RentARideDatabase`;

CREATE DATABASE IF NOT EXISTS RentARideDatabase;

USE RentARideDatabase;

DROP TABLE IF EXISTS `HourlyPrice`;
DROP TABLE IF EXISTS `Rental`;
DROP TABLE IF EXISTS `RentalLocations`;
DROP TABLE IF EXISTS `RentARideConfig`;
DROP TABLE IF EXISTS `Reservations`;
DROP TABLE IF EXISTS `Users`;
DROP TABLE IF EXISTS `Vehicle`;
DROP TABLE IF EXISTS `VehicleType`;

--
-- Database: `RentARideDatabase`
--

-- disable foreign key checks to create tables in any order
SET foreign_key_checks = 0;

-- --------------------------------------------------------

--
-- Table structure for table `Comments`
--

CREATE TABLE `Comments` (
  `customer` varchar(255) NOT NULL,
  `rental` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  
  INDEX(customer),

  FOREIGN KEY(customer)
  	  REFERENCES Users(userName),
	
  INDEX(rental),

  FOREIGN KEY(rental)
  	  REFERENCES Rental(rentalNo)

  
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
  `rentalNo` int(11) NOT NULL AUTO_INCREMENT,
  `customer` varchar(255) NOT NULL,
  `pickupTime` date NOT NULL,
  `returnTime` date NOT NULL,

  PRIMARY KEY(rentalNo),

  INDEX(customer),
  FOREIGN KEY(customer)
  	  REFERENCES Users(userName)

  
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `RentalLocations`
--

CREATE TABLE `RentalLocations` (
  `address` varchar(255) NOT NULL,
  `capacity` int(11) NOT NULL,
  `locationName` varchar(255) NOT NULL,

  PRIMARY KEY(locationName)


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
  `rental` int(11) NOT NULL,
  `rentalDuration` int(11) NOT NULL,
  `rentalLocation` varchar(255) NOT NULL,
  `vehicleType` varchar(255) NOT NULL,

  PRIMARY KEY(customer,pickupTime),

  INDEX(rental),
  FOREIGN KEY(rental)
  	  REFERENCES Rentals(rentalNo)


) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `Users`
--

CREATE TABLE `Users` (
  `id` int(11) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  `emailAddress` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `createdDate` date NOT NULL,
  `userStatus` varchar(255) NOT NULL,

  PRIMARY KEY(userName)


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
  `vehicleYear` int(11) NOT NULL,

  PRIMARY KEY(registrationTag),

  INDEX(rentalLocation),
  FOREIGN KEY(rentalLocation)
  	  REFERENCES RentalLocations(locationName)

) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------


SET foreign_key_checks = 1;
