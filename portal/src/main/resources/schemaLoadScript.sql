
INSERT INTO role (id, name) VALUES (1, 'Operations Admin'), (2, 'Operations User'), (3, 'Customer End User');

INSERT INTO company(id,name) VALUES (1,'MARS Systems');

/* password : Welcome12# */
INSERT INTO user (id,email,firstName,lastName,status,company_id,role_id,password, site_id) VALUES
        (1,'admin@mars.com','firstname','lastname','REGISTERED',1,1,'$2a$10$CdZpXJ0WJac0nE8RMvppOupZy1rmjIM5VAwytl0I9NiJen84Ue.Wm', 'site_id');