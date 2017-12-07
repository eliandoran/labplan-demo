package com.labplan.persistence.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.labplan.entities.LabList;
import com.labplan.entities.Patient;
import com.labplan.entities.generic.LazyLoadedEntity;
import com.labplan.persistence.DatabaseConnectionFactory;
import com.mysql.cj.api.jdbc.Statement;

public class LabListDao implements com.labplan.persistence.generic.GenericLabListDao {
	private static final Logger LOGGER = Logger.getGlobal();
	
	@Override
	public LabList read(Integer key) {
		Connection conn = DatabaseConnectionFactory.getConnection();
		String query = "SELECT * FROM `lab_lists` WHERE `list_id`=?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, key);
			ResultSet result = stmt.executeQuery();
			
			if (result.next()) {
				return parseLabList(result);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "SQL connection failed.", e);
		}
		
		return null;
	}

	@Override
	public Set<LabList> readAll() {
		Connection conn = DatabaseConnectionFactory.getConnection();
		String query = "SELECT * FROM `lab_lists`";
		Set<LabList> labLists = new HashSet<LabList>();
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			
			while (result.next()) {
				labLists.add(parseLabList(result));
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "SQL connection failed.", e);
			return null;
		}
		
		return labLists;
	}

	@Override
	public Integer create(LabList entity) {
		Connection conn = DatabaseConnectionFactory.getConnection();
		String query = "INSERT INTO `lab_lists` "
				+ "(`patient_id`, `creation_date`) "
				+ "VALUES (?, ?)";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet generatedKeys;
			
			stmt.setInt(1, entity.getPatient().getKey());
			stmt.setTimestamp(2, parseDate(entity.getCreationDate()));
			
			stmt.execute();
			generatedKeys = stmt.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				return generatedKeys.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "SQL connection failed.", e);
		}
		
		return null;
	}

	@Override
	public boolean update(LabList entity) {
		Connection conn = DatabaseConnectionFactory.getConnection();
		String query = "UPDATE `lab_lists` SET "
				+ "`patient_id`=?, "
				+ "`creation_date`=? "
				+ "WHERE `list_id`=?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			
			stmt.setInt(1, entity.getPatient().getKey());
			stmt.setTimestamp(2, parseDate(entity.getCreationDate()));
			stmt.setInt(3, entity.getId());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "SQL connection failed.", e);
			return false;
		}
		
		return true;
	}

	@Override
	public boolean delete(LabList entity) {
		Connection conn = DatabaseConnectionFactory.getConnection();
		String query = "DELETE FROM `lab_lists` WHERE `list_id`=?";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(query);
			
			stmt.setInt(1, entity.getId());
			stmt.execute();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "SQL connection failed.", e);
			return false;
		}
		
		return true;
	}

	private LabList parseLabList(ResultSet result) throws SQLException {
		LabList list = new LabList();
		
		// Parse Patient by means of Patient ID.
		Integer patientId = result.getInt("patient_id");
		LazyLoadedEntity<Integer, Patient> lazyPatient = new LazyLoadedEntity<>();
		lazyPatient.setKey(patientId);
		lazyPatient.setDao(new PatientDao());
		
		list.setPatient(lazyPatient);
		
		// Parse the rest of the information.
		list.setId(result.getInt("list_id"));
		list.setCreationDate(parseDate(result.getTimestamp("creation_date")));
		
		return list;
	}
	
	private Timestamp parseDate(java.util.Date creationDate) {
		return new Timestamp(creationDate.getTime());
	}
	
	private java.util.Date parseDate(Timestamp creationDate) {
		return new java.util.Date(creationDate.getTime());
	}
}