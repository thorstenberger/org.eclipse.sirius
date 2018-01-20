/*******************************************************************************
 * Copyright (c) 2010, 2014 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.tests.unit.diagram.sequence.structure;

import java.util.List;

import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.CombinedFragment;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.EndOfLife;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Execution;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceElementAccessor;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.InstanceRole;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.InteractionUse;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Lifeline;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.LostMessageEnd;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Message;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ObservationPoint;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Operand;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.SequenceDiagram;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.State;

import org.eclipse.sirius.tests.SiriusTestsPlugin;
import org.eclipse.sirius.tests.unit.diagram.sequence.AbstractSequenceSiriusDiagramTests;
import org.eclipse.sirius.tests.unit.diagram.sequence.InteractionsConstants;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Test for the various basic identification and structural navigation methods
 * between sequence diagram elements.
 * 
 * @author pcdavid
 */
public class SequenceDiagramElementsIdentificationTests extends AbstractSequenceSiriusDiagramTests {

    private static final String PATH = SiriusTestsPlugin.PLUGIN_ID + UNIT_DATA_ROOT + "navigation/";

    private static final String BASIC_MESSAGES_DIAGRAM = "Basic Messages Diagram";

    private static final String BASIC_EXECUTIONS_DIAGRAM = "Basic Executions Diagram";

    private static final String sessionModel = "fixture.aird";

    private static final String typesSemanticModel = "types.ecore";

    private static final String BASIC_INTERACTION_USE_DIAGRAM = "Basic Interaction Use Diagram";

    private static final String BASIC_COMBINED_FRAGMENT_DIAGRAM = "Basic Combined Fragment Diagram";

    private static final String LOST_MESSAGE_END_DIAGRAM = "Basic Lost Message End Diagram";

    private static final String OBSERVATION_DIAGRAM = "Basic Observation Diagram";

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected String getSemanticModel() {
        return "fixture.interactions";
    }

    @Override
    protected String getTypesSemanticModel() {
        return typesSemanticModel;
    }

    @Override
    protected String getSessionModel() {
        return sessionModel;
    }

    @Override
    protected String getRepresentationId() {
        return InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_ID;
    }

    private View getView(Predicate<DDiagramElement> viewpointElementPredicate) {
        List<DDiagramElement> elements = Lists.newArrayList(Iterables.filter(sequenceDDiagram.getDiagramElements(), viewpointElementPredicate));
        assertFalse(elements.isEmpty());
        IGraphicalEditPart editPart = getEditPart(elements.get(0));
        assertNotNull(editPart);
        return editPart.getNotationView();
    }

    /**
     * Check that we can correctly identify a sequence diagram from the GMF
     * View.
     */
    public void test_diagram_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(BASIC_MESSAGES_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());
        assertTrue(ISequenceElementAccessor.getSequenceDiagram(diagramView).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(diagramView).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getCombinedFragment(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getAbstractNodeEvent(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getISequenceEvent(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getMessage(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getOperand(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getState(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(diagramView).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(diagramView).isPresent());
    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_instancerole_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(BASIC_MESSAGES_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View irView = getView(InstanceRole.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getInstanceRole(irView).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(irView).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getCombinedFragment(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getAbstractNodeEvent(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getISequenceEvent(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getMessage(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getOperand(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getState(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(irView).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(irView).isPresent());
    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_lifeline_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(BASIC_MESSAGES_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View lifeline = getView(Lifeline.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getISequenceEvent(lifeline).isPresent());
        assertTrue(ISequenceElementAccessor.getLifeline(lifeline).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(lifeline).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getCombinedFragment(lifeline).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(lifeline).isPresent());
        assertFalse(ISequenceElementAccessor.getAbstractNodeEvent(lifeline).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(lifeline).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(lifeline).isPresent());
        assertFalse(ISequenceElementAccessor.getMessage(lifeline).isPresent());
        assertFalse(ISequenceElementAccessor.getOperand(lifeline).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(lifeline).isPresent());
        assertFalse(ISequenceElementAccessor.getState(lifeline).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(lifeline).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(lifeline).isPresent());
    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_execution_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(BASIC_EXECUTIONS_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View execution = getView(Execution.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getExecution(execution).isPresent());
        assertTrue(ISequenceElementAccessor.getAbstractNodeEvent(execution).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceEvent(execution).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(execution).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getCombinedFragment(execution).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(execution).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(execution).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(execution).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(execution).isPresent());
        assertFalse(ISequenceElementAccessor.getMessage(execution).isPresent());
        assertFalse(ISequenceElementAccessor.getOperand(execution).isPresent());
        assertFalse(ISequenceElementAccessor.getState(execution).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(execution).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(execution).isPresent());
    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_state_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(BASIC_EXECUTIONS_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View state = getView(State.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getState(state).isPresent());
        assertTrue(ISequenceElementAccessor.getAbstractNodeEvent(state).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceEvent(state).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(state).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getCombinedFragment(state).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(state).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(state).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(state).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(state).isPresent());
        assertFalse(ISequenceElementAccessor.getMessage(state).isPresent());
        assertFalse(ISequenceElementAccessor.getOperand(state).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(state).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(state).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(state).isPresent());
    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_message_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(BASIC_MESSAGES_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View message = getView(Message.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getMessage(message).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceEvent(message).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(message).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getCombinedFragment(message).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(message).isPresent());
        assertFalse(ISequenceElementAccessor.getAbstractNodeEvent(message).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(message).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(message).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(message).isPresent());
        assertFalse(ISequenceElementAccessor.getOperand(message).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(message).isPresent());
        assertFalse(ISequenceElementAccessor.getState(message).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(message).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(message).isPresent());
    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_end_of_life_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(BASIC_MESSAGES_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View eol = getView(EndOfLife.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getEndOfLife(eol).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(eol).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getCombinedFragment(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getAbstractNodeEvent(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getISequenceEvent(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getMessage(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getOperand(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getState(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(eol).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(eol).isPresent());
    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_interaction_use_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(BASIC_INTERACTION_USE_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View iu = getView(InteractionUse.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getInteractionUse(iu).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceEvent(iu).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(iu).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getCombinedFragment(iu).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(iu).isPresent());
        assertFalse(ISequenceElementAccessor.getAbstractNodeEvent(iu).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(iu).isPresent());
        assertFalse(ISequenceElementAccessor.getMessage(iu).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(iu).isPresent());
        assertFalse(ISequenceElementAccessor.getOperand(iu).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(iu).isPresent());
        assertFalse(ISequenceElementAccessor.getState(iu).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(iu).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(iu).isPresent());
    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_combined_fragment_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(BASIC_COMBINED_FRAGMENT_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View cf = getView(CombinedFragment.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getCombinedFragment(cf).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceEvent(cf).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(cf).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getMessage(cf).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(cf).isPresent());
        assertFalse(ISequenceElementAccessor.getAbstractNodeEvent(cf).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(cf).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(cf).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(cf).isPresent());
        assertFalse(ISequenceElementAccessor.getOperand(cf).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(cf).isPresent());
        assertFalse(ISequenceElementAccessor.getState(cf).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(cf).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(cf).isPresent());

    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_operand_fragment_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(BASIC_COMBINED_FRAGMENT_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View op = getView(Operand.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getOperand(op).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceEvent(op).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(op).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getCombinedFragment(op).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(op).isPresent());
        assertFalse(ISequenceElementAccessor.getAbstractNodeEvent(op).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(op).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(op).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(op).isPresent());
        assertFalse(ISequenceElementAccessor.getMessage(op).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(op).isPresent());
        assertFalse(ISequenceElementAccessor.getState(op).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(op).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(op).isPresent());
    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_lost_message_end_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(LOST_MESSAGE_END_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View le = getView(LostMessageEnd.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getLostMessageEnd(le).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(le).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getOperand(le).isPresent());
        assertFalse(ISequenceElementAccessor.getISequenceEvent(le).isPresent());
        assertFalse(ISequenceElementAccessor.getCombinedFragment(le).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(le).isPresent());
        assertFalse(ISequenceElementAccessor.getAbstractNodeEvent(le).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(le).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(le).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(le).isPresent());
        assertFalse(ISequenceElementAccessor.getMessage(le).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(le).isPresent());
        assertFalse(ISequenceElementAccessor.getState(le).isPresent());
        assertFalse(ISequenceElementAccessor.getObservationPoint(le).isPresent());
    }

    /**
     * Check that we can correctly identify a sequence element from the GMF
     * View.
     */
    public void test_observation_point_identification() {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(OBSERVATION_DIAGRAM, InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL);
        assertNotNull(sequenceDiagram);
        assertTrue(sequenceDiagram.isPresent());

        View le = getView(ObservationPoint.viewpointElementPredicate());

        assertTrue(ISequenceElementAccessor.getObservationPoint(le).isPresent());
        assertTrue(ISequenceElementAccessor.getISequenceElement(le).isPresent());
        // Also check we don't get any false positive for other element kinds.
        assertFalse(ISequenceElementAccessor.getOperand(le).isPresent());
        assertFalse(ISequenceElementAccessor.getISequenceEvent(le).isPresent());
        assertFalse(ISequenceElementAccessor.getCombinedFragment(le).isPresent());
        assertFalse(ISequenceElementAccessor.getEndOfLife(le).isPresent());
        assertFalse(ISequenceElementAccessor.getAbstractNodeEvent(le).isPresent());
        assertFalse(ISequenceElementAccessor.getInstanceRole(le).isPresent());
        assertFalse(ISequenceElementAccessor.getInteractionUse(le).isPresent());
        assertFalse(ISequenceElementAccessor.getLifeline(le).isPresent());
        assertFalse(ISequenceElementAccessor.getMessage(le).isPresent());
        assertFalse(ISequenceElementAccessor.getExecution(le).isPresent());
        assertFalse(ISequenceElementAccessor.getState(le).isPresent());
        assertFalse(ISequenceElementAccessor.getLostMessageEnd(le).isPresent());
    }
}
