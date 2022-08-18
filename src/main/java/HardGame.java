public class HardGame extends Game{

    public void setHardGame(){
        matrixXSize = 2;
        matrixYSize = 8;
        attemptsNumber = 15;
    }
    public HardGame() {
        board = new String[2][8];
        cards = new String[2][8];
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
