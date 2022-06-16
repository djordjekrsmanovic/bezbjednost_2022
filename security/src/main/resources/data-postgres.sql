
-- ADMINS

INSERT INTO public.client
(id, common_name, country, given_name, mail, organization, organization_unit, password, surname) VALUES
(1,'Admin Admin', 'Serbia', 'Admin', 'admin@gmail.com', 'PKI', 'PKI-Novi Sad', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 'Admin');

INSERT INTO public.client
(id, common_name, country, given_name, mail, organization, organization_unit, password, surname) VALUES
(2,'Rastislav Kukucka', 'Serbia', 'Rasti', 'rastislav@gmail.com', 'PKI', 'PKI-Novi Sad', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 'Rasti');

INSERT INTO public.client
(id, common_name, country, given_name, mail, organization, organization_unit, password, surname) VALUES
(3,'Djordje Krsmanovic', 'Serbia', 'Djordje', 'djordje@gmail.com', 'FTN', 'FTN-Novi Sad', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 'Djordje');

INSERT INTO public.client
(id, common_name, country, given_name, mail, organization, organization_unit, password, surname) VALUES
(4,'Bojan Prodanovic', 'Serbia', 'Bojan', 'bojan@gmail.com', 'FTN', 'FTN-Novi Sad', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 'Bojan');

INSERT INTO public.client
(id, common_name, country, given_name, mail, organization, organization_unit, password, surname) VALUES
(5,'Aleksandar Savic', 'Serbia', 'Aleksandar', 'aleksandar@gmail.com', 'FTN', 'FTN-Novi Sad', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 'Aleksandar');


-- USERS

INSERT INTO public.client
(id, common_name, country, given_name, mail, organization, organization_unit, password, surname) VALUES
(6,'User User', 'Serbia', 'NiceUser', 'user@gmail.com', 'PKI', 'PKI-Novi Sad', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 'User');

INSERT INTO public.client
(id, common_name, country, given_name, mail, organization, organization_unit, password, surname) VALUES
(7,'Pavle Stefanovic', 'Bosnia', 'pavle', 'pavle@gmail.com', 'Continental', 'Continental-Novi Sad', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 'Pavle');

INSERT INTO public.client
(id, common_name, country, given_name, mail, organization, organization_unit, password, surname) VALUES
(8,'Marko Njegosov', 'Serbia', 'Marko', 'marko@gmail.com', 'SuperORG', 'SORG-Beograd', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 'Marko');

INSERT INTO public.client
(id, common_name, country, given_name, mail, organization, organization_unit, password, surname) VALUES
(9,'XWS project', 'Serbia', 'xws', 'dislinkt@gmail.com', 'DislinktORG', 'XWS-ftn', '$2a$10$KqyJ6IcfwIwV4pkjbfQ7LeGlhRRHTlIKtPKS7ElLJ0NcsOP2Zgpqm', 'dislinkt');


INSERT INTO public.role (id, name) VALUES (1,'ADMIN');
INSERT INTO public.role (id, name) VALUES (2,'USER');

INSERT INTO user_role (user_id, role_id) VALUES (1,1);
INSERT INTO user_role (user_id, role_id) VALUES (2,1);
INSERT INTO user_role (user_id, role_id) VALUES (3,1);
INSERT INTO user_role (user_id, role_id) VALUES (4,1);
INSERT INTO user_role (user_id, role_id) VALUES (5,1);
INSERT INTO user_role (user_id, role_id) VALUES (6,2);
INSERT INTO user_role (user_id, role_id) VALUES (7,2);
INSERT INTO user_role (user_id, role_id) VALUES (8,2);
INSERT INTO user_role (user_id, role_id) VALUES (9,2);

INSERT INTO permission(id, name) VALUES (1, 'CREATE_CERTIFICATES_PERMISSION');
INSERT INTO permission(id, name) VALUES (2, 'GET_USERS_PERMISSION');
INSERT INTO permission(id, name) VALUES (3, 'REVOKE_CERTIFICATE_PERMISSION');
INSERT INTO permission(id, name) VALUES (4, 'CHECK_CERTIFICATE_CHAIN_PERMISSION');
INSERT INTO permission(id, name) VALUES (5, 'DOWNLOAD_CERTIFICATE_PERMISSION');
INSERT INTO permission(id, name) VALUES (6, 'GET_CERTIFICATES_PERMISSION');
INSERT INTO permission(id, name) VALUES (7, 'CREATE_ROOT_CERTIFICATE_PERMISSION');
INSERT INTO permission(id, name) VALUES (8, 'GET_CERTIFICATES_FOR_SIGNING_PERMISSION');
INSERT INTO permission(id, name) VALUES (9, 'GET_USERS_WITHOUT_PRINCIPAL_PERMISSION');

INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 1);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 2);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 3);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 4);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 5);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 6);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 7);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 8);
INSERT INTO roles_perms(role_id, permission_id) VALUES (1, 9);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 1);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 3);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 5);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 6);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 8);
INSERT INTO roles_perms(role_id, permission_id) VALUES (2, 9);
