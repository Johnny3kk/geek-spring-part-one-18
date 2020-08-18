package ru.geekbrains.client;

import java.time.LocalDateTime;

public class TextMessage {

    private LocalDateTime created;

    private String userFrom;

    private String userTo;

    private String text;

    public TextMessage(String userFrom, String userTo, String text, LocalDateTime created) {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.text = text;
        this.created = created;
    }

    public TextMessage(String userFrom, String userTo, String text) {
        this(userFrom, userTo, text, LocalDateTime.now());
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    public String getUserTo() {
        return userTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Это не очень хорошее решение проблемы с использованием класса как на клиенте
     * так и на сервере, но ничего лучше пока не придумал
     */
    public void swapUsers() {
        String tmp = userFrom;
        userFrom = userTo;
        userTo = tmp;
    }
}
