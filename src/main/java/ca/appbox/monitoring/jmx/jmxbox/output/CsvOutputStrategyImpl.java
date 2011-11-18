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
package ca.appbox.monitoring.jmx.jmxbox.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import ca.appbox.monitoring.jmx.jmxbox.commands.JmxCommandResult;
import ca.appbox.monitoring.jmx.jmxbox.commons.JmxException;
import ca.appbox.monitoring.jmx.jmxbox.commons.context.JmxContext;

/**
 * One and only supported output format for now.
 * 
 */
public class CsvOutputStrategyImpl implements OutputStrategy {

	private static final OutputStrategy instance = new CsvOutputStrategyImpl();

	private FileWriter fstream;
	
	private BufferedWriter out;

	private boolean open;
	
	private CsvOutputStrategyImpl() {
		super();
		open = false;
	}

	public static OutputStrategy getInstance() {
		return instance;
	}

	
	public void writeOutput(JmxContext context, List<JmxCommandResult> results)
			throws JmxException {

		if (!open) {
			open(context.getOutputFile());
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(new Date());
		
		for (JmxCommandResult currentResult : results) {
			sb.append(context.getRecordDelimiter());
			sb.append(currentResult.getResult());
		}
		
		sb.append(System.getProperty("line.separator"));
		
		try {
			out.write(sb.toString());
			out.flush();
		} catch (IOException e) {
			throw new JmxException("Problem writing to file.", e);
		}
	}

	private void open(File outputFile) throws JmxException {

		open = true;
		
		try {
			
			fstream = new FileWriter(outputFile, true);
		
		} catch (IOException exception) {
			throw new JmxException("Unable to open the output strategy.", exception);
		}
		
		out = new BufferedWriter(fstream);
	}
}
