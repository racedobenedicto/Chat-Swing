package Client;
//Model classe amb l'estructura de socket, enviament de dades i recepció
import java.io.*;
import java.net.*;

public class SocketClient extends Thread{
    ChatClient chat;
    int port;
    String host;
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    boolean active=true;

    public SocketClient(){
        this.port=5000;
        this.host="localhost";
    }

    public void setControlador(ChatClient chat){
        this.chat=chat;
    }

    public void connect(){
        try{
            this.socket=new Socket(host, port);
            this.bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException ex){
            System.out.println("Error connect");
        }
    }

    public void sendMsg(String msg){
        try{
            bufferedWriter.write(msg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }catch (IOException ex){
            System.out.println("Error sendMsg");
        }
    }

    public String readMsg(){
        try{
            String msg=bufferedReader.readLine();
            return msg;
        }catch (IOException ex){
            System.out.println("Error readMsg");
        }
        return "";
    }

    public void run(){
        while(active){
       		String msg=readMsg();
            chat.updateView(msg);
        }
    }
    
    public void salir() {
    	this.active=false;
    	try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public boolean isActive() {
    	return this.active;
    }
    
    public void setActive(boolean act) {
    	this.active=act;
    }

}

