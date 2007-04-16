
#
# Table structure for table 'contextfilter'
#

drop table contextfilter;
CREATE TABLE  contextfilter (
  CONTEXT_FILTER_ID varchar(128) NOT NULL  ,
  CAMPAIN_ID bigint,
  BANNER_ID bigint default NULL,
  CATEGORYS VARCHAR(10000),
  PAGEPOSITION VARCHAR(10000),
  PLACES VARCHAR(10000),
  PRIMARY KEY (CONTEXT_FILTER_ID)
);


#
# Dumping data for table 'contextfilter'
#
