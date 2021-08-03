package com.hemant.db.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongValidator implements ConstraintValidator<IsLong, java.lang.Long> {
	public boolean isValid(java.lang.Long Id, ConstraintValidatorContext constraintValidatorContext) {
		if(Id instanceof java.lang.Long) {
		    return true;
		} else {
		    return false;
		}
	}
}
