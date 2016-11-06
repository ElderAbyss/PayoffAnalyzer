package analyzer.com.jodycaudill.payoffAnalyzer;

import java.time.LocalDate;
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
    private LinkedList<ScheduledDebt> debtList;
    private double totalInterestPaid;
    private double totalCost;
    private LocalDate payoffDate;
    private HashMap<LocalDate, String> scheduleInvoices;

    /*
    Constructor section
     */
    public PayoffSchedule() {
        this.debtList = new LinkedList<>();
        this.scheduleInvoices = new HashMap<>();
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
        for (ScheduledDebt debt: this.debtList) {
            budget += debt.getMinPayment();
        }
        return budget;
    }

    public LinkedList<ScheduledDebt> getDebtList() {
        return debtList;
    }

    public void setDebtList(LinkedList<ScheduledDebt> debtList) {
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
        ScheduledDebt newDebt = new ScheduledDebt(debt.getName(), debt.getInitialAmount(), debt.getAnnualPercentageRate(), debt.getMinPayment(), this.debtList.size());
        this.debtList.add(newDebt);
    }

    public void addDebtToList(int sequence, ScheduledDebt debt) {
        this.debtList.add(sequence, debt);
    }

    public void removeDebtFromList(Debt debt) {
        ScheduledDebt result = this.debtList.stream().filter(x -> x.getName().equals(debt.getName()))
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
        Collections.sort(this.debtList, new Comparator<ScheduledDebt>() {
            @Override
            public int compare(ScheduledDebt o1, ScheduledDebt o2) {
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
        Collections.sort(this.debtList, new Comparator<ScheduledDebt>() {
            @Override
            public int compare(ScheduledDebt o1, ScheduledDebt o2) {
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

    public void saveSchedule() {
        System.out.println("PayoffSchedule.saveSchedule() is not built yet");
        //// TODO: 11/6/2016
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
        while (!debtIsPaid()) {
            thisMonthsBudget = this.getMonthlyPayoffBudget();
            thisMonthsBudget = payMinPaymentOnUnpaidDebts(thisMonthsBudget);
            payBudgetBalanceOnHighestPriorityUnpaidDebt(thisMonthsBudget);
            ageSchedule();
        }
        printSchedule();
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
     * Pay the blanace of the budget after all minums have been paid to the highest priority debt
     * If any is left after paying the highest debt off then move the budget balance into a recursive call
     * to pay the balance on the next highest priority debt
     * @param thisMonthsBuget budget balance to make payments with
     */
    private void payBudgetBalanceOnHighestPriorityUnpaidDebt(double thisMonthsBuget) {
        ScheduledDebt priorityDebt = null;
        ScheduledDebt currentDebt;
        ListIterator<ScheduledDebt> debtItr = this.debtList.listIterator();
        while (debtItr.hasNext()) {
            currentDebt = debtItr.next();
            if (!currentDebt.isPaid()) {
                if (priorityDebt == null) {
                    priorityDebt = currentDebt;
                } else if (currentDebt.getSequence() > priorityDebt.getSequence()) {
                    priorityDebt = currentDebt;
                }
            }
        }
        if (priorityDebt != null) {
            double payment = Math.min(priorityDebt.getAmount(), thisMonthsBuget);
            priorityDebt.makePayment(payment);
            thisMonthsBuget -= payment;
            if (thisMonthsBuget > 0.0) {
                payBudgetBalanceOnHighestPriorityUnpaidDebt(thisMonthsBuget);
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
        ScheduledDebt currentDebt;
        ListIterator<ScheduledDebt> debtItr = this.debtList.listIterator();
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
        ListIterator<ScheduledDebt> debtItr = this.debtList.listIterator();
        while (debtItr.hasNext()) {
            if (!debtItr.next().isPaid()) {
                return false;
            }
        }
        return true;
    }

    public void printSchedule(){
        for (LocalDate date: this.scheduleInvoices.keySet()){
            System.out.println(date.toString());
            System.out.println(this.scheduleInvoices.get(date));
        }
    }

}
