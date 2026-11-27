-- =====================================================================
-- HoopsManager (Equipo-06) - Estructura de la base de datos (SQLite)
-- =====================================================================
-- Este script crea todas las tablas del proyecto.
-- Se puede ejecutar a mano (por ejemplo en DB Browser for SQLite, pestaña
-- "Ejecutar SQL") y además lo ejecuta GestorBD.crearTablas() al arrancar.
-- Usa CREATE TABLE IF NOT EXISTS, así que ejecutarlo varias veces no borra
-- nada: solo crea las tablas que falten.
--
-- Criterios:
--   - Cada tabla corresponde a una clase del paquete "clase".
--   - Las fechas (LocalDate) se guardan como TEXT con formato aaaa-mm-dd.
--   - Los enums se guardan como TEXT con su name() (BASE, TECNICO, LOCAL...).
--   - Las listas de las clases (List<Jugador> en Equipo, etc.) no son
--     columnas: se guardan como una clave foránea en la tabla "hija".
--   - NO escribir punto y coma dentro de los comentarios: GestorBD separa
--     las sentencias por ese carácter.
-- =====================================================================


-- Club: un club puede tener varios equipos
CREATE TABLE IF NOT EXISTS Club (
	id          INTEGER PRIMARY KEY AUTOINCREMENT,
	nombre      TEXT NOT NULL,
	direccion   TEXT,
	telefono    TEXT,
	correo      TEXT,
	ciudad      TEXT
);

-- Entrenador
CREATE TABLE IF NOT EXISTS Entrenador (
	id          INTEGER PRIMARY KEY AUTOINCREMENT,
	nombre      TEXT NOT NULL,
	apellidos   TEXT,
	telefono    TEXT,
	correo      TEXT
);

-- Equipo: pertenece a un club y tiene un entrenador
CREATE TABLE IF NOT EXISTS Equipo (
	id             INTEGER PRIMARY KEY AUTOINCREMENT,
	nombre         TEXT NOT NULL,
	categoria      TEXT,              -- enum Categoria (SENIOR, JUNIOR...)
	nivel          TEXT,              -- enum Nivel (PROVINCIAL, NACIONAL...)
	temporada      TEXT,              -- por ejemplo 2026-2027
	club_id        INTEGER,
	entrenador_id  INTEGER,
	FOREIGN KEY (club_id)       REFERENCES Club(id),
	FOREIGN KEY (entrenador_id) REFERENCES Entrenador(id)
);

-- Jugador: pertenece a un equipo
CREATE TABLE IF NOT EXISTS Jugador (
	id          INTEGER PRIMARY KEY AUTOINCREMENT,
	nombre      TEXT NOT NULL,
	apellidos   TEXT NOT NULL,
	dorsal      INTEGER NOT NULL,
	posicion    TEXT,                 -- enum Posicion (BASE, ESCOLTA...)
	edad        INTEGER,
	altura      REAL,                 -- en cm
	peso        REAL,                 -- en kg
	estado      TEXT,                 -- enum EstadoJugador (DISPONIBLE, LESIONADO)
	ruta_foto   TEXT,
	equipo_id   INTEGER,
	FOREIGN KEY (equipo_id) REFERENCES Equipo(id),
	UNIQUE (equipo_id, dorsal)        -- no puede haber dos dorsales iguales en un equipo
);

-- Partido: lo juega un equipo contra un rival
CREATE TABLE IF NOT EXISTS Partido (
	id             INTEGER PRIMARY KEY AUTOINCREMENT,
	rival          TEXT NOT NULL,
	fecha          TEXT NOT NULL,
	localizacion   TEXT,              -- enum LocalizacionPartido (LOCAL, VISITANTE)
	puntos_equipo  INTEGER,           -- NULL mientras no se ha jugado
	puntos_rival   INTEGER,           -- NULL mientras no se ha jugado
	equipo_id      INTEGER,
	FOREIGN KEY (equipo_id) REFERENCES Equipo(id)
);

-- Estadistica: lo que hizo un jugador en un partido
CREATE TABLE IF NOT EXISTS Estadistica (
	id               INTEGER PRIMARY KEY AUTOINCREMENT,
	partido_id       INTEGER NOT NULL,
	jugador_id       INTEGER NOT NULL,
	puntos           INTEGER DEFAULT 0,
	rebotes          INTEGER DEFAULT 0,
	asistencias      INTEGER DEFAULT 0,
	faltas           INTEGER DEFAULT 0,
	minutos_jugados  INTEGER DEFAULT 0,
	FOREIGN KEY (partido_id) REFERENCES Partido(id),
	FOREIGN KEY (jugador_id) REFERENCES Jugador(id),
	UNIQUE (partido_id, jugador_id)   -- una sola fila por jugador y partido
);

-- Entrenamiento: sesión de un equipo
CREATE TABLE IF NOT EXISTS Entrenamiento (
	id             INTEGER PRIMARY KEY AUTOINCREMENT,
	fecha          TEXT NOT NULL,
	tipo           TEXT,              -- enum TipoEntrenamiento (FISICO, TECNICO...)
	observaciones  TEXT,
	equipo_id      INTEGER,
	FOREIGN KEY (equipo_id) REFERENCES Equipo(id)
);

-- Asistencia: qué jugadores fueron a cada entrenamiento (relación N a N)
CREATE TABLE IF NOT EXISTS Asistencia (
	entrenamiento_id  INTEGER NOT NULL,
	jugador_id        INTEGER NOT NULL,
	PRIMARY KEY (entrenamiento_id, jugador_id),
	FOREIGN KEY (entrenamiento_id) REFERENCES Entrenamiento(id),
	FOREIGN KEY (jugador_id)       REFERENCES Jugador(id)
);

-- Lesion: lesión de un jugador
CREATE TABLE IF NOT EXISTS Lesion (
	id                           INTEGER PRIMARY KEY AUTOINCREMENT,
	jugador_id                   INTEGER NOT NULL,
	tipo_lesion                  TEXT NOT NULL,
	fecha_inicio                 TEXT NOT NULL,
	fecha_estimada_recuperacion  TEXT,
	observaciones                TEXT,
	FOREIGN KEY (jugador_id) REFERENCES Jugador(id)
);