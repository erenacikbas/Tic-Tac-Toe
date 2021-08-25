package com.erentuna;
// Tic Tac Toe Game written in Java by Eren Tuna 2021

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int[][] gameTableAsInt = new int[3][3];
        char[][] gameTable = new char[][]{{'_','_','_'},{'_', '_', '_'}, {'_', '_', '_'}};


        printGame(gameTable);
        int rowN = 0;
        int columnN = 0;
        boolean isValid = false;
        boolean gameFinished = false;
        int turn = 0; // X starts game X = 0 , O = 1

        while (!isValid || !gameFinished) {
            try {
                System.out.print("Enter the coordinates: ");
                String[] pieces = scanner.nextLine().split(" ");
                rowN = Integer.parseInt(pieces[0]);
                columnN = Integer.parseInt(pieces[1]);
                if (rowN > 3 || rowN <= 0 || columnN > 3 || columnN <= 0) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
                char selectedPosition = gameTable[rowN-1][columnN-1];
                if (selectedPosition == '_') {
                    switch (turn) {
                        case 0:
                            gameTable[rowN - 1][columnN - 1] = 'X';
                            gameTableAsInt[rowN - 1][columnN - 1] = 'X';
                            break;
                        case 1:
                            gameTable[rowN -1][columnN - 1] = 'O';
                            gameTableAsInt[rowN - 1][columnN - 1] = 'O';
                    }
                    printGame(gameTable);

                } else {
                    System.out.println("This cell is occupied! Chose another one!");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("You should enter numbers!");
                continue;
            }

            isValid = true;
            gameFinished = analyzeGame(gameTableAsInt, gameTable);
            turn = turn == 0 ? 1 : 0;
        }



    }

    private static void printGame(char[][] gameTable) {
        System.out.println("---------");
        System.out.println("| " + gameTable[0][0] + " " + gameTable[0][1] + " " + gameTable[0][2] + " |");
        System.out.println("| " + gameTable[1][0] + " " + gameTable[1][1] + " " + gameTable[1][2] + " |");
        System.out.println("| " + gameTable[2][0] + " " + gameTable[2][1] + " " + gameTable[2][2] + " |");
        System.out.println("---------");
    }

    private static boolean analyzeGame(int[][] gameTableAsInt, char[][] gameTable) {
        // Count symbols for "Impossible" or "Game not finished" case
        int xs = 0;
        int os = 0;
        int empty = 0;

        for (int i = 0; i < gameTable.length; i++) {
            for (int j = 0; j < gameTable[i].length; j++) {
                if (gameTable[i][j] == 'X') {
                    xs++;
                } else if (gameTable[i][j] == 'O') {
                    os++;
                } else {
                    empty++;
                }
            }
        }

        if (Math.abs(xs - os) >= 2) {
            System.out.println("Draw");
            return true;
        }

        // Checking
        int[] sumOfLines = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        int i = 0;
        for (int[] row : gameTableAsInt) {
            // Horizontal Lines
            sumOfLines[i] = row[0] + row[1] + row[2];
            // Vertical Lines
            sumOfLines[3] += row[0];
            sumOfLines[4] += row[1];
            sumOfLines[5] += row[2];
            // Diagonal Liens
            sumOfLines[6] += row[i];
            sumOfLines[7] += row[2 - i];
            i++;
        }

        int x = 0;
        int o = 0;
        for (int n : sumOfLines) {
            if (n == 237) {
                o++;
            } else if (n == 264) {
                x++;
            }
        }

        if (x > 0 || o > 0 && empty == 0) {
            if (x == 1 && o == 0) {
                System.out.println("X wins");
            } else if (o == 1 && x == 0) {
                System.out.println("O wins");
            } else {
                System.out.println("Draw");
            }
            return true;
        }

        // If there's an empty in the game after all condition checked
        if (empty > 0) {
            // System.out.println("Game not finished");
            return false;
        } else {
            // Draw as a last possible case
            System.out.println("Draw");
            return true;
        }

    }

}
