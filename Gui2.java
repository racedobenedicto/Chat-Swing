package Client;

import javax.swing.*;
import java.awt.*;
class Gui2 {   
    public static void main(String args[]) {        
 
        // Creando el Marco        
        JFrame frame = new JFrame("Chat Frame");       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        frame.setSize(400, 400);        
 
        // Creando MenuBar y agregando componentes   
        JMenuBar mb = new JMenuBar();       
        JMenu m1 = new JMenu("ARCHIVO");       
        JMenu m2 = new JMenu("Ayuda");       
        mb.add(m1);       
        mb.add(m2);       
        JMenuItem m11 = new JMenuItem("Abrir");       
        JMenuItem m22 = new JMenuItem("Guardar como");       
        m1.add(m11);       
        m1.add(m22);        
 
        // Creando el panel en la parte inferior y agregando componentes       
        JPanel panel = new JPanel(); // el panel no está visible en la salida      
        JLabel label = new JLabel("Introducir texto");       
        JTextField tf = new JTextField(10); // acepta hasta 10 caracteres        
        JButton send = new JButton("Enviar");       
        JButton reset = new JButton("Restablecer");       
        panel.add(label); // Componentes agregados usando Flow Layout     
        panel.add(label); // Componentes agregados usando Flow Layout      
        panel.add(tf);       
        panel.add(send);       
        panel.add(reset);        
 
        // Área de texto en el centro  
        JPanel panel1 = new JPanel();
        JTextArea ta = new JTextArea(); 
        JTextArea ta2 = new JTextArea();
        panel1.add(BorderLayout.EAST,ta);
        panel1.add(BorderLayout.WEST, ta2);
 
        // Agregar componentes al marco.      
        frame.getContentPane().add(BorderLayout.SOUTH, panel);       
        frame.getContentPane().add(BorderLayout.NORTH, mb);       
        frame.getContentPane().add(BorderLayout.CENTER, panel1);       
        frame.setVisible(true);   
    }
}
