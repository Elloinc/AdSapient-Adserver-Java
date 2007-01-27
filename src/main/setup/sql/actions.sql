
#
# Table structure for table 'actions'
#
drop table  actions;
CREATE TABLE  actions (
  CAMPAIN_ID bigint NOT NULL  ,
  ACTIONS  bigint NOT NULL DEFAULT '0' ,
  CLICKS   bigint  NOT NULL DEFAULT '0', 
  IMPRESSION bigint not null,
    ID varchar(254) NOT NULL  ,
  PRIMARY KEY (ID) );


#
# Dumping data for table 'banner'
#

