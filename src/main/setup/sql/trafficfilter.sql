
#
# Table structure for table 'trafficfilter'
#
drop table   trafficfilter;
CREATE TABLE trafficfilter(
  TRAFFIC_FILTER_ID bigint NOT NULL  ,
  CAMPAIN_ID bigint,
  BANNER_ID bigint default NULL,
   
  MAX_IMPRESSIONS_PER_DAY bigint,
  MAX_CLICKS_PER_DAY bigint,    
  
  MAX_IMPRESSIONS_PER_CAMPAIN bigint,
  MAX_CLICKS_PER_CAMPAIN bigint,
   
  MAX_IMPRESSIONS_PER_DAY_UNIQUE bigint,
  MAX_CLICKS_PER_DAY_UNIQUE bigint, 
   
  MAX_IMPRESSIONS_PER_CAMPAIN_UNIQUE bigint,
  MAX_CLICKS_PER_CAMPAIN_UNIQUE bigint,
  
 MAX_IMPRESSIONS_PER_MONTH_UNIQUE  bigint,
 MAX_CLICKS_PER_MONTH_UNIQUE bigint,  
 
 CUSTOM_PERIOD_DAY bigint,
 CUSTOM_PERIOD_HOUR bigint,
 CUSTOM_PERIOD_VALUE bigint, 
 
  
 CUSTOM_PERIOD_CLICK_DAY bigint,
 CUSTOM_PERIOD_CLICK_HOUR bigint,
 CUSTOM_PERIOD_CLICK_VALUE bigint,  
 
 CUSTOM_PERIOD_DAY_UNIQUE	 bigint,
 CUSTOM_PERIOD_HOUR_UNIQUE bigint,
 CUSTOM_PERIOD_VALUE_UNIQUE bigint, 
 
  
 CUSTOM_PERIOD_CLICK_DAY_UNIQUE bigint,
 CUSTOM_PERIOD_CLICK_HOUR_UNIQUE bigint,
 CUSTOM_PERIOD_CLICK_VALUE_UNIQUE bigint,

 TRAFFIC_SHARE bigint,
  
  PRIMARY KEY (TRAFFIC_FILTER_ID)
);


#
# Dumping data for table 'trafficfilter'
#
