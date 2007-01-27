 
#
# Table structure for table 'keywordfilter'
#
drop table  keywordfilter;
CREATE TABLE keywordfilter (
  KEYWORD_FILTER_ID varchar(120) NOT NULL ,
  CAMPAIN_ID bigint,
  BANNER_ID bigint default NULL,
  PRIMARY KEY (KEYWORD_FILTER_ID)
);


#
# Dumping data for table 'keywordfilter'
#
