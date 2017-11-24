/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.ui.tools.api.services;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.business.api.session.Session;
import org.eclipse.sirius.common.tools.api.interpreter.IInterpreter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

/**
 * Provides generic services that can be invoked from any expression inside a VSM to help debug modeler definitions.
 * 
 * @author pcdavid
 */
public class DebugServices {

    private static final String EOL = "\n"; //$NON-NLS-1$

    /**
     * All services defined in this class are only effective if the following system property is set to
     * <code>true</code>. Otherwise they have no effect (except for any side effect of evaluating their arguments, if
     * any). This allows VSMs to be deployed with service invocations still present in their definitions, but
     * ineffective (until needed explicitly).
     */
    private static final String DEBUG_SERVICES_FLAG = "org.eclipse.sirius.ui.debugServicesEnabled"; //$NON-NLS-1$

    /**
     * Dumps context information to standard output.
     * 
     * @param self
     *            the current element.
     * @return self.
     */
    public EObject dumpContext(EObject self) {
        if (isEnabled()) {
            Session.of(self).ifPresent(session -> {
                StringBuilder sb = new StringBuilder();
                sb.append("Context report:\n"); //$NON-NLS-1$
                sb.append("- Session ID: ").append(session.getID()).append(EOL); //$NON-NLS-1$
                IInterpreter itp = session.getInterpreter();
                sb.append("- self: ").append(self).append(EOL); //$NON-NLS-1$
                itp.getVariables().keySet().stream().sorted().forEach((String var) -> {
                    sb.append("- ").append(var).append(": ").append(itp.getVariables().get(var)).append(EOL); //$NON-NLS-1$ //$NON-NLS-2$
                });
                print(sb.append(EOL).toString());
            });
        }
        return self;
    }

    /**
     * .
     * 
     * @param self self.
     * @return self.
     */
    public EObject inspect(EObject self) {
        if (isEnabled()) {
            Session.of(self).ifPresent(session -> {
                Display display = PlatformUI.getWorkbench().getDisplay();
            });
        }
        return self;
    }

    private boolean isEnabled() {
        return true;
        // return "true".equals(System.getProperty(DEBUG_SERVICES_FLAG, "false")); //$NON-NLS-1$ //$NON-NLS-2$
    }

    private void print(String s) {
        // CHECKSTYLE:OFF
        System.out.println(s);
        // CHECKSTYLE:OFF
    }

}
