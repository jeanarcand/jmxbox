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
package ca.appbox.monitoring.jmx.jmxbox.commons;

import ca.appbox.monitoring.jmx.jmxbox.JmxClient;
import ca.appbox.monitoring.jmx.jmxbox.JmxClientImpl;
import ca.appbox.monitoring.jmx.jmxbox.commons.context.JmxContext;
import ca.appbox.monitoring.jmx.jmxbox.commons.context.parser.JmxContextParserImpl;
import ca.appbox.monitoring.jmx.jmxbox.output.CsvOutputStrategyImpl;
import ca.appbox.monitoring.jmx.jmxbox.output.LoggerOutputStrategyImpl;
import ca.appbox.monitoring.jmx.jmxbox.output.OutputStrategy;

/**
 * Ugly dependency management is done here.
 * 
 */
public class SingletonHolder {

	public static JmxClient getJmxClient() {
		return JmxClientImpl.getInstance();
	}
	
	public static JmxContextParserImpl getJmxContextParser() {
		return JmxContextParserImpl.getInstance();
	}
	
	public static OutputStrategy getOutputStragey(JmxContext context) {
		
		OutputStrategy outputStrategy;
		
		if (context.hasOutputFile()) {
			outputStrategy = CsvOutputStrategyImpl.getInstance();
		} else {
			outputStrategy = LoggerOutputStrategyImpl.getInstance();
		}
		
		return outputStrategy;
	}
}
