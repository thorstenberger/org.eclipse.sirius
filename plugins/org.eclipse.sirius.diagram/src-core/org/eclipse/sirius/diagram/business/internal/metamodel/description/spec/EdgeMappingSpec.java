/*******************************************************************************
 * Copyright (c) 2007, 2009 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.metamodel.description.spec;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.EdgeStyle;
import org.eclipse.sirius.diagram.business.api.query.EObjectQuery;
import org.eclipse.sirius.diagram.business.internal.metamodel.description.operations.SiriusElementMappingSpecOperations;
import org.eclipse.sirius.diagram.business.internal.metamodel.helper.EdgeMappingHelper;
import org.eclipse.sirius.diagram.business.internal.metamodel.helper.MappingHelper;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.impl.EdgeMappingImpl;
import org.eclipse.sirius.viewpoint.DMappingBased;
import org.eclipse.sirius.viewpoint.SiriusPlugin;

/**
 * Implementation of EdgeMapping.
 * 
 * @author cbrun, mchauvin, ymortier
 */
public class EdgeMappingSpec extends EdgeMappingImpl {
    @Override
    public EdgeStyle getBestStyle(final EObject modelElement, final EObject viewVariable, final EObject containerVariable) {
        IInterpreter interpreter = SiriusPlugin.getDefault().getInterpreterRegistry().getInterpreter(modelElement);
        return (EdgeStyle) new MappingHelper(interpreter).getBestStyle(this, modelElement, viewVariable, containerVariable, new EObjectQuery(viewVariable).getParentDiagram().get());
    }
    
    @Override
    public EList<EObject> getEdgeTargetCandidates(final EObject semanticOrigin, final DDiagram diagram) {
        IInterpreter interpreter = SiriusPlugin.getDefault().getInterpreterRegistry().getInterpreter(semanticOrigin);
        return new EdgeMappingHelper(interpreter).getEdgeTargetCandidates(this, semanticOrigin, diagram);
    }

    @Override
    public EList<EObject> getEdgeSourceCandidates(final EObject semanticOrigin, final DDiagram diagram) {
        IInterpreter interpreter = SiriusPlugin.getDefault().getInterpreterRegistry().getInterpreter(getStyle());
        return new EdgeMappingHelper(interpreter).getEdgeSourceCandidates(this, semanticOrigin, diagram);
    }

    @Override
    public boolean checkPrecondition(final EObject modelElement, final EObject container, final EObject containerView) {
        return SiriusElementMappingSpecOperations.checkPrecondition(this, modelElement, container, containerView);
    }

    @Override
    public EList<DiagramElementMapping> getAllMappings() {
        return new BasicEList.UnmodifiableEList<DiagramElementMapping>(0, new Object[0]);
    }

    @Override
    public boolean isFrom(final DMappingBased element) {
        return SiriusElementMappingSpecOperations.isFrom(this, element);
    }

    @Override
    public String toString() {
        return getClass().getName() + " " + getName(); //$NON-NLS-1$
    }
}
