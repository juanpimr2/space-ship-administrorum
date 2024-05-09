-- Comienza una transacción
BEGIN;

-- Inserts para las facciones correspondientes a las naves de Warhammer 40k
INSERT INTO faction (code, name) VALUES ('IMPERIUM', 'Imperium');
INSERT INTO faction (code, name) VALUES ('CHAOS', 'Chaos');
INSERT INTO faction (code, name) VALUES ('ELDAR', 'Eldar');
INSERT INTO faction (code, name) VALUES ('ORKS', 'Orks');
INSERT INTO faction (code, name) VALUES ('NECRONS', 'Necrones');

-- Inserts de naves para la facción "Imperium"
INSERT INTO space_ship (name, faction_code, description) 
VALUES 
('Pride of the Emperor', 'IMPERIUM', 'Buque insignia del Primarca Fulgrim y la Legión de los Hijos del Emperador'),
('Thunderhawk Gunship', 'IMPERIUM', 'Versátil nave de despliegue rápido utilizada por los Marines Espaciales.'),
('Phalax', 'IMPERIUM', 'Fortaleza-monasterio móvil del Capítulo de los Puños Imperiales y la mayor nave estelar al servicio del Imperio.'),
('Imperial Navy Cruiser', 'IMPERIUM', 'Nave de batalla capital utilizada por la Armada Imperial.'),
('Photep', 'IMPERIUM', 'Buque insignia de la Legión de los Mil Hijos');

-- Inserts de naves para la facción "Chaos"
INSERT INTO space_ship (name, faction_code, description) 
VALUES 
('Chaos Warhound Titan', 'CHAOS', 'Versión corrupta del Warhound Titan, usado por los Seguidores del Caos.'),
('Hell Talon Fighter-Bomber', 'CHAOS', 'Caza-bombardero ágil y letal utilizado por las fuerzas del Caos.'),
('Despoiler-class Battleship', 'CHAOS', 'Nave de batalla corrupta y retorcida, símbolo de la flota del Caos.'),
('Conqueror', 'CHAOS', 'Nave de batalla del Caos con armamento pesado y defensas avanzadas, líder de las flotas del Caos en la guerra espacial.'),
('Vengeful Spirit-class Battleship', 'CHAOS', 'Nave de batalla emblemática del Caos, conocida por su poder devastador y su papel en la herejía de Horus.');

-- Inserts de naves para la facción "Eldar"
INSERT INTO space_ship (name, faction_code, description) 
VALUES 
('Nightshade Interceptor', 'ELDAR', 'Caza interceptor rápido y ágil utilizado por los Eldar.'),
('Void Stalker', 'ELDAR', 'La Void Stalker es la más grande e imponente de las naves Eldar, conocida por su tamaño colosal y su poder devastador en combate estelar.'),
('Wave Serpent', 'ELDAR', 'Transporte blindado utilizado por las fuerzas de los Eldar.'),
('Void Spinner', 'ELDAR', 'Nave de asalto dedicada a la guerra espacial utilizada por los Eldar.'),
('Phoenix Ship', 'ELDAR', 'Nave espacial ágil y poderosa, conocida por su elegancia y tecnología avanzada.');

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
('Doomsday Ark', 'NECRONS', 'Arca de los Sigilos con poderosos cañones de energía que devastan a sus enemigos.'),
('Night Scythe', 'NECRONS', 'Nave de transporte aéreo utilizada por los Necrones para desplegar unidades de guerreros.'),
('Tesseract Ark', 'NECRONS', 'Arca de los Sigilos equipada con tecnología tesseract para manipular el tiempo y el espacio.'),
('Scythe-class Harvest Ship', 'NECRONS', 'Nave de recolección utilizada por los Necrones para cosechar recursos y almas.'),
('Monolith', 'NECRONS', 'Colosal fortaleza voladora equipada con armamento pesado y capaz de teletransportarse a través de la realidad.');

-- Commit de la transacción
COMMIT;
