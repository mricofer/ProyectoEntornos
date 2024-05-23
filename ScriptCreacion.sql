CREATE OR REPLACE TABLE empresas (
                          id_empresa INT AUTO_INCREMENT PRIMARY KEY,
                          codigo_empresa VARCHAR(20) NOT NULL UNIQUE,
                          cif CHAR(9) NOT NULL UNIQUE,
                          nombre_empresa VARCHAR(100),
                          direccion VARCHAR(255),
                          cp VARCHAR(10),
                          localidad VARCHAR(100),
                          tipo_jornada ENUM('Completa', 'Parcial'),
                          modalidad ENUM('Presencial', 'Semipresencial', 'Distancia'),
                          mail VARCHAR(100),
                          dni_responsable CHAR(9),
                          nombre_responsable VARCHAR(100),
                          apellidos_responsable VARCHAR(100),
                          dni_tutor_laboral CHAR(9),
                          nombre_tutor_laboral VARCHAR(100),
                          apellidos_tutor_laboral VARCHAR(100),
                          telefono_tutor_laboral VARCHAR(20)
);


CREATE OR REPLACE TABLE alumnos (
                         id_alumno INT AUTO_INCREMENT PRIMARY KEY,
                         codigo_alumno VARCHAR(20) NOT NULL UNIQUE,
                         dni CHAR(9),
                         nombre VARCHAR(100) NOT NULL,
                         apellidos VARCHAR(100) NOT NULL,
                         fecha_nacimiento DATE NOT NULL,
                         email VARCHAR(100),
                         telefono VARCHAR(20)
);


CREATE OR REPLACE TABLE tutores (
                         id_tutor INT AUTO_INCREMENT PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         apellidos VARCHAR(100) NOT NULL,
                         email VARCHAR(100),
                         telefono VARCHAR(20),
                         departamento VARCHAR(50)
);


CREATE OR REPLACE TABLE asignaciones (
                              id_asignacion INT AUTO_INCREMENT PRIMARY KEY,
                              id_alumno INT NOT NULL,
                              id_empresa INT NOT NULL,
                              fecha_inicio DATE NOT NULL,
                              id_tutor INT NOT NULL,
                              fecha_fin DATE,
                              FOREIGN KEY (id_alumno) REFERENCES alumnos(id_alumno),
                              FOREIGN KEY (id_empresa) REFERENCES empresas(id_empresa),
                              FOREIGN KEY (id_tutor) REFERENCES tutores(id_tutor)
);



