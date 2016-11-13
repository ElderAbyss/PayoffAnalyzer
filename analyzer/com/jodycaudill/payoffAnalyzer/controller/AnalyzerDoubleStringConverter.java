package analyzer.com.jodycaudill.payoffAnalyzer.controller;

import javafx.util.StringConverter;

/**
 * Created by Jody_Admin on 11/12/2016.
 */
public class AnalyzerDoubleStringConverter extends StringConverter<Double> {


    @Override
    public String toString(Double number) {
        return String.valueOf(number);
    }

    @Override
    public java.lang.Double fromString(String valueString) {
        valueString = valueString.replaceAll("[$,%]", "");
        double  value;
        try {
            value = java.lang.Double.parseDouble(valueString);
        }catch (NumberFormatException exp){
            value =  -1.0;       //marker failure
        }
        return Double.valueOf(value);
    }
}
