
INSERT INTO roles(rolename)
VALUES ('BUYER'), ('SELLER');

INSERT INTO users (username, password, email) VALUES
                                                      ('gebruiker1', 'wachtwoord1', 'gebruiker1@example.com'),
                                                      ('gebruiker2', 'wachtwoord2', 'gebruiker2@example.com'),
                                                      ('gebruiker3', 'wachtwoord3', 'gebruiker3@example.com');


INSERT INTO categories (name, description) VALUES
                                                   ('Elektronica', 'Allerlei elektronische apparaten en gadgets'),
                                                   ('Boeken', 'Verschillende genres van boeken en literatuur'),
                                                   ('Kleding', 'Kleding en accessoires voor alle gelegenheden'),
                                                   ('Sport & Vrije tijd', 'Artikelen voor sportactiviteiten en recreatie'),
                                                   ('Huis & Tuin', 'Producten voor huisinrichting en tuinieren');


INSERT INTO products (name, description, price, user_id, category_id) VALUES
                                                                              ('Product 1', 'Beschrijving van Product 1', 10.00, 1, 1),
                                                                              ('Product 2', 'Beschrijving van Product 2', 15.99, 3, 1),
                                                                              ('Product 3', 'Beschrijving van Product 3', 20.99, 2, 2),
                                                                              ('Product 4', 'Beschrijving van Product 4', 25.99, 2, 2),
                                                                              ('Product 5', 'Beschrijving van Product 5', 30.99, 3, 3);

