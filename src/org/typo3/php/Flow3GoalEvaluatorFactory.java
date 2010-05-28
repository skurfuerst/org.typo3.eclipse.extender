package org.typo3.php;

import java.util.List;

import org.eclipse.dltk.ast.ASTNode;
import org.eclipse.dltk.ast.expressions.CallExpression;
import org.eclipse.dltk.ast.references.SimpleReference;
import org.eclipse.dltk.ast.references.VariableKind;
import org.eclipse.dltk.ast.references.VariableReference;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.IType;
import org.eclipse.dltk.ti.IGoalEvaluatorFactory;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.codeassist.CodeAssistUtils;
import org.eclipse.php.internal.core.compiler.ast.nodes.PHPCallExpression;
import org.eclipse.php.internal.core.compiler.ast.nodes.Scalar;
import org.eclipse.php.internal.core.compiler.ast.nodes.StaticMethodInvocation;
import org.typo3.php.util.InferenceUtil;

/**
 * This class implements type hinting for PHP classes which are created through FLOW3's
 * Object Manager.
 * 
 * $foo = $objectManager->create('MyClass');
 * $foo->|
 * 
 * @author sebastian
 */
public class Flow3GoalEvaluatorFactory implements IGoalEvaluatorFactory {

	@Override
	public GoalEvaluator createEvaluator(IGoal goal) {

		// We're overriding only the expression type goal
		if (goal.getClass() == ExpressionTypeGoal.class) {
			ASTNode expression = ((ExpressionTypeGoal) goal).getExpression();

			// This is a call
			if (expression instanceof PHPCallExpression) {
				CallExpression invocation = (CallExpression) expression;
				ASTNode receiver = invocation.getReceiver();

				if (receiver instanceof VariableReference) {
					ISourceModule sourceModel = InferenceUtil
							.getSourceModel((ExpressionTypeGoal) goal);
					IType[] receiverTypes = CodeAssistUtils.getVariableType(
							sourceModel, ((VariableReference) receiver).getName(),
							((VariableReference) receiver).sourceStart());
					
					String calledMethodName = invocation.getCallName().getName();
					List arguments = invocation.getArgs().getChilds();

					if (isTypeFound("F3\\FLOW3\\Object", "ObjectManager", receiverTypes) 
							&& calledMethodName.equals("create")
							&& arguments.size() >= 1
							&& arguments.get(0) instanceof Scalar) {
						String className = ((Scalar) arguments.get(0)).getValue();
						className = className.substring(1, className .length() - 1);
						return new DummyGoalEvaluator(goal, className); 
					}
				}
			}
		}

		// Give the control to the default PHP goal evaluator
		return null;

	}

	private boolean isTypeFound(String namespace, String className,
			IType[] receiverTypes) {
		for (IType receiverType : receiverTypes) {
			if (receiverType.getTypeQualifiedName().equals(
					namespace + "$" + className)) {
				return true;
			}
		}
		return false;
	}
}
