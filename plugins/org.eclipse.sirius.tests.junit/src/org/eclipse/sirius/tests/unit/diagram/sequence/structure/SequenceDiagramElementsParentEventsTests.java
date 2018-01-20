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

import org.eclipse.sirius.diagram.sequence.business.internal.elements.CombinedFragment;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Execution;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.InteractionUse;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Lifeline;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Message;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Operand;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.SequenceDiagram;

import org.eclipse.sirius.tests.SiriusTestsPlugin;
import org.eclipse.sirius.tests.unit.diagram.sequence.AbstractSequenceSiriusDiagramTests;
import org.eclipse.sirius.tests.unit.diagram.sequence.InteractionsConstants;
import org.junit.Assert;

/**
 * Test for the various basic identification and structural navigation methods
 * between sequence diagram elements.
 * 
 * @author pcdavid
 */
public class SequenceDiagramElementsParentEventsTests extends AbstractSequenceSiriusDiagramTests {

    private static final String PATH = SiriusTestsPlugin.PLUGIN_ID + UNIT_DATA_ROOT + "navigation/";

    private static final String COMPLEX_DIAGRAM = "Complex with CF";

    private static final String REPRESENTATION_TYPE = InteractionsConstants.SEQUENCE_DIAGRAM_REPRESENTATION_LABEL;

    private static final String sessionModel = "fixture.aird";

    private static final String typesSemanticModel = "types.ecore";

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

    public void test_sub_events_lifeline() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<Lifeline> p1 = getLifelineByName(sequenceDiagram.get(), "p1");
        Assert.assertTrue(p1.isPresent());

        java.util.Optional<Lifeline> p2 = getLifelineByName(sequenceDiagram.get(), "p2");
        Assert.assertTrue(p2.isPresent());
    }

    public void test_sub_events_execution() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<Execution> e1 = getExecutionByName(sequenceDiagram.get(), "e1");
        Assert.assertTrue(e1.isPresent());

        java.util.Optional<Execution> e2 = getExecutionByName(sequenceDiagram.get(), "e2");
        Assert.assertTrue(e2.isPresent());

        java.util.Optional<Execution> e3 = getExecutionByName(sequenceDiagram.get(), "e3");
        Assert.assertTrue(e3.isPresent());

        java.util.Optional<Execution> e4 = getExecutionByName(sequenceDiagram.get(), "e4");
        Assert.assertTrue(e4.isPresent());

        java.util.Optional<Execution> e5 = getExecutionByName(sequenceDiagram.get(), "e5");
        Assert.assertTrue(e5.isPresent());

        java.util.Optional<Execution> e6 = getExecutionByName(sequenceDiagram.get(), "e6");
        Assert.assertTrue(e6.isPresent());

    }

    public void test_sub_events_message() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<Message> m1 = getMessageByName(sequenceDiagram.get(), "m1");
        Assert.assertTrue(m1.isPresent());

        java.util.Optional<Message> m2 = getMessageByName(sequenceDiagram.get(), "m2");
        Assert.assertTrue(m2.isPresent());
    }

    public void test_sub_events_interaction_use() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<InteractionUse> iu1 = getInteractionUseByName(sequenceDiagram.get(), "ref1");
        Assert.assertTrue(iu1.isPresent());

        java.util.Optional<InteractionUse> iu2 = getInteractionUseByName(sequenceDiagram.get(), "ref2");
        Assert.assertTrue(iu2.isPresent());

        java.util.Optional<InteractionUse> iu3 = getInteractionUseByName(sequenceDiagram.get(), "ref.3");
        Assert.assertTrue(iu3.isPresent());
    }

    public void test_sub_events_combined_fragment() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<CombinedFragment> cf1 = getCombinedFragmentByName(sequenceDiagram.get(), "opt1");
        Assert.assertTrue(cf1.isPresent());

        java.util.Optional<CombinedFragment> cf2 = getCombinedFragmentByName(sequenceDiagram.get(), "opt2");
        Assert.assertTrue(cf2.isPresent());
    }

    public void test_sub_events_operand() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<Operand> op1 = getOperandByName(sequenceDiagram.get(), "op1");
        Assert.assertTrue(op1.isPresent());

        java.util.Optional<Operand> op2 = getOperandByName(sequenceDiagram.get(), "op2");
        Assert.assertTrue(op2.isPresent());
    }
}
