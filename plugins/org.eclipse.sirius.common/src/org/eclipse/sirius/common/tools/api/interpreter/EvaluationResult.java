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
import java.util.Collections;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.common.tools.internal.interpreter.DefaultConverter;
import org.eclipse.sirius.common.tools.internal.interpreter.IConverter;

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
    private final IStatus status;

    /**
     * The converter used to coerce the raw values.
     */
    private final IConverter converter = new DefaultConverter();

    /**
     * Create a new {@link EvaluationResult}.
     * 
     * @param rawValue
     *            the raw result of the evaluation.
     * @param status
     *            the status of the evaluation.
     */
    protected EvaluationResult(Object rawValue, IStatus status) {
        this.rawValue = rawValue;
        this.status = status;
    }

    /**
     * Creates an evaluation result indicating a successful evaluation.
     * 
     * @param rawResult
     *            the value produced by the evaluation.
     * @return an {@link EvaluationResult} inducating a successful evaluation which produced <code>o</code>.
     */
    public static EvaluationResult of(Object rawResult) {
        return of(rawResult, Status.OK_STATUS);
    }

    /**
     * Creates an evaluation result with an associated diagnostic.
     * 
     * @param rawResult
     *            the value produced by the evaluation.
     * @param status
     *            the status describing the outcome of the evaluation.
     * @return an {@link EvaluationResult} inducating an evaluation which produced <code><o>/code>.
     */
    public static EvaluationResult of(Object rawResult, IStatus status) {
        return new EvaluationResult(rawResult, status);
    }

    /**
     * Creates an evaluation result for a computation which was canceled or never actually executed.
     * 
     * @return an evaluation result for a computation which was canceled or never actually executed.
     */
    public static EvaluationResult noEvaluation() {
        return new EvaluationResult(null, Status.CANCEL_STATUS);
    }

    /**
     * Creates an evaluation result for a computation which did not produce a meaningful value because of an error.
     * 
     * @param status
     *            a description of the error.
     * @return an evaluation result for a computation which did not produce a meaningful value because of an error.
     */
    public static EvaluationResult withError(IStatus status) {
        return new EvaluationResult(null, status);
    }

    /**
     * Creates an evaluation result for a computation which did not produce a meaningful value because of an error.
     * 
     * @param message
     *            a textual description of the error.
     * @return an evaluation result for a computation which did not produce a meaningful value because of an error.
     */
    public static EvaluationResult withError(String message) {
        return new EvaluationResult(null, new Status(IStatus.ERROR, IInterpreter.class.getName(), message, null));
    }

    @Override
    public Object getValue() {
        return rawValue;
    }

    @Override
    public IStatus getStatus() {
        return status;
    }

    @Override
    public boolean success() {
        return Diagnostic.ERROR != status.getSeverity();
    }

    @Override
    public Collection<EObject> asEObjects() {
        return converter.toEObjectCollection(rawValue).orElse(Collections.emptySet());
    }

    @Override
    public String asString() {
        return converter.toString(rawValue).orElse(""); //$NON-NLS-1$
    }

    @Override
    public int asInt() {
        return converter.toInt(rawValue).orElse(0);
    }
    
    @Override
    public boolean asBoolean() {
        return converter.toBoolean(rawValue).orElse(false);
    }
    
    @Override
    public EObject asEObject() {
        return converter.toEObject(rawValue).orElse(null);
    }
}
