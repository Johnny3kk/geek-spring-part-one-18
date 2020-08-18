package ru.geekbrains.client.swing;

import ru.geekbrains.client.MessageReciever;
import ru.geekbrains.client.Network;
import ru.geekbrains.client.TextMessage;
import ru.geekbrains.client.history.ChatHistory;
import ru.geekbrains.client.history.ChatHistoryTextFileImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ChatMainWindow extends JFrame implements MessageReciever {

    private final JList<TextMessage> messageList;

    private final DefaultListModel<TextMessage> messageListModel;

    private final TextMessageCellRenderer messageCellRenderer;

    private final JScrollPane scroll;

    private final JPanel sendMessagePanel;

    private final JButton sendButton;

    private final JTextField messageField;

    private final JList<String> userList;

    private final DefaultListModel<String> userListModel;

    private final Network network;

    private ChatHistory chatHistory;

    public ChatMainWindow() {
        setTitle("Сетевой чат.");
        setBounds(200,200, 500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        messageList = new JList<>();
        messageListModel = new DefaultListModel<>();
        messageCellRenderer = new TextMessageCellRenderer();
        messageList.setModel(messageListModel);
        messageList.setCellRenderer(messageCellRenderer);

        scroll = new JScrollPane(messageList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll, BorderLayout.CENTER);

        sendMessagePanel = new JPanel();
        sendMessagePanel.setLayout(new BorderLayout());

        sendButton = new JButton("Отправить");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = messageField.getText();
                String userTo = userList.getSelectedValue();
                if (userTo == null) {
                    JOptionPane.showMessageDialog(ChatMainWindow.this,
                            "Ошибка",
                            "Не выбран пользователь",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (text != null && !text.trim().isEmpty()) {
                    TextMessage msg = new TextMessage(network.getLogin(), userTo, text);
                    messageListModel.add(messageListModel.size(), msg);
                    messageField.setText(null);
                    network.sendTextMessage(msg);
                    chatHistory.addMessage(msg);
                }
            }
        });
        sendMessagePanel.add(sendButton, BorderLayout.EAST);
        messageField = new JTextField();
        sendMessagePanel.add(messageField, BorderLayout.CENTER);

        add(sendMessagePanel, BorderLayout.SOUTH);

        userList = new JList<>();
        userListModel = new DefaultListModel<>();
        userList.setModel(userListModel);
        userList.setPreferredSize(new Dimension(100, 0));
        add(userList, BorderLayout.WEST);

        setVisible(true);

        this.network = new Network("localhost", 7777, this);

        LoginDialog loginDialog = new LoginDialog(this, network);
        loginDialog.setVisible(true);

        if (!loginDialog.isConnected()) {
            System.exit(0);
        }

        this.network.requestConnectedUserList();
        try {
            this.chatHistory = new ChatHistoryTextFileImpl(network.getLogin());
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(ChatMainWindow.this,
                    "Ошибка",
                    "Не запускается сервис истории сообщений",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        List<TextMessage> lastMessages = this.chatHistory.getLastMessages(5);
        for (TextMessage msg : lastMessages) {
            messageListModel.add(messageListModel.size(), msg);
        }

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (network != null) {
                    network.close();
                }
                if (chatHistory != null) {
                    chatHistory.flush();
                }
                super.windowClosing(e);
            }
        });

        setTitle("Сетевой чат. Пользователь " + network.getLogin());
    }

    @Override
    public void submitMessage(TextMessage message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                messageListModel.add(messageListModel.size(), message);
                messageList.ensureIndexIsVisible(messageListModel.size() - 1);
                chatHistory.addMessage(message);
            }
        });
    }

    @Override
    public void userConnected(String login) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int ix = userListModel.indexOf(login);
                if (ix == -1) {
                    userListModel.add(userListModel.size(), login);
                }
            }
        });
    }

    @Override
    public void userDisconnected(String login) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int ix = userListModel.indexOf(login);
                if (ix >= 0) {
                    userListModel.remove(ix);
                }
            }
        });
    }

    @Override
    public void updateUserList(Set<String> users) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                userListModel.clear();
                for (String usr : users) {
                    userListModel.addElement(usr);
                }
            }
        });
    }
}
