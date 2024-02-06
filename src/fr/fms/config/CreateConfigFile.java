package fr.fms.config;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class CreateConfigFile {
	public static void main(String[] args) {
		try (OutputStream ops = new FileOutputStream("files/config.properties")) {		
			Properties properties = new Properties();
			
			properties.setProperty("db.driver", "org.mariadb.jdbc.Driver");
			properties.setProperty("db.url", "jdbc:mariadb://localhost:3306/Shop");
			properties.setProperty("db.login", "root");
			properties.setProperty("db.password", "ArthurGibertTosse40230");			
			properties.store(ops , "Fichier de Configuration");
			
			System.out.println("Fichier de configuration créer avec succès !");
		}
		 catch (IOException io) {
	            io.printStackTrace();
		}
	}
}
