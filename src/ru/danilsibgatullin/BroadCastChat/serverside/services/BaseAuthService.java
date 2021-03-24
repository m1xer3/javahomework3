package ru.danilsibgatullin.BroadCastChat.serverside.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.danilsibgatullin.BroadCastChat.serverside.interfaces.AuthService;

import java.util.List;

public class BaseAuthService implements AuthService {
    private List<Entry> entryList;

    private static final Logger logger = LogManager.getLogger(BaseAuthService.class);

    public BaseAuthService() {

    }

    @Override
    public void start() {
        logger.info("AuthService start");
        //System.out.println("AuthService start");
    }

    @Override
    public void stop() {
        logger.info("AuthService stop");
        //System.out.println("AuthService stop");
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
