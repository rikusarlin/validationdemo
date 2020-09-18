package fi.rikusarlin.validationdemo.data;

import javax.validation.constraints.Min;

import fi.rikusarlin.validationdemo.IncomeType;
import fi.rikusarlin.validationdemo.validation.NotNullIfAnotherFieldHasValue;

@NotNullIfAnotherFieldHasValue(
	    fieldName = "incomeType",
	    fieldValue = "OTHER",
	    dependFieldName = "otherIncomeDescription")
public class Income extends ControlledDateRangeEntity {
	IncomeType incomeType;
	@Min(value = 0, message = "Amount must be greater than zero")
    Double amount;
    String otherIncomeDescription;
    
	public IncomeType getIncomeType() {
		return incomeType;
	}
	public void setIncomeType(IncomeType incomeType) {
		this.incomeType = incomeType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getOtherIncomeDescription() {
		return otherIncomeDescription;
	}
	public void setOtherIncomeDescription(String otherIncomeDescription) {
		this.otherIncomeDescription = otherIncomeDescription;
	}
}
