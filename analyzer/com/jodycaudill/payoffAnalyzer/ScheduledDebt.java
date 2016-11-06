package analyzer.com.jodycaudill.payoffAnalyzer;

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

public class ScheduledDebt extends Debt {

    private int sequence;           //used by schedule to track payoff priority
    private LocalDate planPayoffDate;   //used by schedule to track payoff date of this debt

    /**
   * Constructor Section
    */

    public ScheduledDebt(String name, double initialAmount, double annualPercentageRate, double minPayment, int sequence) {
        super(name, initialAmount, annualPercentageRate, minPayment);
        this.sequence = sequence;
        this.planPayoffDate = this.payoffDate;
    }

    /*
    Getters and Setters
     */

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public LocalDate getPlanPayoffDate() {
        return planPayoffDate;
    }

    public void setPlanPayoffDate(LocalDate planPayoffDate) {
        this.planPayoffDate = planPayoffDate;
    }

    /*
    Methods
     */

}
