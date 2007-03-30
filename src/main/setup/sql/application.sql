#
# Table structure for table 'application'
#

drop table application;
CREATE TABLE  application (
  ID bigint NOT NULL ,
  NAME varchar (240) ,
   VALUE varchar (240),
   PRIMARY KEY (ID) );


#
# Dumping data for table 'application'
#
#INSERT INTO application VALUES(0,'application','banner');
INSERT INTO application VALUES(0,'application','adnetwork');