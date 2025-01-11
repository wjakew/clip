/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.jakubwawak.clip.database.Database;
import com.jakubwawak.clip.maintanance.Properties;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.spring.annotation.EnableVaadin;
import com.vaadin.flow.theme.Theme;

/**
 * Web application for storing and sharing clips
 */
@SpringBootApplication
@EnableVaadin({"com.jakubwawak"})
@Theme(value = "maintheme")
public class ClipApplication extends SpringBootServletInitializer implements AppShellConfigurator{

	public static String version = "0.0.1";
	public static String build = "cla11012025REV01";

	public static Properties properties;
	public static Database database;

	/**
	 * Main function for running the application
	 * @param args
	 */
	public static void main(String[] args) {
		showHeader();
		properties = new Properties("clip.properties");
		if ( properties.fileExists ){
			properties.parsePropertiesFile();
			database = new Database(properties.getValue("databaseIP"), properties.getValue("databaseUser"), properties.getValue("databasePassword"));
			try{
				database.connect();
				if( database.connected ){
					SpringApplication.run(ClipApplication.class, args);
				}
				else{
					database.log("Failed to connect to database", "DB-CONNECTION");
				}
			}catch(Exception ex){
				database.log("Failed to connect to database (" + ex.toString() + ")", "DB-CONNECTION");
			}
		}
		else{
			System.out.println("Properties file not found");
			properties.createPropertiesFile();
			System.out.println("Properties file created");
		}
	}

	/**
	 * Function for showing the header
	 */
	static void showHeader(){
		String header = "	  _ _\n" + //
						"  ___| (_)_ __\n" + //
						" / __| | | '_ \\\n" + //
						"| (__| | | |_) |\n" + //
						" \\___|_|_| .__/\n" + //
						"         |_|";

		header = header + version + " " + build;
		System.out.println(header);
	}

	/**
	 * Function for showing a notification
	 * @param message
	 */
	public static void showNotification(String message){
		Notification noti = Notification.show(message);
		noti.addClassName("notification");
		noti.setPosition(Notification.Position.BOTTOM_END);
	}
}
