package com.test.healthcare.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.test.healthcare.app.PatientEntity.PatientEntity;
import com.test.healthcare.app.enums.Gender;
import com.test.healthcare.app.model.Patient;
import com.test.healthcare.app.repository.PatientRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * PatientService is the service class for patient table
 * 
 * @author hamad
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

	private final PatientRepository patientRepository;

    /**
     * findAll is used to find all the patient list.
     * 
     * @return List<Patient>
     */
    public List<PatientEntity> findAll() {
    	List<PatientEntity> patientList = patientRepository.findAll().stream()
    			.map(patient -> toEntity(patient))
    			.collect(Collectors.toList());
    	log.info("Patient records fetched : {}", patientList);
    	return patientList;
    }
    
    /**
     * findById is used to find patient by id.
     * 
     * @return Optional<Patient>
     */
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    /**
     * save is used to save the patient details.
     * 
     * @param patient
     */
    public void save(Patient patient) {
        patientRepository.save(patient);
    }

    /**
     * deleteById is used to delete a patient by ID.
     * 
     * @param id
     */
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

	/**
     * findByName is used to find all the patient list by name.
     * 
     * @return List<PatientEntity>
     */
    public List<PatientEntity> findByName(String name) {
    	return patientRepository.findByName(name.toUpperCase()).stream().map(patient -> toEntity(patient)).collect(Collectors.toList());
    }
    
    /**
	 * findAllByPage is used to find patients by page.
	 * 
	 * @param pageNo
	 * @return List<Patient>
	 */
	public List<PatientEntity> findAllByPage(int pageNo) {
		int patientPerPage = 5;
		Pageable pageable = PageRequest.of(pageNo, patientPerPage); // Using pageable interface to do pagination, 5 patient per page.
		Page<Patient> patientPage =  patientRepository.findAll(pageable);
		return patientPage.getContent().stream().map(patient -> toEntity(patient)).collect(Collectors.toList());
	}

	private PatientEntity toEntity(Patient patient) {
		PatientEntity patientEntity = new PatientEntity();
		patientEntity.setId(patient.getId());
		patientEntity.setName(patient.getName());
		patientEntity.setGender(Gender.findByCode(patient.getGender()));
		patientEntity.setPhoneNumber(patient.getPhoneNumber());
		return patientEntity;
	}
	
}
