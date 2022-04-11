import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;


import static java.util.stream.Collectors.toList;

public class LogParser {
    BufferedReader reader;
    List<Game> games = new ArrayList<>();

    public LogParser(String fileName) {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
        this.reader = new BufferedReader(isr);
    }

    public List<Game> parseFile() {
        try {
            for (String line; (line = reader.readLine()) != null;) {
                ArrayList<String> lineSplited = new ArrayList<>(Arrays.stream(line.split(" ")).collect(toList()));
                lineSplited.removeIf(s -> Objects.equals(s, ""));

                switch (lineSplited.get(1)) {
                    case "InitGame:" -> handleInitGame();
                    case "ClientConnect:" -> handleClientConnect(lineSplited);
                    case "ClientUserinfoChanged:" -> handleClientUserinfoChanged(lineSplited);
                    case "Kill:" -> handleKill(lineSplited);
                }
            }

            return games;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return games;
    }

    private void handleKill(List<String> lineSplited) {
        int killer = Integer.parseInt(lineSplited.get(2));
        int killed = Integer.parseInt(lineSplited.get(3));

        lastGame().getStatus().addKill();

        // 1022 = <world>
        if (killer == 1022) {
            Player playerKilled = lastGame().getStatus().getPlayerById(killed)
                    .orElseThrow(() -> new IllegalArgumentException("Player not found"));
            playerKilled.subtractKill();
        } else {
            Player playerKiller = lastGame().getStatus().getPlayerById(killer)
                    .orElseThrow(() -> new IllegalArgumentException("Player not found"));

            playerKiller.addKill();
        }
    }

    private void handleClientUserinfoChanged(List<String> lineSplited) {
        Player player = lastGame().getStatus().getPlayerById(Integer.parseInt(lineSplited.get(2)))
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        StringBuilder sb = new StringBuilder();
        for (int i = 3; i <= lineSplited.size(); i++) {
            String unparsedName = lineSplited.get(i);
            if (unparsedName.contains("n\\") && i == 3) {
                unparsedName = unparsedName.substring(2);
            }

            if (unparsedName.contains("\\t\\")) {
                unparsedName = unparsedName.replace("\\", "\\\\").split("\\\\")[0];

                sb.append(unparsedName);
                break;
            }

            sb.append(unparsedName);
            sb.append(" ");
        }

        String name = sb.toString();

        if (player.getName() != null && !Objects.equals(player.getName(), name)) player.nameToOldName();
        player.setName(name);
    }

    private void handleClientConnect(List<String> lineSplited) {
        int id = Integer.parseInt(lineSplited.get(lineSplited.size() - 1));

        if (lastGame().getStatus().getPlayerById(id).isPresent()) return;

        Player player = new Player(id);
        lastGame().getStatus().addPlayer(player);
    }

    private void handleInitGame() {
        Game game = new Game(games.size() + 1, new Status());
        games.add(game);
    }

    private Game lastGame() {
        return games.get(games.size() - 1);
    }
}
