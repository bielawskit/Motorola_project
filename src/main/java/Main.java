import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        // point 2: ask difficulty level
        boolean shouldContinue = true;
        String artWelcome =
                "█░█░█ █▀▀ █░░ █▀▀ █▀█ █▀▄▀█ █▀▀ \n" +
                "▀▄▀▄▀ ██▄ █▄▄ █▄▄ █▄█ █░▀░█ ██▄ \n" +
                "\n" +
                "▀█▀ █▀█ \n" +
                "░█░ █▄█ \n" +
                "\n" +
                "█▀▄▀█ █▀▀ █▀▄▀█ █▀█ █▀█ █▄█ \n" +
                "█░▀░█ ██▄ █░▀░█ █▄█ █▀▄ ░█░ \n" +
                "\n" +
                "█▀▀ ▄▀█ █▀▄▀█ █▀▀ █ █ █\n" +
                "█▄█ █▀█ █░▀░█ ██▄ ▄ ▄ ▄\n";


        while (shouldContinue){
            System.out.println(artWelcome);
            System.out.println("Hi, choose level:");
            System.out.println("Press e for Easy level: 4 randomly selected words to discover, 10 chances ");
            System.out.println("Press h for Hard: 8 randomly selected word pairs, 15 chances");
            System.out.println("Press q Quit");

            String ehq = scanner.nextLine();
            if (ehq.equals("q")) {
                System.out.println("Exiting");
                break;
            } else if (ehq.equals("e")) {
                EasyGame easyGame = new EasyGame();
                easyGame.nameDownload();
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
                hardGame.nameDownload();
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
}