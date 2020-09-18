package fi.rikusarlin.validationdemo.validation;

import java.time.format.DateTimeFormatter;
import java.util.Collection;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.utils.ValidatorUtils;

/**
 * Implementation of {@link SubCollectionOverlappingDateRange} validator.
 **/
public class SubCollectionOverlappingDateRangeValidator
    implements ConstraintValidator<SubCollectionOverlappingDateRange, Object> {

    private String dateRangeFieldName;
    private String collectionName;
    private String collectionDateRangeFieldName;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void initialize(SubCollectionOverlappingDateRange annotation) {
        dateRangeFieldName = annotation.dateRangeFieldName();
        collectionName = annotation.collectionName();
        collectionDateRangeFieldName = annotation.collectionDateRangeFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {

        if (value == null) {
            return true;
        }

        boolean isValid = true;
       	DateRange dateRange1 = (DateRange) ValidatorUtils.getProperty(value, dateRangeFieldName);
       	Collection<?> collection = (Collection<?>) ValidatorUtils.getProperty(value, collectionName);

        HibernateConstraintValidatorContext hibernateContext = ctx.unwrap(
                HibernateConstraintValidatorContext.class );
        hibernateContext.disableDefaultConstraintViolation();
       	
        int index=0;
       	for(Object subvalue:collection) {
           	DateRange dateRange2 = (DateRange) ValidatorUtils.getProperty(subvalue, collectionDateRangeFieldName);
           	if(dateRange1 == null || dateRange2 == null) {
           	} else if( dateRange1.getEndDate().isBefore(dateRange2.getStartDate()) ||
           		dateRange1.getStartDate().isAfter(dateRange2.getEndDate())) {
           		ValidatorUtils.addViolation(
           	    		collectionName, 
           	    		index, 
           	    		collectionDateRangeFieldName,
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
