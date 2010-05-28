package org.typo3.php;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.core.codeassist.ICompletionStrategyFactory;
import org.eclipse.php.internal.core.codeassist.strategies.ClassInstantiationStrategy;

/**
 * Here, the Typo3CompletionContext is linked together with the CustomClassInstanciationStrategy, which determines what should be suggested at this point.
 * @author sebastian
 *
 */
public class GenericCompletionStrategyFactory implements
		ICompletionStrategyFactory {

	@Override
	public ICompletionStrategy[] create(ICompletionContext[] contexts) {
		List<ICompletionStrategy> result = new LinkedList<ICompletionStrategy>();
		for (ICompletionContext context : contexts) {
			if (context.getClass() == Typo3CompletionContext.class) {
				// connect the "typo3 completion context" with the normal class instanciation strategy.
				result.add(new CustomClassInstantiationStrategy(context));
			}
		}
		return (ICompletionStrategy[]) result
				.toArray(new ICompletionStrategy[result.size()]);
	}

}
