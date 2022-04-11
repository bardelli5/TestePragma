public class Game {
    int game;
    Status status;

    public Game() {
    }

    public Game(int game, Status status) {
        this.game = game;
        this.status = status;
    }

    public int getGame() {
        return game;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
