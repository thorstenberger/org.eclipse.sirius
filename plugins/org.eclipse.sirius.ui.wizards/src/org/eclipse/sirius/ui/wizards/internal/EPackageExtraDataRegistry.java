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
package org.eclipse.sirius.ui.wizards.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

/**
 * Handle contributions to /org.eclipse.sirius.ui.wizards/schema/org.eclipse.sirius.ui.wizards.exsd.
 * 
 * @author <a href="mailto:axel.richard@obeo.fr">Axel Richard</a>
 */
public class EPackageExtraDataRegistry {

    /** Extension-point ID. */
    private static final String EPACKAGE_EXTRA_DATA_EXTENSION_POINT = "org.eclipse.sirius.ui.wizards"; //$NON-NLS-1$

    /** NsURI attribute. */
    private static final String NS_URI_ATTRIBUTE = "ePackageNsURI"; //$NON-NLS-1$

    /** Label attribute. */
    private static final String LABEL_ATTRIBUTE = "ePackageLabel"; //$NON-NLS-1$

    /** Documentation attribute. */
    private static final String DOCUMENTATION_ATTRIBUTE = "ePackageDocumentation"; //$NON-NLS-1$

    /** Preferred root element attribute. */
    private static final String PREFERRED_ROOT_ELEMENT_ATTRIBUTE = "ePackagePreferredRootElement"; //$NON-NLS-1$

    /**
     * Find the label attribute of an EPackage.
     * 
     * @param ePackage
     *            to find its label.
     * @return the label attribute of the given ePackage.
     */
    public String getLabel(EPackage ePackage) {
        IConfigurationElement element = getEPackageConfigurationElement(ePackage);
        if (element != null) {
            return getLabel(element);
        }
        return null;
    }

    /**
     * Find the documentation attribute of an EPackage.
     * 
     * @param ePackage
     *            to find its documentation.
     * @return the documentation attribute of the given ePackage.
     */
    public String getDocumentation(EPackage ePackage) {
        IConfigurationElement element = getEPackageConfigurationElement(ePackage);
        if (element != null) {
            return getDocumentation(element);
        }
        return null;
    }

    /**
     * Find the preferred root element attribute of an EPackage.
     * 
     * @param ePackage
     *            to find its label.
     * @return the preferred root element attribute of the given ePackage.
     */
    public EClass getPreferredRootElement(EPackage ePackage) {
        IConfigurationElement element = getEPackageConfigurationElement(ePackage);
        if (element != null) {
            String rootElementName = getPreferredRootElement(element);
            EClassifier eClassifier = ePackage.getEClassifier(rootElementName);
            if (eClassifier instanceof EClass) {
                return (EClass) eClassifier;
            }
        }
        return null;
    }

    /**
     * Finds all extensions concerning the bundled image shape extension point.
     * 
     * @return all extensions concerning the bundled image shape extension point.
     */
    private IConfigurationElement[] getExtensions() {
        return Platform.getExtensionRegistry().getConfigurationElementsFor(EPACKAGE_EXTRA_DATA_EXTENSION_POINT);
    }

    /**
     * Find the IConfigurationElement of an EPackage.
     * 
     * @param ePackage
     *            to find its IConfigurationElement.
     * @return the IConfigurationElement of the given ePackage.
     */
    private IConfigurationElement getEPackageConfigurationElement(EPackage ePackage) {
        if (ePackage != null) {
            for (IConfigurationElement element : getExtensions()) {
                String ePackageNsURI = getNsURI(element);
                if (ePackageNsURI != null && ePackageNsURI.equals(ePackage.getNsURI())) {
                    return element;
                }
            }
        }
        return null;
    }

    /**
     * Find the ePackage nsURI attribute of an extension.
     * 
     * @param extension
     *            to find its ePackage.
     * @return the ePackage nsURI attribute of the given extension.
     */
    private String getNsURI(IConfigurationElement extension) {
        return extension.getAttribute(NS_URI_ATTRIBUTE);
    }

    /**
     * Find the label attribute of an extension.
     * 
     * @param extension
     *            to find its label.
     * @return the label attribute of the given extension.
     */
    private String getLabel(IConfigurationElement extension) {
        return extension.getAttribute(LABEL_ATTRIBUTE);
    }

    /**
     * Find the documentation attribute of an extension.
     * 
     * @param extension
     *            to find its documentation.
     * @return the documentation attribute of the given extension.
     */
    private String getDocumentation(IConfigurationElement extension) {
        return extension.getAttribute(DOCUMENTATION_ATTRIBUTE);
    }

    /**
     * Find the preferred root element attribute of an extension.
     * 
     * @param extension
     *            to find its preferred root element.
     * @return the preferred root element attribute of the given extension.
     */
    private String getPreferredRootElement(IConfigurationElement extension) {
        return extension.getAttribute(PREFERRED_ROOT_ELEMENT_ATTRIBUTE);
    }
}
