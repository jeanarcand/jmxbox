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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.appbox.monitoring.jmx.jmxbox.commons.JmxException;
import ca.appbox.monitoring.jmx.jmxbox.commons.SingletonHolder;
import ca.appbox.monitoring.jmx.jmxbox.commons.context.JmxContext;

/**
 * App launcher.
 * 
 */
public class JmxClientApp {

	private static final Logger logger = LoggerFactory.getLogger(JmxClientApp.class);
	
	public static void main(String[] args) {

		JmxContext jmxContext = null;
		
		try {
			jmxContext = SingletonHolder.getJmxContextParser().parse(args);
		} catch (JmxException e) {
			System.exit(1);
		}
		
		try {
			SingletonHolder.getJmxClient().run(jmxContext);
		} catch (JmxException e) {
			logger.error(e.getMessage());
			System.exit(1);
		}		
	}
}
