package com.myelin.hivemeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.AlreadyExistsException;
import org.apache.hadoop.hive.metastore.api.InvalidObjectException;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.Partition;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;

//test program
public class Test {
	private static final String list = null;

	public static void main(String[] args) throws InvalidObjectException, AlreadyExistsException, TException {

		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("hadoop.home.dir", "e:\\hadoop\\");
		HiveConf hiveConf = new HiveConf();
		hiveConf.setIntVar(HiveConf.ConfVars.METASTORETHRIFTCONNECTIONRETRIES, 3);
		hiveConf.setVar(HiveConf.ConfVars.METASTOREURIS, "thrift://mdc1nn01:9083");

		HiveMetaStoreClient hiveMetaStoreClient = new HiveMetaStoreClient(hiveConf);
//		List<Partition> partitions = new ArrayList<Partition>();
//		Partition partitionTest = new Partition();
//		partitionTest.setDbName("myelin");
//		partitionTest.setTableName("myelin_contents_plan");
//		partitionTest.setSd(sd);TableName("myelin_contents_plan");
//		hiveMetaStoreClient.add_partitions(partitions);
		List<String> vals = new ArrayList<String>();
		Table table = hiveMetaStoreClient.getTable("myelin", "myelin_contents_plan");
		Partition part = new Partition();
		part.setDbName(table.getDbName());
	    part.setTableName(table.getTableName());
	    part.setValues(vals);
	    part.setParameters(new HashMap<String, String>());
	    part.setSd(table.getSd());
	    part.getSd().setSerdeInfo(table.getSd().getSerdeInfo());
	    //part.getSd().setLocation(table.getSd().getLocation() + location);
	    hiveMetaStoreClient.add_partition(part);
		
		
//		HiveMetaStoreConnector hiveMetaStoreConnector = new HiveMetaStoreConnector(hiveConf);
//		if (hiveMetaStoreConnector != null) {
//			System.out.print(hiveMetaStoreConnector.getAllPartitionInfo("ergo"));
//			
//			List<String> columnsList = hiveMetaStoreConnector.getTableColumnsInformation("myelin", "myelin_contents_plan");
//			for (int ii = 0; ii < columnsList.size(); ii++) {
//				System.out.println(columnsList.get(ii));
//			}
////			java.util.List<String> partList = hiveMetaStoreConnector.getTablePartitionNames("myelin", "myelin_contents_plan");
////			for (int ii = 0; ii < partList.size(); ii++) {
////				System.out.println(partList.get(ii));
////			}
//
//			System.out.println("=========================================================");
//			List<String> schemasList = hiveMetaStoreConnector.getTableSchemaInformation("myelin", "myelin_contents_plan");
//			for (int ii = 0; ii < schemasList.size(); ii++) {
//				System.out.println(schemasList.get(ii));
//			}
//			
//			System.out.println("=========================================================");
//			List<String> partitionsList = hiveMetaStoreConnector.getTablePartitionInformation("myelin", "myelin_contents_plan");
//			for (int ii = 0; ii < partitionsList.size(); ii++) {
//				System.out.println(partitionsList.get(ii));
//			}
//			System.out.println("=========================================================");
//			
//			List<String> columnsInfoList =  hiveMetaStoreConnector.getTablePartitionNames("myelin", "myelin_contents_plan");
//			for (int ii = 0; ii < columnsInfoList.size(); ii++) {
//				System.out.println(columnsInfoList.get(ii));
//			}
//		}
	}
}
