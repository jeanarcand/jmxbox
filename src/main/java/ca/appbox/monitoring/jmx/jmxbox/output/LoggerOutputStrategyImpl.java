package ca.appbox.monitoring.jmx.jmxbox.output;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.appbox.monitoring.jmx.jmxbox.commands.JmxCommandResult;
import ca.appbox.monitoring.jmx.jmxbox.commons.JmxException;
import ca.appbox.monitoring.jmx.jmxbox.commons.context.JmxContext;

public class LoggerOutputStrategyImpl implements OutputStrategy {

	private static final Logger logger = LoggerFactory.getLogger(LoggerOutputStrategyImpl.class);
	
	private static final OutputStrategy instance = new LoggerOutputStrategyImpl();

	private LoggerOutputStrategyImpl() {
		super();
	}

	public static OutputStrategy getInstance() {
		return instance;
	}
	
	public void open(JmxContext context) throws JmxException {
		//  NOOP
	}

	public void writeOutput(JmxContext context, List<JmxCommandResult> results)
			throws JmxException {
		
		for (JmxCommandResult result : results) {
			
			StringBuilder sb = new StringBuilder();
			sb.append("Invoked ");
			sb.append(result.getJmxCommand());
			
			if (result.getJmxCommand().hasOutput()) {
				sb.append(", Result : ");
				sb.append(result.getResult());
			}
			
			logger.info(sb.toString());
		}
	}

	public void close(JmxContext context) throws JmxException {
		// NOOP
	}
}
