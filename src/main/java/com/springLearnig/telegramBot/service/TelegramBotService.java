package com.springLearnig.telegramBot.service;

import com.springLearnig.telegramBot.config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot {
//   TelegranBotHook...

    final BotConfig botConfig;

    public TelegramBotService(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    @Override
    public String getBotUsername() {
        return botConfig.getName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText() ) {
            String messageText = update.getMessage().getText();
            long chatId=update.getMessage().getChatId();
            switch (messageText){
                case "/start":
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                    break;
                default: sendMessage(chatId, "Sorry. command doesn't recognised");
            }
        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Hi, " + name + ")";
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String messageToSend) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(messageToSend)
                .build();
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.info("Sending message error: ", e);
        }

    }

}
