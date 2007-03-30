drop table publishing_report_by_site;
CREATE TABLE  publishing_report_by_site (
	ID bigint NOT NULL,
	URL varchar(240) NOT NULL,
	UNIQUES_DAILY bigint,
	UNIQUES_WEEKLY bigint,
	UNIQUES_MONTHLY bigint,
	UNIQUES_TOTAL bigint,
	VIEWS bigint,
	CLICKS bigint,
	LEADS bigint,
	SALES bigint,
	CTR double,
	LTR double,
	STR double,
	CPM double,
	CPC double,
	CPL double,
	CPS double,
	REVENUE double,
	PRIMARY KEY (ID) );
insert into publishing_report_by_site values (1,'http://adsapient.com',30,200,600,1000,3000,30,3,3,1,0.1,0.1,10,0.1,3,3,500);