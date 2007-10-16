package org.sodeja.parsec.examples.lisp.executor.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.parsec.examples.lisp.executor.Frame;
import org.sodeja.parsec.examples.lisp.executor.Procedure;
import org.sodeja.parsec.examples.lisp.model.SExpression;

public class LispProcedure implements Procedure {
	
	private final Frame frame;
	private final List<String> params;
	private final SExpression body;
	
	public LispProcedure(Frame frame, List<String> params, SExpression body) {
		this.frame = frame;
		this.params = params;
		this.body = body;
	}

	@Override
	public Object execute(final Object... vals) {
		Map<String, Object> paramsMapping = new HashMap<String, Object>() {
			private static final long serialVersionUID = 7740081972870762415L;
		{
			for(int i = 0, n = params.size();i < n;i++) {
				put(params.get(i), vals[i]);
			}
		}};
		Frame thisFrame = new Frame(frame, paramsMapping);
		
		Object result = thisFrame.apply(body);
		
		System.out.print("AAA: " + paramsMapping);
		System.out.print(" TTT: " + body);
		System.out.println(" RRR: " + result);
		return result;
	}
}
