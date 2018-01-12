/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.common.tools.api.interpreter;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

/**
 * This interface represents the result of the evaluation of an expression with its value and a diagnostic.
 *
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public interface IEvaluationResult {

    /**
     * Returns the raw value computed from the evaluation of the expression.
     *
     * @return The value returned by the expression
     */
    Object getValue();

    /**
     * The status of the evaluation.
     *
     * @return the status
     */
    IStatus getStatus();

    /**
     * Tests of the evaluation was a success, i.e. it produced an actual (non- <code>null</code>) result and no warnings
     * or errors.
     *
     * @return <code>true</code> iff the evaluation was a success.
     */
    boolean success();

    /**
     * Coerces the value as a collection of EObject if possible.
     *
     * @return a collection of {@link EObject}, which will be empty if the raw value could not be coerced.
     */
    Collection<EObject> asEObjects();

    /**
     * Coerces the value as a string.
     *
     * @return a string representation of the value, which will be empty if the raw value could not be coerced
     *         meaningfully.
     */
    String asString();
    
    /**
     * Coerces the value as an int.
     *
     * @return an int representation of the value, or <code>0</code> the raw value could not be coerced
     *         meaningfully.
     */
    int asInt();
}
