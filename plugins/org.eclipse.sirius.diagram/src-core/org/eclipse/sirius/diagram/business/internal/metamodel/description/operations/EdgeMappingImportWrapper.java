/*******************************************************************************
 * Copyright (c) 2009, 2016 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.business.internal.metamodel.description.operations;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Internal;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.sirius.common.tools.api.util.MessageTranslator;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.EdgeStyle;
import org.eclipse.sirius.diagram.business.api.query.DiagramElementMappingQuery;
import org.eclipse.sirius.diagram.business.api.query.EObjectQuery;
import org.eclipse.sirius.diagram.business.internal.metamodel.helper.MappingHelper;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ConditionalEdgeStyleDescription;
import org.eclipse.sirius.diagram.description.DescriptionPackage;
import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.EdgeMapping;
import org.eclipse.sirius.diagram.description.EdgeMappingImport;
import org.eclipse.sirius.diagram.description.IEdgeMapping;
import org.eclipse.sirius.diagram.description.style.EdgeStyleDescription;
import org.eclipse.sirius.diagram.description.tool.DeleteElementDescription;
import org.eclipse.sirius.diagram.description.tool.DirectEditLabel;
import org.eclipse.sirius.diagram.description.tool.DoubleClickDescription;
import org.eclipse.sirius.diagram.description.tool.ReconnectEdgeDescription;
import org.eclipse.sirius.viewpoint.DMappingBased;
import org.eclipse.sirius.viewpoint.SiriusPlugin;
import org.eclipse.sirius.viewpoint.description.tool.PasteDescription;
import org.eclipse.sirius.viewpoint.description.tool.RepresentationCreationDescription;
import org.eclipse.sirius.viewpoint.description.tool.RepresentationNavigationDescription;

/**
 * Implementation of the EdgeMapping interface. This class is more or less a
 * wrapper for another EdgeMapping, it helps in reusing mappings from
 * EdgeMapping.
 * 
 * @author <a href="mailto:laurent.redor@obeo.fr">Laurent Redor</a>
 * 
 */
public final class EdgeMappingImportWrapper extends EObjectImpl implements EdgeMapping, EdgeMappingImport {

    /**
     * The default value of the '{@link #isSynchronizationLock()
     * <em>Synchronization Lock</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see org.eclipse.sirius.viewpoint.description.impl.DiagramElementMappingImpl#isSynchronizationLock()
     * @generated
     * @ordered
     */
    protected static final boolean SYNCHRONIZATION_LOCK_EDEFAULT = false;

    private static Map<EdgeMappingImport, EdgeMappingImportWrapper> wrappers = new HashMap<EdgeMappingImport, EdgeMappingImportWrapper>();

    /**
     * The cached value of the '{@link #isSynchronizationLock()
     * <em>Synchronization Lock</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see org.eclipse.sirius.viewpoint.description.impl.DiagramElementMappingImpl#isSynchronizationLock()
     * @generated
     * @ordered
     */
    protected boolean synchronizationLock = EdgeMappingImportWrapper.SYNCHRONIZATION_LOCK_EDEFAULT;

    private EdgeMappingImport edgeMappingImport;

    /**
     * Default constructor.
     * 
     * @param edgeMappingImport
     *            The edgeMappingImport to wrap
     */
    private EdgeMappingImportWrapper(final EdgeMappingImport edgeMappingImport) {
        this.edgeMappingImport = edgeMappingImport;
    }

    /**
     * Return the wrapper corresponding to the edgeMappingImport.
     * 
     * @param edgeMappingImport
     *            The edgeMappingImport to wrap
     * @return the corresponding wrapper
     */
    public static EdgeMappingImportWrapper getWrapper(final EdgeMappingImport edgeMappingImport) {
        if (!EdgeMappingImportWrapper.wrappers.containsKey(edgeMappingImport)) {
            EdgeMappingImportWrapper.wrappers.put(edgeMappingImport, new EdgeMappingImportWrapper(edgeMappingImport));
        }
        return EdgeMappingImportWrapper.wrappers.get(edgeMappingImport);
    }

    @Override
    public EdgeStyle getBestStyle(final EObject modelElement, final EObject viewVariable, final EObject containerVariable) {
        IInterpreter interpreter = SiriusPlugin.getDefault().getInterpreterRegistry().getInterpreter(modelElement);
        EdgeStyle result = (EdgeStyle) new MappingHelper(interpreter).getBestStyle(this, modelElement, viewVariable, containerVariable, new EObjectQuery(viewVariable).getParentDiagram().get());
        if (result == null && edgeMappingImport.getImportedMapping() != null && edgeMappingImport.getImportedMapping() != this) {
            result = edgeMappingImport.getImportedMapping().getBestStyle(modelElement, viewVariable, containerVariable);
        }
        return result;
    }

    @Override
    public EList<ConditionalEdgeStyleDescription> getConditionnalStyles() {
        return edgeMappingImport.getConditionnalStyles();
    }

    @Override
    public String getDomainClass() {
        String result = null;
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            result = edgeMapping.getDomainClass();
        }
        return result;
    }

    @Override
    public EList<EObject> getEdgeSourceCandidates(final EObject semanticOrigin, final DDiagram viewPoint) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getEdgeSourceCandidates(semanticOrigin, viewPoint);
        }
        return null;
    }

    @Override
    public EList<EObject> getEdgeTargetCandidates(final EObject semanticOrigin, final DDiagram viewPoint) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getEdgeTargetCandidates(semanticOrigin, viewPoint);
        }
        return null;
    }

    @Override
    public EList<EObject> getEdgeTargetCandidates(final EObject semanticOrigin, final EObject container, final EObject containerView) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getEdgeTargetCandidates(semanticOrigin, container, containerView);
        }
        return null;
    }

    @Override
    public String getPathExpression() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getPathExpression();
        }
        return null;
    }

    @Override
    public EList<AbstractNodeMapping> getPathNodeMapping() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getPathNodeMapping();
        }
        return null;
    }

    @Override
    public EList<ReconnectEdgeDescription> getReconnections() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getReconnections();
        }
        return null;
    }

    @Override
    public String getSourceFinderExpression() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getSourceFinderExpression();
        }
        return null;
    }

    @Override
    public EList<DiagramElementMapping> getSourceMapping() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getSourceMapping();
        }
        return null;
    }

    @Override
    public EdgeStyleDescription getStyle() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getStyle();
        }
        return null;
    }

    @Override
    public String getTargetExpression() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getTargetExpression();
        }
        return null;
    }

    @Override
    public String getTargetFinderExpression() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getTargetFinderExpression();
        }
        return null;
    }

    @Override
    public EList<DiagramElementMapping> getTargetMapping() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getTargetMapping();
        }
        return null;
    }

    @Override
    public boolean isUseDomainElement() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.isUseDomainElement();
        }
        return false;
    }

    @Override
    public void setDomainClass(final String value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setDomainClass(value);
        }
    }

    @Override
    public void setPathExpression(final String value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setPathExpression(value);
        }
    }

    @Override
    public void setSourceFinderExpression(final String value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setSourceFinderExpression(value);
        }
    }

    @Override
    public void setStyle(final EdgeStyleDescription value) {
        // do nothing
    }

    @Override
    public void setTargetExpression(final String value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setTargetExpression(value);
        }
    }

    @Override
    public void setTargetFinderExpression(final String value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setTargetFinderExpression(value);
        }
    }

    @Override
    public void setUseDomainElement(final boolean value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setUseDomainElement(value);
        }
    }

    @Override
    public boolean checkPrecondition(final EObject modelElement, final EObject container, final EObject containerView) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return SiriusElementMappingSpecOperations.checkPrecondition(edgeMapping, modelElement, container, containerView);
        }
        return false;
    }

    @Override
    public EList<DiagramElementMapping> getAllMappings() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getAllMappings();
        }
        return null;
    }

    @Override
    public DeleteElementDescription getDeletionDescription() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getDeletionDescription();
        }
        return null;
    }

    @Override
    public EList<PasteDescription> getPasteDescriptions() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getPasteDescriptions();
        }
        return null;
    }

    @Override
    public DirectEditLabel getLabelDirectEdit() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getLabelDirectEdit();
        }
        return null;
    }

    @Override
    public String getPreconditionExpression() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getPreconditionExpression();
        }
        return null;
    }

    @Override
    public String getSemanticCandidatesExpression() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getSemanticCandidatesExpression();
        }
        return null;
    }

    @Override
    public String getSemanticElements() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getSemanticElements();
        }
        return null;
    }

    @Override
    public boolean isCreateElements() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.isCreateElements();
        }
        return false;
    }

    @Override
    public boolean isFrom(final DMappingBased element) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return new DiagramElementMappingQuery(edgeMapping).isTypeOf(element);
        }
        return false;
    }

    @Override
    public void setCreateElements(final boolean value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setCreateElements(value);
        }
    }

    @Override
    public void setDeletionDescription(final DeleteElementDescription value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setDeletionDescription(value);
        }
    }

    @Override
    public void setLabelDirectEdit(final DirectEditLabel value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setLabelDirectEdit(value);
        }
    }

    @Override
    public void setPreconditionExpression(final String value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setPreconditionExpression(value);
        }
    }

    @Override
    public void setSemanticCandidatesExpression(final String value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setSemanticCandidatesExpression(value);
        }
    }

    @Override
    public void setSemanticElements(final String value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setSemanticElements(value);
        }
    }

    @Override
    public EList<RepresentationCreationDescription> getDetailDescriptions() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getDetailDescriptions();
        }
        return null;
    }

    @Override
    public String getName() {
        return edgeMappingImport.getName();
    }

    @Override
    public EList<RepresentationNavigationDescription> getNavigationDescriptions() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getNavigationDescriptions();
        }
        return null;
    }

    @Override
    public void setName(final String value) {
        edgeMappingImport.setName(value);
    }

    @Override
    public TreeIterator<EObject> eAllContents() {
        return edgeMappingImport.eAllContents();
    }

    @Override
    public EClass eClass() {
        return edgeMappingImport.eClass();
    }

    @Override
    public EObject eContainer() {
        return edgeMappingImport.eContainer();
    }

    @Override
    public EStructuralFeature eContainingFeature() {
        return edgeMappingImport.eContainingFeature();
    }

    @Override
    public EReference eContainmentFeature() {
        return edgeMappingImport.eContainmentFeature();
    }

    @Override
    public int eContainerFeatureID() {
        return ((InternalEObject) edgeMappingImport).eContainerFeatureID();
    }

    @Override
    public EList<EObject> eContents() {
        return edgeMappingImport.eContents();
    }

    @Override
    public EList<EObject> eCrossReferences() {
        return edgeMappingImport.eCrossReferences();
    }

    @Override
    public Object eGet(final EStructuralFeature feature) {
        Object result = null;
        if (isEdgeMappingImportFeature(feature)) {
            result = edgeMappingImport.eGet(feature);
        } else {
            final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
            if (edgeMapping != null) {
                result = edgeMapping.eGet(feature);
            }
        }
        return result;
    }

    @Override
    public Object eGet(final EStructuralFeature feature, final boolean resolve) {
        Object result = null;
        if (isEdgeMappingImportFeature(feature)) {
            result = edgeMappingImport.eGet(feature, resolve);
        } else {
            final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
            if (edgeMapping != null) {
                result = edgeMapping.eGet(feature, resolve);
            }
        }
        return result;
    }

    @Override
    public boolean eIsSet(final EStructuralFeature feature) {
        boolean result = false;
        if (isEdgeMappingImportFeature(feature)) {
            result = edgeMappingImport.eIsSet(feature);
        } else {
            final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
            if (edgeMapping != null) {
                result = edgeMapping.eIsSet(feature);
            }
        }
        return result;
    }

    private boolean isEdgeMappingImportFeature(final EStructuralFeature feature) {
        return feature.equals(org.eclipse.sirius.viewpoint.description.DescriptionPackage.eINSTANCE.getIdentifiedElement_Name())
                || feature.equals(DescriptionPackage.eINSTANCE.getEdgeMappingImport_ImportedMapping()) || feature.equals(DescriptionPackage.eINSTANCE.getEdgeMappingImport_ConditionnalStyles());
    }

    @Override
    public InternalEObject eInternalContainer() {
        return ((InternalEObject) edgeMappingImport).eInternalContainer();
    }

    @Override
    public Internal eDirectResource() {
        return ((InternalEObject) edgeMappingImport).eDirectResource();
    }

    @Override
    public Internal eInternalResource() {
        return ((InternalEObject) edgeMappingImport).eInternalResource();
    }

    @Override
    public Resource eResource() {
        return edgeMappingImport.eResource();
    }

    @Override
    public boolean eIsProxy() {
        return edgeMappingImport.eIsProxy();
    }

    @Override
    public void eSetProxyURI(URI uri) {
        ((InternalEObject) edgeMappingImport).eSetProxyURI(uri);
    }

    @Override
    public URI eProxyURI() {
        return ((InternalEObject) edgeMappingImport).eProxyURI();
    }

    @Override
    public void eSet(final EStructuralFeature feature, final Object newValue) {
        if (isEdgeMappingImportFeature(feature)) {
            edgeMappingImport.eSet(feature, newValue);
        } else {
            final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
            if (edgeMapping != null) {
                edgeMapping.eSet(feature, newValue);
            }
        }
    }

    @Override
    public void eUnset(final EStructuralFeature feature) {
        if (isEdgeMappingImportFeature(feature)) {
            edgeMappingImport.eUnset(feature);
        } else {
            final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
            if (edgeMapping != null) {
                edgeMapping.eUnset(feature);
            }
        }
    }

    @Override
    public EList<Adapter> eAdapters() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.eAdapters();
        }
        return edgeMappingImport.eAdapters();
    }

    @Override
    public boolean eDeliver() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.eDeliver();
        }
        return edgeMappingImport.eDeliver();
    }

    @Override
    public void eNotify(final Notification notification) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.eNotify(notification);
        }
        edgeMappingImport.eNotify(notification);
    }

    @Override
    public String getDocumentation() {
        return edgeMappingImport.getDocumentation();
    }

    @Override
    public void setDocumentation(final String value) {
        edgeMappingImport.setDocumentation(value);
    }

    @Override
    public IEdgeMapping getImportedMapping() {
        return edgeMappingImport.getImportedMapping();
    }

    /**
     * Return The EdgeMappingImport.
     * 
     * @return the edgeMappingImport
     */
    public EdgeMappingImport getWrappedEdgeMappingImport() {
        return edgeMappingImport;
    }

    @Override
    public void setImportedMapping(final IEdgeMapping value) {
        this.edgeMappingImport.setImportedMapping(value);
    }

    @Override
    public DoubleClickDescription getDoubleClickDescription() {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            return edgeMapping.getDoubleClickDescription();
        }
        return null;
    }

    @Override
    public void setDoubleClickDescription(DoubleClickDescription value) {
        final EdgeMapping edgeMapping = MappingHelper.getEdgeMapping(edgeMappingImport);
        if (edgeMapping != null) {
            edgeMapping.setDoubleClickDescription(value);
        }
    }

    @Override
    public boolean isInheritsAncestorFilters() {
        return edgeMappingImport.isInheritsAncestorFilters();
    }

    @Override
    public void setInheritsAncestorFilters(boolean newInheritsAncestorFilters) {
        this.edgeMappingImport.setInheritsAncestorFilters(newInheritsAncestorFilters);
    }

    @Override
    public boolean isSynchronizationLock() {
        return synchronizationLock;
    }

    @Override
    public void setSynchronizationLock(boolean newSynchronizationLock) {
        boolean oldSynchronizationLock = synchronizationLock;
        synchronizationLock = newSynchronizationLock;
        if (eNotificationRequired()) {
            eNotify(new ENotificationImpl(this, Notification.SET, DescriptionPackage.DIAGRAM_ELEMENT_MAPPING__SYNCHRONIZATION_LOCK, oldSynchronizationLock, synchronizationLock));
        }
    }

    @Override
    public String getLabel() {
        return MessageTranslator.INSTANCE.getMessage(edgeMappingImport.getLabel());
    }

    @Override
    public void setLabel(final String value) {
        edgeMappingImport.setLabel(value);
    }
}
