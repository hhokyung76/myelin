package com.myelin.builder.framework.core.orc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.myelin.builder.framework.util.OpenStringUtils;
import com.myelin.builder.server.util.LtmhUtils;

import org.apache.hadoop.hive.ql.io.orc.*;
import org.apache.hadoop.hive.serde2.io.DoubleWritable;
import org.apache.hadoop.hive.serde2.io.TimestampWritable;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

public class OrcWriterFromJson {

	private Configuration conf = new Configuration();
	private Writer writer;

	public OrcWriterFromJson() {
	}

	public void createWriter(Configuration conf, String path, List<StructField> fieldList)
			throws IllegalArgumentException, IOException {
		this.conf = conf;
		ObjectInspector ObjInspector = new OrcRowInspector(fieldList);
		writer = OrcFile.createWriter(new Path(path), OrcFile.writerOptions(conf).inspector(ObjInspector)
				.stripeSize(10000000).bufferSize(100000).compress(CompressionKind.ZLIB).version(OrcFile.Version.V_0_12));
	}

	public void addRow(OrcRow orcRow) throws IOException {
		writer.addRow(orcRow);
	}

	public boolean closeWriter() {
		boolean returnVal = false;
		try {
			if (writer != null)
				writer.close();
			returnVal = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			returnVal = false;
		}
		return returnVal;
	}

	public static void main23(String[] args) {
		String[] types = {"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"bigint"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"bigint"
		,"bigint"
		,"bigint"
		,"bigint"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"bigint"
		,"bigint"
		,"bigint"
		,"bigint"
		,"bigint"
		,"bigint"
		,"string"
		,"bigint"
		,"bigint"
		,"string"
		,"string"
		,"string"
		,"string"
		,"bigint"
		,"string"
		,"string"
		,"string"
		,"string"
		,"double"
		,"double"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"
		,"string"};
		
		String[] fieldNames = {
				"target_id",			
				"m_ip",                 
				"origin",               
				"create_time",          
				"flag",                 
				"from_ip",              
				"start_time",           
				"end_time",             
				"duration",             
				"machine_name",         
				"fw_rule_id",           
				"nat_rule_id",          
				"vlan_id",              
				"src_ip",               
				"user_id",              
				"src_port",             
				"dst_ip",               
				"dst_port",             
				"protocol",             
				"ingres_if",            
				"input_interface",      
				"packets_forward",      
				"packets_backward",     
				"bytes_forward",
				"bytes_backward",
				"fragment_info",        
				"flag_record",          
				"terminate_reason",     
				"extented_header",      
				"applied_if",           
				"src_ip_nat",           
				"src_port_nat",         
				"dst_ip_nat",           
				"dst_port_nat",         
				"time",                 
				"allow_packets",        
				"deny_packets",         
				"sessions",             
				"max_sessions",         
				"allow_bytes",          
				"deny_bytes",           
				"action",               
				"packets",              
				"bytes",                
				"authserver",           
				"accessip",             
				"result",               
				"message",              
				"cumulative_sessions",  
				"lifetime",             
				"zone",                 
				"geo_country_cd",       
				"geo_country_name",     
				"geo_latitude",         
				"geo_longitude",        
				"geo_city_name",        
				"p_year",				
				"p_month",			//=,
				"p_day",			//=, 
				"p_hour",			//=, 
				"p_minute"				
				
				
				
		};
		
		String[] values = {
			"COLT201611250001",			//target_id=COLT201611250001, 
			"sehdp01",			//m_ip=sehdp01, 
			"<14>1 2016-10-10T15:02:43.916495Z [fw4_allow] [192.168.40.250]2017-03-14 18:56:05,2017-03-14 18:56:05,60,seoulelec,1,-,24.54.16.0,,58352,224.0.0.252,5355,UDP,INT,eth1,2,0,158,0, ,-,- ,",			//origin= 
			"20170314185624",			//create_time=20170314185624, 
			"fw4_allow",			//flag=, fw4_allow
			"192.168.40.250",			//from_ip=192.168.40.250, 
			"2017-03-14 18:56:05",			//start_time=2017-03-14 18:56:05, 
			"2017-03-14 18:56:05",			//end_time=2017-03-14 18:56:05, 
			"60",			//duration=60, 
			"seoulelec",			//machine_name=seoulelec, 
			"1",			//fw_rule_id=1, 
			"",			//nat_rule_id=, 
			"",			//vlan_id=-, 
			"seoulelec",			//src_ip=24.54.16.0, 
			"",			//user_id=, 
			"58532",			//src_port=58352,
			"224.0.0.252",			//dst_ip=224.0.0.252,
			"5355",			//dst_port=5355, 
			"UDP",			//protocol=UDP, 
			"INT",			//ingres_if=INT, 
			"eth1",			//input_interface=eth1, 
			"2",			//packets_forward=2, 
			"0",			//packets_backward=0, 
			"158",			//bytes_forward=158, 
			"0",			//bytes_backward=0
			"",			//fragment_info= ,
			"",			//flag_record=-, 
			"",			//terminate_reason=- , 
			"",			//extented_header=, 
			"",			//applied_if=, 
			"",			//src_ip_nat=, 
			"",			//src_port_nat=, 
			"",			//dst_ip_nat=, 
			"",			//dst_port_nat=, 
			"",			//time=,  
			"",			//allow_packets=, 
			"",			//deny_packets=, 
			"",			//sessions=,  
			"",			//max_sessions=,
			"",			//allow_bytes=, 
			"",			//deny_bytes=, 
			"",			//action=, 
			"",			//packets=, 
			"",			//bytes=, 
			"",			//authserver=, 
			"",			//accessip= 
			"",			//result=
			"",			//message=, 
			"",			//cumulative_sessions=, 
			"",			//lifetime=,
			"",			//zone=
			"CA",			//geo_country_cd=CA, 
			"Canada",			//geo_country_name=Canada, 
			"43.6319",			//geo_latitude=43.6319, 
			"-79.3716",			//geo_longitude=-79.3716, 
			"",			//geo_city_name=, 
			"2017",			//p_year=, 
			"201703",			//p_month=, 
			"20170315",			//p_day=, 
			"2017031514",			//p_hour=, 
			"201703151445"			//p_minute=,
		};
		
		System.out.println("types: "+types.length+" fieldNames: "+fieldNames.length+" values: "+values.length);
		List<StructField> fieldList = new ArrayList<StructField>();
		for (int ii = 0; ii < types.length; ii++) {
			String type = types[ii];
			if (type.equals("string")) {
				OrcField field = new OrcField(fieldNames[ii], PrimitiveObjectInspectorFactory.writableStringObjectInspector, ii);
				fieldList.add(field);
			}else if (type.equals("bigint")) {
				OrcField field = new OrcField(fieldNames[ii], PrimitiveObjectInspectorFactory.writableLongObjectInspector, ii);
				fieldList.add(field);
			}else if (type.equals("double")) {
				OrcField field = new OrcField(fieldNames[ii], PrimitiveObjectInspectorFactory.writableDoubleObjectInspector, ii);
				fieldList.add(field);
			}
		}
		
		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "hive");
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://192.168.40.121:8020");

		String path = "/tmp/secui_fw_log5_test-205.orc";

		OrcWriterFromJson orcWriter = new OrcWriterFromJson();

		try {
			orcWriter.createWriter(conf, path, fieldList);
		} catch (IllegalArgumentException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String content = "<36>[SNIPER-0000] [Attack_Name=(2335)Ethereal SMB Malformed Packet DoS], [Time=2016/09/27 15:42:07], [Hacker=192.168.40.200], [Victim=192.168.40.255], [Protocol=udp/138], [Risk=High], [Handling=Alarm], [Information=], [SrcPort=138], [HackType=01100]";

		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader("d:\\tmp\\secui_fw_test.txt");
			br = new BufferedReader(fr);

			String sCurrentLine;

			JSONParser parser = new JSONParser();
			
			long count  = 0;
			String firstLine = "";
			JSONObject obj = null;
			while ((sCurrentLine = br.readLine()) != null) {
//				System.out.println(sCurrentLine);
				if (count < 100) {
					firstLine = sCurrentLine;
					obj = (JSONObject) parser.parse(firstLine);
				}
//				System.out.println(obj.toJSONString());
//				System.exit(0);
				OrcRow orcRecord = new OrcRow(61);
				for (int kk = 0; kk < types.length; kk++) {

					String value = (String) obj.get(fieldNames[kk]);
					String type = types[kk];
					if (type.equals("string")) {
						orcRecord.setFieldValue(kk, new Text(value));
					}else if (type.equals("bigint")) {
						long iValue = 0;
						if (value == null) {
							value= "0";
						}else if (value.trim().equals("")) {
							value = "0";
						}
						if (value != null) {
							iValue = Long.parseLong(value);
						}
						orcRecord.setFieldValue(kk, new LongWritable(iValue));
					}else if (type.equals("double")) {
						Double dValue = 0.0;
						if (value == null) {
							value = "0.0";
						}else if (value.trim().equals("")) {
							value = "0.0";
						}
						if (value != null) {
							dValue = Double.parseDouble(value);
						}			
						orcRecord.setFieldValue(kk, new DoubleWritable(dValue));
					}
				}
				orcWriter.addRow(orcRecord);
				count++;
				if (count % 100000 == 0)
					System.out.println(count+ " " + OpenStringUtils.getCurrentTime());
			}
			
			
			

		} catch (IOException e) {

			e.printStackTrace();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
//
//		for (int ii = 0; ii < 3000000; ii++) {
//			OrcRow orcRecord = new OrcRow(61);
//			
//			for (int kk = 0; kk < types.length; kk++) {
//				String type = types[kk];
//				if (type.equals("string")) {
//					orcRecord.setFieldValue(kk, new Text(values[kk]));
//				}else if (type.equals("bigint")) {
//					long iValue = 0;
//					if (values[kk] == null) {
//						values[kk]= "0";
//					}else if (values[kk].trim().equals("")) {
//						values[kk] = "0";
//					}
//					if (values[kk] != null) {
//						iValue = Long.parseLong(values[kk]);
//					}
//					orcRecord.setFieldValue(kk, new LongWritable(iValue));
//				}else if (type.equals("double")) {
//					Double dValue = 0.0;
//					if (values[kk] == null) {
//						values[kk] = "0.0";
//					}else if (values[kk].trim().equals("")) {
//						values[kk] = "0.0";
//					}
//					if (values[kk] != null) {
//						dValue = Double.parseDouble(values[kk]);
//					}			
//					orcRecord.setFieldValue(kk, new DoubleWritable(dValue));
//				}
//			}
//			try {
//				orcWriter.addRow(orcRecord);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if (ii % 100000 == 0)
//				System.out.println(ii + " " + MarlinStringUtils.getCurrentTime());
//		}

		orcWriter.closeWriter();
		
		System.out.println("end time: "+OpenStringUtils.getCurrentTime());
	}

	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

		System.setProperty("hadoop.home.dir", "e:\\hadoop\\");

		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "hive");
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://192.168.0.42:8020");

		String path = "/tmp/file127-t4012.orc";

		OrcWriterFromJson orcWriter = new OrcWriterFromJson();
		try {

			Map<String, String> partInfo =LtmhUtils.getLtmhPartion();
			
			List<StructField> fieldList = new ArrayList<StructField>();
			OrcField id = new OrcField("id", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 0);
			OrcField cust_id = new OrcField("cust_id", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 1);
			OrcField cust_email = new OrcField("cust_email", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 2);
			OrcField ltmh_flag = new OrcField("ltmh_flag", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 3);
			OrcField ltmh_content = new OrcField("ltmh_content", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 4);
			OrcField create_time = new OrcField("create_time", PrimitiveObjectInspectorFactory.writableTimestampObjectInspector, 5);
			OrcField p_year = new OrcField("p_year", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 6);
			OrcField p_month = new OrcField("p_year", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 7);
			OrcField p_day = new OrcField("p_year", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 8);
			OrcField p_hour = new OrcField("p_year", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 9);
			OrcField p_minute = new OrcField("p_year", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 10);
			fieldList.add(id);
			fieldList.add(cust_id);
			fieldList.add(cust_email);
			fieldList.add(ltmh_flag);
			fieldList.add(ltmh_content);
			fieldList.add(create_time);
			fieldList.add(p_year);
			fieldList.add(p_month);
			fieldList.add(p_day);
			fieldList.add(p_hour);
			fieldList.add(p_minute);
			
			path = "/apps/hive/warehouse/ltmh.db/ltmh_contents_plan/p_year="+partInfo.get("pYear")+"/p_month="+partInfo.get("pMonth")+"/p_day="+partInfo.get("pDay")+"/p_hour="+partInfo.get("pHour")+"/p_minute="+partInfo.get("pMinute")+"/";
			String fileName = LtmhUtils.getRandomUUID()+".orc";
			
			orcWriter.createWriter(conf, path+fileName, fieldList);
			StringBuilder sb = new StringBuilder();
			sb.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n" + 
					"<HTML>\r\n" + 
					" <HEAD>\r\n" + 
					"	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n" + 
					" </HEAD>\r\n" + 
					" <BODY>\r\n" + 
					" <table>\r\n" + 
					"	<tr>\r\n" + 
					"		<td>assign</td>\r\n" + 
					"		<td>[동] ①할당하다, 배당하다  ②지명하다, 지정하다 </td>\r\n" + 
					"	</tr>\r\n" + 
					"	<tr>\r\n" + 
					"		<td>[əsáin]</td>\r\n" + 
					"		<td>He assigned work to each man.<br>그는 각자에게 작업을 할당하다 </td>\r\n" + 
					"	</tr>\r\n" + 
					" </table>\r\n" + 
					" </BODY>\r\n" + 
					"</HTML>\r\n"  
					);
			//String content = "<36>[SNIPER-0000] [Attack_Name=(2335)Ethereal SMB Malformed Packet DoS], [Time=2016/09/27 15:42:07], [Hacker=192.168.40.200], [Victim=192.168.40.255], [Protocol=udp/138], [Risk=High], [Handling=Alarm], [Information=], [SrcPort=138], [HackType=01100]";

			for (int ii = 0; ii < 1000; ii++) {
				OrcRow orcRecord = new OrcRow(11);
				orcRecord.setFieldValue(0, new Text(Integer.toString(ii)));
				orcRecord.setFieldValue(1, new Text("hhokyung"));
				orcRecord.setFieldValue(2, new Text("hhokyung@gmail.com"));
				orcRecord.setFieldValue(3, new Text("00"));
				orcRecord.setFieldValue(4, new Text(sb.toString()));
				orcRecord.setFieldValue(5, new TimestampWritable(new Timestamp(new Date().getTime())));
				orcRecord.setFieldValue(6, new Text(partInfo.get("pYear")));
				orcRecord.setFieldValue(7, new Text(partInfo.get("pMonth")));
				orcRecord.setFieldValue(8, new Text(partInfo.get("pDay")));
				orcRecord.setFieldValue(9, new Text(partInfo.get("pHour")));
				orcRecord.setFieldValue(10, new Text(partInfo.get("pMinute")));
				orcWriter.addRow(orcRecord);
//				if (ii % 100000 == 0)
//					System.out.println(ii + " " + OpenStringUtils.getCurrentTime());
			}

			orcWriter.closeWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main2(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "hive");
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://192.168.40.121:8020");

		String path = "/tmp/file127-t40.orc";

		OrcWriterFromJson orcWriter = new OrcWriterFromJson();
		try {

			// conf = new Configuration();
			// FileSystem fs = FileSystem.getLocal(conf);

			ObjectInspector ObjInspector = new OrcRowInspector(
					new OrcField("field1", PrimitiveObjectInspectorFactory.writableIntObjectInspector, 0),
					new OrcField("field2", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 1),
					new OrcField("field3", PrimitiveObjectInspectorFactory.writableStringObjectInspector, 2));
			orcWriter.writer = OrcFile.createWriter(new Path(path),
					OrcFile.writerOptions(conf).inspector(ObjInspector).stripeSize(1000000).bufferSize(100000)
							.compress(CompressionKind.ZLIB).version(OrcFile.Version.V_0_12));

			String content = "<36>[SNIPER-0000] [Attack_Name=(2335)Ethereal SMB Malformed Packet DoS], [Time=2016/09/27 15:42:07], [Hacker=192.168.40.200], [Victim=192.168.40.255], [Protocol=udp/138], [Risk=High], [Handling=Alarm], [Information=], [SrcPort=138], [HackType=01100]";

			for (int ii = 0; ii < 100; ii++) {
				OrcRow orcRecord = new OrcRow(3);
				orcRecord.setFieldValue(0, new IntWritable(ii));
				orcRecord.setFieldValue(1, new Text(content));
				orcRecord.setFieldValue(2, new Text("type4"));
				orcWriter.writer.addRow(orcRecord);
				if (ii % 100000 == 0)
					System.out.println(ii + " " + OpenStringUtils.getCurrentTime());
			}

			orcWriter.writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}