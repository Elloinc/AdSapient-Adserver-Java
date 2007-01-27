
#
# Table structure for table 'contentfilter'
#
drop table  contentfilter;
CREATE TABLE  contentfilter (
  CONTENT_FILTER_ID bigint NOT NULL  ,
  CAMPAIN_ID bigint,
  BANNER_ID bigint default NULL,
  CATEGORYS text,
  POSITION text, 
  PLACES text,
  ALL_PLACES varchar(2),
  PRIMARY KEY (CONTENT_FILTER_ID)
);


#
# Dumping data for table 'contentfilter'
#
