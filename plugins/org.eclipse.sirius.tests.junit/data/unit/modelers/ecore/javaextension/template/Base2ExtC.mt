<%
metamodel http://www.eclipse.org/emf/2002/Ecore

import org.eclipse.sirius.service.Base2ExtC
%>

<%script type="ecore.EClass" name="ecoreTemplate" file="test.txt"%>

<%script type="ecore.EClass" name="isDocumentedTemplate" post="trim()"%>
<%isDocumented()%>