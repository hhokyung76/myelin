package com.ltmh.framework.core.orc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.BytesColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DecimalColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.DoubleColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.LongColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.TimestampColumnVector;
import org.apache.hadoop.hive.ql.exec.vector.VectorizedRowBatch;
import org.apache.hadoop.hive.serde2.io.HiveDecimalWritable;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;
import org.apache.orc.TypeDescription;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MarlinOrcReader {
	private Configuration conf;
	
	
	public MarlinOrcReader() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MarlinOrcReader(Configuration conf) {
		super();
		this.conf = conf;
	}

	public static void main(String[] argv) {
		try {

			System.setProperty("HADOOP_USER_NAME", "hdfs");
			Configuration conf = new Configuration();
			conf.set("fs.default.name", "hdfs://192.168.40.121:8020");
			FileSystem fs = FileSystem.get(conf);
			// Reader reader = OrcFile.createReader(fs, new
			// Path("/tmp/my-file2.orc"));
			Path sourcePath = new Path("/tmp/test22127.orc");
//			FileStatus[] fileStatus = fs.listStatus(sourcePath);
//			Path[] paths = FileUtil.stat2Paths(fileStatus);
//			for(FileStatus status : fileStatus){
//			    System.out.println(status.getPath().toString());
//			    Reader reader = OrcFile.createReader(status.getPath(), OrcFile.readerOptions(conf));   
////			    readOrc(conf, status.getPath());
//			}
			//readOrc(conf, new Path("/apps/hive/warehouse/secui_fw_log5/p_year=2017/p_month=201703/p_day=20170315/p_hour=2017031516/p_minute=201703151607/9c27c402-6429-49b5-bbc0-ab9beb64e91d.orc"));
			readOrc(conf, new Path("/apps/hive/warehouse/secui_fw_log5/p_year=2017/p_month=201703/p_day=20170316/p_hour=2017031613/p_minute=201703161357/a127f470-e899-4f38-aaa5-b68ca06486a0.orc"));
			
			System.exit(0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	public static void readOrc(Configuration conf, Path srcFilePath) throws IOException {

		Reader reader = OrcFile.createReader(srcFilePath, OrcFile.readerOptions(conf));
		RecordReader rows = reader.rows();
//		System.out.println(rows);
		VectorizedRowBatch batch = reader.getSchema().createRowBatch();
		
		List<TypeDescription> fList = reader.getSchema().getChildren();
		
		List<Object> vList = new ArrayList<Object>();
		

		for (int ii = 0; ii < fList.size(); ii++) {
			TypeDescription dt = fList.get(ii);
			System.out.println("good.."+dt.toJson());
		}
		
		for (int ii = 0; ii < fList.size(); ii++) {
			TypeDescription dt = fList.get(ii);
			System.out.println(dt.toString());
			if (dt.toString().equals("int")) {
				LongColumnVector fieldVt = (LongColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().equals("string")) {
				BytesColumnVector fieldVt = (BytesColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().equals("long")) {
				LongColumnVector fieldVt = (LongColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().equals("long")) {
				LongColumnVector fieldVt = (LongColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().equals("boolean")) {
				LongColumnVector fieldVt = (LongColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().equals("smallint")) {
				LongColumnVector fieldVt = (LongColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().equals("tinyint")) {
				LongColumnVector fieldVt = (LongColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().equals("bigint")) {
				LongColumnVector fieldVt = (LongColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().equals("double")) {
				DoubleColumnVector fieldVt = (DoubleColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().startsWith("decimal")) {
				DecimalColumnVector fieldVt = (DecimalColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().equals("timestamp")) {
				TimestampColumnVector fieldVt = (TimestampColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}else if (dt.toString().equals("date")) {
				LongColumnVector fieldVt = (LongColumnVector) batch.cols[ii];
				vList.add(fieldVt);
			}
		}
		
//		LongColumnVector field1 = (LongColumnVector) batch.cols[0];
//		BytesColumnVector field2 = (BytesColumnVector) batch.cols[1];
//		TimestampColumnVector field3 = (TimestampColumnVector) batch.cols[2];
		
		long count = 0;
//		System.out.println("## rows.nextBatch(batch): " + rows.nextBatch(batch));
		while (rows.nextBatch(batch)) {
//			System.out.println("## batch index: " + batch.toString());
			for (int r = 0; r < batch.size; ++r) {
//				System.out.println("## batch index: " + r);
				for (int ii = 0; ii < vList.size(); ii++) {
					Object temp = vList.get(ii);
					if (count % 100000 == 0) {
						if (temp instanceof LongColumnVector) {
							System.out.print(r+" LongColumnVector : ");
							System.out.println(new Long(((LongColumnVector) temp).vector[r]));
						}else if (temp instanceof BytesColumnVector) {
							System.out.print("BytesColumnVector : ");
							System.out.println(new String(((BytesColumnVector) temp).toString(r)));
						}else if (temp instanceof DoubleColumnVector) {
							System.out.print("DoubleColumnVector : ");
							System.out.println(new Double(((DoubleColumnVector) temp).vector[r]));
						}else if (temp instanceof DecimalColumnVector) {
							System.out.print("DecimalColumnVector : ");
							System.out.println(new HiveDecimalWritable(((DecimalColumnVector) temp).vector[r]));
						}else if (temp instanceof TimestampColumnVector) {
							System.out.print("TimestampColumnVector : ");
							System.out.println(new Timestamp(((TimestampColumnVector) temp).time[r]));
						}
					}
				}
//				System.out.println(new Long(field1.vector[r]));
//				System.out.println(new String(field2.toString(r)));
//				System.out.println(new String(field3.toString(r)));
				
				count++;
				if (count % 100000 == 0) {
					System.out.println("count: "+count);
				}
			}
		}
		System.out.println("count: "+count);
		rows.close();
	}
}