#
# Table structure for table 'type'
#


DROP TABLE type;
CREATE TABLE  type (
  TYPE_ID bigint NOT NULL  ,
  TYPE varchar(128) not null,
    PRIMARY KEY (TYPE_ID)
);


#
# Dumping data for table 'type'
#
INSERT INTO type VALUES(1,'image');
INSERT INTO type VALUES(2,'HTML');
INSERT INTO type VALUES(3,'flash');
#INSERT INTO type VALUES (5,'UTE');
#INSERT INTO type VALUES (6,'XML/XSLT');
INSERT INTO type VALUES (5,'superstitial');



