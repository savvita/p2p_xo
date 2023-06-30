import jui.GameForm;
import jui.StartForm;
import net.P2PClient;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        final int PORT = 6066;
        final String HOST = "localhost";
        final int ROWS = 3;
        final int COLUMNS = 3;
//        try {
//            P2PClient t = new P2PClient(HOST, PORT);
//            t.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        JFrame form = new StartForm(HOST, PORT, ROWS, COLUMNS);
        form.setVisible(true);
    }

}
