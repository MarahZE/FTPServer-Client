import javax.swing.*;

public class FTPServer extends JFrame {

    public FTPServer() {
        setTitle("FTPServer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FTPServer();
    }
}
