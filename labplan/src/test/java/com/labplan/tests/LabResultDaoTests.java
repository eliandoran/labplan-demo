package com.labplan.tests;

import org.junit.BeforeClass;

import com.labplan.entities.CompositeKeyPair;
import com.labplan.entities.LabList;
import com.labplan.entities.LabResult;
import com.labplan.entities.LabTest;
import com.labplan.persistence.sql.LabResultDao;
import com.labplan.tests.helpers.CrudTester;

public class LabResultDaoTests extends CrudTester<CompositeKeyPair<LabTest, LabList>, LabResult, LabResultDao> {
	private static LabResultDao dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dao = new LabResultDao();

		System.setProperty("java.util.logging.config.file",
				ClassLoader.getSystemResource("logging.properties").getPath());
	}

	@Override
	public LabResult getRandomEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LabResultDao getDao() {
		return dao;
	}
}
