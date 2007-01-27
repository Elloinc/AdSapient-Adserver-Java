#
# Table structure for table 'setupfiles'
#


DROP TABLE IF EXISTS setupfiles;
CREATE TABLE  setupfiles(
  ID  bigint not null,
  URL_PATH varchar (200),
  SERVER_PATH varchar (200),
  COMMAND varchar (200),
  PRIMARY KEY (ID)
);


#
# Dumping data for table 'setupfiles'
#

INSERT INTO setupfiles VALUES(0,'','resource','mkdir');
INSERT INTO setupfiles VALUES(1,'','adnetwork/banners/default','mkdir');
INSERT INTO setupfiles VALUES(2,'','default','mkdir');
INSERT INTO setupfiles VALUES(3,'setup/adnetwork/banners/default/125_125.gif','adnetwork/banners/default/125_125.gif','copy');
INSERT INTO setupfiles VALUES(4,'setup/adnetwork/banners/default/150_85.gif','adnetwork/banners/default/150_85.gif','copy');
INSERT INTO setupfiles VALUES(5,'setup/adnetwork/banners/default/160_600.gif','adnetwork/banners/default/160_600.gif','copy');
INSERT INTO setupfiles VALUES(6,'setup/adnetwork/banners/default/468_60.gif','adnetwork/banners/default/468_60.gif','copy');
INSERT INTO setupfiles VALUES(7,'setup/adnetwork/banners/default/88_31.gif','adnetwork/banners/default/88_31.gif','copy');
INSERT INTO setupfiles VALUES(8,'setup/adnetwork/banners/default/120_60.gif','adnetwork/banners/default/120_60.gif','copy');
INSERT INTO setupfiles VALUES(9,'setup/adnetwork/banners/default/universal_default_ad.gif','default/universal_default_ad.gif','copy');
INSERT INTO setupfiles VALUES(10,'setup/GeoIP.dat','resource/GeoIP.dat','copy');


