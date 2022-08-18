public class EasyGame extends Game {

    public void setEasyGame() {
        matrixXSize = 2;
        matrixYSize = 4;
        attemptsNumber = 10;
    }

    public EasyGame() {
        board = new String[2][4];
        cards = new String[2][4];
    }

    public int getMatrixXSize() {
        return matrixXSize;
    }

    public void setMatrixXSize(int matrixXSize) {
        this.matrixXSize = matrixXSize;
    }

    public int getMatrixYSize() {
        return matrixYSize;
    }

    public void setMatrixYSize(int matrixYSize) {
        this.matrixYSize = matrixYSize;
    }

    public int getAttemptsNumber() {
        return attemptsNumber;
    }

    public void setAttemptsNumber(int attemptsNumber) {
        this.attemptsNumber = attemptsNumber;
    }
}

