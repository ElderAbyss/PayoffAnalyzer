package analyzer.com.jodycaudill.payoffAnalyzer.test;

import analyzer.com.jodycaudill.payoffAnalyzer.models.Debt;
import org.junit.Assert;
import org.junit.Test;

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

public class DebtTest {

    /**
     * Test the isPaid predicate
      * @throws Exception
     */
    @Test
    public void isPaid() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        Assert.assertFalse(testDebt.isPaid());
        testDebt.makePayment(testDebt.getAmount());
        Assert.assertTrue(testDebt.isPaid());
    }

    /**
     * Test the make payment method
     * @throws Exception
     */
    @org.junit.Test
    public void makePayment() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        Assert.assertEquals("initial Balance Test", 1000.00,testDebt.getInitialAmount(),0.01);
        Assert.assertEquals("Balance Test", 1000.00,testDebt.getAmount(),0.01);
        testDebt.makePayment(100.00);
        Assert.assertEquals("Balance Test", 900.00,testDebt.getAmount(),0.01);
    }

    /**
     * test debt interest calculations in the compoundInterest method
     * @throws Exception
     */
    @org.junit.Test
    public void compoundInterest() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        Assert.assertEquals("Interest incurred before calculation",0.00,testDebt.getIntrestIncurred(),0.005);
        testDebt.compoundInterest(30);
        Assert.assertEquals("Interest incurred after calculation",8.22,testDebt.getIntrestIncurred(),0.005);
        Assert.assertEquals("Amount after interest calculation",1008.22,testDebt.getAmount(),0.005);
    }

    /**
     * test daily rete calculations
     * @throws Exception
     */
    @org.junit.Test
    public void calculateDailyPercentageRate() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        Assert.assertEquals("Daily interest Test",0.0274,testDebt.getDailyInterestRate(),0.00005);
    }

    /**
     * test reconcile method to calculate interest for the time period and apply payment to debt item
     * @throws Exception
     */
    @org.junit.Test
    public void reconcileMonth() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        testDebt.reconcileMonth(100.00,30);
        Assert.assertEquals("Interest incurred after calculation",8.22,testDebt.getIntrestIncurred(),0.005);
        Assert.assertEquals("Amount after interest calculation",908.22,testDebt.getAmount(),0.005);
    }

    /**
     * test the payoff date calculation
     * @throws Exception
     */
    @org.junit.Test
    public void calculatePayoffDate() throws Exception {
        //Debt testDebt = new Debt("test",1000.00,10,80);
        //// TODO: 11/6/2016
    }

}