#
# Table structure for table 'banner_state'
#


DROP TABLE banner_state;
CREATE TABLE  banner_state (
  BANNER_STATE_ID varchar(64) DEFAULT '0' ,
  STATE varchar(128) default '0',
    PRIMARY KEY (BANNER_STATE_ID)
);


# 
# Dumping data for table 'banner_state'
#
INSERT INTO banner_state VALUES('1','Uppload but not use in any campain');
INSERT INTO banner_state VALUES('2','Uppload and use in campain but still saved (not in server)');
INSERT INTO banner_state VALUES('3','Use in campain and saved in server');



