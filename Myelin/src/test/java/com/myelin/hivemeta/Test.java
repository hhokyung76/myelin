package com.myelin.hivemeta;

import org.apache.hadoop.hive.conf.HiveConf;

//test program
public class Test {
	private static final String list = null;

	public static void main(String[] args) {

		System.setProperty("HADOOP_USER_NAME", "hdfs");
		System.setProperty("hadoop.home.dir", "e:\\hadoop\\");
		HiveConf hiveConf = new HiveConf();
		hiveConf.setIntVar(HiveConf.ConfVars.METASTORETHRIFTCONNECTIONRETRIES, 3);
		hiveConf.setVar(HiveConf.ConfVars.METASTOREURIS, "thrift://mdc1nn01:9083");

		HiveMetaStoreConnector hiveMetaStoreConnector = new HiveMetaStoreConnector(hiveConf);
		if (hiveMetaStoreConnector != null) {
			System.out.print(hiveMetaStoreConnector.getAllPartitionInfo("ergo"));
			
			java.util.List<String> columnsList = hiveMetaStoreConnector.getTableColumnsInformation("myelin", "myelin_contents_plan");
			for (int ii = 0; ii < columnsList.size(); ii++) {
				System.out.println(columnsList.get(ii));
			}
//			java.util.List<String> partList = hiveMetaStoreConnector.getTablePartitionNames("myelin", "myelin_contents_plan");
//			for (int ii = 0; ii < partList.size(); ii++) {
//				System.out.println(partList.get(ii));
//			}
			
			java.util.List<String> schemasList = hiveMetaStoreConnector.getTableSchemaInformation("myelin", "myelin_contents_plan");
			for (int ii = 0; ii < schemasList.size(); ii++) {
				System.out.println(schemasList.get(ii));
			}
			
		}
	}
}
