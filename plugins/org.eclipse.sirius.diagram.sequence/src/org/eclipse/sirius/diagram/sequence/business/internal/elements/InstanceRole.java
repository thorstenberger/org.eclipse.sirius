/*******************************************************************************
 * Copyright (c) 2010, 2015 THALES GLOBAL SERVICES and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.diagram.sequence.business.internal.elements;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Bounds;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNode;
import org.eclipse.sirius.diagram.business.api.query.NodeStyleQuery;
import org.eclipse.sirius.diagram.sequence.Messages;
import org.eclipse.sirius.diagram.sequence.description.DescriptionPackage;


import org.eclipse.sirius.viewpoint.DRepresentationElement;
import org.eclipse.sirius.viewpoint.RGBValues;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Represents the instance role node at the top of a lifeline.
 * 
 * @author mporhel, pcdavid
 */
public class InstanceRole extends AbstractSequenceNode {

    /**
     * The visual ID. Same as a normal node.
     */
    public static final int VISUAL_ID = 2001;

    /**
     * Predicate to check whether a Sirius DDiagramElement represents an
     * instance role.
     */
    private enum SiriusElementPredicate implements Predicate<DDiagramElement> {
        INSTANCE;

        @Override
        public boolean apply(DDiagramElement input) {
            return AbstractSequenceElement.isSequenceDiagramElement(input, DescriptionPackage.eINSTANCE.getInstanceRoleMapping());
        }
    }

    /**
     * Constructor.
     * 
     * @param node
     *            the GMF Node representing this instance role.
     */
    InstanceRole(Node node) {
        super(node);
        Preconditions.checkArgument(InstanceRole.notationPredicate().apply(node), Messages.InstanceRole_nonInstanceRoleNode);

    }

    /**
     * Returns a predicate to check whether a GMF View represents an instance
     * role.
     * 
     * @return a predicate to check whether a GMF View represents an instance
     *         role.
     */
    public static Predicate<View> notationPredicate() {
        return new NotationPredicate(NotationPackage.eINSTANCE.getNode(), VISUAL_ID, InstanceRole.viewpointElementPredicate());
    }

    /**
     * Returns a predicate to check whether a Sirius DDiagramElement
     * represents an instance role.
     * 
     * @return a predicate to check whether a Sirius DDiagramElement
     *         represents an instance role.
     */
    public static Predicate<DDiagramElement> viewpointElementPredicate() {
        return SiriusElementPredicate.INSTANCE;
    }

    /**
     * Tests whether the instance role is explicitly created by a creation
     * message, or if it starts from the beginning of the sequence.
     * 
     * @return <code>true</code> if the instance role is explicitly created by a
     *         creation message.
     */
    public boolean isExplicitlyCreated() {
        return getCreationMessage().isPresent();
    }

    /**
     * Locate the creation message which creates the instance role, if any.
     * 
     * @return the creation message which creates the instance role, if any.
     */
    public java.util.Optional<Message> getCreationMessage() {
        Node node = getNotationNode();
        for (Edge edge : Iterables.filter(node.getTargetEdges(), Edge.class)) {
            java.util.Optional<Message> message = ISequenceElementAccessor.getMessage(edge);
            if (message.isPresent() && message.get().getKind() == Message.Kind.CREATION) {
                return message;
            }
        }
        return java.util.Optional.empty();
    }

    @Override
    public java.util.Optional<Lifeline> getLifeline() {
        for (View child : Iterables.filter(getNotationView().getChildren(), View.class)) {
            java.util.Optional<Lifeline> lifeline = ISequenceElementAccessor.getLifeline(child);
            if (lifeline.isPresent()) {
                return lifeline;
            }
        }
        return java.util.Optional.empty();
    }

    @Override
    public Rectangle getProperLogicalBounds() {
        if (getNotationNode().getLayoutConstraint() instanceof Bounds) {
            Bounds bounds = (Bounds) getNotationNode().getLayoutConstraint();
            return new Rectangle(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Return the name of the DRepresentationElement associated to this Instance
     * role.
     * 
     * @return the name of the DRepresentationElement associated to this
     *         Instance role
     */
    public String getName() {
        EObject targetElement = getNotationNode().getElement();
        if (targetElement instanceof DRepresentationElement) {
            return ((DRepresentationElement) targetElement).getName();
        }
        return ""; //$NON-NLS-1$
    }

    /**
     * Return the background color of the style of the DRepresentationElement
     * associated to this Instance role.
     * 
     * @return the background color of the style of the DRepresentationElement
     *         associated to this Instance role.
     */
    public java.util.Optional<RGBValues> getBackgroundColor() {
        EObject targetElement = getNotationNode().getElement();
        if (targetElement instanceof DNode) {
            return new NodeStyleQuery(((DNode) targetElement).getOwnedStyle()).getBackgroundColor();
        }
        return java.util.Optional.empty();
    }

    /**
     * Return the label color of the style of the DRepresentationElement
     * associated to this Instance role.
     * 
     * @return the label color of the style of the DRepresentationElement
     *         associated to this Instance role.
     */
    public java.util.Optional<RGBValues> getLabelColor() {
        EObject targetElement = getNotationNode().getElement();
        if (targetElement instanceof DNode) {
            return new NodeStyleQuery(((DNode) targetElement).getOwnedStyle()).getLabelColor();
        }
        return java.util.Optional.empty();
    }
}
