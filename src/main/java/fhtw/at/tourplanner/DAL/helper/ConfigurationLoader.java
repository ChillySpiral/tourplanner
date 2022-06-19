package fhtw.at.tourplanner.DAL.helper;

import java.io.IOException;
import java.util.Properties;

public class ConfigurationLoader {

    private static Properties loadedProperties;
    public static String getConfig(String key){
        if(key == null || key.isEmpty())
            return null;

        if(loadedProperties == null)
            loadedProperties = new Properties();

        String result = null;
        try {

            loadedProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("app.config"));
            result = loadedProperties.getProperty(key);

        } catch (IOException e){
            e.printStackTrace();
        }
        return result;
    }
}
