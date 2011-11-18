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
package ca.appbox.monitoring.jmx.jmxbox.commons.context.parser;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.Option;

import ca.appbox.monitoring.jmx.jmxbox.commons.CommandLineOptions;

class CommandLineOptionsComparator implements Comparator<Option> {

	private static Map<String, Integer> displayOrder = new HashMap<String, Integer>();
	
	public int compare(Option option1, Option option2) {
		return displayOrder.get(option1.getOpt()).compareTo(displayOrder.get(option2.getOpt()));
	}
	
	static {
		displayOrder.put(CommandLineOptions.HELP, Integer.valueOf(0));
		displayOrder.put(CommandLineOptions.HOST, Integer.valueOf(10));
		displayOrder.put(CommandLineOptions.PORT, Integer.valueOf(20));
		displayOrder.put(CommandLineOptions.USER, Integer.valueOf(30));
		displayOrder.put(CommandLineOptions.PASSWORD, Integer.valueOf(40));
		displayOrder.put(CommandLineOptions.INVOKE, Integer.valueOf(50));
		displayOrder.put(CommandLineOptions.READ_ATTRIBUTE, Integer.valueOf(60));
		displayOrder.put(CommandLineOptions.OUTPUT_FILE, Integer.valueOf(70));
		displayOrder.put(CommandLineOptions.INTERVAL_IN_SECONDS, Integer.valueOf(80));
		displayOrder.put(CommandLineOptions.REPETITIONS, Integer.valueOf(90));
		displayOrder.put(CommandLineOptions.RECORD_DELIMITER, Integer.valueOf(100));
	}
}
