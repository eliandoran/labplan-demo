package com.labplan.persistence.generic;

import java.util.Set;
import com.labplan.entities.*;
import com.labplan.persistence.exceptions.ConnectionFailedException;

public interface IPatientDao extends CrudInterface<Patient, Integer> {
	Patient read(String firstName, String lastName);
}
