package analyzer.com.jodycaudill.payoffAnalyzer;

import java.time.LocalDate;

/**
 * Created by Jody_Admin on 10/22/2016.
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

    public void reconcileMonth(double payment, int daysInMonth){
        compoundInterest(daysInMonth);
        makePayment(payment);
        //calculatePayoffDate();
    }

    public void calculatePayoffDate() {
        System.out.println("Debt.calculatePayoffDate() is not built yet");
    }

    public String getinvoice(){
        String invoice = "";
        if(this.getAmount() > 0.0 || this.getInvoidePayment() > 0.0){
            invoice = String.format("Account %s :: Interest incurred = $%.2f    A payment of $%.2f was made leaving a balance of %.2f %n",
                    this.getName(), this.getInvoiceInterest(), this.getInvoidePayment(), this.getAmount());
            if(this.getAmount() <= 0.0){
               invoice = invoice.concat(this.getName() + " has been PAID IN FULL...\n");
            }
            this.setInvoidePayment(0.0);
            this.setInvoiceInterest(0.0);
        }
        return invoice;
    }

}
