
/*INSERT INTO role (id, name) VALUES (1, 'IPC Operations Admin'), (2, 'IPC Operations User'), (3, 'Manufacturing'), (4, 'Customer Admin'), (5, 'Customer End User');*/
INSERT INTO role (id, name) VALUES (1, 'IPC Operations Admin'), (2, 'IPC Operations User'), (3, 'Customer End User');

INSERT INTO company(id,name) VALUES (1,'IPC Systems');

/* password : ipcdefaultopsuser */
INSERT INTO user (id,email,firstName,lastName,status,company_id,role_id,password, site_id) VALUES
        (1,'defaultunigy360opsuser@ipc.com','defaultopsuserfirstname','defaultopsuserlastname','REGISTERED',1,1,'$2a$10$D64KDU9FFb7F7UpBzKOnFO6bTsbLKHCzwKzh0tT9IwrDfgqi4Sw1O', 'site_id');