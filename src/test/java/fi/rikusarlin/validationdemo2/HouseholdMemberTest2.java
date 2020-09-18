package fi.rikusarlin.validationdemo2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.data2.HouseholdMember;
import fi.rikusarlin.validationdemo.utils.ValidatorUtils;
import fi.rikusarlin.validationdemo.validation.HouseholdChecks;

public class HouseholdMemberTest2 
{
	private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	Set<ConstraintViolation<HouseholdMember>> violations;

    @Test
    public void testValidHousehold()
    {
    	HouseholdMember hm1 = new HouseholdMember();
    	DateRange dateRange = new DateRange();
    	hm1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	hm1.setPersonNumber("170871-0091");
    	violations = validator.validate(hm1, HouseholdChecks.class);
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
    	violations = validator.validate(hm1, HouseholdChecks.class);
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
    	violations = validator.validate(hm1, HouseholdChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 2);
    }
    
    @AfterEach
    public void logValdiationErrorMessages()
    {
        ValidatorUtils.logValidationErrors(violations);
    }

}
