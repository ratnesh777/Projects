---role insert-----
insert into role VALUES (1, 'IPC Operations'),
(2, 'Manufacturing'),
(3, 'Customer Admin'),
(4, 'Customer End User');

---company insert-----
insert into company VALUES (1000, 'some company');
insert into company VALUES (1001, 'IPC-TEST');

---user insert-----
INSERT INTO user (id, email, firstName, lastName, status, company_id, role_id, password, site_id)
VALUES (1, 'test@ipc.com', 'FN1', 'LN', 'CREATED', 1000, 1, '$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO', 'site id'), --password=WElcome12#
(2, 'test2@ipc.com', 'FN3', 'LN', 'REGISTERED', 1001, 2, '$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO', 'site id2'), --password=WElcome12#
 (3, 'test@gmail.com', 'FN2', 'LN', 'REGISTERED', 1000, 1, '$2a$10$bhe2VRUcV8ETXwWuLiXL8OuWpSjfrRDQ0CZkPeujp2igGVbfaDkgO', 'site id'); --password=WElcome12#

INSERT INTO back_room (id, home_zone_ip, management_proxy_ip, name, voip_proxy_ip, company_id) VALUES
(1, '11.11.11.11', '12.22.22.22', 'back room 1', '13.33.33.33', 1001),
(2, '21.11.11.11', '22.22.22.22', 'back room 2', '23.33.33.33', 1000),
(3, '31.11.11.11', '32.22.22.22', 'back room 3', '33.33.33.33', 1001);

INSERT INTO product (id, name) VALUES
('1', 'touch'),
('2', 'pulse'),
('3', 'soft client');