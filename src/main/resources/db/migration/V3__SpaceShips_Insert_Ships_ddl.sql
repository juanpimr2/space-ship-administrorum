-- Comienza una transacción
BEGIN;

-- Inserts para las facciones correspondientes a las naves de Warhammer 40k
INSERT INTO faction (code, description) VALUES ('IP', 'IMPERIUM');
INSERT INTO faction (code, description) VALUES ('CH', 'CHAOS');
INSERT INTO faction (code, description) VALUES ('EL', 'ELDAR');
INSERT INTO faction (code, description) VALUES ('ORKS', 'ORKS');
INSERT INTO faction (code, description) VALUES ('NE', 'NECRONS');

-- Inserts de naves para la facción "Imperium"
INSERT INTO space_ship (name, faction_code, description) 
VALUES 
('Pride of the Emperor', 'IP', 'Buque insignia del Primarca Fulgrim y la Legión de los Hijos del Emperador'),
('Thunderhawk Gunship', 'IP', 'Versátil nave de despliegue rápido utilizada por los Marines Espaciales.'),
('Phalax', 'IP', 'Fortaleza-monasterio móvil del Capítulo de los Puños Imperiales y la mayor nave estelar al servicio del Imperio.'),
('Imperial Navy Cruiser', 'IP', 'Nave de batalla capital utilizada por la Armada Imperial.'),
('Photep', 'IP', 'Buque insignia de la Legión de los Mil Hijos');

-- Inserts de naves para la facción "Chaos"
INSERT INTO space_ship (name, faction_code, description) 
VALUES 
('The Treacherous', 'CH', 'Acorazado de clase profanadora y buque insignia del maestro de guerra Heinrich Bale.'),
('Hell Talon Fighter-Bomber', 'CH', 'Caza-bombardero ágil y letal utilizado por las fuerzas del Caos.'),
('Despoiler-class Battleship', 'CH', 'Nave de batalla corrupta y retorcida, símbolo de la flota del Caos.'),
('Conqueror', 'CH', 'Nave de batalla del Caos con armamento pesado y defensas avanzadas, líder de las flotas del Caos en la guerra espacial.'),
('Vengeful Spirit-class Battleship', 'CH', 'Nave de batalla emblemática del Caos, conocida por su poder devastador y su papel en la herejía de Horus.');

-- Inserts de naves para la facción "Eldar"
INSERT INTO space_ship (name, faction_code, description) 
VALUES 
('Nightshade Interceptor', 'EL', 'Caza interceptor rápido y ágil utilizado por los Eldar.'),
('Void Stalker', 'EL', 'La Void Stalker es la más grande e imponente de las naves Eldar, conocida por su tamaño colosal y su poder devastador en combate estelar.'),
('Wave Serpent', 'EL', 'Transporte blindado utilizado por las fuerzas de los Eldar.'),
('Void Spinner', 'EL', 'Nave de asalto dedicada a la guerra espacial utilizada por los Eldar.'),
('Phoenix Ship', 'EL', 'Nave espacial ágil y poderosa, conocida por su elegancia y tecnología avanzada.');

-- Inserts de naves para la facción "Orks"
INSERT INTO space_ship (name, faction_code, description) 
VALUES 
('Hammer Battlekroozer', 'ORKS', '¡Mákinota orka, grande y molona, ¡KRUMP!'),
('Dakka Jet', 'ORKS', '¡Caza-bomba orko, RAAAPIDAA!'),
('Slamblasta', 'ORKS', '¡Nave orka, ¡Krump y destroza!'),
('Mega Armoured Nobz', 'ORKS', '¡Nobles orkos, ¡Gordotes y pegones!'),
('Savage Gunship', 'ORKS', '¡GRRRANDE Y PODEROZA! ¡Cañones ser los más fuertes, hacemos bam bam!');

-- Inserts de naves para la facción "Necrones"
INSERT INTO space_ship (name, faction_code, description) 
VALUES 
('Doomsday Ark', 'NE', 'Arca de los Sigilos con poderosos cañones de energía que devastan a sus enemigos.'),
('Night Scythe', 'NE', 'Nave de transporte aéreo utilizada por los Necrones para desplegar unidades de guerreros.'),
('Tesseract Ark', 'NE', 'Arca de los Sigilos equipada con tecnología tesseract para manipular el tiempo y el espacio.'),
('Scythe-class Harvest Ship', 'NE', 'Nave de recolección utilizada por los Necrones para cosechar recursos y almas.'),
('Monolith', 'NE', 'Colosal fortaleza voladora equipada con armamento pesado y capaz de teletransportarse a través de la realidad.');

-- Commit de la transacción
COMMIT;
