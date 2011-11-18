/*   Copyright 2011 Appbox inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.appbox.monitoring.jmx.jmxbox.commands;

import javax.management.AttributeNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeDataSupport;

import ca.appbox.monitoring.jmx.jmxbox.commons.JmxException;

/**
 * A mxBean read attribute command.
 * 
 */
public class JmxReadAttributeCommandImpl extends AbstractJmxCommand {

	private static final String COMPOSITE_ATTRIBUTE_DELIMITER = ".";
	private String attribute;
	
	public JmxReadAttributeCommandImpl(String jmxBean, String attribute) {
		super(jmxBean);
		this.attribute = attribute;
	}

	@Override
	public boolean hasOutput() {
		return true;
	}

	@Override
	public JmxCommandResult actualInvoke(ObjectName mBean, MBeanServerConnection mBeanServerConnection) throws JmxException {
		
		Object value = null;
		
		try {
			
			if (isCompositeAttribute()) {
				String[] compositeAttribute = attribute.split("\\" + COMPOSITE_ATTRIBUTE_DELIMITER);
				Object rawAttributeValue = mBeanServerConnection.getAttribute(mBean, compositeAttribute[0]);
				
				if (rawAttributeValue instanceof CompositeDataSupport) {
					value = ((CompositeDataSupport)rawAttributeValue).get(compositeAttribute[1]);
					
					if (value == null) {
						throw new AttributeNotFoundException();
					}
				} else {
					throw new JmxException("The following attribute is not a composite attribute : " + attribute);
				}
				
			} else {
				value = mBeanServerConnection.getAttribute(mBean, attribute);
			}
			
			
		} catch (AttributeNotFoundException e) {
			throw new JmxException("Attribute (" + attribute + ") not found on " + mBean);
		} catch (Exception e) {
			throw new JmxException("Problem reading attribute (" + attribute + ") not found on " + mBean, e);
		} 
		
		return new JmxCommandResultImpl(value.toString(), this);
	}
	
	private boolean isCompositeAttribute() {
		return attribute.contains(COMPOSITE_ATTRIBUTE_DELIMITER);
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("mBean : ");
		sb.append(super.getJmxBean());
		sb.append(", ");
		sb.append("attribute : ");
		sb.append(attribute);
		
		return sb.toString();
	}
}
