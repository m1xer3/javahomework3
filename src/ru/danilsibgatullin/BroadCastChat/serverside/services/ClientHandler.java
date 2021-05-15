package ru.danilsibgatullin.BroadCastChat.serverside.services;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean isAuthorized;
    private boolean isTimeOutCloseConnection;
    private boolean shouldKillTimeoutMessage;
    private String name;
    private final ExecutorService execPool= Executors.newCachedThreadPool(); // добавление запуска потоков чере ExecutorService


    public ClientHandler(MyServer myServer, Socket socket) {
        try {

            this.myServer = myServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.name = "";

            myServer.addExecuteTreadInExecPool(new Thread(() -> {
                try {
                    authentication();
                    if (isAuthorized){
                        readMessage();
                    }
                } catch (IOException ignored) {
                    ignored.printStackTrace();
                } finally {
                    if(!isTimeOutCloseConnection){
                        closeConnection();
                    }
                }
            }));
            /*Закрываем соединение на стороне сервера если прошло 120 секунд
            * после подключения но пользователь не авторизовался
            */
            myServer.addExecuteTreadInExecPool(new Thread(() -> {
                try {
                    Thread.sleep(30000);
                    System.out.println("Stream up");
                    if (!isAuthorized) {
                        System.out.println("Timeout close connection");
                        closeConnection();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ));

        } catch (IOException e) {
            closeConnection();
            throw new RuntimeException("Problem with ClientHandler");
        }
    }


    public void authentication() throws IOException {

            String str = dis.readUTF();
            if (str.startsWith("/auth")) { //  /auth login password
                String[] arr = str.split("\\s");

                //проверка авторизация через БД
                String nick = getNickIfAuthorityIsOK(arr[1], arr[2]);

                if (nick != null) {
                    if (!myServer.isNickBusy(nick)) {
                        isAuthorized = true;
                        sendMessage("/authok " + nick);
                        name = nick;
                        myServer.broadcastMessage("/list::"+name);// после успешной авторизации посылаем команду всем что бы добавили к себе в лист контактов
                        myServer.broadcastMessage("Hello " + name);
                        myServer.subscribe(this);
                        return;
                    } else {
                        sendMessage("/nickbusy");
                    }

                } else {
                    System.out.println("Client access deny");
                    sendMessage("Wrong login and password");
                }
            }
    }

    public void readMessage() throws IOException {
        while (true) {
            try {
                //TODO как через ExecutroService отсановить 1 конкретный поток
                Thread tread = new Thread(()->{
                    try {
                        Thread.sleep(30000);
                        System.out.println("Timeout for enter message");
                        if(shouldKillTimeoutMessage){
                            sendMessage("/finish");
                            isTimeOutCloseConnection=true;
                            closeConnection();
                            return;
                        }
                    } catch (InterruptedException ignore) {
                    }
                });
                Future f1 = myServer.addSubmitTreadInExecPool(tread);
                shouldKillTimeoutMessage=true;
                String messageFromClient = dis.readUTF();
                shouldKillTimeoutMessage=false;
                    f1.cancel(true);
                System.out.println(name + " send message " + messageFromClient);
                if(messageFromClient.startsWith("/")) {
                    if (messageFromClient.equals("/end")) {
                        System.out.println("Client live server");
                        return;
                    }
                    if (messageFromClient.startsWith("/w")) {
                        String[] arr = messageFromClient.split("\\s");
                        myServer.whisperMessage(this, arr[1], name + "(whispered): " + messageFromClient.substring(3 + arr[1].length()));
                    }
                    if(messageFromClient.equals("/list")){
                        myServer.getChatMembers();
                    }
                    if(messageFromClient.startsWith("/change")){
                        String[] arr = messageFromClient.split("::");
                        String oldNick = name;
                        changeUserNick(arr[1]);
                        myServer.getChatMembers();
                        myServer.broadcastMessage("User "+oldNick+" change nick to " + name);
                    }
                }
                else {
                    myServer.broadcastMessage(name + ": " + messageFromClient);
                }
            }catch (SocketException e){
                System.out.println("Client live server");
                return;
            }
        }
    }

    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException ignored) {
        }
    }

    private void closeConnection() {
        myServer.unsubscribe(this);
        myServer.broadcastMessage(name + " Leave chat");
        try {
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException ignored) {
        }
    }

    private String getNickIfAuthorityIsOK(String login, String password){
        Statement statement = null;
        String nick =null;
        try{
            statement = ConnectDB.getConnectDB().createStatement();
            ResultSet set = statement.executeQuery("SELECT nick FROM USERS WHERE LOGIN = '"+login+"' and PASSWORD ='"+password+"'");
            while(set.next()){
                nick= set.getString("nick");
            }
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            if (statement != null){
                try{
                    statement.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        return nick;
    };

    private void changeUserNick (String newNick){
        if(myServer.isNickBusy(newNick)){
            sendMessage("/nickbusy");
            return;
        }
        Statement statement = null;
        try{
            statement = ConnectDB.getConnectDB().createStatement();
            statement.executeUpdate("UPDATE USERS SET nick ='"+newNick+"' WHERE nick ='"+name+"'");
            name = newNick;
            sendMessage("/nickchangeok::"+name);
        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        finally {
            if(statement!=null){
                try{
                    statement.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public String getName() {
        return name;
    }
}
