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
package ca.appbox.monitoring.jmx.jmxbox;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import ca.appbox.monitoring.jmx.jmxbox.commands.JmxCommand;
import ca.appbox.monitoring.jmx.jmxbox.commands.JmxCommandResult;
import ca.appbox.monitoring.jmx.jmxbox.commons.JmxException;
import ca.appbox.monitoring.jmx.jmxbox.commons.SingletonHolder;
import ca.appbox.monitoring.jmx.jmxbox.commons.context.JmxContext;
import ca.appbox.monitoring.jmx.jmxbox.output.OutputStrategy;

/**
 * Invokes all the commands in the context and output the results in the output file
 * or to the standard output accordingly.
 * 
 */
public class JmxClientImpl implements JmxClient {

	private static final JmxClientImpl instance = new JmxClientImpl();

	private JmxClientImpl() {
		super();
	}

	public static JmxClientImpl getInstance() {
		return instance;
	}

	public void run(JmxContext context) throws JmxException {

		OutputStrategy outputStrategy = SingletonHolder.getOutputStragey(context);
		
		try {

			MBeanServerConnection mBeanServerConnection = createJmxConnection(context);

			for (Integer i = 0; i < context.getRepetitions(); i++) {

				List<JmxCommandResult> currentResults = new ArrayList<JmxCommandResult>();
				
				for (JmxCommand command : context.getCommands()) {

					JmxCommandResult result = command
							.invoke(mBeanServerConnection);

					currentResults.add(result);
					
				}
				
				outputStrategy.writeOutput(context, currentResults);
				
				Thread.sleep(context.getIntervalInMiliseconds());
			}

		} catch (Exception e) {
			throw new JmxException("Problem while invoking commands." + e);
		} 
	}

	private MBeanServerConnection createJmxConnection(JmxContext context)
			throws MalformedURLException, IOException {
		JMXServiceURL target;

		Map<String, Object> env = new HashMap<String, Object>();

		if (context.hasCredentials()) {
			String[] credentials = new String[]{context.getUser(), context.getPassword()};
			env.put(JMXConnector.CREDENTIALS, credentials);
		}

		target = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"
				+ context.getHost() + ":" + context.getPort() + "/jmxrmi");
		JMXConnector connector = JMXConnectorFactory.connect(target,env);
		MBeanServerConnection mBeanServerConnection = connector
				.getMBeanServerConnection();
		return mBeanServerConnection;
	}
}
