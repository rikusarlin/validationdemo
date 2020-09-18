package fi.rikusarlin.validationdemo.validation;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import fi.rikusarlin.validationdemo.DateRange;

public class ValidDateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {
	
	@Override
	public void initialize (ValidDateRange constraintAnnotation) {
	}

	@Override
	public boolean isValid (Object value, ConstraintValidatorContext context) {

		if(!(value instanceof DateRange)) {
			return false;
		}
		
		DateRange dateRange = (DateRange) value;
		LocalDate startDate = dateRange.getStartDate();
		LocalDate endDate = dateRange.getEndDate();
		
		// If both are given, startDate must be before endDate
    	if(startDate != null && endDate != null) {
			return (startDate.isBefore(endDate));
		} else {
			return true;
		}
			
	}
}
