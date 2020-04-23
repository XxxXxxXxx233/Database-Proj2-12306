import java.util.Properties;
import java.io.IOException;

public class PropertiesUtils {

    static Properties prop = new Properties();

    public static boolean loadFile(String name) {
        try {
            prop.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(name));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String getPropertyValue(String key) {
        return prop.getProperty(key);
    }

}
