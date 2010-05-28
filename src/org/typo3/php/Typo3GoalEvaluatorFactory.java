package org.typo3.php;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.nodes.StaticMethodInvocation;


/**
 * This class implements type hinting for PHP classes which are created through t3lib_div::makeInstance()
 * in TYPO3 v4.
 * 
 * $foo = t3lib_div::makeInstance('MyClass');
 * $foo->|
 * 
 * @author sebastian
 */
public class Typo3GoalEvaluatorFactory implements IGoalEvaluatorFactory {

	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {
		Class<?> goalClass = goal.getClass();

		// We're overriding only the expression type goal:
		if (goalClass == ExpressionTypeGoal.class) {
			ASTNode expression = ((ExpressionTypeGoal) goal).getExpression();

			// Check the expression AST node type
			if (expression instanceof StaticMethodInvocation) {
				StaticMethodInvocation inv = (StaticMethodInvocation) expression;
				ASTNode reciever = inv.getReceiver();

				// Check that the class name is 't3lib_div':
				if (reciever instanceof SimpleReference
						&& "t3lib_div".equals(((SimpleReference) reciever)
								.getName())) {

					// Check that the method name is 'makeInstance'
					if ("makeInstance".equals(inv.getCallName().getName())) {

						// Take the first call argument:
						List arguments = inv.getArgs().getChilds();
						if (arguments.size() >= 1) {
							Object first = arguments.get(0);

							if (first instanceof Scalar
									&& ((Scalar) first).getScalarType() == Scalar.TYPE_STRING) {

								String className = ((Scalar) first).getValue();
								className = className.substring(1, className
										.length() - 1);
								// Return the evaluated type through dummy
								// evaluator
								return new DummyGoalEvaluator(goal, className);
							}
						}
					}
				}
			}
		}

		// Give the control to the default PHP goal evaluator
		return null;

	}

}
