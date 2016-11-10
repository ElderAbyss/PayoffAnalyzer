package analyzer.com.jodycaudill.payoffAnalyzer;

import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.ListIterator;

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

public class PayoffAnalyzer {
    private String userName;
    private PayoffSchedule currentSchedule;
    private final String CURRENT_SCHEDULE_NAME = "Payoff_Schedule.dat";

    /*
    Constructor section
     */
    public PayoffAnalyzer() {
        currentSchedule = new PayoffSchedule();
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

    public ArrayList<Debt> getDebts() {
        return this.getCurrentSchedule().getDebtList();
    }

    public void setDebts(ObservableList<Debt> newList){
        currentSchedule.getDebtList().clear();
        ListIterator<Debt> listITR = newList.listIterator();
        while(listITR.hasNext()){
            Debt debt = listITR.next();
            createNewDebt(debt);
        }
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
        currentSchedule.addDebtToList(newDebt);
        updateAndSave();
    }

    public void createNewDebt(Debt debt){
        currentSchedule.addDebtToList(debt);
        updateAndSave();
    }

    public void removeDebt(Debt debt){
        currentSchedule.removeDebtFromList(debt);
        updateAndSave();
    }

    public void reset(){
        currentSchedule = new PayoffSchedule();

    }



    private void updateAndSave() {
        currentSchedule.calculateSchedule();
        //saveDebts();
        //saveSchedule();
    }

    public void createNewPayoffSchedule(){
       this.currentSchedule = new PayoffSchedule();
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
