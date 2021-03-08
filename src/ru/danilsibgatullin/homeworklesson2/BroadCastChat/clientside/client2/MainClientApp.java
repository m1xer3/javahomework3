package ru.danilsibgatullin.homeworklesson2.BroadCastChat.clientside.client2;

import ru.danilsibgatullin.homeworklesson2.BroadCastChat.clientside.ui.AutorityInterface;

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
