package com.test.healthcare.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.healthcare.app.PatientEntity.PatientEntity;
import com.test.healthcare.app.exception.ResourceNotFoundException;
import com.test.healthcare.app.model.Patient;
import com.test.healthcare.app.service.PatientService;

import lombok.extern.slf4j.Slf4j;


/**
 * PatientController is a controller to perform CRUD operations on patients.
 * 
 * @author Hamad
 *
 */
@Slf4j
@RestController
@RequestMapping("")
public class PatientController {

	@Autowired
    PatientService patientService;
	
	/**
	 * getAllPatients is used to retrieve all the  patients list.
	 * 
	 * @return ResponseEntity<List<Patient>>
	 */
	@GetMapping("/getAllPatients")
    public ResponseEntity<List<PatientEntity>> getAllPatients() {
        return ResponseEntity.ok(patientService.findAll());
    }
	
    /**
     * createPatient is used to create a patient. 
     * 
     * @param patient
     * @return ResponseEntity<Patient>
     */
    @PostMapping("/createPatient")
    public ResponseEntity<Patient> createPatient(Patient patient) {
        patientService.save(patient);
        log.info("Patient record created successfully : {}", patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    /**
     * updatePatient is used to update the patient record.
     * 
     * @param patient
     * @return ResponseEntity<Patient>
     */
    @PutMapping("/updatePatient")
    public ResponseEntity<?> updatePatient(Patient patient) {
		try {
			Long patientId = patient.getId();
			patientService.findById(patientId).orElseThrow(
					() -> new ResourceNotFoundException("There is no record for the given ID", "id", patientId));
			Patient patientDetails = patient;
			patientDetails.setId(patientId);
			patientService.save(patientDetails);
			log.info("Patient record updated successfully with Entity : {}", patient);
		} catch (ResourceNotFoundException exception) {
			return ResponseEntity.of(Optional.of("There is no record for the given ID"));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(patient);
	}

    /**
     * deletePatient is used to delete the patient by ID.
     * 
     * @param patientId
     * @return ResponseEntity<?>
     */
    @DeleteMapping("/deletePatientById/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable(value = "id") Long patientId) {
    	try {
        patientService.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("There is no record for the given ID", "id", patientId));
        patientService.deleteById(patientId);
        log.info("Patient Record deleted successfully for patient ID : {}", patientId);
    	} catch(ResourceNotFoundException exception) {
    		return ResponseEntity.of(Optional.of("There is no record for the given ID"));
    	}
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    
    /**
	 * searchPatients is used to search  patients list.
	 * @param name
	 * @return ResponseEntity<List<Patient>>
	 */
	@GetMapping("/searchPatients")
    public ResponseEntity<List<PatientEntity>> searchPatients(@RequestParam String name) {
		List<PatientEntity> patientList = patientService.findByName(name);
		log.trace("Searched Patient Record : {} for query : {}", patientList, name);
        return ResponseEntity.ok(patientList);
    }
    
    /**
     * getPatientsByPage is used to get patients by page number.
     * 
     * @param pageNo
     * @return ResponseEntity<List<Patient>>
     */
    @GetMapping("/getPatientsByPage/{pageNo}")
    public ResponseEntity<List<PatientEntity>> getPatientsByPage(@PathVariable(value = "pageNo") int pageNo) {
        return ResponseEntity.ok(patientService.findAllByPage(pageNo));
    }
    
}
