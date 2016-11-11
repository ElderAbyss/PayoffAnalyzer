# Payoff Analyzer

One difficulty in understanding debt is planning for the future payoff and handling of a debt in relationship to a person’s other debts. Budgeting software can help a person figure out the month to month expenditure of the payoff, but typically in order to make a payoff plan that someone can analyze and compare, one typically has to turn to a spreadsheet.  Payoff Analyzer will streamline payoff analysis and forecast without the need to build complex spreadsheets.
***

## Project Description

This application will provided users with a way to model their debt such as credit cards and loans.  Once the debts are modeled the system will offer the functionality to forecast payoff timeline and cost for each debt.  The primary functionality of the system is to allow users to analyze and plan a payoff schedule by help users implement a popularly accepted payoff scheme. 
The payoff scheme is based on the cost of the minimum monthly payments for each debt and an additional monthly paydown budget, typically at least $200~$300 to establish a monthly payoff budget.  The debts are paid on the minimum monthly payments except one of the debts. This one focused debt receives the balance of the monthly payoff budget.  As debts are paid off and closed, the monthly payoff budget does not change. This monthly budget stays the same dollar amount over the payoff schedule with the balance always applied to the focused debt. The application can then allow the user to test different payoff schedules for the various debts to determine the most profitable payoff schedule for the debts. 
***
## Usage Senario
This application will be targeted for users planning and analyzing their personal budgets.  A user can enter their debt items into the application, determine the allotment of Monthly Paydown Budget, and then use the system to analyze different payoff schedules and timelines to accomplish their goals.  Those goals could be to plan a schedule to save the most money or to develop a schedule to remove individual debt item quicker.  The application will provide feedback for the payoff analysis such as cost, payoff timeline, interest paid, and milestone information for each debit item.  The user can then establish a plan and budget to achieve their personal debt payoff goals.

![alt text](https://github.com/ElderAbyss/PayoffAnalyzer/raw/master/images/payoff%20analyzer%20-%20Use%20Case.png "Use Case")
***
## EVALUATION CRITERIA
The following criteria will identify the successful completion of the project
* Can the user enter debt items into the system?
* Can the user enter Monthly Paydown Budget amount?
* Can the user see a visual feedback and breakdown of the debt items?
* Can the user manipulate the debt items in a GUI format to construct a Payoff Schedule?
* Can the user have the system automatically organize a Payoff Schedule based on debt amount (smallest to largest or largest to smallest)?
* Can the user have the system automatically organize a Payoff Schedule based on debt interest rate (smallest to largest or largest to smallest)?
* Can the user see the calculated timeline and cost of the current Payoff Schedule?
* Can the user manually sequence debt items in the Payoff Schedule?
* Can the user save Payoff Schedules for review and comparison later?
* Can the system accurately calculate compounding interest on the debt items?
* Can the system accurately calculate payoff times for debt items?
 
***
## INTENDED METHODOLOGY, DATA STORE & PATTERN
The application will be built in Java and targeted to be deployed as a desktop application on local user systems. Since data store requirements will be relatively small in size the application will use serialized object files to store local user data.  A facade pattern will be used as part of an MVC model to build an interface for the user that abstracts them away from complexities of the debt object calculations and application of the Monthly Payoff Budget to the appropriate account over time as the simulation is calculated. The application removes the difficulties with the spreadsheet approach of the user needing to figure out the calculations and where and when to apply the needed payoff amounts to which debt item in order to plan a payoff schedule. The user only needs to inputs some basic information and make a couple choices in the application GUI to utilize the complex debt item calculations and analysis results. 
***
## Architecture
![alt text](https://github.com/ElderAbyss/PayoffAnalyzer/raw/master/images/arch%20model%20-%20Page%201.png "Architecture Model")

![alt text](https://github.com/ElderAbyss/PayoffAnalyzer/raw/master/images/payoff%20analyzer%20-%20Components.png "Component Model")

![alt text](https://github.com/ElderAbyss/PayoffAnalyzer/raw/master/images/payoff%20analyzer%20-%20State.png "State Model")

***
## UI Models and flows  

* [Application UI Flow Diagram](https://github.com/ElderAbyss/PayoffAnalyzer/raw/master/images/UI%20flow%20-%20UX.png)

![alt text](https://github.com/ElderAbyss/PayoffAnalyzer/raw/master/images/UI%20flow%20-%20Debt%20Flow.png "Enter Debts Flow")
![alt text](https://github.com/ElderAbyss/PayoffAnalyzer/raw/master/images/UI%20flow%20-%20Plan%20Flow.png "Enter Schedule Flow")
![alt text](https://github.com/ElderAbyss/PayoffAnalyzer/raw/master/images/UI%20flow%20-%20Debts%20Screen.png "Debts Screen")
![alt text](https://github.com/ElderAbyss/PayoffAnalyzer/raw/master/images/UI%20flow%20-%20Payoff%20Screen.png "Schedule Screen")
## Installation
Build the project artifact to produce an executable java jar file for the program. 
***
## Execution
The appliaiton is currently built as an executable Java jar file and can be executed with the following command:
```sh
java -jar 
```
***
### Development

***
### Todos

 - build a GUI component to show the minimum payment schedule for comparison.
 - Edit Debts in Table

 
***

## License

MIT

**Free Software**
***
