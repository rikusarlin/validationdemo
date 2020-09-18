package fi.rikusarlin.validationdemo.validation;

import java.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.data3.Expense;
import fi.rikusarlin.validationdemo.data3.HouseholdMember;
import fi.rikusarlin.validationdemo.data3.HousingBenefitApplication;
import fi.rikusarlin.validationdemo.data3.Income;
import fi.rikusarlin.validationdemo.utils.ValidatorUtils;

/**
 * Implementation of {@link ValidApplicationDataRanges} validator.
 **/
public class ValidApplicationDataRangesValidator
    implements ConstraintValidator<ValidApplicationDataRanges, Object> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void initialize(ValidApplicationDataRanges annotation) {
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {

        if (!(value instanceof HousingBenefitApplication)) {
            return false;
        }

        boolean isValid = true;
        HousingBenefitApplication hba = (HousingBenefitApplication) value;
        
        HibernateConstraintValidatorContext hibernateContext = ctx.unwrap(
                HibernateConstraintValidatorContext.class );
        hibernateContext.disableDefaultConstraintViolation();
        
       	DateRange dateRange1 = hba.getDateRange();

       	// I guess you could generalize the following a bit...
       	
        int index=0;
       	for(HouseholdMember householdMember:hba.getHouseholdMembers()) {
           	DateRange dateRange2 = householdMember.getDateRange();
           	if(dateRange1 == null || dateRange2 == null) {
           		// Do nothing
           	} else if( dateRange1.getEndDate().isBefore(dateRange2.getStartDate()) ||
           		dateRange1.getStartDate().isAfter(dateRange2.getEndDate())) {
           		ValidatorUtils.addViolation(
           	    		"householdMembers", 
           	    		index, 
           	    		"dateRange",
           	    		"must overlap with application date range, and {0}-{1} and {2}-{3} do not overlap",
           				hibernateContext,
                   		dateRange1.getStartDate().format(formatter),
                   		dateRange1.getEndDate().format(formatter),
                   		dateRange2.getStartDate().format(formatter),
                   		dateRange2.getEndDate().format(formatter)
           		);
                isValid = false;
           	}
           	index++;
       	}
       	
        index=0;
       	for(Expense expense:hba.getHousingExpenses()) {
           	DateRange dateRange2 = expense.getDateRange();
           	if(dateRange1 == null || dateRange2 == null) {
           		// Do nothing
           	} else if( dateRange1.getEndDate().isBefore(dateRange2.getStartDate()) ||
           		dateRange1.getStartDate().isAfter(dateRange2.getEndDate())) {
           		ValidatorUtils.addViolation(
           	    		"housingExpenses", 
           	    		index, 
           	    		"dateRange",
           	    		"must overlap with application date range, and {0}-{1} and {2}-{3} do not overlap",
           				hibernateContext,
                   		dateRange1.getStartDate().format(formatter),
                   		dateRange1.getEndDate().format(formatter),
                   		dateRange2.getStartDate().format(formatter),
                   		dateRange2.getEndDate().format(formatter)
           		);
                isValid = false;
           	}
           	index++;
       	}

        index=0;
       	for(Income income:hba.getIncomes()) {
           	DateRange dateRange2 = income.getDateRange();
           	if(dateRange1 == null || dateRange2 == null) {
           		// Do nothing
           	} else if( dateRange1.getEndDate().isBefore(dateRange2.getStartDate()) ||
           		dateRange1.getStartDate().isAfter(dateRange2.getEndDate())) {
           		ValidatorUtils.addViolation(
           	    		"incomes", 
           	    		index, 
           	    		"dateRange",
           	    		"must overlap with application date range, and {0}-{1} and {2}-{3} do not overlap",
           				hibernateContext,
                   		dateRange1.getStartDate().format(formatter),
                   		dateRange1.getEndDate().format(formatter),
                   		dateRange2.getStartDate().format(formatter),
                   		dateRange2.getEndDate().format(formatter)
           		);
                isValid = false;
           	}
           	index++;
       	}
       	
       	
       	return isValid;
    }
}
