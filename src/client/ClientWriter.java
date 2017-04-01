
package client;

import client.gui.ClientMainWindow;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.swing.JOptionPane;

public class ClientWriter implements Runnable{
private String text;
    private OutputStream outputStream;

    public ClientWriter(String text, OutputStream outputStream) {
        this.text = text;
        this.outputStream = outputStream;
    }
    
    
    @Override
    public void run() {
        try  {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(text);
            writer.write("\n");
            writer.flush();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ClientMainWindow.getFrame(), "Не получилось отправить сообщение");
        }
    }
    
}
