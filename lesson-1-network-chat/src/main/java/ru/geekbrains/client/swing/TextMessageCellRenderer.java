package ru.geekbrains.client.swing;

import ru.geekbrains.client.TextMessage;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TextMessageCellRenderer extends JPanel implements ListCellRenderer<TextMessage> {

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final JLabel created;

    private final JLabel userName;

    private final JTextArea messageText;

    private final JPanel panel;

    public TextMessageCellRenderer() {
        super();
        setLayout(new BorderLayout());

        created = new JLabel();
        userName = new JLabel();
        messageText = new JTextArea();
        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.add(created);
        panel.add(userName);

        Font f = userName.getFont();
        userName.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setEditable(false);

        add(panel, BorderLayout.NORTH);
        add(messageText, BorderLayout.SOUTH);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends TextMessage> list,
                                                  TextMessage value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setBackground(list.getBackground());
        created.setText(value.getCreated().format(timeFormatter));
        userName.setText(value.getUserFrom());
        messageText.setText(value.getText());
        return this;
    }
}
