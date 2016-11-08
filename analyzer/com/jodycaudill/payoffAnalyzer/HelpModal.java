package analyzer.com.jodycaudill.payoffAnalyzer;

/**
 * Created by Jody_Admin on 11/7/2016.
 */
public class HelpModal {

    public static void display(){
        double widthOfhelp = 500;
        String help = "\tOne difficulty in understanding debt is planning for the future payoff and handling of a debt in relationship to" +
                " a person’s other debts. Budgeting software can help a person figure out the month to month expenditure of the payoff, " +
                "but typically in order to make a payoff plan that someone can analyze and compare, one typically has to turn to a spreadsheet.  " +
                "Payoff Analyzer will streamline payoff analysis and forecast without the need to build complex spreadsheets.\n";

        String how = "\tThis application will provided users with a way to model their debt such as credit cards and loans.  Once the debts are " +
                "modeled the system will offer the functionality to forecast payoff timeline and cost for each debt.  The primary functionality " +
                "of the system is to allow users to analyze and plan a payoff schedule by help users implement a popularly accepted payoff scheme. \n" +
                "\tThe payoff scheme is based on the cost of the minimum monthly payments for each debt and an additional monthly paydown budget, " +
                "typically at least $200~$300 to establish a monthly payoff budget.  The debts are paid on the minimum monthly payments except " +
                "one of the debts. This one focused debt receives the balance of the monthly payoff budget.  As debts are paid off and closed, " +
                "the monthly payoff budget does not change. This monthly budget stays the same dollar amount over the payoff schedule with the " +
                "balance always applied to the focused debt. The application can then allow the user to test different payoff schedules for the " +
                "various debts to determine the most profitable payoff schedule for the debts. ";

        String title = "Payoff Analyzer Help";

        String header1 = "Payoff Analyzer";
        String header2 = "How It Works";

        InformationModal.display(title,header1,help,header2,how ,widthOfhelp);
    }
}
