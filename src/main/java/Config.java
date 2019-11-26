import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.Properties;

public class Config
{
    Properties configFile;
    public Config()
    {
        configFile = new java.util.Properties();
        try {
            File jarPath=new File(Config.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath=jarPath.getParentFile().getAbsolutePath();
            System.out.println(" propertiesPath-"+propertiesPath);
            configFile.load(new FileInputStream(propertiesPath+"/config.cfg"));
        }catch(Exception eta){
            eta.printStackTrace();
        }
    }

    public String getProperty(String key)
    {
        String value = this.configFile.getProperty(key);
        return value;
    }
}