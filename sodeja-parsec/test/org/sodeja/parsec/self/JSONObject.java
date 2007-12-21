package org.sodeja.parsec.self;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.lang.StringUtils;

public class JSONObject {
	private final List<JSONField> fields;

	public JSONObject(List<JSONField> fields) {
		this.fields = fields;
	}

	public JSONField getClassField() {
		return ListUtils.head(fields);
	}
	
	public List<JSONField> getFields() {
		return ListUtils.tail(fields);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{");
		sb.append(StringUtils.appendWithSeparatorToString(fields, ","));
		sb.append("}");
		return sb.toString();
	}
}
