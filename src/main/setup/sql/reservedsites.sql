# Table structure for table 'reservedsites'
#
drop table reservedsites  ;
CREATE TABLE  reservedsites (
   ID varchar(64) NOT NULL  ,
   PLACE_ID bigint NOT NULL  ,
   CAMPAIN_ID bigint NOT NULL , 
   PRIMARY KEY (ID)
);


#
# Dumping data for table 'reservedsites'
#
