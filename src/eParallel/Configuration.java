package eParallel;

import java.io.IOException;
import java.util.Properties;
import java.io.InputStream;

/**
 *
 * @author v3rgu1 - maurmorr
 */
public class Configuration {

	Properties properties = null;
	private static Configuration instance = null;

	public final static String CONFIG_FILE_NAME = "Configuration.properties";
	public final static String DATABASE_SERVER = "dataBaseServer";
	public final static String DATABASE_CATALOG = "dataBaseCatalog";
	public final static String DATABASE_USER = "dataBaseUser";
	public final static String DATABASE_PSWD = "dataBasePassword";
	public final static String DIR_WORK = "dir_work";
	public final static String DIR_INPUT = "dir_input";
	public final static String DIR_OUTPUT = "dir_output";
	public final static String APLIACION = "aplicacion";

	InputStream input = null;

	private Configuration() {
		this.properties = new Properties();

		try {

			String filename = CONFIG_FILE_NAME;
			input = Configuration.class.getClassLoader().getResourceAsStream("Configuration.properties");
			if (input == null) {
				String workingDir = System.getProperty("user.dir");
				System.out.println("Current working directory : " + workingDir);
				System.out.println("Sorry, unable to find " + filename);
				return;
			}
			properties.load(Configuration.class.getClassLoader().getResourceAsStream(CONFIG_FILE_NAME));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}// Configuration

	/**
	 * Implementando Singleton
	 *
	 * @return x public static Configuration getInstance() { return
	 *         ConfigurationHolder.INSTANCE; } private static class
	 *         ConfigurationHolder { private static final Configuration INSTANCE
	 *         = new Configuration(); }
	 * 
	 *         /** Retorna la propiedad de configuración solicitada
	 *
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}// getProperty

	public static synchronized Configuration getInstance() {
		if (instance == null)
			instance = new Configuration();
		return instance;
	}

	public String getValue(String propKey) {
		return this.properties.getProperty(propKey);
	}

}