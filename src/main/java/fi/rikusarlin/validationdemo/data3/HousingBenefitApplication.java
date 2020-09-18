package fi.rikusarlin.validationdemo.data3;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.validation.AllChecks;
import fi.rikusarlin.validationdemo.validation.ApplicationChecks;
import fi.rikusarlin.validationdemo.validation.ExpenseChecks;
import fi.rikusarlin.validationdemo.validation.HouseholdChecks;
import fi.rikusarlin.validationdemo.validation.IncomeChecks;
import fi.rikusarlin.validationdemo.validation.Severity;
import fi.rikusarlin.validationdemo.validation.ValidApplicationDataRanges;
import fi.rikusarlin.validationdemo.validation.ValidDateRange;

@ValidApplicationDataRanges(
		groups={IncomeChecks.class, ExpenseChecks.class, HouseholdChecks.class, AllChecks.class}, 
		payload={Severity.Info.class})
public class HousingBenefitApplication{
	@Valid
	List<HouseholdMember> householdMembers = new ArrayList<HouseholdMember>();
	@Valid
	List<Income> incomes = new ArrayList<Income>();
	@Valid
	List<Expense> housingExpenses = new ArrayList<Expense>();
	@ValidDateRange(groups=ApplicationChecks.class, payload={Severity.Error.class})
	DateRange dateRange;
 
	public DateRange getDateRange() {
		return dateRange;
	}
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
	}
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
}
