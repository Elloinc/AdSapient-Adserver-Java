#
# Table structure for table 'size'
#


DROP TABLE size;
CREATE TABLE size (
  SIZE_ID  bigint NOT NULL DEFAULT '0' ,
  SIZE varchar(128) not null default '0/0', 
  WIDTH bigint,
  HEIGHT bigint, 
  DEFAULT_FILE_NAME varchar (100),
  DEFAULT_FILE_TYPE_ID bigint,
  MAX_BANNER_SIZE bigint,
    PRIMARY KEY (size_id)
);


#
# Dumping data for table 'size'
#

INSERT INTO size VALUES('1','468x60',468,60,'adnetwork/banners/default/468_60.gif',1,25000);
INSERT INTO size VALUES('2','125x125'	,125,125,'adnetwork/banners/default/125_125.gif',1,25000);
INSERT INTO size VALUES('3','88x31',88,31,'adnetwork/banners/default/88_31.gif',1,25000);
INSERT INTO size VALUES('4','120x60',120,60,'adnetwork/banners/default/120_60.gif',1,25000);
INSERT INTO size VALUES('5','160x600',160,600,'adnetwork/banners/default/160_600.gif',1,25000);
INSERT INTO size VALUES('6','150x85',150,85,'adnetwork/banners/default/150_85.gif',1,25000);
INSERT INTO size VALUES('7','176x208',176,208,'adnetwork/banners/default/150_85.gif',1,25000);
INSERT INTO size VALUES('8','176x40',176,40,'adnetwork/banners/default/150_85.gif',1,25000);



