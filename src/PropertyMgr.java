import java.io.IOException;
import java.util.Properties;


public class PropertyMgr {
	private static Properties pro = new Properties();
	static{
		try {
			pro.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static String getProperty(String key){
		return pro.getProperty(key);
	}
}
