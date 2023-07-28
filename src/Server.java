import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

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

                        JButton remove = new JButton("remove");
                        remove.setPreferredSize(new Dimension(50,75));
                        remove.setFont(new Font("Arial",Font.BOLD,20));

                        filePanel.add(labelFileName);
                        filePanel.add(show);
                        filePanel.add(download);
                        filePanel.add(remove);
                        panel.add(filePanel);
                        frame.validate();
                        System.out.println(nameOfFile);

                        show.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFrame newFrame = new JFrame("Show Content File:" + nameOfFile);
                                newFrame.setSize(600,600);

                                JPanel contentPanel = new JPanel();
                                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

                                JLabel fileNameLabel = new JLabel("");
                                fileNameLabel.setFont(new Font("Arial",Font.BOLD,18));
                                fileNameLabel.setBorder(new EmptyBorder(20,0,20,0));

                                int i = nameOfFile.lastIndexOf(".");

                                String fileExtension = nameOfFile.substring(i);

                                if(fileExtension.equalsIgnoreCase(".txt")) {
                                    fileNameLabel.setText(new String(contentSize));
                                } else if (fileExtension.equalsIgnoreCase(".png") || fileExtension.equalsIgnoreCase(".jpg")){
                                    fileNameLabel.setIcon(new ImageIcon(contentSize));
                                } else if (fileExtension.equalsIgnoreCase(".pdf")) {

                                }

                                newFrame.add(fileNameLabel);


                                newFrame.setVisible(true);
                            }
                        });

                        download.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFrame newFrame = new JFrame("Download");
                                newFrame.setSize(800,200);

                                JPanel contentPanel = new JPanel();
                                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

                                JPanel newPanel = new JPanel();
                                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

                                JLabel jLabel = new JLabel("Are you sure to download " + nameOfFile);
                                jLabel.setFont(new Font("Arial",Font.BOLD,18));
                                jLabel.setBorder(new EmptyBorder(20,0,20,0));
                                //jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                                newPanel.add(jLabel);


                                JButton yes = new JButton("Yes");
                                yes.setPreferredSize(new Dimension(50,75));
                                yes.setFont(new Font("Arial",Font.BOLD,20));
                                newPanel.add(yes);

                                JButton No = new JButton("No");
                                No.setPreferredSize(new Dimension(50,75));
                                No.setFont(new Font("Arial",Font.BOLD,20));
                                newPanel.add(No);

                                //contentPanel.add(newPanel);

                                newFrame.add(newPanel);
                                newFrame.setVisible(true);

                                No.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        newFrame.dispose();
                                    }
                                });

                                yes.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        File downloadFile = new File(nameOfFile);
                                        try {
                                            FileOutputStream fileOutput = new FileOutputStream(downloadFile);
                                            fileOutput.write(contentSize);
                                            fileOutput.close();

                                            newFrame.dispose();
                                        } catch (IOException error) {
                                            error.printStackTrace();
                                        }

                                    }
                                });

                            }
                        });


                        remove.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                filePanel.remove(labelFileName);
                                filePanel.remove(download);
                                filePanel.remove(show);
                                filePanel.remove(remove);
                                filePanel.revalidate();
                                filePanel.repaint();
                            }
                        });

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
