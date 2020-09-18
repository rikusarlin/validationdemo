package fi.rikusarlin.validationdemo.data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import fi.rikusarlin.validationdemo.validation.ValidPersonNumber;

public class HouseholdMember extends ControlledDateRangeEntity {
	@NotNull
	@Size(min = 11, max = 11)
	@ValidPersonNumber
	String personNumber;
	
	public String getPersonNumber() {
		return personNumber;
	}
	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}
}
