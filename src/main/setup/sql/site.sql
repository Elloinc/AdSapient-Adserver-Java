
#
# Table structure for table 'site'
#
DROP TABLE  site;
CREATE TABLE  site (
  SITE_ID bigint NOT NULL  ,
  USER_ID bigint NOT NULL  ,
  TYPE_ID bigint NOT NULL  ,
   URL varchar(240) NOT NULL  ,
  NAME varchar(240) ,
  DESCRIPTION varchar(240) ,
  CATEGORYS text,
   CLICKS_CAMPAIN_ALLOW varchar(2), 
   RATE_ID bigint NOT NULL,
   STATE_ID bigint NOT NULL ,
   START_DATE varchar (20)NOT NULL,
   OWN_PLACES varchar(2) not null,
  PRIMARY KEY (SITE_ID)
);


#
# Dumping data for table 'site'
#
