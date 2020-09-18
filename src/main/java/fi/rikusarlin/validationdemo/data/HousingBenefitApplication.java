package fi.rikusarlin.validationdemo.data;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import fi.rikusarlin.validationdemo.DateRange;
import fi.rikusarlin.validationdemo.validation.ValidDateRange;

public class HousingBenefitApplication {
	@Valid
	List<HouseholdMember> householdMembers = new ArrayList<HouseholdMember>();
	@Valid
	List<Income> incomes = new ArrayList<Income>();
	@Valid
	List<Expense> housingExpenses = new ArrayList<Expense>();
	@ValidDateRange
	DateRange dateRange;
	
	public List<HouseholdMember> getHouseholdMembers() {
		return householdMembers;
	}
	public void setHouseholdMembers(List<HouseholdMember> householdMembers) {
		this.householdMembers = householdMembers;
	}
	public void addHouseholdMember(HouseholdMember hm) {
		hm.setParentDateRange(this.dateRange);
		this.householdMembers.add(hm);		
	}
	public List<Income> getIncomes() {
		return incomes;
	}
	public void setIncomes(List<Income> incomes) {
		this.incomes = incomes;
	}
	public void addIncome(Income income) {
		income.setParentDateRange(this.dateRange);
		this.incomes.add(income);		
	}
	public List<Expense> getHousingExpenses() {
		return housingExpenses;
	}
	public void setHousingExpenses(List<Expense> housingExpenses) {
		this.housingExpenses = housingExpenses;
	}
	public void addExpense(Expense expense) {
		expense.setParentDateRange(this.dateRange);
		this.housingExpenses.add(expense);		
	}
	public DateRange getDateRange() {
		return dateRange;
	}
	public void setDateRange(DateRange dateRange) {
		this.dateRange = dateRange;
		// Need to propagate change to date range -controlled sub-objects
		for(Expense expense:housingExpenses) {
			expense.setParentDateRange(dateRange);
		}
		for(Income income:incomes) {
			income.setParentDateRange(dateRange);
		}
		for(HouseholdMember hm:householdMembers) {
			hm.setParentDateRange(dateRange);
		}
	}
	
}
