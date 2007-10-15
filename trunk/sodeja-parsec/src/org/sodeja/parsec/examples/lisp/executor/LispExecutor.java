package org.sodeja.parsec.examples.lisp.executor;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.VFunction1;
import org.sodeja.parsec.examples.lisp.model.SExpression;
import org.sodeja.parsec.examples.lisp.model.Script;

public class LispExecutor {
	
	private final Frame frame;
	
	public LispExecutor() {
		final Map<String, Object> procedures = new HashMap<String, Object>() {
			private static final long serialVersionUID = -6084661181815911365L;
		{
			put("\\", new LambdaForm());
			put("def", new DefineForm());
			
			put("+", new SumProcedure());
			put("-", new SubProcedure());
		}};
		
		frame = new Frame(null, procedures);
	}
	
	protected Frame getFrame() {
		return frame;
	}
	
	public void execute(final Script script) {
		ListUtils.execute(script.expressions, new VFunction1<SExpression>() {
			@Override
			public void executeV(SExpression p) {
				System.out.println(p);
				System.out.println(frame.apply(p));
			}});
	}
}
