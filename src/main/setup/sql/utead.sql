
#
# Table structure for table 'utead'
#
drop table utead;
CREATE TABLE  utead (
  BANNER_ID bigint NOT NULL  ,
  USER_ID bigint NOT NULL ,
  CAMPAIN_ID bigint NOT NULL , 
  SIZE_ID  bigint NOT NULL  ,
  URL text not null,
  TYPE_ID varchar(64) NOT NULL  ,
  BANNER_STATE_ID varchar(64) NOT NULL,
  FILE_TEST varchar(240) NOT NULL , 
  FILE_NAME varchar(128) not null,
  CONTENTTYPE varchar(64) , 
  BANNER_PRIOR bigint  NOT NULL , 
  STATUS bigint,
  RATE_ID bigint ,
  STATUSBAR_TEXT varchar (240) not null,
  ALT_TEXT varchar (240) not null,
  LOADINGTYPE_ID bigint not null,
  PLACETYPE_ID bigint not null,
  TARGETWINDOW_ID bigint not null,
  START_DATE varchar (20) NOT NULL ,
  BANNER_NAME varchar(200)not null,
  RESOURCE_ID bigint NOT NULL,
 
  PRIMARY KEY (BANNER_ID) );
#
# Dumping data for table 'utead'
#

