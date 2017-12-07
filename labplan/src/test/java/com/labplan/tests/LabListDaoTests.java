package com.labplan.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import org.junit.BeforeClass;
import org.junit.Test;

import com.labplan.entities.LabList;
import com.labplan.entities.Patient;
import com.labplan.entities.generic.LazyLoadedEntity;
import com.labplan.persistence.sql.LabListDao;
import com.labplan.persistence.sql.PatientDao;
import com.labplan.tests.helpers.CrudTester;

public class LabListDaoTests extends CrudTester<Integer, LabList, LabListDao> {
	private static LabListDao dao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new LabListDao();

		System.setProperty("java.util.logging.config.file",
				ClassLoader.getSystemResource("logging.properties").getPath());
	}
	
	@Test
	public void testPatientLazyLoading() {
		// CREATE a random lab list.
		LabList dummyLabList = getRandomEntity();
		Integer dummyLabListId = dao.create(dummyLabList);
		assertNotNull("SQL insertion failed.", dummyLabListId);
		dummyLabList.setId(dummyLabListId);
		
		// READ the lab list back.
		LabList sameLabList = dao.read(dummyLabList.getId());
		assertNotNull("SQL retrieval failed.", sameLabList);
		
		// Check to see whether patient is not null.
		assertNotNull("Patient should not be null.", sameLabList.getPatient());
		
		// Check to see that the patient has not been yet loaded (lazy loading).
		assertTrue(!sameLabList.getPatient().getIsLoaded());
		
		// Load the patient and compare it against the generated Patient.
		Patient readPatient = sameLabList.getPatient().getEntity();
		assertTrue(sameLabList.getPatient().getIsLoaded());
		assertEquals(dummyLabList.getPatient().getEntity(), readPatient);
		
		// DELETE the generated lab list.
		assertTrue("SQL deletion failed.", dao.delete(dummyLabList));
	}

	@Override
	public LabList getRandomEntity() {
		LabList list = new LabList();
		
		// Generate a random patient.
		PatientDaoTests patientDaoTests = new PatientDaoTests();
		PatientDao patientDao = new PatientDao();
		
		Patient patient = patientDaoTests.getRandomEntity();
		Integer patientId = patientDao.create(patient);
		patient.setId(patientId);
		
		list.setPatient(new LazyLoadedEntity<>(patient));
		
		// Generate a random creation date.
		list.setCreationDate(new Date());
		
		return list;
	}

	@Override
	public LabListDao getDao() {
		return dao;
	}

}
