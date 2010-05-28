package org.typo3.php;

import org.eclipse.dltk.ti.GoalState;
import org.eclipse.dltk.ti.goals.GoalEvaluator;
import org.eclipse.dltk.ti.goals.IGoal;
import org.eclipse.php.internal.core.typeinference.PHPClassType;

/**
 * Helper class for the type inference. 
 * @author sebastian
 */
public class DummyGoalEvaluator extends GoalEvaluator {

	  private String className;

	  public DummyGoalEvaluator(IGoal goal, String className) {
	    super(goal);
	    this.className = className;
	  }

	  public Object produceResult() {
	    return new PHPClassType(className);
	  }

	  public IGoal[] init() {
	    return null;
	  }

	  public IGoal[] subGoalDone(IGoal subgoal, Object result,
	      GoalState state) {
	    return null;
	  }

}
