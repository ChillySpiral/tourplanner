package fhtw.at.tourplanner.Configuration;

import java.util.Properties;

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
                System.exit(1);
            }
            initialized = true;
        }

        return AppConfiguration.fromProperties(appProps);
    }

}