package org.typo3.php;

import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.core.codeassist.ICompletionContextResolver;
import org.eclipse.php.internal.core.codeassist.contexts.CompletionContextResolver;

/**
 * This class registers the Typo3CompletionContext as relevant context.
 * @author sebastian
 */
public class GenericCompletionContextResolver extends CompletionContextResolver implements ICompletionContextResolver {

    public ICompletionContext[] createContexts() {
        return new ICompletionContext[] { new Typo3CompletionContext() };
    }

}
