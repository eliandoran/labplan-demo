package com.labplan.persistence.generic;

import java.util.Set;
import com.labplan.entities.*;
import com.labplan.persistence.exceptions.ConnectionFailedException;

public interface IPatientDao {
	Patient getPatientById(int id);
	Patient getPatientByName(String firstName, String lastName);
	Set<Patient> getAllPatients();
	boolean insertPatient(Patient patient);
	boolean updatePatient(Patient patient);
	boolean deletePatient(Patient patient);
}