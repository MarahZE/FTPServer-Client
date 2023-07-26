import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class Client {

    public static void main(String[] args) {
        File ftpFile;

        JFrame jFrame = new JFrame("FTP Client");
        jFrame.setSize(500,500);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(),BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Welcome to FTP Client!");
        title.setFont(new Font("Arial",Font.BOLD,23));
        title.setBorder(new EmptyBorder(20,0,10,0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jPanelChoseFile = new JPanel();
        jPanelChoseFile.setBorder(new EmptyBorder(10,0,10,0));

        JButton jbChooseFile = new JButton("Choose File");
        jbChooseFile.setPreferredSize(new Dimension(150,75));
        jbChooseFile.setFont(new Font("Arial",Font.BOLD,20));

        jPanelChoseFile.add(jbChooseFile);

        JLabel fileNameLabel = new JLabel("File to send: ");
        fileNameLabel.setFont(new Font("Arial",Font.BOLD,18));
        title.setBorder(new EmptyBorder(10,0,10,0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jPanel = new JPanel();
        jPanel.setBorder(new EmptyBorder(75,0,10,0));

        JButton jbSender = new JButton("Send file");
        jbSender.setPreferredSize(new Dimension(150,75));
        jbSender.setFont(new Font("Arial",Font.BOLD,20));

        jPanel.add(jbSender);

        jFrame.add(title);
        jFrame.add(jPanelChoseFile);
        jFrame.add(fileNameLabel);
        jFrame.add(jPanel);
        jFrame.setVisible(true);
    }
}
