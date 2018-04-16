DROP DATABASE IF EXISTS 20161_service_g1Beta;
CREATE DATABASE 20161_service_g1Beta;
USE 20161_service_g1Beta;

---------------------------------------------------

-- Modelo de productos

CREATE TABLE prod_marcas
(
 IdProdMarca serial,
 Nombre varchar(40) unique,
 Fecha_Baja date, 
 Usuario_baja bigint unsigned references pers_personal(IdPersonal),
 PRIMARY KEY (IdProdMarca)
);

INSERT INTO prod_marcas(Nombre) VALUES('Whirlpool');
INSERT INTO prod_marcas(Nombre) VALUES('Phillips');
INSERT INTO prod_marcas(Nombre) VALUES('Samsung');
INSERT INTO prod_marcas(Nombre) VALUES('Patrick');
INSERT INTO prod_marcas(Nombre) VALUES('Sanyo');
INSERT INTO prod_marcas(Nombre) VALUES('Longvie');
INSERT INTO prod_marcas(Nombre) VALUES('Toshiba');

-- Modelo de electrodomesticos


CREATE TABLE elec_electrodomesticos
(
  IdElectro serial,
  idProdMarca bigint unsigned not null,
  Modelo varchar(20),
  Descripcion varchar(60),
  Fecha_Baja date, 
  Usuario_baja bigint unsigned references pers_personal(IdPersonal),
  PRIMARY KEY (IdElectro),
  FOREIGN KEY (IdProdMarca) REFERENCES prod_marcas (IdProdMarca),
  UNIQUE KEY (idProdMarca,Modelo)
);

INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(1,'WFX56DX','Cocina â 4 hornallas â 55 Cm. â A gas');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(1,'WNQ86AB','Lavarropas Carga Frontal â 8 Kg. â Blanco');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(1,'WNQ86AN','Lavarropas Carga Frontal â 8 Kg. â Negro');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(6,'18601XF','Cocina Inox.-Rej.fund-TT-Enc. a 1 mano');
INSERT INTO elec_electrodomesticos(idProdMarca,Modelo,Descripcion) VALUES(3,'WF1702WECU','AEGIS BUBBLE lavarropas con Eco Bubble, 7 kg');

---------------------------------------------------

-- Modelo de personal


CREATE TABLE pers_roles
(
  IdRol serial,
  Descripcion varchar(20),
  PRIMARY KEY (IdRol)
);

INSERT INTO pers_roles(Descripcion) VALUES('Superusuario');
INSERT INTO pers_roles(Descripcion) VALUES('Administrativo');
INSERT INTO pers_roles(Descripcion) VALUES('Tecnico');


CREATE TABLE pers_personal 
(
  IdPersonal serial,
  IdRol bigint unsigned not null,
  Nombre varchar(20),
  Apellido varchar(20),
  Usuario varchar(20) unique,
  pass varchar(32),
  Fecha_Baja date, 
  Usuario_baja bigint unsigned references pers_personal(IdPersonal),
  PRIMARY KEY (IdPersonal),
  FOREIGN KEY (IdRol) REFERENCES pers_roles (IdRol),
  UNIQUE KEY (Usuario)
);

INSERT INTO pers_personal (IdRol,Nombre,Apellido,Usuario,Pass) 
VALUES(1,'Dario','Rick','admin',MD5('admin'));

---------------------------------------------------

-- Modelo de clientes

CREATE TABLE ot_zonas (
	idZonaPosible serial,
    Nombre VARCHAR(60) not null,
    Precio integer not null,
    PRIMARY KEY (idZonaPosible)
);

INSERT INTO ot_zonas(Nombre, Precio) VALUES('Zona 1', 100); 		-- 1
INSERT INTO ot_zonas(Nombre, Precio) VALUES('Zona 2', 150); 		-- 2
INSERT INTO ot_zonas(Nombre, Precio) VALUES('Zona 3', 200); 		-- 3
INSERT INTO ot_zonas(Nombre, Precio) VALUES('Zona 4', 250); 		-- 4
INSERT INTO ot_zonas(Nombre, Precio) VALUES('Zona 5', 300);			-- 5

create table localidades (
codigoPostal integer,
nombre varchar(20),
zonaDeEnvio bigint unsigned,
primary key (codigoPostal),
foreign key (zonaDeEnvio) references ot_zonas(idZonaPosible)
);

insert into localidades values ('1667', 'Tortuguitas', 1);

CREATE TABLE cli_clientes
(
  IdCliente serial,
  Nombre varchar(20) not null,
  Apellido varchar(20) not null,
  Direccion varchar(20),
  -- Localidad varchar(6),
  Provincia varchar(20),
  Codigo_postal integer,
  Telefono varchar(20),
  Email varchar(30),
  Fecha_Baja date, 
  Usuario_baja bigint unsigned references personal(IdPersonal),
  PRIMARY KEY (IdCliente),
  foreign key (Codigo_postal) references localidades (codigoPostal)
);

INSERT INTO cli_clientes (Nombre,Apellido,Direccion,Provincia,Codigo_postal,Telefono,Email) 
VALUES('Dario','Rick','Eva Peron 710','Buenos Aires', 1667,'1566060529','dario_rick@outlook.com');


-- Modelo de envios

create table env_estados_vehiculos (
idEstado serial,
nombreEstado varchar (20),
primary key (idEstado)
);

insert into env_estados_vehiculos (nombreEstado) values ("operativo");
insert into env_estados_vehiculos (nombreEstado) values ("en reparaciÃ³n");

CREATE TABLE env_vehiculos
(
  Patente varchar(10) not null unique,
  Marca varchar(20) not null,
  Modelo varchar(20) not null,
  CapacidadCarga bigint not null,
  FechaVencimientoVTV date not null,
  Oblea boolean,
  FechaVencimientoOblea date,
  Estado bigint unsigned not null,
  UsuarioBaja bigint unsigned,
  PRIMARY KEY (Patente),
  foreign key (Estado) references env_estados_vehiculos(idEstado),
  foreign key (UsuarioBaja) references pers_personal (IdPersonal)
); 

INSERT INTO env_vehiculos (Patente, Marca, Modelo,  CapacidadCarga, FechaVencimientoVTV, Oblea, Estado) 
VALUES('WCG680', 'Mercedes Benz', '1114', 12, '2016-06-01', false, 1);

CREATE TABLE env_fleteros
(
 IdFletero serial,
 Nombre varchar(20),
 Apellido varchar(20),
 Celular varchar(20),
 RegistroConducir varchar(12) unique,
 FechaVencimientoRegistro date,
 IdVehiculo varchar(10) not null,
 Fecha_Baja date, 
 Usuario_baja bigint unsigned references personal(IdPersonal),
 PRIMARY KEY (IdFletero),
 FOREIGN KEY (IdVehiculo) REFERENCES env_vehiculos (Patente)
);

INSERT INTO env_fleteros(Nombre,Apellido,Celular, RegistroConducir, FechaVencimientoRegistro,idVehiculo) 
VALUES('Juan', 'Araya', '1121223322', '00003564723', '2017-02-01', 'WCG680');
INSERT INTO env_fleteros(Nombre,Apellido,Celular, RegistroConducir, FechaVencimientoRegistro,idVehiculo) 
VALUES('Jorge', 'Porcel', '1123456678', '00007795222', '2016-11-16','WCG680');


CREATE TABLE env_costos_envios
(
 IdCosto serial,
 Codigo_postal integer not null,
 Costo_envio integer not null,
 PRIMARY KEY (idCosto),
 UNIQUE KEY (codigo_postal)
);

INSERT INTO env_costos_envios(Codigo_postal,Costo_envio) 
VALUES(1613,350);

-------------------------------------------------

-- Modelo proveedores

CREATE TABLE prov_proveedores
(
 IdProveedor serial,
 Nombre varchar(20),
 Contacto varchar (20),
 Cuit varchar(11) unique,
 Telefono varchar(20),
 Mail varchar(30),
 Fecha_Baja date,
 Usuario_baja bigint unsigned references pers_personal(idPersonal),
 PRIMARY KEY (idProveedor)
);

INSERT INTO prov_proveedores(Nombre,Cuit,Mail) 
VALUES('Proveedor1','20111111114','proveedor@proveedor.com');

-- Modelo de productos

CREATE TABLE prod_piezas
(
 idProdPieza serial,
 idProdMarca bigint unsigned not null,
 idUnico varchar (8) not null unique,
 Descripcion varchar(40),
 Precio_venta decimal(10,4), -- Precio de venta de ElectroR al publico
 bajo_stock smallint not null, -- Indica el mÃ­nimo stock que puede tener una pieza antes de que salte la alerta
 Fecha_Baja date,
 Usuario_baja bigint unsigned references pers_personal(idPersonal),
 PRIMARY KEY (idProdPieza),
 FOREIGN KEY (idProdMarca) REFERENCES prod_marcas(idProdMarca) 
);

INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(1,'ATM3453I','Lampara 40 watts', 50, 10);

INSERT INTO prod_piezas(idProdMarca,idUnico,Descripcion,Precio_venta,bajo_stock) 
VALUES(1,'ATM34536','Lampara 60 watts',60, 10);

-- Fin modelo productos

CREATE TABLE prov_provee_marca
(
 idProvXMarca serial,
 idProveedor bigint unsigned not null,
 idProdMarca bigint unsigned not null,
 PRIMARY KEY (idProvXMarca),
 FOREIGN KEY (idProveedor) REFERENCES prov_proveedores(idProveedor),
 FOREIGN KEY (idProdMarca) REFERENCES prod_marcas(idProdMarca)
);

INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(1,1);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(1,2);
INSERT INTO prov_provee_marca(idProveedor,idProdMarca) 
VALUES(1,6);

CREATE TABLE prov_precios_piezas
(
 idProvXPiezas serial,
 idProveedor bigint unsigned not null,
 idProdPieza bigint unsigned not null,
 precio_compra decimal(10,4) not null,
 PRIMARY KEY (idProvXPiezas),
 FOREIGN KEY (idProveedor) REFERENCES prov_proveedores(idProveedor),
 FOREIGN KEY (idProdPieza) REFERENCES prod_piezas(idProdPieza)
);

INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) 
VALUES(1,1,25);

INSERT INTO prov_precios_piezas(idProveedor,idProdPieza,precio_compra) 
VALUES(1,2,30);


---------------------------------------------------

-- Modelo de productos


CREATE TABLE prod_estados 
(
  idProdEstado serial,
  Estado varchar(20),
  PRIMARY KEY (idProdEstado)
); 

INSERT INTO prod_estados(Estado) VALUES('Disponible');
INSERT INTO prod_estados(Estado) VALUES('Vendido');
INSERT INTO prod_estados(Estado) VALUES('Rota');
INSERT INTO prod_estados(Estado) VALUES('Perdida');

CREATE TABLE prod_piezas_stock -- Cada fila representa un item fÃ­sico a vender o ya vendido
(
  idProdStock serial,
  idProdPieza bigint unsigned not null,
  idProdEstado  bigint unsigned not null,
  PRIMARY KEY (idProdStock),
  FOREIGN KEY (idProdPieza) REFERENCES prod_piezas(idProdPieza),
  FOREIGN KEY (idProdEstado) REFERENCES prod_estados(idProdEstado)
);

INSERT INTO prod_piezas_stock(idProdPieza,idProdEstado) 
VALUES(1,1);
INSERT INTO prod_piezas_stock(idProdPieza,idProdEstado) 
VALUES(1,1);
INSERT INTO prod_piezas_stock(idProdPieza,idProdEstado) 
VALUES(1,1);
INSERT INTO prod_piezas_stock(idProdPieza,idProdEstado) 
VALUES(1,1);

-------------------------------------------------

-- Modelo de solicitudes de compra

CREATE TABLE solc_estados 
(
  idSolcEstado serial,
  Descripcion varchar(40),
  PRIMARY KEY (idSolcEstado)
);

INSERT INTO solc_estados(Descripcion) VALUES('Ingresada');
INSERT INTO solc_estados(Descripcion) VALUES('Enviada');
INSERT INTO solc_estados(Descripcion) VALUES('Procesada');
INSERT INTO solc_estados(Descripcion) VALUES('Cancelada');

CREATE TABLE solc_solicitud_compra 
(
  idSolcCompra serial,
  idSolcEstado bigint unsigned not null,
  idUsuarioAlta bigint unsigned not null,
  idProveedor bigint unsigned not null,
  Fecha_Alta date not null,
  PRIMARY KEY (idSolcCompra),
  FOREIGN KEY (idSolcEstado) REFERENCES solc_estados(idSolcEstado),
  FOREIGN KEY (idUsuarioAlta) REFERENCES pers_personal (idPersonal),
  FOREIGN KEY (idProveedor) REFERENCES prov_proveedores(idProveedor)
);

INSERT INTO solc_solicitud_compra(idSolcEstado,idUsuarioAlta,idProveedor,Fecha_Alta) 
VALUES(1,1,1,now());

CREATE TABLE solc_piezas 
(
  idSolcPiezas serial,
  idSolcCompra bigint unsigned not null,
  idProdPieza  bigint unsigned not null,
  cantidad int not null,
  PRIMARY KEY (idSolcPiezas),
  FOREIGN KEY (idSolcCompra) REFERENCES solc_solicitud_compra(idSolcCompra),
  FOREIGN KEY (idProdPieza) REFERENCES prod_piezas(idProdPieza)
);

INSERT INTO solc_piezas(idSolcCompra,idProdPieza,cantidad) 
VALUES(1,1,2);
INSERT INTO solc_piezas(idSolcCompra,idProdPieza,cantidad) 
VALUES(1,2,1);


-------------------------------------------------

-- Modelo de ordenes de trabajo


CREATE TABLE ot_estados ( -- Posibles estados de una orden de trabajo
    idEstadoPosible serial,
    estado varchar(20) NOT NULL,
    PRIMARY KEY (idEstadoPosible)
);
INSERT INTO ot_estados(estado) VALUES('Ingresada'); -- 1
INSERT INTO ot_estados(estado) VALUES('Presupuestada'); -- 2
INSERT INTO ot_estados(estado) VALUES('Aprobada'); -- 3
INSERT INTO ot_estados(estado) VALUES('Desaprobada'); -- 4
INSERT INTO ot_estados(estado) VALUES('En reparaciÃ³n'); -- 5
INSERT INTO ot_estados(estado) VALUES('En espera de piezas'); -- 6
INSERT INTO ot_estados(estado) VALUES('Reparada'); -- 7
INSERT INTO ot_estados(estado) VALUES('Irreparable'); -- 8
INSERT INTO ot_estados(estado) VALUES('Despachada'); -- 9
INSERT INTO ot_estados(estado) VALUES('Entregada'); -- 10

CREATE TABLE ot_ordenes_trabajo (
    idOT serial,
    idCliente bigint unsigned not null,
    idElectro bigint unsigned not null,
    descripcion VARCHAR(60) not null,
    idUsuarioAlta bigint unsigned not null,
    idTecnicoAsoc bigint unsigned,
    esDelivery boolean not null,
    vencPresup date,
    fechaReparado date,
    expiraGarantia date,
    idOtAsociada bigint unsigned,
	estado_orden bigint unsigned not null,
    manoDeObra decimal(10,2),
    costoDeEnvio decimal(10,2),
    PRIMARY KEY (idOT),
    FOREIGN KEY (idCliente) REFERENCES cli_clientes (idCliente),
    FOREIGN KEY (idUsuarioAlta) REFERENCES pers_personal (idPersonal),
    FOREIGN KEY (idTecnicoAsoc) REFERENCES pers_personal (idPersonal),
    FOREIGN KEY (idElectro) REFERENCES elec_electrodomesticos (idElectro),
    FOREIGN KEY (estado_orden) REFERENCES ot_estados(idEstadoPosible)
);

create table env_envios (
id serial,
idOT bigint unsigned,
idFletero bigint unsigned,
primary key (id),
foreign key (idOT) references ot_ordenes_trabajo(idOT),
foreign key (idFletero) references env_fleteros(idFletero)
);

CREATE TABLE ot_piezas_presupuestadas (
	idOTPiezaPresup serial,
    idOT bigint unsigned not null, -- ot asociada
    idProdPieza bigint unsigned,
    costoPresupuestada double(5,2), -- precio al momento de hacer el presupuesto, este puede variar
    PRIMARY KEY (idOTPiezaPresup),
    FOREIGN KEY (idOT) REFERENCES ot_ordenes_trabajo (idOT),
    FOREIGN KEY (idProdPieza) REFERENCES prod_piezas (idProdPieza)
);

CREATE TABLE ot_piezas_usadas (
	idOTPiezaStock serial,
	idProdStock bigint unsigned,
    idOT bigint unsigned not null,
    PRIMARY KEY (idOTPiezaStock),
    FOREIGN KEY (idProdStock) REFERENCES prod_piezas_stock (idProdStock),
	FOREIGN KEY (idOT) REFERENCES ot_ordenes_trabajo (idOT)
);



-- Modelo contable

CREATE TABLE ot_estados_historico -- Guarda histÃ³rico de ot_ordenes_trabajo
(
  idOt_estado serial,
  idOT bigint unsigned not null,
  estado_orden bigint unsigned not null,
  Fecha_Alta date not null, 
  Fecha_Baja date,
  PRIMARY KEY (idot_estado),
  FOREIGN KEY (idOT) REFERENCES ot_ordenes_trabajo (idOT),
  FOREIGN KEY (estado_orden) REFERENCES ot_estados(idEstadoPosible)
);

-------------------------------------------------

-- Tablas accesorias

CREATE TABLE acc_parametros
(
    parametro VARCHAR(40),
    parm_valor1 VARCHAR(40),
    parm_valor2 VARCHAR(40)
);

INSERT INTO acc_parametros(parametro,parm_valor1)
VALUES ('MAIL_ADMIN','jony.alv86@gmail.com');

INSERT INTO acc_parametros(parametro,parm_valor1)
VALUES ('HASHCODE_PASSWORD','');


CREATE TABLE acc_alertas
(
    idAlerta serial,
    fecha_Alerta date,
    varchar_1 VARCHAR(80),
    varchar_2 VARCHAR(80),
    Fecha_Baja date,
    PRIMARY KEY (idAlerta)
);

-------------------------------------------------

-- Vistas

-- Vista que trae todas las piezas y sus precios de compra y venta
CREATE VIEW view_Piezas AS
    SELECT 
        piez.idProdPieza, 
        marc.Nombre, 
        piez.idUnico, 
        piez.Descripcion, 
        piez.Precio_venta, 
        piez.bajo_stock,
       -- prov.Nombre,
        ppp.precio_compra
    FROM
        prod_piezas piez
	JOIN prod_marcas marc
		ON marc.IdProdMarca = piez.idProdPieza
	LEFT JOIN prov_precios_piezas ppp
		ON ppp.idProdPieza = piez.idProdPieza
	JOIN prov_proveedores prov
		ON prov.IdProveedor = ppp.idProveedor
	WHERE marc.Fecha_Baja IS NULL
    AND piez.Fecha_Baja IS NULL;

-------------------------------------------------

-- Stored procedures


-- SP Personal

DELIMITER $$
CREATE FUNCTION obtenerIdRol(Descripcion Varchar(20)) 
  RETURNS BIGINT UNSIGNED
BEGIN
  DECLARE IdRol BIGINT;

    SELECT pers.IdRol 
    INTO IdRol 
    FROM pers_roles pers 
    WHERE pers.Descripcion = Descripcion;
  
  RETURN IdRol;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE eliminarPersonal (
IN IdPersonal  bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarPersonal() - Error General';

  SET retCode = 0;
    SET descErr = 'eliminarPersonal() - OK';

  SELECT EXISTS(
        SELECT * FROM pers_personal pers
        WHERE pers.IdPersonal = IdPersonal
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    UPDATE pers_personal pers
    SET pers.Usuario_baja = Usuario_baja,
    Fecha_Baja = now()
    WHERE pers.IdPersonal = IdPersonal;
  ELSE
    SET retCode = 2;
    SET descErr = 'eliminarPersonal() - El usuario no existe';
  END IF;

END $$
DELIMITER ;

-------------------------------------------------

-- SP Clientes

DELIMITER $$
CREATE PROCEDURE insertarCliente (
IN Nombre varchar(20), 
IN Apellido varchar(20), 
IN Direccion varchar(20), 
-- IN Localidad varchar(20), 
IN Provincia varchar(20), 
IN Codigo_postal smallint, 
IN Telefono varchar(20), 
IN Email varchar(30), 
OUT retIdCliente  int(11),
OUT retCode  int(11),
OUT descErr varchar(40)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarCliente() - Error General';

  SET retCode = 0;
    SET descErr = 'insertarCliente() - OK';

  INSERT INTO cli_clientes (
  Nombre,
  Apellido,
  Direccion,
  -- Localidad,
  Provincia,
  Codigo_postal, 
  Telefono,
  Email
  )
  VALUES (
  Nombre, 
  Apellido, 
  Direccion, 
  -- Localidad,
  Provincia,
  Codigo_postal,
  Telefono,
  Email
  );

  SELECT MAX(IdCliente) 
  INTO retIdCliente
  FROM cli_clientes;

END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE eliminarCliente (
IN IdCliente  bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarCliente() - Error General';

  SET retCode = 0;
    SET descErr = 'eliminarCliente() - OK';

  SELECT EXISTS(
        SELECT * FROM cli_clientes cli
        WHERE cli.idCliente = IdCliente
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    UPDATE cli_clientes cli
    SET cli.Usuario_baja = Usuario_baja,
    Fecha_Baja = now()
    WHERE cli.IdCliente = IdCliente;
  ELSE
    SET retCode = 2;
    SET descErr = 'eliminarCliente() - El cliente no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$


DELIMITER $$
CREATE PROCEDURE modificarCliente (
IN IdCliente int(11),
IN Nombre varchar(20), 
IN Apellido varchar(20), 
IN Direccion varchar(20), 
-- IN Localidad varchar(20), 
IN Provincia varchar(20), 
IN Codigo_postal smallint, 
IN Telefono varchar(20), 
IN Email varchar(30), 
OUT retCode  int(11),
OUT descErr varchar(40)
) 

BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarCliente() - Error General';

  SET retCode = 0;
    SET descErr = 'modificarCliente() - OK';

  SELECT EXISTS(
        SELECT * FROM cli_clientes cli
        WHERE cli.IdCliente = IdCliente
        )
  INTO vExiste;

  IF (vExiste = 1) 
  THEN  
    UPDATE cli_clientes cli
    SET 
    cli.Nombre = Nombre,
    cli.Apellido = Apellido,
    cli.Direccion = Direccion,
    -- cli.Localidad = Localidad,
    cli.Provincia = Provincia,
    cli.Codigo_postal = Codigo_postal, 
    cli.Telefono = Telefono,
    cli.Email = Email
    WHERE cli.IdCliente = IdCliente;
  ELSE
    SET retCode = 2;
    SET descErr = 'modificarCliente() - El cliente no existe';
  END IF;

END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE traerClientes (
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todos los clientes que no esten dados de baja'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerClientes() - Error General';

  SET retCode = 0;
    SET descErr = 'traerClientes() - OK';

  SELECT * 
    FROM cli_clientes cli
    WHERE cli.Fecha_Baja IS NULL;
    
END $$
DELIMITER ;

-- SP Usuarios

DELIMITER $$

CREATE PROCEDURE insertarUsuario(
IN IdRol bigint unsigned,
IN Nombre varchar(20),
IN Apellido varchar(20),
IN Usuario varchar(20),
IN pass varchar(20),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarUsuario() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarUsuario() - OK';
    
    INSERT INTO pers_personal (IdRol, Nombre, Apellido, Usuario, Pass) 
  VALUES(IdRol, Nombre, Apellido, Usuario, MD5(Pass));
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE eliminarUsuario(
IN IdPersonal bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
    
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarUsuario() - Error General';
    
  SET retCode = 0;
    SET descErr = 'eliminarUsuario() - OK';
    
  SELECT EXISTS(
        SELECT * FROM pers_personal pp
        WHERE pp.IdPersonal = IdPersonal
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN  
    UPDATE pers_personal pp
    SET pp.Usuario_baja = Usuario_baja,
    Fecha_Baja = now()
    WHERE pp.IdPersonal = IdPersonal;
  ELSE
    SET retCode = 2;
    SET descErr = 'eliminarUsuario() - El usuario no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerUsuarios ( 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todos los usuarios que no esten dados de baja'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerUsuarios() - Error General';

  SET retCode = 0;
    SET descErr = 'traerUsuarios() - OK';

  SELECT pp.IdPersonal, pp.IdRol, pr.Descripcion, pp.Nombre, pp. Apellido, pp.Usuario, pp.Pass
  FROM pers_personal pp, pers_roles pr
  WHERE pp.IdRol = pr.IdRol
  AND pp.Fecha_Baja IS NULL;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerUsuariosComunes (
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve los usuarios que no son administradores'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerUsuariosComunes() - Error General';

  SET retCode = 0;
    SET descErr = 'traerUsuariosComunes() - OK';

  SELECT pp.IdPersonal, pp.IdRol, pr.Descripcion, pp.Nombre, pp. Apellido, pp.Usuario, pp.Pass
  FROM pers_personal pp, pers_roles pr
  WHERE pp.IdRol = pr.IdRol
  AND pp.Fecha_Baja IS NULL
    AND pp.IdRol != 1;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerUsuario(
IN Usuario varchar(20),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
    
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerUsuario() - Error General';

  SET retCode = 0;
    SET descErr = 'traerUsuario() - OK';

  SELECT EXISTS(
        SELECT * FROM pers_personal pers
        WHERE pers.Usuario = Usuario
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    SELECT *
    FROM pers_personal pers
    JOIN pers_roles rol
      ON pers.IdRol = rol.IdRol
    WHERE pers.Usuario = Usuario
    AND pers.Fecha_Baja IS NULL;
  ELSE
    SET retCode = 2;
    SET descErr = 'traerUsuario() - El usuario no existe';
  END IF;

    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE verificarPassUsuario(
IN IdPersonal bigint unsigned,
IN pass varchar (32),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
    
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'verificarPassUsuario() - Error General';

  SET retCode = 0;
    SET descErr = 'verificarPassUsuario() - OK';

  SELECT EXISTS(
        SELECT * FROM pers_personal pers
        WHERE pers.IdPersonal = IdPersonal 
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN  
    SELECT * 
    FROM pers_personal pp 
    WHERE pp.IdPersonal = IdPersonal 
    AND pp.pass = MD5(pass);
  ELSE
    SET retCode = 2;
    SET descErr = 'verificarPassUsuario() - El usuario no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE modificarUsuario(
IN IdPersonal bigint unsigned,
IN IdRol bigint unsigned,
IN Usuario varchar(20),
IN pass varchar(20),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarUsuario() - Error General';
    
  SET retCode = 0;
    SET descErr = 'modificarUsuario() - OK';
    
  SELECT EXISTS(
        SELECT * FROM pers_personal pers
        WHERE pers.IdPersonal = IdPersonal
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    UPDATE pers_personal pp SET
    pp.IdRol = IdRol,
    pp.Usuario = Usuario, 
    pp.Pass = MD5(Pass)
    WHERE pp.IdPersonal = IdPersonal;
  ELSE
    SET retCode = 2;
    SET descErr = 'modificarUsuario() - El usuario no existe';
  END IF;

END $$
DELIMITER ;


-- SP Piezas

DELIMITER $$
CREATE PROCEDURE traerPiezas (
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todas las piezas con sus marcas'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerPiezas() - Error General';

  SET retCode = 0;
    SET descErr = 'traerPiezas() - OK';
    
  Select *, count(idProdEstado) as cantidad 
	from prod_piezas piez
	inner join prod_marcas marc
		on piez.idProdMarca = marc.idProdMarca
	left join prod_piezas_stock stock
		on piez.idProdPieza = stock.idProdPieza
		and stock.idProdEstado = 1
  AND marc.Fecha_Baja IS NULL
  AND piez.Fecha_Baja IS NULL
  		group by piez.idProdPieza;
    
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE eliminarPieza(
IN idProdPieza  bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarPieza() - Error General';
    
  SET retCode = 0;
    SET descErr = 'eliminarPieza() - OK';
    
  SELECT EXISTS(
        SELECT * FROM prod_piezas piez
        WHERE piez.idProdPieza = idProdPieza
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN  
    UPDATE prod_piezas piez
    SET piez.Usuario_baja = Usuario_baja,
    Fecha_Baja = now()
    WHERE piez.idProdPieza = idProdPieza;
  ELSE
    SET retCode = 2;
    SET descErr = 'eliminarPieza() - La pieza no existe';
  END IF;


END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE modificarPieza(
IN idProdPieza  bigint unsigned,
IN idProdMarca bigint unsigned,
IN idUnico varchar (8),
IN Descripcion varchar(40),
IN Precio_venta decimal(10,4),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExistePieza INT;
  DECLARE existeMarca INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarPieza() - Error General';
    
  SET retCode = 0;
    SET descErr = 'modificarPieza() - OK';
    
  SELECT EXISTS(
        SELECT * FROM prod_piezas piez
        WHERE piez.idProdPieza = idProdPieza
        )
  INTO vExistePieza;
    
    SELECT COUNT(*)
  INTO existeMarca
    FROM prod_marcas marc
    WHERE marc.IdProdMarca = idProdMarca;
 
  IF (existeMarca = 1)
    THEN
    
    IF (vExistePieza = 1) 
    THEN  
      UPDATE prod_piezas piez
      SET piez.idProdMarca = idProdMarca,
        piez.idUnico = idUnico,
        piez.Descripcion = Descripcion,
        piez.Precio_venta = Precio_venta
      WHERE piez.idProdPieza = idProdPieza;
    ELSE
      SET retCode = 2;
      SET descErr = 'modificarPieza() - La pieza no existe';
    END IF;
        
    ELSE
    SET retCode = 3, descErr = 'modificarPieza() - Error. Marca inexistente';
    END IF;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE altaPiezaStockFisico( 
IN idProdPieza  bigint unsigned,
IN cantidad smallint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Da de alta piezas en el stock fisico, impactando en la prod_piezas_stock'
BEGIN

  DECLARE v_contador int unsigned default 0;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'altaPiezaStockFisico() - Error General';
    
  SET retCode = 0;
    SET descErr = 'altaPiezaStockFisico() - OK';
  
  
  START TRANSACTION;
   
   WHILE v_contador < cantidad DO
    
    INSERT INTO prod_piezas_stock (idProdPieza,idProdEstado)
    VALUES (idProdPieza,1); -- idProdEstado = 'Disponible'  
    
    SET v_contador = v_contador+1;
    
   END WHILE;
   
  COMMIT;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE insModPrecioCompra ( 
IN idProveedor bigint,
IN idProdPieza bigint,
IN precio_compra decimal(10,4),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Inserta o modifica el precio de compra de una pieza para un proveedor'
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insModPrecioCompra() - Error General';

  SET retCode = 0;
    SET descErr = 'insModPrecioCompra() - OK';
    
  SELECT EXISTS(
        SELECT * FROM prov_precios_piezas ppp
        WHERE ppp.idProveedor = idProveedor
        AND ppp.idProdPieza = idProdPieza
        )
  INTO vExiste;	

  IF (vExiste = 1) 
  THEN 
	-- Existe la pieza. La actualizo
    UPDATE prov_precios_piezas ppp
    SET ppp.precio_compra = precio_compra
    WHERE ppp.idProveedor = idProveedor
    AND ppp.idProdPieza = idProdPieza;
 
  ELSE
	-- No existe la pieza. La creo.
    INSERT INTO prov_precios_piezas
    (idProveedor,idProdPieza,precio_compra)
    VALUES 
    (idProveedor,idProdPieza,precio_compra);
  END IF;
    
END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE getPrecioCompra ( 
IN idProveedor bigint,
IN idProdPieza bigint,
OUT retPrecio_compra decimal(10,4),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Obtiene el precio de compra de una pieza'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insModPrecioCompra() - Error General';

  SET retCode = 0;
    SET descErr = 'insModPrecioCompra() - OK';
    
  SELECT ppp.precio_compra INTO retPrecio_compra 
  FROM prov_precios_piezas ppp
  WHERE ppp.idProveedor = idProveedor
  AND ppp.idProdPieza = idProdPieza;
    
END $$
DELIMITER ;



DELIMITER $$
CREATE PROCEDURE getPreciosProveedor( 
IN idProveedor bigint,
IN idProdMarca bigint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve listado de piezas + el precio de compra'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insModPrecioCompra() - Error General';

  SET retCode = 0;
    SET descErr = 'insModPrecioCompra() - OK';
    
  SELECT *
  FROM prod_piezas piez
  LEFT JOIN
	   prov_precios_piezas ppp
	ON ppp.idProdPieza = piez.idProdPieza
  WHERE piez.Fecha_Baja IS NULL
  AND piez.idProdMarca = idProdMarca
  AND ppp.idProveedor = idProveedor;
    
END $$
DELIMITER ;


-- SP Electrodomesticos


DELIMITER $$
CREATE PROCEDURE insertarElectrodomestico (
IN idProdMarca bigint unsigned, 
IN Modelo varchar(20), 
IN Descripcion varchar(20),  
OUT retIdElectro  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarElectrodomestico() - Error General';

  SET retCode = 0;
    SET descErr = 'insertarElectrodomestico() - OK';

  INSERT INTO elec_electrodomesticos (
  idProdMarca,
  Modelo,
  Descripcion
  )
  VALUES (
  idProdMarca, 
  Modelo, 
  Descripcion
  );

  SELECT MAX(IdElectro) 
  INTO retIdElectro
  FROM elec_electrodomesticos;

END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE traerElectrodomesticos ( 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todos los electrodomesticos con sus marcas'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerElectrodomesticos() - Error General';

  SET retCode = 0;
    SET descErr = 'traerElectrodomesticos() - OK';
    
  SELECT *
  FROM elec_electrodomesticos elec
    JOIN prod_marcas prod
    ON elec.idProdMarca = prod.idProdMarca
  WHERE elec.Fecha_Baja IS NULL;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE eliminarElectrodomestico(
IN IdElectro bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
    
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarElectrodomestico() - Error General';
    
  SET retCode = 0;
    SET descErr = 'eliminarElectrodomestico() - OK';
    
  SELECT EXISTS(
        SELECT * FROM elec_electrodomesticos elec
        WHERE elec.IdElectro = IdElectro
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    UPDATE elec_electrodomesticos elec
    SET elec.Usuario_baja = Usuario_baja,
    Fecha_Baja = now()
    WHERE elec.IdElectro = IdElectro;
  ELSE
    SET retCode = 2;
    SET descErr = 'eliminarElectrodomestico() - El elec. no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE modificarElectrodomestico(
IN IdElectro bigint unsigned,
IN Modelo varchar(20), 
IN Descripcion varchar(40),  
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
  DECLARE existeMarca INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarElectrodomestico() - Error General';
    
  SET retCode = 0;
    SET descErr = 'modificarElectrodomestico() - OK';
    
  SELECT EXISTS(
        SELECT * FROM elec_electrodomesticos elec
        WHERE elec.IdElectro = IdElectro
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN  
    UPDATE elec_electrodomesticos elec
    SET elec.Modelo = Modelo,
      elec.Descripcion = Descripcion
    WHERE elec.IdElectro = IdElectro;
  ELSE
    SET retCode = 2;
    SET descErr = 'modificarElectrodomestico() - El elec. no existe';
  END IF;   
    
END $$
DELIMITER ;

-- SP Marca

DELIMITER $$
CREATE PROCEDURE insertarMarca (
IN Nombre varchar(20), 
OUT retIdMarca int(11),
OUT retCode  int(11),
OUT descErr varchar(40)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarMarca() - Error General';

  SET retCode = 0;
    SET descErr = 'insertarMarca() - OK';

  INSERT INTO prod_marcas (Nombre)
  VALUES (Nombre);

  SELECT MAX(IdProdMarca) 
  INTO retIdMarca
  FROM prod_marcas;

END $$
DELIMITER ;

-- SP Proveedores

DELIMITER $$

CREATE PROCEDURE insertarProveedor(
IN Nombre varchar(20),
IN Contacto varchar(20),
IN Cuit varchar(11),
IN Telefono varchar(20),
IN Mail varchar(30),
OUT retIdProveedor  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarProveedor() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarProveedor() - OK';
    
  INSERT INTO prov_proveedores (
	Nombre,
	Contacto,
	Cuit,
    Telefono,
    Mail
  )
  VALUES (
	Nombre, 
    Contacto,
	Cuit,
    Telefono,
    Mail
  );

  SELECT MAX(IdProveedor) 
  INTO retIdProveedor
  FROM prov_proveedores;

END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE modificarProveedor(
IN idProveedor bigint,
IN Nombre varchar(20),
IN Contacto varchar(20),
IN Cuit varchar(11),
IN Telefono varchar(20),
IN Mail varchar(30),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarProveedor() - Error General';
    
  SET retCode = 0;
    SET descErr = 'modificarProveedor() - OK';
    
    
  SELECT EXISTS(
        SELECT * FROM prov_proveedores pp
        WHERE pp.IdProveedor = idProveedor
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN
    UPDATE prov_proveedores pp
    SET 
    pp.Nombre = Nombre,
    pp.Contacto = Contacto,
    pp.Cuit = Cuit,
    pp.Telefono = Telefono,
    pp.Mail = Mail
    WHERE pp.IdProveedor = idProveedor;
  ELSE
    SET retCode = 2;
    SET descErr = 'modificarProveedor() - El proov. no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE insertarProveeMarca(
IN idProveedor bigint,
IN idProdMarca bigint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarProveeMarca() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarProveeMarca() - OK';
    
  INSERT INTO prov_provee_marca (
    idProveedor,
    idProdMarca
  )
  VALUES (
  idProveedor,
    idProdMarca
  );

END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE borrarProveeMarca(
IN idProveedor bigint,
IN idProdMarca bigint,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'borrarProveeMarca() - Error General';
    
  SET retCode = 0;
    SET descErr = 'borrarProveeMarca() - OK';
    
  SELECT EXISTS(
        SELECT * FROM prov_provee_marca ppm
        WHERE  ppm.idProveedor = idProveedor
        AND ppm.idProdMarca = idProdMarca
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN  
    DELETE ppm FROM prov_provee_marca ppm
    WHERE ppm.idProveedor = idProveedor
    AND ppm.idProdMarca = idProdMarca;
  ELSE
    SET retCode = 2;
    SET descErr = 'borrarProveeMarca() - La relacion no existe';
  END IF;
   
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerProveedores ( 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todos los proveedores'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerProveedores() - Error General';

  SET retCode = 0;
    SET descErr = 'traerProveedores() - OK';

  SELECT * 
    FROM prov_proveedores
    WHERE Fecha_Baja IS NULL;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerProveedor ( 
IN idProveedor BIGINT UNSIGNED, 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve proveedor por id'
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerProveedor() - Error General';

  SET retCode = 0;
    SET descErr = 'traerProveedor() - OK';

  SELECT EXISTS(
        SELECT * FROM prov_proveedores prov
        WHERE prov.idProveedor = idProveedor
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN  
    SELECT * 
    FROM prov_proveedores prov
    WHERE 
    prov.idProveedor = idProveedor
    AND Fecha_Baja IS NULL;
  ELSE
    SET retCode = 2;
    SET descErr = 'traerProveedor() - El proveedor no existe';
  END IF;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerMarcasProvistas (
IN idProveedor bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
)
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerMarcasProvistas() - Error General';

  SET retCode = 0;
    SET descErr = 'traerMarcasProvistas() - OK';

  SELECT * 
    FROM prov_provee_marca ppm, prod_marcas pm
    WHERE ppm.idProveedor = idProveedor
    AND ppm.idProdMarca = pm.IdProdMarca;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE eliminarProveedor(
IN IdProveedor bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarProveedor() - Error General';
    
  SET retCode = 0;
    SET descErr = 'eliminarProveedor() - OK';
    
    UPDATE prov_proveedores pp
  SET pp.Usuario_baja = Usuario_baja,
  Fecha_Baja = now()
  WHERE pp.IdProveedor = IdProveedor;

END $$
DELIMITER ;

-- SP de stock

DELIMITER $$

CREATE PROCEDURE descontarStock(
IN idOT int(11),
OUT retCode int(11),
OUT descErr varchar(60)
) 
COMMENT 'Asocia las piezas del stock fÃ­sico a una OT. Se debe llamar Ãºnicamente cuando la OT pase a estado Reparada'
BEGIN

  DECLARE sinRegistros INT DEFAULT FALSE;

  DECLARE vidOTItems BIGINT UNSIGNED;
  DECLARE vidProdPieza BIGINT UNSIGNED;
  DECLARE vidProdStock BIGINT UNSIGNED;
  
  -- Busco los registros de la ot_items
  DECLARE datosCur CURSOR 
  FOR SELECT it.idOTItems,
			 it.idProdPieza
  FROM ot_items it
  WHERE it.idOT = idOT;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET sinRegistros = TRUE;
  
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'descontarStock() - Error General';
    
  SET retCode = 0;
    SET descErr = 'descontarStock() - OK';
  
  read_loop: LOOP
	FETCH datosCur INTO 
	vidOTItems,
	vidProdPieza;
	IF sinRegistros THEN
		LEAVE read_loop;
	END IF;
    
    -- Tomo un item fÃ­sico de la prod_piezas_stock en estado Disponible
	SELECT stock.idProdStock
    INTO vidProdStock
    FROM prod_piezas_stock stock 
    WHERE stock.idProdEstado = 1
    AND stock.idProdPieza = vidProdPieza
    LIMIT 1;
    
    -- Agrego en ot_items el valor idProdStock
	UPDATE ot_items it
    SET it.idProdStock = vidProdStock
    WHERE it.idOTItems = vidOTItems;
  
	-- Cambio el estado de la piezas a "vendido"
    UPDATE prod_piezas_stock stock
    SET stock.idProdEstado = 2
    WHERE stock.idProdStock = vidProdStock;
		
  END LOOP read_loop;
      
END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE cambiarEstadoDePieza(	-- mas abarcativo
IN idPieza int(11),
IN idEstadoAnterior int(11),
IN idEstadoNuevo int(11),
OUT retCode int(11),
OUT descErr varchar(60)
) 
BEGIN


  DECLARE vidProdStock BIGINT UNSIGNED;
  
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'cambiarEstadoDePieza() - Error General';
    
  SET retCode = 0;
    SET descErr = 'cambiarEstadoDePieza() - OK';
  

    -- Tomo un item fÃ­sico de la prod_piezas_stock en estado viejo
	SELECT MIN(stock.idProdStock)
    INTO vidProdStock
    FROM prod_piezas_stock stock 
    WHERE stock.idProdEstado = idEstadoAnterior
    AND stock.idProdPieza = idPieza;
  
	-- Cambio el estado de la piezas al nuevo
    UPDATE prod_piezas_stock stock
    SET stock.idProdEstado = idEstadoNuevo
    WHERE stock.idProdStock = vidProdStock;
		
      
END $$
DELIMITER ;

-- SP de contabilidad

DELIMITER $$

CREATE PROCEDURE impactarContabilidadSC(
IN idSolcCompra  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE sinRegistros INT DEFAULT FALSE;
  DECLARE cantRegistros INT;
  DECLARE cantRegSolp INT;
  
  DECLARE vIdSolcPiezas INT;
  DECLARE vidProdPieza INT;
  DECLARE vCantidad INT;
  DECLARE vPrecio_compra INT;
  
  DECLARE vMonto INT;

  -- Busco las piezas y sus precios de compra, asociadas a esa solicitud
  DECLARE datosCur CURSOR 
  FOR SELECT solp.idSolcPiezas,
			 solp.idProdPieza, 
			 solp.cantidad,
             ppp.precio_compra
  FROM solc_solicitud_compra solc,
	   solc_piezas solp,
	   prov_precios_piezas ppp
  WHERE solp.idSolcCompra = idSolcCompra
  AND solp.idProdPieza = ppp.idProdPieza
  AND solc.idSolcCompra = solp.idSolcCompra
  AND solc.idProveedor = ppp.idProveedor;
  
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET sinRegistros = TRUE;
  
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'impactarContabilidadSC() - Error General';
    
  SET retCode = 0;
    SET descErr = 'impactarContabilidadSC() - OK';
    
   OPEN datosCur;
 
   SELECT FOUND_ROWS() INTO cantRegistros ;
   
   SELECT COUNT(*) 
   INTO cantRegSolp
   FROM solc_piezas solp
   WHERE solp.idSolcCompra = idSolcCompra;
  
   -- Comparo la cantidad de registros con los que hay en la solc_piezas
   IF cantRegistros < cantRegSolp
   THEN
		-- Posible falta o inconsistencia de precios en la prov_precios_piezas
		SET retCode = 2, descErr = 'impactarContabilidadSC() - Error al obtener precios de compra';
   ELSE
	   read_loop: LOOP
		FETCH datosCur INTO 
		vIdSolcPiezas,
		vidProdPieza, 
		vCantidad, 
		vPrecio_compra;
		IF sinRegistros THEN
		  LEAVE read_loop;
		END IF;
		
		-- Multiplico el precio de la pieza por la cantidad que comprÃ©
		SELECT vCantidad * vPrecio_compra
		INTO vMonto;
		
		-- Impacto el monto en la cont_libro_diario
		INSERT INTO cont_libro_diario (monto,idOTItems,idSolcPiezas,Fecha)
		VALUES(vMonto,null,vIdSolcPiezas,now());
		
	   END LOOP read_loop;
   
   END IF;
   
   CLOSE datosCur;
      
END $$
DELIMITER ;


DELIMITER $$

CREATE PROCEDURE impactarContabilidadOT( 
IN idOT  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE sinRegistros INT DEFAULT FALSE;
  
  DECLARE vidOTItems BIGINT UNSIGNED;
  DECLARE vPrecio DECIMAL(10,2);

  -- Busco los items de laOT y sus precios de venta
  DECLARE datosCur CURSOR 
  FOR SELECT it.idOTItems,	 
			 it.precio
  FROM  ot_items it
  WHERE it.idOT = idOT;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET sinRegistros = TRUE;
 
  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'impactarContabilidadOT() - Error General';
    
  SET retCode = 0;
    SET descErr = 'impactarContabilidadOT() - OK';
    
  OPEN datosCur;
  
  	   read_loop: LOOP
		FETCH datosCur INTO 
		vidOTItems,
		vPrecio;
		IF sinRegistros THEN
		  LEAVE read_loop;
		END IF;
		
		-- Impacto el monto en la cont_libro_diario
		INSERT INTO cont_libro_diario (monto,idOTItems,idSolcPiezas,Fecha)
		VALUES(vPrecio,vidOTItems,null,now());
		
	   END LOOP read_loop;

  CLOSE datosCur;

END $$
DELIMITER ;


-- SP Solicitudes de compra

DELIMITER $$

CREATE PROCEDURE insertarSolicitudCompra(
IN idSolcEstado bigint unsigned,
IN idUsuarioAlta bigint unsigned,
IN idProveedor bigint unsigned,
OUT retIdSC  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarSolicitudCompra() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarSolicitudCompra() - OK';
    
    INSERT INTO solc_solicitud_compra
    ( 
  idSolcEstado, -- el estado inicial es 1
  idUsuarioAlta,
  idProveedor,
  Fecha_Alta
  )
    VALUES 
  (
  idSolcEstado,
  idUsuarioAlta,
  idProveedor,
  NOW()
  );
    
  SELECT MAX(idSolcCompra) 
  INTO retIdSC
  FROM solc_solicitud_compra;
    
END $$
DELIMITER ;


DELIMITER $$

CREATE PROCEDURE insertarPiezasSolicitud(
IN idSolcCompra bigint unsigned,
IN idProdPieza bigint unsigned,
IN cantidad int,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarPiezasSolicitud() - Error General';
    
  SET retCode = 0;
    SET descErr = 'insertarPiezasSolicitud() - OK';
    
    INSERT INTO solc_piezas
    ( 
  idSolcCompra,
  idProdPieza,
  cantidad
  )
    VALUES 
  (
  idSolcCompra,
  idProdPieza,
  cantidad
  );
        
END $$
DELIMITER ;


DELIMITER $$

CREATE PROCEDURE traerSolicitudes(
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todos las solicitudes'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerSolicitudes() - Error General';
    
  SET retCode = 0;
    SET descErr = 'traerSolicitudes() - OK';
  
  SELECT *
  FROM solc_solicitud_compra solc
  JOIN prov_proveedores prov
    ON solc.idProveedor = prov.IdProveedor
  JOIN solc_estados est
    ON solc.idSolcEstado = est.idSolcEstado;    
    
END $$
DELIMITER ;


DELIMITER $$

CREATE PROCEDURE traerPiezasSolicitud( 
IN idSolcCompra bigint unsigned,
OUT retCode int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todas las piezas de una solicitud'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerPiezasSolicitud() - Error General';
    
  SET retCode = 0;
    SET descErr = 'traerPiezasSolicitud() - OK';
  
  SELECT *
  FROM solc_piezas solp
  JOIN prod_piezas prod
    ON solp.idProdPieza = prod.idProdPieza
  JOIN prod_marcas marc
    ON marc.IdProdMarca = prod.IdProdMarca
  WHERE solp.idSolcCompra = idSolcCompra;
    
    
END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE eliminarPiezasSolicitud(
IN idSolcCompra bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'eliminarPiezasSolicitud() - Error General';
    
  SET retCode = 0;
    SET descErr = 'eliminarPiezasSolicitud() - OK';
    
  DELETE solc FROM solc_piezas solc
  WHERE solc.idSolcCompra = idSolcCompra;
    
END $$
DELIMITER ;


DELIMITER $$

CREATE PROCEDURE modificarSolicitudCompra(
IN idSolcCompra  int(11),
IN idSolcEstado bigint unsigned,
IN idUsuarioAlta bigint unsigned,
IN idProveedor bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'modificarSolicitudCompra() - Error General';
    
  SET retCode = 0;
    SET descErr = 'modificarSolicitudCompra() - OK';
    
  SELECT EXISTS(
        SELECT * FROM solc_solicitud_compra solc
        WHERE solc.idSolcCompra = idSolcCompra
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN
    UPDATE solc_solicitud_compra solc
    SET solc.idSolcEstado = idSolcEstado,
      solc.idUsuarioAlta = idUsuarioAlta,
      solc.idProveedor = idProveedor
    WHERE solc.idSolcCompra = idSolcCompra;
  ELSE
    SET retCode = 2;
    SET descErr = 'modificarSolicitudCompra() - La SC no existe';
  END IF;
    
END $$
DELIMITER ;

DELIMITER $$

CREATE PROCEDURE cambiarEstadoSC(
IN idSolcCompra  int(11),
IN idSolcEstado bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
  DECLARE vEstadoActual INT;
    DECLARE vEsCambioValido INT DEFAULT 0;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'cambiarEstadoSC() - Error General';
    
  SET retCode = 0;
    SET descErr = 'cambiarEstadoSC() - OK';
    
  
  SELECT EXISTS(
        SELECT * FROM solc_solicitud_compra solc
        WHERE solc.idSolcCompra = idSolcCompra
        )
  INTO vExiste;
  
  IF (vExiste = 1) 
  THEN
  
    -- Traigo el estado actual
    SELECT solc.idSolcEstado
    INTO vEstadoActual
        FROM solc_solicitud_compra solc
        WHERE solc.idSolcCompra = idSolcCompra;
        
        -- ValidaciÃ³n de cambio de estados
        IF (vEstadoActual = 1 and idSolcEstado = 2) -- Si pasa de Ingresada a Enviada
        THEN
			SET vEsCambioValido = 1;
        END IF;
        
    IF (vEstadoActual = 2 and idSolcEstado = 3) -- Si pasa de Enviada a Procesada
        THEN
			-- Registro el pago al proveedor en el libro diario
			CALL impactarContabilidadSC(idSolcCompra,@retCode,@descErr);
			SET vEsCambioValido = 1;
        END IF;
        
    IF (vEstadoActual = 1 and idSolcEstado = 4) -- Si pasa de Ingresada a Cancelada
        THEN
			SET vEsCambioValido = 1;
        END IF;
        
        
	-- Si el cambio de estado es vÃ¡lido, hago update
	IF (vEsCambioValido = 1)
	THEN
      UPDATE solc_solicitud_compra solc
      SET solc.idSolcEstado = idSolcEstado
      WHERE solc.idSolcCompra = idSolcCompra;    
    ELSE
      SET retCode = 2;
      SET descErr = 'cambiarEstadoSC() - Cambio de estado invÃ¡lido';
    END IF;
    
    ELSE
    SET retCode = 2;
    SET descErr = 'cambiarEstadoSC() - La SC no existe';
  END IF;
    
END $$
DELIMITER ;


-- SP Ordenes de trabajo

DELIMITER $$
CREATE PROCEDURE cambiarVencimientoPresup ( 
IN idOT bigint unsigned,
IN vencPresup date, 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Cambia el vencimiento al presupuesto de una OT determinada.'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'cambiarVencimientoPresup() - Error General';

  SET retCode = 0;
    SET descErr = 'cambiarVencimientoPresup() - OK';
  
  UPDATE ot_ordenes_trabajo ot
  SET ot.vencPresup = vencPresup
  WHERE ot.idOt = idOT;
  
        
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE agregarPiezasUsadasOT (
IN idOT bigint unsigned,
IN idProdPieza bigint unsigned, 
OUT retCode  int(11),
OUT descErr varchar(80)
)

BEGIN
	
    DECLARE vidProdStock BIGINT UNSIGNED;
    
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    -- SET retCode = 1, descErr = 'agregarPiezasUsadasOT() - Error General';

	SET retCode = 0;
    SET descErr = 'agregarPiezasUsadasOT() - OK';
    
    SELECT MIN(stock.idProdStock)
    INTO vidProdStock
    FROM prod_piezas_stock stock 
    WHERE stock.idProdEstado = 1
    AND stock.idProdPieza = idPieza;
    
    UPDATE prod_piezas_stock stock
    SET stock.idProdEstado = 2
    WHERE stock.idProdStock = vidProdStock;
    
    INSERT INTO ot_piezas_usadas
    (idProdStock, idOT)
    VALUES
    (vidProdStock, idOT);

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE agregarItemOt ( 
IN idOT bigint unsigned,
IN idProdPieza bigint unsigned, 
IN costo decimal(5,2),
OUT retCode  int(11),
OUT descErr varchar(80)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'agregarItemOt() - Error General';

  SET retCode = 0;
    SET descErr = 'agregarItemOt() - OK';
  
	INSERT INTO ot_piezas_presupuestadas
    (idOT, idProdPieza, costoPresupuestada)
    VALUES
    (idOT, idProdPieza, costo);
        
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerOTsConEstado ( 
IN idEstado bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve todas las OT y sus datos con un estado en particular'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerOTsConEstado() - Error General';

  SET retCode = 0;
    SET descErr = 'traerOTsConEstado() - OK';

  SELECT * 
    FROM ot_ordenes_trabajo ot
  JOIN elec_electrodomesticos elec
    ON elec.IdElectro = ot.idElectro
  JOIN prod_marcas marca
    ON elec.idProdMarca=marca.idProdMarca
  JOIN cli_clientes cli
    ON  ot.idCliente = cli.IdCliente
  JOIN pers_personal pers1
    ON ot.idUsuarioAlta = pers1.IdPersonal
  LEFT JOIN pers_personal pers2
    ON ot.idTecnicoAsoc = pers2.IdPersonal 
  LEFT JOIN env_fleteros env
    ON  ot.idFleteroAsoc = env.IdFletero
  WHERE ot.estado_orden = idEstado;
        
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerItemsOT ( 
IN idOt bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve los items de una OT'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerItemsOT() - Error General';

  SET retCode = 0;
    SET descErr = 'traerItemsOT() - OK';
    
        
  SELECT *
    FROM ot_items it
  WHERE  it.idOt = idOt;
        
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerOT (
IN idOt bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Devuelve una OT en particular'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerOT() - Error General';

  SET retCode = 0;
    SET descErr = 'traerOT() - OK';

  SELECT * 
    FROM ot_ordenes_trabajo ot
    WHERE ot.idOt = idOt;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE crearOT(
IN idCliente bigint unsigned,
IN idElectro bigint unsigned,
IN descripcion VARCHAR(60),
IN idUsuarioAlta bigint unsigned,
-- IN idTecnicoAsoc bigint unsigned,
IN esDelivery boolean,
-- IN vencPresup date,
-- IN expiraGarantia date,
-- IN idFleteroAsoc bigint unsigned,
-- IN idOtAsociada bigint unsigned,
OUT retIdOT  int(11),
OUT retCode  int(11),
OUT descErr varchar(60)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'crearOT() - Error General';
    
  SET retCode = 0;
    SET descErr = 'crearOT() - OK';
    
    insert into ot_ordenes_trabajo (
    idCliente,
    idElectro,
    descripcion,
    idUsuarioAlta,
    -- idTecnicoAsoc,
    esDelivery,
    -- vencPresup,
    -- expiraGarantia,
    -- idFleteroAsoc,
    -- idOtAsociada,
  estado_orden
    ) values (
    idCliente,
    idElectro,
    descripcion,
    idUsuarioAlta,
    -- idTecnicoAsoc,
    esDelivery,
    -- vencPresup,
    -- expiraGarantia,
    -- idFleteroAsoc,
    -- idOtAsociada,
  1
    );
    
    SELECT MAX(idOT) 
  INTO retIdOT
  FROM ot_ordenes_trabajo;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerOTs (
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerOTs() - Error General';

  SET retCode = 0;
    SET descErr = 'traerOTs() - OK';

  SELECT * 
    FROM ot_ordenes_trabajo;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE cambiarEstadoOT(
IN idOT bigint unsigned, 
IN idEstadoPosible bigint unsigned, 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE vExiste INT;
  DECLARE vEstadoActual INT;
  DECLARE vEsCambioValido INT DEFAULT 0;

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'cambiarEstadoOT() - Error General';
    
  SET retCode = 0;
    SET descErr = 'cambiarEstadoOT() - OK';
    
  SELECT EXISTS(
        SELECT * FROM ot_ordenes_trabajo ot 
        WHERE ot.idOT = idOT
        )
  INTO vExiste;
    
  IF (vExiste = 1) 
  THEN
   
	-- Traigo el estado actual
    SELECT his.estado_orden
    INTO vEstadoActual
	FROM ot_estados_historico his
	WHERE his.idOT = idOT
    AND his.Fecha_Baja IS NULL;
        
	-- ValidaciÃ³n de cambio de estados
	IF (vEstadoActual = 1 and idEstadoPosible = 2) -- Si pasa de Ingresada a Presupuestada
	THEN
      SET vEsCambioValido = 1;
	END IF;
    
	IF (vEstadoActual = 2 and idEstadoPosible in (3,4)) -- Si pasa de Presupuestada a Aprobada o Desaprobada
	THEN
      SET vEsCambioValido = 1;
	END IF;
  
  	IF (vEstadoActual = 3 and idEstadoPosible = 5) -- Si pasa de Aprobada a En reparaciÃ³n
	THEN
      SET vEsCambioValido = 1;
	END IF;
    
	IF (vEstadoActual = 5 and idEstadoPosible = 7) -- Si pasa de En reparaciÃ³n a Reparada
	THEN
      -- Tomo los items del stock y los utilizo
      CALL descontarStock(idOT,@retCode,@descErr);
      SET vEsCambioValido = 1;
	END IF;

	IF (vEstadoActual = 5 and idEstadoPosible = 6) -- Si pasa de En reparaciÃ³n a En espera de piezas
	THEN
      SET vEsCambioValido = 1;
	END IF;
    
	IF (vEstadoActual = 7 and idEstadoPosible = 9) -- Si pasa de Reparada a Despachada
	THEN
      SET vEsCambioValido = 1;
	END IF;
    
	IF (vEstadoActual in (7,9) and idEstadoPosible = 10) -- Si pasa de Despachada o Reparada a Entregada
	THEN
	  -- Registro el cobro en el libro diario
	  CALL impactarContabilidadOT(idOT,@retCode,@descErr);
      SET vEsCambioValido = 1;
	END IF;
    
    IF (vEsCambioValido = 1)
    THEN
		
        UPDATE ot_estados_historico his
        SET his.Fecha_Baja = now()
        WHERE his.idOT = idOT
        AND his.Fecha_Baja IS NULL;
        
		UPDATE ot_ordenes_trabajo ot 
		SET estado_orden=idEstadoPosible 
		WHERE ot.idOT = idOT;

		INSERT INTO ot_estados_historico(
		idOT,
		estado_orden,
		Fecha_Alta
		)
		VALUES (
		idOT,
		idEstadoPosible,
		now()
		);
	
    ELSE
      SET retCode = 2;
      SET descErr = 'cambiarEstadoOt() - Cambio de estado invÃ¡lido';
    END IF;

  ELSE
    SET retCode = 2;
    SET descErr = 'cambiarEstadoOT() - La OT no existe';
  END IF;

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE traerOTasignadasA ( 

IN idFletero bigint unsigned,

OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'traerOTasignadasA() - Error General';

  SET retCode = 0;
    SET descErr = 'traerOTasignadasA() - OK';

  SELECT * 
    FROM ot_ordenes_trabajo ot
  JOIN elec_electrodomesticos elec
    ON elec.IdElectro = ot.idElectro
  JOIN prod_marcas marca
    ON elec.idProdMarca=marca.idProdMarca
  JOIN cli_clientes cli
    ON  ot.idCliente = cli.IdCliente
  JOIN pers_personal pers1
    ON ot.idUsuarioAlta = pers1.IdPersonal
  LEFT JOIN pers_personal pers2
    ON ot.idTecnicoAsoc = pers2.IdPersonal 
  LEFT JOIN env_fleteros env
    ON  ot.idFleteroAsoc = env.IdFletero
  WHERE ot.idFleteroAsoc = idFletero;
        
END $$
DELIMITER ;

-------------------------------------------------

-- SP Accesorios

DELIMITER $$
CREATE PROCEDURE getCostoVariable(
IN Tipo_Costo varchar(30), 
OUT Precio decimal(10,2), 
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'getCostoVariable() - Error General';
    
  SET retCode = 0;
    SET descErr = 'getCostoVariable() - OK';
    
  SELECT CAST(cost.Precio  AS Decimal (10,2)) INTO Precio
  FROM ot_costos_variables cost
  WHERE cost.Tipo_Costo = Tipo_Costo; -- COSTO_HORA_MANO_OBRA 

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE insertarFletero (
IN Nombre varchar(20), 
IN Apellido varchar(20),
IN Celular varchar(20),
IN RegistroConducir varchar(12),
IN FechaVencimientoRegistro date,
IN IdVehiculo varchar(10),  
OUT retIdFletero  int(11),
OUT retCode int(11),
OUT descErr varchar(40)
) 

BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'insertarFletero() - Error General';

  SET retCode = 0;
    SET descErr = 'insertarFletero() - OK';

  INSERT INTO env_fleteros (
  Nombre,
  Apellido,
  Celular,
  RegistroConducir,
  FechaVencimientoRegistro,
  IdVehiculo
  )
  VALUES (
  Nombre,
  Apellido,
  Celular,
  RegistroConducir,
  FechaVencimientoRegistro,
  IdVehiculo
  );

  SELECT MAX(IdFletero) 
  INTO retIdFletero
  FROM env_fleteros;

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE borrarFletero(
IN IdFletero bigint unsigned,
IN Usuario_baja bigint unsigned,
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'borrarFletero() - Error General';
    
  SET retCode = 0;
    SET descErr = 'borrarFletero() - OK';
    
    UPDATE env_fleteros f
  SET f.Usuario_baja = Usuario_baja,
  Fecha_Baja = now()
  WHERE f.IdFletero = IdFletero;

END $$
DELIMITER ;

-- SP config

DELIMITER $$
CREATE PROCEDURE updateClaveDesbloqueo(
IN clave VARCHAR(32),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'updateClaveDesbloqueo() - Error General';
    
  SET retCode = 0;
    SET descErr = 'updateClaveDesbloqueo() - OK';
    
    UPDATE acc_parametros 
    SET parm_valor1 = MD5(clave)
    WHERE parametro = 'HASHCODE_PASSWORD';

END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE verificarClaveReest(
IN clave varchar (32),
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'verificarClaveReest() - Error General';

  SET retCode = 0;
    SET descErr = 'verificarClaveReest() - OK';

    SELECT * FROM acc_parametros 
    WHERE parametro = 'HASHCODE_PASSWORD' 
    AND parm_valor1 = MD5(clave);
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE reestablecerSuperUsuario(
OUT retCode  int(11),
OUT descErr varchar(60)
) 
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'reestablecerSuperUsuario() - Error General';

  SET retCode = 0;
    SET descErr = 'reestablecerSuperUsuario() - OK';

    UPDATE pers_personal 
  SET Usuario = 'admin', pass = MD5('admin') 
    WHERE IdRol = 1;
    
END $$
DELIMITER ;

-- SP Reportes

DELIMITER $$
CREATE PROCEDURE RepElectrodomesticos(
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Reporte ranking de "Electrodom�sticos m�s reparados"'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'RepElectrodomesticos() - Error General';

  SET retCode = 0;
    SET descErr = 'RepElectrodomesticos() - OK';
	
  SELECT m.Nombre as Marca, 
		 el.Modelo as Modelo,
		 COUNT(*) AS 'Cantidad'
  FROM ot_ordenes_trabajo ot 
  INNER JOIN elec_electrodomesticos el on ot.idElectro = el.idElectro
  INNER JOIN prod_marcas m on el.idProdMarca = m.IdProdMarca
  GROUP BY ot.idElectro
  ORDER BY COUNT(*) DESC;
    
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE RepEnvios(
OUT retCode  int(11),
OUT descErr varchar(60)
) 
COMMENT 'Reporte de "EnvÃ­os del dÃ­a"'
BEGIN

  DECLARE CONTINUE HANDLER FOR SQLEXCEPTION 
    SET retCode = 1, descErr = 'RepEnvios() - Error General';

  SET retCode = 0;
    SET descErr = 'RepEnvios() - OK';
    
   /* 
   Me fijo en la tabla historico las OT que tengan estado enviado 
   con fecha de alta hoy 
   */
   SELECT *
   FROM ot_ordenes_trabajo ot,
		ot_estados_historico his
   WHERE his.Fecha_Alta BETWEEN NOW()-1 AND NOW()
   AND his.estado_orden = 10
   AND ot.idOT = his.idOT;
   
END $$
DELIMITER ;


-------------------------------------------------

-- Triggers

DELIMITER $$
-- Trigger: notificar_cliente
CREATE TRIGGER notificar_cliente
AFTER INSERT ON ot_estados_historico
FOR EACH ROW 
BEGIN
-- Cuando una orden de trabajo se finaliza, se deberÃ¡ notificar al cliente, mediante correo
-- electrÃ³nico o telÃ©fono, para que Ã©ste retire su producto reparado. Si se notifica por 
-- correo electrÃ³nico, se deberÃ¡ indicar el producto que se reparÃ³, horario y dÃ­as en los 
-- que se pueda retirar, como asÃ­ tambiÃ©n la direcciÃ³n del local.
END$$
DELIMITER ;

DELIMITER $$
-- Trigger: notificar_administrativo
CREATE TRIGGER notificar_administrativo
AFTER UPDATE ON prod_piezas_stock 
FOR EACH ROW 
BEGIN
-- Notificar al usuario administrativo las piezas de stock con las que se cuenta con poca
-- existencia. AdemÃ¡s, indicar aquellas piezas que es urgente comprar ya que deben 
-- utilizarse en productos recibidos y estÃ¡n en faltante.

DECLARE vidProdEstado bigint unsigned;
DECLARE vidProdPieza bigint unsigned;
DECLARE vstock_actual smallint;
DECLARE vbajo_stock smallint;

SELECT NEW.idProdEstado INTO vidProdEstado;
SELECT NEW.idProdPieza INTO vidProdPieza;


IF vidProdEstado = 2 -- Si se vendiÃ³ una pieza
THEN
  -- Verifico cuantas de las mismas quedaron disponibles luego de hacer la venta
    SELECT COUNT(*) 
    INTO vstock_actual 
    FROM prod_piezas_stock prod
    WHERE prod.idProdPieza = vidProdPieza
    AND prod.idProdEstado = 1; -- Estado disponible
    
    -- Traigo el bajo stock parametrizado de la pieza
    SELECT piez.bajo_stock
    INTO vbajo_stock
    FROM prod_piezas piez
    WHERE piez.idProdPieza = vidProdEstado;
    
    -- Chequeo si bajÃ³ el stock por debajo del lÃ­mite
    IF vstock_actual <= vbajo_stock
    THEN
    -- Lanza alerta si el stock es bajo
        INSERT INTO acc_alertas (fecha_Alerta,varchar_1,varchar_2)
      VALUES(NOW(),'Alerta - Bajo stock de pieza', vidProdPieza);
    END IF;
  
END IF;

END$$
DELIMITER ;