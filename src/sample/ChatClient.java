package sample;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private String username;
    private String password;
    private final String IP = "localhost";
    private final int port = 5001;
    private boolean accepted;

    public ChatClient(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void connect() throws IOException {
        this.socket = new Socket(this.IP, this.port);
    }

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }
}
