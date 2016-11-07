package analyzer;

import analyzer.com.jodycaudill.payoffAnalyzer.Debt;
import analyzer.com.jodycaudill.payoffAnalyzer.PayoffAnalyzer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

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
* */

public class Controller implements Initializable {


    public PayoffAnalyzer analyzer;

    public Button debtAddButton;

    public TextField debtNameTextField;
    public TextField debtBalanceTextField;
    public TextField debtInterestTextField;
    public TextField debtPaymentTextField;



    public TableView<Debt> debtTable;
    public TableColumn<Debt , String> debtNameColumn;
    public TableColumn<Debt , Double> debtAmountColumn;
    public TableColumn<Debt , Double> debtInterestColumn;
    public TableColumn<Debt , Double> debtPaymentColumn;

    public TextField monthlyPaydownTextField;
    public Button calculateButton;

    public Label payoffDateLabel;
    public Label interestLabel;
    public Label costLabel;

    public TextArea scheduleTextArea;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        analyzer = new PayoffAnalyzer();

        debtNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        debtAmountColumn.setCellValueFactory(new PropertyValueFactory<>("initialAmount"));
        debtInterestColumn.setCellValueFactory(new PropertyValueFactory<>("annualPercentageRate"));
        debtPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("minPayment"));
        //updateDebtTable();
    }

    /**
     * event handler methods
     */
    public void addDebtItemClicked(){

        String name = debtNameTextField.getText();
        double amount = validateDouble(debtBalanceTextField.getText(),"Balance must be a valid number");
        double interest = validateDouble(debtInterestTextField.getText(),"Interest must be a valid percentage");
        double payment = validateDouble(debtPaymentTextField.getText(),"Minimum Payment must be a valid number");

        if((amount > 0.0) && (interest > 0.0) && (payment > 0.0)){
            debtNameTextField.clear();
            debtBalanceTextField.clear();
            debtInterestTextField.clear();
            debtPaymentTextField.clear();
            analyzer.createNewDebt(name,amount,interest,payment);
            updateDebtTable();
        }
    }

    public void removeSelectedDebtItemClicked(){
        int removeIndex = debtTable.getSelectionModel().getSelectedIndex();
        analyzer.removeDebt(removeIndex);
        updateDebtTable();
    }

    public void calculateButtonClicked(){
        Double paydownBudget = validateDouble(monthlyPaydownTextField.getText(),"The Monthly Paydown Budget Amount is invalid.");
        if(paydownBudget > 0.0){
            analyzer.getCurrentSchedule().setMonthlyPayDownAmount(paydownBudget);
        }
        analyzer.getCurrentSchedule().calculateSchedule();
        scheduleTextArea.setText(analyzer.getCurrentSchedule().getScheduleDetails());
    }

    /**
     * Controller methods
     */

    public void updateDebtTable(){
        ObservableList<Debt> currentDebts = FXCollections.observableArrayList();
        if(analyzer.getDebts().size() > 0) {
            currentDebts.addAll(analyzer.getDebts());
            debtTable.setItems(currentDebts);
        }else{
            debtTable.getItems().clear();
        }
    }

    /**
     *  Validator methods
     */

    private double validateDouble(String valueString, String message) {
        valueString = valueString.replaceFirst("$|%", "");
        double value = -1.0;
        try {
            value = Double.parseDouble(valueString);
        }catch (NumberFormatException exp){
            //// TODO: 11/6/2016  throw modal with passed in message.
        }
        return value;
    }

}
