import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.Timer;

class BankAccount {
    private String accountHolder;
    private String accountNumber;
    private String pin;
    private double balance;
    private java.util.List<Double> balanceHistory;

    public BankAccount(String accountHolder, String accountNumber, String pin, double balance) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.balanceHistory = new ArrayList<>();
        this.balanceHistory.add(balance);
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public java.util.List<Double> getBalanceHistory() {
        return balanceHistory;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        balanceHistory.add(balance);
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        balanceHistory.add(balance);
        return true;
    }
}

class GradientPanel extends JPanel {
    private final Color c1;
    private final Color c2;

    public GradientPanel(Color c1, Color c2) {
        this.c1 = c1;
        this.c2 = c2;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        GradientPaint gp = new GradientPaint(0, 0, c1, getWidth(), getHeight(), c2);
        g2.setPaint(gp);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}

class RoundedPanel extends JPanel {
    private final int radius;
    private final Color bg;

    public RoundedPanel(int radius, Color bg) {
        this.radius = radius;
        this.bg = bg;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(bg);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }
}

class HoverButton extends JButton {
    private final Color normalColor;
    private final Color hoverColor;

    public HoverButton(String text, Color normalColor, Color hoverColor) {
        super(text);
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;

        setFocusPainted(false);
        setForeground(Color.WHITE);
        setBackground(normalColor);
        setFont(new Font("Segoe UI", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        setContentAreaFilled(false);
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // no border
    }
}

class CardAnimationPanel extends JPanel {
    private int cardX = -220;
    private final Timer timer;

    public CardAnimationPanel() {
        setOpaque(false);

        timer = new Timer(12, e -> {
            if (cardX < 20) {
                cardX += 8;
                repaint();
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
    }

    public void startAnimation() {
        cardX = -220;
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int y = 20;
        int w = 200;
        int h = 120;

        GradientPaint cardGradient = new GradientPaint(cardX, y, new Color(28, 34, 54),
                cardX + w, y + h, new Color(92, 107, 192));
        g2.setPaint(cardGradient);
        g2.fillRoundRect(cardX, y, w, h, 28, 28);

        g2.setColor(new Color(255, 215, 0));
        g2.fillRoundRect(cardX + 18, y + 18, 35, 25, 8, 8);

        g2.setFont(new Font("Consolas", Font.BOLD, 16));
        g2.setColor(Color.WHITE);
        g2.drawString("SMART BANK", cardX + 18, y + 72);

        g2.setFont(new Font("Consolas", Font.PLAIN, 15));
        g2.drawString("****  ****  ****  2048", cardX + 18, y + 98);

        g2.dispose();
    }
}

class BalanceGraphPanel extends JPanel {
    private java.util.List<Double> history = new ArrayList<>();

    public BalanceGraphPanel() {
        setOpaque(false);
    }

    public void setHistory(java.util.List<Double> history) {
        this.history = history;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int pad = 35;
        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(245, 247, 250));
        g2.fillRoundRect(0, 0, w, h, 24, 24);

        g2.setColor(new Color(210, 215, 223));
        g2.drawRoundRect(0, 0, w - 1, h - 1, 24, 24);

        g2.setColor(new Color(120, 130, 150));
        g2.drawLine(pad, h - pad, w - pad, h - pad);
        g2.drawLine(pad, pad, pad, h - pad);

        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.setColor(new Color(40, 50, 70));
        g2.drawString("Live Balance Graph", 18, 22);

        if (history == null || history.size() < 2) {
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            g2.setColor(Color.GRAY);
            g2.drawString("Do a deposit or withdrawal to see changes.", 18, 45);
            g2.dispose();
            return;
        }

        double max = Collections.max(history);
        double min = Collections.min(history);
        if (max == min) max += 1;

        int usableW = w - 2 * pad;
        int usableH = h - 2 * pad;

        for (int i = 0; i < history.size() - 1; i++) {
            int x1 = pad + (i * usableW / (history.size() - 1));
            int x2 = pad + ((i + 1) * usableW / (history.size() - 1));

            int y1 = h - pad - (int) ((history.get(i) - min) / (max - min) * usableH);
            int y2 = h - pad - (int) ((history.get(i + 1) - min) / (max - min) * usableH);

            g2.setStroke(new BasicStroke(3f));
            g2.setColor(new Color(46, 134, 222));
            g2.drawLine(x1, y1, x2, y2);

            g2.fillOval(x1 - 4, y1 - 4, 8, 8);
            if (i == history.size() - 2) {
                g2.fillOval(x2 - 4, y2 - 4, 8, 8);
            }
        }

        g2.setColor(new Color(90, 100, 120));
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        g2.drawString("â‚¹" + String.format("%.0f", max), 5, pad + 5);
        g2.drawString("â‚¹" + String.format("%.0f", min), 5, h - pad);
        g2.drawString("T1", pad - 5, h - 10);
        g2.drawString("T" + history.size(), w - pad - 10, h - 10);

        g2.dispose();
    }
}

public class UltimateATM extends JFrame implements ActionListener {

    private final Map<String, BankAccount> accounts = new LinkedHashMap<>();
    private BankAccount currentAccount;

    private JLabel welcomeLabel;
    private JLabel accountNumberLabel;
    private JLabel balanceLabel;

    private JTextField amountField;
    private JTextArea historyArea;

    private HoverButton depositBtn;
    private HoverButton withdrawBtn;
    private HoverButton balanceBtn;
    private HoverButton clearBtn;
    private HoverButton receiptBtn;
    private HoverButton logoutBtn;

    private BalanceGraphPanel graphPanel;
    private CardAnimationPanel cardPanel;

    private String lastReceipt = "No transaction yet.";

    public UltimateATM() {
        createAccounts();
        loginScreen();
    }

    private void createAccounts() {
        accounts.put("Ruhi", new BankAccount("Ruhi", "SB-2048-1001", "1234", 10000));
        accounts.put("Ayesha", new BankAccount("Ayesha", "SB-2048-1002", "4321", 15000));
        accounts.put("Sana", new BankAccount("Sana", "SB-2048-1003", "1111", 8000));
    }

    private void loginScreen() {
        String[] users = accounts.keySet().toArray(new String[0]);
        JComboBox<String> userBox = new JComboBox<>(users);
        JPasswordField pinField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.add(new JLabel("Select User Account:"));
        panel.add(userBox);
        panel.add(new JLabel("Enter PIN:"));
        panel.add(pinField);

        int option = JOptionPane.showConfirmDialog(
                null,
                panel,
                "ATM Login - Multiple User Accounts",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option != JOptionPane.OK_OPTION) {
            System.exit(0);
        }

        String selectedUser = (String) userBox.getSelectedItem();
        String enteredPin = new String(pinField.getPassword());

        BankAccount acc = accounts.get(selectedUser);

        if (acc != null && acc.getPin().equals(enteredPin)) {
            currentAccount = acc;
            buildGUI();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid PIN. Login failed.");
            System.exit(0);
        }
    }

    private void buildGUI() {
        setTitle("Ultimate ATM Dashboard");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        GradientPanel mainPanel = new GradientPanel(new Color(13, 27, 42), new Color(27, 38, 59));
        mainPanel.setLayout(new BorderLayout(18, 18));
        mainPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        setContentPane(mainPanel);

        JPanel topBar = new RoundedPanel(30, new Color(255, 255, 255, 28));
        topBar.setLayout(new BorderLayout());
        topBar.setBorder(new EmptyBorder(16, 20, 16, 20));

        welcomeLabel = new JLabel("Welcome, " + currentAccount.getAccountHolder());
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));

        accountNumberLabel = new JLabel("Account No: " + currentAccount.getAccountNumber());
        accountNumberLabel.setForeground(new Color(220, 230, 240));
        accountNumberLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JPanel titleWrap = new JPanel();
        titleWrap.setOpaque(false);
        titleWrap.setLayout(new BoxLayout(titleWrap, BoxLayout.Y_AXIS));
        titleWrap.add(welcomeLabel);
        titleWrap.add(Box.createVerticalStrut(6));
        titleWrap.add(accountNumberLabel);

        topBar.add(titleWrap, BorderLayout.WEST);
        mainPanel.add(topBar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 18, 18));
        centerPanel.setOpaque(false);

        JPanel leftPanel = new RoundedPanel(32, new Color(255, 255, 255, 238));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(22, 22, 22, 22));

        cardPanel = new CardAnimationPanel();
        cardPanel.setPreferredSize(new Dimension(260, 160));
        cardPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        cardPanel.startAnimation();

        JLabel amountTitle = new JLabel("Enter Amount");
        amountTitle.setFont(new Font("Segoe UI", Font.BOLD, 17));
        amountTitle.setForeground(new Color(30, 40, 60));
        amountTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        amountField = new JTextField();
        amountField.setMaximumSize(new Dimension(280, 42));
        amountField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 205, 215), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        balanceLabel = new JLabel("Balance hidden. Click 'Check Balance'");
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        balanceLabel.setForeground(new Color(40, 60, 90));
        balanceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        depositBtn = new HoverButton("Deposit", new Color(39, 174, 96), new Color(30, 132, 73));
        withdrawBtn = new HoverButton("Withdraw", new Color(231, 76, 60), new Color(192, 57, 43));
        balanceBtn = new HoverButton("Check Balance", new Color(41, 128, 185), new Color(31, 97, 141));
        clearBtn = new HoverButton("Clear", new Color(243, 156, 18), new Color(211, 84, 0));
        receiptBtn = new HoverButton("Receipt", new Color(142, 68, 173), new Color(108, 52, 131));
        logoutBtn = new HoverButton("Logout", new Color(99, 110, 114), new Color(75, 85, 99));

        depositBtn.addActionListener(this);
        withdrawBtn.addActionListener(this);
        balanceBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        receiptBtn.addActionListener(this);
        logoutBtn.addActionListener(this);

        JPanel buttonGrid = new JPanel(new GridLayout(3, 2, 12, 12));
        buttonGrid.setOpaque(false);
        buttonGrid.add(depositBtn);
        buttonGrid.add(withdrawBtn);
        buttonGrid.add(balanceBtn);
        buttonGrid.add(clearBtn);
        buttonGrid.add(receiptBtn);
        buttonGrid.add(logoutBtn);

        leftPanel.add(cardPanel);
        leftPanel.add(Box.createVerticalStrut(12));
        leftPanel.add(amountTitle);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(amountField);
        leftPanel.add(Box.createVerticalStrut(16));
        leftPanel.add(balanceLabel);
        leftPanel.add(Box.createVerticalStrut(18));
        leftPanel.add(buttonGrid);

        JPanel rightPanel = new JPanel(new BorderLayout(14, 14));
        rightPanel.setOpaque(false);

        JPanel historyPanel = new RoundedPanel(32, new Color(255, 255, 255, 238));
        historyPanel.setLayout(new BorderLayout());
        historyPanel.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel historyTitle = new JLabel("Transaction History");
        historyTitle.setFont(new Font("Segoe UI", Font.BOLD, 19));
        historyTitle.setForeground(new Color(35, 45, 60));

        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        historyArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        historyArea.setText("ATM session started for " + currentAccount.getAccountHolder() + "\n");

        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 232), 1));

        historyPanel.add(historyTitle, BorderLayout.NORTH);
        historyPanel.add(historyScroll, BorderLayout.CENTER);

        graphPanel = new BalanceGraphPanel();
        graphPanel.setPreferredSize(new Dimension(0, 220));
        graphPanel.setHistory(currentAccount.getBalanceHistory());

        rightPanel.add(historyPanel, BorderLayout.CENTER);
        rightPanel.add(graphPanel, BorderLayout.SOUTH);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private double getAmountFromField() {
        String text = amountField.getText().trim();

        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an amount.");
            return -1;
        }

        try {
            double amount = Double.parseDouble(text);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be greater than 0.");
                return -1;
            }
            return amount;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount.");
            return -1;
        }
    }

    private void refreshGraph() {
        graphPanel.setHistory(currentAccount.getBalanceHistory());
    }

    private void appendHistory(String text) {
        historyArea.append(text + "\n");
    }

    private String getTimeStamp() {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
    }

    private void showReceiptWindow(String title, String type, double amount, double balance) {
        lastReceipt =
                "============================\n" +
                "      SMART ATM RECEIPT\n" +
                "============================\n" +
                "Account Holder : " + currentAccount.getAccountHolder() + "\n" +
                "Account Number : " + currentAccount.getAccountNumber() + "\n" +
                "Transaction    : " + type + "\n" +
                "Amount         : â‚¹" + String.format("%.2f", amount) + "\n" +
                "Balance        : â‚¹" + String.format("%.2f", balance) + "\n" +
                "Date & Time    : " + getTimeStamp() + "\n" +
                "============================\n" +
                "Thank you for using Smart ATM\n" +
                "============================";

        JTextArea receiptArea = new JTextArea(lastReceipt);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        receiptArea.setBackground(new Color(250, 250, 250));

        JScrollPane pane = new JScrollPane(receiptArea);
        pane.setPreferredSize(new Dimension(420, 260));

        JOptionPane.showMessageDialog(this, pane, title, JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == depositBtn) {
            double amount = getAmountFromField();
            if (amount != -1) {
                if (currentAccount.deposit(amount)) {
                    appendHistory("[DEPOSIT] â‚¹" + String.format("%.2f", amount) + " at " + getTimeStamp());
                    refreshGraph();
                    amountField.setText("");
                    JOptionPane.showMessageDialog(this, "Deposit successful.");
                    showReceiptWindow("Transaction Receipt", "Deposit", amount, currentAccount.getBalance());
                }
            }
        }

        else if (source == withdrawBtn) {
            double amount = getAmountFromField();
            if (amount != -1) {
                if (currentAccount.withdraw(amount)) {
                    appendHistory("[WITHDRAW] â‚¹" + String.format("%.2f", amount) + " at " + getTimeStamp());
                    refreshGraph();
                    amountField.setText("");
                    JOptionPane.showMessageDialog(this, "Withdrawal successful.");
                    showReceiptWindow("Transaction Receipt", "Withdraw", amount, currentAccount.getBalance());
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance.");
                    appendHistory("[FAILED] Withdrawal of â‚¹" + String.format("%.2f", amount) + " failed at " + getTimeStamp());
                }
            }
        }

        else if (source == balanceBtn) {
            balanceLabel.setText("Current Balance: â‚¹" + String.format("%.2f", currentAccount.getBalance()));
            appendHistory("[BALANCE CHECK] â‚¹" + String.format("%.2f", currentAccount.getBalance()) + " at " + getTimeStamp());
            JOptionPane.showMessageDialog(this,
                    "Current Balance: â‚¹" + String.format("%.2f", currentAccount.getBalance()),
                    "Balance",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        else if (source == clearBtn) {
            amountField.setText("");
        }

        else if (source == receiptBtn) {
            JTextArea receiptArea = new JTextArea(lastReceipt);
            receiptArea.setEditable(false);
            receiptArea.setFont(new Font("Consolas", Font.PLAIN, 14));
            receiptArea.setBackground(new Color(250, 250, 250));
            JOptionPane.showMessageDialog(this, new JScrollPane(receiptArea), "Last Receipt", JOptionPane.INFORMATION_MESSAGE);
        }

        else if (source == logoutBtn) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Do you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                SwingUtilities.invokeLater(UltimateATM::new);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(UltimateATM::new);
    }
}
