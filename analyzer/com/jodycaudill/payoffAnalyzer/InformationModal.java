package analyzer.com.jodycaudill.payoffAnalyzer;

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
 * Created by Jody_Admin on 11/7/2016.
 */
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
