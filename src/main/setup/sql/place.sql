
# Table structure for table 'place'
#
drop table  place;
CREATE TABLE  place (
   PLACE_ID  bigint NOT NULL  ,
   PLACE varchar(128) NOT NULL ,
  PRIMARY KEY (PLACE_ID)
);


#
# Dumping data for table 'place'
#
INSERT INTO place VALUES('1','Top-Left');
INSERT INTO place VALUES('2','Top-Middle');
INSERT INTO place VALUES('3','Top-Right');
INSERT INTO place VALUES('4','Left-Middle');
INSERT INTO place VALUES('5','Middle');
INSERT INTO place VALUES('6','Right-Middle');
INSERT INTO place VALUES('7','Bottom-Left');
INSERT INTO place VALUES('8','Bottom-Middle');
INSERT INTO place VALUES('9','Bottom-Right');




