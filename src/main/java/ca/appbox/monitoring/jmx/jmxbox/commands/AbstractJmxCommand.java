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

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

import ca.appbox.monitoring.jmx.jmxbox.commons.JmxException;

/**
 * Base class for all jmx commands.
 * 
 */
public abstract class AbstractJmxCommand implements JmxCommand {

	private String jmxBean;

	public AbstractJmxCommand(String jmxBean) {
		super();
		this.jmxBean = jmxBean;
	}

	public String getJmxBean() {
		return jmxBean;
	}

	public abstract boolean hasOutput();

	public JmxCommandResult invoke(MBeanServerConnection mBeanServerConnection)
			throws JmxException {

		if (mBeanServerConnection == null) {
			throw new IllegalArgumentException("mBeanServer cannot be null");
		}

		ObjectName objectName = null;

		try {
			objectName = new ObjectName(jmxBean);
		} catch (Exception e) {
			throw new JmxException("Invalid mBean name : " + jmxBean);
		}

		try {
			if (!mBeanServerConnection.isRegistered(objectName)) {
				throw new JmxException("No such mBean : " + objectName);
			}
		} catch (Exception e) {
			throw new JmxException("No such mBean : " + objectName);
		}

		JmxCommandResult commandResult = actualInvoke(objectName,
				mBeanServerConnection);

		return commandResult;
	}

	protected abstract JmxCommandResult actualInvoke(ObjectName mBean,
			MBeanServerConnection mBeanServerConnection) throws JmxException;
}
