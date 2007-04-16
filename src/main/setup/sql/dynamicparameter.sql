
# Table structure for table 'dynamicparameter'
#
drop table   dynamicparameter;
CREATE TABLE dynamicparameter (
	PARAMETER_ID bigint NOT NULL  ,
	PARAMETER_FILTER_ID varchar(128) NOT NULL,
	NAME varchar(240) NOT NULL,
	TYPE_ID bigint NOT NULL   ,
	PARAM_VALUE VARCHAR(255),
	IS_REGEX varchar(2),
  	PRIMARY KEY (PARAMETER_ID)
);

#
# Dumping data for table 'dynamicparameter'
#



