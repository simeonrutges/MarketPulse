
INSERT INTO roles(rolename)
VALUES ('BUYER'), ('SELLER');

INSERT INTO users (username, password, email) VALUES
                                                      ('gebruiker1', 'wachtwoord1', 'gebruiker1@example.com'),
                                                      ('gebruiker2', 'wachtwoord2', 'gebruiker2@example.com'),
                                                      ('gebruiker3', 'wachtwoord3', 'gebruiker3@example.com');


INSERT INTO categories (id, name, description) VALUES
                                                   (1, 'Elektronica', 'Allerlei elektronische apparaten en gadgets'),
                                                   (2, 'Boeken', 'Verschillende genres van boeken en literatuur'),
                                                   (3, 'Kleding', 'Kleding en accessoires voor alle gelegenheden'),
                                                   (4, 'Sport & Vrije tijd', 'Artikelen voor sportactiviteiten en recreatie'),
                                                   (5, 'Huis & Tuin', 'Producten voor huisinrichting en tuinieren');


INSERT INTO products (id, name, description, price, user_id, category_id) VALUES
                                                                              (1, 'Product 1', 'Beschrijving van Product 1', 10.00, 1, 1),
                                                                              (2, 'Product 2', 'Beschrijving van Product 2', 15.99, 3, 1),
                                                                              (3, 'Product 3', 'Beschrijving van Product 3', 20.99, 2, 2),
                                                                              (4, 'Product 4', 'Beschrijving van Product 4', 25.99, 2, 2),
                                                                              (5, 'Product 5', 'Beschrijving van Product 5', 30.99, 3, 3);

