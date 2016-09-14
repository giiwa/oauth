#drop table if exists gi_appid;
create table gi_appid
(
	id varchar(20) not null,
	appid varchar(20),
	secret varchar(40),
	name varchar(50),
	callback varchar(128),
	created bigint default 0,
	updated bigint default 0
);
create unique index gi_appid_index_appid on gi_appid(appid);

#drop table if exists gi_appcode;
create table gi_appcode
(
	id varchar(20) not null,
	code varchar(40),
	appid varchar(20),
	scope varchar(128),
	sid varchar(50),
	ip varchar(50),
	uid bigint,
	created bigint default 0,
	updated bigint default 0
);
create unique index gi_appcode_index_code on gi_appcode(code);

#drop table if exists gi_apptoken;
create table gi_apptoken
(
	id varchar(20) not null,
	token varchar(40),
	code varchar(40),
	appid varchar(20),
	scope varchar(128),
	uid bigint,
	created bigint default 0,
	updated bigint default 0
);
create unique index gi_apptoken_index_token on gi_apptoken(token);
