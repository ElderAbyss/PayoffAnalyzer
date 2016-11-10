package analyzer;

import analyzer.com.jodycaudill.payoffAnalyzer.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
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

    @FXML private VBox baseVBox;

    @FXML private MenuItem closeFileMenuItem;
    @FXML private MenuItem resetFileMenuItem;
    @FXML private MenuItem helpMenuItem;
    @FXML private MenuItem aboutMenuItem;

    @FXML private Button debtAddButton;

    @FXML private TextField debtNameTextField;
    @FXML private TextField debtBalanceTextField;
    @FXML private TextField debtInterestTextField;
    @FXML private TextField debtPaymentTextField;



    @FXML private TableView<Debt> debtTable;
    @FXML private TableColumn<Debt , String> debtNameColumn;
    @FXML private TableColumn<Debt , Double> debtAmountColumn;
    @FXML private TableColumn<Debt , Double> debtInterestColumn;
    @FXML private TableColumn<Debt , Double> debtPaymentColumn;

    @FXML private TextField scheduleNameTextField;
    @FXML private Label errorLabel;
    @FXML private Button saveButton;

    @FXML private Label minPaymentLabel;
    @FXML private TextField monthlyPaydownTextField;
    @FXML private Label monthlyBudgetLabel;
    @FXML private Button calculateButton;

    @FXML private Label payoffDateLabel;
    @FXML private Label interestLabel;
    @FXML private Label costLabel;

    @FXML private TextArea scheduleTextArea;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        analyzer = new PayoffAnalyzer();

        debtNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        debtAmountColumn.setCellValueFactory(new PropertyValueFactory<>("initialAmount"));
        debtInterestColumn.setCellValueFactory(new PropertyValueFactory<>("annualPercentageRate"));
        debtPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("minPayment"));

        helpMenuItem.setOnAction(e -> HelpModal.display());
        aboutMenuItem.setOnAction(e -> AboutModal.display());

        //updateDebtTab();
       // debtTable.getItems().addListener((ListChangeListener<? super Debt>)(event )-> sortDebts());


    }

    /**
     * event handler methods
     */
    @FXML
    private void addDebtItemClicked(){

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
            updateGUI();
        }
    }

    @FXML
    private void removeSelectedDebtItemClicked(){
        analyzer.removeDebt(debtTable.getSelectionModel().getSelectedItem());
        updateGUI();
    }

    @FXML
    private void calculateButtonClicked(){
        Double paydownBudget = validateDouble(monthlyPaydownTextField.getText(),"The Monthly Paydown Budget Amount is invalid.");
        if(paydownBudget >= 0.0){
            analyzer.getCurrentSchedule().setMonthlyPayDownAmount(paydownBudget);
        }
        analyzer.getCurrentSchedule().calculateSchedule();
       updateGUI();
    }

    @FXML
    private void sortDebts(){
        analyzer.setDebts(debtTable.getItems());
        updateScheduleTab();
    }

    @FXML
    private void resetMenuItemClicked(){
        analyzer.reset();
        monthlyPaydownTextField.clear();
        updateGUI();
    }

    @FXML
    private void closeMenuItemClicked(){
       Stage app = (Stage) baseVBox.getScene().getWindow();
        app.close();
    }

    @FXML
    private void savebuttonClicked(){
        if(scheduleNameTextField.getText().isEmpty()){
            errorLabel.setText("Invalid Schedule Name");
        }else{
            errorLabel.setText("");
            try{
                analyzer.getCurrentSchedule().saveSchedule(scheduleNameTextField.getText());
                InformationModal.display("Schedule Save Successful","You can locate your Payment Schedule file at","","<Application Directory>/Schedules/<Schedule Name>.txt","",350);
            } catch (IOException exp) {
                exp.printStackTrace();
                InformationModal.display("Application Error","An IO Error has occurred",exp.getMessage(),"Source","Schedule Saving",300);
            }catch (SecurityException sexp){
                sexp.printStackTrace();
                InformationModal.display("Application Error","A Permissions Error has occurred",sexp.getMessage(),"Source","Schedule Saving",300);
            }
        }
    }

    /**
     * Controller methods
     */
    private void updateGUI(){
        updateScheduleTab();
        updateDebtTab();
    }

    private void updateScheduleTab() {
        if (analyzer.getCurrentSchedule() != null){
            payoffDateLabel.setText(analyzer.getCurrentSchedule().getPayoffDate().format(DateTimeFormatter.ofPattern("MMM - yy")));
            interestLabel.setText(String.format("$%.2f", analyzer.getCurrentSchedule().getTotalScheduleInterest()));
            costLabel.setText(String.format("$%.2f", analyzer.getCurrentSchedule().getTotalScheduleCost()));
            scheduleTextArea.setText(analyzer.getCurrentSchedule().getScheduleDetails());
            minPaymentLabel.setText(String.format("$%.2f" , analyzer.getCurrentSchedule().getMonthlyMinPayment()));
            monthlyBudgetLabel.setText(String.format("$%.2f", analyzer.getCurrentSchedule().getMonthlyPayoffBudget()));
        }
    }

    private void updateDebtTab(){
        ObservableList<Debt> currentDebts = FXCollections.observableArrayList();
        if(analyzer.getDebts().size() > 0) {
            currentDebts.addAll(analyzer.getDebts());
            //debtTable.getItems().clear();
           // debtTable.getItems().addAll(currentDebts);
            debtTable.setItems(currentDebts);
        }else{
            debtTable.getItems().clear();
        }
        debtTable.getItems().addListener((ListChangeListener<? super Debt>)(event )-> sortDebts());
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
