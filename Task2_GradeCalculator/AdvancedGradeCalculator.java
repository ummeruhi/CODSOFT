import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class AdvancedGradeCalculator extends JFrame implements ActionListener {

    private JTextField studentNameField;
    private JTextField[] markFields;
    private JTextField[] subjectFields;
    private JButton calculateButton, resetButton;
    private JLabel totalValueLabel, averageValueLabel, percentageValueLabel;
    private JLabel gradeValueLabel, remarkValueLabel, statusValueLabel, studentDisplayLabel;
    private JProgressBar progressBar;
    private JPanel resultCard;

    public AdvancedGradeCalculator() {
        setTitle("Advanced Student Grade Calculator");
        setSize(920, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setBackground(new Color(10, 15, 30));
        add(mainPanel);

        JPanel headerPanel = new JPanel(null);
        headerPanel.setBounds(20, 20, 865, 85);
        headerPanel.setBackground(new Color(24, 36, 68));
        headerPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(70, 130, 255), 2, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(headerPanel);

        JLabel title = new JLabel("Student Grade Calculator Dashboard", SwingConstants.CENTER);
        title.setBounds(180, 12, 500, 30);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        headerPanel.add(title);

        JLabel subtitle = new JLabel("Enter subject details, calculate performance, and view results instantly", SwingConstants.CENTER);
        subtitle.setBounds(150, 48, 570, 20);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(200, 220, 255));
        headerPanel.add(subtitle);

        JPanel inputPanel = new JPanel(null);
        inputPanel.setBounds(20, 125, 400, 460);
        inputPanel.setBackground(new Color(22, 28, 45));
        inputPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(90, 110, 170), 2, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(inputPanel);

        JLabel inputTitle = new JLabel("Student Input Section");
        inputTitle.setBounds(115, 15, 220, 25);
        inputTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        inputTitle.setForeground(Color.WHITE);
        inputPanel.add(inputTitle);

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setBounds(30, 60, 120, 28);
        nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        nameLabel.setForeground(new Color(220, 225, 235));
        inputPanel.add(nameLabel);

        studentNameField = createTextField();
        studentNameField.setBounds(160, 60, 200, 30);
        inputPanel.add(studentNameField);

        JLabel subjectHeading = new JLabel("Subject");
        subjectHeading.setBounds(55, 105, 100, 25);
        subjectHeading.setFont(new Font("SansSerif", Font.BOLD, 15));
        subjectHeading.setForeground(Color.WHITE);
        inputPanel.add(subjectHeading);

        JLabel marksHeading = new JLabel("Marks");
        marksHeading.setBounds(260, 105, 100, 25);
        marksHeading.setFont(new Font("SansSerif", Font.BOLD, 15));
        marksHeading.setForeground(Color.WHITE);
        inputPanel.add(marksHeading);

        subjectFields = new JTextField[6];
        markFields = new JTextField[6];

        String[] defaultSubjects = {"Math", "Science", "English", "Computer", "Social", "Physics"};

        int y = 140;
        for (int i = 0; i < 6; i++) {
            subjectFields[i] = createTextField();
            subjectFields[i].setBounds(30, y, 170, 30);
            subjectFields[i].setText(defaultSubjects[i]);
            inputPanel.add(subjectFields[i]);

            markFields[i] = createTextField();
            markFields[i].setBounds(230, y, 130, 30);
            inputPanel.add(markFields[i]);

            y += 45;
        }

        calculateButton = new JButton("Calculate");
        calculateButton.setBounds(50, 420, 130, 35);
        styleButton(calculateButton, new Color(37, 99, 235), Color.WHITE);
        calculateButton.addActionListener(this);
        inputPanel.add(calculateButton);

        resetButton = new JButton("Reset");
        resetButton.setBounds(215, 420, 130, 35);
        styleButton(resetButton, new Color(220, 38, 38), Color.WHITE);
        resetButton.addActionListener(this);
        inputPanel.add(resetButton);

        JPanel outputPanel = new JPanel(null);
        outputPanel.setBounds(445, 125, 440, 460);
        outputPanel.setBackground(new Color(22, 28, 45));
        outputPanel.setBorder(new CompoundBorder(
                new LineBorder(new Color(90, 110, 170), 2, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(outputPanel);

        JLabel outputTitle = new JLabel("Performance Dashboard");
        outputTitle.setBounds(105, 15, 250, 25);
        outputTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        outputTitle.setForeground(Color.WHITE);
        outputPanel.add(outputTitle);

        studentDisplayLabel = new JLabel("Student: -");
        studentDisplayLabel.setBounds(30, 60, 350, 25);
        studentDisplayLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
        studentDisplayLabel.setForeground(new Color(173, 216, 255));
        outputPanel.add(studentDisplayLabel);

        totalValueLabel = createOutputLabel("Total Marks: -");
        totalValueLabel.setBounds(30, 105, 350, 28);
        outputPanel.add(totalValueLabel);

        averageValueLabel = createOutputLabel("Average Marks: -");
        averageValueLabel.setBounds(30, 145, 350, 28);
        outputPanel.add(averageValueLabel);

        percentageValueLabel = createOutputLabel("Percentage: -");
        percentageValueLabel.setBounds(30, 185, 350, 28);
        outputPanel.add(percentageValueLabel);

        resultCard = new JPanel(null);
        resultCard.setBounds(30, 230, 375, 120);
        resultCard.setBackground(new Color(30, 41, 59));
        resultCard.setBorder(new CompoundBorder(
                new LineBorder(new Color(100, 116, 139), 2, true),
                new EmptyBorder(10, 10, 10, 10)
        ));
        outputPanel.add(resultCard);

        gradeValueLabel = new JLabel("Grade: -");
        gradeValueLabel.setBounds(20, 15, 300, 25);
        gradeValueLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        gradeValueLabel.setForeground(Color.WHITE);
        resultCard.add(gradeValueLabel);

        remarkValueLabel = new JLabel("Remark: -");
        remarkValueLabel.setBounds(20, 50, 320, 22);
        remarkValueLabel.setFont(new Font("SansSerif", Font.PLAIN, 17));
        remarkValueLabel.setForeground(Color.WHITE);
        resultCard.add(remarkValueLabel);

        statusValueLabel = new JLabel("Status: -");
        statusValueLabel.setBounds(20, 80, 300, 22);
        statusValueLabel.setFont(new Font("SansSerif", Font.BOLD, 17));
        statusValueLabel.setForeground(Color.WHITE);
        resultCard.add(statusValueLabel);

        JLabel meterLabel = new JLabel("Performance Meter");
        meterLabel.setBounds(30, 375, 180, 22);
        meterLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        meterLabel.setForeground(new Color(200, 220, 255));
        outputPanel.add(meterLabel);

        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(30, 405, 375, 25);
        progressBar.setStringPainted(true);
        progressBar.setValue(0);
        progressBar.setString("0%");
        progressBar.setFont(new Font("SansSerif", Font.BOLD, 14));
        progressBar.setForeground(new Color(34, 197, 94));
        progressBar.setBackground(new Color(226, 232, 240));
        outputPanel.add(progressBar);

        setVisible(true);
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBackground(new Color(241, 245, 249));
        field.setForeground(Color.BLACK);
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(148, 163, 184), 1, true),
                new EmptyBorder(5, 8, 5, 8)
        ));
        return field;
    }

    private JLabel createOutputLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 18));
        label.setForeground(Color.WHITE);
        return label;
    }

    private void styleButton(JButton button, Color bg, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("SansSerif", Font.BOLD, 15));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(bg.brighter(), 1, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            calculateGrades();
        } else if (e.getSource() == resetButton) {
            resetFields();
        }
    }

    private void calculateGrades() {
        try {
            String studentName = studentNameField.getText().trim();
            if (studentName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter student name.");
                return;
            }

            int total = 0;
            int subjectCount = markFields.length;
            boolean anyFailSubject = false;

            for (int i = 0; i < subjectCount; i++) {
                String subjectName = subjectFields[i].getText().trim();
                String markText = markFields[i].getText().trim();

                if (subjectName.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter all subject names.");
                    return;
                }

                if (markText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter marks for all subjects.");
                    return;
                }

                int mark = Integer.parseInt(markText);

                if (mark < 0 || mark > 100) {
                    JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.");
                    return;
                }

                if (mark < 35) {
                    anyFailSubject = true;
                }

                total += mark;
            }

            double average = (double) total / subjectCount;
            double percentage = average;

            String grade;
            String remark;
            String status;

            if (anyFailSubject) {
                grade = "F";
                remark = "Failed in one or more subjects";
                status = "FAIL";
                setResultTheme(new Color(127, 29, 29), new Color(248, 113, 113));
            } else if (percentage >= 90) {
                grade = "A+";
                remark = "Outstanding Performance";
                status = "PASS";
                setResultTheme(new Color(20, 83, 45), new Color(34, 197, 94));
            } else if (percentage >= 80) {
                grade = "A";
                remark = "Excellent Performance";
                status = "PASS";
                setResultTheme(new Color(30, 64, 175), new Color(59, 130, 246));
            } else if (percentage >= 70) {
                grade = "B";
                remark = "Very Good Performance";
                status = "PASS";
                setResultTheme(new Color(133, 77, 14), new Color(234, 179, 8));
            } else if (percentage >= 60) {
                grade = "C";
                remark = "Good Performance";
                status = "PASS";
                setResultTheme(new Color(154, 52, 18), new Color(249, 115, 22));
            } else if (percentage >= 50) {
                grade = "D";
                remark = "Needs Improvement";
                status = "PASS";
                setResultTheme(new Color(131, 24, 67), new Color(236, 72, 153));
            } else {
                grade = "F";
                remark = "Poor Performance";
                status = "FAIL";
                setResultTheme(new Color(127, 29, 29), new Color(248, 113, 113));
            }

            studentDisplayLabel.setText("Student: " + studentName);
            totalValueLabel.setText("Total Marks: " + total + " / " + (subjectCount * 100));
            averageValueLabel.setText(String.format("Average Marks: %.2f", average));
            percentageValueLabel.setText(String.format("Percentage: %.2f%%", percentage));
            gradeValueLabel.setText("Grade: " + grade);
            remarkValueLabel.setText("Remark: " + remark);
            statusValueLabel.setText("Status: " + status);

            animateProgressBar((int) percentage);

            JOptionPane.showMessageDialog(this, "Result calculated successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter only valid numeric marks.");
        }
    }

    private void setResultTheme(Color cardBg, Color accent) {
        resultCard.setBackground(cardBg);
        gradeValueLabel.setForeground(accent);
        remarkValueLabel.setForeground(Color.WHITE);
        statusValueLabel.setForeground(accent);
        progressBar.setForeground(accent);
    }

    private void animateProgressBar(int target) {
        progressBar.setValue(0);
        Timer timer = new Timer(15, null);
        timer.addActionListener(new ActionListener() {
            int value = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (value <= target) {
                    progressBar.setValue(value);
                    progressBar.setString(value + "%");
                    value++;
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    private void resetFields() {
        studentNameField.setText("");

        for (int i = 0; i < markFields.length; i++) {
            markFields[i].setText("");
        }

        studentDisplayLabel.setText("Student: -");
        totalValueLabel.setText("Total Marks: -");
        averageValueLabel.setText("Average Marks: -");
        percentageValueLabel.setText("Percentage: -");
        gradeValueLabel.setText("Grade: -");
        remarkValueLabel.setText("Remark: -");
        statusValueLabel.setText("Status: -");

        resultCard.setBackground(new Color(30, 41, 59));
        gradeValueLabel.setForeground(Color.WHITE);
        remarkValueLabel.setForeground(Color.WHITE);
        statusValueLabel.setForeground(Color.WHITE);

        progressBar.setValue(0);
        progressBar.setString("0%");
        progressBar.setForeground(new Color(34, 197, 94));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdvancedGradeCalculator());
    }
}
