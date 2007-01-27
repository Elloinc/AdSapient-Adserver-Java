
#
# Table structure for table 'banner'
#
drop table  banner;
CREATE TABLE  banner (
  BANNER_ID bigint NOT NULL  ,
  USER_ID bigint NOT NULL ,
  CAMPAIN_ID bigint NOT NULL , 
  SIZE_ID bigint  ,
  URL text ,
  TYPE_ID varchar(64) NOT NULL ,
  BANNER_STATE_ID varchar(64) NOT NULL ,
  FILE_TEST varchar(254)  ,
  FILE_NAME varchar(128),
  CONTENTTYPE varchar(64)  , 
  BANNER_PRIOR bigint ,
  STATUS bigint,
  RATE_ID bigint ,
  STATUSBAR_TEXT varchar (240),
  ALT_TEXT varchar (240),
  LOADINGTYPE_ID bigint,
  PLACETYPE_ID bigint,
  TARGETWINDOW_ID bigint,
  START_DATE varchar (20)NOT NULL  ,
  END_DATE varchar(20) NOT NULL  ,
  BANNER_NAME varchar(200)not null,
  OWN_CAMPAIGNS varchar(2),
  EXTERNALURL varchar(254),
  banner_text varchar (240),
  list_text varchar (20),
  sms_number varchar (20),
  sms_text varchar(160),
  call_number varchar (20),
  PRIMARY KEY (BANNER_ID) );
#
# Dumping data for table 'banner'
#

#vs- universal default ad belongs to admin! there is no user with id '0'
INSERT INTO `banner` VALUES (1,1,0,0,'http://www.adsapient.com','1','5','default/universal_default_ad.gif','universal_default_ad.gif','image/gif',1,2,1,'adsapient.com','adsapient.com',1,1,1,'','','','0',NULL,NULL,NULL,NULL,NULL,NULL);