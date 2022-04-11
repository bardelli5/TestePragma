import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LogParser parser = new LogParser("Quake.txt");

        List<Game> games = parser.parseFile();

        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        try {
            File file = new File(System.getProperty("user.dir") + "/games.json");
            PrintWriter pw = new PrintWriter(file);
            pw.print(gson.toJson(games));
            pw.close();

            System.out.println(gson.toJson(games));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
