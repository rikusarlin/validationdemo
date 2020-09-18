package fi.rikusarlin.validationdemo.data3;

import javax.validation.constraints.Min;

import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.IncomeType;
import fi.rikusarlin.validationdemo.validation.IncomeChecks;
import fi.rikusarlin.validationdemo.validation.NotNullIfAnotherFieldHasValue;
import fi.rikusarlin.validationdemo.validation.Severity;
import fi.rikusarlin.validationdemo.validation.ValidDateRange;

@NotNullIfAnotherFieldHasValue(
	    fieldName = "incomeType",
	    fieldValue = "OTHER",
	    dependFieldName = "otherIncomeDescription",
	    groups=IncomeChecks.class,
	    payload={Severity.Error.class})
public class Income {
	IncomeType incomeType;
	@Min(value = 0, message = "Amount must be greater than zero",groups=IncomeChecks.class,payload={Severity.Error.class})
    Double amount;
    String otherIncomeDescription;
	@ValidDateRange(groups=IncomeChecks.class, payload={Severity.Error.class})
	DateRange dateRange;

	public DateRange getDateRange() {
		return dateRange;
	}
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
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
