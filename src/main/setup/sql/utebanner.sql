#
# Table structure for table 'utebanner'
#


DROP TABLE  utebanner;
CREATE TABLE utebanner (
  ID  bigint NOT NULL,
  LOCATION varchar(240) , 
  PRICE bigint ,
  BANNER_ID bigint, 
  START_DATE varchar(10),
    PRIMARY KEY (ID)
);


#
# Dumping data for table 'utebanner'
#




