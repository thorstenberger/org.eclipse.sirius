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
package org.eclipse.sirius.ext.base;

import java.util.stream.Stream;

/**
 * Stream utilities.
 * 
 * @author pcdavid
 */
public final class StreamUtil {
    private StreamUtil() {
        // Prevent instantiation.
    }

    /**
     * Filters an arbitrary stream to keep only instances of a specified type (or subtypes). The resulting stream is
     * properly typed.
     * 
     * @param input
     *            the input stream.
     * @param type
     *            the type of elements to keep.
     * @param <T>
     *            the type of elements to keep.
     * @return a stream consisting of the elements of the input stream which are instances of the specified type (or a
     *         subtype).
     */
    public <T> Stream<T> filter(Stream<?> input, Class<T> type) {
        return input.filter(type::isInstance).map(type::cast);
    }
}
