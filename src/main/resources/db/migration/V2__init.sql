-- Creación de las tablas de autorización
/*
  creado_por VARCHAR(45) NOT NULL,
  fecha_creado TIMESTAMP NOT NULL,
  modificado_por VARCHAR(45) NOT NULL,
  fecha_modificado TIMESTAMP NOT NULL
*/
CREATE TABLE auth_modulo (
  id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(100) NOT NULL,
  icono VARCHAR(45) NOT NULL,
  orden INTEGER NOT NULL
);

CREATE TABLE auth_submodulo (
  id VARCHAR(45) NOT NULL PRIMARY KEY,
  modulo_id BIGINT(20) NOT NULL,
  nombre VARCHAR(100) NOT NULL,
  icono VARCHAR(50) NOT NULL,
  enlace VARCHAR(150) NOT NULL,
  orden INTEGER NOT NULL
);

CREATE TABLE auth_permiso(
  rol_nombre VARCHAR(30) NOT NULL,
  submodulo_id VARCHAR(45) NOT NULL,
  CONSTRAINT `fk_rol_permiso`
  FOREIGN KEY (rol_nombre) REFERENCES auth_rol (nombre)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_submodulo_permiso`
  FOREIGN KEY (submodulo_id) REFERENCES auth_submodulo (id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  PRIMARY KEY (rol_nombre, submodulo_id)
);

INSERT INTO auth_modulo (id, nombre, icono, orden) VALUES
  (1, 'Autorización', 'lock', 1);

INSERT INTO auth_submodulo(id, modulo_id, nombre, icono, enlace, orden) VALUES
  ('AUTH_ROLES', 1, 'Roles', 'user-circle', '/auth/roles', 1),
  ('AUTH_USUARIOS', 1, 'Usuarios', 'user', '/auth/usuarios', 2),
  ('AUTH_PERMISOS', 1, 'Permisos', 'shield', '/auth/permisos', 3);

INSERT INTO auth_permiso(rol_nombre, submodulo_id) VALUES
  ('ROLE_ADMIN', 'AUTH_ROLES'),
  ('ROLE_ADMIN', 'AUTH_USUARIOS'),
  ('ROLE_ADMIN', 'AUTH_PERMISOS')
;

CREATE TABLE auth_enlace (
  id BIGINT(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  nombre VARCHAR(150) NOT NULL,
  submodulo_id VARCHAR(45) NOT NULL,
  descripcion VARCHAR(250) NOT NULL,
  metodo VARCHAR(10) NOT NULL,
  FOREIGN KEY (submodulo_id) REFERENCES auth_submodulo (id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT
);

CREATE UNIQUE INDEX auth_enlace_nombre ON auth_enlace(nombre);

INSERT INTO auth_enlace(id, nombre, submodulo_id, descripcion, metodo) VALUES
  (1, '/auth/roles', 'AUTH_ROLES', 'Leer roles', 'GET'),
  (2, '/auth/roles-nuevo', 'AUTH_ROLES', 'Crear roles', 'ANY'),
  (3, '/auth/auth/roles-editar/**', 'AUTH_ROLES', 'Editar roles', 'ANY'),
  (4, '/auth/auth/roles-eliminar/**', 'AUTH_ROLES', 'Eliminar roles', 'POST'),

  (5, '/auth/usuarios', 'AUTH_USUARIOS', 'Leer usuarios', 'GET'),
  (6, '/api/auth/usuarios', 'AUTH_USUARIOS', 'Leer usuarios - API', 'GET'),
  (7, '/auth/usuarios-nuevo', 'AUTH_USUARIOS', 'Crear usuarios', 'ANY'),
  (8, '/auth/auth/usuarios-editar/**', 'AUTH_USUARIOS', 'Editar usuarios', 'ANY'),
  (9, '/auth/auth/usuarios-eliminar/**', 'AUTH_USUARIOS', 'Eliminar usuarios', 'POST');

CREATE TABLE auth_permiso_enlace(
  rol_nombre VARCHAR(30) NOT NULL,
  enlace_id BIGINT(20) NOT NULL,
  CONSTRAINT `fk_rol_permiso_enlace`
  FOREIGN KEY (rol_nombre) REFERENCES auth_rol (nombre)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  CONSTRAINT `fk_enlace_permiso_enlace`
  FOREIGN KEY (enlace_id) REFERENCES auth_enlace (id)
    ON DELETE CASCADE
    ON UPDATE RESTRICT,
  PRIMARY KEY (rol_nombre, enlace_id)
)