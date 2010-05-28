package org.typo3.php;

import org.eclipse.dltk.core.CompletionRequestor;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.php.core.codeassist.ICompletionContext;
import org.eclipse.php.internal.core.codeassist.CompletionCompanion;
import org.eclipse.php.internal.core.codeassist.contexts.ClassMemberContext;
import org.eclipse.php.internal.core.codeassist.contexts.QuotesContext;
import org.eclipse.php.internal.core.util.text.TextSequence;

/**
 * This context is activated as soon as one has written:
 * 
 * t3lib_div::makeInstance("|
 * t3lib_div::makeInstance("M|
 * 
 * @author sebastian
 */
public class Typo3CompletionContext extends QuotesContext {
	public boolean isValid(ISourceModule sourceModule, int offset,
		      CompletionRequestor requestor) {

		    if (super.isValid(sourceModule, offset, requestor)) {
	    		String statementText = getStatementText().toString();
	    		if (statementText.matches("t3lib_div::makeInstance\\([\"'][^\"']*")) {
	    			return true;
	    		}
	    		
   		    }
		    return false;
	}
}
