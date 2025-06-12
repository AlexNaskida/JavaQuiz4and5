package chatbot;

import java.util.Scanner;

public class ChatBot {
    private final String botName;
    private final ApiClient apiClient;
    private final Scanner scanner;

    public ChatBot(String botName, ApiClient apiClient) {
        this.botName = botName;
        this.apiClient = apiClient;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        System.out.println(botName + ": Hello! I'm " + botName + ". How can I assist you today?");
        while (true) {
            System.out.println("\nCommands: create, view posts, stats, exit");
            System.out.print("> ");
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "create":
                    handleCreatePost();
                    break;
                case "view posts":
                    handleViewPosts();
                    break;
                case "stats":
                    handleStats();
                    break;
                case "exit":
                    System.out.println(botName + ": Goodbye!");
                    return;
                default:
                    System.out.println(botName + ": I'm sorry, I didn't understand that command.");
            }
        }
    }

    private void handleCreatePost() {
        System.out.print("Enter post title: ");
        String title = scanner.nextLine();
        System.out.print("Enter post body: ");
        String body = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        try {
            String response = apiClient.createPost(title, body, author);
            System.out.println(botName + ": " + response);
        } catch (Exception e) {
            System.out.println(botName + ": Error creating post: " + e.getMessage());
        }
    }

    private void handleViewPosts() {
        try {
            String response = apiClient.listPostsJson();
            System.out.println(botName + ": Posts:\n" + response);
        } catch (Exception e) {
            System.out.println(botName + ": Error retrieving posts: " + e.getMessage());
        }
    }

    private void handleStats() {
        try {
            String response = apiClient.getStatsJson();
            System.out.println(botName + ": Stats:\n" + response);
        } catch (Exception e) {
            System.out.println(botName + ": Error retrieving stats: " + e.getMessage());
        }
    }
}
