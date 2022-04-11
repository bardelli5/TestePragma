import java.util.ArrayList;
import java.util.List;

public class Player {
    int id;
    String name;
    int kills = 0;
    List<String> oldNames = new ArrayList<>();

    public Player(){}

    public Player(int id) {
        this.id = id;
    }

    public Player(int id, String name, int kills, List<String> oldNames) {
        this.id = id;
        this.name = name;
        this.kills = kills;
        this.oldNames = oldNames;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void addKill() {
        this.kills++;
    }

    public void subtractKill() {
        this.kills--;
    }

    public List<String> getOldNames() {
        return oldNames;
    }

    public void setOldNames(List<String> oldNames) {
        this.oldNames = oldNames;
    }

    public void nameToOldName() {
        this.oldNames.add(this.name);
    }
}
