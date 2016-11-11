package analyzer.com.jodycaudill.payoffAnalyzer.models;

import java.time.LocalDate;

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

public class Debt {

    private String name;
    private double amount;
    private double initialAmount;
    private double annualPercentageRate;
    private double dailyInterestRate;
    private double averageDailyBalance;
    private double minPayment;
    private double intrestIncurred;
    private double cost;
    private double invoiceInterest;
    private double invoidePayment;

    protected LocalDate payoffDate;     //this is protected due to child class needing to set this in its constructor

    private final int NUM_ANNUAL_INTEREST_DAYS = 365;

    /*
    Constructor Section
     */
    public Debt(String name, double initialAmount, double annualPercentageRate, double minPayment) {
        this.name = name;
        this.initialAmount = initialAmount;
        this.annualPercentageRate = annualPercentageRate;
        this.minPayment = minPayment;
        this.amount = this.initialAmount;
        this.dailyInterestRate = this.annualPercentageRate / NUM_ANNUAL_INTEREST_DAYS;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(double initialAmount) {
        this.initialAmount = initialAmount;
    }

    public double getAnnualPercentageRate() {
        return annualPercentageRate ;
    }

    public void setAnnualPercentageRate(double annualPercentageRate) {
        this.annualPercentageRate = annualPercentageRate;
        calculateDailyPercentageRate();
    }

    public double getAverageDailyBalance() {
        return averageDailyBalance;
    }

    private void setAverageDailyBalance(double averageDailyBalance) {
        this.averageDailyBalance = averageDailyBalance;
    }

    public double getMinPayment() {
        return minPayment;
    }

    public void setMinPayment(double minPayment) {
        this.minPayment = minPayment;
        calculatePayoffDate();
    }

    public double getIntrestIncurred() {
        return intrestIncurred;
    }

    public void setIntrestIncurred(double intrestIncurred) {
        this.intrestIncurred = intrestIncurred;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getPayoffDate() {
        return payoffDate;
    }

    private void setPayoffDate(LocalDate payoffDate) {
        this.payoffDate = payoffDate;
    }

    public double getDailyInterestRate() {
        return dailyInterestRate;
    }

    private void setDailyInterestRate(double dailyInterestRate) {
        this.dailyInterestRate = dailyInterestRate;
    }

    private double getInvoiceInterest() {
        return invoiceInterest;
    }

    private void setInvoiceInterest(double invoiceInterest) {
        this.invoiceInterest = invoiceInterest;
    }

    private double getInvoidePayment() {
        return invoidePayment;
    }

    private void setInvoidePayment(double invoidePayment) {
        this.invoidePayment = invoidePayment;
    }

    /*
    Methods Section
     */
    public boolean isPaid(){
        return this.getAmount() <= 0;
    }

    public void makePayment(double paymentAmount){
        setAmount(getAmount() - paymentAmount);
        setInvoidePayment(getInvoidePayment()+paymentAmount);
        setCost(getCost() + paymentAmount);
    }

    public void compoundInterest(int days){
        //using getAmount() in the interest calculation since the application doesn't forecast varying amounts on accounts,
        // such as additional charges or off cycle payments that could impact the daily balance.  If those features are
        // introduced the interest calculation below is the hook for that functionality
        this.setInvoiceInterest(getAmount() * getDailyInterestRate() * days / 100);
        addInterestIncurred(this.getInvoiceInterest());
        setAmount(getAmount() + this.getInvoiceInterest());
    }

    public void calculateDailyPercentageRate(){
        setDailyInterestRate(getAnnualPercentageRate() / NUM_ANNUAL_INTEREST_DAYS);
    }

    private void calculateAvgBalance(){
        System.out.println("Debt.calculateAvgBalance() is not built yet");
    }


    private void addInterestIncurred(double interestAmount){
        setIntrestIncurred(getIntrestIncurred() + interestAmount);
    }

    /**
     * used to reconcile a monthly transaction including payment and interest
     * @param payment the amount of the payment to process
     * @param daysInMonth  the number of days in the time period, usually the month, to calculate interest incurred
     */
    public void reconcileMonth(double payment, int daysInMonth){
        compoundInterest(daysInMonth);
        makePayment(payment);
        //calculatePayoffDate();
    }

    public void calculatePayoffDate() {
        System.out.println("Debt.calculatePayoffDate() is not built yet");
    }

    /**
     * used to return the oustanding payment and interest information since the last invoice. It is like the account statement.
     * calling this method reports the outstanding data and clears the counters for the next start accumulating for the next invoice
     * @return
     */
    public String getinvoice(){
        StringBuilder invoice = new StringBuilder();
        if(this.getAmount() > 0.0 || this.getInvoidePayment() > 0.0){
            invoice.append(String.format("Account %s :: Interest incurred = $%.2f    A payment of $%.2f was made leaving a balance of %.2f ",
                    this.getName(), this.getInvoiceInterest(), this.getInvoidePayment(), this.getAmount()));
            if(this.getAmount() <= 0.0){
               invoice.append("... PAID IN FULL ...");
            }
            this.setInvoidePayment(0.0);
            this.setInvoiceInterest(0.0);
        }
        invoice.append("\n");
        return invoice.toString();
    }

    @Override
    public String toString() {
        return String.format("%s with an initial amount of $%.2f and APR of %.1f%%\n", name, initialAmount, annualPercentageRate);
    }
}
