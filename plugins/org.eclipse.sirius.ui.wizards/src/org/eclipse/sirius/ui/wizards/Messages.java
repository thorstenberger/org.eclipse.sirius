/**
 * Copyright (c) 2017 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Obeo - initial API and implementation
 */
package org.eclipse.sirius.ui.wizards;

import org.eclipse.osgi.util.NLS;

/**
 * Sirius UI Wizards messages caltalog.
 * 
 * @author <a href="mailto:axel.richard@obeo.fr">Axel Richard</a>
 *
 */
public final class Messages extends NLS {

    private static final String BASE_NAME = "org.eclipse.sirius.ui.wizards.Messages"; //$NON-NLS-1$

    static {
        // initialize resource bundle
        NLS.initializeMessages(BASE_NAME, Messages.class);
    }

    // CHECKSTYLE:OFF
    public static String CreateEMFModelWizard_windowTitle;

    public static String CreateEMFModelWizard_modelNamePrefix;

    public static String CreateEMFModelWizard_errorInstantiateRootElement;

    public static String SelectEMFMetamodelWizardPage_title;

    public static String SelectEMFMetamodelWizardPage_description;

    public static String SelectEMFMetamodelWizardPage_metamodelLabel;

    public static String SelectEMFMetamodelWizardPage_documentationLabel;

    public static String SelectRootElementWizardPage_title;

    public static String SelectRootElementWizardPage_description;

    public static String SelectRootElementWizardPage_label;

    public static String SelectRootElementWizardPage_checkboxLabel;

    public static String NameAndLocationWizardPage_title;

    public static String NameAndLocationWizardPage_description;

    public static String NameAndLocationWizardPage_errorMessage;
    // CHECKSTYLE:ON
}
