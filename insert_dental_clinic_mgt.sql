-- USE dental_clinic_mgt;

-- =====================
-- ROLES
-- =====================
INSERT INTO roles (roleName) VALUES
('ADMIN'),
('DOCTOR'),
('LEADER_NURSE'),
('NURSE'),
('RECEPTIONIST');

-- =====================
-- USERS
-- Password đã được BCrypt hash từ chuỗi: "123456"
-- Hash (Spring Security BCryptPasswordEncoder.encode("123456")):
-- $2a$10$Dow1Tj1nWQpH2bQ7Ho/LO7cZ47VIZE8oHv7t8qFn1rYwU01Y0K

INSERT INTO users (username, fullName, password, email, roleId, enabled) VALUES
('admin', 'System Admin', '$2a$12$1ocjeCaSkKzEFPdc3e5IOOC63aXuZfPx5qjJC8/0hxf976xbOXXkm', 'admin@clinic.com', 1, 1),
('doctor1', 'Dr. John Doe', '$2a$12$1ocjeCaSkKzEFPdc3e5IOOC63aXuZfPx5qjJC8/0hxf976xbOXXkm', 'doctor1@clinic.com', 2, 1),
('leadernurse1', 'Leader Nurse Mary', '$2a$12$1ocjeCaSkKzEFPdc3e5IOOC63aXuZfPx5qjJC8/0hxf976xbOXXkm', 'leadernurse1@clinic.com', 3, 1),
('nurse1', 'Nurse Anna', '$2a$12$1ocjeCaSkKzEFPdc3e5IOOC63aXuZfPx5qjJC8/0hxf976xbOXXkm', 'nurse1@clinic.com', 4, 1),
('reception1', 'Receptionist Jane', '$2a$12$1ocjeCaSkKzEFPdc3e5IOOC63aXuZfPx5qjJC8/0hxf976xbOXXkm', 'reception1@clinic.com', 5, 1);


-- Permissions cho User module
INSERT INTO permission (permissionId, permissionName) VALUES
(1, 'USER_VIEW'),
(2, 'USER_CREATE'),
(3, 'USER_UPDATE'),
(4, 'USER_DELETE'),
(5, 'PATIENT_VIEW'),
(6, 'PATIENT_CREATE'),
(7, 'PATIENT_UPDATE'),
(8, 'PATIENT_VIEW_DETAIL'),
(9, 'CATEGORYSERVICE_VIEW'),
(10, 'CATEGORYSERVICE_CREATE'),
(11, 'CATEGORYSERVICE_UPDATE'),
(12, 'SERVICE_VIEW'),
(13, 'SERVICE_CREATE'),
(14, 'SERVICE_UPDATE');

-- Map ADMIN -> full quyền
INSERT INTO rolePermissionMap (roleId, permissionId) VALUES
(1, 1), -- ADMIN -> USER_VIEW
(1, 2), -- ADMIN -> USER_CREATE
(1, 3), -- ADMIN -> USER_UPDATE
(1, 4), -- ADMIN -> USER_DELETE
(1, 5), -- ADMIN -> PATIENT_VIEW
(1, 6), -- ADMIN -> PATIENT_CREATE
(1, 7), -- ADMIN -> PATIENT_UPDATE
(1, 8), -- ADMIN -> PATIENT_UPDATE
(1, 9), -- ADMIN -> CATEGORYSERVICE_VIEW
(1, 10), -- ADMIN -> CATEGORYSERVICE_CREATE
(1, 11), -- ADMIN -> CATEGORYSERVICE_UPDATE
(1, 12), -- ADMIN -> SERVICE_VIEW
(1, 13), -- ADMIN -> SERVICE_CREATE
(1, 14), -- ADMIN -> SERVICE_UPDATE

(2, 5), -- DOCTOR -> PATIENT_VIEW
(2, 9), -- DOCTOR -> CATEGORYSERVICE_VIEW
(2, 12), -- DOCTOR -> SERVICE_VIEW

(3, 5), -- LEADER_NURSE -> PATIENT_VIEW
(3, 9), -- LEADER_NURSE -> CATEGORYSERVICE_VIEW
(3, 12), -- LEADER_NURSE -> SERVICE_VIEW

(4, 5), -- NURSE -> PATIENT_VIEW
(4, 9), -- NURSE -> CATEGORYSERVICE_VIEW
(4, 12), -- NURSE -> SERVICE_VIEW

(5, 5), -- RECEPTIONIST -> PATIENT_VIEW
(5, 6), -- RECEPTIONIST -> PATIENT_CREATE
(5, 7), -- RECEPTIONIST -> PATIENT_UPDATE
(5, 8), -- RECEPTIONIST -> PATIENT_UPDATE
(5, 9), -- RECEPTIONIST -> CATEGORYSERVICE_VIEW
(5, 12); -- RECEPTIONIST -> SERVICE_VIEW

INSERT INTO patients (patientName, birthdate, gender, address, phone, email, bodyPrehistory, teethPrehistory, status, isDeleted) VALUES
('Nguyen Van A', '1990-01-10', 'MALE', 'Hanoi', '0901111111', 'vana@gmail.com', 'None', 'Cavity', 'NOT_TREATMENT', 0),
('Tran Thi B', '1992-05-15', 'FEMALE', 'HCMC', '0902222222', 'thib@gmail.com', 'Asthma', 'Gum Disease', 'TREATING', 0),
('Le Van C', '1985-09-21', 'MALE', 'Da Nang', '0903333333', 'vanc@gmail.com', 'Diabetes', 'Tooth Loss', 'COMPLETED', 0),
('Pham Thi D', '1998-11-02', 'FEMALE', 'Hai Phong', '0904444444', 'thid@gmail.com', 'None', 'Cavity', 'NOT_TREATMENT', 0),
('Hoang Van E', '1995-07-18', 'MALE', 'Can Tho', '0905555555', 'vane@gmail.com', 'None', 'Gum Disease', 'TREATING', 0),
('Nguyen Thi F', '2000-03-25', 'FEMALE', 'Hanoi', '0906666666', 'thif@gmail.com', 'Hypertension', 'None', 'NOT_TREATMENT', 0),
('Tran Van G', '1987-12-30', 'MALE', 'HCMC', '0907777777', 'vang@gmail.com', 'None', 'Cavity', 'COMPLETED', 0),
('Do Thi H', '1993-08-08', 'FEMALE', 'Da Nang', '0908888888', 'thih@gmail.com', 'Allergy', 'Tooth Loss', 'TREATING', 0),
('Bui Van I', '1989-06-12', 'MALE', 'Hue', '0909999999', 'vani@gmail.com', 'Asthma', 'None', 'NOT_TREATMENT', 0),
('Ngo Thi J', '1997-04-05', 'FEMALE', 'Hanoi', '0910000000', 'thij@gmail.com', 'None', 'Cavity', 'TREATING', 0),
('Pham Van K', '1994-02-14', 'MALE', 'HCMC', '0911111111', 'vank@gmail.com', 'None', 'Gum Disease', 'COMPLETED', 0),
('Tran Thi L', '1988-10-09', 'FEMALE', 'Da Nang', '0912222222', 'thil@gmail.com', 'Diabetes', 'None', 'NOT_TREATMENT', 0),
('Le Van M', '1991-09-17', 'MALE', 'Hue', '0913333333', 'vanm@gmail.com', 'None', 'Cavity', 'TREATING', 0),
('Nguyen Thi N', '1996-12-11', 'FEMALE', 'Hanoi', '0914444444', 'thin@gmail.com', 'Hypertension', 'Tooth Loss', 'NOT_TREATMENT', 0),
('Hoang Van O', '1984-03-03', 'MALE', 'HCMC', '0915555555', 'vano@gmail.com', 'None', 'Cavity', 'COMPLETED', 0),
('Vu Thi P', '1999-07-22', 'FEMALE', 'Da Nang', '0916666666', 'thip@gmail.com', 'Asthma', 'Gum Disease', 'TREATING', 0),
('Bui Van Q', '1993-01-29', 'MALE', 'Can Tho', '0917777777', 'vanq@gmail.com', 'None', 'None', 'NOT_TREATMENT', 0),
('Ngo Thi R', '1990-05-06', 'FEMALE', 'Hue', '0918888888', 'thir@gmail.com', 'Allergy', 'Cavity', 'TREATING', 0),
('Tran Van S', '1986-11-19', 'MALE', 'Hanoi', '0919999999', 'vans@gmail.com', 'None', 'Gum Disease', 'COMPLETED', 0),
('Nguyen Thi T', '1998-02-28', 'FEMALE', 'HCMC', '0920000000', 'thit@gmail.com', 'None', 'Cavity', 'NOT_TREATMENT', 0);


INSERT INTO categoryService (categoryServiceName) VALUES
('General Examination'),
('Cavity Treatment'),
('Tooth Extraction'),
('Orthodontics'),
('Teeth Whitening'),
('Periodontics'),
('Prosthodontics');

INSERT INTO services (serviceName, price, marketPrice, categoryServiceId) VALUES
('General Dental Check-up',       100000, 120000, 1),
('Aesthetic Tooth Filling',       300000, 350000, 2),
('Root Canal Treatment',          800000, 900000, 2),
('Milk Tooth Extraction',         150000, 200000, 3),
('Wisdom Tooth Extraction',      1200000, 1500000, 3),
('Metal Braces Orthodontics',   25000000, 28000000, 4),
('Invisalign Orthodontics',     80000000, 85000000, 4),
('In-Clinic Teeth Whitening',    2000000, 2500000, 5),
('Teeth Scaling & Polishing',     500000, 600000, 6),
('Dental Implant',              15000000, 16000000, 7),
('Dental Crown (Porcelain)',     3000000, 3500000, 7);





