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

public class JmxCommandResultImpl implements JmxCommandResult {

	private final String result;

	private final JmxCommand jmxCommand;
	
	public JmxCommandResultImpl(String result, JmxCommand jmxCommand) {
		super();
		this.result = result;
		this.jmxCommand = jmxCommand;
	}

	public String getResult() {
		return result;
	}

	public JmxCommand getJmxCommand() {
		return jmxCommand;
	}
}
