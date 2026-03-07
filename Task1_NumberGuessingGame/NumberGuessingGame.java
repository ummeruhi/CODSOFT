import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {

    private JTextField nameField;
    private JTextField minField;
    private JTextField maxField;
    private JTextField guessField;

    private JComboBox<String> difficultyBox;

    private JButton startButton;
    private JButton guessButton;
    private JButton newRoundButton;
    private JButton resetButton;
    private JButton leaderboardButton;

    private JLabel welcomeLabel;
    private JLabel rangeLabel;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;
    private JLabel bestScoreLabel;
    private JLabel roundsWonLabel;
    private JLabel statusLabel;
    private JLabel hintLabel;

    private JTextArea leaderboardArea;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;

    private Random random = new Random();

    private String playerName = "Player";
    private int secretNumber;
    private int minRange = 1;
    private int maxRange = 100;
    private int maxAttempts = 7;
    private int attemptsUsed = 0;
    private int totalScore = 0;
    private int bestScore = 0;
    private int roundsWon = 0;
    private boolean gameActive = false;

    private ArrayList<String> leaderboard = new ArrayList<>();

    public NumberGuessingGameGUI() {
        setTitle("Ultimate Number Guessing Game");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(18, 18, 32));

        buildTopPanel();
        buildCenterPanel();
        buildBottomPanel();

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void buildTopPanel() {
        topPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        topPanel.setBackground(new Color(18, 18, 32));

        JLabel titleLabel = new JLabel("🎯 Ultimate Number Guessing Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 215, 0));

        welcomeLabel = new JLabel("Welcome! Enter details and start the game.", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeLabel.setForeground(Color.WHITE);

        topPanel.add(titleLabel);
        topPanel.add(welcomeLabel);
    }

    private void buildCenterPanel() {
        centerPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        centerPanel.setBackground(new Color(18, 18, 32));

        JPanel leftPanel = createCardPanel();
        JPanel rightPanel = createCardPanel();

        leftPanel.setLayout(new GridLayout(10, 2, 10, 10));
        rightPanel.setLayout(new BorderLayout(10, 10));

        JLabel nameLabel = createLabel("Player Name:");
        nameField = createTextField();

        JLabel difficultyLabel = createLabel("Difficulty:");
        difficultyBox = new JComboBox<>(new String[]{
                "Easy (1-50, 10 attempts)",
                "Medium (1-100, 7 attempts)",
                "Hard (1-500, 5 attempts)",
                "Custom Range"
        });
        styleComboBox(difficultyBox);

        JLabel minLabel = createLabel("Min Range:");
        minField = createTextField();
        minField.setText("1");

        JLabel maxLabel = createLabel("Max Range:");
        maxField = createTextField();
        maxField.setText("100");

        startButton = createButton("Start Game", new Color(46, 204, 113));
        guessButton = createButton("Submit Guess", new Color(52, 152, 219));
        newRoundButton = createButton("New Round", new Color(155, 89, 182));
        resetButton = createButton("Reset Game", new Color(231, 76, 60));
        leaderboardButton = createButton("Show Leaderboard", new Color(241, 196, 15));

        JLabel guessLabel = createLabel("Enter Guess:");
        guessField = createTextField();
        guessField.setEnabled(false);

        leftPanel.add(nameLabel);
        leftPanel.add(nameField);
        leftPanel.add(difficultyLabel);
        leftPanel.add(difficultyBox);
        leftPanel.add(minLabel);
        leftPanel.add(minField);
        leftPanel.add(maxLabel);
        leftPanel.add(maxField);
        leftPanel.add(guessLabel);
        leftPanel.add(guessField);
        leftPanel.add(startButton);
        leftPanel.add(guessButton);
        leftPanel.add(newRoundButton);
        leftPanel.add(resetButton);
        leftPanel.add(new JLabel());
        leftPanel.add(leaderboardButton);

        JPanel statsPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        statsPanel.setOpaque(false);

        rangeLabel = createInfoLabel("Range: -");
        attemptsLabel = createInfoLabel("Attempts Left: -");
        scoreLabel = createInfoLabel("Total Score: 0");
        bestScoreLabel = createInfoLabel("Best Score: 0");
        roundsWonLabel = createInfoLabel("Rounds Won: 0");
        statusLabel = createInfoLabel("Status: Game not started");
        hintLabel = createInfoLabel("Hint: -");

        leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        leaderboardArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        leaderboardArea.setBackground(new Color(12, 12, 24));
        leaderboardArea.setForeground(Color.WHITE);
        leaderboardArea.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 120), 2));
        leaderboardArea.setText("Leaderboard will appear here.");

        statsPanel.add(rangeLabel);
        statsPanel.add(attemptsLabel);
        statsPanel.add(scoreLabel);
        statsPanel.add(bestScoreLabel);
        statsPanel.add(roundsWonLabel);
        statsPanel.add(statusLabel);
        statsPanel.add(hintLabel);

        rightPanel.add(statsPanel, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(leaderboardArea), BorderLayout.CENTER);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        startButton.addActionListener(e -> startGame());
        guessButton.addActionListener(e -> submitGuess());
        newRoundButton.addActionListener(e -> startGame());
        resetButton.addActionListener(e -> resetGame());
        leaderboardButton.addActionListener(e -> showLeaderboard());
    }

    private void buildBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(18, 18, 32));

        JLabel footer = new JLabel("Built with Java Swing | Interactive GUI Mini Project");
        footer.setForeground(new Color(180, 180, 180));
        footer.setFont(new Font("Segoe UI", Font.ITALIC, 14));

        bottomPanel.add(footer);
    }

    private JPanel createCardPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(28, 28, 48));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120), 2),
                new EmptyBorder(15, 15, 15, 15)
        ));
        return panel;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 15));
        return label;
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(220, 220, 255));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return label;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        field.setBackground(new Color(245, 245, 255));
        field.setForeground(Color.BLACK);
        return field;
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return button;
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
    }

    private void startGame() {
        playerName = nameField.getText().trim();
        if (playerName.isEmpty()) {
            playerName = "Player";
        }

        String selectedDifficulty = (String) difficultyBox.getSelectedItem();

        if (selectedDifficulty.startsWith("Easy")) {
            minRange = 1;
            maxRange = 50;
            maxAttempts = 10;
        } else if (selectedDifficulty.startsWith("Medium")) {
            minRange = 1;
            maxRange = 100;
            maxAttempts = 7;
        } else if (selectedDifficulty.startsWith("Hard")) {
            minRange = 1;
            maxRange = 500;
            maxAttempts = 5;
        } else {
            try {
                minRange = Integer.parseInt(minField.getText().trim());
                maxRange = Integer.parseInt(maxField.getText().trim());

                if (minRange >= maxRange) {
                    JOptionPane.showMessageDialog(this, "Minimum must be smaller than maximum.");
                    return;
                }
                maxAttempts = 8;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter valid numbers for custom range.");
                return;
            }
        }

        secretNumber = random.nextInt(maxRange - minRange + 1) + minRange;
        attemptsUsed = 0;
        gameActive = true;

        guessField.setEnabled(true);
        guessField.setText("");
        guessField.requestFocus();

        welcomeLabel.setText("Welcome, " + playerName + "! Game started.");
        rangeLabel.setText("Range: " + minRange + " to " + maxRange);
        attemptsLabel.setText("Attempts Left: " + maxAttempts);
        statusLabel.setText("Status: Guess the hidden number!");
        hintLabel.setText("Hint: Try your first guess.");
    }

    private void submitGuess() {
        if (!gameActive) {
            JOptionPane.showMessageDialog(this, "Start a game first.");
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(guessField.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            return;
        }

        if (guess < minRange || guess > maxRange) {
            JOptionPane.showMessageDialog(this, "Enter a number between " + minRange + " and " + maxRange);
            return;
        }

        attemptsUsed++;
        int attemptsLeft = maxAttempts - attemptsUsed;
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);

        if (guess == secretNumber) {
            int roundScore = (maxAttempts - attemptsUsed + 1) * 10;
            totalScore += roundScore;
            roundsWon++;

            if (roundScore > bestScore) {
                bestScore = roundScore;
            }

            scoreLabel.setText("Total Score: " + totalScore);
            bestScoreLabel.setText("Best Score: " + bestScore);
            roundsWonLabel.setText("Rounds Won: " + roundsWon);
            statusLabel.setText("Status: 🎉 Correct! You guessed it.");
            hintLabel.setText("Hint: Excellent work, " + playerName + "!");

            leaderboard.add(playerName + " - " + roundScore + " points");
            gameActive = false;
            guessField.setEnabled(false);

            JOptionPane.showMessageDialog(this,
                    "🎉 Correct Guess!\n\nNumber: " + secretNumber +
                            "\nRound Score: " + roundScore +
                            "\nTotal Score: " + totalScore);
            return;
        }

        if (attemptsLeft <= 0) {
            statusLabel.setText("Status: ❌ Game Over! Number was " + secretNumber);
            hintLabel.setText("Hint: Better luck next round.");
            gameActive = false;
            guessField.setEnabled(false);
            JOptionPane.showMessageDialog(this,
                    "Out of attempts!\nThe correct number was: " + secretNumber);
            return;
        }

        if (guess < secretNumber) {
            statusLabel.setText("Status: Too Low!");
        } else {
            statusLabel.setText("Status: Too High!");
        }

        int diff = Math.abs(secretNumber - guess);

        if (diff <= 5) {
            hintLabel.setText("Hint: 🔥 Very Close!");
        } else if (diff <= 15) {
            hintLabel.setText("Hint: 🌡 Close!");
        } else {
            hintLabel.setText("Hint: ❄ Far Away!");
        }

        if (attemptsUsed == 2) {
            if (secretNumber % 2 == 0) {
                hintLabel.setText(hintLabel.getText() + " | Number is EVEN");
            } else {
                hintLabel.setText(hintLabel.getText() + " | Number is ODD");
            }
        }

        if (attemptsUsed == 4) {
            int mid = (minRange + maxRange) / 2;
            if (secretNumber <= mid) {
                hintLabel.setText(hintLabel.getText() + " | Lower Half");
            } else {
                hintLabel.setText(hintLabel.getText() + " | Upper Half");
            }
        }

        guessField.setText("");
        guessField.requestFocus();
    }

    private void showLeaderboard() {
        if (leaderboard.isEmpty()) {
            leaderboardArea.setText("No scores yet.\nPlay and win rounds to fill leaderboard.");
            return;
        }

        ArrayList<String> sortedBoard = new ArrayList<>(leaderboard);
        Collections.sort(sortedBoard);

        StringBuilder sb = new StringBuilder();
        sb.append("===== SESSION LEADERBOARD =====\n\n");
        for (int i = 0; i < sortedBoard.size(); i++) {
            sb.append(i + 1).append(". ").append(sortedBoard.get(i)).append("\n");
        }

        leaderboardArea.setText(sb.toString());
    }

    private void resetGame() {
        playerName = "Player";
        totalScore = 0;
        bestScore = 0;
        roundsWon = 0;
        attemptsUsed = 0;
        gameActive = false;
        leaderboard.clear();

        nameField.setText("");
        minField.setText("1");
        maxField.setText("100");
        guessField.setText("");
        guessField.setEnabled(false);

        welcomeLabel.setText("Welcome! Enter details and start the game.");
        rangeLabel.setText("Range: -");
        attemptsLabel.setText("Attempts Left: -");
        scoreLabel.setText("Total Score: 0");
        bestScoreLabel.setText("Best Score: 0");
        roundsWonLabel.setText("Rounds Won: 0");
        statusLabel.setText("Status: Game not started");
        hintLabel.setText("Hint: -");
        leaderboardArea.setText("Leaderboard will appear here.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGameGUI::new);
    }
}
