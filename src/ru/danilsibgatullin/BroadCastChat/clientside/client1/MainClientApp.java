package ru.danilsibgatullin.BroadCastChat.clientside.client1;

import ru.danilsibgatullin.BroadCastChat.clientside.ui.AutorityInterface;

import javax.swing.*;
import java.io.IOException;

public class MainClientApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            try {
                new AutorityInterface();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
