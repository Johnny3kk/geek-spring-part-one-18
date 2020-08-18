package ru.geekbrains.client.swing;

import javax.swing.*;

public class ChatSwingApp {

    private static ChatMainWindow chatMainWindow;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chatMainWindow = new ChatMainWindow();
            }
        });
    }
}
