package com.hemant.db.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntegerValidator implements ConstraintValidator<IsInteger, Integer>{
	public boolean isValid(Integer Id, ConstraintValidatorContext constraintValidatorContext) {
        if(Id instanceof Integer) {
            return true;
        } else {
            return false;
        }
    }
}
