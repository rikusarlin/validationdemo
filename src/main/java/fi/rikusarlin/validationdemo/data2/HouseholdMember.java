package fi.rikusarlin.validationdemo.data2;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.validation.HouseholdChecks;
import fi.rikusarlin.validationdemo.validation.Severity;
import fi.rikusarlin.validationdemo.validation.ValidDateRange;
import fi.rikusarlin.validationdemo.validation.ValidPersonNumber;

public class HouseholdMember {
	@NotNull(groups=HouseholdChecks.class, payload={Severity.Error.class})
	@Size(min = 11, max = 11,groups=HouseholdChecks.class, payload={Severity.Error.class})
	@ValidPersonNumber(groups=HouseholdChecks.class, payload={Severity.Error.class})
	String personNumber;
	@ValidDateRange(groups=HouseholdChecks.class, payload={Severity.Error.class})
	DateRange dateRange;
	
	public String getPersonNumber() {
		return personNumber;
	}
	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}
	public DateRange getDateRange() {
		return dateRange;
	}
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
}
