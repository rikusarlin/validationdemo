package fi.rikusarlin.validationdemo.data;

import fi.rikusarlin.validationdemo.validation.ValidDateRange;
import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.validation.OverlappingDateRanges;

@OverlappingDateRanges(dateRange1 = "dateRange", dateRange2="parentDateRange")
public class ControlledDateRangeEntity {
	@ValidDateRange
	protected DateRange dateRange;
	protected DateRange parentDateRange;
	
	public DateRange getDateRange() {
		return dateRange;
	}
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
	public DateRange getParentDateRange() {
		return parentDateRange;
	}
	public void setParentDateRange(DateRange parentDateRange) {
		this.parentDateRange = parentDateRange;
	}
}
