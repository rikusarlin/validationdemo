# Testing JSR380 Java Validation Framework 2.0
The purpose of this project is to try out JSR 380. We should be able to do the following kinds of validations with the framework:
* Mandatory check
* Data length check
* Date format validity check
* Data format validity check
* Conditional mandatory check
* Cross-validation of data within a single object
* Cross-validating data between two or more objects within on object graph

In addition, at least the following checks should be done in a reusable way it's OK if others are as well:
* Data format validity check
* Cross-validation of data within a single object

The domain here will be an imaginary "housing benefit application" containing various pieces of information. Date periods and amounts of many are a prominent feature is this domain model. The business objects are as follows
* Housing benefit application
    * List of HouseholdMember 
        * person number
        * date range
    * List of Income
        * incomeType
        * otherIncomeDescription
        * amount
        * date range
    * List of HousingExpense
        * expenseType
        * otherExpenseDescription
        * amount
        * date range
    * date range

Data validity checks will be done for HouseholdMember.personNumber, Income.amount and HousingExpenses.amount. Cross-validation within one object will be done for various date periods (HousingBenefitApplication.start/endDate, HouseholdMember.start/endDate, Income.start/endDate, HousingExpenses.start/endDate). Cross-validation between objects is done
* between HousingBenefitApplication.start/endDate and the other date periods (ie. all date periods must be within the date period of the application)

The results show the following:
* Mandatory check: this is trivial, using @NotNull
* Data length check: trivial, using @Size(min=X, max=y)
* Date format validity check: trivial @DateTimeFormat(pattern="dd.MM.yyyy")
* Data format validity check: easy using custom validator, see ValidPersonNumber
* Conditional mandatory check: can be done, requires custom validator, see NullIfAnotherFieldHasValue
* Cross-validation of data within a single object: can be done, requires custom validator, see ValidDateRange
* Cross-validating data between two or more objects within on object graph: can be done, but is somewhat painful. Three different approaches were made:
    * OverlappingDataRanges, associated with entity classes in fi.rikusarlin.validationdemo.data
    * SubCollectionOverlappingDataRange, , associated with entity classes in fi.rikusarlin.validationdemo.data2
    * ValidApplicationDataRanges, associated with entity classes in fi.rikusarlin.validationdemo.data3

Of these, SubCollectionOverlappingDataRange might be the most elegant. 

In addition to the validations listed above, grouping of validations for "wizard-like" data input was studied. This turned out to be pretty easy - you can quite easily select a subset of validations to be done.

Most validators can be implemented in a resuable way, though this is quite complex with cross-validatotion of object graphs - which is quite natural, since they tend to be context specific. The basic idea of Bean Validation is to verify data "one aspect at a time", which supports reuse. One just adds various validations to a piece of data. This is easy enough to do one field at a time, but more complex for cross-validation within a class, and yet more complex for cross-validating an object graph.

Custom error messages are somewhat complex to make.

Severity of errors can be controlled quite easily using "payload". In this approach, for any given validation you declare its severity in the annotation.

As an example, the following example states that PersonNumber is a string that may not be null, is to be excactly 11 characters long, and pass "ValidPersonNumber" validation. If any of these fails, this results in Error severity notification. These checks are done when "HouseholdChecks" are done.
```java
@NotNull(groups=HouseholdChecks.class, payload={Severity.Error.class})
@Size(min = 11, max = 11,groups=HouseholdChecks.class, payload={Severity.Error.class})
@ValidPersonNumber(groups=HouseholdChecks.class, payload={Severity.Error.class})
String personNumber;
```