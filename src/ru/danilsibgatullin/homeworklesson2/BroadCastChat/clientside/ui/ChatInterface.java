package ru.danilsibgatullin.homeworklesson2.BroadCastChat.clientside.ui;

import ru.danilsibgatullin.homeworklesson2.BroadCastChat.serverside.services.ClientHandler;
import ru.danilsibgatullin.homeworklesson2.BroadCastChat.clientside.ui.AutorityInterface;
import ru.danilsibgatullin.homeworklesson2.BroadCastChat.serverside.services.ConnectDB;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Statement;


public class ChatInterface extends JFrame {

    final Color elementsColor = Color.lightGray; // константа для стиля
    final Color backgroundColor = Color.DARK_GRAY; // константа для стиля
    private JTextArea textArea = new JTextArea(40,40);
    private JTextField messageLine = new JTextField(30);
    DataInputStream  dis;
    DataOutputStream dos;
    private String myNick="";
    private DefaultListModel<String>memberList = new DefaultListModel<>();
    private JList<String> jList = new JList<>(memberList);
    private boolean nickAbleToChange =true ;

    public ChatInterface(Socket socket, String serverAddress, Integer serverPort,String nick) {

        myNick=nick;
        try {
            connection(socket,serverAddress,serverPort);
        }
        catch (IOException ignore){

        }

        //задаем параметры окна
        setTitle("Broadcast chat - "+myNick);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(650,600);
        setPreferredSize(new Dimension(650,720));
        setLocation(20,20);
        setResizable(false);
        BoxLayout gLine = new BoxLayout(getContentPane(),BoxLayout.X_AXIS);
        setLayout(gLine);
        JPanel group2 = new JPanel();
        group2.setPreferredSize(new Dimension(500,700));
        group2.setLayout(new BoxLayout(group2,BoxLayout.Y_AXIS)) ;
        group2.setBackground(backgroundColor);
        group2.setAutoscrolls(false);

        //Вспомогательные панели
        JPanel panCentr = new JPanel();
        JPanel panFoot= new JPanel();
        JPanel panLeft= new JPanel();
        panCentr.setPreferredSize(new Dimension(490,650));
        panLeft.setPreferredSize(new Dimension(100,700));
        panFoot.setPreferredSize(new Dimension(500,30));
        panFoot.setBackground(backgroundColor);
        panCentr.setBackground(backgroundColor);
        panLeft.setBackground(elementsColor);

        //Набор надписей
        JLabel currentUser = new JLabel("Участники чата");
        currentUser.setBackground(backgroundColor);
        currentUser.setForeground(elementsColor);

        //Набор кнопок
        JButton okButton =new JButton("ОК");
        okButton.setBackground(elementsColor);
        JButton endButton =new JButton("End session");
        endButton.setBackground(elementsColor);
        JButton changeNick = new JButton("Change NICK");
        changeNick.setBackground(elementsColor);

        //Настройка листа с контактами
        jList.setBackground(elementsColor);
        jList.setPreferredSize(new Dimension(100,300));
        jList.setVisible(true);
        JScrollPane scrollMembers = new JScrollPane(jList);
        scrollMembers.setBackground(backgroundColor);
        scrollMembers.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        scrollMembers.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scrollMembers.setAutoscrolls(true);
        panLeft.add(jList);

        //Настройка текстового поля

        textArea.setEditable(false);
        textArea.setAutoscrolls(true);
        textArea.setBackground(elementsColor);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBackground(elementsColor);
        scrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
        scrollPane.setAutoscrolls(true);

        messageLine.setPreferredSize(new Dimension(100,25));

        //Строка ввода
        messageLine.setBackground(elementsColor);

        //Отправка по кнопке
        okButton.addActionListener(e -> {
            send();
        });

        //листенер смены ника
        changeNick.addActionListener(e -> {
            String result = JOptionPane.showInputDialog(this,
                    "<html><h2>Enter new nick");
            send("/change::"+result);
        });

        //Закрываем сессию
        endButton.addActionListener(e-> {
        send("/end");
        dispose();
            try {
                 new AutorityInterface();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });


        //Листенер на добавление сообщения через Enter
        messageLine.addActionListener(e -> send() );

        //добавляем элементы в контейнеры

        panLeft.add(changeNick);
        panLeft.add(jList,BorderLayout.NORTH);
        panCentr.add(scrollPane);
        panFoot.add(messageLine);
        panFoot.add(okButton);
        panFoot.add(endButton);
        group2.add(panCentr);
        group2.add(panFoot);
        add(panLeft);
        add(group2);
        pack();
        setVisible(true);
    }

    private void connection (Socket socketAuth,String serverAddress,Integer serverPort) throws IOException {
        dis = new DataInputStream(socketAuth.getInputStream());
        dos = new DataOutputStream(socketAuth.getOutputStream());
        send("/list");
        new Thread(() -> {

            try {
                while (true) {
                    String message = dis.readUTF();
                    if (message.equalsIgnoreCase("/finish")) {
                        JOptionPane.showMessageDialog(new JDialog(),"<HTML><h2>Connection lost");
                        new AutorityInterface();
                        this.dispose();
                        break;
                    }
                    else if (message.startsWith("/nickbusy")){
                        nickAbleToChange=false;
                        JOptionPane.showMessageDialog(new JDialog(),"<HTML><h2>This nick already used");
                    }
                    else if (message.startsWith("/nickchangeok")){
                        String[] arr = message.split("::");
                        myNick=arr[1];
                        setTitle("Broadcast chat - "+myNick);
                    }
                    else if(message.startsWith("/list")){
                        memberList.clear();
                        addNewMemberToMembersList(message);
                    }
                    else if (!message.equals("")){
                    textArea.append(""+message + "\n");
                    }
                }
            } catch (IOException ignored){
            }

        }).start();
    }

    private void send() {
        if (messageLine.getText() != null && !messageLine.getText().trim().isEmpty()) {
            try {
                if(jList.getSelectedValue()!=null&&!jList.getSelectedValue().equals(myNick)){
                    dos.writeUTF("/w "+jList.getSelectedValue()+" "+messageLine.getText());
                    jList.clearSelection();
                    messageLine.setText("");
                } else {
                dos.writeUTF(messageLine.getText());
                messageLine.setText("");
                }
            } catch (IOException ignored) {
            }
        }
    }

    private void send(String endMessage) {
            try {
                dos.writeUTF(endMessage);
                messageLine.setText("");
            } catch (IOException ignored) {
            }
    }


    private void addNewMemberToMembersList(String message) throws IOException {
        String[] arrNames = message.split("::");
        for (int i =1 ; i<arrNames.length;i++) {
            if(arrNames[i].equals(myNick)){
                continue;
            }
            memberList.addElement(arrNames[i]);
        }
        this.jList.repaint();
        this.jList.updateUI();
    }




}
