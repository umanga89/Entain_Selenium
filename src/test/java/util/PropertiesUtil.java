package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    static Properties configProperties = new Properties();
    public static Properties loadProperties(String url) throws IOException {
        try {
            File configPropertiesFile = new File(url);
            FileInputStream inputStream = new FileInputStream(configPropertiesFile);
            configProperties.load(inputStream);

        }catch (IOException e) {
            e.printStackTrace();
        }
        return configProperties;
    }
}
