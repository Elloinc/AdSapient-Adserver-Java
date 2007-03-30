#
# Table structure for table 'camp'
#


DROP TABLE  camp;
CREATE TABLE  camp (
 CAMPAIN_ID bigint NOT NULL ,
  USER_ID bigint NOT NULL ,
  STATE_ID bigint NOT NULL ,
  USER_CAMPAIN_STATE_ID  varchar(64) NOT NULL,
  START_DATE varchar (20) NOT NULL  ,
  END_DATE varchar(20) NOT NULL  ,
  CAMPAIN_PRIORITET bigint,
  CAMPAIN_NAME varchar(128) not null,
  CAMPAIN_URL varchar(128) ,
  RATE_ID bigint NOT NULL,
  BUDGET bigint not null,
  STATUSBAR_TEXT varchar (240),
  ALT_TEXT varchar (240),
  LOADINGTYPE_ID bigint,
  PLACETYPE_ID bigint,
  TARGETWINDOW_ID bigint,
  OWN_CAMPAIGNS varchar(2),
  PRIMARY KEY (CAMPAIN_ID)
);


#
# Dumping data for table 'camp'
#
INSERT INTO camp VALUES(0 ,0,9,1,'','','1','system.default','http://www.adsapient.com',0,0,'adsapient.com','adsapient.com',1,1,1,0);
