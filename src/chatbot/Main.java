package chatbot;

import java.io.FileInputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties cfg = new Properties();
        try (FileInputStream in = new FileInputStream("config.txt")) {
            cfg.load(in);
        }

        String botName   = cfg.getProperty("botName", "ChatBot");
        String serverUrl = cfg.getProperty("serverUrl");
        if (serverUrl == null || serverUrl.isEmpty()) {
            System.err.println("Missing serverUrl in config.txt");
            return;
        }

        ApiClient api = new ApiClient(serverUrl);
        new ChatBot(botName, api).run();
    }
}
