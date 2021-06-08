package Client;
//Vista Classe amb l'interficie gràfica
import javax.swing.*;
import java.awt.*;
class Gui {   
	public final String ENVIAR = "Enviar";
	public final String LOGOUT = "Logout";
	public final String LOGIN = "Login";
	ChatClient chat; //Controlador
	//Gui Enter area
	JFrame enterFrame;
    JLabel nickLabel, errorLabel1, errorLabel2;
    JTextField nick;
    JButton login;
    JPanel panelEnter;
    //Gui Chat area
    JFrame chatFrame;
    JTextArea messageArea;  
    JPanel panel;
    JTextField messageBox;
    JButton sendMsg, logout;
    JList usersList;
    JMenuBar menu;
    JMenu users;
    
    DefaultListModel<String> listModel;
    JScrollPane listScrollPane;
    
    public Gui() {
    	// create initial listModel
        listModel = new DefaultListModel<>();
        //Inicializa los dos frames
    	//Enter area
        initializeEnter();
        //Chat area
        initializeChat();
    	
    }
    
    public void initializeEnter() {
        enterFrame = new JFrame();
        enterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWidgetsEnter();

        enterFrame.setSize(500, 500);
        enterFrame.setLocationRelativeTo(null);
        enterFrame.setVisible(true);
    }
    
    public void initializeChat() {
        chatFrame = new JFrame();
        chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWidgetsChat();

        chatFrame.setSize(500, 500);
        chatFrame.setLocationRelativeTo(null);
        chatFrame.setVisible(false);
    }
    
    public void addWidgetsEnter() {
        nickLabel = new JLabel("Enter your nickname:");
        errorLabel1 = new JLabel("This nickname is already used");
        errorLabel2 = new JLabel("Invalid nickname");
        nick = new JTextField(30);
        login = new JButton("LOGIN");
        panelEnter = new JPanel();
        
        //Add the widgets to the container
        enterFrame.getContentPane().add(BorderLayout.CENTER, panelEnter); //BorderLayout.CENTER, 
        panelEnter.add(nickLabel);
        panelEnter.add(nick);
        panelEnter.setBorder(BorderFactory.createEmptyBorder(100, 50, 50, 50));
        
        panelEnter.add(errorLabel2);
        errorLabel2.setVisible(false);
        //enterFrame.getContentPane().add(BorderLayout.CENTER, nick);
        enterFrame.getContentPane().add(BorderLayout.SOUTH, login);
    }
    
    public void addWidgetsChat() {
        messageArea = new JTextArea();
        panel = new JPanel();
        messageBox = new JTextField(20);
        sendMsg = new JButton("SEND");
        logout = new JButton("LOGOUT");
        usersList = new JList<>(listModel);
        listScrollPane = new JScrollPane(usersList);
        menu = new JMenuBar();
        users = new JMenu("USERS");
        
        //Add the widgets to the container
        chatFrame.getContentPane().add(BorderLayout.CENTER, messageArea);
        chatFrame.getContentPane().add(BorderLayout.SOUTH, panel);
        panel.add(listScrollPane, BorderLayout.CENTER);
        panel.add(messageBox);
        panel.add(sendMsg);
        panel.add(logout);
        //chatFrame.getContentPane().add(usersList);
        chatFrame.getContentPane().add(BorderLayout.NORTH, menu);
        menu.add(users);
        users.add(usersList);
    }
    
    public String getMsg(String accio) {
    	switch(accio) {
    	case "Enviar":
    		return messageBox.getText();
    	case "Login":
    		return nick.getText();
    	}
    	return "";
    }
    
    public void updateView(String msg) {
    	messageArea.append(msg + "\n");
    }
    
    public void deleteMsg() {
    	messageBox.setText("");
    }
    
    public void setControlador(ChatClient chat) {
    	this.chat=chat;
    }
    
    public void connectEstablished() {
    	enterFrame.setVisible(false);
    	chatFrame.setVisible(true);
    }
    
    public void errorNick() {
    	errorLabel2.setVisible(true);
    }
    
    public void users(String users) {
    	users = users.substring(1, users.length()-1);
        for (String name : users.split(", ")) {
            if (!this.listModel.contains(name)) {
                this.listModel.addElement(name);
                //this.users.add(listModel);
            }
        }

    }
    
    public void iniciar() {
    	//Listen to events from the sendMsg button
        sendMsg.setActionCommand(ENVIAR);
        sendMsg.addActionListener(chat);
        logout.setActionCommand(LOGOUT);
        logout.addActionListener(chat);
        
      //Listen to events from the Login button
        login.setActionCommand(LOGIN);
        login.addActionListener(chat);
    }
    
    public void salir() {
    	nick.setText("");
    	enterFrame.setVisible(true);
    	chatFrame.setVisible(false);
    }
    
    
}
