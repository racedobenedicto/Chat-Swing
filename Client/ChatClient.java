package Client;

//Controller. Class that act between ChatClient and SocketClient
import java.awt.event.*;

public class ChatClient implements ActionListener {
    Gui gui;
    SocketClient socketClient;
    String nick;
    
    //Constructor
    public ChatClient(Gui client) {
        this.gui = client;
    }

    public void actionPerformed(ActionEvent e) {
    	String msg;
        switch(e.getActionCommand()) {
            case "Enviar":
                msg = this.gui.getMsg("Enviar");
                this.socketClient.sendMsg(nick+" -> "+msg);
                this.gui.deleteMsg();
                break;
            case "Login":
            	msg = this.gui.getMsg("Login");
            	System.out.println("Connecting with nick: "+msg);
            	if (msg != null) {
            		nick = msg;
            		socketClient = new SocketClient();
            		socketClient.setController(this);
            		this.socketClient.connect();
        			this.socketClient.start();
            		
            		this.socketClient.sendMsg(msg);
            	} else {
            		this.gui.errorNick();
            	}	
            	break;
            case "Logout":
            	this.socketClient.sendMsg("QUIT:"+nick);
            	break;
        }
    }

    public void updateView(String msg) {
    	//Possible responses control
    	String[] message = msg.split(":", 2);
    	switch(message[0]) {
    		case "ACCEPT":
    			System.out.println("Connection accepted");
    			break;
    		case "ERROR":
    			System.out.println("Nick Error");
    			this.gui.errorNick();
    			break;
    		case "JOIN":
    			System.out.println("Connection established");
    			this.gui.connectEstablished();
    			break;
    		case "USERS":
    			System.out.println("Users:" + message[1]);
    			this.gui.users(message[1]);
    			break;
    		case "MESSAGE":
    			this.gui.updateView(message[1]);
    			break;
    		case "QUIT":
    			this.socketClient.getOut();
    			this.gui.getOut();
    			break;
    	}
    }

    public static void main(String[] args) {
        Gui gui = new Gui(); //View
        ChatClient chat = new ChatClient(gui); //Controller
        gui.setController(chat);
        gui.start();
    }
}