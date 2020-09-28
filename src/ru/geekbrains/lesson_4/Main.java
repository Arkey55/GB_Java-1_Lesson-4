package ru.geekbrains.lesson_4;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static int size;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
	    runGame(scanner, random);
    }

    static void runGame(Scanner scanner, Random random){
        System.out.println("Пожалуйста, укажите размер поля!");
        int fieldSize = scanner.nextInt();
        size = fieldSize;
        char[][] gameField = createField(fieldSize);
        drawField(gameField);

        do {
            doPlayerMove(scanner, gameField);
            if (isFinal(gameField, 'X')){
                break;
            }
            doAIMove(random, gameField);
            if (isFinal(gameField, 'O')){
                break;
            }
            drawField(gameField);
        } while (true);

        System.out.println("Результат:");
        drawField(gameField);
    }

    //=== Создаем и отрисовываем поле ===
    static char[][] createField (int fieldSize){
        char[][] field = new char[fieldSize][fieldSize];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = '-';
            }
        }
        return field;
    }
    static void drawField(char[][] gameField){
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                System.out.print(gameField[i][j]);
            }
            System.out.println();
        }
    }

    //=== Логика игры ===
    static int getCoordinate (Scanner scanner, char coordName){
        int coordinate;
        do {
            System.out.println(String.format("Введите %s-координату", coordName));
            coordinate = scanner.nextInt() - 1;
        } while (coordinate < 0 || coordinate > size - 1);
        return coordinate;
    }

    static void doPlayerMove (Scanner scanner, char[][] field){
        int x, y;
        do {
            x = getCoordinate(scanner, 'X');
            y = getCoordinate(scanner, 'Y');
        } while (field[x][y] != '-');
        field[x][y] = 'X';
    }

    static void doAIMove (Random random, char[][] field){
        int x, y;
        if (ternOnAI(field)){
            runAI(field);
            return;
        }
        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (field[x][y] != '-');
        field[x][y] = 'O';
    }

    //=== Условия завершения ====

    static boolean isFinal (char[][] field, char sign){
        if (isWin(field, sign)){
            String name = sign == 'X' ? "Игрок" : "Компьютер";
            System.out.println(String.format("%s победил!", name));
            return true;
        }
        if (isDraw(field)){
            System.out.println("Ничья!");
            return true;
        }
        return false;
    }
    static boolean isWin(char[][] field, char sign){
        for (int i = 0; i < field.length; i++) {
            int countH = 0;
            int countV = 0;
            int countDOne = 0;
            int countDTwo = 0;
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == sign){
                    countH++;
                    if (countH == field.length){
                        return true;
                    }
                }
                if (field[j][i] == sign){
                    countV++;
                    if (countV == field.length){
                        return true;
                    }
                }
                if(field[j][j] == sign) {
                    countDOne++;
                    if (countDOne == field.length) {
                        return true;
                    }
                }
                if (field[j][j + (field.length - (1 + j * 2))] == sign){
                    countDTwo++;
                    if (countDTwo == field.length){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    static boolean isDraw(char[][] field){
        int count = (int)Math.pow(field.length, 2);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] != '-'){
                    count--;
                }
            }
        }
        return count == 0;
    }

    //=== AI====

    static void runAI(char[][] field){
        char o = 'O';
        char x = 'X';
        char e = '-';
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == e){
                    field[i][j] = o;
                    if (isWin(field, o)){
                        return;
                    } else {
                        field[i][j] = e;
                    }
                }
            }
        }
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == e){
                    field[i][j] = x;
                    if (isWin(field, x)){
                        field[i][j] = o;
                        return;
                    } else {
                        field[i][j] = e;
                    }
                }
            }
        }
    }

    static boolean ternOnAI(char[][] field){
        char x = 'X';
        for (int i = 0; i < field.length; i++) {
            int countH = 0;
            int countV = 0;
            int countDOne = 0;
            int countDTwo = 0;
            for (int j = 0; j < field.length; j++) {
                if (field[i][j] == x){
                    countH++;
                    if (countH == field.length - 1){
                        return true;
                    }
                }
                if (field[j][i] == x){
                    countV++;
                    if (countV == field.length - 1){
                        return true;
                    }
                }
                if(field[j][j] == x) {
                    countDOne++;
                    if (countDOne == field.length - 1) {
                        return true;
                    }
                }
                if (field[j][j + (field.length - (1 + j * 2))] == x){
                    countDTwo++;
                    if (countDTwo == field.length - 1){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
