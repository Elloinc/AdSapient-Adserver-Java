
#
# Table structure for table 'accountsettings'
#
drop table accountsettings;
CREATE TABLE  accountsettings (
 	ID bigint NOT NULL,
    USER_ID bigint NOT NULL  ,
    AXIS  bigint NOT NULL  ,
    DEPTH bigint  NOT NULL, 
    DIAGRAM_ID bigint NOT NULL,
     PRIMARY KEY (ID) );


#
# Dumping data for table 'accountsettings'
#
INSERT INTO accountsettings VALUES('1','1','1','3','0');
