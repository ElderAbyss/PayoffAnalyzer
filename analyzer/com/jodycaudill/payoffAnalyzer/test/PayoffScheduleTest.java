package analyzer.com.jodycaudill.payoffAnalyzer.test;

import analyzer.com.jodycaudill.payoffAnalyzer.Debt;
import analyzer.com.jodycaudill.payoffAnalyzer.PayoffSchedule;
import analyzer.com.jodycaudill.payoffAnalyzer.ScheduledDebt;
import org.junit.Assert;
import org.junit.Test;

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

public class PayoffScheduleTest {
    /**
     * test adding debt to end of list in schedule
     * @throws Exception
     */
    @Test
    public void addDebtToList() throws Exception {
        Debt testDebt = new Debt("test",1000.00,10,80);
        PayoffSchedule testSchedule = new PayoffSchedule();
        testSchedule.addDebtToList(testDebt);

        ScheduledDebt temp = testSchedule.getDebtList().stream().filter(d -> d.getName().equals(testDebt.getName())).findFirst().orElse(null);
        Assert.assertTrue(temp != null);
        Assert.assertTrue(temp.getName().equals(testDebt.getName()));
    }

    /**
     * test adding debt to given index in list of schedule
     * @throws Exception
     */
    @Test
    public void addDebtToList1() throws Exception {
        Debt testDebt1 = new Debt("test1",1000.00,10,80);
        Debt testDebt2 = new Debt("test2",1000.00,10,80);
        Debt testDebt3 = new Debt("test3",1000.00,10,80);
        PayoffSchedule testSchedule = new PayoffSchedule();

        testSchedule.addDebtToList(testDebt1);
        testSchedule.addDebtToList(testDebt2);
        testSchedule.addDebtToList(testDebt3);

        testSchedule.addDebtToList(0,testSchedule.getDebtList().remove(2));
        Assert.assertTrue(testSchedule.getDebtList().get(0).getName().equals(testDebt3.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(1).getName().equals(testDebt1.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(2).getName().equals(testDebt2.getName()));
    }

    /**
     * test removing debt from list in schedule
     * @throws Exception
     */
    @Test
    public void removeDebtFromList() throws Exception {
        Debt testDebt1 = new Debt("test1",1000.00,10,80);
        Debt testDebt2 = new Debt("test2",1000.00,10,80);
        Debt testDebt3 = new Debt("test3",1000.00,10,80);
        PayoffSchedule testSchedule = new PayoffSchedule();

        testSchedule.addDebtToList(testDebt1);
        testSchedule.addDebtToList(testDebt2);
        testSchedule.addDebtToList(testDebt3);

        testSchedule.removeDebtFromList(testDebt2);
        Assert.assertEquals(2,testSchedule.getDebtList().size());
        Assert.assertTrue(testSchedule.getDebtList().get(0).getName().equals(testDebt1.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(1).getName().equals(testDebt3.getName()));
    }

    /**
     * test setting the sequence directly in the list
     * @throws Exception
     */
    @Test
    public void setDebtSequence() throws Exception {
        Debt testDebt1 = new Debt("test1",1000.00,10,80);
        Debt testDebt2 = new Debt("test2",1000.00,10,80);
        Debt testDebt3 = new Debt("test3",1000.00,10,80);
        PayoffSchedule testSchedule = new PayoffSchedule();

        testSchedule.addDebtToList(testDebt1);
        testSchedule.addDebtToList(testDebt2);
        testSchedule.addDebtToList(testDebt3);

        testSchedule.setDebtSequence(1,0);
        Assert.assertTrue(testSchedule.getDebtList().get(0).getName().equals(testDebt2.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(1).getName().equals(testDebt1.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(2).getName().equals(testDebt3.getName()));

        testSchedule.setDebtSequence(2,1);
        Assert.assertTrue(testSchedule.getDebtList().get(0).getName().equals(testDebt2.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(1).getName().equals(testDebt3.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(2).getName().equals(testDebt1.getName()));
    }

    /**
     * test forward and revers sequencing of debt list by debt amount
     * @throws Exception
     */
    @Test
    public void sequenceDebtListByAmount() throws Exception {
        Debt testDebt1 = new Debt("test1",1000.00,10,80);
        Debt testDebt2 = new Debt("test2",1100.00,10,80);
        Debt testDebt3 = new Debt("test3",900.00,10,80);
        PayoffSchedule testSchedule = new PayoffSchedule();

        testSchedule.addDebtToList(testDebt1);
        testSchedule.addDebtToList(testDebt2);
        testSchedule.addDebtToList(testDebt3);

        testSchedule.sequenceDebtListByAmount();
        Assert.assertTrue(testSchedule.getDebtList().get(0).getName().equals(testDebt3.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(1).getName().equals(testDebt1.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(2).getName().equals(testDebt2.getName()));

        testSchedule.sequenceDebtListByAmountReverse();
        Assert.assertTrue(testSchedule.getDebtList().get(0).getName().equals(testDebt2.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(1).getName().equals(testDebt1.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(2).getName().equals(testDebt3.getName()));
    }

    /**
     * test forward and revers sequencing of debt list by debt interest rate
     * @throws Exception
     */
    @Test
    public void sequenceDebtListByInterestRate() throws Exception {
        Debt testDebt1 = new Debt("test1",1000.00,10,80);
        Debt testDebt2 = new Debt("test2",1000.00,8,80);
        Debt testDebt3 = new Debt("test3",1000.00,12,80);
        PayoffSchedule testSchedule = new PayoffSchedule();

        testSchedule.addDebtToList(testDebt1);
        testSchedule.addDebtToList(testDebt2);
        testSchedule.addDebtToList(testDebt3);

        testSchedule.sequenceDebtListByInterestRate();
        Assert.assertTrue(testSchedule.getDebtList().get(0).getName().equals(testDebt2.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(1).getName().equals(testDebt1.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(2).getName().equals(testDebt3.getName()));

        testSchedule.sequenceDebtListByInterestRateReverse();
        Assert.assertTrue(testSchedule.getDebtList().get(0).getName().equals(testDebt3.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(1).getName().equals(testDebt1.getName()));
        Assert.assertTrue(testSchedule.getDebtList().get(2).getName().equals(testDebt2.getName()));
    }

    /**
     * test the schedule calcuations for debt list
     * @throws Exception
     */
    @Test
    public void calculateSchedule() throws Exception {
        Debt testDebt1 = new Debt("test1",1000.00,10,80);
        Debt testDebt2 = new Debt("test2",1000.00,8,80);
        Debt testDebt3 = new Debt("test3",1000.00,12,80);
        PayoffSchedule testSchedule = new PayoffSchedule();

        testSchedule.setMonthlyPayDownAmount(500.00);
        testSchedule.addDebtToList(testDebt1);
        testSchedule.addDebtToList(testDebt2);
        testSchedule.addDebtToList(testDebt3);

        Assert.assertEquals(740.00, testSchedule.getMonthlyPayoffBudget(),0.005);

        testSchedule.calculateSchedule();

        Assert.assertTrue(testSchedule.getPayoffDate().equals(LocalDate.now().plusMonths(4)));
        Assert.assertTrue(testSchedule.getDebtList().get(0).isPaid());
        Assert.assertTrue(testSchedule.getDebtList().get(1).isPaid());
        Assert.assertTrue(testSchedule.getDebtList().get(2).isPaid());

        System.out.println(testSchedule.getPayoffDate().toString());
    }

}