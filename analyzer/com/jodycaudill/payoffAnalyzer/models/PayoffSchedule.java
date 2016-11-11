package analyzer.com.jodycaudill.payoffAnalyzer.models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Payoff Analyzer
 * CSC 318 Final Project
 * Jody  Caudill  -  https://github.com/ElderAbyss/PayoffAnalyzer
 *
 * Copyright (c) 2016 Jody Caudill
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the “Software”), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

public class PayoffSchedule {
    private String name;
    private String description;
    private double monthlyPayDownAmount;
    private ArrayList<Debt> debtList;
    private double totalInterestPaid;
    private double totalCost;
    private LocalDate payoffDate;
    private LinkedHashMap<LocalDate, String> scheduleInvoices;

    /*
    Constructor section
     */
    public PayoffSchedule() {
        this.debtList = new ArrayList<>();
        this.scheduleInvoices = new LinkedHashMap<>();
        this.payoffDate = LocalDate.now();
    }

    /*
    Getters and Setters Section
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMonthlyPayDownAmount() {
        return monthlyPayDownAmount;
    }

    public void setMonthlyPayDownAmount(double monthlyPayDownAmount) {
        this.monthlyPayDownAmount = monthlyPayDownAmount;
    }

    public double getMonthlyPayoffBudget() {
        double budget = this.monthlyPayDownAmount;
        for (Debt debt: this.debtList) {
            budget += debt.getMinPayment();
        }
        return budget;
    }

    public double getMonthlyMinPayment() {
        double budget = 0.0;
        for (Debt debt: this.debtList) {
            budget += debt.getMinPayment();
        }
        return budget;
    }

    public ArrayList<Debt> getDebtList() {
        return debtList;
    }

    public void setDebtList(ArrayList<Debt> debtList) {
        this.debtList = debtList;
    }

    public double getTotalInterestPaid() {
        return totalInterestPaid;
    }

    public void setTotalInterestPaid(double totalInterestPaid) {
        this.totalInterestPaid = totalInterestPaid;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public LocalDate getPayoffDate() {
        return payoffDate;
    }

    public void setPayoffDate(LocalDate payoffDate) {
        this.payoffDate = payoffDate;
    }

    /*
    Methods Section
     */
    public void addDebtToList(Debt debt) {
        this.debtList.add(debt);
    }

    public void addDebtToList(int sequence, Debt debt) {
        this.debtList.add(sequence, debt);
    }

    public void removeDebtFromList(Debt debt) {
        Debt result = this.debtList.stream().filter(x -> x.getName().equals(debt.getName()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            debtList.remove(result);
        }
    }

    public void setDebtSequence(int oldSequence, int newSequence) {
        try {
            this.debtList.add(newSequence, this.debtList.remove(oldSequence));
        } catch (NoSuchElementException e) {
            System.out.println("No matches element was found to move sequence.");
            e.printStackTrace();
        }
    }

    public void sequenceDebtListByAmount() {
        Collections.sort(this.debtList, new Comparator<Debt>() {
            @Override
            public int compare(Debt o1, Debt o2) {
                if (o1.getAmount() > o2.getAmount()) {
                    return 1;
                } else if (o1.getAmount() < o2.getAmount()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    public void sequenceDebtListByAmountReverse() {
        this.sequenceDebtListByAmount();
        Collections.reverse(this.debtList);
    }

    public void sequenceDebtListByInterestRate() {
        Collections.sort(this.debtList, new Comparator<Debt>() {
            @Override
            public int compare(Debt o1, Debt o2) {
                if (o1.getAnnualPercentageRate() > o2.getAnnualPercentageRate()) {
                    return 1;
                } else if (o1.getAnnualPercentageRate() < o2.getAnnualPercentageRate()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    public void sequenceDebtListByInterestRateReverse() {
        this.sequenceDebtListByInterestRate();
        Collections.reverse(this.debtList);

    }

    public void saveSchedule(String filename) throws SecurityException, IOException{
        String file = filename;
        file = "Schedules/".concat(file).concat(".txt");
        File saveFile = new File("Schedules/");

        if(!saveFile.exists()){
                saveFile.mkdir();
        }
        saveFile = new File(file);
        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }
        FileWriter writer = new FileWriter(saveFile);
        BufferedWriter buffer = new BufferedWriter(writer);
        buffer.write(this.getScheduleSaveText(filename));
        buffer.close();
    }

    private String getScheduleSaveText(String name) {
        String marker = "************************************************************\n";
        StringBuilder report = new StringBuilder();
        report.append(name);
        report.append("\n\n");
        report.append("Debts on Schedule:\n");
        for(Debt debt: this.getDebtList()){
            report.append(debt.toString());
            report.append("\n");
        }
        report.append("\n\n");
        report.append(String.format("Minimum Payment Total:      $%.2f\t\t\tPaid Date:                  %s\n", this.getMonthlyMinPayment(), this.getPayoffDate().format(DateTimeFormatter.ofPattern("MMM - yy"))));
        report.append(String.format("Additional Paydown Budget:  $%.2f\t\t\tInterest Paid:              $%.2f\n", this.getMonthlyPayDownAmount(),this.getTotalScheduleInterest()));
        report.append(String.format("Monthly Budget:             $%.2f\t\t\tCost:                       $%.2f", this.getMonthlyPayoffBudget(),this.getTotalCost()));
        report.append("\n\n");
        report.append("\n\n");
        report.append(marker);
        report.append(marker);
        report.append(this.getScheduleDetails());
        report.append(marker);
        report.append(marker);
        return report.toString();
    }

    public void loadSchedule() {
        System.out.println("PayoffSchedule.loadSchedule() is not built yet");
        //// TODO: 11/6/2016
    }

    /**
     * Primary function of calculating the debt payoff schedule by processing payments and supplying
     * date management for interests of time of schedule and collects the account invoices to build the
     * schedule for payoff with results.
     */
    public void calculateSchedule() {
        double thisMonthsBudget;
        resetScheduleForRecalculation();
        while (!debtIsPaid()) {
            thisMonthsBudget = this.getMonthlyPayoffBudget();
            thisMonthsBudget = payMinPaymentOnUnpaidDebts(thisMonthsBudget);
            payBudgetBalanceOnHighestPriorityUnpaidDebt(thisMonthsBudget);
            ageSchedule();
        }
        //printSchedule();        //// TODO: 11/6/2016  remove this or comment out.. for debugging only
    }

    /**
     * re-initializes objects to prepare for a schedule calculation
     * reset date to current month, clear the exisitn invoice schedule for payments
     * and reset all the debts to original status
     */
    private void resetScheduleForRecalculation() {
        this.setPayoffDate( LocalDate.now());
        this.scheduleInvoices.clear();
        for(Debt debt : getDebtList()){
            debt.setAmount(debt.getInitialAmount());
            debt.setCost(0.0);
            debt.setIntrestIncurred(0.0);
        }
    }

    /**
     * During schedule calculation this method collects the invoices and will
     * Age the schedule by one month
     */
    private void ageSchedule() {
        String invoice = this.getDebtList().stream()
                .map(x -> x.getinvoice())
                .collect(Collectors.joining());
        scheduleInvoices.put(this.getPayoffDate(),invoice);
        if(!this.debtIsPaid()) {
            setPayoffDate(getPayoffDate().plusMonths(1));
        }
    }

    /**
     * Pay the balance of the budget after all minums have been paid to the highest priority debt
     * If any is left after paying the highest debt off then move the budget balance into a recursive call
     * to pay the balance on the next highest priority debt
     * @param thisMonthsBudget budget balance to make payments with
     */
    private void payBudgetBalanceOnHighestPriorityUnpaidDebt(double thisMonthsBudget) {
        Debt priorityDebt = null;
        Debt currentDebt;
        ListIterator<Debt> debtItr = this.debtList.listIterator();
        while (debtItr.hasNext()) {
            currentDebt = debtItr.next();
            if (!currentDebt.isPaid()) {
                if (priorityDebt == null) {
                    priorityDebt = currentDebt;
                }
            }
        }
        if (priorityDebt != null) {
            double payment = Math.min(priorityDebt.getAmount(), thisMonthsBudget);
            priorityDebt.makePayment(payment);
            thisMonthsBudget -= payment;
            if (thisMonthsBudget > 0.0) {
                payBudgetBalanceOnHighestPriorityUnpaidDebt(thisMonthsBudget);
            }
        }
    }

    /**
     * Pay the minimum month payment on each of the debt belonging to the schedule that are not
     * already paided off
     * @param thisMonthsBudget amount of monthly budget to work with making payments
     * @return balance of the monthly budget after all minimum payments have been made
     */
    private double payMinPaymentOnUnpaidDebts(double thisMonthsBudget) {
        double payment;
        Debt currentDebt;
        ListIterator<Debt> debtItr = this.debtList.listIterator();
        while (debtItr.hasNext()) {
            currentDebt = debtItr.next();
            if (!currentDebt.isPaid() && thisMonthsBudget > 0.0) {
                payment = Math.min(currentDebt.getAmount(), currentDebt.getMinPayment());
                if (payment <= thisMonthsBudget) {
                    thisMonthsBudget -= payment;
                    currentDebt.reconcileMonth(payment, this.getPayoffDate().getMonth().length(this.getPayoffDate().isLeapYear()));
                }
            }
        }
        return thisMonthsBudget;
    }

    /**
     * predicate to determine if all the debts belonging to the schedule are paid in full
     * @return
     */
    private boolean debtIsPaid() {
        ListIterator<Debt> debtItr = this.debtList.listIterator();
        while (debtItr.hasNext()) {
            if (!debtItr.next().isPaid()) {
                return false;
            }
        }
        return true;
    }

    public String getScheduleDetails(){
        StringBuilder details = new StringBuilder();
        for (LocalDate date: this.scheduleInvoices.keySet()) {
            details.append(date.toString());
            details.append("\n");
            details.append(this.scheduleInvoices.get(date));
            details.append("********************\n");
        }
        return details.toString();
    }

    public void printSchedule(){
        for (LocalDate date: this.scheduleInvoices.keySet()){
            System.out.println(date.toString());
            System.out.println(this.scheduleInvoices.get(date));
        }
    }

    public double getTotalScheduleInterest(){
        double interest = 0.0;
        for( Debt debt : this.getDebtList()){
            interest += debt.getIntrestIncurred();
        }
        return interest;
    }

    public  double getTotalScheduleCost(){
        double cost = 0.0;
        for (Debt debt : this.getDebtList()){
            cost += debt.getCost();
        }
        return cost;
    }

}
