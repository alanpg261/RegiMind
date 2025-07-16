Endpoint Registro y login:
http://localhost:8080/api/usuarios/login   (post)
http://localhost:8080/api/usuarios/registro   (post)


endpoint busqueda patente:
http://localhost:8080/api/patentes/buscar  (post)
    {
  "cip": "A01B",
  "titulo": "semilla",
  "tipoPatente": "invención",
  "fechaInicio": "2023-01-01",
  "fechaFin": "2023-12-31"
    }



    solicitudes faltantes:
    - crear solicitud del solicitante (tabla temporal)

        (solicitud patentes)
        
    - crear el mostrador de patentes solicitadas en dashboard de admin
    - aceptar solicitud de patente y agregarla a la tabla principal







-- Crear base de datos (opcional)
-- CREATE DATABASE sistema_patentes;
-- USE sistema_patentes;
-- Tabla Usuario
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipoDocumento VARCHAR(50) NOT NULL,
    NoDocumento VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    tipoUsuario VARCHAR(50) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    celular VARCHAR(20),
    fechaNacimiento DATE NOT NULL
);

CREATE TABLE patentes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    expediente VARCHAR(100) NOT NULL UNIQUE,
    tipoPatente VARCHAR(100) NOT NULL,
    titulo VARCHAR(500) NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(50) NOT NULL,
    id_solicitante INT NOT NULL,
    id_inventor INT NOT NULL,
    id_apoderado INT NOT NULL,
    cip VARCHAR(100) NOT NULL,
    FOREIGN KEY (id_solicitante) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_inventor) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_apoderado) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla solicitudPatentes
CREATE TABLE solicitudPatentes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    expediente VARCHAR(100) NOT NULL,
    tipoPatente VARCHAR(100) NOT NULL,
    titulo VARCHAR(500) NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(50) NOT NULL,
    id_solicitante INT NOT NULL,
    id_inventor INT NOT NULL,
    id_apoderado INT,
    cip VARCHAR(100),
    FOREIGN KEY (id_solicitante) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_inventor) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_apoderado) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla solicitudProyectos
CREATE TABLE solicitudProyectos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipoProyecto VARCHAR(100) NOT NULL,
    titulo VARCHAR(500) NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(50) NOT NULL,
    id_solicitante INT NOT NULL,
    id_inventor INT NOT NULL,
    FOREIGN KEY (id_solicitante) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_inventor) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla proyectos
CREATE TABLE proyectos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipoProyecto VARCHAR(100) NOT NULL,
    titulo VARCHAR(500) NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(50) NOT NULL,
    id_solicitante INT NOT NULL,
    id_inventor INT NOT NULL,
    FOREIGN KEY (id_solicitante) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_inventor) REFERENCES Usuario(id) ON DELETE CASCADE ON UPDATE CASCADE
);
