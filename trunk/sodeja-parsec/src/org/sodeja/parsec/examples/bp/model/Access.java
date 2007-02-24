package org.sodeja.parsec.examples.bp.model;

import java.util.List;
import java.util.Map;

public interface Access extends Expression {
	public static class Util {
		public static Object read(Map<String, Object> rootContext, Object context, List<Access> accesses) {
			Object tmp = context;
			for(Access acc : accesses) {
				tmp = acc.read(rootContext, tmp);
			}
			return tmp;
		}
	}
}
