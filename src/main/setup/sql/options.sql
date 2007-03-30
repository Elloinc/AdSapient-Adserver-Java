
#
# Table structure for table 'options'
#
drop table  options;
CREATE TABLE options (
    ITEM_ID  bigint NOT NULL ,
  ITEM_NAME varchar(128),
  ITEM_VALUE varchar(200),
  PRIMARY KEY (ITEM_ID)
);


#
# Dumping data for table 'options'
#
INSERT INTO options VALUES(1,'mailServer','localhost');
INSERT INTO options VALUES(2,'mailServerLogin','');
INSERT INTO options VALUES(3,'mailServerPassword','');
INSERT INTO options VALUES(4,'targetWindow','2');
INSERT INTO options VALUES(5,'loadingType','2');
insert into options values (6,'databaseURL','jdbc:mysql://localhost:3306/adsapient?relaxAutoCommit=true;autoReconnect=true');
INSERT into options VALUES (7,'databaseUsername','banner');
insert into options values (8,'databasePassword','banner123');
insert into options values (9,'driverClass','com.mysql.jdbc.Driver');