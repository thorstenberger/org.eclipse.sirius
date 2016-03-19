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
package org.eclipse.sirius.tests.swtbot.sequence;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.sirius.diagram.sequence.SequenceDDiagram;
import org.eclipse.sirius.diagram.sequence.business.internal.layout.LayoutConstants;
import org.eclipse.sirius.diagram.sequence.ui.tool.internal.edit.part.SequenceDiagramEditPart;
import org.eclipse.sirius.ext.base.Option;
import org.eclipse.sirius.ext.base.Options;
import org.eclipse.sirius.sample.interactions.Execution;
import org.eclipse.sirius.sample.interactions.Interaction;
import org.eclipse.sirius.tests.swtbot.support.api.condition.CheckEditPartMoved;
import org.eclipse.sirius.tests.swtbot.support.api.condition.CheckEditPartResized;
import org.eclipse.sirius.tests.swtbot.support.api.condition.OperationDoneCondition;
import org.eclipse.sirius.tests.swtbot.support.api.matcher.WithSemantic;
import org.eclipse.sirius.tests.unit.diagram.sequence.InteractionsConstants;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditPart;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.junit.Assert;

/**
 * Execution Move Tests.
 * 
 * @author pcdavid
 */
public class ExecutionMoveTests extends AbstractDefaultModelSequenceTests {

    private static final String PATH = DATA_UNIT_DIR + "vp-851/";

    private static final String REPRESENTATION_NAME = "Sequence Diagram on Sample";

    private static final String MODEL = "executions.interactions";

    private static final String SESSION_FILE = "executions.aird";

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected String getSemanticModel() {
        return MODEL;
    }

    @Override
    protected String getSessionModel() {
        return SESSION_FILE;
    }

    @Override
    protected Option<String> getDRepresentationName() {
        return Options.newSome(REPRESENTATION_NAME);
    }

    private SWTBotGefEditPart sequenceDiagramBot;

    private Interaction interaction;

    private SWTBotGefEditPart instanceRoleABot;

    private SWTBotGefEditPart instanceRoleBBot;

    private SWTBotGefEditPart[] bots = new SWTBotGefEditPart[9];

    private EObject[] executions = new EObject[9];

    private Rectangle[] bounds = new Rectangle[9];

    private static final int NB_ENDS_EXECUTIONS = (5 * 2) + (4 * 2);

    @Override
    protected void onSetUpAfterOpeningDesignerPerspective() throws Exception {
        super.onSetUpAfterOpeningDesignerPerspective();

        editor.mainEditPart().part().getViewer().setProperty(SnapToGeometry.PROPERTY_SNAP_ENABLED, Boolean.FALSE);
        editor.mainEditPart().part().getViewer().setProperty(SnapToGrid.PROPERTY_GRID_ENABLED, Boolean.FALSE);

        maximizeEditor(editor);
        editor.reveal(LIFELINE_A);

        sequenceDiagramBot = editor.mainEditPart();
        SequenceDiagramEditPart sequenceDiagramEditPart = (SequenceDiagramEditPart) sequenceDiagramBot.part();
        SequenceDDiagram sequenceDDiagram = (SequenceDDiagram) sequenceDiagramEditPart.resolveSemanticElement();
        interaction = (Interaction) sequenceDDiagram.getTarget();

        // InstanceRoles
        instanceRoleABot = editor.getEditPart(LIFELINE_A);
        instanceRoleBBot = editor.getEditPart(LIFELINE_B);

        // Execs
        int[] indices = new int[] { 2, 3, 8, 4, 6, 0, 1, 7, 5 };
        EList<Execution> allExecutions = interaction.getExecutions();
        for (int i = 0; i < indices.length; i++) {
            executions[i] = allExecutions.get(i);
        }

        bots[0] = instanceRoleABot.parent().descendants(WithSemantic.withSemantic(executions[0])).get(0);
        bots[1] = bots[0].descendants(WithSemantic.withSemantic(executions[1])).get(0);
        bots[2] = instanceRoleABot.parent().descendants(WithSemantic.withSemantic(executions[2])).get(0);
        bots[3] = bots[1].descendants(WithSemantic.withSemantic(executions[3])).get(0);
        bots[4] = bots[0].descendants(WithSemantic.withSemantic(executions[4])).get(0);
        bots[5] = instanceRoleBBot.parent().descendants(WithSemantic.withSemantic(executions[5])).get(0);
        bots[6] = instanceRoleBBot.parent().descendants(WithSemantic.withSemantic(executions[6])).get(0);
        bots[7] = instanceRoleBBot.parent().descendants(WithSemantic.withSemantic(executions[7])).get(0);
        bots[8] = instanceRoleBBot.parent().descendants(WithSemantic.withSemantic(executions[8])).get(0);

        for (int i = 0; i < bots.length; i++) {
            bounds[i] = editor.getBounds(bots[i]);
        }
    }

    /**
     * Move e6 with a message in lifeline start zone.
     */
    public void test_move_execution_with_message_in_init_zone() {
        ICondition done = new OperationDoneCondition();
        arrangeAll();
        bot.waitUntil(done);

        Rectangle bounds = getExecutionLogicalBounds(LIFELINE_B, 0);
        Point e6Center = bounds.getCenter();

        done = new OperationDoneCondition();
        createMessage(InteractionsConstants.READ_TOOL_ID, e6Center, new Point(getLifelineScreenX(LIFELINE_C), e6Center.y));
        bot.waitUntil(done);

        done = new OperationDoneCondition();
        arrangeAll();
        bot.waitUntil(done);

        bounds = getExecutionLogicalBounds(LIFELINE_B, 0);
        assertEquals(130, bounds.y);
        assertEquals(40, bounds.height);

        int pos = getSequenceMessageVerticalPosition("m1");
        assertEquals(150, pos);

        Rectangle following = getExecutionLogicalBounds(LIFELINE_B, 1);
        assertEquals(190, following.y);
        assertEquals(40, following.height);

        done = new OperationDoneCondition();
        int dY = 15;
        editor.drag(bounds.getTop(), bounds.getTop().getTranslated(0, -dY));
        bot.waitUntil(done);

        bounds = getExecutionLogicalBounds(LIFELINE_B, 0);
        assertEquals(115, bounds.y);
        assertEquals(40, bounds.height);

        pos = getSequenceMessageVerticalPosition("m1");
        assertEquals(135, pos);

        following = getExecutionLogicalBounds(LIFELINE_B, 1);
        assertEquals(190, following.y);
        assertEquals(40, following.height);

    }

    /**
     * Move executions[2] between executions[1] and e5
     */
    public void test_move_execution_inside_new_parent_between_siblings() {
        int newY = (bounds[1].getBottom().y + bounds[4].y) / 2;
        int dy = newY - bounds[2].y;

        bots[2].select();

        ICondition conditionE1 = new CheckEditPartResized(bots[0]);
        ICondition conditionE5 = new CheckEditPartMoved(bots[4]);
        ICondition conditionE8 = new CheckEditPartMoved(bots[7]);
        ICondition conditionE9 = new CheckEditPartResized(bots[8]);

        editor.drag(bots[2], bounds[2].x, newY);

        bot.waitUntil(conditionE1);
        bot.waitUntil(conditionE5);
        bot.waitUntil(conditionE8);
        bot.waitUntil(conditionE9);

        bots[2] = bots[0].descendants(WithSemantic.withSemantic(executions[2])).get(0);

        // On lifeline 'a'
        int dxShift = 3 * LayoutConstants.DEFAULT_EXECUTION_WIDTH / 4;
        int dyShift = bounds[2].height;
        Assert.assertEquals(bounds[0].getResized(0, dyShift), editor.getBounds(bots[0]));
        Assert.assertEquals(bounds[1], editor.getBounds(bots[1]));
        Assert.assertEquals(bounds[2].getTranslated(dxShift, dy), editor.getBounds(bots[2]));
        Assert.assertEquals(bounds[3], editor.getBounds(bots[3]));
        Assert.assertEquals(bounds[4].getTranslated(0, dyShift), editor.getBounds(bots[4]));

        // On lifelines 'b' and 'c'
        Assert.assertEquals(bounds[5], editor.getBounds(bots[5]));
        Assert.assertEquals(bounds[6], editor.getBounds(bots[6]));
        Assert.assertEquals(bounds[7].getTranslated(0, dyShift), editor.getBounds(bots[7]));
        Assert.assertEquals(bounds[8].getResized(0, dyShift), editor.getBounds(bots[8]));

        validateOrdering(NB_ENDS_EXECUTIONS);
    }

    /**
     * Move executions[2] just above executions[0] but close enough to trigger a
     * shift.
     */
    public void test_move_plain_execution_above_sibling_shifts_sibling_and_children() {
        int newY = 170;
        int dy = newY - bounds[2].y;

        bots[2].select();

        ICondition conditionE1 = new CheckEditPartMoved(bots[0]);
        ICondition conditionE2 = new CheckEditPartMoved(bots[1]);
        ICondition conditionE3 = new CheckEditPartMoved(bots[2]);
        ICondition conditionE4 = new CheckEditPartMoved(bots[3]);
        ICondition conditionE5 = new CheckEditPartMoved(bots[4]);
        ICondition conditionE7 = new CheckEditPartMoved(bots[6]);
        ICondition conditionE8 = new CheckEditPartMoved(bots[7]);
        ICondition conditionE9 = new CheckEditPartMoved(bots[8]);

        editor.drag(bots[2], bounds[2].x, newY);

        bot.waitUntil(conditionE1);
        bot.waitUntil(conditionE2);
        bot.waitUntil(conditionE3);
        bot.waitUntil(conditionE4);
        bot.waitUntil(conditionE5);
        bot.waitUntil(conditionE7);
        bot.waitUntil(conditionE8);
        bot.waitUntil(conditionE9);

        // On lifeline 'a'
        int dyShift = bounds[2].height;
        Assert.assertEquals(bounds[0].getTranslated(0, dyShift), editor.getBounds(bots[0]));
        Assert.assertEquals(bounds[1].getTranslated(0, dyShift), editor.getBounds(bots[1]));
        Assert.assertEquals(bounds[2].getTranslated(0, dy), editor.getBounds(bots[2]));
        Assert.assertEquals(bounds[3].getTranslated(0, dyShift), editor.getBounds(bots[3]));
        Assert.assertEquals(bounds[4].getTranslated(0, dyShift), editor.getBounds(bots[4]));

        // On lifelines 'b' and 'c'
        Assert.assertEquals(bounds[5], editor.getBounds(bots[5]));
        Assert.assertEquals(bounds[6].getTranslated(0, dyShift), editor.getBounds(bots[6]));
        Assert.assertEquals(bounds[7].getTranslated(0, dyShift), editor.getBounds(bots[7]));
        Assert.assertEquals(bounds[8].getTranslated(0, dyShift), editor.getBounds(bots[8]));

        validateOrdering(NB_ENDS_EXECUTIONS);
    }

    /**
     * Move the top of executions[0] inside executions[2].
     */
    public void test_move_execution_group_on_top_of_smaller_execution() {
        int newY = bounds[2].getCenter().y;
        int dy = newY - bounds[0].y;

        bots[0].select();

        ICondition conditionE3 = new CheckEditPartResized(bots[2]);
        ICondition conditionE8 = new CheckEditPartResized(bots[7]);

        editor.drag(bots[0], bounds[0].x, newY);

        bot.waitUntil(conditionE3);
        bot.waitUntil(conditionE8);

        // executions[0], executions[1], e4 and e5 reparented => reget bot
        bots[0] = bots[2].descendants(WithSemantic.withSemantic(executions[0])).get(0);
        bots[1] = bots[0].descendants(WithSemantic.withSemantic(executions[1])).get(0);
        bots[3] = bots[1].descendants(WithSemantic.withSemantic(executions[3])).get(0);
        bots[4] = bots[0].descendants(WithSemantic.withSemantic(executions[4])).get(0);

        // On lifeline 'a'
        int dxShift = 3 * LayoutConstants.DEFAULT_EXECUTION_WIDTH / 4;
        int dyShift = bounds[0].height;
        Assert.assertEquals(bounds[0].getTranslated(dxShift, dy), editor.getBounds(bots[0]));
        Assert.assertEquals(bounds[1].getTranslated(dxShift, dy), editor.getBounds(bots[1]));
        Assert.assertEquals(bounds[2].getResized(0, dyShift), editor.getBounds(bots[2]));
        Assert.assertEquals(bounds[3].getTranslated(dxShift, dy), editor.getBounds(bots[3]));
        Assert.assertEquals(bounds[4].getTranslated(dxShift, dy), editor.getBounds(bots[4]));

        // On lifelines 'b' and 'c'
        Assert.assertEquals(bounds[5], editor.getBounds(bots[5]));
        Assert.assertEquals(bounds[6], editor.getBounds(bots[6]));
        Assert.assertEquals(bounds[7].getResized(0, dyShift), editor.getBounds(bots[7]));
        Assert.assertEquals(bounds[8], editor.getBounds(bots[8]));

        validateOrdering(NB_ENDS_EXECUTIONS);

    }

    /**
     * Move executions[2] at the beginning of executions[0] to reparent
     * executions[2], expand executions[0] and shift previous executions[0]
     * children.
     */
    public void test_move_execution_inside_new_parent_with_expansion() {
        int newY = bounds[0].y + LayoutConstants.EXECUTION_CHILDREN_MARGIN;
        int dy = newY - bounds[2].y;

        bots[2].select();

        ICondition conditionE1 = new CheckEditPartResized(bots[0]);
        ICondition conditionE2 = new CheckEditPartMoved(bots[1]);
        ICondition conditionE4 = new CheckEditPartMoved(bots[3]);
        ICondition conditionE5 = new CheckEditPartMoved(bots[4]);
        ICondition conditionE7 = new CheckEditPartResized(bots[6]);
        ICondition conditionE8 = new CheckEditPartMoved(bots[7]);
        ICondition conditionE9 = new CheckEditPartMoved(bots[8]);

        editor.drag(bots[2], bounds[2].x, newY);

        bot.waitUntil(conditionE1);
        bot.waitUntil(conditionE2);
        bot.waitUntil(conditionE4);
        bot.waitUntil(conditionE5);
        bot.waitUntil(conditionE7);
        bot.waitUntil(conditionE8);
        bot.waitUntil(conditionE9);

        bots[2] = bots[0].descendants(WithSemantic.withSemantic(executions[2])).get(0);

        // On lifeline 'a'
        int dxShift = 3 * LayoutConstants.DEFAULT_EXECUTION_WIDTH / 4;
        int dyShift = bounds[2].height;
        Assert.assertEquals(bounds[0].getResized(0, dyShift), editor.getBounds(bots[0]));
        Assert.assertEquals(bounds[1].getTranslated(0, dyShift), editor.getBounds(bots[1]));
        Assert.assertEquals(bounds[2].getTranslated(dxShift, dy), editor.getBounds(bots[2]));
        Assert.assertEquals(bounds[3].getTranslated(0, dyShift), editor.getBounds(bots[3]));
        Assert.assertEquals(bounds[4].getTranslated(0, dyShift), editor.getBounds(bots[4]));

        // On lifelines 'b' and 'c'
        Assert.assertEquals(bounds[5], editor.getBounds(bots[5]));
        Assert.assertEquals(bounds[6].getResized(0, dyShift), editor.getBounds(bots[6]));
        Assert.assertEquals(bounds[7].getTranslated(0, dyShift), editor.getBounds(bots[7]));
        Assert.assertEquals(bounds[8].getTranslated(0, dyShift), editor.getBounds(bots[8]));

        validateOrdering(NB_ENDS_EXECUTIONS);
    }

    /**
     * Move executions[1] out of executions[0], just above it
     */
    public void test_move_execution_out_of_parent_and_above_it() {
        int newY = 170;
        int dy = newY - bounds[1].y;

        bots[1].select();

        ICondition conditionE7 = new CheckEditPartMoved(bots[6]);
        ICondition conditionE8 = new CheckEditPartMoved(bots[7]);
        ICondition conditionE9 = new CheckEditPartMoved(bots[8]);

        editor.drag(bots[1], bounds[1].x, newY);

        bot.waitUntil(conditionE7);
        bot.waitUntil(conditionE8);
        bot.waitUntil(conditionE9);

        bots[0] = instanceRoleABot.parent().descendants(WithSemantic.withSemantic(executions[0])).get(0);
        bots[1] = instanceRoleABot.parent().descendants(WithSemantic.withSemantic(executions[1])).get(0);
        bots[2] = instanceRoleABot.parent().descendants(WithSemantic.withSemantic(executions[2])).get(0);
        bots[3] = bots[1].descendants(WithSemantic.withSemantic(executions[3])).get(0);
        bots[4] = bots[0].descendants(WithSemantic.withSemantic(executions[4])).get(0);

        // On lifeline 'a'
        int dxShift = -3 * LayoutConstants.DEFAULT_EXECUTION_WIDTH / 4;
        int dyShift = bounds[1].height;
        Assert.assertEquals(bounds[0].getTranslated(0, dyShift), editor.getBounds(bots[0]));
        Assert.assertEquals(bounds[1].getTranslated(dxShift, dy), editor.getBounds(bots[1]));
        Assert.assertEquals(bounds[2].getTranslated(0, dyShift), editor.getBounds(bots[2]));
        Assert.assertEquals(bounds[3].getTranslated(dxShift, dy), editor.getBounds(bots[3]));
        Assert.assertEquals(bounds[4].getTranslated(0, dyShift), editor.getBounds(bots[4]));

        // On lifelines 'b' and 'c'
        Assert.assertEquals(bounds[5], editor.getBounds(bots[5]));
        Assert.assertEquals(bounds[6].getTranslated(0, dyShift), editor.getBounds(bots[6]));
        Assert.assertEquals(bounds[7].getTranslated(0, dyShift), editor.getBounds(bots[7]));
        Assert.assertEquals(bounds[8].getTranslated(0, dyShift), editor.getBounds(bots[8]));

        validateOrdering(NB_ENDS_EXECUTIONS);
    }

    /**
     * Move e5 partially below executions[0] to trigger a expansion of
     * executions[0].
     */
    public void test_move_execution_partially_below_parent_trigger_expansion1() {
        int newY = bounds[0].getBottom().y - bounds[4].height + 5;
        int dy = newY - bounds[4].y;

        bots[4].select();

        ICondition conditionE1 = new CheckEditPartResized(bots[0]);
        ICondition conditionE3 = new CheckEditPartMoved(bots[2]);
        ICondition conditionE5 = new CheckEditPartMoved(bots[4]);
        ICondition conditionE8 = new CheckEditPartMoved(bots[7]);

        editor.drag(bots[4], bounds[4].x, newY);

        bot.waitUntil(conditionE1);
        bot.waitUntil(conditionE3);
        bot.waitUntil(conditionE5);
        bot.waitUntil(conditionE8);

        // On lifeline 'a'
        int dyShift = bounds[4].height;
        Assert.assertEquals(bounds[0].getResized(0, dyShift), editor.getBounds(bots[0]));
        Assert.assertEquals(bounds[1], editor.getBounds(bots[1]));
        Assert.assertEquals(bounds[2].getTranslated(0, dyShift), editor.getBounds(bots[2]));
        Assert.assertEquals(bounds[3], editor.getBounds(bots[3]));
        Assert.assertEquals(bounds[4].getTranslated(0, dy), editor.getBounds(bots[4]));

        // On lifelines 'b' and 'c'
        Assert.assertEquals(bounds[5], editor.getBounds(bots[5]));
        Assert.assertEquals(bounds[6], editor.getBounds(bots[6]));
        Assert.assertEquals(bounds[7].getTranslated(0, dyShift), editor.getBounds(bots[7]));
        Assert.assertEquals(bounds[8], editor.getBounds(bots[8]));

        validateOrdering(NB_ENDS_EXECUTIONS);
    }

    /**
     * Move e5 partially below executions[0] to trigger a expansion of
     * executions[0].
     */
    public void test_move_execution_partially_below_parent_trigger_expansion2() {
        int newY = bounds[0].getBottom().y - bounds[4].height + 15;
        int dy = newY - bounds[4].y;

        bots[4].select();

        ICondition conditionE1 = new CheckEditPartResized(bots[0]);
        ICondition conditionE3 = new CheckEditPartMoved(bots[2]);
        ICondition conditionE5 = new CheckEditPartMoved(bots[4]);
        ICondition conditionE8 = new CheckEditPartMoved(bots[7]);

        editor.drag(bots[4], bounds[4].x, newY);

        bot.waitUntil(conditionE1);
        bot.waitUntil(conditionE3);
        bot.waitUntil(conditionE5);
        bot.waitUntil(conditionE8);

        // On lifeline 'a'
        int dyShift = bounds[4].height;
        Assert.assertEquals(bounds[0].getResized(0, dyShift), editor.getBounds(bots[0]));
        Assert.assertEquals(bounds[1], editor.getBounds(bots[1]));
        Assert.assertEquals(bounds[2].getTranslated(0, dyShift), editor.getBounds(bots[2]));
        Assert.assertEquals(bounds[3], editor.getBounds(bots[3]));
        Assert.assertEquals(bounds[4].getTranslated(0, dy), editor.getBounds(bots[4]));

        // On lifelines 'b' and 'c'
        Assert.assertEquals(bounds[5], editor.getBounds(bots[5]));
        Assert.assertEquals(bounds[6], editor.getBounds(bots[6]));
        Assert.assertEquals(bounds[7].getTranslated(0, dyShift), editor.getBounds(bots[7]));
        Assert.assertEquals(bounds[8], editor.getBounds(bots[8]));

        validateOrdering(NB_ENDS_EXECUTIONS);
    }

    /**
     * Move e5 partially below executions[0] to trigger a expansion of
     * executions[0].
     */
    public void test_move_execution_partially_below_parent_trigger_expansion3() {
        int newY = bounds[0].getBottom().y - bounds[4].height + 25;
        int dy = newY - bounds[4].y;

        bots[4].select();

        ICondition conditionE1 = new CheckEditPartResized(bots[0]);
        ICondition conditionE3 = new CheckEditPartMoved(bots[2]);
        ICondition conditionE5 = new CheckEditPartMoved(bots[4]);
        ICondition conditionE8 = new CheckEditPartMoved(bots[7]);

        editor.drag(bots[4], bounds[4].x, newY);

        bot.waitUntil(conditionE1);
        bot.waitUntil(conditionE3);
        bot.waitUntil(conditionE5);
        bot.waitUntil(conditionE8);

        // On lifeline 'a'
        int dyShift = bounds[4].height;
        Assert.assertEquals(bounds[0].getResized(0, dyShift), editor.getBounds(bots[0]));
        Assert.assertEquals(bounds[1], editor.getBounds(bots[1]));
        Assert.assertEquals(bounds[2].getTranslated(0, dyShift), editor.getBounds(bots[2]));
        Assert.assertEquals(bounds[3], editor.getBounds(bots[3]));
        Assert.assertEquals(bounds[4].getTranslated(0, dy), editor.getBounds(bots[4]));

        // On lifelines 'b' and 'c'
        Assert.assertEquals(bounds[5], editor.getBounds(bots[5]));
        Assert.assertEquals(bounds[6], editor.getBounds(bots[6]));
        Assert.assertEquals(bounds[7].getTranslated(0, dyShift), editor.getBounds(bots[7]));
        Assert.assertEquals(bounds[8], editor.getBounds(bots[8]));

        validateOrdering(NB_ENDS_EXECUTIONS);
    }

    /**
     * Move e5 partially below executions[0] to check that it is not allowed.
     */
    public void test_move_execution_partially_below_parent_not_allowed() {
        int newY = bounds[0].getBottom().y - bounds[4].height + 30;

        bots[4].select();

        editor.drag(bots[4], bounds[4].x, newY);

        bot.sleep(500);

        // On lifeline 'a'
        Assert.assertEquals(bounds[0], editor.getBounds(bots[0]));
        Assert.assertEquals(bounds[1], editor.getBounds(bots[1]));
        Assert.assertEquals(bounds[2], editor.getBounds(bots[2]));
        Assert.assertEquals(bounds[3], editor.getBounds(bots[3]));
        Assert.assertEquals(bounds[4], editor.getBounds(bots[4]));

        // On lifelines 'b' and 'c'
        Assert.assertEquals(bounds[5], editor.getBounds(bots[5]));
        Assert.assertEquals(bounds[6], editor.getBounds(bots[6]));
        Assert.assertEquals(bounds[7], editor.getBounds(bots[7]));
        Assert.assertEquals(bounds[8], editor.getBounds(bots[8]));

        validateOrdering(NB_ENDS_EXECUTIONS);
    }

    /**
     * Move executions[2] on executions[0] before executions[1].
     */
    public void test_move_execution_inside_new_parent_before_siblings() {
        int newY = (bounds[0].y + bounds[1].y) / 2 - 2;
        int dy = newY - bounds[2].y;

        bots[2].select();

        ICondition conditionE1 = new CheckEditPartResized(bots[0]);
        ICondition conditionE2 = new CheckEditPartMoved(bots[1]);
        ICondition conditionE4 = new CheckEditPartMoved(bots[3]);
        ICondition conditionE5 = new CheckEditPartMoved(bots[4]);
        ICondition conditionE7 = new CheckEditPartResized(bots[6]);
        ICondition conditionE8 = new CheckEditPartMoved(bots[7]);
        ICondition conditionE9 = new CheckEditPartMoved(bots[8]);

        editor.drag(bots[2], bounds[2].x, newY);

        bot.waitUntil(conditionE1);
        bot.waitUntil(conditionE2);
        bot.waitUntil(conditionE4);
        bot.waitUntil(conditionE5);
        bot.waitUntil(conditionE7);
        bot.waitUntil(conditionE8);
        bot.waitUntil(conditionE9);

        bots[2] = bots[0].descendants(WithSemantic.withSemantic(executions[2])).get(0);

        // On lifeline 'a'
        int dxShift = 3 * LayoutConstants.DEFAULT_EXECUTION_WIDTH / 4;
        int dyShift = bounds[2].height;
        int dErr = 3; // layout, e7 is resized and there is only 2 pix between 2
                      // two consecutives events
        Assert.assertEquals(bounds[0].getResized(0, dyShift + dErr), editor.getBounds(bots[0]));
        Assert.assertEquals(bounds[1].getTranslated(0, dyShift + dErr), editor.getBounds(bots[1]));
        Assert.assertEquals(bounds[2].getTranslated(dxShift, dy), editor.getBounds(bots[2]));
        Assert.assertEquals(bounds[3].getTranslated(0, dyShift + dErr), editor.getBounds(bots[3]));
        Assert.assertEquals(bounds[4].getTranslated(0, dyShift + dErr), editor.getBounds(bots[4]));

        // On lifelines 'b' and 'c'
        Assert.assertEquals(bounds[5], editor.getBounds(bots[5]));
        Assert.assertEquals(bounds[6].getResized(0, dyShift + dErr), editor.getBounds(bots[6]));
        Assert.assertEquals(bounds[7].getTranslated(0, dyShift + dErr), editor.getBounds(bots[7]));
        Assert.assertEquals(bounds[8].getTranslated(0, dyShift + dErr), editor.getBounds(bots[8]));

        validateOrdering(NB_ENDS_EXECUTIONS);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        sequenceDiagramBot = null;
        interaction = null;
        instanceRoleABot = null;
        instanceRoleBBot = null;
        bots = null;
        executions = null;
        bounds = null;
    }
}
