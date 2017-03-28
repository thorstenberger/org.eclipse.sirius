/*******************************************************************************
 * Copyright (c) 2017-2018 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.common.tools.api.interpreter;

import org.eclipse.emf.common.util.Diagnostic;

/**
 * This interface represents the result of the evaluation of an expression with its value and a diagnostic.
 *
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public interface IEvaluationResult {

    /**
     * Returns the raw value computed from the evaluation of the expression. The raw value is exactly what the
     * underlying backend has returned, untransformed in any way.
     *
     * @return The value returned by the expression
     */
    Object getRawValue();

    /**
     * The diagnostic of the evaluation.
     *
     * @return the diagnostic
     */
    Diagnostic getDiagnostic();
}
