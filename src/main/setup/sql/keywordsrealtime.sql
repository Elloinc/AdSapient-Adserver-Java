
#
# Table structure for table 'KeywordsRealtime'
#
drop table  keywordsrealtime;
CREATE TABLE  keywordsrealtime (
    id BIGINT NOT NULL,
    PUBLISHERURL varchar(254) NOT NULL  ,
    KEYWORDS text NOT NULL ,
    PRIMARY KEY (id),
    UNIQUE (PUBLISHERURL)
    );



#
# Dumping data for table 'KeywordsRealtime'
#

