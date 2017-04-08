package client;

import client.gui.ClientMainWindow;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ClientReader implements Runnable {

    private final JTextArea readTextArea;
    private Socket socket;

    public ClientReader(JTextArea readTextArea, Socket socket) {
        this.readTextArea = readTextArea;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                InputStream inputStream = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
            String in;
            while ((in = reader.readLine()) != null) {
                synchronized (readTextArea) {
                    readTextArea.append("server > ");
                    readTextArea.append(new Date().toString() + "  ");
                    readTextArea.append(in);
                    readTextArea.append("\n");
                }

            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ClientMainWindow.getFrame(), "Что-то сломалось с той стороны");
            readTextArea.append("----------------------------------------------");
            readTextArea.append("Все сломалось");
            readTextArea.append("----------------------------------------------");
            readTextArea.append("\n");
        }
    }

}
