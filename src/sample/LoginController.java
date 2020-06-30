package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class LoginController {
    public static ChatClient chatClient;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label label;
    private boolean connected = false;

    private String usernameInput;
    private String passwordInput;
    private DataOutputStream output;
    private DataInputStream input;

    public void login(ActionEvent e) throws IOException {
        usernameInput = username.getText();
        passwordInput = password.getText();
        if (!connected) {
            this.chatClient = new ChatClient(usernameInput, passwordInput);
            try {
                chatClient.connect();
                connected = true;
                output = new DataOutputStream(chatClient.getSocket().getOutputStream());
                input = new DataInputStream(new BufferedInputStream(chatClient.getSocket().getInputStream()));
                System.out.println("Connected");
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        acceptedByServer(e);
    }

    public void acceptedByServer(ActionEvent e) throws IOException {

        output.writeUTF("JOIN " + usernameInput + " " + chatClient.getIP() + " " + chatClient.getPort());
        System.out.println("JOIN " + usernameInput + " " + chatClient.getIP() + " " + chatClient.getPort());
        String response = input.readUTF();
        System.out.println(response);
        if (!response.equals("J_OK")) {
            String split[] = response.split(":");
            String errorMsg = split[1]; //extracting errormsg
            label.setText(errorMsg);
        } else {
            loadUI("Chat", e);
        }
    }

    public void loadUI(String UI, ActionEvent e) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(UI + ".fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
