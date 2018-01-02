package com.myelin.builder.framework.core.orc;

import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.StructField;

public class OrcField implements StructField {
	private final String name;
	private final ObjectInspector inspector;
	public final int offset;

	public OrcField(String name, ObjectInspector inspector, int offset) {
		this.name = name;
		this.inspector = inspector;
		this.offset = offset;
	}

	@Override
	public String getFieldName() {
		return name;
	}

	@Override
	public ObjectInspector getFieldObjectInspector() {
		return inspector;
	}

	@Override
	public int getFieldID() {
		return offset;
	}

	@Override
	public String getFieldComment() {
		return null;
	}
}