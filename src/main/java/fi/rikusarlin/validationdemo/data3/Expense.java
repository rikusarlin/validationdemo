package fi.rikusarlin.validationdemo.data3;

import javax.validation.constraints.Min;

import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.ExpenseType;
import fi.rikusarlin.validationdemo.validation.ExpenseChecks;
import fi.rikusarlin.validationdemo.validation.NotNullIfAnotherFieldHasValue;
import fi.rikusarlin.validationdemo.validation.Severity;
import fi.rikusarlin.validationdemo.validation.ValidDateRange;

@NotNullIfAnotherFieldHasValue(
	    fieldName = "expenseType",
	    fieldValue = "OTHER",
	    dependFieldName = "otherExpenseDescription",
	    groups=ExpenseChecks.class,
	    payload={Severity.Error.class})
public class Expense{
	ExpenseType expenseType;
	String otherExpenseDescription;
	@Min(value = 0, message = "Amount must be greater than zero", groups=ExpenseChecks.class, payload={Severity.Error.class})
    Double amount;
	@ValidDateRange(groups=ExpenseChecks.class, payload={Severity.Error.class})
	DateRange dateRange;
   
	public ExpenseType getExpenseType() {
		return expenseType;
	}
	public void setExpenseType(ExpenseType expenseType) {
		this.expenseType = expenseType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getOtherExpenseDescription() {
		return otherExpenseDescription;
	}
	public void setOtherExpenseDescription(String otherExpenseDescription) {
		this.otherExpenseDescription = otherExpenseDescription;
	}
	public DateRange getDateRange() {
		return dateRange;
	}
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}

}
