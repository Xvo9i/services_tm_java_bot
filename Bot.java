// @charset "UTF-8"
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Bot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "Household_services_bot";
    }

    @Override
    public String getBotToken() {
        return "token";
    }

    public Bot() {
        List<BotCommand> botCommands = new ArrayList<>();
        botCommands.add(new BotCommand("/start", "Начало работы"));
        try {
            this.execute(new SetMyCommands(botCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws TelegramApiException {
        Bot bot = new Bot();
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        try {
            botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (messageText.equals("/start")) {
                sendTextMessageWithButtons(chatId, "Добро пожаловать в нашего бота! Здесь вы можете заказать бытовую услугу. Выберите из списка то, что вам нужно, чтобы посмотреть описание и заказать услугу.");
            } else if (messageText.startsWith("Информация: ")) {
                Random random = new Random();
                String[] data = messageText.split(", ");
                if (data.length != 4) {
                    sendTextMessage(chatId, "Неверный формат данных.");
                    return;
                }
                String fioClient = data[0].trim();
                String phoneClient = data[1].trim();
                String mailClient = data[2].trim();
                String idClient = Integer.toString(random.nextInt((99999 - 10000) + 1) + 10000);
                String idService = data[3].trim();

                fioClient.replace("Информация: ", "");

                // Выполнение операции записи в базу данных
                boolean success = insertRecord(fioClient, phoneClient, mailClient, idClient, idService);

                if (success) {
                    sendTextMessage(chatId, "Запись успешно добавлена в базу данных.");
                } else {
                    sendTextMessage(chatId, "Ошибка при добавлении записи в базу данных.");
                }
            }

        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long massageID = update.getCallbackQuery().getMessage().getMessageId();
            long chatID = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals("button1")) {
                String text1 = getTextFromDatabase("Уборка квартиры");
                EditMessageText massage1 = new EditMessageText();
                massage1.setChatId(String.valueOf(chatID));
                massage1.setText(text1);
                massage1.setMessageId((int) massageID);

                InlineKeyboardMarkup mykupInLine = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine = new ArrayList<>();

                var button5 = new InlineKeyboardButton();
                button5.setText("Назад");
                button5.setCallbackData("button5");

                var button6 = new InlineKeyboardButton();
                button6.setText("Стоимость услуги");
                button6.setCallbackData("button6");

                var button7 = new InlineKeyboardButton();
                button7.setText("Записаться на услугу");
                button7.setCallbackData("button7");

                rowInLine.add(button5);
                rowInLine.add(button6);
                rowInLine.add(button7);

                rowsInLine.add(rowInLine);

                mykupInLine.setKeyboard(rowsInLine);
                massage1.setReplyMarkup(mykupInLine);

                try {
                    execute(massage1);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackData.equals("button2")) {
                String text2 = getTextFromDatabase("Ремонт в санузле");
                EditMessageText massage2 = new EditMessageText();
                massage2.setChatId(String.valueOf(chatID));
                massage2.setText(text2);
                massage2.setMessageId((int) massageID);

                InlineKeyboardMarkup mykupInLine = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine = new ArrayList<>();

                var button5 = new InlineKeyboardButton();
                button5.setText("Назад");
                button5.setCallbackData("button5");

                var button8 = new InlineKeyboardButton();
                button8.setText("Стоимость услуги");
                button8.setCallbackData("button8");

                var button7 = new InlineKeyboardButton();
                button7.setText("Записаться на услугу");
                button7.setCallbackData("button7");

                rowInLine.add(button5);
                rowInLine.add(button8);
                rowInLine.add(button7);

                rowsInLine.add(rowInLine);

                mykupInLine.setKeyboard(rowsInLine);
                massage2.setReplyMarkup(mykupInLine);

                try {
                    execute(massage2);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackData.equals("button3")) {
                String text3 = getTextFromDatabase("Ремонт в комнате");
                EditMessageText massage3 = new EditMessageText();
                massage3.setChatId(String.valueOf(chatID));
                massage3.setText(text3);
                massage3.setMessageId((int) massageID);

                InlineKeyboardMarkup mykupInLine = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine = new ArrayList<>();

                var button5 = new InlineKeyboardButton();
                button5.setText("Назад");
                button5.setCallbackData("button5");

                var button9 = new InlineKeyboardButton();
                button9.setText("Стоимость услуги");
                button9.setCallbackData("button9");

                var button7 = new InlineKeyboardButton();
                button7.setText("Записаться на услугу");
                button7.setCallbackData("button7");

                rowInLine.add(button5);
                rowInLine.add(button9);
                rowInLine.add(button7);

                rowsInLine.add(rowInLine);

                mykupInLine.setKeyboard(rowsInLine);
                massage3.setReplyMarkup(mykupInLine);

                try {
                    execute(massage3);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackData.equals("button4")) {
                String text4 = getTextFromDatabase("Доставка еды");
                EditMessageText massage4 = new EditMessageText();
                massage4.setChatId(String.valueOf(chatID));
                massage4.setText(text4);
                massage4.setMessageId((int) massageID);

                InlineKeyboardMarkup mykupInLine = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine = new ArrayList<>();

                var button5 = new InlineKeyboardButton();
                button5.setText("Назад");
                button5.setCallbackData("button5");

                var button10 = new InlineKeyboardButton();
                button10.setText("Стоимость услуги");
                button10.setCallbackData("button10");

                var button7 = new InlineKeyboardButton();
                button7.setText("Записаться на услугу");
                button7.setCallbackData("button7");

                rowInLine.add(button5);
                rowInLine.add(button10);
                rowInLine.add(button7);

                rowsInLine.add(rowInLine);

                mykupInLine.setKeyboard(rowsInLine);
                massage4.setReplyMarkup(mykupInLine);

                try {
                    execute(massage4);
                } catch (
                        TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackData.equals("button5")) {
                sendTextMessageWithButtons2(chatID, "Добро пожаловать в нашего бота! Здесь вы можете заказать бытовую услугу. Выберите из списка то, что вам нужно, чтобы посмотреть описание и заказать услугу.", massageID);

            } else if (callbackData.equals("button6")) {
                String text = getTextFromDatabaseCost("clear");
                EditMessageText massage = new EditMessageText();
                massage.setChatId(String.valueOf(chatID));
                massage.setText(text);
                massage.setMessageId((int) massageID);

                InlineKeyboardMarkup mykupInLine = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine = new ArrayList<>();

                var button5 = new InlineKeyboardButton();
                button5.setText("Назад");
                button5.setCallbackData("button5");

                rowInLine.add(button5);

                rowsInLine.add(rowInLine);

                mykupInLine.setKeyboard(rowsInLine);
                massage.setReplyMarkup(mykupInLine);

                try {
                    execute(massage);
                } catch (
                        TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackData.equals("button7")) {
                String text = "Введите свои данные через запятую и начиная со слов 'Информация:'  (ФИО, телефон, mail, id услуги) ID услуги (Уборка квартиры clear, Ремонт в санузле rem01, Ремонт в комнате rem02, Доставка еды eat03,):";
                EditMessageText massage = new EditMessageText();
                massage.setChatId(String.valueOf(chatID));
                massage.setText(text);
                massage.setMessageId((int) massageID);
                try {
                    execute(massage);
                } catch (
                        TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackData.equals("button8")) {
                String text = getTextFromDatabaseCost("rem01");
                EditMessageText massage = new EditMessageText();
                massage.setChatId(String.valueOf(chatID));
                massage.setText(text);
                massage.setMessageId((int) massageID);

                InlineKeyboardMarkup mykupInLine = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine = new ArrayList<>();

                var button5 = new InlineKeyboardButton();
                button5.setText("Назад");
                button5.setCallbackData("button5");

                rowInLine.add(button5);

                rowsInLine.add(rowInLine);

                mykupInLine.setKeyboard(rowsInLine);
                massage.setReplyMarkup(mykupInLine);

                try {
                    execute(massage);
                } catch (
                        TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackData.equals("button9")) {
                String text = getTextFromDatabaseCost("rem02");
                EditMessageText massage = new EditMessageText();
                massage.setChatId(String.valueOf(chatID));
                massage.setText(text);
                massage.setMessageId((int) massageID);

                InlineKeyboardMarkup mykupInLine = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine = new ArrayList<>();

                var button5 = new InlineKeyboardButton();
                button5.setText("Назад");
                button5.setCallbackData("button5");

                rowInLine.add(button5);

                rowsInLine.add(rowInLine);

                mykupInLine.setKeyboard(rowsInLine);
                massage.setReplyMarkup(mykupInLine);

                try {
                    execute(massage);
                } catch (
                        TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackData.equals("button10")) {
                String text = getTextFromDatabaseCost("eat03");
                EditMessageText massage = new EditMessageText();
                massage.setChatId(String.valueOf(chatID));
                massage.setText(text);
                massage.setMessageId((int) massageID);

                InlineKeyboardMarkup mykupInLine = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
                List<InlineKeyboardButton> rowInLine = new ArrayList<>();

                var button5 = new InlineKeyboardButton();
                button5.setText("Назад");
                button5.setCallbackData("button5");

                rowInLine.add(button5);

                rowsInLine.add(rowInLine);

                mykupInLine.setKeyboard(rowsInLine);
                massage.setReplyMarkup(mykupInLine);

                try {
                    execute(massage);
                } catch (
                        TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    private void sendTextMessageWithButtons(long chatId, String message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);

        InlineKeyboardMarkup mykupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var button1 = new InlineKeyboardButton();
        button1.setText("Уборка квартиры");
        button1.setCallbackData("button1");

        var button2 = new InlineKeyboardButton();
        button2.setText("Ремонт в санузле");
        button2.setCallbackData("button2");

        var button3 = new InlineKeyboardButton();
        button3.setText("Ремонт в комнате");
        button3.setCallbackData("button3");

        var button4 = new InlineKeyboardButton();
        button4.setText("Доставка еды");
        button4.setCallbackData("button4");

        rowInLine.add(button1);
        rowInLine.add(button2);
        rowInLine.add(button3);
        rowInLine.add(button4);

        rowsInLine.add(rowInLine);

        mykupInLine.setKeyboard(rowsInLine);
        sendMessage.setReplyMarkup(mykupInLine);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTextMessageWithButtons2(long chatId, String message, long massageID) {

        String text = message;
        EditMessageText massage = new EditMessageText();
        massage.setChatId(String.valueOf(chatId));
        massage.setText(text);
        massage.setMessageId((int) massageID);

        InlineKeyboardMarkup mykupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var button1 = new InlineKeyboardButton();
        button1.setText("Уборка квартиры");
        button1.setCallbackData("button1");

        var button2 = new InlineKeyboardButton();
        button2.setText("Ремонт в санузле");
        button2.setCallbackData("button2");

        var button3 = new InlineKeyboardButton();
        button3.setText("Ремонт в комнате");
        button3.setCallbackData("button3");

        var button4 = new InlineKeyboardButton();
        button4.setText("Доставка еды");
        button4.setCallbackData("button4");

        rowInLine.add(button1);
        rowInLine.add(button2);
        rowInLine.add(button3);
        rowInLine.add(button4);

        rowsInLine.add(rowInLine);

        mykupInLine.setKeyboard(rowsInLine);
        massage.setReplyMarkup(mykupInLine);

        try {
            execute(massage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendTextMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    private Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5433/tm_bot_bd";
        String username = "postgres";
        String password = "5032";

        logger.info("Попытка подключения к базе данных...");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            logger.info("Подключение к базе данных выполнено успешно.");
            return connection;
        } catch (SQLException e) {
            logger.error("Ошибка при подключении к базе данных: {}", e.getMessage());
            throw e;
        }
    }

    private String getTextFromDatabase(String type) {
        String text = "Текст не найден";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT description FROM Service WHERE name_service = ?")) {

            // Установка значения параметра запроса
            statement.setString(1, type);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                text = resultSet.getString("description");
            }
        } catch (SQLException e) {
            logger.error("Ошибка при выполнении запроса в базе данных: {}", e.getMessage());
        }

        return text;
    }

    private String getTextFromDatabaseCost(String type) {
        String text = "Текст не найден";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT description_cost FROM Cost WHERE code = ?")) {

            // Установка значения параметра запроса
            statement.setString(1, type);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                text = resultSet.getString("description_cost");
            }
        } catch (SQLException e) {
            logger.error("Ошибка при выполнении запроса в базе данных: {}", e.getMessage());
        }

        return text;
    }

    private boolean insertRecord(String fioClient, String phoneClient, String mailClient, String idClient, String Code) {
        try (Connection connection = getConnection();
             PreparedStatement clientStatement = connection.prepareStatement(
                     "INSERT INTO Client (id_client, fio_client, phone_client, mail_client, code) VALUES (?, ?, ?, ?, ?)")){

            // Вставка данных в таблицу Client
            clientStatement.setString(2, fioClient);
            clientStatement.setString(3, phoneClient);
            clientStatement.setString(4, mailClient);
            clientStatement.setString(1, idClient);
            clientStatement.setString(5, Code);
            clientStatement.executeUpdate();

            logger.info("Запись успешно добавлена в таблицу Client.");
            return true;
        } catch (SQLException e) {
            logger.error("Ошибка при вставке записи в таблицу Client: {}", e.getMessage());
            return false;
        }
    }
}
