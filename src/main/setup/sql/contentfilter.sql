
#
# Table structure for table 'contentfilter'
#
drop table  contentfilter;
CREATE TABLE  contentfilter (
  CONTENT_FILTER_ID bigint NOT NULL  ,
  CAMPAIN_ID bigint,
  BANNER_ID bigint default NULL,
  CATEGORYS VARCHAR(30000),
  PAGEPOSITION VARCHAR(30000),
  PLACES VARCHAR(30000),
  ALL_PLACES varchar(2),
  PRIMARY KEY (CONTENT_FILTER_ID)
);


#
# Dumping data for table 'contentfilter'
#
