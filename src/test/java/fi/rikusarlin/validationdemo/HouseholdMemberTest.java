package fi.rikusarlin.validationdemo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fi.rikusarlin.validationdemo.data.HouseholdMember;

public class HouseholdMemberTest 
{
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    public void testValidHousehold()
    {
    	HouseholdMember hm1 = new HouseholdMember();
    	DateRange dateRange = new DateRange();
    	hm1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	hm1.setPersonNumber("170871-0091");
    	Set<ConstraintViolation<HouseholdMember>> violations = validator.validate(hm1);
        Assertions.assertTrue(violations.isEmpty());
    	hm1.setPersonNumber("170871+0091");
    	violations = validator.validate(hm1);
        Assertions.assertTrue(violations.isEmpty());
    	hm1.setPersonNumber("130570-216E");
    	violations = validator.validate(hm1);
        Assertions.assertTrue(violations.isEmpty());
    	hm1.setPersonNumber("020103A678R");
    	violations = validator.validate(hm1);
        Assertions.assertTrue(violations.isEmpty());
    	hm1.setPersonNumber("270205A515X");
    	violations = validator.validate(hm1);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void testBadPerson()
    {
    	HouseholdMember hm1 = new HouseholdMember();
    	DateRange dateRange = new DateRange();
    	hm1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	hm1.setPersonNumber("170871-0092");
    	Set<ConstraintViolation<HouseholdMember>> violations = validator.validate(hm1);
        Assertions.assertTrue(!violations.isEmpty());
    }
    
    @Test
    public void testTooShortPersonNumber()
    {
    	HouseholdMember hm1 = new HouseholdMember();
    	DateRange dateRange = new DateRange();
    	hm1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	hm1.setPersonNumber("170871-009");
    	Set<ConstraintViolation<HouseholdMember>> violations = validator.validate(hm1);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 2);
        for(ConstraintViolation<HouseholdMember> violation:violations) {
        	System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

    @Test
    public void testNonOverlappingDateRange()
    {
    	HouseholdMember hm1 = new HouseholdMember();
    	DateRange dateRange = new DateRange();
    	hm1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	DateRange dateRange2 = new DateRange();
    	hm1.setParentDateRange(dateRange2);
    	dateRange2.setStartDate(LocalDate.parse("01.07.2020", formatter));
    	dateRange2.setEndDate(LocalDate.parse("31.07.2020", formatter));
    	hm1.setPersonNumber("170871-009");
    	Set<ConstraintViolation<HouseholdMember>> violations = validator.validate(hm1);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 3);
        for(ConstraintViolation<HouseholdMember> violation:violations) {
        	System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

}
