package com.ltmh.framework.core.orc;

public class OrcRow {
	public Object[] columns;

	public OrcRow(int colCount) {
		columns = new Object[colCount];
	}

	public void setFieldValue(int FieldIndex, Object value) {
		columns[FieldIndex] = value;
	}

	public void setNumFields(int newSize) {
		if (newSize != columns.length) {
			Object[] oldColumns = columns;
			columns = new Object[newSize];
			System.arraycopy(oldColumns, 0, columns, 0, oldColumns.length);
		}
	}
}