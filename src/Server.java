import javax.imageio.IIOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<File> files = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("FTPServer");
        frame.setSize(700,900);
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JLabel title = new JLabel("FTP Files from client");
        title.setFont(new Font("Arial",Font.BOLD,18));


        frame.add(title);
        frame.add(scroll);
        frame.setVisible(true);

        ServerSocket serverSocket = new ServerSocket(1234);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());

                int ftpFileNLength = input.readInt();

                if(ftpFileNLength >0) {
                    byte[] ftpNameSize = new byte[ftpFileNLength];
                    input.readFully(ftpNameSize,0,ftpNameSize.length);
                    String nameOfFile = new String(ftpNameSize);

                    int ftpFileContentSize = input.readInt();
                    if (ftpFileContentSize > 0) {
                        byte[] contentSize = new byte[ftpFileContentSize];
                        input.readFully(contentSize, 0, contentSize.length);
                        JPanel filePanel = new JPanel();
                        filePanel.setLayout(new BoxLayout(filePanel,BoxLayout.X_AXIS));
                        JLabel labelFileName = new JLabel(nameOfFile);
                        labelFileName.setFont(new Font("Arial",Font.BOLD,18));
                        labelFileName.setBorder(new EmptyBorder(20,0,20,0));
                        JButton download = new JButton("Download");
                        download.setPreferredSize(new Dimension(50,75));
                        download.setFont(new Font("Arial",Font.BOLD,20));

                        JButton show = new JButton("Show");
                        show.setPreferredSize(new Dimension(50,75));
                        show.setFont(new Font("Arial",Font.BOLD,20));

                        filePanel.add(labelFileName);
                        filePanel.add(download);
                        filePanel.add(show);
                        panel.add(filePanel);
                        frame.validate();
                        System.out.println(nameOfFile);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
