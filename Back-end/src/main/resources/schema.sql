-- Database Schema for Vehicle Rental Application

-- Drop tables if they exist to ensure a clean start
DROP TABLE IF EXISTS bill_item;
DROP TABLE IF EXISTS bill;
DROP TABLE IF EXISTS vehicle_log;
DROP TABLE IF EXISTS vehicle_reservation;
DROP TABLE IF EXISTS vehicle;
DROP TABLE IF EXISTS parking_stall;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS car_rental_location;
DROP TABLE IF EXISTS car_rental_system;
DROP TABLE IF EXISTS car;
DROP TABLE IF EXISTS truck;
DROP TABLE IF EXISTS suv;
DROP TABLE IF EXISTS motorcycle;
DROP TABLE IF EXISTS van;
DROP TABLE IF EXISTS receptionist;
DROP TABLE IF EXISTS driver;
DROP TABLE IF EXISTS service;

-- Create tables
CREATE TABLE IF NOT EXISTS car_rental_system (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS car_rental_location (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    street_address VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zipcode VARCHAR(20),
    country VARCHAR(255),
    car_rental_system_id BIGINT,
    FOREIGN KEY (car_rental_system_id) REFERENCES car_rental_system(id)
);

CREATE TABLE IF NOT EXISTS parking_stall (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    stall_number VARCHAR(50) NOT NULL,
    location_identifier VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS account (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(50),
    status VARCHAR(50),
    created_date BIGINT,
    account_type VARCHAR(50),
    person_id BIGINT,
    person_type VARCHAR(50),
    street_address VARCHAR(255),
    city VARCHAR(255),
    state VARCHAR(255),
    zipcode VARCHAR(20),
    country VARCHAR(255),
    license_number VARCHAR(255),
    license_expiry BIGINT,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS vehicle (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number_plate VARCHAR(20) NOT NULL,
    stock_number VARCHAR(50),
    passenger_capacity INT NOT NULL,
    has_sunroof BOOLEAN DEFAULT FALSE,
    model VARCHAR(255) NOT NULL,
    make VARCHAR(255) NOT NULL,
    manufacturing_year INT NOT NULL,
    mileage INT NOT NULL,
    barcode VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    car_rental_location_id BIGINT,
    parking_stall_id BIGINT,
    vehicle_type VARCHAR(50) NOT NULL,
    type VARCHAR(50),
    FOREIGN KEY (car_rental_location_id) REFERENCES car_rental_location(id),
    FOREIGN KEY (parking_stall_id) REFERENCES parking_stall(id)
);

CREATE TABLE IF NOT EXISTS vehicle_reservation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    creation_date BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    due_date BIGINT NOT NULL,
    return_date BIGINT,
    pickup_location_id BIGINT,
    return_location_id BIGINT,
    vehicle_id BIGINT,
    account_id BIGINT,
    FOREIGN KEY (pickup_location_id) REFERENCES car_rental_location(id),
    FOREIGN KEY (return_location_id) REFERENCES car_rental_location(id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE IF NOT EXISTS vehicle_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    description TEXT,
    creation_date BIGINT NOT NULL,
    vehicle_id BIGINT,
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id)
);

CREATE TABLE IF NOT EXISTS bill (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    creation_date BIGINT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    reservation_id BIGINT,
    FOREIGN KEY (reservation_id) REFERENCES vehicle_reservation(id)
);

CREATE TABLE IF NOT EXISTS bill_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10,2) NOT NULL,
    type VARCHAR(50) NOT NULL,
    bill_id BIGINT,
    FOREIGN KEY (bill_id) REFERENCES bill(id)
);

-- Create tables for vehicle subtypes
CREATE TABLE IF NOT EXISTS car (
    id BIGINT PRIMARY KEY,
    type VARCHAR(50),
    FOREIGN KEY (id) REFERENCES vehicle(id)
);

CREATE TABLE IF NOT EXISTS truck (
    id BIGINT PRIMARY KEY,
    type VARCHAR(50),
    FOREIGN KEY (id) REFERENCES vehicle(id)
);

CREATE TABLE IF NOT EXISTS suv (
    id BIGINT PRIMARY KEY,
    type VARCHAR(50),
    FOREIGN KEY (id) REFERENCES vehicle(id)
);

CREATE TABLE IF NOT EXISTS motorcycle (
    id BIGINT PRIMARY KEY,
    type VARCHAR(50),
    FOREIGN KEY (id) REFERENCES vehicle(id)
);

CREATE TABLE IF NOT EXISTS van (
    id BIGINT PRIMARY KEY,
    type VARCHAR(50),
    FOREIGN KEY (id) REFERENCES vehicle(id)
);

-- Create table for receptionists
CREATE TABLE IF NOT EXISTS receptionist (
    id BIGINT PRIMARY KEY,
    date_joined BIGINT,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id) REFERENCES account(id)
);

-- Create table for service (parent for Driver)
CREATE TABLE IF NOT EXISTS service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_id VARCHAR(255),
    vehicle_reservation_id BIGINT,
    service_type VARCHAR(50),
    FOREIGN KEY (vehicle_reservation_id) REFERENCES vehicle_reservation(id)
);

-- Create table for driver service
CREATE TABLE IF NOT EXISTS driver (
    id BIGINT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES service(id)
);

-- Create indexes for better query performance
CREATE INDEX idx_vehicle_status ON vehicle(status);
CREATE INDEX idx_reservation_status ON vehicle_reservation(status);
CREATE INDEX idx_reservation_dates ON vehicle_reservation(creation_date, due_date, return_date);
CREATE INDEX idx_account_username ON account(username);
CREATE INDEX idx_bill_status ON bill(status);
CREATE INDEX idx_vehicle_log_type ON vehicle_log(type);
