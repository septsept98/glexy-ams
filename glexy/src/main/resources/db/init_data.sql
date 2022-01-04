INSERT INTO roles (name_role,code,"version",created_by,created_at,is_active,id) VALUES
	('superadmin','SA',0,'1',now(),true,'1'),
	('system','S',0,'1',now(),true,'2');
	

INSERT INTO companies(names, code, address, description, email, website, phone_number, fax, "version", created_by, created_at, is_active, id) VALUES 
	('Lawencon', 'LWN', 'jl. mana nih', 'perusahaan a', 'a@gmail.com', 'www.lawencon.com', '021545677', '0214543', 0, '1', now(), true,'1'),
	('Linov', 'LNV', 'jl. ajah', 'perusahaan b', 'b@gmail.com', 'www.linov.com', '021545444', '0214444', 0, '1', now(), true,'2');


INSERT INTO employees(name_employee, nip, email_employee, phone_number, gender,company_id, "version", created_by, created_at, is_active,id) VALUES 
	('super admin', 'k227', 'septianardi053@gmail.com', '0894300', 'male','1', 0, '1', now(), true,'1'),
	('system', 'k228', 'glexyamms03@gmail.com', '0895673', 'male','1', 0, '1', now(), true,'2');
	


INSERT INTO users(email, pass, roles_id,employee_id, "version", created_by, created_at, is_active,id) VALUES
	('septianardi053@gmail.com','$2a$10$Tv9mievZDYyio/NcGkVQW.Ho7qC9vPO5CzSDFWVofdChYNVIET/lm', '1', '1', 0, '1', now(), true,'1'),
	('glexyamms03@gmail.com', '$2a$10$Tv9mievZDYyio/NcGkVQW.Ho7qC9vPO5CzSDFWVofdChYNVIET/lm', '2', '2', 0, '1', now(), true,'2');

