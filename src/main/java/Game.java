import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.time.Duration;
import java.time.Instant;

public abstract class Game {

    private static final String X_String = "X";
    private static final String Y_String = " | ";
    public static String[][] board;
    public static String[][] cards;
    protected String nickName;
    protected int bestTime;
    protected int matrixXSize;
    protected int matrixYSize;
    protected int attemptsNumber;
    protected ArrayList<String> words = new ArrayList<>();
    protected Map<String, Map<String, Integer>> bestResults = new HashMap<>();
    public static Scanner scanner = new Scanner(System.in);

    //     point 1: loading words from text file to list
    public void loadWords() {
        File file = new File("/Users/Tomek/IdeaProjects/Projekt_Motorola/Words.txt");
        Scanner readFileScanner = null;
        try {
            readFileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException thrown!!!!");
            throw new RuntimeException(e);
        }
        while (readFileScanner.hasNextLine()) {
            words.add(readFileScanner.nextLine());
        }
    }
    //    public void loadWords() {
//        Scanner readFileScanner;
//        try {
//            File file = getFileFromResource("Words.txt");
//            readFileScanner = new Scanner(file);
//        } catch (Exception e) {
//            System.out.println("FileNotFoundException thrown!!!!");
//            throw new RuntimeException(e);
//        }
//
//        while(readFileScanner.hasNextLine()) {
//            words.add(readFileScanner.nextLine());
//        }
//    }
//    public List<String> getWords() {
//        return words;
//    }
    //printing board to game
    public void printBoard() {
        for (int i = 0; i < matrixXSize; i++) {

            for (int j = 0; j < matrixYSize; j++) {
                System.out.print(board[i][j]);
                System.out.print(Y_String);
            }
            System.out.println();
        }
    }
    public String[][] shuffleCards() {
        Random random = new Random();
        List<Integer> chosenIndexes = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {//indexes words
            indexes.add(i);
        }
        for (int i = 0; i < matrixYSize; i++) {
            int chosenIndex = random.nextInt(0, indexes.size());//draw index, which later use in list words
            chosenIndexes.add(chosenIndex);//adding
            indexes.remove(chosenIndex);//removing
        }
        List<String> chosenWords = new ArrayList<>();//index - each element from my list indexes
        for (int index : chosenIndexes) {
            chosenWords.add(words.get(index));
        }
        List<Map<Integer, Integer>> boardIndexes = new ArrayList<>();

        for (int i = 0; i < matrixXSize; i++) {
            for (int j = 0; j < matrixYSize; j++) {
                Map<Integer, Integer> boardIndex = new HashMap<>();
                boardIndex.put(i, j);
                boardIndexes.add(boardIndex);
            }
        }
        for (String word : chosenWords) { //
            int firstIndex = random.nextInt(0, boardIndexes.size()); //first place to put word
            for (int key : boardIndexes.get(firstIndex).keySet()) {
                int a = key;  // first number from first pair
                int b = boardIndexes.get(firstIndex).get(a);  //second number from first pair
                cards[a][b] = word;
            }
            boardIndexes.remove(firstIndex);
            int secondIndex = random.nextInt(0, boardIndexes.size()); //duplikat
            for (int key : boardIndexes.get(secondIndex).keySet()) {
                int a = key;
                int b = boardIndexes.get(secondIndex).get(a);
                cards[a][b] = word;
            }
            boardIndexes.remove(secondIndex);
        }
        return cards;
    }
    //playing game
    public void checkInput(String[][] cards) {
        String artCorrect =
                " ┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌┌┌█████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌┌███████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌███┌┌┌██┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌██┌┌┌┌██┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌███┌┌┌┌███┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌███┌┌┌┌███┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌██┌┌┌┌┌███┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌███┌┌┌┌┌███┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌┌███┌┌┌┌┌████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌┌┌██┌┌┌┌┌┌████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌┌┌███┌┌┌┌┌┌┌███┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌┌┌┌███┌┌┌┌┌┌┌███┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌┌┌┌███┌┌┌┌┌┌┌┌███┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌┌┌┌┌██┌┌┌┌┌┌┌┌┌┌███┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌███┌███┌┌┌┌┌┌┌┌┌┌██┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌████████████┌┌┌┌┌┌┌┌┌┌┌███┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌████████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌███┌┌┌┌┌┌┌┌┌\n" +
                " ┌███┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌█████████┌┌\n" +
                " ███┌┌┌┌█████████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌█████┌\n" +
                " ██┌┌┌███████┌████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌███┌\n" +
                " ██┌┌┌┌███┌┌┌┌┌┌┌███┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌██┌\n" +
                " ███┌┌┌┌┌┌┌┌┌┌┌█████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌██┌\n" +
                " ┌███┌┌┌┌┌┌┌████████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌██┌\n" +
                " ┌┌████████████┌┌┌████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌██┌\n" +
                " ┌███┌██████┌┌┌┌┌┌┌████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌██┌\n" +
                " ┌███┌┌┌┌┌┌┌┌┌┌┌██████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌██┌\n" +
                " ┌┌████┌████┌██████████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌██┌\n" +
                " ┌┌┌████████████┌┌┌┌┌███┌┌┌┌┌┌┌┌┌┌┌┌┌███┌\n" +
                " ┌┌┌┌██┌┌┌┌┌┌┌┌┌┌┌███████┌┌┌┌┌┌┌███████┌┌\n" +
                " ┌┌┌┌████┌┌┌┌┌┌████████┌┌┌┌┌┌┌┌████████┌┌\n" +
                " ┌┌┌┌┌████████████┌┌┌███┌┌┌┌┌███┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌███┌█┌█┌┌┌┌┌┌███┌┌┌███┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌███┌┌┌┌┌┌█████┌┌█████┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌██████████████████┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌┌██████████████┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌\n" +
                " ┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌┌";
        Instant start = Instant.now();
        while (true) {
            //downloading information from the user
            if (!gameOver()) {
                System.out.println("Row (1-" + matrixXSize + ")");
                int row1 = scanner.nextInt();
                if (row1 != 1 && row1 != matrixXSize){
                    System.out.println("Invalid character");
                    continue;
                }
                System.out.println("Column (1-" + matrixYSize + ")");
                int column1 = scanner.nextInt();
                if (!board[row1 - 1][column1 - 1].equals(X_String)) {
                    System.out.println("Already Entered");
                    board[row1 - 1][column1 - 1] = X_String;
                    System.out.println();
                    printBoard();
                    continue;
                } else {
                    board[row1 - 1][column1 - 1] = " " + cards[row1 - 1][column1 - 1] + " ";
                    printBoard();
                }
                System.out.println("Row (1-" + matrixXSize + ")");
                int row2 = scanner.nextInt();
                if (row2 != 1 && row2 != matrixXSize){
                    System.out.println("Invalid character");
                    continue;
                }
                System.out.println("Column (1-" + matrixYSize + ")");
                int column2 = scanner.nextInt();
                if (!board[row2 - 1][column2 - 1].equals(X_String)) {
                    System.out.println("Already Entered");
                    board[row1 - 1][column1 - 1] = X_String;
                    System.out.println();
                    printBoard();
                    continue;
                } else {
                    //checking elements
                    board[row2 - 1][column2 - 1] = " " + cards[row2 - 1][column2 - 1] + " ";
                    if (board[row1 - 1][column1 - 1].equals(board[row2 - 1][column2 - 1])) {
                        printBoard();
                        System.out.println("Correct ! ");
                        System.out.println("");
                    } else {
                        printBoard();
                        System.out.println("False");
                        board[row1 - 1][column1 - 1] = X_String;
                        board[row2 - 1][column2 - 1] = X_String;
                        printBoard();
                    }
                    attemptsNumber--;
                    System.out.println("Number of chances to guess is: " + attemptsNumber);
                    if (attemptsNumber == 0) {
                        break;
                    } else {
                        continue;
                    }
                }
            } else {
                System.out.println(artCorrect);
                System.out.println("Game Over !!");

            }
            break;
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Game duration: "+ timeElapsed.toMillis() /1000 +" seconds");
        System.out.println("");
    }

    public void fillBoard() {
        for (int i = 0; i < matrixXSize; i++) {
            for (int j = 0; j < matrixYSize; j++) {
                Arrays.fill(board[i], X_String);
            }
        }
    }

    public boolean gameOver() {
        for (int i = 0; i < matrixXSize; i++) {
            for (int j = 0; j < matrixYSize; j++) {
                if (board[i][j].equals(X_String)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void restartGame(){
        boolean shouldContinue = true;
        if (attemptsNumber == 0){
            System.out.println("You lost, do you want to play one more ? ");
        } else {
            System.out.println("You won, do you want to play one more ?");
        }
        while (shouldContinue){
            System.out.println("Press e for Easy level: 4 randomly selected words to discover, 10 chances ");
            System.out.println("Press h for Hard: 8 randomly selected word pairs, 15 chances");
            System.out.println("Press q Quit");

            String ehq = scanner.nextLine();
            if (ehq.equals("q")) {
                System.out.println("Exiting");
                break;
            } else if (ehq.equals("e")) {
                EasyGame easyGame = new EasyGame();
                easyGame.setEasyGame();
                easyGame.fillBoard();
                easyGame.loadWords();
                easyGame.printBoard();
                easyGame.checkInput(easyGame.shuffleCards());
//                easyGame.loadScore();
                easyGame.restartGame();
                break;
            } else if (ehq.equals("h")) {
                HardGame hardGame = new HardGame();
                hardGame.setHardGame();
                hardGame.fillBoard();
                hardGame.loadWords();
                hardGame.printBoard();
                hardGame.checkInput(hardGame.shuffleCards());
                hardGame.restartGame();
                break;
            }else {
                System.out.println("Invalid character");
                continue;
            }
        }
    }

    public void loadScore() throws IOException {
//        read in string
        String score = String.valueOf(Files.readAllBytes(Paths.get("./bestResults.json")));
//        read as json array
        JSONArray jsonArray = new JSONArray(score);
        for (int i = 0; i < jsonArray.length(); i++) {
            //parse in json
            JSONObject object = jsonArray.getJSONObject(i);
            //or
            String str = jsonArray.get(i).toString();
            JSONObject object1 = new JSONObject(str);

            String userName = object1.getString("userName");
            int bestTime = object1.getInt("bestTime");
            System.out.println("Username: " + userName);
            System.out.println("Besttime: " + bestTime);
        }
    }
    public void nameDownload() {
        System.out.println("Give us your nickname: ");
        String nickName = scanner.nextLine();
        System.out.println("Nickname: " + nickName);
    }
}