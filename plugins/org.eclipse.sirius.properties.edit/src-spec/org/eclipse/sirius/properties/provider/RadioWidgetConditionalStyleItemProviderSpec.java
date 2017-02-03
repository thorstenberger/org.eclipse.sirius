/**
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *
 */
package org.eclipse.sirius.properties.provider;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.sirius.properties.RadioWidgetConditionalStyle;

/**
 * Subclass used to not have to modify the generated code.
 *
 * @author sbegaudeau
 */
public class RadioWidgetConditionalStyleItemProviderSpec extends RadioWidgetConditionalStyleItemProvider {

    /**
     * The constructor.
     *
     * @param adapterFactory
     *            The adapter factory
     */
    public RadioWidgetConditionalStyleItemProviderSpec(AdapterFactory adapterFactory) {
        super(adapterFactory);
    }

    @Override
    public String getText(Object object) {
        String label = ((RadioWidgetConditionalStyle) object).getPreconditionExpression();
        return label == null || label.length() == 0 ? getString("_UI_RadioWidgetConditionalStyle_type") : //$NON-NLS-1$
                label;
    }

}
