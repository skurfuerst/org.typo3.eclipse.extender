package org.typo3.php;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionStrategy;
import org.eclipse.php.internal.core.codeassist.contexts.AbstractCompletionContext;
import org.eclipse.php.internal.core.codeassist.strategies.ClassInstantiationStrategy;
import org.eclipse.php.internal.core.codeassist.strategies.GlobalTypesStrategy;

/**
 * This class determines the additions made to the autocompletion.
 * 
 * @TODO: It should add fully qualified type names only.
 * @author sebastian
 *
 */
public class CustomClassInstantiationStrategy extends
		GlobalTypesStrategy {

	public CustomClassInstantiationStrategy(ICompletionContext context) {
		super(context);
	}
}
