# Table structure for table 'places'
#

drop table   places;
CREATE TABLE  places (
   ID bigint not null,
   PLACE_ID bigint NOT NULL  ,
   SITE_ID bigint NOT NULL ,  
   SIZE_ID bigint NOT NULL,
   CATEGORY_ID bigint,
   RATE_ID bigint NOT NULL,
   LOADINGTYPE_ID bigint,
   PLACETYPE_ID  bigint,
   TARGETWINDOW_ID bigint,
   ROW_COUNT bigint,
   COLUMN_COINT bigint,
   SORTING varchar(2),
   KEYWORDS varchar(2),
   CATEGORYS text,
   OWN_PLACES varchar(2),
   USER_ID bigint not null,
   PRIMARY KEY (ID)
);


#
# Dumping data for table 'places'
#
