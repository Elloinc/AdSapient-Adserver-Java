#
# Table structure for table 'user_campain_state'
#


DROP TABLE user_campain_state;
CREATE TABLE  user_campain_state (
  USER_CAMPAIN_STATE_ID varchar(64) NOT NULL ,
  USER_CAMPAIN_STATE varchar(128) not null,
    PRIMARY KEY (USER_CAMPAIN_STATE_ID)
);


# 
# Dumping data for table 'user_campain_state'
#

INSERT INTO user_campain_state VALUES('1','active');
INSERT INTO user_campain_state VALUES('2','inactive');

