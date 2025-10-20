/*******************************************************************************
 * Copyright (c) 2000, 2019 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.internal.core.manipulation.dom;

import org.eclipse.jdt.core.dom.*;


/**
 * Helper methods to find AST nodes or bindings.
 */
// @see JDTUIHelperClasses
// @see org.eclipse.jdt.internal.ui.text.correction.ASTResolving (subclass of this one)
public class ASTResolving {
    public static ITypeBinding getParameterTypeBinding(IMethodBinding methodBinding, int argumentIndex) {
        ITypeBinding[] paramTypes= methodBinding.getParameterTypes();
        if (methodBinding.isVarargs() && argumentIndex >= paramTypes.length - 1) {
            return paramTypes[paramTypes.length - 1].getComponentType();
        }
        if (argumentIndex >= 0 && argumentIndex < paramTypes.length) {
            return paramTypes[argumentIndex];
        }
        return null;
    }

    /**
     * Returns the lambda expression node which encloses the given <code>node</code>, or
     * <code>null</code> if none.
     *
     * @param node the node
     * @return the enclosing lambda expression node for the given <code>node</code>, or
     *         <code>null</code> if none
     *
     * @since 3.10
     */
    public static LambdaExpression findEnclosingLambdaExpression(ASTNode node) {
        node= node.getParent();
        while (node != null) {
            if (node instanceof LambdaExpression) {
                return (LambdaExpression) node;
            }
            if (node instanceof BodyDeclaration || node instanceof AnonymousClassDeclaration) {
                return null;
            }
            node= node.getParent();
        }
        return null;
    }

    /**
     * The node's enclosing method declaration or <code>null</code> if
     * the node is not inside a method and is not a method declaration itself.
     *
     * @param node a node
     * @return the enclosing method declaration or <code>null</code>
     */
    public static MethodDeclaration findParentMethodDeclaration(ASTNode node) {
        while (node != null) {
            if (node instanceof MethodDeclaration) {
                return (MethodDeclaration) node;
            } else if (node instanceof BodyDeclaration || node instanceof AnonymousClassDeclaration || node instanceof LambdaExpression) {
                return null;
            }
            node= node.getParent();
        }
        return null;
    }

}
