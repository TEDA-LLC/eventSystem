package com.example.eventsystem.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramService extends TelegramWebhookBot {
    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return "5432072116:AAHHjQHDP-IBzzQdiRyzHhqValr5tKQ6tlI";
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
