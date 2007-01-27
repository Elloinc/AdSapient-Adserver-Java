#
# Table structure for table 'user'
#
DROP TABLE users;
CREATE TABLE users (
  USER_ID bigint NOT NULL ,
  name varchar(240) NOT NULL  ,
  email varchar(240) NOT NULL ,
  login varchar(240) NOT NULL ,
  user_password varchar(240) NOT NULL ,
  REAL_USER_ID bigint, 
  user_role varchar (128) NOT NULL ,
  STATE_ID bigint NOT NULL ,
  PRIMARY KEY (USER_ID)
);


#
# Dumping data for table 'user'
#
INSERT INTO users VALUES('1','Administrator','admin@yourhost.com','admin','admin',null,'admin',2);