package org.example.bot;

import org.example.utils.files.FileUtils;
import org.example.utils.images.RGBMaster;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        PhotoSize photoSize = message.getPhoto().get(3);
        String fileId = photoSize.getFileId();

        try {
            org.telegram.telegrambots.meta.api.objects.File file = sendApiMethod(new GetFile(fileId));
            String imageURL = "https://api.telegram.org/file/bot" + getBotToken() + "/" + file.getFilePath();
            FileUtils.saveImageByURL(imageURL, "images/image.png");
            FileUtils.processImage("images/image.png", RGBMaster::greyScale);
        }

        catch(TelegramApiException | IOException e) {
            throw new RuntimeException();
        }

        InputFile inputFile = new InputFile();
        inputFile.setMedia(new File("images/image.png"));

        SendPhoto sendPhoto = new SendPhoto();

        sendPhoto.setChatId(message.getChatId());
        sendPhoto.setPhoto(inputFile);

        try {
            execute(sendPhoto);
        }

        catch(TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Image filter bot";
    }

    @Override
    public String getBotToken() {
        return "6354700287:AAHTtdQYxk0Wz9MGdSetQn5c_Td6YP-DkDU";
    }
}
