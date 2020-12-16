-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator
-- Ovo su sve imena kolona u tabelama db(Column anotacija u klasama), a ne imena polja u klasama

-- ROLES this represents the high-level roles of the user in the system; each role will have a set of low-level privileges
-- Each role has privileges
INSERT INTO authorities (id, role) VALUES (1, 'ROLE_ADMINISTRATOR');
INSERT INTO authorities (id, role) VALUES (2, 'ROLE_OPERATOR');
-- PERMISSIONS represents a low-level, granular privilege/authority in the system
-- granular authority unit
INSERT INTO privileges(id, permission) values (1, 'PERMISSION_FIND_ALL_CERTIFICATES');
INSERT INTO privileges(id, permission) values (2, 'PERMISSION_GET_RECOMMENDED_VALIDITY_PERIODS');
INSERT INTO privileges(id, permission) values (3, 'PERMISSION_FIND_CERTIFICATE_BY_SERIAL_NUMBER');
INSERT INTO privileges(id, permission) values (4, 'PERMISSION_GET_ALL_CAS_WHICH_ARE_NOT_REVOKED');
INSERT INTO privileges(id, permission) values (5, 'PERMISSION_GET_ALL_CERTIFICATES_AS_TREE_DATA');
INSERT INTO privileges(id, permission) values (6, 'PERMISSION_CREATE_NEW_CERTIFICATE');
INSERT INTO privileges(id, permission) values (7, 'PERMISSION_REVOKE_CERTIFICATE');
INSERT INTO privileges(id, permission) values (8, 'PERMISSION_SEARCH_LOGS');
INSERT INTO privileges(id, permission) values (9, 'PERMISSION_READ_ALARMS');
INSERT INTO privileges(id, permission) values (10, 'PERMISSION_READ_RAISED_ALARMS');
INSERT INTO privileges(id, permission) values (11, 'PERMISSION_WRITE_ALARMS');
INSERT INTO privileges(id, permission) values (12, 'PERMISSION_REPORTS');


-- USERS each user has roles
-- password is 'administrator' (bcrypt encoded)
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'administrator', 1, 'AUsername', '$2a$10$ZA/skdcjCMvCxRSYfmMq3.JUuMnMzOozRaiH6GOUqzjkY86TYxi86','AFirstName', 'ALastName', 'administrator@gmail.com', true, '2020-03-23 21:15:00', '0641234567', false);
-- password is 'operator' (bcrypt encoded)
INSERT INTO users (user_type, id, username, password, first_name, last_name, email, enabled,last_password_reset_date, phone_number, first_time) VALUES ( 'operator', 2, 'OUsername', '$2a$10$VnXFO25WuL4.jGOYVZkLDuZP82Oi2vMVovC21jgDWkZYddTfqT7Qy','OFirstName', 'OLastName', 'operator@gmail.com', true, '2020-03-20 22:22:40', '0647654321', false);


INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1); -- ROLE_ADMINISTRATOR
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2); -- ROLE_OPERATOR
-- PRIVILEGE ADMINISTRATOR
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 1);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 2);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 3);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 4);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 5);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 6);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 7);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 8);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 9);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 10);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 11);
INSERT INTO authority_privilege(authority_id, privilege_id) values (1, 12);
-- PRIVILEGE OPERATOR
INSERT INTO authority_privilege(authority_id, privilege_id) values (2, 8);
INSERT INTO authority_privilege(authority_id, privilege_id) values (2, 9);
INSERT INTO authority_privilege(authority_id, privilege_id) values (2, 12);




--
-- Dumping data for table `certificate`
--

LOCK TABLES `certificate` WRITE;
/*!40000 ALTER TABLE `certificate` DISABLE KEYS */;
INSERT INTO `certificate` VALUES (1000,'Defence First','2040-05-12 19:59:16.586000','2020-05-11 00:00:00.000000','1589306415049',NULL,_binary '\0','1589306415049',0),(2000,'Serbian Army','2030-05-12 20:00:21.514000','2020-05-11 00:00:00.000000','1589306415049',NULL,_binary '\0','1589306485071',1),(3000,'siem_centar','2030-05-12 20:01:38.257000','2020-05-11 00:00:00.000000','1589306415049',NULL,_binary '\0','1589306535643',1),(4000,'siem_agent','2030-05-12 20:02:24.804000','2020-05-11 00:00:00.000000','1589306415049',NULL,_binary '\0','1589306569889',1),(5000,'localhost','2040-05-12 19:59:16.586000','2020-05-11 00:00:00.000000','1589306415049',NULL,_binary '\0','1589314819549',1);
/*!40000 ALTER TABLE `certificate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `demo_table`
--
