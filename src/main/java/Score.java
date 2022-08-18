public class Score extends Game {

    String userName;
    int time;

    public String getUserName() {
        return userName;
    }

    public int getTime() {
        return time;
    }

    public Score(String userName, int time) {
        this.userName = userName;
        this.time = time;
    }
}


