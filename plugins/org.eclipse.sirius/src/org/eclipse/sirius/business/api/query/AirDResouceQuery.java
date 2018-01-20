/*******************************************************************************
 * Copyright (c) 2010, 2015 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.api.query;

import org.eclipse.sirius.business.api.session.resource.AirdResource;


import org.eclipse.sirius.viewpoint.DAnalysis;
import org.eclipse.sirius.viewpoint.description.DAnnotationEntry;

/**
 * A class aggregating all the queries (read-only!) having a {@link AirDResouce}
 * as a starting point.
 * 
 * @author nlepine
 * 
 */
public class AirDResouceQuery {

    /**
     * the annotation source.
     */
    public static final String ANNOTATION_SOURCE = "Migration"; //$NON-NLS-1$

    private DAnalysis analysis;

    /**
     * Create a new query.
     * 
     * @param resource
     *            the element to query.
     */
    public AirDResouceQuery(AirdResource resource) {
        if (resource != null) {
            if (!resource.getContents().isEmpty() && resource.getContents().get(0) instanceof DAnalysis) {
                analysis = (DAnalysis) resource.getContents().get(0);
            }
        }
    }

    /**
     * return the DAnalysis of the resource.
     * 
     * @return the DAnalysis of the resource
     */
    public java.util.Optional<DAnalysis> getDAnalysis() {
        if (analysis != null) {
            return java.util.Optional.of(analysis);
        }
        return java.util.Optional.empty();
    }

    /**
     * Check if the analysis contains the migration annotation with
     * <code>tag</code>.
     * 
     * @param tag
     *            the tag to search.
     * @return true if the analysis contains the migration annotation with
     *         <code>tag</code>.
     */
    public boolean hasMigrationTag(String tag) {
        if (analysis == null) {
            return true;
        }
        final java.util.Optional<DAnnotationEntry> annotation = new DAnalysisQuery(analysis).getAnnotation(ANNOTATION_SOURCE, tag);
        return annotation.isPresent();
    }
}
