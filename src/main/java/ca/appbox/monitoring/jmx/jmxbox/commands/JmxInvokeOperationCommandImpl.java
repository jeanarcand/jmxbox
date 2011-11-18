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

import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import ca.appbox.monitoring.jmx.jmxbox.commons.JmxException;

/**
 * A mxBean operation invocation.
 * 
 */
public class JmxInvokeOperationCommandImpl extends AbstractJmxCommand {

	private final List<String> parameters;
	
	private final String operation;
	
	public JmxInvokeOperationCommandImpl(String jmxBean, String operation) {
		super(jmxBean);
		parameters = new ArrayList<String>();
		this.operation = operation;
	}

	@Override
	public boolean hasOutput() {
		return false;
	}

	@Override
	public JmxCommandResult actualInvoke(ObjectName mBean, MBeanServerConnection mBeanServerConnection) throws JmxException {
		
		try {
			mBeanServerConnection.invoke(mBean, operation, parameters.toArray(), null);
		} catch (Exception e) {
			throw new JmxException("Problem invoking operation (" + operation +") on mBean : " + mBean, e);
		} 
		
		return new JmxCommandResultImpl(null, this);
	}
	
	public void addParameter(final String parameter) {
		this.parameters.add(parameter);
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("mBean : ");
		sb.append(super.getJmxBean());
		sb.append(", ");
		sb.append("operation : ");
		sb.append(operation);
		sb.append(", ");
		sb.append("parameters : ");
		
		for (String parameter : parameters) {
			sb.append(parameter);
			sb.append(",");
		}
		
		return sb.toString();
	}
}
