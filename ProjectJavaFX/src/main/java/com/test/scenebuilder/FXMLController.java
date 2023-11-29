package com.test.scenebuilder;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FXMLController implements Initializable {
    
    private Label label;
    @FXML
    private Label lbl_Title;
    @FXML
    private Label lbl_Text1;
    @FXML
    private Label lbl_Text2;
    @FXML
    private Label lbl_fact;
    @FXML
    private Button btn_action;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void showNotification(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notificação");
        alert.setHeaderText(null);
        alert.setContentText("Você clicou no botão! Clique OK para ver seu fato :)");
        alert.showAndWait();
        try {

            URL url = new URL("https://catfact.ninja/fact");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                String fact = "Ocorreu um erro imprevisto";
                lbl_fact.setText(fact);
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                System.out.println(informationString);

                JSONParser parser = new JSONParser();
                JSONObject json = (JSONObject) parser.parse(informationString.toString());
                
                String fact = (String) json.get("fact");
                lbl_fact.setText(fact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
