package ru.geekbrains.client.history;

import ru.geekbrains.client.TextMessage;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChatHistoryTextFileImpl implements ChatHistory {

    private static final String HISTORY_FILE_TEMPLATE = "%s-message-history.txt";
    private static final String MSG_PATTERN = "%s\t%s\t%s\t%s";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final File file;
    private final PrintWriter historyWriter;

    public ChatHistoryTextFileImpl(String login) throws IOException {
        file = new File(String.format(HISTORY_FILE_TEMPLATE, login));

        if (!file.exists()) {
            file.createNewFile();
        }
        historyWriter = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file, true)));
    }

    @Override
    public synchronized void addMessage(TextMessage message) {
        String msg = String.format(MSG_PATTERN, message.getCreated().format(DATE_FORMATTER),
                message.getUserFrom(), message.getUserTo(), message.getText());
        historyWriter.println(msg);
    }

    @Override
    public List<TextMessage> getLastMessages(int count) {
        List<String> msgs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.ready()) {
                msgs.add(reader.readLine());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        List<TextMessage> res = new ArrayList<>();
        if (msgs.size() > count) {
            msgs = msgs.subList(msgs.size() - count, msgs.size());
        }
        for (String str : msgs) {
            res.add(parseMsg(str));
        }
        return res;
    }

    @Override
    public void flush() {
        historyWriter.flush();
    }

    private TextMessage parseMsg(String str) {
        String[] split = str.split("\t", 4);
        return new TextMessage(split[1], split[2], split[3], LocalDateTime.parse(split[0], DATE_FORMATTER));
    }
}
