DROP TABLE daily_publishing_report;
CREATE TABLE  daily_publishing_report (
	ID bigint NOT NULL,
	DAY bigint NOT NULL,
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
insert into daily_publishing_report values (1,1,30,200,600,1000,3000,30,3,3,1,0.1,0.1,10,0.1,3,3,500);