package Client;
//Controlador. Classe que actua entre chatClient i socketClient
import java.awt.event.*;

public class ChatClient implements ActionListener{
    Gui gui;
    SocketClient socketClient;
    String nick;
    

    //Constructor
    public ChatClient(Gui client){
        this.gui=client;
    }

    public void actionPerformed(ActionEvent e){
    	String msg;
        switch(e.getActionCommand()){
            case "Enviar":
                msg=this.gui.getMsg("Enviar");
                //this.gui.updateView(msg);
                this.socketClient.sendMsg(nick+" -> "+msg);
                this.gui.deleteMsg();
            break;
            case "Login":
            	msg=this.gui.getMsg("Login");
            	System.out.println("Conectando con nick: "+msg);
            	if (msg != null) {
            		nick=msg;
            		socketClient = new SocketClient();
            		socketClient.setControlador(this);
            		this.socketClient.connect();
        			this.socketClient.start();
            		
            		this.socketClient.sendMsg(msg);
            	}else {
            		this.gui.errorNick();
            	}
            	
            break;
            case "Logout":
            	this.socketClient.sendMsg("QUIT:"+nick);
            	
            break;
        }
    }

    public void updateView(String msg){
    	//Control de posibles respuestas
    	String[] message = msg.split(":", 2);
    	switch(message[0]) {
    		case "ACCEPT":
    			System.out.println("Connexió acceptada");
    		break;
    		case "ERROR":
    			System.out.println("Error nick");
    			this.gui.errorNick();
    		break;
    		case "JOIN":
    			System.out.println("Connexió establerta");
    			this.gui.connectEstablished();
    		break;
    		case "USERS":
    			System.out.println("Users:"+ message[1]);
    			this.gui.users(message[1]);
    		break;
    		case "MESSAGE":
    			this.gui.updateView(message[1]);
    		break;
    		case "QUIT":
    			this.socketClient.salir();
    			this.gui.salir();
    		break;
    	}
        
    }

    public static void main(String[] args){
    	//final int port=5000;
        //final String host="localhost";
        Gui gui = new Gui(); //Vista
        //SocketClient socketClient = new SocketClient(); // Modelo
        ChatClient chat = new ChatClient(gui); //Controlador
        gui.setControlador(chat);
        //socketClient.setControlador(chat);

        //chat.iniciar();
        gui.iniciar();
    }

}