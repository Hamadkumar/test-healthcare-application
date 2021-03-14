package com.test.healthcare.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.test.healthcare.app.model.Patient;

/**
 * PatientRepository for crud operations on patient table
 * 
 * @author hamad
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

	@Query(value= "Select p from Patient p where UPPER(p.name) like :name%")
	List<Patient> findByName(String name);

}