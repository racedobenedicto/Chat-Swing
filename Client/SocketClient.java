package Client;

//Model. Class with socket structure, data sending and receiving
import java.io.*;
import java.net.*;

public class SocketClient extends Thread {
    ChatClient chat;
    int port;
    String host;
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    boolean active = true;

    public SocketClient() {
        this.port = 5000;
        this.host = "localhost";
    }

    public void setController(ChatClient chat) {
        this.chat = chat;
    }

    public void connect() {
        try {
            this.socket = new Socket(host, port);
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ex) {
            System.out.println("Connection Error");
        }
    }

    public void sendMsg(String msg) {
        try {
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException ex) {
            System.out.println("sendMsg Error");
        }
    }

    public String readMsg() {
        try {
            String msg = bufferedReader.readLine();
            return msg;
        } catch (IOException ex) {
            System.out.println("readMsg Error");
        }
        return "";
    }

    public void run() {
        while(active) {
       		String msg = readMsg();
            chat.updateView(msg);
        }
    }
    
    public void getOut() {
    	this.active = false;
    	try {
			socket.close();
		} catch (IOException e) { e.printStackTrace(); }
    }
    
    public boolean isActive() {
    	return this.active;
    }
    
    public void setActive(boolean act) {
    	this.active = act;
    }
}

