-- Creación de las tablas de autorización
/*
  creado_por VARCHAR(45) NOT NULL,
  fecha_creado TIMESTAMP NOT NULL,
  modificado_por VARCHAR(45) NOT NULL,
  fecha_modificado TIMESTAMP NOT NULL
*/
CREATE TABLE auth_rol (
  nombre VARCHAR(30) NOT NULL PRIMARY KEY
);

CREATE TABLE auth_usuario(
  id BIGINT(20) NOT NULL PRIMARY key AUTO_INCREMENT,
  rol_nombre VARCHAR(30) NOT NULL,
  login VARCHAR (45) NOT NULL,
  clave VARCHAR (250) NOT NULL,
  correo VARCHAR (250) NOT NULL,
  habilitado BIT(1) NOT NULL,
  reset_key VARCHAR(150),
  reset_date TIMESTAMP,
  CONSTRAINT `fk_rol_usuario`
  FOREIGN KEY (rol_nombre) REFERENCES auth_rol (nombre)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
);

CREATE UNIQUE INDEX usuario_login ON auth_usuario(login);
CREATE UNIQUE INDEX usuario_correo ON auth_usuario(correo);

INSERT INTO auth_rol(nombre) VALUES ('ROLE_ADMIN');

/*Usuario admin: admin*/
INSERT INTO auth_usuario(id, login, clave, correo, habilitado, rol_nombre)
    VALUE
  (1, 'admin', '$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC', 'admin@admin.com', TRUE, 'ROLE_ADMIN');