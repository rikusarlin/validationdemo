package fi.rikusarlin.validationdemo.data;

import javax.validation.constraints.Min;

import fi.rikusarlin.validationdemo.ExpenseType;
import fi.rikusarlin.validationdemo.validation.NotNullIfAnotherFieldHasValue;

@NotNullIfAnotherFieldHasValue(
	    fieldName = "expenseType",
	    fieldValue = "OTHER",
	    dependFieldName = "otherExpenseDescription")
public class Expense extends ControlledDateRangeEntity {
	ExpenseType expenseType;
	String otherExpenseDescription;
	@Min(value = 0, message = "Amount must be greater than zero")
    Double amount;
    
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
}
