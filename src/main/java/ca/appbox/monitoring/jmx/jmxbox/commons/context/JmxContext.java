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
package ca.appbox.monitoring.jmx.jmxbox.commons.context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ca.appbox.monitoring.jmx.jmxbox.commands.JmxCommand;

/**
 * Context passed through the whole app.
 * 
 */
public class JmxContext {

	private static final int ALMOST_INFINITE_REPETITIONS = 0;
	private static final String DEFAULT_RECORD_DELIMITER = ";";
	private static final Integer DEFAULT_INTERVAL = 1;
	private static final Integer DEFAULT_REPETITIONS = 1;
	
	private final String host;
	private final Integer port;
	private final String user;
	private final String password;
	private final Integer intervalInSeconds;
	private final Integer repetitions;
	private final File outputFile;
	private final String recordDelimiter;
	private final List<JmxCommand> commands;

	public JmxContext(String host, Integer port, String user,
			String password, Integer intervalInSeconds, File outputFile,
			String recordDelimiter, Integer repetitions) {
		super();
		
		this.host = host;
		this.port = port;
		this.user = user;
		this.password = password;
		
		commands = new ArrayList<JmxCommand>();
		
		if (intervalInSeconds == null) {
			this.intervalInSeconds = DEFAULT_INTERVAL;
		} else {
			this.intervalInSeconds = intervalInSeconds;
		}
		
		if (recordDelimiter == null) {
			this.recordDelimiter = DEFAULT_RECORD_DELIMITER;
		} else {
			this.recordDelimiter = recordDelimiter;
		}
		
		this.outputFile = outputFile;
		
		if (repetitions == null) {
			this.repetitions = DEFAULT_REPETITIONS;
		} else if (repetitions == 0) {
			//for ever, almost
			this.repetitions = Integer.MAX_VALUE;
		}
		else {
			this.repetitions = repetitions;
		}
	}

	public String getHost() {
		return host;
	}

	public Integer getPort() {
		return port;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public Integer getIntervalInSeconds() {
		return intervalInSeconds;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public String getRecordDelimiter() {
		return recordDelimiter;
	}

	public void addCommand(final JmxCommand command) {
		this.commands.add(command);
	}
	
	public Integer getRepetitions() {
		return repetitions == Integer.valueOf(ALMOST_INFINITE_REPETITIONS) ? Integer.MAX_VALUE : repetitions;
	}

	public List<JmxCommand> getCommands() {
		return commands;
	}
	
	public boolean hasCredentials() {
		return ((this.user != null && !this.user.isEmpty()) && (this.password != null && !this.password.isEmpty()));
	}
	
	public boolean hasOutputFile() {
		return (this.outputFile != null);
	}
}
