INSERT INTO roles (name_role,code,"version",created_by,created_at,is_active,id) VALUES
	('human resource','HR',0,'1',now(),true,'1'),
	('superadmin','SA',0,'1',now(),true,'2'),
	('admin','ADM',0,'1',now(),true,'3');

	
INSERT INTO permissions (name_permission,code,"version",created_by,created_at,is_active,id) VALUES
	('master','MA',0,'1',now(),true,'1'),
	('transaction','TR',0,'1',now(),true,'2'),
	('history','HT',0,'1',now(),true,'3'),
	('inventory','INV',0,'1',now(),true,'4');

	
INSERT INTO permission_role (roles_id,permissions_id,"version",created_by,created_at,is_active,id) VALUES
	('1','2',0,'1',now(),true,'1'),
	('1','3',0,'1',now(),true,'2'),
	('1','4',0,'1',now(),true,'3'),
	('2','1',0,'1',now(),true,'4'),
	('2','2',0,'1',now(),true,'5'),
	('2','3',0,'1',now(),true,'6'),
	('2','4',0,'1',now(),true,'7');


INSERT INTO companies(names, code, address, description, email, website, phone_number, fax, "version", created_by, created_at, is_active, id) VALUES 
	('Lawencon', 'LWN', 'jl. mana nih', 'perusahaan a', 'a@gmail.com', 'www.lawencon.com', '021545677', '0214543', 0, 1, now(), true,'1'),
	('Linov', 'LNV', 'jl. ajah', 'perusahaan b', 'b@gmail.com', 'www.linov.com', '021545444', '0214444', 0, 1, now(), true,'2');


INSERT INTO employees(name_employee, nip, email_employee, phone_number, gender,company_id, "version", created_by, created_at, is_active,id) VALUES 
	('septian', 'k227', 'septianardi053@gmail.com', '0894300', 'male','1', 0, 1, now(), true,'1'),
	('glenn', 'k228', 'glenn9808@gmail.com', '0895673', 'male','1', 0, 1, now(), true,'2'),
	('febri', 'k229', 'febri.ufairahasan20@gmail.com', '08397466', 'male','2', 0, 1, now(), true,'3');


INSERT INTO users(email, pass, roles_id,employee_id, "version", created_by, created_at, is_active,id) VALUES
	('septianardi053@gmail.com', '1234', '1', '1', 0, 1, now(), true,'1'),
	('glenn9808@gmail.com', '1234', '2', '2', 0, 1, now(), true,'2');


INSERT INTO status_assets(code_status_asset,name_status_asset,"version",created_by,created_at,is_active, id) VALUES
		('SA1','Deployable',0,'1',now(),true, '1'),
		('SA2','Undeployble',0,'1',now(),true, '2'),
		('SA3','Archived',0,'1',now(),true, '3'),
		('SA4','Pending',0,'1',now(),true, '4'),
		('SA5','Assign',0,'1',now(),true, '5');


INSERT INTO status_transactions(code_status_tr,name_status_tr,status_asset_id,"version",created_by,created_at,is_active, id) VALUES
		('STR1','Ready to Deploy','1',0,'1',now(),true,'1'),
		('STR2','Broken','2',0,'1',now(),true,'2'),
		('STR3','Lost','2',0,'1',now(),true,'3'),
		('STR4','Repair','3',0,'1',now(),true,'4');


INSERT INTO asset_types(names, code, "version", created_by, created_at, is_active, id) VALUES 
	('General', 'GNR', 0, 1, now(), true, '1'),
	('Lisence', 'LSC', 0, 1, now(), true, '2'),
	('Consumable', 'CSM', 0, 1, now(), true, '3'),
	('Component', 'CPN', 0, 1, now(), true, '4');

	
INSERT INTO invoices(code, purchase_date, total_price, "version", created_by, created_at, is_active, id) VALUES 
	('12345677', now(), 100000, 0, 1, now(), true, '1'),
	('12345688', now(), 110000, 0, 1, now(), true, '2'),
	('12345699', now(), 120000, 0, 1, now(), true, '3'),
	('12345600', now(), 130000, 0, 1, now(), true, '4'),
	('12345611', now(), 140000, 0, 1, now(), true, '5');


INSERT INTO locations(name_place, code, company_id, "version", created_by, created_at, is_active, id) VALUES 
	('Bootcamp Room', 'BRM', '1', 0, 1, now(), true, '1'),
	('Meeting Room', 'MRM', '2', 0, 1, now(), true, '2');


INSERT INTO brands (names,code,"version",created_by,created_at,is_active,id) VALUES
	('lenovo','ln',0,'1',now(),true,'1'),
	('snowman','sw',0,'1',now(),true,'2');

INSERT INTO inventories(code, name_asset, stock, latest_stock, "version", created_by, created_at, is_active,id) VALUES 
	('LTP', 'Laptop', 10, 10, 0, 1, now(), true,'1'),
	('SPL', 'Spidol Snowman', 10, 10, 0, 1, now(), true, '2');


INSERT INTO assets(names, code, status_asset_id,brand_id, invoice_id, company_id, asset_type_id, inventory_id, "version", created_by, created_at, is_active, id) VALUES 
	('Laptop', 'LTP1', '1','1', '1','1', '1', '1', 0, 1, now(), true, '1'),
	('Laptop', 'LTP2', '1','1', '1','1', '1', '1', 0, 1, now(), true, '2'),
	('Laptop', 'LTP3', '1','1', '1','1', '1', '1', 0, 1, now(), true, '3'),
	('Laptop', 'LTP4', '1','1', '1','1', '1', '1', 0, 1, now(), true, '4'),
	('Laptop', 'LTP5', '1','1', '1','1', '1', '1', 0, 1, now(), true, '5'),
	('Laptop', 'LTP6', '1','1', '1','1', '1', '1', 0, 1, now(), true, '6'),
	('Laptop', 'LTP7', '1','1', '1','1', '1', '1', 0, 1, now(), true, '7'),
	('Laptop', 'LTP8', '1','1', '1','1', '1', '1', 0, 1, now(), true, '8'),
	('Laptop', 'LTP9', '1','1', '1','1', '1', '1', 0, 1, now(), true, '9'),
	('Laptop', 'LTP10', '1','1', '1','1', '1', '1', 0, 1, now(), true, '10'),
	('Spidol', 'SPL1', '2','2', '2','2', '4', '2', 0, 1, now(), true, '11');


INSERT INTO transactions(code_transaction,checkout_date,quantity,location_id, users_id,"version",created_by,created_at,is_active, id) VALUES 
	('TR1',now(),5,'1', '1', 0, 1, now(), true, '1'),
	('TR2',now(),5,'2', '1',0, 1, now(), true, '2');


INSERT INTO transaction_details(transaction_id,duration_date,asset_id,status_asset_checkout_id,"version",created_by,created_at,is_active,id) VALUES
	('1','2021-12-23','1','1',0, 1, now(), true,'1'),
	('1','2021-12-28','1','1',0, 1, now(), true,'2');


INSERT INTO track_assets (code_asset,name_activity,date_activity,user_id,transaction_code,"version",created_by,created_at,is_active,id) VALUES
	('LTP1','Deployable',now(),'1','TR1',0,'1',now(),true,'1'),
	('SPL','Deployable',now(),'2','TR2',0,'1',now(),true,'2');
