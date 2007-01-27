


#
# Table structure for table 'click'
#


DROP    TABLE click;
CREATE TABLE  click(
  CLICK_ID BIGINT NOT NULL,
  PLACE_ID bigint ,
  BANNER_ID bigint,
  CAMPAIN_ID bigint,
  SITE_ID  bigint, 
  TIME bigint,
  ADVERTIZER_ID bigint,
  BANNER_RATE bigint,
  PLACES_RATE bigint,
  PUBLISHER_ID bigint ,
  POSITION_ID bigint,
  COUNTRY varchar(5),
  CATEGORY_ID int,
  END_TIME bigint,
  UNIQUE_USER_ID bigint,  
  PRIMARY KEY (CLICK_ID)
);


#
# Dumping data for table 'click'
#

