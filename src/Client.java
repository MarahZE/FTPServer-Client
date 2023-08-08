import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        final File[] ftpFile = new File[1];

        // Set up client GUI layout
        JFrame jFrame = new JFrame("FTP Client");
        jFrame.setSize(700,500);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(),BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components and buttons
        JLabel title = new JLabel("Welcome to FTP Client!");
        title.setFont(new Font("Arial",Font.BOLD,23));
        title.setBorder(new EmptyBorder(20,0,30,0));

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


        // ActionListener for choosing a file
        jbChooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a file chooser dialog and store the selected file in ftpFile
                JFileChooser chooser = new JFileChooser();
                chooser.setDialogTitle("Choose a file!");
                if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    ftpFile[0] = chooser.getSelectedFile();
                    fileNameLabel.setText("File to send : " + ftpFile[0].getName());
                }
            }
        });


        // ActionListener for sending the file
        jbSender.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(ftpFile[0] == null) {
                    // Display an error message if no file is selected
                    fileNameLabel.setText("You have to select file first!");
                } else {
                    try {
                        // Read the selected file and prepare to send it
                        FileInputStream fileInputStream = new FileInputStream(ftpFile[0].getAbsolutePath());

                        Socket socket = new Socket("localhost",1234);

                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                        String ftpFileName = ftpFile[0].getName();

                        byte[] ftpFileSize = ftpFileName.getBytes();

                        byte[] ftpFileContentSize = new byte[(int) ftpFile[0].length()];
                        fileInputStream.read(ftpFileContentSize);

                        // Send the size of the file name to the server
                        output.writeInt(ftpFileSize.length);
                        // Send the file name
                        output.write(ftpFileSize);
                        // Send the size of the file content to the server
                        output.writeInt(ftpFileContentSize.length);
                        // Send the file content
                        output.write(ftpFileContentSize);

                        // Close the streams and socket
                        output.close();
                        fileInputStream.close();
                        socket.close();
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                }
            }
        });
    }
}
