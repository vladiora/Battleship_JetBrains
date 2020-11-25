package battleship;
import java.util.*;

public class Main {
    public static void newBoard(char[][] board) {
        for (char[] row: board) {
            Arrays.fill(row, '~');
        }
    }

    public static void printBoard(char[][] board) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < board.length; i++) {
            System.out.print((char)(i + 65) + " ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void addShip(char[][] board, String start, String end) {
        int startLetter = start.charAt(0) - 65;
        int startNum = Integer.parseInt(start.substring(1)) - 1;
        int endLetter = end.charAt(0) - 65;
        int endNum = Integer.parseInt(end.substring(1)) - 1;
        if (startLetter == endLetter) {
            for (int i = Math.min(startNum, endNum); i <= Math.max(startNum, endNum); i++) {
                board[startLetter][i] = 'O';
            }
        } else if(startNum == endNum) {
            for (int i = Math.min(startLetter, endLetter); i <= Math.max(startLetter, endLetter); i++) {
                board[i][startNum] = 'O';
            }
        }
        printBoard(board);
    }

    public static boolean lengthOfShip(String start, String end, int length) {
        int startLetter = start.charAt(0) - 65;
        int startNum = Integer.parseInt(start.substring(1)) - 1;
        int endLetter = end.charAt(0) - 65;
        int endNum = Integer.parseInt(end.substring(1)) - 1;
        return Math.abs(startLetter - endLetter) != length && Math.abs(startNum - endNum) != length;
    }

    public static boolean wrongLocation(String start, String end) {
        int startLetter = start.charAt(0) - 65;
        int startNum = Integer.parseInt(start.substring(1)) - 1;
        int endLetter = end.charAt(0) - 65;
        int endNum = Integer.parseInt(end.substring(1)) - 1;
        return startLetter != endLetter && startNum != endNum;
    }

    public static boolean tooClose(String start, String end, char[][] board) {
        int startLetter = start.charAt(0) - 65;
        int startNum = Integer.parseInt(start.substring(1)) - 1;
        int endLetter = end.charAt(0) - 65;
        int endNum = Integer.parseInt(end.substring(1)) - 1;

        if (startLetter == endLetter) { // horizontal line
            if (startNum != 0 && startNum != 9 && endNum != 0 && endNum != 9) {
                if (board[startLetter][startNum - 1] == 'O' || board[startLetter][startNum + 1] == 'O' ||
                        board[startLetter][endNum - 1] == 'O' || board[startLetter][endNum + 1] == 'O') {
                    return true;
                }
            }
            if (startLetter == 0) {
                for (int i = Math.min(startNum, endNum); i < Math.max(startNum, endNum); i++) {
                    if (board[0][i] == 'O' || board[1][i] == 'O') {
                        return true;
                    }
                }
            } else if (startLetter == 9) {
                for (int i = Math.min(startNum, endNum); i < Math.max(startNum, endNum); i++) {
                    if (board[9][i] == 'O' || board[8][i] == 'O') {
                        return true;
                    }
                }
            } else {
                for (int i = Math.min(startNum, endNum); i < Math.max(startNum, endNum); i++) {
                    if (board[startLetter][i] == 'O' || board[startLetter - 1][i] == 'O' || board[startLetter + 1][i] == 'O') {
                        return true;
                    }
                }
            }
        } else if (startNum == endNum) { // vertical line
            if (startLetter != 0 && startLetter != 9 && endLetter != 0 && endLetter != 9) {
                if (board[startLetter - 1][startNum] == 'O' || board[startLetter + 1][startNum] == 'O' ||
                        board[endLetter - 1][startNum] == 'O' || board[endLetter + 1][startNum] == 'O') {
                    return true;
                }
            }
            if (startNum == 0) {
                for (int i = Math.min(startLetter, endLetter); i < Math.max(startLetter, endLetter); i++) {
                    if (board[i][0] == 'O' || board[i][1] == 'O') {
                        return true;
                    }
                }
            } else if (startNum == 9) {
                for (int i = Math.min(startLetter, endLetter); i < Math.max(startLetter, endLetter); i++) {
                    if (board[i][9] == 'O' || board[i][8] == 'O') {
                        return true;
                    }
                }
            } else {
                for (int i = Math.min(startLetter, endLetter); i < Math.max(startLetter, endLetter); i++) {
                    if (board[i][startNum] == 'O' || board[i][startNum - 1] == 'O' || board[i][startNum + 1] == 'O') {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void handleError(String start, String end, int length, Scanner scanner, char[][] board) {
        if(wrongLocation(start, end)) {
            while (wrongLocation(start, end)) {
                System.out.println("Error! Wrong ship location! Try again:");
                start = scanner.next();
                end = scanner.next();
            }
        } else if (lengthOfShip(start, end, length)) {
            while (lengthOfShip(start, end, length)) {
                System.out.println("Error! Wrong length of the Submarine! Try again: ");
                start = scanner.next();
                end = scanner.next();
            }
        } else if (tooClose(start, end, board)) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            start = scanner.next();
            end = scanner.next();
        }
        addShip(board, start, end);
    }

    public static void takePosition(char[][] board, Scanner scanner) {
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        String startAir = scanner.next();
        String endAir = scanner.next();
        handleError(startAir, endAir, 4, scanner, board);

        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        String startBattle = scanner.next();
        String endBattle = scanner.next();
        handleError(startBattle, endBattle, 3, scanner, board);

        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        String startSub = scanner.next();
        String endSub = scanner.next();
        handleError(startSub, endSub, 2, scanner, board);

        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        String startCru = scanner.next();
        String endCru = scanner.next();
        handleError(startCru, endCru, 2, scanner, board);

        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        String startDes = scanner.next();
        String endDes = scanner.next();
        handleError(startDes, endDes, 1, scanner, board);
    }

    public static void gameplay(char[][] board, Scanner scanner) {
        System.out.println("Take a shot!");
        String shot = scanner.next();
        int letter = shot.charAt(0) - 65;
        int num = Integer.parseInt(shot.substring(1)) - 1;
        while (letter < 0 || letter > 9 || num < 0 || num > 9) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            shot = scanner.next();
            letter = shot.charAt(0) - 65;
            num = Integer.parseInt(shot.substring(1)) - 1;
        }
        if (board[letter][num] == 'O') {
            board[letter][num] = 'X';
            printBoard(board);
            System.out.println("You hit a ship!");
        } else {
            board[letter][num] = 'M';
            printBoard(board);
            System.out.println("You missed!");
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[][] board = new char[10][10];
        newBoard(board);
        printBoard(board);
        takePosition(board, scanner);
        System.out.println("The game starts!");
        printBoard(board);
        gameplay(board, scanner);


    }
}
