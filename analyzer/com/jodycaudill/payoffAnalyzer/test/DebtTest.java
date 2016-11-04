package analyzer.com.jodycaudill.payoffAnalyzer.test;

import analyzer.com.jodycaudill.payoffAnalyzer.Debt;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Jody_Admin on 11/4/2016.
 */
public class DebtTest {
    @Test
    public void isPaid() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        Assert.assertFalse(testDebt.isPaid());
        testDebt.makePayment(testDebt.getAmount());
        Assert.assertTrue(testDebt.isPaid());
    }

    @org.junit.Test
    public void makePayment() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        Assert.assertEquals("initial Balance Test", 1000.00,testDebt.getInitialAmount(),0.01);
        Assert.assertEquals("Balance Test", 1000.00,testDebt.getAmount(),0.01);
        testDebt.makePayment(100.00);
        Assert.assertEquals("Balance Test", 900.00,testDebt.getAmount(),0.01);
    }

    @org.junit.Test
    public void compoundInterest() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        Assert.assertEquals("Interest incurred before calculation",0.00,testDebt.getIntrestIncurred(),0.005);
        testDebt.compoundInterest(30);
        Assert.assertEquals("Interest incurred after calculation",8.22,testDebt.getIntrestIncurred(),0.005);
        Assert.assertEquals("Amount after interest calculation",1008.22,testDebt.getAmount(),0.005);
    }

    @org.junit.Test
    public void calculateDailyPercentageRate() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        Assert.assertEquals("Daily interest Test",0.0274,testDebt.getDailyInterestRate(),0.00005);
    }

    @org.junit.Test
    public void reconcileMonth() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        testDebt.reconcileMonth(100.00,30);
        Assert.assertEquals("Interest incurred after calculation",8.22,testDebt.getIntrestIncurred(),0.005);
        Assert.assertEquals("Amount after interest calculation",908.22,testDebt.getAmount(),0.005);
    }

    @org.junit.Test
    public void calculatePayoffDate() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
    }

}