package analyzer.com.jodycaudill.payoffAnalyzer;

import java.time.LocalDate;
import java.util.*;

/**
 * Created by Jody_Admin on 10/30/2016.
 */
public class PayoffSchedule {
    private String name;
    private String description;
    private double monthlyPayDownAmount;
    private LinkedList<ScheduledDebt> debtList;
    private double totalInterestPaid;
    private double totalCost;
    private LocalDate payoffDate;

    /*
    Constructor section
     */
    public PayoffSchedule() {
        this.debtList = new LinkedList<>();
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
    }

    public void loadSchedule() {
        System.out.println("PayoffSchedule.loadSchedule() is not built yet");
    }

    public void calculateSchedule() {
        double thisMonthsBudget;
        while (!debtIsPaid()) {
            thisMonthsBudget = this.getMonthlyPayoffBudget();
            thisMonthsBudget = payMinPaymentOnUnpaidDebts(thisMonthsBudget);
            payBudgetBalanceOnHighestPriorityUnpaidDebt(thisMonthsBudget);
            ageSchedule();
        }

    }

    private void ageSchedule() {
        setPayoffDate(getPayoffDate().plusMonths(1));
        System.out.println(this.getPayoffDate());
    }

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

    private boolean debtIsPaid() {
        ListIterator<ScheduledDebt> debtItr = this.debtList.listIterator();
        while (debtItr.hasNext()) {
            if (!debtItr.next().isPaid()) {
                return false;
            }
        }
        return true;
    }

}
