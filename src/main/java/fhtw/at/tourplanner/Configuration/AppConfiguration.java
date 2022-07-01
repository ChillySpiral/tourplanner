package fhtw.at.tourplanner.Configuration;

import lombok.Builder;
import lombok.Data;

import java.util.Properties;

@Data
@Builder
public class AppConfiguration {
    private String datasourceUrl;
    private String datasourceUsername;
    private String datasourcePassword;
    private String imageFolder;
    private String apiKey;

    public static AppConfiguration fromProperties(Properties appProps) {
        return AppConfiguration.builder()
                .imageFolder(appProps.getProperty("folder.image"))
                .datasourceUrl(appProps.getProperty("datasource.url"))
                .datasourceUsername(appProps.getProperty("datasource.username"))
                .datasourcePassword(appProps.getProperty("datasource.password"))
                .apiKey(appProps.getProperty("api.key"))
                .build();
    }
}
