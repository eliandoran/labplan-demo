package com.labplan.services;

import java.util.Date;
import java.util.List;

import com.labplan.entities.LabList;
import com.labplan.entities.LabTest;
import com.labplan.entities.Patient;
import com.labplan.entities.generic.LazyLoadedEntity;
import com.labplan.persistence.generic.LabListDao;
import com.labplan.persistence.generic.PatientDao;
import com.labplan.persistence.sql.SqlPatientDao;

public class LabListService extends Service<LabList, LabListDao> {

	public LabListService(LabListDao dao) {
		super(dao);
	}

	@Override
	public List<LabList> getPage(int page, int entriesPerPage) {
		if (page < 1)
			throw new RuntimeException("`page` argument should be a positive number.");

		if (entriesPerPage < 1)
			throw new RuntimeException("`entriesPerPage` should be a positive number.");

		return dao.read(entriesPerPage, (page - 1) * entriesPerPage);
	}

	@Override
	public Integer getPageCount(int entriesPerPage) {
		return (int) Math.round(Math.ceil(((double) dao.getCount() / entriesPerPage)));
	}

	public LabList parse(String patientId) {
		Validator validator = new Validator();
		
		validator.assertStringIsInteger("Patient (ID)", patientId);
		validator.validate();
		
		PatientDao patientDao = new SqlPatientDao();
		Patient patient = patientDao.read(Integer.parseInt(patientId));
		
		validator.assertNotNull("Patient", patient);
		validator.validate();
		
		LabList list = new LabList(new LazyLoadedEntity<Integer, Patient>(patient), new Date());
		return list;
	}
}