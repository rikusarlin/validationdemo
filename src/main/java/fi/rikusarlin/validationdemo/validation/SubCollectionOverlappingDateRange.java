package fi.rikusarlin.validationdemo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SubCollectionOverlappingDateRange.List.class)
@Constraint(validatedBy = SubCollectionOverlappingDateRangeValidator.class)
@Documented
public @interface SubCollectionOverlappingDateRange {

    String dateRangeFieldName();
    String collectionName();
    String collectionDateRangeFieldName();

    String message() default "SubCollectionOverlappingDateRange default message";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        SubCollectionOverlappingDateRange[] value();
    }

}
