package com.myelin.builder.framework.core.orc;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DecimalColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DoubleColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.orc.CompressionKind;
import org.apache.orc.OrcFile;
import org.apache.orc.TypeDescription;
import org.apache.orc.Writer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.myelin.builder.framework.core.orc.entity.MarlinOrcField;
import com.myelin.builder.framework.core.orc.entity.StoreValueInfo;
import com.myelin.builder.framework.core.orc.entity.MarlinOrcField.OrcFieldType;
import com.myelin.builder.framework.util.OpenStringUtils;

public class MarlinOrcWriter {

	private static final Logger log = LogManager.getLogger(MarlinOrcWriter.class);
	
	private Configuration conf;
	private TypeDescription schema;
	private Writer writer;
	private String writerSrcPath;
	private VectorizedRowBatch batch;
	
	private List<MarlinOrcField> fieldList;
	private Map<String, OrcFieldType> fieldInfoMap;
	
	int batchRow;
	long msgCount;

	public MarlinOrcWriter() {
	}

	public MarlinOrcWriter(Configuration conf) {
		fieldInfoMap = new HashMap<String, OrcFieldType>();
		this.conf = conf;
	}

	public Configuration getConf() {
		return conf;
	}

	public void setConf(Configuration conf) {
		this.conf = conf;
	}

	public void createOrcWriter(String orcPath, List<MarlinOrcField> pFieldList) throws IllegalArgumentException, IOException {
		writerSrcPath = orcPath;
		fieldList = pFieldList;
		//schema = TypeDescription.createStruct().addField("field1", TypeDescription.createLong()).addField("field2", TypeDescription.createString()).addField("field3", TypeDescription.createString());
		schema = TypeDescription.createStruct();
		for (int ii = 0; ii < fieldList.size(); ii++) {
			MarlinOrcField fieldInfo = fieldList.get(ii);
			if (fieldInfo.getFieldType().equals(MarlinOrcField.OrcFieldType.Int)) {
				schema.addField(fieldInfo.getFieldName(), TypeDescription.createInt());
			}else if (fieldInfo.getFieldType().equals(MarlinOrcField.OrcFieldType.Decimal)) {
				schema.addField(fieldInfo.getFieldName(), TypeDescription.createDecimal());				
			}else if (fieldInfo.getFieldType().equals(MarlinOrcField.OrcFieldType.Double)) {
				schema.addField(fieldInfo.getFieldName(), TypeDescription.createDouble());				
			}else if (fieldInfo.getFieldType().equals(MarlinOrcField.OrcFieldType.String)) {
				schema.addField(fieldInfo.getFieldName(), TypeDescription.createString());				
			}else if (fieldInfo.getFieldType().equals(MarlinOrcField.OrcFieldType.Timestamp)) {
				schema.addField(fieldInfo.getFieldName(), TypeDescription.createTimestamp());				
			}else if (fieldInfo.getFieldType().equals(MarlinOrcField.OrcFieldType.Long)) {
				schema.addField(fieldInfo.getFieldName(), TypeDescription.createLong());				
			}else if (fieldInfo.getFieldType().equals(MarlinOrcField.OrcFieldType.Char)) {
				schema.addField(fieldInfo.getFieldName(), TypeDescription.createChar());						
			}else if (fieldInfo.getFieldType().equals(MarlinOrcField.OrcFieldType.Date)) {
				schema.addField(fieldInfo.getFieldName(), TypeDescription.createDate());				
			}
			fieldInfoMap.put(fieldInfo.getFieldName(), fieldInfo.getFieldType());
		}
		
		writer = OrcFile.createWriter(new Path(orcPath),
				OrcFile.writerOptions(conf).setSchema(schema).stripeSize(12800000).bufferSize(10000)
						.compress(CompressionKind.ZLIB).version(OrcFile.Version.V_0_11));


		batch = schema.createRowBatch();
		
		msgCount = 0;
		
	}
	
	public void writeRecord(Map<String, String> valueRecord) throws IOException, ParseException, InterruptedException {
		if (batch == null) {
			while(true) {
				Thread.sleep(200);
				if (batch != null) {
					break;
				}
			}
		}
		batchRow = batch.size++;
		msgCount++;
		
		List<String> fieldNames = schema.getFieldNames();
		List<TypeDescription> fieldTList = schema.getChildren();
		
		for (int ii = 0; ii < fieldNames.size(); ii++) {
			//System.out.println(fieldNames.get(ii));
			TypeDescription temp = fieldTList.get(ii);
			//System.out.println(temp.toString());
		}
		
		for (int ii = 0; ii < fieldNames.size(); ii++) {
			//System.out.println(fieldNames.get(ii));
			TypeDescription temp = fieldTList.get(ii);
			//System.out.println(temp.toString());
			
			MarlinOrcField.OrcFieldType orcTypeInfo = fieldInfoMap.get(fieldNames);
			if (temp.toString().equals("int")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				int iValue = 0;
				if (rValue != null) {
					iValue = Integer.parseInt(rValue);
				}
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = iValue;
			}else if (temp.toString().equals("long")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				int iValue = 0;
				if (rValue != null) {
					iValue = Integer.parseInt(rValue);
				}
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = iValue;
			}else if (temp.toString().equals("string")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				((BytesColumnVector) batch.cols[ii]).setVal(batchRow, rValue.getBytes());
			}else if (temp.toString().equals("double")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				Double dValue = 0.0;
				if (rValue != null) {
					dValue = Double.parseDouble(rValue);
				}				
				((DoubleColumnVector) batch.cols[ii]).vector[batchRow] = dValue;
			}else if (temp.toString().equals("decimal")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				HiveDecimalWritable bigDecimal = new HiveDecimalWritable(0);
				if (rValue != null) {
					bigDecimal = new HiveDecimalWritable(Long.parseLong(rValue));
				}
				((DecimalColumnVector) batch.cols[ii]).vector[batchRow] = bigDecimal;
			}else if (temp.toString().equals("bigint")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				long longValue = 0;
				if (rValue != null) {
					longValue = Long.parseLong(rValue);
				}
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = longValue;
			}else if (temp.toString().equals("date")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateValue = null;
				Timestamp msgTimestampValue = null;
			    if (rValue != null) {
			    	dateValue = dateFormat.parse(rValue);
			    }
			    msgTimestampValue = new java.sql.Timestamp(dateValue.getTime());
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = dateValue.getTime();
			}else if (temp.toString().equals("timestamp")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Date dateValue = null;
				Timestamp msgTimestampValue = null;
			    if (rValue != null) {
			    	dateValue = dateFormat.parse(rValue);
			    }
			    msgTimestampValue = new java.sql.Timestamp(dateValue.getTime());
				((TimestampColumnVector) batch.cols[ii]).set(batchRow, msgTimestampValue);
			}
			
			if (temp.toString().equals("int")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				int iValue = 0;
				if (rValue == null) {
					rValue = "0";
				}else if (rValue.trim().equals("")) {
					rValue = "0";
				}
				
				if (rValue != null) {
					iValue = Integer.parseInt(rValue);
				}
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = iValue;
			}else if (temp.toString().equals("long")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				long iValue = 0;
				if (rValue == null) {
					rValue = "0";
				}else if (rValue.trim().equals("")) {
					rValue = "0";
				}
				if (rValue != null) {
					iValue = Long.parseLong(rValue);
				}
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = iValue;
			}else if (temp.toString().equals("string")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				((BytesColumnVector) batch.cols[ii]).setVal(batchRow, rValue.getBytes());
			}else if (temp.toString().equals("double")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				Double dValue = 0.0;
				if (rValue == null) {
					rValue = "0.0";
				}else if (rValue.trim().equals("")) {
					rValue = "0.0";
				}
				if (rValue != null) {
					dValue = Double.parseDouble(rValue);
				}				
				((DoubleColumnVector) batch.cols[ii]).vector[batchRow] = dValue;
			}else if (temp.toString().equals("decimal")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				HiveDecimalWritable bigDecimal = new HiveDecimalWritable(0);
				long longValue = 0;
				if (rValue == null) {
					longValue = 0;
				}else if (rValue.trim().equals("")) {
					longValue = 0;
				}
				
				if (rValue != null) {
					bigDecimal = new HiveDecimalWritable(longValue);
				}
				((DecimalColumnVector) batch.cols[ii]).vector[batchRow] = bigDecimal;
			}else if (temp.toString().equals("bigint")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				
				if (rValue == null) {
					rValue = "0";
				}else if (rValue.trim().equals("")) {
					rValue = "0";
				}
				long longValue = 0;
				if (rValue != null) {
					longValue = Long.parseLong(rValue);
				}
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = longValue;
			}else if (temp.toString().equals("date")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date dateValue = null;
				Timestamp msgTimestampValue = null;
				if (rValue == null) {
					rValue = "";
				}else if (rValue.trim().equals("")) {
					rValue = "";
				}
				
			    if (rValue != null) {
			    	dateValue = dateFormat.parse(rValue);
			    }
			    msgTimestampValue = new java.sql.Timestamp(dateValue.getTime());
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = dateValue.getTime();
			}else if (temp.toString().equals("timestamp")) {
				String rValue = valueRecord.get(fieldNames.get(ii));
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Date dateValue = null;
				Timestamp msgTimestampValue = null;
				if (rValue == null) {
					rValue = "";
				}else if (rValue.trim().equals("")) {
					rValue = "";
				}
				
				dateValue = dateFormat.parse(rValue);
			    if (!rValue.equals("")) {
			    	msgTimestampValue = new java.sql.Timestamp(dateValue.getTime());
			    }
			    
				((TimestampColumnVector) batch.cols[ii]).set(batchRow, msgTimestampValue);
			}
		}
		
		
		
//		if (msgCount % 10000 == 0) {
//			System.out.println("row: "+msgCount);
//		}
		if (batch.size == batch.getMaxSize()) {
	        writer.addRowBatch(batch);
	        batch.reset();
	    }
		
	}
	

	public void writeRecordInStore(Map<String, StoreValueInfo> valueRecord) throws IOException, ParseException {
		batchRow = batch.size++;
		msgCount++;
		
		List<String> fieldNames = schema.getFieldNames();
		List<TypeDescription> fieldTList = schema.getChildren();
		
		for (int ii = 0; ii < fieldNames.size(); ii++) {
			//System.out.println(fieldNames.get(ii));
			TypeDescription temp = fieldTList.get(ii);
			//System.out.println(temp.toString());
		}
		
		for (int ii = 0; ii < fieldNames.size(); ii++) {
			//System.out.println(fieldNames.get(ii));
			TypeDescription temp = fieldTList.get(ii);
			//System.out.println(temp.toString());
			
			MarlinOrcField.OrcFieldType orcTypeInfo = fieldInfoMap.get(fieldNames);
			if (temp.toString().equals("int")) {
				StoreValueInfo rValueInfo = valueRecord.get(fieldNames.get(ii));
				String rValue = rValueInfo.getValue();
				int iValue = 0;
				if (rValue == null) {
					rValue = "0";
				}else if (rValue.trim().equals("")) {
					rValue = "0";
				}
				
				if (rValue != null) {
					iValue = Integer.parseInt(rValue);
				}
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = iValue;
			}else if (temp.toString().equals("long")) {
				StoreValueInfo rValueInfo = valueRecord.get(fieldNames.get(ii));
				String rValue = rValueInfo.getValue();
//				String rValue = valueRecord.get(fieldNames.get(ii));
				long iValue = 0;
				if (rValue == null) {
					rValue = "0";
				}else if (rValue.trim().equals("")) {
					rValue = "0";
				}
				if (rValue != null) {
					iValue = Long.parseLong(rValue);
				}
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = iValue;
			}else if (temp.toString().equals("string")) {
				StoreValueInfo rValueInfo = valueRecord.get(fieldNames.get(ii));
				String rValue = rValueInfo.getValue();
//				String rValue = valueRecord.get(fieldNames.get(ii));
				((BytesColumnVector) batch.cols[ii]).setVal(batchRow, rValue.getBytes());
			}else if (temp.toString().equals("double")) {
				StoreValueInfo rValueInfo = valueRecord.get(fieldNames.get(ii));
				String rValue = rValueInfo.getValue();
//				String rValue = valueRecord.get(fieldNames.get(ii));
				Double dValue = 0.0;
				if (rValue == null) {
					rValue = "0.0";
				}else if (rValue.trim().equals("")) {
					rValue = "0.0";
				}
				if (rValue != null) {
					dValue = Double.parseDouble(rValue);
				}				
				((DoubleColumnVector) batch.cols[ii]).vector[batchRow] = dValue;
			}else if (temp.toString().equals("decimal")) {
				StoreValueInfo rValueInfo = valueRecord.get(fieldNames.get(ii));
				String rValue = rValueInfo.getValue();
//				String rValue = valueRecord.get(fieldNames.get(ii));
				HiveDecimalWritable bigDecimal = new HiveDecimalWritable(0);
				long longValue = 0;
				if (rValue == null) {
					longValue = 0;
				}else if (rValue.trim().equals("")) {
					longValue = 0;
				}
				
				if (rValue != null) {
					bigDecimal = new HiveDecimalWritable(longValue);
				}
				((DecimalColumnVector) batch.cols[ii]).vector[batchRow] = bigDecimal;
			}else if (temp.toString().equals("bigint")) {
				StoreValueInfo rValueInfo = valueRecord.get(fieldNames.get(ii));
				String rValue = rValueInfo.getValue();
				
				if (rValue == null) {
					rValue = "0";
				}else if (rValue.trim().equals("")) {
					rValue = "0";
				}
				long longValue = 0;
				if (rValue != null) {
					longValue = Long.parseLong(rValue);
				}
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = longValue;
			}else if (temp.toString().equals("date")) {
				StoreValueInfo rValueInfo = valueRecord.get(fieldNames.get(ii));
				String rValue = rValueInfo.getValue();
				long dateLong = 0;
				if (rValue == null) {
					rValue = "";
				}else if (rValue.trim().equals("")) {
					rValue = "";
				}
			    if (!rValue.equals("")) {
			    	dateLong = rValueInfo.getFieldInfo().convertStrToDate(rValue);
			    }
			    
				((LongColumnVector) batch.cols[ii]).vector[batchRow] = dateLong;
			}else if (temp.toString().equals("timestamp")) {
				StoreValueInfo rValueInfo = valueRecord.get(fieldNames.get(ii));
				String rValue = rValueInfo.getValue();
				Timestamp msgTimestampValue = null;
				if (rValue == null) {
					rValue = "";
				}else if (rValue.trim().equals("")) {
					rValue = "";
				}
			    if (!rValue.equals("")) {
			    	 msgTimestampValue = rValueInfo.getFieldInfo().convertStrToTimeStamp(rValue);
			    }
			   
				((TimestampColumnVector) batch.cols[ii]).set(batchRow, msgTimestampValue);
			}					
		}
		
//		if (msgCount % 10000 == 0) {
//			System.out.println("row: "+msgCount);
//		}
		if (batch.size == batch.getMaxSize()) {
	        writer.addRowBatch(batch);
	        batch.reset();
	    }
		
	}
	
	public void resetBatch() throws IOException {
		if (batch.size > 0) {
	        writer.addRowBatch(batch);
	        batch.reset();
	    }
	}
	
	public boolean close()  {
		boolean successed = false;
		if (writer != null)
			try {
				this.writer.close();
				successed = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e.toString()+"msgCount: "+msgCount+" writer src path:"+writerSrcPath);
				log.error(e.getMessage()+"msgCount: "+msgCount+" writer src path:"+writerSrcPath);
			}
		
		return successed;
	}

	public static void main2(String[] args) throws IllegalArgumentException, IOException, ParseException, InterruptedException {
		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "hive");

		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "hive");
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://192.168.40.121:8020");
		
		MarlinOrcWriter orcWriter = new MarlinOrcWriter();
		orcWriter.setConf(conf);
		
		List<MarlinOrcField> fieldList = new ArrayList<MarlinOrcField>();
		
		MarlinOrcField orcField = new MarlinOrcField();
		orcField.setFieldName("field1");
		orcField.setFieldType(OrcFieldType.Long);
		fieldList.add(orcField);
		

		MarlinOrcField orcField2 = new MarlinOrcField();
		orcField2.setFieldName("field2");
		orcField2.setFieldType(OrcFieldType.String);
		fieldList.add(orcField2);

		MarlinOrcField orcField3 = new MarlinOrcField();
		orcField3.setFieldName("field3");
		orcField3.setFieldType(OrcFieldType.String);
		fieldList.add(orcField3);
		

		MarlinOrcField orcField6 = new MarlinOrcField();
		orcField6.setFieldName("field6");
		orcField6.setFieldType(OrcFieldType.Timestamp);
		fieldList.add(orcField6);
		
		MarlinOrcField orcField7 = new MarlinOrcField();
		orcField7.setFieldName("field7");
		orcField7.setFieldType(OrcFieldType.Date);
		fieldList.add(orcField7);
		
		
		
		orcWriter.createOrcWriter("/tmp/bad-55.orc", fieldList);

		

		String content = "\"[23/Mar/2016:11:40:26 +0900]\" 490 10.207.64.242 - OBSERVED \"KT_Works;Government/Legal\" 304 TCP_HIT 264 722 GET http www.minwon.go.kr 80 /common/js/gubiDoc.js - 118166000 ì¶©ì²­ë<84>¤í<8a>¸ì<9b><8c>í<81>¬ì<9a>´ì<9a>©ë³¸ë¶<80>%20ë¬´ì<84> ì<9a>´ì<9a>©ì<84>¼í<84>°%20ì<95>¡ì<84>¸ì<8a>¤ë§<9d>í<8c><80> 125.60.35.158 application/x-javascript \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; GTB7.5; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; BRI/2; .NET4.0E)\" - 10.228.31.132 125.60.35.158 http://www.minwon.go.kr/minwon/AA040_form2.jsp?q=7D3C23AC189E02F4EAAADE6D0E11FABF3DCAC86C395F1D&charset=euc-kr";
		String flag[] = {"allow", "disallow"};
		Random generator = new Random();
		for (int ii = 0; ii < 1000000; ii++) {
			Map<String, String> rValueMap = new HashMap<String, String>();
			rValueMap.put("field1", "2"+ii);
			int num = generator.nextInt(2);
			 
			 String flagStr = flag[num];
			rValueMap.put("field2", flagStr);
			rValueMap.put("field3", content);
			orcWriter.writeRecord(rValueMap);
			if (ii % 10000 == 0) 
				System.out.println("count: "+ii);
			
		}

		System.out.println("writer: "+orcWriter.writer);
		orcWriter.resetBatch();
		orcWriter.close();
		System.out.println(OpenStringUtils.getCurrentTime());
	}

	public static void main(String[] args) throws IllegalArgumentException, IOException, ParseException, InterruptedException {
		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "hive");

		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("HADOOP_GROUP_NAME", "hive");
		Configuration conf = new Configuration();
		conf.set("fs.default.name", "hdfs://192.168.40.121:8020");
		
		MarlinOrcWriter orcWriter = new MarlinOrcWriter();
		orcWriter.setConf(conf);
		
		List<MarlinOrcField> fieldList = new ArrayList<MarlinOrcField>();
		
		MarlinOrcField orcField = new MarlinOrcField();
		orcField.setFieldName("field1");
		orcField.setFieldType(OrcFieldType.Long);
		fieldList.add(orcField);
		

		MarlinOrcField orcField2 = new MarlinOrcField();
		orcField2.setFieldName("field2");
		orcField2.setFieldType(OrcFieldType.String);
		fieldList.add(orcField2);

		MarlinOrcField orcField3 = new MarlinOrcField();
		orcField3.setFieldName("field3");
		orcField3.setFieldType(OrcFieldType.String);
		fieldList.add(orcField3);
		
		MarlinOrcField orcField4 = new MarlinOrcField();
		orcField4.setFieldName("field4");
		orcField4.setFieldType(OrcFieldType.Double);
		fieldList.add(orcField4);

		MarlinOrcField orcField5 = new MarlinOrcField();
		orcField5.setFieldName("field5");
		orcField5.setFieldType(OrcFieldType.Decimal);
		fieldList.add(orcField5);

		MarlinOrcField orcField6 = new MarlinOrcField();
		orcField6.setFieldName("field6");
		orcField6.setFieldType(OrcFieldType.Timestamp);
		fieldList.add(orcField6);
		
		MarlinOrcField orcField7 = new MarlinOrcField();
		orcField7.setFieldName("field7");
		orcField7.setFieldType(OrcFieldType.Date);
		fieldList.add(orcField7);
		
		System.out.println("writer: "+orcWriter.writer);
		orcWriter.createOrcWriter("/tmp/bad55.orc", fieldList);

		System.out.println("writer: "+orcWriter.writer);
		

		SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String content = "\"[23/Mar/2016:11:40:26 +0900]\" 490 10.207.64.242 - OBSERVED \"KT_Works;Government/Legal\" 304 TCP_HIT 264 722 GET http www.minwon.go.kr 80 /common/js/gubiDoc.js - 118166000 ì¶©ì²­ë<84>¤í<8a>¸ì<9b><8c>í<81>¬ì<9a>´ì<9a>©ë³¸ë¶<80>%20ë¬´ì<84> ì<9a>´ì<9a>©ì<84>¼í<84>°%20ì<95>¡ì<84>¸ì<8a>¤ë§<9d>í<8c><80> 125.60.35.158 application/x-javascript \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0; GTB7.5; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; BRI/2; .NET4.0E)\" - 10.228.31.132 125.60.35.158 http://www.minwon.go.kr/minwon/AA040_form2.jsp?q=7D3C23AC189E02F4EAAADE6D0E11FABF3DCAC86C395F1D&charset=euc-kr";
		String flag[] = {"allow", "disallow"};
		Random generator = new Random();
		for (int ii = 0; ii < 100000000; ii++) {
			Map<String, String> rValueMap = new HashMap<String, String>();
			rValueMap.put("field1", "2"+ii);
			
			int num = generator.nextInt(2);
			 
			String flagStr = flag[num];
			rValueMap.put("field2", flagStr);
			rValueMap.put("field3", content);
			rValueMap.put("field4", "36.25");
			rValueMap.put("field5", "1236952");
			String timeStampValue= null;
			String dateValue = null;
			Date date = new Date();
	    	timeStampValue = timestampFormat.format(date);
		    dateValue = dateFormat.format(date);
		    
			rValueMap.put("field6", timeStampValue);
			rValueMap.put("field7", dateValue);
			orcWriter.writeRecord(rValueMap);
		}

		System.out.println("writer: "+orcWriter.writer);
		orcWriter.resetBatch();
		orcWriter.close();
	}
}
