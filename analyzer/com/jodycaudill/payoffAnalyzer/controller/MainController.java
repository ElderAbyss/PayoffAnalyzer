package analyzer.com.jodycaudill.payoffAnalyzer.controller;

import analyzer.com.jodycaudill.payoffAnalyzer.facade.PayoffAnalyzer;
import analyzer.com.jodycaudill.payoffAnalyzer.models.Debt;
import analyzer.com.jodycaudill.payoffAnalyzer.view.modals.AboutModal;
import analyzer.com.jodycaudill.payoffAnalyzer.view.modals.HelpModal;
import analyzer.com.jodycaudill.payoffAnalyzer.view.modals.InformationModal;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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

public class MainController implements Initializable {


    private PayoffAnalyzer analyzer;

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
        debtNameColumn.setCellFactory(TextFieldTableCell.<Debt>forTableColumn());


        debtAmountColumn.setCellValueFactory(new PropertyValueFactory<>("initialAmount"));
        debtAmountColumn.setCellFactory(TextFieldTableCell.<Debt,Double>forTableColumn(new AnalyzerDoubleStringConverter ()));

        debtInterestColumn.setCellValueFactory(new PropertyValueFactory<>("annualPercentageRate"));
        debtInterestColumn.setCellFactory(TextFieldTableCell.<Debt,Double>forTableColumn(new AnalyzerDoubleStringConverter ()));


        debtPaymentColumn.setCellValueFactory(new PropertyValueFactory<>("minPayment"));
        debtPaymentColumn.setCellFactory(TextFieldTableCell.<Debt,Double>forTableColumn(new AnalyzerDoubleStringConverter ()));

        helpMenuItem.setOnAction(e -> HelpModal.display());
        aboutMenuItem.setOnAction(e -> AboutModal.display());

        //updateDebtTab();
       // debtTable.getItems().addListener((ListChangeListener<? super Debt>)(event )-> updateDebts());


    }

    /**
     * event handler methods
     */
    @FXML
    private void addDebtItemClicked(){

        String name = debtNameTextField.getText();
        double amount = validateDouble(debtBalanceTextField.getText());
        double interest = validateDouble(debtInterestTextField.getText());
        double payment = validateDouble(debtPaymentTextField.getText());

        if(!name.isEmpty() && (amount > 0.0) && (interest > 0.0) && (payment > 0.0)){
            debtNameTextField.clear();
            debtBalanceTextField.clear();
            debtInterestTextField.clear();
            debtPaymentTextField.clear();
            analyzer.createNewDebt(name,amount,interest,payment);
            updateGUI();
        }else{
            StringBuilder message = new StringBuilder();
            if(name.isEmpty()){
                message.append("Account must have a valid name\n");
            }
            if(amount < 0.0){
                message.append("Amount must be a valid number\n");
            }
            if(interest < 0.0){
                message.append("Interest must be a valid percentage\n");
            }
            if(payment < 0.0){
                message.append("Minimum Payment must be a valid number\n");
            }
            InformationModal.display("Input Error","You have entered an Invalid number",message.toString(),"","",300);
        }
    }

    @FXML
    private void removeSelectedDebtItemClicked(){
        analyzer.removeDebt(debtTable.getSelectionModel().getSelectedItem());
        updateGUI();
    }

    @FXML
    private void calculateButtonClicked(){
        Double paydownBudget = validateDouble(monthlyPaydownTextField.getText());
        if(paydownBudget >= 0.0){
            analyzer.getCurrentSchedule().setMonthlyPayDownAmount(paydownBudget);
        }else{
            InformationModal.display("Input Error","You have entered an Invalid number","The Monthly Paydown Budget Amount is invalid.","","",300);
        }
        analyzer.getCurrentSchedule().calculateSchedule();
       updateGUI();
    }

    @FXML
    private void updateDebts(){
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
     * tableview edit methods
     */

    @FXML
    private void editNameColumn(TableColumn.CellEditEvent<Debt, String> editEvent)  {
        Debt updatedDebt = editEvent.getTableView().getItems().get(
                editEvent.getTablePosition().getRow());
        if(!editEvent.getNewValue().isEmpty()){
            updatedDebt.setName(editEvent.getNewValue());
            updateDebts();
        }else{
            //updatedDebt.setName(editEvent.getOldValue());
            InformationModal.display("Input Error","Account must have a valid name\n","","","",300);
            editEvent.getTableView().refresh();
        }
    }

    @FXML
    private void editAmountColumn(TableColumn.CellEditEvent<Debt, Double> editEvent)  {
        Debt updatedDebt = editEvent.getTableView().getItems().get(
                editEvent.getTablePosition().getRow());
        double newAmount = editEvent.getNewValue();
        if( newAmount >= 0.0 ){
            updatedDebt.setInitialAmount(newAmount);
            updateDebts();
        }else{
            InformationModal.display("Input Error","Amount must be a valid number\n","","","",300);
            editEvent.getTableView().refresh();
        }
    }

    @FXML
    private void editInterestColumn(TableColumn.CellEditEvent<Debt, Double> editEvent)  {
        Debt updatedDebt = editEvent.getTableView().getItems().get(
                editEvent.getTablePosition().getRow());
        double newAmount = editEvent.getNewValue();
        if( newAmount >= 0.0 ){
            updatedDebt.setAnnualPercentageRate(newAmount);
            updateDebts();
        }else{
            InformationModal.display("Input Error","Interest must be a valid percentage\n","","","",300);
            editEvent.getTableView().refresh();
        }
    }

    @FXML
    private void editPaymentColumn(TableColumn.CellEditEvent<Debt, Double> editEvent)  {
        Debt updatedDebt = editEvent.getTableView().getItems().get(
                editEvent.getTablePosition().getRow());
        double newAmount = editEvent.getNewValue();
        if( newAmount >= 0.0 ){
            updatedDebt.setMinPayment(newAmount);
            updateDebts();
        }else{
            //updatedDebt.setName(editEvent.getOldValue());
            InformationModal.display("Input Error","Minimum Payment must be a valid number\n","","","",300);
            editEvent.getTableView().refresh();
        }
    }

    /**
     * MainController methods
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
        debtTable.getItems().addListener((ListChangeListener<? super Debt>)(event )-> updateDebts());
    }



     /**
     *  Validator methods
     */

    /**
     * Used to validate debt account financial metrics, negative numbers are unacceptable doubles for this purpose
     * validate Double input String data
     * @param valueString  String to parse into double
     * @return double value of the String, -1 if parse failed
     */
    private double validateDouble(String valueString ) {
        valueString = valueString.replaceAll("[$,%]", "");
        double value;
        try {
            value = Double.parseDouble(valueString);
        }catch (NumberFormatException exp){
            value = -1.0;       //marker failure
        }
        return value;
    }

    private double validateDouble(Double value ) {
        value = value >= 0.0 ? value : -1.0;
        return value;
    }
}
