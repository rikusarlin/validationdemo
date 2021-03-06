package fi.rikusarlin.validationdemo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fi.rikusarlin.validationdemo.data.Expense;
import fi.rikusarlin.validationdemo.data.HouseholdMember;
import fi.rikusarlin.validationdemo.data.HousingBenefitApplication;
import fi.rikusarlin.validationdemo.data.Income;

public class HousingBenefitApplicationTest 
{
	private static boolean setUpIsDone = false;
	private static Validator validator;
	private static DateTimeFormatter formatter;

	@BeforeAll
	public static void setUp() {
	    if (setUpIsDone) {
	        return;
	    }
    	validator = Validation.buildDefaultValidatorFactory().getValidator();
    	formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
   	    setUpIsDone = true;
	}
	
	private HousingBenefitApplication goodHuosingBenefitApplication() {
    	Expense expense1 = new Expense();
    	expense1.setExpenseType(ExpenseType.RENT);
    	expense1.setAmount(1520.25);
    	DateRange dateRange = new DateRange();
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter));
    	expense1.setDateRange(dateRange);
    	Expense expense2 = new Expense();
    	expense2.setExpenseType(ExpenseType.ELECTRICITY);
    	expense2.setAmount(120.5);
    	expense2.setDateRange(dateRange);
    	
    	Income income1 = new Income();
    	income1.setIncomeType(IncomeType.SALARY);
    	income1.setAmount(3400.20);
    	income1.setDateRange(dateRange);
    	Income income2 = new Income();
    	income2.setIncomeType(IncomeType.DIVIDEND);
    	income2.setAmount(120.47);
    	income2.setDateRange(dateRange);

    	HouseholdMember hm1 = new HouseholdMember();
    	hm1.setDateRange(dateRange);
    	hm1.setPersonNumber("170871-0091");
    	HouseholdMember hm2 = new HouseholdMember();
    	hm2.setDateRange(dateRange);
    	hm2.setPersonNumber("130570-216E");
    	HouseholdMember hm3 = new HouseholdMember();
    	hm3.setDateRange(dateRange);
    	hm3.setPersonNumber("020103A678R");
    	HouseholdMember hm4 = new HouseholdMember();
    	hm4.setDateRange(dateRange);
    	hm4.setPersonNumber("270205A515X");
    	
    	HousingBenefitApplication hba = new HousingBenefitApplication();
    	hba.setDateRange(dateRange);
    	hba.addExpense(expense1);
    	hba.addExpense(expense2);
    	hba.addIncome(income1);
    	hba.addIncome(income2);
    	hba.addHouseholdMember(hm1);
    	hba.addHouseholdMember(hm2);
    	hba.addHouseholdMember(hm3);
    	hba.addHouseholdMember(hm4);

    	return hba;
	}
	
    @Test
    public void testGoodApplication()
    {
    	HousingBenefitApplication hba = goodHuosingBenefitApplication();
    	Set<ConstraintViolation<HousingBenefitApplication>> violations = validator.validate(hba);
        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void testBadApplicaton()
    {
    	HousingBenefitApplication hba = goodHuosingBenefitApplication();
    	DateRange dateRange = new DateRange();
    	dateRange.setStartDate(LocalDate.parse("01.11.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.08.2020", formatter));
    	hba.getIncomes().get(0).setDateRange(dateRange);
    	Set<ConstraintViolation<HousingBenefitApplication>> violations = validator.validate(hba);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 2);
        for(ConstraintViolation<HousingBenefitApplication> violation:violations) {
        	System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }

    @Test
    public void testVeryBadApplicaton()
    {
    	HousingBenefitApplication hba = goodHuosingBenefitApplication();
    	DateRange dateRange = new DateRange();
    	dateRange.setStartDate(LocalDate.parse("01.11.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.08.2020", formatter));
    	hba.getIncomes().get(0).setDateRange(dateRange);

    	DateRange dateRange2 = new DateRange();
    	dateRange2.setStartDate(LocalDate.parse("01.11.2019", formatter));
    	dateRange2.setEndDate(LocalDate.parse("01.08.2019", formatter));
    	hba.getHouseholdMembers().get(1).setDateRange(dateRange2);
    	
    	hba.getHouseholdMembers().get(2).setPersonNumber("020202A002B");
    	
    	Set<ConstraintViolation<HousingBenefitApplication>> violations = validator.validate(hba);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 5);
        for(ConstraintViolation<HousingBenefitApplication> violation:violations) {
        	System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }
    
    @Test
    public void testChangeOfApplicationDateRange()
    {
    	HousingBenefitApplication hba = goodHuosingBenefitApplication();
    	Set<ConstraintViolation<HousingBenefitApplication>> violations = validator.validate(hba);
        Assertions.assertTrue(violations.isEmpty());
        // This should invalidate really many sub-objects
    	DateRange dateRange2 = new DateRange();
    	dateRange2.setStartDate(LocalDate.parse("01.01.2019", formatter));
    	dateRange2.setEndDate(LocalDate.parse("01.06.2019", formatter));
    	hba.setDateRange(dateRange2);
    	violations = validator.validate(hba);
        Assertions.assertTrue(!violations.isEmpty());
        for(ConstraintViolation<HousingBenefitApplication> violation:violations) {
        	System.out.println(violation.getPropertyPath() + ": " + violation.getMessage());
        }
    }


}
