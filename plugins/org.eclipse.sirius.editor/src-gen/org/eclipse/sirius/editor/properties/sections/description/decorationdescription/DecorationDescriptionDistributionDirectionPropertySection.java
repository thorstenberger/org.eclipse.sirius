/*******************************************************************************
 * Copyright (c) 2007, 2013 THALES GLOBAL SERVICES.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.editor.properties.sections.description.decorationdescription;

// Start of user code imports

import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.editor.editorPlugin.SiriusEditor;
import org.eclipse.sirius.editor.properties.sections.common.AbstractRadioButtonPropertySection;
import org.eclipse.sirius.viewpoint.description.DecorationDistributionDirection;
import org.eclipse.sirius.viewpoint.description.DescriptionPackage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

// End of user code imports

/**
 * A section for the distributionDirection property of a DecorationDescription
 * object.
 */
public class DecorationDescriptionDistributionDirectionPropertySection extends AbstractRadioButtonPropertySection {
    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#getDefaultLabelText()
     */
    protected String getDefaultLabelText() {
        return "DistributionDirection"; //$NON-NLS-1$
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#getLabelText()
     */
    protected String getLabelText() {
        String labelText;
        labelText = super.getLabelText() + "*:"; //$NON-NLS-1$
        // Start of user code get label text

        // End of user code get label text
        return labelText;
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#getFeature()
     */
    protected EAttribute getFeature() {
        return DescriptionPackage.eINSTANCE.getDecorationDescription_DistributionDirection();
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#getFeatureValue(int)
     */
    protected Object getFeatureValue(int index) {
        return getChoiceOfValues().get(index);
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#isEqual(int)
     */
    protected boolean isEqual(int index) {
        return getChoiceOfValues().get(index).equals(eObject.eGet(getFeature()));
    }

    /**
     * @see org.eclipse.sirius.editor.properties.sections.AbstractRadioButtonPropertySection#getEnumerationFeatureValues()
     */
    protected List<?> getChoiceOfValues() {
        return DecorationDistributionDirection.VALUES;
    }

    /**
     * {@inheritDoc}
     */
    public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);

        nameLabel.setToolTipText("Used when there are more than one decoration at a given position.");

        CLabel help = getWidgetFactory().createCLabel(composite, "");
        FormData data = new FormData();
        data.top = new FormAttachment(nameLabel, 0, SWT.TOP);
        data.left = new FormAttachment(nameLabel);
        help.setLayoutData(data);
        help.setImage(getHelpIcon());
        help.setToolTipText("Used when there are more than one decoration at a given position.");
        nameLabel.setFont(SiriusEditor.getFontRegistry().get("required"));

    }
}
