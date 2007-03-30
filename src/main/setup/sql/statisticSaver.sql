#
# Table structure for table 'statisticsaver'
#


DROP TABLE  statisticsaver;
CREATE TABLE  statisticsaver (
  ID bigint NOT NULL ,
  PERIOD_NAME varchar(240) ,
  TABLE_NAME  varchar(240) ,
  STATISTIC_CLASS varchar(240),
  PRIMARY KEY (ID)
);


#
# Dumping data for table 'statisticsaver'
#
INSERT INTO statisticsaver VALUES(0 ,'impressions','statistic','com.adsapient.shared.mappable.StatisticImpl');
INSERT INTO statisticsaver VALUES(1 ,'clicks','click','com.adsapient.shared.mappable.ClickImpl');
INSERT INTO statisticsaver VALUES(2 ,'leads','lead','com.adsapient.shared.mappable.LeadImpl');
INSERT INTO statisticsaver VALUES(3 ,'sales','sale','com.adsapient.shared.mappable.SaleImpl');