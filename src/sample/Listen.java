package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Listen implements Runnable {
    private Socket socket;
    private DataInputStream input;
    private StringProperty text = new SimpleStringProperty("");

    public Listen(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream())); // takes input from the server socket
            readMessages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readMessages() throws IOException {
        String text = "";
        while (socket.isConnected()) {
            try {
                text += input.readUTF() + "\n";
                this.text.setValue(text);
                System.out.println(text);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public String getText() {
        return text.get();
    }

    public StringProperty textProperty() {
        return text;
    }
}