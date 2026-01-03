/*package org.example;

import java.util.Scanner;
import java.util.Random;

// Линейные

// z0 = w0 + w1x1 + w2x2 .... + wnxn

// p = 1 / 1 + e^(-z),   p > 0.5   -> 1      < 0.5  -> 0

public class Main {

    static double[] w = {0.0, 0.5, 0.5, 0.5};
    static double lr = 0.1;

    static  double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    static double predict(double[] x) {
        double sum = w[0];
        for (int i = 0; i < x.length; i++) {
            sum += w[i + 1] * x[i];
        }

        return sigmoid(sum);
    }

    static void train(double[] x, int y) {
        double p = predict(x);
        double error = y - p;
        w[0] += lr * error;
        for (int i = 0; i < x.length; i++) {
            w[i + 1] += lr * x[i];
        }
    }

    public static void main(String[] args) {
        double[][] X = new double[3][3];

        int[] y = {1, 0, 1, 0};

        for (int epoch = 0; epoch < 500; epoch++) {
            for (int i = 0; i < X.length; i++) {
                train(X[i], y[i]);
            }
        }

        double[] test = {0.7, 0.9, 1};
        double p = predict(test);

        System.out.println("Вероятность = " + p);
        System.out.println("Решение = " + ((p > 0.5) ? "ALERT" : "OK"));

    }
}*/

/*package org.example;

public class Main {

    // веса: w0 — bias, остальные — под входы
    static double[] w = new double[4]; // 3 входа + bias
    static double lr = 0.1;

    // сигмоида
    static double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    // предсказание
    static double predict(double[] x) {
        double sum = w[0]; // bias
        for (int i = 0; i < x.length; i++) {
            sum += w[i + 1] * x[i];
        }
        return sigmoid(sum);
    }

    // обучение (логистическая регрессия)
    static void train(double[] x, int y) {
        double p = predict(x);
        double error = y - p;

        // обновляем bias
        w[0] += lr * error;

        // обновляем веса
        for (int i = 0; i < x.length; i++) {
            w[i + 1] += lr * error * x[i];
        }
    }

    public static void main(String[] args) {

        // обучающая выборка
        double[][] X = {
                {1, 1, 1}, // все чётные
                {1, 0, 1}, // есть нечётное
                {1, 1, 0},
                {0, 1, 1},
                {0, 0, 1},
                {1, 0, 0}
        };

        int[] y = {
                1, // все чётные
                0,
                0,
                0,
                0,
                0
        };

        // обучение
        for (int epoch = 0; epoch < 1000; epoch++) {
            for (int i = 0; i < X.length; i++) {
                train(X[i], y[i]);
            }
        }

        // тест
        double[] test1 = {1, 1, 1}; // 2,4,6
        double[] test2 = {1, 0, 1}; // 2,3,6

        double p1 = predict(test1);
        double p2 = predict(test2);

        System.out.println("Тест 1 (все чётные)");
        System.out.println("Вероятность = " + p1);
        System.out.println("Решение = " + (p1 > 0.5));

        System.out.println();

        System.out.println("Тест 2 (есть нечётное)");
        System.out.println("Вероятность = " + p2);
        System.out.println("Решение = " + (p2 > 0.5));
    }
}*/



/*import java.util.Scanner;
import java.util.Random;

public class TicTacToe {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Главное меню
            System.out.println("\n=== КРЕСТИКИ-НОЛИКИ ===");
            System.out.println("1. Играть против друга");
            System.out.println("2. Играть против компьютера (легкий уровень)");
            System.out.println("3. Правила игры");
            System.out.println("0. Выйти из игры");
            System.out.print("Выберите вариант: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                scanner.nextLine(); // очистка буфера
                continue;
            }

            switch (choice) {
                case 0:
                    System.out.println("Спасибо за игру! До свидания!");
                    scanner.close();
                    return;

                case 1:
                    playAgainstFriend(scanner);
                    break;

                case 2:
                    playAgainstComputer(scanner);
                    break;

                case 3:
                    showRules();
                    break;

                default:
                    System.out.println("Неверный выбор! Попробуйте снова.");
            }
        }
    }

    // Игра против друга
    private static void playAgainstFriend(Scanner scanner) {
        char[][] board = createEmptyBoard();
        char currentPlayer = 'X';
        boolean gameWon = false;
        int movesCount = 0;

        System.out.println("\n=== ИГРА ПРОТИВ ДРУГА ===");

        while (!gameWon && movesCount < 9) {
            printBoard(board);

            boolean validMove = false;
            int row = -1, col = -1;

            while (!validMove) {
                System.out.println("Ход игрока " + currentPlayer);
                int[] move = getPlayerMove(scanner, board);
                row = move[0];
                col = move[1];
                validMove = true;
            }

            board[row][col] = currentPlayer;
            movesCount++;

            if (checkWin(board, currentPlayer)) {
                printBoard(board);
                System.out.println("Игрок " + currentPlayer + " победил! Поздравляем!");
                gameWon = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }

        if (!gameWon) {
            printBoard(board);
            System.out.println("Ничья! Игра окончена.");
        }

        System.out.print("Нажмите Enter для возврата в меню...");
        scanner.nextLine(); // очистка буфера
        scanner.nextLine();
    }

    // Игра против компьютера
    private static void playAgainstComputer(Scanner scanner) {
        char[][] board = createEmptyBoard();
        char humanPlayer = 'X';
        char computerPlayer = 'O';
        char currentPlayer = humanPlayer;
        boolean gameWon = false;
        int movesCount = 0;

        System.out.println("\n=== ИГРА ПРОТИВ КОМПЬЮТЕРА ===");
        System.out.println("Вы играете за 'X', компьютер за 'O'");

        while (!gameWon && movesCount < 9) {
            printBoard(board);

            if (currentPlayer == humanPlayer) {
                // Ход человека
                System.out.println("Ваш ход (X):");
                int[] move = getPlayerMove(scanner, board);
                board[move[0]][move[1]] = humanPlayer;
            } else {
                // Ход компьютера
                System.out.println("Ход компьютера (O):");
                try {
                    Thread.sleep(1000); // Небольшая задержка для реалистичности
                } catch (InterruptedException e) {
                    // Игнорируем

                }

                int[] computerMove = makeComputerMove(board);
                board[computerMove[0]][computerMove[1]] = computerPlayer;
                System.out.println("Компьютер выбрал: " + (computerMove[0] + 1) + " " + (computerMove[1] + 1));
            }

            movesCount++;

            // Проверить победу текущего игрока
            if (checkWin(board, currentPlayer)) {
                printBoard(board);
                if (currentPlayer == humanPlayer) {
                    System.out.println("Вы победили! Поздравляем!");
                } else {
                    System.out.println("Компьютер победил! Попробуйте еще раз!");
                }
                gameWon = true;
            } else {
                currentPlayer = (currentPlayer == humanPlayer) ? computerPlayer : humanPlayer;
            }
        }

        if (!gameWon) {
            printBoard(board);
            System.out.println("Ничья! Неплохой результат!");
        }

        System.out.print("Нажмите Enter для возврата в меню...");
        scanner.nextLine();
        scanner.nextLine();
    }

    // Простой ИИ: сначала пытается победить, потом блокирует игрока, потом случайный ход
    private static int[] makeComputerMove(char[][] board) {
        Random random = new Random();

        // 1. Попытаться выиграть (поставить O в выигрышную позицию)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    if (checkWin(board, 'O')) {
                        board[i][j] = ' ';
                        return new int[]{i, j};
                    }
                    board[i][j] = ' ';
                }
            }
        }

        // 2. Попытаться заблокировать игрока (помешать X выиграть)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'X';
                    if (checkWin(board, 'X')) {
                        board[i][j] = ' ';
                        return new int[]{i, j};
                    }
                    board[i][j] = ' ';
                }
            }
        }

        // 3. Если центр свободен, занять его
        if (board[1][1] == ' ') {
            return new int[]{1, 1};
        }

        // 4. Занять углы, если они свободны
        int[][] corners = {{0,0}, {0,2}, {2,0}, {2,2}};
        for (int[] corner : corners) {
            if (board[corner[0]][corner[1]] == ' ') {
                return corner;
            }
        }

        // 5. Случайный ход из оставшихся
        while (true) {
            int i = random.nextInt(3);
            int j = random.nextInt(3);
            if (board[i][j] == ' ') {
                return new int[]{i, j};
            }
        }
    }

    // Получить ход от игрока
    private static int[] getPlayerMove(Scanner scanner, char[][] board) {
        while (true) {
            try {
                System.out.print("Введите строку и столбец (например: 1 2): ");
                int row = scanner.nextInt() - 1;
                int col = scanner.nextInt() - 1;

                if (row >= 0 && row < 3 && col >= 0 && col < 3) {
                    if (board[row][col] == ' ') {
                        return new int[]{row, col};
                    } else {
                        System.out.println("Эта клетка уже занята! Выберите другую.");
                    }
                } else {
                    System.out.println("Неверные координаты! Введите числа от 1 до 3.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка ввода! Введите два числа через пробел.");
                scanner.nextLine(); // очистка буфера
            }
        }
    }

    // Показать правила
    private static void showRules() {
        System.out.println("\n=== ПРАВИЛА ИГРЫ ===");

        System.out.println("1. Игровое поле размером 3x3 клетки");
        System.out.println("2. Игроки ходят по очереди");
        System.out.println("3. Первый игрок ставит 'X', второй - 'O'");
        System.out.println("4. Чтобы сделать ход, введите номер строки и столбца");
        System.out.println("5. Выигрывает тот, кто первым выстроит");
        System.out.println("   3 своих символа в ряд:");
        System.out.println("   - по горизонтали");
        System.out.println("   - по вертикали");
        System.out.println("   - по диагонали");
        System.out.println("6. Если все клетки заполнены, но нет победителя - ничья");
        System.out.println("\nПример координат:");
        System.out.println("   1 1 - верхний левый угол");
        System.out.println("   2 2 - центр");
        System.out.println("   3 3 - нижний правый угол");
        System.out.print("\nНажмите Enter для возврата в меню...");

        try {
            System.in.read();
        } catch (Exception e) {
            // Игнорируем
        }
    }

    // Создать пустую доску
    private static char[][] createEmptyBoard() {
        return new char[][] {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
    }

    // Метод для отображения игрового поля
    private static void printBoard(char[][] board) {
        System.out.println("\n  1 2 3");
        for (int i = 0; i < 3; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 3; j++) {
                char cell = board[i][j];
                // Добавляем цвета для наглядности
                if (cell == 'X') {
                    System.out.print("\u001B[31m" + cell + "\u001B[0m"); // Красный
                } else if (cell == 'O') {
                    System.out.print("\u001B[34m" + cell + "\u001B[0m"); // Синий
                } else {
                    System.out.print(cell);
                }
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) System.out.println("  -+-+-");
        }
        System.out.println();
    }

    // Метод для проверки победы
    private static boolean checkWin(char[][] board, char player) {
        // Проверка строк
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        // Проверка столбцов
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == player && board[1][j] == player && board[2][j] == player) {
                return true;
            }
        }

        // Проверка диагоналей
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }

        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }
}*/



package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class TicTacToe extends JFrame {

    private final JButton[][] buttons = new JButton[3][3];
    private final char[][] board = new char[3][3];
    private char currentPlayer = 'X';
    private boolean vsComputer = false;

    public TicTacToe() {
        setTitle("Крестики-нолики");
        setSize(400, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createMenu();
        createBoard();
        resetBoard();

        setVisible(true);
    }

    // ===== МЕНЮ =====
    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Игра");

        JMenuItem friend = new JMenuItem("Против друга");
        JMenuItem computer = new JMenuItem("Против компьютера");
        JMenuItem rules = new JMenuItem("Правила");
        JMenuItem exit = new JMenuItem("Выход");

        friend.addActionListener(e -> {
            vsComputer = false;
            resetBoard();
        });

        computer.addActionListener(e -> {
            vsComputer = true;
            resetBoard();
        });

        rules.addActionListener(e ->
                JOptionPane.showMessageDialog(
                        this,
                        "Крестики-нолики\n\n" +
                                "X ходит первым\n" +
                                "3 в ряд — победа\n" +
                                "Можно играть с другом или компьютером",
                        "Правила",
                        JOptionPane.INFORMATION_MESSAGE
                )
        );

        exit.addActionListener(e -> System.exit(0));

        gameMenu.add(friend);
        gameMenu.add(computer);
        gameMenu.addSeparator();
        gameMenu.add(rules);
        gameMenu.add(exit);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    // ===== ПОЛЕ =====
    private void createBoard() {
        JPanel panel = new JPanel(new GridLayout(3, 3));
        Font font = new Font("Arial", Font.BOLD, 60);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton("");
                button.setFont(font);

                int r = i;
                int c = j;

                button.addActionListener(e -> handleMove(r, c));

                buttons[i][j] = button;
                panel.add(button);
            }
        }

        add(panel, BorderLayout.CENTER);
    }

    // ===== ЛОГИКА ИГРЫ =====
    private void handleMove(int r, int c) {
        if (board[r][c] != ' ') return;

        makeMove(r, c, currentPlayer);

        if (checkWin(currentPlayer)) {
            endGame("Игрок " + currentPlayer + " победил!");
            return;
        }

        if (isDraw()) {
            endGame("Ничья!");
            return;
        }

        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

        if (vsComputer && currentPlayer == 'O') {
            computerMove();
        }
    }

    private void computerMove() {
        Random random = new Random();

        while (true) {
            int r = random.nextInt(3);
            int c = random.nextInt(3);

            if (board[r][c] == ' ') {
                makeMove(r, c, 'O');
                break;
            }
        }

        if (checkWin('O')) {
            endGame("Компьютер победил!");
        } else if (isDraw()) {
            endGame("Ничья!");
        } else {
            currentPlayer = 'X';
        }
    }

    private void makeMove(int r, int c, char p) {
        board[r][c] = p;
        buttons[r][c].setText(String.valueOf(p));
        buttons[r][c].setForeground(p == 'X' ? Color.RED : Color.BLUE);
    }

    private boolean checkWin(char p) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == p && board[i][1] == p && board[i][2] == p) return true;
            if (board[0][i] == p && board[1][i] == p && board[2][i] == p) return true;
        }
        return (board[0][0] == p && board[1][1] == p && board[2][2] == p)
                || (board[0][2] == p && board[1][1] == p && board[2][0] == p);
    }

    private boolean isDraw() {
        for (char[] row : board)
            for (char c : row)
                if (c == ' ') return false;
        return true;
    }

    private void resetBoard() {
        currentPlayer = 'X';
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText("");
            }
    }

    private void endGame(String message) {
        JOptionPane.showMessageDialog(this, message);
        resetBoard();
    }

    // ===== СТАРТ =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}