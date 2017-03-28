/*******************************************************************************
 * Copyright (c) 2018 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.common.tools.api.interpreter;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;

/**
 * Default implementaiton of {@link IEvaluationResult}, suitable for most cases.
 * 
 * @author pcdavid
 */
public class EvaluationResult implements IEvaluationResult {
    /**
     * The raw value resulting fromt the expression's evaluation, without any coercion applied. May be <code>null</code>
     * in case of evaluation error.
     */
    private final Object rawValue;

    /**
     * The status of the evaluation.
     */
    private final Diagnostic status;

    /**
     * Create a new {@link EvaluationResult}.
     * 
     * @param rawValue
     *            the raw result of the evaluation.
     * @param status
     *            the status of the evaluation.
     */
    protected EvaluationResult(Object rawValue, Diagnostic status) {
        this.rawValue = rawValue;
        this.status = status;
    }

    /**
     * Creates an evaluation result indicating a successful evaluation.
     * 
     * @param rawValue
     *            the value produced by the evaluation.
     * @return an {@link EvaluationResult} inducating a successful evaluation which produced <code><o>/code>.
     */
    public static EvaluationResult withValue(Object rawValue) {
        return of(rawValue, Diagnostic.OK_INSTANCE);
    }

    /**
     * Creates an evaluation result with an associated diagnostic.
     * 
     * @param o
     *            the value produced by the evaluation.
     * @param diag
     *            the diagnostic describing the outcome of the evaluation.
     * @return an {@link EvaluationResult} inducating an evaluation which produced <code><o>/code>.
     */
    public static EvaluationResult of(Object o, Diagnostic diag) {
        return new EvaluationResult(o, diag);
    }

    /**
     * Creates an evaluation result for a computation which did not produce a meaningful value because of an error.
     * 
     * @param diag
     *            a description of the error.
     * @return an evaluation result for a computation which did not produce a meaningful value because of an error.
     */
    public static EvaluationResult withError(Diagnostic diag) {
        return new EvaluationResult(null, diag);
    }

    /**
     * Creates an evaluation result for a computation which did not produce a meaningful value because of an error.
     * 
     * @param message
     *            a textual description of the error.
     * @return an evaluation result for a computation which did not produce a meaningful value because of an error.
     */
    public static EvaluationResult withError(String message) {
        return new EvaluationResult(null, new BasicDiagnostic(Diagnostic.ERROR, IInterpreter.class.getName(), 0, message, null));
    }

    @Override
    public Object getRawValue() {
        return rawValue;
    }

    @Override
    public Diagnostic getDiagnostic() {
        return status;
    }

}
