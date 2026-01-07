package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {


//    static FileInputStream fis;
    static Properties prop;

    static
    {
        try {
            prop = new Properties();
//            fis = new FileInputStream();
            prop.load(ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties"));
//            System.out.println(ConfigReader.class.getClassLoader().getResourceAsStream("config.properties"));

        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file.");
        }
    }

    public static String getBaseUrl() {
        return prop.getProperty("baseUrl");
    }

    public static String getUsername() {
        return prop.getProperty("username");
    }

    public static String getPassword() {
        return prop.getProperty("password");
    }

    public static String getValue(String key) {
        return prop.getProperty(key);
    }


}
