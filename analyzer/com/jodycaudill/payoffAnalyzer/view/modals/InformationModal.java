package analyzer.com.jodycaudill.payoffAnalyzer.view.modals;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

public class InformationModal {

    public static void display(String title,String header1, String text1, String header2, String text2, double width) {
        double widthOfModal = width;
        Stage helpWindow = new Stage();

        helpWindow.initModality(Modality.APPLICATION_MODAL);
        helpWindow.setTitle(title);
        helpWindow.setMinWidth(widthOfModal + 50);

        Label headerLabel = new Label(header1);
        Text helpText = new Text(text1);
        helpText.setWrappingWidth(widthOfModal);

        Label howLabel = new Label(header2);
        Text howText = new Text(text2);
        howText.setWrappingWidth(widthOfModal);


        Button closeButton = new Button("close");
        closeButton.setOnAction(e -> helpWindow.close());


        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(headerLabel, helpText, howLabel, howText, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        helpWindow.setScene(scene);
        helpWindow.showAndWait();
    }
}
