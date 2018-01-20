/*******************************************************************************
 * Copyright (c) 2011 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.query;

import org.eclipse.core.internal.resources.ProjectInfo;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.sirius.business.api.modelingproject.ModelingProject;
import org.eclipse.sirius.common.tools.api.util.ReflectionHelper;



/**
 * A class aggregating all the queries (read-only!) having a
 * {@link IResourceDelta} as a starting point.
 * 
 * @author lredor
 */
public class ResourceDeltaQuery {

    private IResourceDelta resourceDelta;

    /**
     * Create a new query.
     * 
     * @param resourceDelta
     *            the resource delta to query.
     */
    public ResourceDeltaQuery(IResourceDelta resourceDelta) {
        this.resourceDelta = resourceDelta;
    }

    /**
     * Check if the modeling nature has been added in this resource delta.
     * 
     * @return true if the nature has been added, false otherwise.
     */
    public boolean hasModelingNatureAdded() {
        return !hasModelingNature(getOldProjectDescription()) && hasModelingNature(getNewProjectDescription());
    }

    /**
     * Check if the modeling nature has been removed in this resource delta.
     * 
     * @return true if the nature has been removed, false otherwise.
     */
    public boolean hasModelingNatureRemoved() {
        return hasModelingNature(getOldProjectDescription()) && !hasModelingNature(getNewProjectDescription());
    }
    
    private boolean hasModelingNature(java.util.Optional<IProjectDescription> project) {
        return project.isPresent() && project.get().hasNature(ModelingProject.NATURE_ID);
    }
    
    private java.util.Optional<IProjectDescription> getOldProjectDescription() {
        return getProjectDescription(ReflectionHelper.getFieldValueWithoutException(resourceDelta, "oldInfo")); //$NON-NLS-1$
    }

    private java.util.Optional<IProjectDescription> getNewProjectDescription() {
        return getProjectDescription(ReflectionHelper.getFieldValueWithoutException(resourceDelta, "newInfo")); //$NON-NLS-1$
    }

    @SuppressWarnings("restriction")
    private java.util.Optional<IProjectDescription> getProjectDescription(java.util.Optional<Object> info) {
        if (info.isPresent() && info.get() instanceof ProjectInfo) {
            IProjectDescription oldProjectDescription = ((ProjectInfo) info.get()).getDescription();
            return java.util.Optional.ofNullable(oldProjectDescription);
        } else {
            return java.util.Optional.empty();
        }
    }

}
