package fi.rikusarlin.validationdemo;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class DateRange {
	@DateTimeFormat(pattern="dd.MM.yyyy")
	LocalDate startDate;
	@DateTimeFormat(pattern="dd.MM.yyyy")
	LocalDate endDate;
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

}
