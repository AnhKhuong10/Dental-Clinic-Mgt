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
