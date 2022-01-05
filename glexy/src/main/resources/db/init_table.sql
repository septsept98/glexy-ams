CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE files (
	id varchar(36) DEFAULT uuid_generate_v4(),
	file bytea,
	extension varchar(5),
	version integer,
	created_by text,
	created_at timestamp without time zone,
	updated_by text,
	updated_at timestamp without time zone,
	is_active boolean
	
);

CREATE TABLE permissions(
	id varchar(36) DEFAULT uuid_generate_v4(),
	name_permission varchar(30),
	code varchar(20),
	version int,
	created_by text,
	created_at timestamp,
	updated_by text,
	updated_at timestamp,
	is_active boolean
);

CREATE TABLE roles(
	id varchar(36) DEFAULT uuid_generate_v4(),
	name_role varchar(30),
	code varchar(20),
	version int,
	created_by text,
	created_at timestamp,
	updated_by text,
	updated_at timestamp,
	is_active boolean
);

CREATE TABLE permission_role(
	id varchar(36) DEFAULT uuid_generate_v4(),
	roles_id varchar(36),
	permissions_id varchar(36),
	version int,
	created_by text,
	created_at timestamp,
	updated_by text,
	updated_at timestamp,
	is_active boolean
);

CREATE TABLE employees(
	id varchar(36) DEFAULT uuid_generate_v4(),
	name_employee varchar(30),
	nip varchar(20),
	email_employee varchar(30),
	phone_number varchar(20),
	gender varchar(10),
	company_id varchar(36),
	version int,
	created_by text,
	created_at timestamp,
	updated_by text,
	updated_at timestamp,
	is_active boolean
);

CREATE TABLE companies(
	id varchar(36) DEFAULT uuid_generate_v4(),
	names varchar(30),
	code varchar(20),
	address text,
	description text,
	email varchar(30),
	website varchar(30),
	phone_number varchar(20),
	fax varchar(20),
	company_img varchar(36),
	version integer,
	created_by text,
	created_at timestamp with time zone,
	updated_by text,
	updated_at timestamp with time zone,
	is_active boolean
	
);

CREATE TABLE brands(
	id varchar(36) DEFAULT uuid_generate_v4(),
	names varchar(30),
	code varchar(20),
	version int,
	created_by text,
	created_at timestamp,
	updated_by text,
	updated_at timestamp,
	is_active boolean
);

CREATE TABLE users(
	id varchar(36) DEFAULT uuid_generate_v4(),
	email varchar(30),
	pass text,
	users_img varchar(36),
	roles_id varchar(36),
	employee_id varchar(36),
	version int,
	created_by text,
	created_at timestamp,
	updated_by text,
	updated_at timestamp,
	is_active boolean
);

CREATE TABLE asset_types(
	id varchar(36) DEFAULT uuid_generate_v4(),
	names varchar(30),
	code varchar(20),
	version integer,
	created_by text,
	created_at timestamp with time zone,
	updated_by text,
	updated_at timestamp with time zone,
	is_active boolean
	
);

CREATE TABLE inventories(
	id varchar(36) DEFAULT uuid_generate_v4(),
	code varchar(20),
	name_asset varchar(30),
	stock integer,
	latest_stock integer,
	version integer,
	created_by text,
	created_at timestamp with time zone,
	updated_by text,
	updated_at timestamp with time zone,
	is_active boolean
);


CREATE TABLE invoices(
	id varchar(36) DEFAULT uuid_generate_v4(),
	code varchar(20),
	purchase_date date,
	total_price integer,
	invoice_img varchar(36),
	version integer,
	created_by text,
	created_at timestamp with time zone,
	updated_by text,
	updated_at timestamp with time zone,
	is_active boolean
	
);

CREATE TABLE locations(
	id varchar(36) DEFAULT uuid_generate_v4(),
	name_place varchar(30),
	code varchar(20),
	company_id varchar(36),
	version integer,
	created_by text,
	created_at timestamp with time zone,
	updated_by text,
	updated_at timestamp with time zone,
	is_active boolean
	
);

CREATE TABLE status_assets(
	id varchar(36) DEFAULT uuid_generate_v4(),
	code_status_asset varchar(20),
	name_status_asset varchar(30),
	version integer,
	created_by text,
	created_at timestamp without time zone,
	updated_by text,
	updated_at timestamp without time zone,
	is_active boolean 
);

CREATE TABLE status_transactions(
	id varchar(36) DEFAULT uuid_generate_v4(),
	code_status_tr varchar(20),
	name_status_tr varchar(30),
	status_asset_id varchar(36),
	version integer,
	created_by text,
	created_at timestamp without time zone,
	updated_by text,
	updated_at timestamp without time zone,
	is_active boolean
);

CREATE TABLE assets(
	id varchar(36) DEFAULT uuid_generate_v4(),
	names varchar(30),
	code varchar(20),
	expired_date date,
	asset_img varchar(36),
	status_asset_id varchar(36),
	invoice_id varchar(36),
	company_id varchar(36),
	asset_type_id varchar(36),
	inventory_id varchar(36),
	brand_id varchar(36),
	version integer,
	created_by text,
	created_at timestamp with time zone,
	updated_by text,
	updated_at timestamp with time zone,
	is_active boolean
);


CREATE TABLE transactions(
	id varchar(36) DEFAULT uuid_generate_v4(),
	code_transaction varchar(20),
	checkout_date date,
	description text,
	employee_id varchar(36),
	location_id varchar(36),
	asset_general_id varchar(36),
	users_id varchar(36),
	version integer,
	created_by text,
	created_at timestamp without time zone,
	updated_by text,
	updated_at timestamp without time zone,
	is_active boolean
);

CREATE TABLE transaction_details(
	id varchar(36) DEFAULT uuid_generate_v4(),
	transaction_id varchar(36),
	duration_date date,
	asset_id varchar(36),
	status_asset_checkout_id varchar(36),
	date_checkin date,
	status_tr_checkin_id varchar(36),
	status_email boolean,
	version integer,
	created_by text,
	created_at timestamp without time zone,
	updated_by text,
	updated_at timestamp without time zone,
	is_active boolean
);

CREATE TABLE track_assets(
	id varchar(36) DEFAULT uuid_generate_v4(),
	code_asset varchar(20),
	name_activity varchar(30),
	date_activity date,
	user_id varchar(36),
	transaction_code varchar(20),
	version integer,
	created_by text,
	created_at timestamp without time zone,
	updated_by text,
	updated_at timestamp without time zone,
	is_active boolean
);


ALTER TABLE asset_types
	ADD CONSTRAINT asset_types_id PRIMARY KEY(id);
ALTER TABLE invoices
	ADD CONSTRAINT invoices_id PRIMARY KEY(id);
ALTER TABLE locations
	ADD CONSTRAINT locations_id PRIMARY KEY(id);
ALTER TABLE companies
	ADD CONSTRAINT companies_id PRIMARY KEY(id);
ALTER TABLE assets
	ADD CONSTRAINT assets_id PRIMARY KEY(id);


ALTER TABLE files
	ADD CONSTRAINT files_id PRIMARY KEY(id),
	ALTER COLUMN file SET NOT NULL,
	ALTER COLUMN extension SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;


ALTER TABLE permissions
	ADD CONSTRAINT permissions_pk PRIMARY KEY(id),
	ADD CONSTRAINT permissions_bk UNIQUE(code),
	ALTER COLUMN name_permission SET NOT NULL,
	ALTER COLUMN code SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;


ALTER TABLE roles
	ADD CONSTRAINT roles_pk PRIMARY KEY(id),
	ADD CONSTRAINT roles_bk UNIQUE(code),
	ALTER COLUMN name_role SET NOT NULL,
	ALTER COLUMN code SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;


ALTER TABLE permission_role
	ADD CONSTRAINT pd_pk PRIMARY KEY(id),
	ADD CONSTRAINT roles_fk FOREIGN KEY(roles_id)
		REFERENCES roles(id),
	ADD CONSTRAINT permission_fk FOREIGN KEY(permissions_id)
		REFERENCES permissions(id),
	ALTER COLUMN roles_id SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN permissions_id SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;	


ALTER TABLE employees 
	ADD CONSTRAINT employees_pk PRIMARY KEY(id),
	ADD CONSTRAINT employees_bk UNIQUE(nip),
	ADD CONSTRAINT companies_fk FOREIGN KEY(company_id)
		REFERENCES companies(id),
	ALTER COLUMN name_employee SET NOT NULL,
	ALTER COLUMN nip SET NOT NULL,
	ALTER COLUMN phone_number SET NOT NULL,
	ALTER COLUMN gender SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;
	
	
ALTER TABLE companies
	ADD CONSTRAINT companies_bk UNIQUE (code),
	ADD CONSTRAINT files_fk FOREIGN KEY(company_img)
		REFERENCES files(id);
ALTER TABLE companies
	ALTER COLUMN names set not null,
	ALTER COLUMN address set not null,
	ALTER COLUMN description set not null,
	ALTER COLUMN email set not null,
	ALTER COLUMN phone_number set not null,
	ALTER COLUMN website set not null,
	ALTER COLUMN fax set not null,
	ALTER COLUMN version set not null,
	ALTER COLUMN created_by set not null,
	ALTER COLUMN created_at set not null,
	ALTER COLUMN is_active set not null;

ALTER TABLE brands
	ADD CONSTRAINT brands_pk PRIMARY KEY(id),
	ADD CONSTRAINT brands_bk UNIQUE(code),
	ALTER COLUMN names SET NOT NULL,
	ALTER COLUMN code SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;


ALTER TABLE users
	ADD CONSTRAINT users_pk PRIMARY KEY(id),
	ADD CONSTRAINT users_bk UNIQUE(email),
	ADD CONSTRAINT roles_fk FOREIGN KEY(roles_id)
		REFERENCES roles(id),
	ADD CONSTRAINT employees_fk FOREIGN KEY(employee_id)
		REFERENCES employees(id),
	ADD CONSTRAINT files_fk FOREIGN KEY(users_img)
		REFERENCES files(id),
	ALTER COLUMN email SET NOT NULL,
	ALTER COLUMN pass SET NOT NULL,
	ALTER COLUMN roles_id SET NOT NULL,
	ALTER COLUMN employee_id SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;


ALTER TABLE asset_types
	ADD CONSTRAINT asset_types_bk UNIQUE (code);
ALTER TABLE asset_types
	ALTER COLUMN names set not null,
	ALTER COLUMN code set not null,
	ALTER COLUMN version set not null,
	ALTER COLUMN created_by set not null,
	ALTER COLUMN created_at set not null,
	ALTER COLUMN is_active set not null;

ALTER TABLE inventories 
	ADD CONSTRAINT inventories_pk PRIMARY KEY(id),
	ADD CONSTRAINT inventories_bk UNIQUE(code);
ALTER TABLE inventories 
	ALTER COLUMN id SET NOT NULL,
	ALTER COLUMN code SET NOT NULL,
	ALTER COLUMN name_asset SET NOT NULL,
	ALTER COLUMN stock SET NOT NULL,
	ALTER COLUMN latest_stock SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;


ALTER TABLE invoices
	ADD CONSTRAINT invoices_bk UNIQUE (code),
	ADD CONSTRAINT files_fk FOREIGN KEY(invoice_img)
		REFERENCES files(id);

ALTER TABLE invoices
	ALTER COLUMN code set not null,
	ALTER COLUMN purchase_date set not null,
	ALTER COLUMN total_price set not null,
	ALTER COLUMN version set not null,
	ALTER COLUMN created_by set not null,
	ALTER COLUMN created_at set not null,
	ALTER COLUMN is_active set not null;


ALTER TABLE locations
	ALTER COLUMN name_place set not null,
	ALTER COLUMN code set not null,
	ALTER COLUMN company_id set not null,
	ALTER COLUMN version set not null,
	ALTER COLUMN created_by set not null,
	ALTER COLUMN created_at set not null,
	ALTER COLUMN is_active set not null;

ALTER TABLE locations
	ADD CONSTRAINT locations_bk UNIQUE (code),
	ADD CONSTRAINT companies_fk FOREIGN KEY(company_id)
		REFERENCES companies(id);


ALTER TABLE status_assets 
	ADD CONSTRAINT status_pk PRIMARY KEY(id),
	ADD CONSTRAINT status_bk UNIQUE(code_status_asset);

ALTER TABLE status_assets 
	ALTER COLUMN id SET NOT NULL,
	ALTER COLUMN code_status_asset SET NOT NULL,
	ALTER COLUMN name_status_asset SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;


ALTER TABLE status_transactions 
	ADD CONSTRAINT status_tr_pk PRIMARY KEY(id),
	ADD CONSTRAINT status_tr_bk UNIQUE(code_status_tr),
	ADD CONSTRAINT status_assets_fk FOREIGN KEY(status_asset_id)
		REFERENCES status_assets(id);

ALTER TABLE status_transactions 
	ALTER COLUMN id SET NOT NULL,
	ALTER COLUMN code_status_tr SET NOT NULL,
	ALTER COLUMN name_status_tr SET NOT NULL,
	ALTER COLUMN status_asset_id SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;

ALTER TABLE assets
	ADD CONSTRAINT assets_bk UNIQUE (code),
	ADD CONSTRAINT status_assets_fk FOREIGN KEY(status_asset_id)
		REFERENCES status_assets(id),
	ADD CONSTRAINT invoices_fk FOREIGN KEY(invoice_id)
		REFERENCES invoices(id),
	ADD CONSTRAINT companies_fk FOREIGN KEY(company_id)
		REFERENCES companies(id),
	ADD CONSTRAINT inventories_fk FOREIGN KEY(inventory_id)
		REFERENCES inventories(id),
	ADD CONSTRAINT asset_types_fk FOREIGN KEY(asset_type_id)
		REFERENCES asset_types(id),
	ADD CONSTRAINT files_fk FOREIGN KEY(asset_img)
		REFERENCES files(id),
	ADD CONSTRAINT brands_fk FOREIGN KEY(brand_id)
		REFERENCES brands(id);
	
ALTER TABLE assets
	ALTER COLUMN names set not null,
	ALTER COLUMN code set not null,
	ALTER COLUMN status_asset_id set not null,
	ALTER COLUMN invoice_id set not null,
	ALTER COLUMN company_id set not null,
	ALTER COLUMN asset_type_id set not null,
	ALTER COLUMN brand_id set not null,
	ALTER COLUMN version set not null,
	ALTER COLUMN created_by set not null,
	ALTER COLUMN created_at set not null,
	ALTER COLUMN is_active set not null;


ALTER TABLE transactions 
	ADD CONSTRAINT transactions_pk PRIMARY KEY(id),
	ADD CONSTRAINT transactions_bk UNIQUE(code_transaction),
	ADD CONSTRAINT employees_fk FOREIGN KEY(employee_id)
	REFERENCES employees(id),
	ADD CONSTRAINT locations_fk FOREIGN KEY(location_id)
	REFERENCES locations(id),
	ADD CONSTRAINT assets_fk FOREIGN KEY(asset_general_id)
	REFERENCES assets(id),
	ADD CONSTRAINT users_fk FOREIGN KEY(users_id)
	REFERENCES users(id);

ALTER TABLE transactions 
	ALTER COLUMN id SET NOT NULL,
	ALTER COLUMN code_transaction SET NOT NULL,
	ALTER COLUMN checkout_date SET NOT NULL,
	ALTER COLUMN users_id SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;


ALTER TABLE transaction_details 
	ADD CONSTRAINT td_pk PRIMARY KEY(id),
	ADD CONSTRAINT td_fk FOREIGN KEY(transaction_id)
	REFERENCES transactions(id),
	ADD CONSTRAINT status_fk FOREIGN KEY(status_asset_checkout_id)
	REFERENCES status_assets(id),
	ADD CONSTRAINT status_tr_fk FOREIGN KEY(status_tr_checkin_id)
	REFERENCES status_transactions(id);

ALTER TABLE transaction_details 
	ALTER COLUMN id SET NOT NULL,
	ALTER COLUMN transaction_id SET NOT NULL,
	ALTER COLUMN asset_id SET NOT NULL,
	ALTER COLUMN status_asset_checkout_id SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;
	

ALTER TABLE track_assets 
	ADD CONSTRAINT track_assets_pk PRIMARY KEY(id);
ALTER TABLE track_assets 
	ALTER COLUMN id SET NOT NULL,
	ALTER COLUMN code_asset SET NOT NULL,
	ALTER COLUMN name_activity SET NOT NULL,
	ALTER COLUMN date_activity SET NOT NULL,
	ALTER COLUMN transaction_code SET NOT NULL,
	ALTER COLUMN user_id SET NOT NULL,
	ALTER COLUMN version SET NOT NULL,
	ALTER COLUMN created_by SET NOT NULL,
	ALTER COLUMN created_at SET NOT NULL,
	ALTER COLUMN is_active SET NOT NULL;