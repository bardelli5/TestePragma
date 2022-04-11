import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Status {
    @SerializedName("total_kills")
    int totalKills;
    List<Player> players = new ArrayList<>();

    public Status() {
    }

    public Status(int totalKills, List<Player> players) {
        this.totalKills = totalKills;
        this.players = players;
    }

    public int getTotalKills() {
        return totalKills;
    }

    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    public void addKill() {
        this.totalKills++;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Optional<Player> getPlayerById(int id) {
        return this.players.stream().filter(p -> p.getId() == id).findFirst();
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
