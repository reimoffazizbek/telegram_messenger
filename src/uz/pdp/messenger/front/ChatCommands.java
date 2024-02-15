package uz.pdp.messenger.front;

public interface ChatCommands {
    String settings = "/set";
    String deleteMessage = "/d";
    String edit = "/e";
    String like = "/l";
    String[] likes = {"👍", "👎", "☺️", "😁", "😄", "🔥", "❗"};
    String quit = "/q";
    String commandsText = "Commands : " + settings + ", " + edit + ", " + deleteMessage + ", " + quit + ", " + like;
}
