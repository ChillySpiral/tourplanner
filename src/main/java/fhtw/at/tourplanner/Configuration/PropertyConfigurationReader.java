package fhtw.at.tourplanner.Configuration;

import lombok.extern.log4j.Log4j2;

import java.util.Properties;

@Log4j2
public class PropertyConfigurationReader implements AppConfigurationReader {

    private final Properties appProps = new Properties();
    private boolean initialized = false;

    @Override
    public AppConfiguration getAppConfiguration() {
        if (!initialized) {
            try {
                appProps.load(Thread.currentThread()
                        .getContextClassLoader()
                        .getResourceAsStream("app.properties"));
            } catch (Exception e) {
                log.fatal("Could not get resource as stream. [name:app.properties] [ error: " + e + " ]");
                System.exit(1);
            }
            initialized = true;
        }

        return AppConfiguration.fromProperties(appProps);
    }

}