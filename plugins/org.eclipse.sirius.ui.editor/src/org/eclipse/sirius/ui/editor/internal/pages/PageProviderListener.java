/*******************************************************************************
 * Copyright (c) 2017 Obeo
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ui.editor.internal.pages;

import org.eclipse.sirius.ui.editor.api.pages.PageProvider;
import org.eclipse.sirius.ui.editor.api.pages.PageProviderRegistry;

/**
 * The components implementing this interface will be notified when
 * {@link PageProviderRegistry} is added or removed a {@link PageProvider}.
 * 
 * @author <a href="mailto:pierre.guilet@obeo.fr">Pierre Guilet</a>
 *
 */
public interface PageProviderListener {
    /**
     * Invoked after that a {@link PageProvider} has been added or removed from
     * {@link PageProviderRegistry}.
     */
    void pageProviderChanged();
}
