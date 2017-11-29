package xyz.doran.elian.labplan.junit;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import xyz.doran.elian.labplan.persistence.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DatabaseConnectionFactoryTests {
	static String configFilePath = "db.properties";
	
	static String dummyAddress = "jdbc:mysql://localhost:3306/labplan";
	static String dummyUserName = "root";
	static String dummyPassword = "Development.";
	static String dummyDatabase = "database";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testConnection() throws Exception {
		DatabaseConnectionFactory.setConfigFilePath(configFilePath);
		DatabaseConnectionFactory.load();
		
		Connection conn = DatabaseConnectionFactory.getConnection();
		
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM `patients`");
	}
}
