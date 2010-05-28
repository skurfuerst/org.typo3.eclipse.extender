package org.typo3.php.util;

import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.ti.BasicContext;
import org.eclipse.dltk.ti.IContext;
import org.eclipse.dltk.ti.goals.ExpressionTypeGoal;
import org.eclipse.dltk.ti.types.IEvaluatedType;
import org.eclipse.php.internal.core.typeinference.PHPClassType;
import org.eclipse.php.internal.core.typeinference.PHPSimpleTypes;
import org.eclipse.php.internal.core.typeinference.context.MethodContext;

/**
 * THIS CLASS HAS BEEN TAKEN FROM phpsense:
 * 
 * http://code.google.com/p/phpsense/source/browse/trunk/PhpSense/src/net/madarco/phpsense/util/InferenceUtil.java
 * 
 * They released the code unter Eclipse Public License.
 *
 */
public class InferenceUtil {


        /**
         * Return a PHPClassType if the typeName is a class (optionally with a full
         * namespace in the name) or a simple type if is a basic type like string.
         * 
         * @param typeName
         *            type name, optionally with namespace (namespace name must be
         *            real, and not point to the alias or subnamespace under current
         *            namespace)
         * @return
         */
        public static IEvaluatedType fromTypeName(String typeName) {
                IEvaluatedType simpleType = PHPSimpleTypes.fromString(typeName);
                if (simpleType != null) {
                        return simpleType;
                }
                return new PHPClassType(typeName);
        }
        
        public static ISourceModule getSourceModel(ExpressionTypeGoal goal) {
                IContext context = goal.getContext();
                if (context instanceof BasicContext) {
                        return ((BasicContext) context).getSourceModule();
                }
                else if (context instanceof MethodContext) {
                        return ((MethodContext) context).getSourceModule();
                }
                throw new IllegalArgumentException("Context for ExpressionTypeGoal must be of type BasicContext or MethodContext, " + context.getClass() + " given.");
        }

}
