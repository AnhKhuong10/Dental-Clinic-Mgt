-- CREATE DATABASE
-- CREATE DATABASE dental_clinic_mgt;
-- USE dental_clinic_mgt;

-- =====================
-- ROLES & PERMISSIONS
-- =====================
CREATE TABLE roles (
    roleId BIGINT AUTO_INCREMENT PRIMARY KEY,
    roleName VARCHAR(50) NOT NULL
);

CREATE TABLE permission (
    permissionId BIGINT AUTO_INCREMENT PRIMARY KEY,
    permissionName VARCHAR(50) NOT NULL
);

CREATE TABLE rolePermissionMap (
    rolePermissionMapId BIGINT AUTO_INCREMENT PRIMARY KEY,
    roleId BIGINT,
    permissionId BIGINT,
    FOREIGN KEY (roleId) REFERENCES roles(roleId),
    FOREIGN KEY (permissionId) REFERENCES permission(permissionId)
);

-- =====================
-- USERS
-- =====================
CREATE TABLE users (
    userId BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45) NOT NULL UNIQUE,
    fullName VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    birthdate DATE,
    phone VARCHAR(40),
    email VARCHAR(100),
    salary INT,
    roleId BIGINT,
    enabled TINYINT DEFAULT 1,
    refreshToken TEXT,
    FOREIGN KEY (roleId) REFERENCES roles(roleId)
);

-- =====================
-- PATIENTS
-- =====================
CREATE TABLE patients (
    patientId BIGINT AUTO_INCREMENT PRIMARY KEY,
    patientName VARCHAR(100) NOT NULL,
    birthdate DATE,
    gender TINYINT(1),
    address VARCHAR(255),
    phone VARCHAR(40),
    email VARCHAR(100),
    bodyPrehistory VARCHAR(255),
    teethPrehistory VARCHAR(255),
    status INT DEFAULT 1,
    isDeleted TINYINT DEFAULT 0
);

-- =====================
-- SCHEDULE
-- =====================
CREATE TABLE schedule (
    scheduleId BIGINT AUTO_INCREMENT PRIMARY KEY,
    patientId BIGINT,
    date DATE,
    status VARCHAR(45),
    booked TINYINT DEFAULT 0,
    FOREIGN KEY (patientId) REFERENCES patients(patientId)
);

-- =====================
-- PATIENT RECORDS
-- =====================
CREATE TABLE patientRecords (
    patientRecordId BIGINT AUTO_INCREMENT PRIMARY KEY,
    reason VARCHAR(255),
    diagnostic VARCHAR(255),
    causal VARCHAR(255),
    date DATE,
    treatment VARCHAR(255),
    marrowRecord VARCHAR(255),
    debit INT,
    note VARCHAR(255),
    credit INT,
    userId BIGINT,
    prescription TEXT,
    FOREIGN KEY (userId) REFERENCES users(userId)
);

-- =====================
-- TREATMENTS
-- =====================
CREATE TABLE treatments (
    treatmentId BIGINT AUTO_INCREMENT PRIMARY KEY,
    patientId BIGINT,
    FOREIGN KEY (patientId) REFERENCES patients(patientId)
);

-- =====================
-- SERVICES & CATEGORIES
-- =====================
CREATE TABLE categoryService (
    categoryServiceId BIGINT AUTO_INCREMENT PRIMARY KEY,
    categoryServiceName VARCHAR(100) NOT NULL
);

CREATE TABLE services (
    serviceId BIGINT AUTO_INCREMENT PRIMARY KEY,
    serviceName VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    marketPrice INT,
    categoryServiceId BIGINT,
    FOREIGN KEY (categoryServiceId) REFERENCES categoryService(categoryServiceId)
);

-- mapping patient record ↔ service
CREATE TABLE patientRecordServiceMap (
    patientRecordServiceMapId BIGINT AUTO_INCREMENT PRIMARY KEY,
    patientRecordId BIGINT,
    serviceId BIGINT,
    status INT,
    FOREIGN KEY (patientRecordId) REFERENCES patientRecords(patientRecordId),
    FOREIGN KEY (serviceId) REFERENCES services(serviceId)
);

-- mapping treatment ↔ service
CREATE TABLE treatmentServiceMap (
    treatmentServiceMapId BIGINT AUTO_INCREMENT PRIMARY KEY,
    treatmentId BIGINT,
    serviceId BIGINT,
    currentPrice INT,
    discount INT,
    startPatientRecordId BIGINT,
    FOREIGN KEY (treatmentId) REFERENCES treatments(treatmentId),
    FOREIGN KEY (serviceId) REFERENCES services(serviceId),
    FOREIGN KEY (startPatientRecordId) REFERENCES patientRecords(patientRecordId)
);

-- =====================
-- RECEIPTS
-- =====================
CREATE TABLE receipts (
    receiptId BIGINT AUTO_INCREMENT PRIMARY KEY,
    patientId BIGINT,
    paymentDate DATE,
    debit INT,
    credit INT,
    status VARCHAR(45),
    FOREIGN KEY (patientId) REFERENCES patients(patientId)
);

-- =====================
-- MATERIALS
-- =====================
CREATE TABLE materials (
    materialId BIGINT AUTO_INCREMENT PRIMARY KEY,
    materialName VARCHAR(100),
    unit VARCHAR(45),
    amount INT,
    price INT
);

CREATE TABLE materialImport (
    materialImportId BIGINT AUTO_INCREMENT PRIMARY KEY,
    materialId BIGINT,
    date DATE,
    amount INT,
    supplyName VARCHAR(100),
    totalPrice INT,
    isDeleted TINYINT DEFAULT 0,
    FOREIGN KEY (materialId) REFERENCES materials(materialId)
);

CREATE TABLE materialExport (
    materialExportId BIGINT AUTO_INCREMENT PRIMARY KEY,
    materialId BIGINT,
    patientRecordId BIGINT,
    amount INT,
    totalPrice INT,
    isDeleted TINYINT DEFAULT 0,
    FOREIGN KEY (materialId) REFERENCES materials(materialId),
    FOREIGN KEY (patientRecordId) REFERENCES patientRecords(patientRecordId)
);

-- =====================
-- LABOS & SPECIMENS
-- =====================
CREATE TABLE labos (
    laboId BIGINT AUTO_INCREMENT PRIMARY KEY,
    laboName VARCHAR(100),
    phone VARCHAR(45),
    isDeleted TINYINT DEFAULT 0
);

CREATE TABLE specimens (
    specimenId BIGINT AUTO_INCREMENT PRIMARY KEY,
    specimenName VARCHAR(100),
    receiveDate DATE,
    deliveryDate DATE,
    amount INT,
    price INT,
    patientRecordId BIGINT,
    laboId BIGINT,
    FOREIGN KEY (patientRecordId) REFERENCES patientRecords(patientRecordId),
    FOREIGN KEY (laboId) REFERENCES labos(laboId)
);

-- =====================
-- TIMEKEEPING
-- =====================
CREATE TABLE timekeeping (
    timekeepingId BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId BIGINT,
    timeCheck DATETIME,
    timeCheckout DATETIME,
    FOREIGN KEY (userId) REFERENCES users(userId)
);
