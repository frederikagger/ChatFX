package sample;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatController implements Initializable {
    private Protocol protocol = new Protocol();
    private StringProperty chat = new SimpleStringProperty("");
    ExecutorService executor = Executors.newFixedThreadPool(2);
    @FXML
    private TextField textField;
    @FXML
    private TextArea textArea;

    public void logout(ActionEvent e) throws IOException {
        LoginController.chatClient.getSocket().close();
        Parent parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        executor.shutdown();

    }

    public void send() throws IOException {
        DataOutputStream output = new DataOutputStream(LoginController.chatClient.getSocket().getOutputStream());
        output.writeUTF(protocol.data(LoginController.chatClient.getUsername(), textField.getText()));
        textField.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Listen listen = new Listen(LoginController.chatClient.getSocket());
        textArea.textProperty().bindBidirectional(listen.textProperty());
        executor.execute(listen);
    }
}