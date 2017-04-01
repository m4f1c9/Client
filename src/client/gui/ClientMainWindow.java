package client.gui;

import client.ClientReader;
import client.ClientWriter;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClientMainWindow {
    
    private static JFrame frame;
    private static JTextArea in;
    private static JTextArea out;
    private static Socket socket;
    private static JButton button;
    
    public static JFrame getFrame() {
        return frame;
    }
    
    public static void showMainWindow() {
        frame = new JFrame("Client");
        frame.add(createJPanel());
        frame.setJMenuBar(createMenuBar());
        
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setVisible(true);
    }
    
    private static JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        
        JMenuItem start = new JMenuItem("Start");
        start.addActionListener((e) -> {
            try {
                socket = new Socket("127.0.0.1", 9090);
                new Thread(new ClientReader(in, socket)).start();
                start.setEnabled(false);
                 button.setEnabled(true);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(ClientMainWindow.getFrame(), "Что-то сломалось");
            }
                       
        });
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((e) -> {
            System.exit(0);
        });
        
        JMenu menu = new JMenu("Menu");
        menu.add(start);
        menu.add(exit);
        
        menubar.add(menu);
        
        return menubar;
    }
    
    public static JPanel createJPanel() {
        
        button = new JButton("Send");
        button.setEnabled(false);
        in = new JTextArea();
        out = new JTextArea();
        
        button.addActionListener((event) -> {
            try {
                new Thread(new ClientWriter(out.getText(), socket.getOutputStream())).start();
            } catch (IOException ex) {
                 JOptionPane.showMessageDialog(frame, "Не получилось отправить сообщение");
            }
            
            synchronized (in) {
                in.append("me > ");
                in.append(new Date().toString() + "  ");
                in.append(out.getText());
                in.append("\n");
            }
            
            out.setText(null);
        });
        
        in.setEnabled(false);
        
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(in), BorderLayout.CENTER);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(button, BorderLayout.EAST);
        
        southPanel.add(new JScrollPane(out), BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);
        
        return panel;
        
    }
}
