package fi.rikusarlin.validationdemo2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.IncomeType;
import fi.rikusarlin.validationdemo.data2.Income;
import fi.rikusarlin.validationdemo.utils.ValidatorUtils;
import fi.rikusarlin.validationdemo.validation.IncomeChecks;

public class IncomeTest2 
{
	private static boolean setUpIsDone = false;
	private static Validator validator;
	private static DateTimeFormatter formatter;
	Set<ConstraintViolation<Income>> violations;

	@BeforeAll
	public static void setUp() {
	    if (setUpIsDone) {
	        return;
	    }
    	validator = Validation.buildDefaultValidatorFactory().getValidator();
    	formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
   	    setUpIsDone = true;
	}
	
    /**
     * Test that income with start and end dates in sensible order is ok
     */
    @Test
    public void testIncomeWithGoodDateRange()
    {
    	Income income1 = new Income();
    	income1.setIncomeType(IncomeType.SALARY);
    	income1.setAmount(1520.25);
    	DateRange dateRange = new DateRange();
    	income1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	violations = validator.validate(income1, IncomeChecks.class);
        Assertions.assertTrue(violations.isEmpty());
    }

    /**
     * Test that income with start and end dates in sensible order is ok
     */
    @Test
    public void testIncomeWithGoodDateRangeNegativeAmount()
    {
    	Income income1 = new Income();
    	income1.setIncomeType(IncomeType.SALARY);
    	income1.setAmount(-1520.25);
    	DateRange dateRange = new DateRange();
    	income1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	violations = validator.validate(income1, IncomeChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
    }

    /**
     * Test that income with only start date is ok
     */
    @Test
    public void testOpenEndedRange()
    {
    	Income income1 = new Income();
    	income1.setIncomeType(IncomeType.SALARY);
    	income1.setAmount(1520.25);
    	DateRange dateRange = new DateRange();
    	income1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(null);
    	violations = validator.validate(income1, IncomeChecks.class);
        Assertions.assertTrue(violations.isEmpty());
    }
    
    /**
     * Test that income with only start date is ok
     */
    @Test
    public void testOpenStartRange()
    {
    	Income income1 = new Income();
    	income1.setIncomeType(IncomeType.SALARY);
    	income1.setAmount(1520.25);
    	DateRange dateRange = new DateRange();
    	income1.setDateRange(dateRange);
    	dateRange.setStartDate(null);
    	dateRange.setEndDate(LocalDate.parse("01.09.2020", formatter));
    	violations = validator.validate(income1, IncomeChecks.class);
        Assertions.assertTrue(violations.isEmpty());
    }

    /**
     * Test that income with start and end dates in wrong order is NOT ok
     */
    @Test
    public void testIncomeWithBadDateRange()
    {
    	Income income1 = new Income();
    	income1.setIncomeType(IncomeType.SALARY);
    	income1.setAmount(1520.25);
    	DateRange dateRange = new DateRange();
    	income1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.10.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.09.2020", formatter));
    	violations = validator.validate(income1, IncomeChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 1);
    }
    
    @Test
    public void testOtherIncomeWithDescription()
    {
    	Income income1 = new Income();
    	income1.setIncomeType(IncomeType.OTHER);
    	income1.setAmount(1520.25);
    	DateRange dateRange = new DateRange();
    	income1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	income1.setOtherIncomeDescription("Heitin lestiä Hagiksessa yhden perjantai-illan");
    	violations = validator.validate(income1, IncomeChecks.class);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void testOtherIncomeWithoutDescription()
    {
    	Income income1 = new Income();
    	income1.setIncomeType(IncomeType.OTHER);
    	income1.setAmount(1520.25);
    	DateRange dateRange = new DateRange();
    	income1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	violations = validator.validate(income1, IncomeChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 1);
    }
    
    @AfterEach
    public void logValdiationErrorMessages()
    {
        ValidatorUtils.logValidationErrors(violations);
    }
}
