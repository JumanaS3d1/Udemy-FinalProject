package Core_Udemy;

import static org.testng.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class UserCredentials {
	
	String username;
	String password; 

	public void GetCred() throws IOException {
		FileReader readFile=new FileReader("cred.properities");
		Properties prop= new Properties();
		prop.load(readFile);
		 this.username = prop.getProperty("username");
		 this.password = prop.getProperty("password");
	}
	
	public UserCredentials() throws IOException {
		GetCred();
	}
	
	public String getUserName() {
		return this.username;
	}
	public String getPass() {
		return this.password;
	}
}
