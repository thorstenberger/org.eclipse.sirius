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

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author <a href="mailto:axel.richard@obeo.fr">Axel Richard</a>
 */
public class SiriusUIWizards extends AbstractUIPlugin {

    /** The plug-in ID. */
    public static final String PLUGIN_ID = "org.eclipse.sirius.ui.wizards"; //$NON-NLS-1$

    /** The shared instance. */
    private static SiriusUIWizards plugin;

    /** The registry of EPackages extra data. */
    private EPackageExtraDataRegistry extraDataRegistry;

    /**
     * The constructor.
     */
    public SiriusUIWizards() {
        this.extraDataRegistry = new EPackageExtraDataRegistry();
    }

    /**
     * Start bundle.
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework. BundleContext)
     * @param context
     *            the BundleContext.
     * @throws Exception
     *             for any reason.
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /**
     * Stop bundle.
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework. BundleContext)
     * @param context
     *            the BundleContext.
     * @throws Exception
     *             for any reason.
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance.
     *
     * @return the shared instance
     */
    public static SiriusUIWizards getDefault() {
        return plugin;
    }

    /**
     * Get the registry of EPackages extra data.
     * 
     * @return the registry of EPackages extra data.
     */
    public EPackageExtraDataRegistry getExtraDataRegistry() {
        return this.extraDataRegistry;
    }
}
