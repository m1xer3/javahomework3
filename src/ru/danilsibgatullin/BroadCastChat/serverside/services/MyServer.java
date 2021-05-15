package ru.danilsibgatullin.BroadCastChat.serverside.services;




import ru.danilsibgatullin.BroadCastChat.serverside.interfaces.AuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyServer {

    private static final Logger logger = LogManager.getLogger(MyServer.class);



    private final int PORT = 8081;

    private List<ClientHandler> clients;

    private final ExecutorService execPool= Executors.newCachedThreadPool(); // добавление запуска потоков чере ExecutorService

    private AuthService authService;

    public AuthService getAuthService() {
        return this.authService;
    }

    public ExecutorService getExecPool(){
        return execPool;
    }

    public void addExecuteTreadInExecPool(Thread t){
        execPool.execute(t);
    }

    public Future addSubmitTreadInExecPool(Thread t){

       return  execPool.submit(t);
    }


    public MyServer() {
        try (ServerSocket server = new ServerSocket(PORT)){

            authService = new BaseAuthService();
            authService.start();

            clients = new ArrayList<>();

            while (true) {
                //System.out.println("Server wait connection");
                logger.info("Server wait connection");
                Socket socket = server.accept();
                //System.out.println(socket.getInetAddress().getCanonicalHostName());
                logger.info(socket.getInetAddress().getCanonicalHostName());
                //System.out.println("Client join");
                logger.info("Client join");
                new ClientHandler(this, socket);
            }

        } catch (IOException e){
            //System.out.println("Server fatal error");
            logger.error("Server fatal error");
        } finally {
            if (authService != null) {
                authService.stop();
            }
            execPool.shutdown();
        }
    }

    public synchronized void broadcastMessage(String message) {
        for (ClientHandler c : clients) {
            c.sendMessage(message);
        }
    }

    public synchronized void whisperMessage(ClientHandler author,String name,String message) {
        author.sendMessage(message);
        for (ClientHandler c : clients) {
            if (c.getName().equals(name)){
                c.sendMessage(message);
            }
        }
    }

    public synchronized void getChatMembers(){
        StringBuilder strBild = new StringBuilder();
        strBild.append("/list::");
        for (ClientHandler c:clients) {
            strBild.append(c.getName()+"::");
        }
        broadcastMessage(strBild.toString());
    }


    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
        getChatMembers();
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler c : clients) {
            if (c.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }
}
