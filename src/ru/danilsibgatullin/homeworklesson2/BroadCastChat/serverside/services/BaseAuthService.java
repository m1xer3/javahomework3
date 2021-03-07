package ru.danilsibgatullin.homeworklesson2.BroadCastChat.serverside.services;

import ru.danilsibgatullin.homeworklesson2.BroadCastChat.serverside.interfaces.AuthService;

import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private List<Entry> entryList;

    public BaseAuthService() {

    }

    @Override
    public void start() {
        System.out.println("AuthService start");
    }

    @Override
    public void stop() {
        System.out.println("AuthService stop");
    }



    private class Entry {
        private String login;
        private String password;
        private String nick;

        public Entry(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }
    }
}
