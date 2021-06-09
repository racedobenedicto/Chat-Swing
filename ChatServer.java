package server;

import java.io.*;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pract3.Codes;

import java.net.*;

public class ChatServer {
    private static int port;
    private static final int maxThreads = 50;
    private static Set<String> clientNicks = new HashSet<>();
    private static Set<PrintWriter> clients = new HashSet<>();

    public static void main(String[] args) {
        port = 5000;

        if(args.length == 1 && !args[0].isEmpty()) {
            port = Integer.parseInt(args[0]);
        }

        ExecutorService pool = Executors.newFixedThreadPool(maxThreads);
        ServerSocket serversocket;
        try {
            serversocket = new ServerSocket(port);
            System.out.print(Codes.CLEAR);
            System.out.println("Server is listening to port " + port);

            while (true) {
                pool.execute(new Handler(serversocket.accept()));
            }
        } catch (Exception ex) {
            System.out.println("Error while listening to port " + port + ": " + ex);
        }
    }

    private static class Handler implements Runnable {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        String name;

        public Handler(Socket s) {
            this.socket = s;
        }

        public void run() {
            try {
                System.out.println("Entrando cliente");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                while(true) {
                    out.println("ACCEPT:");
                    System.out.println("ACCEPT:");
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    
                    synchronized(clientNicks) {
                        if(!name.isEmpty() && !clientNicks.contains(name)) {
                            clientNicks.add(name);
                            break;
                        } else {
                        	out.println("ERROR:");
                        }
                    }
                }

                //Notify new Join in Chat
                out.println("JOIN:" + name);
                System.out.println(name + " has joined");
                System.out.println("Users: " + clientNicks.toString());
                out.println("USERS:" + clientNicks.toString());
                //Broadcast message of join
                for(PrintWriter writer : clients) {
                    writer.println("MESSAGE:" + "Admin" + " -> " + name + " has joined");
                    writer.println("USERS:" + clientNicks.toString());

                }

                clients.add(out);

                while(true) {
                    String input = in.readLine();
                    if(input.startsWith("QUIT:" + name)) {
                    	out.println("QUIT");
                    	return;
                    }

                    //System.out.println(input);                
                    for(PrintWriter writer : clients) {
                        writer.println("MESSAGE:" + input);
                    }
                }
            } catch(Exception ex) {
            } finally {
                if(name != null) {
                    if(socket != null) {
                        clients.remove(out);
                    }
                    if(name != null) {
                        System.out.println(name + " is leaving");
                        clientNicks.remove(name);
                        for (PrintWriter writer : clients) {
                            writer.println("MESSAGE:Admin -> " + name + " has left");
                            writer.println("USERS:" + clientNicks.toString());
                        }
                    }
                    try {
						socket.close();
					} catch (IOException e) { e.printStackTrace(); }
                }
            }
        }
    }
}