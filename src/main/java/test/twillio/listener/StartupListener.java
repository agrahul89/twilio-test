package test.twillio.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class StartupListener implements ServletContextListener {
	private static final Logger LOGGER = LogManager
			.getLogger(StartupListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent inServletEvent) {
		LOGGER.info("TwiTest Shutdown Initialized");
		LOGGER.info("~~~~~~~~~~~~~~~ TwiTest Shutdown Completed ~~~~~~~~~~~~~~~");
	}

	@Override
	public void contextInitialized(ServletContextEvent inServletEvent) {
		final ServletContext servletContext = inServletEvent
				.getServletContext();
		System.out.println("Initializing Logger");
		initializeLogger(servletContext);
		LOGGER.info("!!! twiliotest Initialization Completed Successfully !!!");
	}

	private void initializeLogger(final ServletContext inServletContext) {
		final StringBuilder builder = new StringBuilder();
		builder.append(
				inServletContext.getInitParameter("log4j-config-file-path"))
				.append(inServletContext
						.getInitParameter("log4j-config-file-name"));
		String log4jConf = builder.toString();
		System.out.println("logger path :: " + log4jConf);
		DOMConfigurator.configure(log4jConf);
		LOGGER.info("~~~~~~~~~~~~~~~ Initializing twiliotest ~~~~~~~~~~~~~~~");
		LOGGER.info("Initialization of Log4j Completed FOR twiliotest!");
	}
}
