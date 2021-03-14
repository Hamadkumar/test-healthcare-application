package com.test.healthcare.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Patient Model class
 * 
 * @author hamad
 */
@Data
@Entity
@Table  
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column  
	private Long id; 
	
	@Column
	private String name;
	
	@Column
	private int gender;
	
	@Column
	private String phoneNumber;
	
}