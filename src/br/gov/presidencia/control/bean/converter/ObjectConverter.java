/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the &quot;License&quot;);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.gov.presidencia.control.bean.converter;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.FacesConverter;

import br.gov.presidencia.util.Component;

/**
 * A basic converter that works with any kind of object. NOTE: It should only be
 * used in long running conversations!
 * 
 * @author <a href="http://community.jboss.org/people/LightGuard">Jason
 *         Porter</a>a>
 */
@RequestScoped
@FacesConverter("objectConverter")
public class ObjectConverter implements javax.faces.convert.Converter, Serializable {

	private static final long serialVersionUID = 1L;

	private ObjectConverterViewAux aux = (ObjectConverterViewAux) Component.getInstance(ObjectConverterViewAux.class);

	// private final transient Logger log =
	// Logger.getLogger(ObjectConverter.class);

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		String clientId = component.getClientId(context);
		return aux.getConverterMap(clientId).get(value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String clientId = component.getClientId(context);
		
		Map<String, Object> converterMap = aux.getConverterMap(clientId);
		Map<Object, String> reverseConverterMap = aux.getReverseConverterMap(clientId);
		
		if (reverseConverterMap.containsKey(value)) {
			return reverseConverterMap.get(value);
		} else {
			final String incrementorStringValue = String.valueOf(aux.increment(clientId));
			converterMap.put(incrementorStringValue, value);
			reverseConverterMap.put(value, incrementorStringValue);
			return incrementorStringValue;
		}
	}
	
}
