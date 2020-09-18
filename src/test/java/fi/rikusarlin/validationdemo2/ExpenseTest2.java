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
import fi.rikusarlin.validationdemo.ExpenseType;
import fi.rikusarlin.validationdemo.data2.Expense;
import fi.rikusarlin.validationdemo.utils.ValidatorUtils;
import fi.rikusarlin.validationdemo.validation.ExpenseChecks;

public class ExpenseTest2 
{
	private static boolean setUpIsDone = false;
	private static Validator validator;
	private static DateTimeFormatter formatter;
	Set<ConstraintViolation<Expense>> violations;

	@BeforeAll
	public static void setUp() {
	    if (setUpIsDone) {
	        return;
	    }
    	validator = Validation.buildDefaultValidatorFactory().getValidator();
    	formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
   	    setUpIsDone = true;
	}
	
    @Test
    public void testExpenseWithGoodDateRange()
    {
    	Expense expense1 = new Expense();
    	expense1.setExpenseType(ExpenseType.RENT);
    	expense1.setAmount(1520.25);
    	DateRange dateRange = new DateRange();
    	expense1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	violations = validator.validate(expense1, ExpenseChecks.class);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void testExpenseWithBadDateRange()
    {
    	Expense expense1 = new Expense();
    	expense1.setExpenseType(ExpenseType.RENT);
    	expense1.setAmount(1520.25);
    	DateRange dateRange = new DateRange();
    	expense1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.11.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.08.2020", formatter));
    	violations = validator.validate(expense1, ExpenseChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 1);
    }

    @Test
    public void testExpenseWithBadDateRangeAndNegativeAmount()
    {
    	Expense expense1 = new Expense();
    	expense1.setExpenseType(ExpenseType.RENT);
    	expense1.setAmount(-1520.25);
    	DateRange dateRange = new DateRange();
    	expense1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.11.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.08.2020", formatter));
    	violations = validator.validate(expense1, ExpenseChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 2);
    }

    @Test
    public void testExpenseWithOtherDescription()
    {
    	Expense expense1 = new Expense();
    	expense1.setExpenseType(ExpenseType.OTHER);
    	expense1.setOtherExpenseDescription("A liter of cognac");
    	expense1.setAmount(220.0);
    	DateRange dateRange = new DateRange();
    	expense1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.08.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.11.2020", formatter));
    	violations = validator.validate(expense1, ExpenseChecks.class);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void testExpenseWithoutOtherDescription()
    {
    	Expense expense1 = new Expense();
    	expense1.setExpenseType(ExpenseType.OTHER);
    	expense1.setAmount(220.0);
    	DateRange dateRange = new DateRange();
    	expense1.setDateRange(dateRange);
    	dateRange.setStartDate(LocalDate.parse("01.08.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.11.2020", formatter));
    	violations = validator.validate(expense1, ExpenseChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 1);

    }

    @AfterEach
    public void logValdiationErrorMessages()
    {
        ValidatorUtils.logValidationErrors(violations);
    }
}
