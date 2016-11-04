package analyzer.com.jodycaudill.payoffAnalyzer;

import java.util.LinkedList;

/**
 * Created by Jody_Admin on 11/3/2016.
 */
public class PayoffAnalyzer {
    private String userName;
    private LinkedList<Debt> debts;
    private PayoffSchedule currentSchedule;
    private final String CURRENT_SCHEDULE_NAME = "Payoff_Schedule.dat";

    /*
    Constructor section
     */
    public PayoffAnalyzer() {
    }

    /*
    Getters and Setters Section
     */
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LinkedList<Debt> getDebts() {
        return debts;
    }

    public void setDebts(LinkedList<Debt> debts) {
        this.debts = debts;
    }

    public PayoffSchedule getCurrentSchedule() {
        return currentSchedule;
    }

    public void setCurrentSchedule(PayoffSchedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }

    /*
    Methods Section
     */
    public void createNewDebt(String name, double initialAmount, double annualPercentageRate, double minPayment){
        Debt newDebt = new Debt(name,initialAmount,annualPercentageRate,minPayment);
        debts.add(newDebt);
        currentSchedule.addDebtToList(newDebt);
        updateAndSave();
    }

    public void removeDebt(int index){
        currentSchedule.removeDebtFromList(debts.remove(index));
        updateAndSave();
    }

    private void updateAndSave() {
        currentSchedule.calculateSchedule();
        saveDebts();
        saveSchedule();
    }

    public void createNewPayoffSchedule(){
       this.currentSchedule = new PayoffSchedule();
        for (Debt debt: debts) {
            this.currentSchedule.addDebtToList(debt);
        }
    }

    public void saveDebts(){
        //TODO
        System.out.println("PayoffAnalyzer.saveDebts() is not built yet");
    }

    public void loadDebts(){
        //TODO
        System.out.println("PayoffAnalyzer.loadDebts() is not built yet");
    }
    public void saveSchedule(){
        saveSchedule(CURRENT_SCHEDULE_NAME);
    }

    public void saveSchedule(String name){
        //TODO
        System.out.println("PayoffAnalyzer.saveSchedule() is not built yet");
    }

    public void loadSchedule(){
        loadSchedule(CURRENT_SCHEDULE_NAME);
    }

    public void loadSchedule(String file){
        //TODO
        System.out.println("PayoffAnalyzer.loadSchedule() is not built yet");
    }
}
