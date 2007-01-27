
#
# Table structure for table 'resource'
#
drop table  resource;
CREATE TABLE  resource (
  RESOURCE_ID bigint NOT NULL  ,
  USER_ID bigint NOT NULL ,
  RES_SIZE bigint NOT NULL  ,
  URL varchar(240),
  TYPE_ID varchar(64) NOT NULL ,
  FILE_NAME varchar(240) NOT NULL , 
  CONTENTTYPE varchar(64), 
  RESOURCE_NAME varchar(200)not null,
  CAMPAIN_ID bigint,
  BANNER_ID bigint,
  SIZE_ID bigint,
  PRIMARY KEY (resource_ID) );
#
# Dumping data for table 'resource'
#

