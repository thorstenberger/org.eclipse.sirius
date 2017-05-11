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
package org.eclipse.sirius.common.tools.internal.ecore;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.sirius.common.tools.api.ecore.EPackageMetaData;
import org.eclipse.sirius.common.tools.api.util.StringUtil;

/**
 * Handle contributions to /org.eclipse.sirius.common/schema/package_extra_data.exsd.
 * 
 * @author <a href="mailto:axel.richard@obeo.fr">Axel Richard</a>
 * @author pcdavid
 */
public class EPackageMetaDataRegistry {

    /** Extension-point ID. */
    private static final String EPACKAGE_META_DATA_EXTENSION_POINT = "org.eclipse.sirius.common.package_meta_data"; //$NON-NLS-1$

    private static final String EPACKAGE_META_DATA_ELEMENT = "ePackageMetaData"; //$NON-NLS-1$

    /** nsURI attribute. */
    private static final String NS_URI_ATTRIBUTE = "nsURI"; //$NON-NLS-1$

    /** Display name attribute. */
    private static final String DISPLAY_NAME_ATTRIBUTE = "displayName"; //$NON-NLS-1$

    /** Documentation attribute. */
    private static final String DOCUMENTATION_ATTRIBUTE = "documentation"; //$NON-NLS-1$

    /** Preferred root element attribute. */
    private static final String SUGGESTED_ROOT_ELEMENT = "suggestedRoot"; //$NON-NLS-1$

    private static final String SUGGESTED_ROOT_CLASS_NAME_ATTRIBUTE = "className"; //$NON-NLS-1$

    private final IExtensionRegistry extensionRegistry;

    private Map<String, EPackageMetaData> packageExtraData = new HashMap<>();

    private IRegistryEventListener listener = new EPackageExtraDataListener();

    /**
     * Creates a new EPackageExtraDataRegistry reading from the specified registry.
     * 
     * @param registry
     *            the registry to read configuration from.
     */
    public EPackageMetaDataRegistry(IExtensionRegistry registry) {
        this.extensionRegistry = registry;
    }

    /**
     * Initialize the regsitry and start listening for dynamic changes.
     */
    public synchronized void start() {
        for (IConfigurationElement cfg : extensionRegistry.getConfigurationElementsFor(EPACKAGE_META_DATA_EXTENSION_POINT)) {
            EPackageMetaData data = parse(cfg);
            if (data != null) {
                packageExtraData.put(data.getNsURI(), data);
            }
        }
        this.extensionRegistry.addListener(listener, EPACKAGE_META_DATA_EXTENSION_POINT);
    }

    /**
     * Clears the registry and stop listening for dynamic changes.
     */
    public synchronized void stop() {
        this.extensionRegistry.removeListener(listener);
        packageExtraData.clear();
    }

    /**
     * Returns the extra data associated to the specified nsURI, if any.
     * 
     * @param nsURI
     *            the nsURI of the EPackage to look for.
     * @return the associated extra data, may be <code>null</code>.
     */
    public synchronized EPackageMetaData getExtraData(String nsURI) {
        return packageExtraData.get(nsURI);
    }

    private EPackageMetaData parse(IConfigurationElement cfg) {
        if (EPACKAGE_META_DATA_ELEMENT.equals(cfg.getName())) {
            EPackageMetaData result = new EPackageMetaData(cfg.getAttribute(NS_URI_ATTRIBUTE));
            result.setDisplayName(cfg.getAttribute(DISPLAY_NAME_ATTRIBUTE));
            result.setDocumentation(cfg.getAttribute(DOCUMENTATION_ATTRIBUTE));
            for (IConfigurationElement child : cfg.getChildren(SUGGESTED_ROOT_ELEMENT)) {
                String className = child.getAttribute(SUGGESTED_ROOT_CLASS_NAME_ATTRIBUTE);
                if (!StringUtil.isEmpty(className)) {
                    result.getSuggestedRoots().add(className.trim());
                }
            }
            return result;
        } else {
            return null;
        }
    }

    private final class EPackageExtraDataListener implements IRegistryEventListener {
        @Override
        public void added(IExtension[] extensions) {
            synchronized (EPackageMetaDataRegistry.this) {
                for (IExtension ext : extensions) {
                    for (IConfigurationElement cfg : ext.getConfigurationElements()) {
                        EPackageMetaData data = parse(cfg);
                        if (data != null) {
                            packageExtraData.put(data.getNsURI(), data);
                        }
                    }
                }
            }
        }

        @Override
        public void removed(IExtension[] extensions) {
            synchronized (EPackageMetaDataRegistry.this) {
                for (IExtension ext : extensions) {
                    for (IConfigurationElement cfg : ext.getConfigurationElements()) {
                        if (EPACKAGE_META_DATA_ELEMENT.equals(cfg.getName())) {
                            String nsURI = cfg.getAttribute(NS_URI_ATTRIBUTE);
                            if (!StringUtil.isEmpty(nsURI)) {
                                packageExtraData.remove(nsURI);
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void removed(IExtensionPoint[] extensionPoints) {
            // no need to listen to this event
        }

        @Override
        public void added(IExtensionPoint[] extensionPoints) {
            // no need to listen to this event
        }
    }

}
