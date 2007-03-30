#
# Table structure for table 'behaviorpattern'
#


DROP TABLE  behaviorpattern;
CREATE TABLE   behaviorpattern (
  ID bigint not null,
  USER_ID bigint not null,
  DURATION bigint,
  NAME varchar(200),
  FREQUENCY_DAY bigint,
  FREQUENCY_COUNT bigint,
  RECENCY bigint,
  KEY_WORDS VARCHAR(30000),
  SELECTED_CATEGORYS VARCHAR(30000),
    PRIMARY KEY (ID)
);


#
# Dumping data for table 'behaviorpattern'
#





