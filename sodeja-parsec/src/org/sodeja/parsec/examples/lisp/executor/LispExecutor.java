package org.sodeja.parsec.examples.lisp.executor;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.VFunction1;
import org.sodeja.parsec.examples.lisp.executor.form.DefineForm;
import org.sodeja.parsec.examples.lisp.executor.form.LambdaForm;
import org.sodeja.parsec.examples.lisp.executor.primitive.DivProcedure;
import org.sodeja.parsec.examples.lisp.executor.primitive.MulProcedure;
import org.sodeja.parsec.examples.lisp.executor.primitive.SubProcedure;
import org.sodeja.parsec.examples.lisp.executor.primitive.SumProcedure;
import org.sodeja.parsec.examples.lisp.model.Expression;
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
			put("*", new MulProcedure());
			put("/", new DivProcedure());
		}};
		
		frame = new Frame(null, procedures);
	}
	
	protected Frame getFrame() {
		return frame;
	}
	
	public void execute(final Script script) {
		ListUtils.execute(script.expressions, new VFunction1<Expression>() {
			@Override
			public void executeV(Expression p) {
				System.out.println("=> " + p);
				System.out.println(frame.eval(p));
			}});
	}
}
