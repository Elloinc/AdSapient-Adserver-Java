#
# Table structure for table 'campain_state'
#


DROP TABLE campain_state;
CREATE TABLE  campain_state (
  CAMPAIN_STATE_ID varchar(64) NOT NULL DEFAULT '0' ,
  STATE varchar(128) not null default '0',
    PRIMARY KEY (CAMPAIN_STATE_ID)
);


# 
# Dumping data for table 'campain_state'
#
INSERT INTO campain_state VALUES('1','active');
INSERT INTO campain_state VALUES('2','stop');
INSERT INTO campain_state VALUES('3','pause');
INSERT INTO campain_state VALUES('4','stop (reason not enough money)');
INSERT INTO campain_state VALUES('5','not verified');
INSERT INTO campain_state VALUES('9','deffault campain for publisher');




