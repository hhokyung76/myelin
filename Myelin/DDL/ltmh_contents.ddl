CREATE TABLE `myelin_contents_plan`(
id string,
cust_id string,
cust_email string,
myelin_roomid string,
myelin_flag string,
myelin_subject string,
myelin_content string,
create_time timestamp)
PARTITIONED BY (
  p_year string,
 p_month string,
 p_day string,
 p_hour string,
 p_minute string)
STORED AS orc tblproperties ("orc.compress"="ZLIB");



CREATE TABLE `myelin_dayly_contents_history`(
id string,
cust_id string,
cust_email string,
myelin_roomid string,
myelin_flag string,
myelin_subject string,
myelin_content string,
create_time timestamp,
myelin_p_minute string)
PARTITIONED BY (
  p_year string,
 p_month string,
 p_day string)
STORED AS orc tblproperties ("orc.compress"="ZLIB");