/*
Created		15. 3. 2019
Modified		12. 12. 2020
Project		
Model		
Company		
Author		
Version		
Database		Oracle 8 
*/


Drop table order
/
Drop table album
/
Drop table author
/
Drop table registry
/
Drop table store
/
Drop table user
/
Drop table song
/


Create table song
(
	song_id Number NOT NULL ,
	album_id Number NOT NULL ,
	author_id Number NOT NULL ,
	type Char(1) NOT NULL ,
	title Varchar2(30) NOT NULL 
)
/

Create table user
(
	user_id Integer NOT NULL  UNIQUE ,
	name Varchar2(30) NOT NULL ,
	surname Varchar2(30) NOT NULL ,
	user_type Char(1) NOT NULL 
)
/

Create table store
(
	store_id Number NOT NULL  UNIQUE ,
	name Varchar2(30) NOT NULL ,
	city Varchar2(30) NOT NULL ,
	street Varchar2(30) NOT NULL 
)
/

Create table registry
(
	song_id Number NOT NULL ,
	store_id Number NOT NULL 
)
/

Create table author
(
	author_id Number NOT NULL  UNIQUE ,
	name Varchar2(30) NOT NULL ,
	surname Varchar2(30) NOT NULL ,
	nationality Varchar2(30) NOT NULL 
)
/

Create table album
(
	album_id Number NOT NULL  UNIQUE ,
	title Varchar2(30) NOT NULL ,
	genre Varchar2(30) NOT NULL ,
	release_date Date NOT NULL 
)
/

Create table order
(
	song_id Number NOT NULL ,
	user_id Integer NOT NULL ,
	order_id Number NOT NULL  UNIQUE ,
	date Date
)
/


Alter table song add primary key (song_id) 
/
Alter table user add primary key (user_id) 
/
Alter table store add primary key (store_id) 
/
Alter table registry add primary key (song_id,store_id) 
/
Alter table author add primary key (author_id) 
/
Alter table album add primary key (album_id) 
/
Alter table order add primary key (song_id,user_id) 
/


Alter table registry add  foreign key (song_id) references song (song_id) 
/
Alter table order add  foreign key (song_id) references song (song_id) 
/
Alter table order add  foreign key (user_id) references user (user_id) 
/
Alter table registry add  foreign key (store_id) references store (store_id) 
/
Alter table song add  foreign key (author_id) references author (author_id) 
/
Alter table song add  foreign key (album_id) references album (album_id) 
/


/* Roles permissions */


/* Users permissions */


