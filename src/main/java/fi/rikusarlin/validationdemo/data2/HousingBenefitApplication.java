package fi.rikusarlin.validationdemo.data2;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.validation.ApplicationChecks;
import fi.rikusarlin.validationdemo.validation.ExpenseChecks;
import fi.rikusarlin.validationdemo.validation.HouseholdChecks;
import fi.rikusarlin.validationdemo.validation.IncomeChecks;
import fi.rikusarlin.validationdemo.validation.Severity;
import fi.rikusarlin.validationdemo.validation.SubCollectionOverlappingDateRange;
import fi.rikusarlin.validationdemo.validation.ValidDateRange;

@SubCollectionOverlappingDateRange.List({
  @SubCollectionOverlappingDateRange(dateRangeFieldName = "dateRange", collectionName = "householdMembers", collectionDateRangeFieldName = "dateRange", groups= {HouseholdChecks.class}, payload={Severity.Info.class}),
  @SubCollectionOverlappingDateRange(dateRangeFieldName = "dateRange", collectionName = "incomes", collectionDateRangeFieldName = "dateRange", groups= {IncomeChecks.class}, payload={Severity.Info.class}),
  @SubCollectionOverlappingDateRange(dateRangeFieldName = "dateRange", collectionName = "housingExpenses", collectionDateRangeFieldName = "dateRange", groups= {ExpenseChecks.class}, payload={Severity.Info.class})
})
public class HousingBenefitApplication {
	@Valid
	List<HouseholdMember> householdMembers = new ArrayList<HouseholdMember>();
	@Valid
	List<Income> incomes = new ArrayList<Income>();
	@Valid
	List<Expense> housingExpenses = new ArrayList<Expense>();
	@ValidDateRange(groups=ApplicationChecks.class, payload={Severity.Error.class})
	DateRange dateRange;
	
	public List<HouseholdMember> getHouseholdMembers() {
		return householdMembers;
	}
	public void setHouseholdMembers(List<HouseholdMember> householdMembers) {
		this.householdMembers = householdMembers;
	}
	public void addHouseholdMember(HouseholdMember hm) {
		this.householdMembers.add(hm);		
	}
	public List<Income> getIncomes() {
		return incomes;
	}
	public void setIncomes(List<Income> incomes) {
		this.incomes = incomes;
	}
	public void addIncome(Income income) {
		this.incomes.add(income);		
	}
	public List<Expense> getHousingExpenses() {
		return housingExpenses;
	}
	public void setHousingExpenses(List<Expense> housingExpenses) {
		this.housingExpenses = housingExpenses;
	}
	public void addExpense(Expense expense) {
		this.housingExpenses.add(expense);		
	}
	public DateRange getDateRange() {
		return dateRange;
	}
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
	
}
