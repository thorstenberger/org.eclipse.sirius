/*******************************************************************************
 * Copyright (c) 2007, 2008 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.business.internal.metamodel.spec;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreEList;
import org.eclipse.sirius.viewpoint.DRepresentation;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.sirius.viewpoint.ViewpointPackage;
import org.eclipse.sirius.viewpoint.impl.DRepresentationContainerImpl;

/**
 * Implementation of {@link DRepresentationContainerSpec}.
 * 
 * @author cbrun
 */
public class DRepresentationContainerSpec extends DRepresentationContainerImpl {

    /**
    }

    @Override
    public EList<EObject> getModels() {
        final Collection<EObject> models = new HashSet<EObject>(3);
        for (final DRepresentation representation : this.getOwnedRepresentations()) {
            if (representation instanceof DSemanticDecorator) {
                models.add(getModel(((DSemanticDecorator) representation).getTarget()));
            }
        }
        return new EcoreEList.UnmodifiableEList<EObject>(eInternalContainer(), ViewpointPackage.eINSTANCE.getDRepresentationContainer_Models(), models.size(), models.toArray());
    }

    private EObject getModel(final EObject target) {
        if (target != null) {
            Resource targetResource = target.eResource();
            if (targetResource != null) {
                return targetResource.getContents().iterator().next();
            }
        }
        return target;
    }
}
