import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyConverterGUI extends JFrame {

    private JComboBox<String> fromCurrencyBox;
    private JComboBox<String> toCurrencyBox;
    private JTextField amountField;
    private JLabel resultLabel;
    private JLabel rateLabel;
    private JLabel updateLabel;
    private JLabel statusLabel;

    private final DecimalFormat df = new DecimalFormat("#,##0.00####");

    private static final LinkedHashMap<String, String> CURRENCIES = new LinkedHashMap<>();

    static {
        CURRENCIES.put("USD - US Dollar", "USD");
        CURRENCIES.put("INR - Indian Rupee", "INR");
        CURRENCIES.put("EUR - Euro", "EUR");
        CURRENCIES.put("GBP - British Pound", "GBP");
        CURRENCIES.put("JPY - Japanese Yen", "JPY");
        CURRENCIES.put("AUD - Australian Dollar", "AUD");
        CURRENCIES.put("CAD - Canadian Dollar", "CAD");
        CURRENCIES.put("CHF - Swiss Franc", "CHF");
        CURRENCIES.put("CNY - Chinese Yuan", "CNY");
        CURRENCIES.put("AED - UAE Dirham", "AED");
        CURRENCIES.put("SGD - Singapore Dollar", "SGD");
        CURRENCIES.put("NZD - New Zealand Dollar", "NZD");
        CURRENCIES.put("SAR - Saudi Riyal", "SAR");
        CURRENCIES.put("QAR - Qatari Riyal", "QAR");
        CURRENCIES.put("KWD - Kuwaiti Dinar", "KWD");
        CURRENCIES.put("BHD - Bahraini Dinar", "BHD");
        CURRENCIES.put("OMR - Omani Rial", "OMR");
        CURRENCIES.put("MYR - Malaysian Ringgit", "MYR");
        CURRENCIES.put("THB - Thai Baht", "THB");
        CURRENCIES.put("KRW - South Korean Won", "KRW");
        CURRENCIES.put("ZAR - South African Rand", "ZAR");
        CURRENCIES.put("BRL - Brazilian Real", "BRL");
        CURRENCIES.put("RUB - Russian Ruble", "RUB");
        CURRENCIES.put("HKD - Hong Kong Dollar", "HKD");
        CURRENCIES.put("TRY - Turkish Lira", "TRY");
        CURRENCIES.put("NPR - Nepalese Rupee", "NPR");
        CURRENCIES.put("PKR - Pakistani Rupee", "PKR");
        CURRENCIES.put("BDT - Bangladeshi Taka", "BDT");
        CURRENCIES.put("LKR - Sri Lankan Rupee", "LKR");
    }

    public CurrencyConverterGUI() {
        setTitle("Ultimate Currency Converter");
        setSize(1020, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createMainPanel());
        setVisible(true);
    }

    private JPanel createMainPanel() {
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        RoundedPanel leftCard = new RoundedPanel(30, new Color(255, 255, 255, 35));
        leftCard.setLayout(new BoxLayout(leftCard, BoxLayout.Y_AXIS));
        leftCard.setBorder(new EmptyBorder(35, 30, 35, 30));
        leftCard.setPreferredSize(new Dimension(360, 500));

        JLabel title = new JLabel("Currency Converter");
        title.setFont(new Font("SansSerif", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel(
                "<html>Convert money across world currencies with a premium modern dashboard and real-time exchange rate fetching.</html>"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 16));
        subtitle.setForeground(new Color(240, 245, 255));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftCard.add(title);
        leftCard.add(Box.createVerticalStrut(18));
        leftCard.add(subtitle);
        leftCard.add(Box.createVerticalStrut(30));
        leftCard.add(createFeatureLabel("â€¢ Real-time exchange rate fetching"));
        leftCard.add(Box.createVerticalStrut(12));
        leftCard.add(createFeatureLabel("â€¢ Stylish fintech-inspired interface"));
        leftCard.add(Box.createVerticalStrut(12));
        leftCard.add(createFeatureLabel("â€¢ One-click swap currencies"));
        leftCard.add(Box.createVerticalStrut(12));
        leftCard.add(createFeatureLabel("â€¢ Displays result with currency symbol"));
        leftCard.add(Box.createVerticalStrut(12));
        leftCard.add(createFeatureLabel("â€¢ Smooth validation and status updates"));

        RoundedPanel rightCard = new RoundedPanel(30, new Color(255, 255, 255, 238));
        rightCard.setLayout(new GridBagLayout());
        rightCard.setBorder(new EmptyBorder(28, 28, 28, 28));
        rightCard.setPreferredSize(new Dimension(540, 500));

        GridBagConstraints rc = new GridBagConstraints();
        rc.insets = new Insets(10, 10, 10, 10);
        rc.gridx = 0;
        rc.gridy = 0;
        rc.gridwidth = 2;
        rc.fill = GridBagConstraints.HORIZONTAL;
        rc.weightx = 1.0;

        JLabel formTitle = new JLabel("Convert Instantly");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        formTitle.setForeground(new Color(35, 40, 80));
        rightCard.add(formTitle, rc);

        rc.gridy++;
        JLabel amountLabel = new JLabel("Enter Amount");
        amountLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        amountLabel.setForeground(new Color(40, 40, 40));
        rightCard.add(amountLabel, rc);

        rc.gridy++;
        amountField = new JTextField("1");
        styleTextField(amountField);
        rightCard.add(amountField, rc);

        rc.gridy++;
        rc.gridwidth = 1;
        rc.weightx = 0.5;

        JLabel fromLabel = new JLabel("From");
        fromLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        fromLabel.setForeground(new Color(40, 40, 40));
        rightCard.add(fromLabel, rc);

        rc.gridx = 1;
        JLabel toLabel = new JLabel("To");
        toLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        toLabel.setForeground(new Color(40, 40, 40));
        rightCard.add(toLabel, rc);

        rc.gridy++;
        rc.gridx = 0;
        fromCurrencyBox = new JComboBox<>(CURRENCIES.keySet().toArray(new String[0]));
        styleComboBox(fromCurrencyBox);
        fromCurrencyBox.setSelectedItem("USD - US Dollar");
        rightCard.add(fromCurrencyBox, rc);

        rc.gridx = 1;
        toCurrencyBox = new JComboBox<>(CURRENCIES.keySet().toArray(new String[0]));
        styleComboBox(toCurrencyBox);
        toCurrencyBox.setSelectedItem("INR - Indian Rupee");
        rightCard.add(toCurrencyBox, rc);

        rc.gridy++;
        rc.gridx = 0;
        rc.gridwidth = 2;
        rc.weightx = 1.0;
        rc.insets = new Insets(18, 10, 18, 10);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(420, 52));

        RoundedButton swapButton = new RoundedButton("Swap", new Color(120, 99, 255), Color.WHITE);
        RoundedButton convertButton = new RoundedButton("Convert", new Color(34, 197, 94), Color.WHITE);
        RoundedButton resetButton = new RoundedButton("Reset", new Color(239, 68, 68), Color.WHITE);

        buttonPanel.add(swapButton);
        buttonPanel.add(convertButton);
        buttonPanel.add(resetButton);

        rightCard.add(buttonPanel, rc);

        rc.gridy++;
        rc.insets = new Insets(5, 10, 10, 10);
        rc.fill = GridBagConstraints.HORIZONTAL;

        RoundedPanel resultPanel = new RoundedPanel(24, new Color(243, 247, 255));
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        resultPanel.setPreferredSize(new Dimension(420, 160));

        resultLabel = new JLabel("Converted Amount: --");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        resultLabel.setForeground(new Color(28, 32, 74));
        resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        rateLabel = new JLabel("Exchange Rate: --");
        rateLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        rateLabel.setForeground(new Color(70, 70, 70));
        rateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        updateLabel = new JLabel("Last Update: --");
        updateLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        updateLabel.setForeground(new Color(70, 70, 70));
        updateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        statusLabel = new JLabel("Ready");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        statusLabel.setForeground(new Color(0, 128, 96));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        resultPanel.add(resultLabel);
        resultPanel.add(Box.createVerticalStrut(14));
        resultPanel.add(rateLabel);
        resultPanel.add(Box.createVerticalStrut(10));
        resultPanel.add(updateLabel);
        resultPanel.add(Box.createVerticalStrut(10));
        resultPanel.add(statusLabel);

        rightCard.add(resultPanel, rc);

        swapButton.addActionListener((ActionEvent e) -> {
            Object temp = fromCurrencyBox.getSelectedItem();
            fromCurrencyBox.setSelectedItem(toCurrencyBox.getSelectedItem());
            toCurrencyBox.setSelectedItem(temp);
        });

        resetButton.addActionListener((ActionEvent e) -> {
            amountField.setText("1");
            fromCurrencyBox.setSelectedItem("USD - US Dollar");
            toCurrencyBox.setSelectedItem("INR - Indian Rupee");
            resultLabel.setText("Converted Amount: --");
            rateLabel.setText("Exchange Rate: --");
            updateLabel.setText("Last Update: --");
            statusLabel.setText("Reset completed");
            statusLabel.setForeground(new Color(220, 38, 38));
        });

        convertButton.addActionListener((ActionEvent e) -> convertCurrency());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.42;
        mainPanel.add(leftCard, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.58;
        mainPanel.add(rightCard, gbc);

        return mainPanel;
    }

    private JLabel createFeatureLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setForeground(new Color(245, 248, 255));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 18));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 215, 230), 1),
                new EmptyBorder(12, 14, 12, 14)
        ));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(30, 30, 30));
        field.setCaretColor(new Color(80, 80, 80));
    }

    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("SansSerif", Font.PLAIN, 16));
        box.setBackground(Color.WHITE);
        box.setForeground(new Color(30, 30, 30));
        box.setFocusable(false);
    }

    private void convertCurrency() {
        String amountText = amountField.getText().trim();

        if (amountText.isEmpty()) {
            showError("Please enter an amount.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
            if (amount < 0) {
                showError("Amount cannot be negative.");
                return;
            }
        } catch (NumberFormatException ex) {
            showError("Please enter a valid numeric amount.");
            return;
        }

        String fromDisplay = (String) fromCurrencyBox.getSelectedItem();
        String toDisplay = (String) toCurrencyBox.getSelectedItem();

        if (fromDisplay == null || toDisplay == null) {
            showError("Please select both currencies.");
            return;
        }

        String fromCode = CURRENCIES.get(fromDisplay);
        String toCode = CURRENCIES.get(toDisplay);

        statusLabel.setText("Fetching latest rates...");
        statusLabel.setForeground(new Color(37, 99, 235));

        SwingWorker<ConversionResult, Void> worker = new SwingWorker<>() {
            @Override
            protected ConversionResult doInBackground() throws Exception {
                return fetchConversion(fromCode, toCode, amount);
            }

            @Override
            protected void done() {
                try {
                    ConversionResult result = get();
                    resultLabel.setText("Converted Amount: " + getCurrencySymbol(toCode) + " " + df.format(result.convertedAmount));
                    rateLabel.setText("Exchange Rate: 1 " + fromCode + " = " + df.format(result.rate) + " " + toCode);
                    updateLabel.setText("Last Update: " + result.lastUpdated);
                    statusLabel.setText("Conversion successful");
                    statusLabel.setForeground(new Color(22, 163, 74));
                } catch (Exception ex) {
                    showError("Failed to fetch exchange rates. Check your internet connection.");
                }
            }
        };

        worker.execute();
    }

    private ConversionResult fetchConversion(String fromCode, String toCode, double amount) throws Exception {
        String apiUrl = "https://open.er-api.com/v6/latest/" + fromCode;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP Error: " + responseCode);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        br.close();
        connection.disconnect();

        String json = response.toString();

        String resultField = extractString(json, "\"result\"\\s*:\\s*\"(.*?)\"");
        if (!"success".equalsIgnoreCase(resultField)) {
            throw new RuntimeException("API returned error.");
        }

        String lastUpdate = extractString(json, "\"time_last_update_utc\"\\s*:\\s*\"(.*?)\"");
        Map<String, Double> rates = extractRates(json);

        if (!rates.containsKey(toCode)) {
            throw new RuntimeException("Target currency not found.");
        }

        double rate = rates.get(toCode);
        double converted = amount * rate;

        return new ConversionResult(rate, converted, lastUpdate);
    }

    private String extractString(String json, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "--";
    }

    private Map<String, Double> extractRates(String json) {
        Map<String, Double> rates = new TreeMap<>();

        Pattern blockPattern = Pattern.compile("\"rates\"\\s*:\\s*\\{(.*?)\\}");
        Matcher blockMatcher = blockPattern.matcher(json);

        if (blockMatcher.find()) {
            String ratesBlock = blockMatcher.group(1);

            Pattern ratePattern = Pattern.compile("\"([A-Z]{3})\"\\s*:\\s*([-]?[0-9]+(?:\\.[0-9]+)?)");
            Matcher rateMatcher = ratePattern.matcher(ratesBlock);

            while (rateMatcher.find()) {
                String code = rateMatcher.group(1);
                double value = Double.parseDouble(rateMatcher.group(2));
                rates.put(code, value);
            }
        }

        return rates;
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(new Color(220, 38, 38));
        JOptionPane.showMessageDialog(this, message, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    private String getCurrencySymbol(String code) {
        switch (code) {
            case "USD": return "$";
            case "INR": return "â‚¹";
            case "EUR": return "â‚¬";
            case "GBP": return "Â£";
            case "JPY": return "Â¥";
            case "AUD": return "A$";
            case "CAD": return "C$";
            case "CHF": return "CHF";
            case "CNY": return "Â¥";
            case "AED": return "Ø¯.Ø¥";
            case "SGD": return "S$";
            case "NZD": return "NZ$";
            case "SAR": return "ï·¼";
            case "QAR": return "ï·¼";
            case "KWD": return "KD";
            case "BHD": return "BD";
            case "OMR": return "ï·¼";
            case "MYR": return "RM";
            case "THB": return "à¸¿";
            case "KRW": return "â‚©";
            case "ZAR": return "R";
            case "BRL": return "R$";
            case "RUB": return "â‚½";
            case "HKD": return "HK$";
            case "TRY": return "â‚º";
            case "NPR": return "â‚¨";
            case "PKR": return "â‚¨";
            case "BDT": return "à§³";
            case "LKR": return "Rs";
            default: return code;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new CurrencyConverterGUI();
        });
    }

    static class ConversionResult {
        double rate;
        double convertedAmount;
        String lastUpdated;

        ConversionResult(double rate, double convertedAmount, String lastUpdated) {
            this.rate = rate;
            this.convertedAmount = convertedAmount;
            this.lastUpdated = lastUpdated;
        }
    }

    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            GradientPaint gp = new GradientPaint(
                    0, 0, new Color(36, 37, 120),
                    getWidth(), getHeight(), new Color(91, 33, 182)
            );
            g2.setPaint(gp);
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setColor(new Color(255, 255, 255, 30));
            g2.fillOval(40, 40, 180, 180);
            g2.fillOval(getWidth() - 240, 70, 180, 180);
            g2.fillOval(120, getHeight() - 180, 220, 220);

            g2.dispose();
        }
    }

    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color backgroundColor;

        public RoundedPanel(int radius, Color backgroundColor) {
            this.radius = radius;
            this.backgroundColor = backgroundColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(backgroundColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    static class RoundedButton extends JButton {
        private final Color bgColor;
        private final Color fgColor;

        public RoundedButton(String text, Color bgColor, Color fgColor) {
            super(text);
            this.bgColor = bgColor;
            this.fgColor = fgColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(fgColor);
            setFont(new Font("SansSerif", Font.BOLD, 16));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(120, 46));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color fill = getModel().isArmed() ? bgColor.darker()
                    : (getModel().isRollover() ? bgColor.brighter() : bgColor);

            g2.setColor(fill);
            Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 24, 24);
            g2.fill(shape);

            FontMetrics fm = g2.getFontMetrics();
            Rectangle r = fm.getStringBounds(getText(), g2).getBounds();

            g2.setColor(fgColor);
            g2.setFont(getFont());
            g2.drawString(
                    getText(),
                    (getWidth() - r.width) / 2,
                    (getHeight() - r.height) / 2 + fm.getAscent()
            );

            g2.dispose();
        }
    }
}
