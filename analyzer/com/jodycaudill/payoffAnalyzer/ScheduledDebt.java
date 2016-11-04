package analyzer.com.jodycaudill.payoffAnalyzer;

import java.time.LocalDate;

/**
 * Created by Jody_Admin on 10/30/2016.
 */
public class ScheduledDebt extends Debt {

    private int sequence;
    private LocalDate planPayoffDate;

    /*
    Constructor Section
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
