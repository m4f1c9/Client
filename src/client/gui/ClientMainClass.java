
package client.gui;

import javax.swing.SwingUtilities;


public class ClientMainClass {
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> ClientMainWindow.showMainWindow());
    }
}
