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

import org.eclipse.sirius.diagram.sequence.business.internal.elements.CombinedFragment;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Execution;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.ISequenceEvent;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.InteractionUse;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Lifeline;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Message;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.Operand;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.SequenceDiagram;
import org.eclipse.sirius.diagram.sequence.business.internal.elements.State;

import org.eclipse.sirius.tests.SiriusTestsPlugin;
import org.eclipse.sirius.tests.unit.diagram.sequence.AbstractSequenceSiriusDiagramTests;
import org.eclipse.sirius.tests.unit.diagram.sequence.InteractionsConstants;
import org.junit.Assert;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Test for the various basic identification and structural navigation methods
 * between sequence diagram elements.
 * 
 * @author pcdavid
 */
public class SequenceDiagramElementsSubEventsTests extends AbstractSequenceSiriusDiagramTests {

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
        checkSubEvents(p1.get(), 5, 2, 1, 1, 0, 1, 0);

        java.util.Optional<Lifeline> p2 = getLifelineByName(sequenceDiagram.get(), "p2");
        Assert.assertTrue(p2.isPresent());
        checkSubEvents(p2.get(), 6, 2, 1, 1, 0, 1, 1);
    }

    public void test_sub_events_execution() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<Execution> e1 = getExecutionByName(sequenceDiagram.get(), "e1");
        Assert.assertTrue(e1.isPresent());
        checkSubEvents(e1.get(), 1, 0, 1, 0, 0, 0, 0);

        java.util.Optional<Execution> e2 = getExecutionByName(sequenceDiagram.get(), "e2");
        Assert.assertTrue(e2.isPresent());
        checkSubEvents(e2.get(), 0, 0, 0, 0, 0, 0, 0);

        java.util.Optional<Execution> e3 = getExecutionByName(sequenceDiagram.get(), "e3");
        Assert.assertTrue(e3.isPresent());
        checkSubEvents(e3.get(), 1, 0, 0, 1, 0, 0, 0);

        java.util.Optional<Execution> e4 = getExecutionByName(sequenceDiagram.get(), "e4");
        Assert.assertTrue(e4.isPresent());
        checkSubEvents(e4.get(), 0, 0, 0, 0, 0, 0, 0);

        java.util.Optional<Execution> e5 = getExecutionByName(sequenceDiagram.get(), "e5");
        Assert.assertTrue(e5.isPresent());
        checkSubEvents(e5.get(), 1, 0, 0, 1, 0, 0, 0);

        java.util.Optional<Execution> e6 = getExecutionByName(sequenceDiagram.get(), "e6");
        Assert.assertTrue(e6.isPresent());
        checkSubEvents(e6.get(), 1, 0, 0, 0, 0, 0, 1);

        java.util.Optional<Execution> e7 = getExecutionByName(sequenceDiagram.get(), "e7");
        Assert.assertTrue(e7.isPresent());
        checkSubEvents(e7.get(), 0, 0, 0, 0, 0, 0, 0);

        java.util.Optional<Execution> e8 = getExecutionByName(sequenceDiagram.get(), "e8");
        Assert.assertTrue(e8.isPresent());
        checkSubEvents(e8.get(), 1, 0, 0, 0, 0, 1, 0);

        java.util.Optional<Execution> e9 = getExecutionByName(sequenceDiagram.get(), "e9");
        Assert.assertTrue(e9.isPresent());
        checkSubEvents(e9.get(), 0, 0, 0, 0, 0, 0, 0);

        java.util.Optional<Execution> e10 = getExecutionByName(sequenceDiagram.get(), "e10");
        Assert.assertTrue(e10.isPresent());
        checkSubEvents(e10.get(), 1, 0, 0, 0, 0, 0, 1);

    }

    public void test_sub_events_state() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<State> s1 = getStateByName(sequenceDiagram.get(), "s1");
        Assert.assertTrue(s1.isPresent());
        checkSubEvents(s1.get(), 0, 0, 0, 0, 0, 0, 0);

        java.util.Optional<State> s2 = getStateByName(sequenceDiagram.get(), "s2");
        Assert.assertTrue(s2.isPresent());
        checkSubEvents(s2.get(), 0, 0, 0, 0, 0, 0, 0);

        java.util.Optional<State> s3 = getStateByName(sequenceDiagram.get(), "s3");
        Assert.assertTrue(s3.isPresent());
        checkSubEvents(s3.get(), 0, 0, 0, 0, 0, 0, 0);
    }

    public void test_sub_events_message() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<Message> m1 = getMessageByName(sequenceDiagram.get(), "m1");
        Assert.assertTrue(m1.isPresent());
        checkSubEvents(m1.get(), 0, 0, 0, 0, 0, 0, 0);

        java.util.Optional<Message> m2 = getMessageByName(sequenceDiagram.get(), "m2");
        Assert.assertTrue(m2.isPresent());
        checkSubEvents(m2.get(), 0, 0, 0, 0, 0, 0, 0);
    }

    public void test_sub_events_interaction_use() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<InteractionUse> iu1 = getInteractionUseByName(sequenceDiagram.get(), "ref1");
        Assert.assertTrue(iu1.isPresent());
        checkSubEvents(iu1.get(), 0, 0, 0, 0, 0, 0, 0);

        java.util.Optional<InteractionUse> iu2 = getInteractionUseByName(sequenceDiagram.get(), "ref2");
        Assert.assertTrue(iu2.isPresent());
        checkSubEvents(iu2.get(), 0, 0, 0, 0, 0, 0, 0);

        java.util.Optional<InteractionUse> iu3 = getInteractionUseByName(sequenceDiagram.get(), "ref.3");
        Assert.assertTrue(iu3.isPresent());
        checkSubEvents(iu3.get(), 0, 0, 0, 0, 0, 0, 0);
    }

    public void test_sub_events_combined_fragment() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<CombinedFragment> cf1 = getCombinedFragmentByName(sequenceDiagram.get(), "opt1");
        Assert.assertTrue(cf1.isPresent());
        checkSubEvents(cf1.get(), 2, 0, 0, 0, 2, 0, 0);

        java.util.Optional<CombinedFragment> cf2 = getCombinedFragmentByName(sequenceDiagram.get(), "opt2");
        Assert.assertTrue(cf2.isPresent());
        checkSubEvents(cf2.get(), 1, 0, 0, 0, 1, 0, 0);
    }

    public void test_sub_events_operand() throws Exception {
        java.util.Optional<SequenceDiagram> sequenceDiagram = openSequenceDiagramOfType(COMPLEX_DIAGRAM, REPRESENTATION_TYPE);
        Assert.assertTrue(sequenceDiagram.isPresent());

        java.util.Optional<Operand> op1 = getOperandByName(sequenceDiagram.get(), "op1");
        Assert.assertTrue(op1.isPresent());
        checkSubEvents(op1.get(), 1, 0, 0, 0, 0, 1, 0);

        java.util.Optional<Operand> op3 = getOperandByName(sequenceDiagram.get(), "op3");
        Assert.assertTrue(op3.isPresent());
        checkSubEvents(op3.get(), 1, 0, 0, 1, 0, 0, 0);

        java.util.Optional<Operand> op4 = getOperandByName(sequenceDiagram.get(), "op4");
        Assert.assertTrue(op4.isPresent());
        checkSubEvents(op4.get(), 1, 1, 0, 0, 0, 0, 0);

        java.util.Optional<Operand> op2 = getOperandByName(sequenceDiagram.get(), "op2");
        Assert.assertTrue(op2.isPresent());
        checkSubEvents(op2.get(), 5, 2, 1, 2, 0, 0, 0);

        java.util.Optional<Operand> op5 = getOperandByName(sequenceDiagram.get(), "op5");
        Assert.assertTrue(op5.isPresent());
        checkSubEvents(op5.get(), 1, 1, 0, 0, 0, 0, 0);

        java.util.Optional<Operand> op6 = getOperandByName(sequenceDiagram.get(), "op6");
        Assert.assertTrue(op6.isPresent());
        checkSubEvents(op6.get(), 4, 2, 0, 0, 0, 2, 0);
    }

    private void checkSubEvents(ISequenceEvent ise, int subs, int execs, int ius, int cfs, int ops, int msgs, int states) {
        // Validate parameters
        Assert.assertEquals("Wrong parameters : subs should be the sum of the other numbers.", subs, execs + cfs + ius + msgs + ops + states);

        List<ISequenceEvent> subEvents = ise.getSubEvents();
        List<InteractionUse> subIUs = Lists.newArrayList(Iterables.filter(subEvents, InteractionUse.class));
        List<CombinedFragment> subCFs = Lists.newArrayList(Iterables.filter(subEvents, CombinedFragment.class));
        List<Execution> subExecs = Lists.newArrayList(Iterables.filter(subEvents, Execution.class));
        List<Message> subMsgs = Lists.newArrayList(Iterables.filter(subEvents, Message.class));
        List<Operand> subOps = Lists.newArrayList(Iterables.filter(subEvents, Operand.class));
        List<State> subStates = Lists.newArrayList(Iterables.filter(subEvents, State.class));

        Assert.assertEquals(subs, subEvents.size());
        Assert.assertEquals(execs, subExecs.size());
        Assert.assertEquals(ius, subIUs.size());
        Assert.assertEquals(cfs, subCFs.size());
        Assert.assertEquals(ops, subOps.size());
        Assert.assertEquals(msgs, subMsgs.size());
        Assert.assertEquals(states, subStates.size());

    }
}
