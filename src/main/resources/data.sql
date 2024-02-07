
INSERT INTO roles(rolename)
VALUES ('BUYER'), ('SELLER'), ('ADMIN');

INSERT INTO users (username, password, email) VALUES
                                                      ('gebruiker1', '$2a$12$W91wFrPpzARW/VZ755bAouA5Kuwa1OewK9B2GXGVALmXkse8Hzrne', 'gebruiker1@example.com'),
                                                      ('gebruiker2', '$2a$12$W91wFrPpzARW/VZ755bAouA5Kuwa1OewK9B2GXGVALmXkse8Hzrne', 'gebruiker2@example.com'),
                                                      ('gebruiker3', 'wachtwoord3', 'gebruiker1@example.com'),
                                                      ('gebruiker4', 'wachtwoord4', 'gebruiker4@example.com');
-- ('gebruiker4', 'wachtwoord4', 'gebruiker4@example.com');


INSERT INTO categories (name, description) VALUES
                                                   ('Elektronica', 'Allerlei elektronische apparaten en gadgets'),
                                                   ('Boeken', 'Verschillende genres van boeken en literatuur'),
                                                   ('Kleding', 'Kleding en accessoires voor alle gelegenheden'),
                                                   ('Sport & Vrije tijd', 'Artikelen voor sportactiviteiten en recreatie'),
                                                   ('Huis & Tuin', 'Producten voor huisinrichting en tuinieren');

INSERT INTO users_roles (users_id, roles_rolename)
VALUES (1, 'ADMIN'),
       (2, 'BUYER'),
       (3, 'BUYER'),
       (4, 'SELLER');

INSERT INTO products (name, description, price, user_id, category_id) VALUES
                                                                              ('Product 1', 'Beschrijving van Product 1', 10.00, 4, 1),
                                                                              ('Product 2', 'Beschrijving van Product 2', 15.99, 4, 1),
                                                                              ('Product 3', 'Beschrijving van Product 3', 20.99, 4, 2),
                                                                              ('Product 4', 'Beschrijving van Product 4', 25.99, 4, 2),
                                                                              ('Product 5', 'Beschrijving van Product 5', 30.99, 4, 3);

