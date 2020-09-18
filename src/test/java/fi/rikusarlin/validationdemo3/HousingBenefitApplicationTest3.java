package fi.rikusarlin.validationdemo3;

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
import fi.rikusarlin.validationdemo.IncomeType;
import fi.rikusarlin.validationdemo.data3.Expense;
import fi.rikusarlin.validationdemo.data3.HouseholdMember;
import fi.rikusarlin.validationdemo.data3.HousingBenefitApplication;
import fi.rikusarlin.validationdemo.data3.Income;
import fi.rikusarlin.validationdemo.utils.ValidatorUtils;
import fi.rikusarlin.validationdemo.validation.AllChecks;
import fi.rikusarlin.validationdemo.validation.ApplicationChecks;
import fi.rikusarlin.validationdemo.validation.ExpenseChecks;
import fi.rikusarlin.validationdemo.validation.HouseholdChecks;
import fi.rikusarlin.validationdemo.validation.IncomeChecks;

public class HousingBenefitApplicationTest3 
{
	private static boolean setUpIsDone = false;
	private static Validator validator;
	private static DateTimeFormatter formatter;
	Set<ConstraintViolation<HousingBenefitApplication>> violations;

	@BeforeAll
	public static void setUp() {
	    if (setUpIsDone) {
	        return;
	    }
    	validator = Validation.buildDefaultValidatorFactory().getValidator();
    	formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
   	    setUpIsDone = true;
	}
	
	private HousingBenefitApplication addHousehold(DateRange dateRange, HousingBenefitApplication hba) {
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
    	hba.addHouseholdMember(hm1);
    	hba.addHouseholdMember(hm2);
    	hba.addHouseholdMember(hm3);
    	hba.addHouseholdMember(hm4);
    	return hba;
	}

	private HousingBenefitApplication addIncomes(DateRange dateRange, HousingBenefitApplication hba) {
    	Income income1 = new Income();
    	income1.setIncomeType(IncomeType.SALARY);
    	income1.setAmount(3400.20);
    	income1.setDateRange(dateRange);
    	Income income2 = new Income();
    	income2.setIncomeType(IncomeType.DIVIDEND);
    	income2.setAmount(120.47);
    	income2.setDateRange(dateRange);
    	hba.addIncome(income1);
    	hba.addIncome(income2);
    	return hba;
	}

	private HousingBenefitApplication addExpenses(DateRange dateRange, HousingBenefitApplication hba) {
    	Expense expense1 = new Expense();
    	expense1.setExpenseType(ExpenseType.RENT);
    	expense1.setAmount(1520.25);
    	expense1.setDateRange(dateRange);
    	Expense expense2 = new Expense();
    	expense2.setExpenseType(ExpenseType.ELECTRICITY);
    	expense2.setAmount(120.5);
    	expense2.setDateRange(dateRange);
    	hba.addExpense(expense1);
    	hba.addExpense(expense2);
    	return hba;
	}

	private HousingBenefitApplication basicHousingBenefitApplication() {
    	DateRange dateRange = new DateRange();
    	dateRange.setStartDate(LocalDate.parse("01.09.2020", formatter));
    	dateRange.setEndDate(LocalDate.parse("01.10.2020", formatter)); 	    	
    	HousingBenefitApplication hba = new HousingBenefitApplication();
    	hba.setDateRange(dateRange);
    	return hba;
	}

	private HousingBenefitApplication goodHuosingBenefitApplication() {
		HousingBenefitApplication hba = basicHousingBenefitApplication();
    	hba = addHousehold(hba.getDateRange(), hba);
    	hba = addIncomes(hba.getDateRange(), hba);
    	hba = addExpenses(hba.getDateRange(), hba);
    	return hba;
	}
	
    @Test
    public void testGoodApplication()
    {
    	HousingBenefitApplication hba = goodHuosingBenefitApplication();
    	violations = validator.validate(hba);
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
    	violations = validator.validate(hba,ApplicationChecks.class);
        Assertions.assertTrue(violations.isEmpty());
    	violations = validator.validate(hba, IncomeChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 2);
    	violations = validator.validate(hba, AllChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 2);
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
    	
    	violations = validator.validate(hba,AllChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 5);
    }
    
    @Test
    public void testChangeOfApplicationDateRange()
    {
    	HousingBenefitApplication hba = goodHuosingBenefitApplication();
    	violations = validator.validate(hba, AllChecks.class);
        Assertions.assertTrue(violations.isEmpty());
        
        // This should invalidate really many sub-objects
        DateRange dateRange1 = hba.getDateRange();
    	DateRange dateRange2 = new DateRange();
    	dateRange2.setStartDate(LocalDate.parse("01.01.2019", formatter));
    	dateRange2.setEndDate(LocalDate.parse("01.06.2019", formatter));
    	hba.setDateRange(dateRange2);
    	hba.getHouseholdMembers().clear();
    	hba.getHousingExpenses().clear();
    	hba.getIncomes().clear();
    	violations = validator.validate(hba, ApplicationChecks.class);
        Assertions.assertTrue(violations.isEmpty());
        /*
         * Note that in this validation model all data input thus far is validated
         * This is different from model 2, where we could specify this separately
         * In here, for example, household and income checks are done when we ask for
         * income checks. Acatually, we could ask all checks just the same.
         */
        hba = addHousehold(dateRange1, hba);
    	violations = validator.validate(hba, HouseholdChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 4);
        hba = addExpenses(dateRange1, hba);
        violations = validator.validate(hba, ExpenseChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 6);
        hba = addIncomes(dateRange1, hba);
    	violations = validator.validate(hba, IncomeChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 8);
    	violations = validator.validate(hba, AllChecks.class);
        Assertions.assertTrue(!violations.isEmpty());
        Assertions.assertTrue(violations.size() == 8);
    }
    
    @AfterEach
    public void logValdiationErrorMessages()
    {
        ValidatorUtils.logValidationErrors(violations);
    }

}
