package com.test.healthcare.app.PatientEntity;

import com.test.healthcare.app.enums.Gender;

import lombok.Data;

/**
 * PatientEntity for patient Dto
 * 
 * @author hamad
 */
@Data
public class PatientEntity {
	
	private Long id;

	private String name;
	
	private Gender gender;
	
	private String phoneNumber;
}
