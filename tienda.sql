DROP DATABASE IF EXISTS tienda;
CREATE DATABASE tienda;
USE tienda;

CREATE TABLE productos (
id INT PRIMARY KEY AUTO_INCREMENT,
nombre VARCHAR(100) NOT NULL,
precio DECIMAL (10,2) NOT NULL,
existencia INT NOT NULL
);

CREATE TABLE roles (
id_rol INT PRIMARY KEY AUTO_INCREMENT,
nombre_rol VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO roles (nombre_rol) VALUES ('Administrador'), ('Empleado');

CREATE TABLE usuarios (
id_usuario INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(50) NOT NULL UNIQUE,
password_user VARCHAR(255) NOT NULL,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
email VARCHAR(100) NOT NULL UNIQUE,
user_status VARCHAR(20) DEFAULT 'Active',
id_rol INT NOT NULL,
fecha_creacion DATE NOT NULL,
telefono VARBINARY(255) NULL,
direccion VARBINARY(255) NULL,
salario VARBINARY(255) NULL,
edad INT NULL,
genero VARCHAR(20) NULL,
ciudad VARCHAR(50) NULL,
codigo_postal VARCHAR(10) NULL,
CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

INSERT INTO usuarios (
    username, password_user, first_name, last_name, email, user_status, id_rol, fecha_creacion,
    telefono, direccion, salario,
    edad, genero, ciudad, codigo_postal
) 
VALUES (
    'Robin',
    sha2('ClaveRobin_2026', 256),
    'Robin',
    'Flores',
    'Robin@tienda.com',
    'Active',
    1,
    CURDATE(),
    AES_ENCRYPT('4451234567', 'Secreto'),
    AES_ENCRYPT('Av. America 120', 'Secreto'),
    AES_ENCRYPT('1500.00', 'Secreto'),
    28,
    'Femenino',
    'Guanajuato',
    '38800'
);

INSERT INTO usuarios (
    username, password_user, first_name, last_name, email, user_status, id_rol, fecha_creacion,
    telefono, direccion, salario,
    edad, genero, ciudad, codigo_postal
) 
VALUES (
    'Jose',
    sha2('ClaveJose_2026', 256),
    'Jose',
    'Lopez',
    'JoseJose@tienda.com',
    'Active',
    2,
    CURDATE(),
    AES_ENCRYPT('4450987654', 'Secreto'),
    AES_ENCRYPT('Calle Pedro Guzman 153', 'Secreto'),
    AES_ENCRYPT('1600.00', 'Secreto'),
    35,
    'Masculino',
    'Guerrero',
    '72000'
);

INSERT INTO usuarios (
    username, password_user, first_name, last_name, email, user_status, id_rol, fecha_creacion,
    telefono, direccion, salario,
    edad, genero, ciudad, codigo_postal
) 
VALUES (
    'Bartolo',
    sha2('ClaveBartolito_2026', 256),
    'Bartolo',
    'Gallardo',
    'ElGallo@tienda.com',
    'Active',
    2,
    CURDATE(),
    AES_ENCRYPT('4451633348', 'Secreto'),
    AES_ENCRYPT('Calle Abasolo 56', 'Secreto'),
    AES_ENCRYPT('2000.00', 'Secreto'),
    25,
    'Masculino',
    'Durango',
    '09877'
);

INSERT INTO usuarios (
    username, password_user, first_name, last_name, email, user_status, id_rol, fecha_creacion,
    telefono, direccion, salario,
    edad, genero, ciudad, codigo_postal
) 
VALUES (
    'Francisco',
    sha2('ClaveFranky_2026', 256),
    'Francisco',
    'Gutierrez',
    'SuperFranky@tienda.com',
    'Active',
    1,
    CURDATE(),
    AES_ENCRYPT('4453501567', 'Secreto'),
    AES_ENCRYPT('Calle Independencia 788', 'Secreto'),
    AES_ENCRYPT('1950.00', 'Secreto'),
    36,
    'Masculino',
    'Michoacan',
    '78600'
);

INSERT INTO usuarios (
    username, password_user, first_name, last_name, email, user_status, id_rol, fecha_creacion,
    telefono, direccion, salario,
    edad, genero, ciudad, codigo_postal
) 
VALUES (
    'Miriam',
    sha2('ClaveMiriam_2026', 256),
    'Miriam',
    'Rodriguez',
    'Miri@tienda.com',
    'Active',
    2,
    CURDATE(),
    AES_ENCRYPT('4451095056', 'Secreto'),
    AES_ENCRYPT('Calle Ortega 34', 'Secreto'),
    AES_ENCRYPT('3400.00', 'Secreto'),
    32,
    'Femenino',
    'Guanajuato',
    '39000'
);

CREATE ROLE 'rol_admin';
CREATE ROLE 'rol_Empleado';


GRANT ALL PRIVILEGES ON tienda.* TO 'rol_admin';

GRANT SELECT ON tienda.productos TO 'rol_Empleado';

CREATE USER 'Robin'@'localhost' IDENTIFIED BY 'ClaveRobin_2026';
CREATE USER 'Jose'@'localhost' IDENTIFIED BY 'ClaveJose_2026';
CREATE USER 'Bartolo'@'localhost' IDENTIFIED BY 'ClaveBartolito_2026';
CREATE USER 'Francisco'@'localhost' IDENTIFIED BY 'ClaveFranky_2026';
CREATE USER 'Miriam'@'localhost' IDENTIFIED BY 'ClaveMiriam_2026';

GRANT 'rol_admin' TO 'Robin'@'localhost';
GRANT 'rol_Empleado' TO 'Jose'@'localhost';
GRANT 'rol_Empleado' TO 'Bartolo'@'localhost';
GRANT 'rol_admin' TO 'Francisco'@'localhost';
GRANT 'rol_Empleado' TO 'Miriam'@'localhost';