package com.test.healthcare.app.enums;

/**
 * Gender enum
 * 
 * @author hamad
 *
 */
public enum Gender {
	
	MALE(1), FEMALE(2), OTHERS(3);
	
	private int genderCode;
	
	Gender(int genderCode) {
		this.genderCode = genderCode;
	}
	
	public int getCode() {
        return genderCode;
    }
	
	public static Gender findByCode(int genderCode){
	    for(Gender gender : Gender.values()){
	        if(gender.getCode() == genderCode){
	            return gender;
	        }
	    }
	    return null;
	}

}
