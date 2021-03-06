/**
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *
 */
package org.eclipse.sirius.properties.ext.widgets.reference.propertiesextwidgetsreference.provider;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.StyledString;
import org.eclipse.sirius.properties.provider.Utils;

/**
 * This class has been created to modify the generated code.
 * 
 * @author sbegaudeau
 */
public class ExtReferenceWidgetStyleItemProviderSpec extends ExtReferenceWidgetStyleItemProvider {

    /**
     * The constructor.
     * 
     * @param adapterFactory
     *            Adapter factory
     */
    public ExtReferenceWidgetStyleItemProviderSpec(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    public String getText(Object object) {
        Object styledText = this.getStyledText(object);
        if (styledText instanceof StyledString) {
            return ((StyledString) styledText).getString();
        }
        return super.getText(object);
    }

    @Override
    public Object getStyledText(Object object) {
        return Utils.computeLabel(this, object, "_UI_ExtReferenceWidgetStyle_type"); //$NON-NLS-1$
    }
}
