package ru.danilsibgatullin.homeworklesson2.BroadCastChat.serverside.services;

import ru.danilsibgatullin.BroadCastChat.serverside.interfaces.AuthService;
import ru.danilsibgatullin.homeworklesson1.BroadCastChat.serverside.services.BaseAuthService;
import ru.danilsibgatullin.homeworklesson1.BroadCastChat.serverside.services.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final int PORT = 8081;

    private List<ClientHandler> clients;

    private AuthService authService;

    public AuthService getAuthService() {
        return this.authService;
    }

    public MyServer() {
        try (ServerSocket server = new ServerSocket(PORT)){

            authService = new BaseAuthService();
            authService.start();

            clients = new ArrayList<>();

            while (true) {
                System.out.println("Server wait connection");
                Socket socket = server.accept();
                System.out.println(socket.getInetAddress().getCanonicalHostName());
                System.out.println("Client join");
                new ClientHandler(this, socket);
            }

        } catch (IOException e){
            System.out.println("Server fatal error");
        } finally {
            if (authService != null) {
                authService.stop();
            }
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
