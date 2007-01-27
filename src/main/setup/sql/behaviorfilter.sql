#
# Table structure for table 'behaviorfilter'
#


DROP TABLE     behaviorfilter;
CREATE TABLE   behaviorfilter (
  BEHAVIOR_FILTER_ID bigint NOT NULL,
	CAMPAIN_ID bigint,
	BANNER_ID bigint default NULL,
	BEHAVIOR_PATTERNS  text,
    PRIMARY KEY (BEHAVIOR_FILTER_ID)
);


#
# Dumping data for table 'behaviorfilter'
#




