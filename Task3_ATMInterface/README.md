# 🏦 Ultimate ATM Simulation System (Java Swing)

A modern **ATM Machine Simulation** built using **Java and Swing GUI**.  
This project demonstrates **Object-Oriented Programming, event-driven programming, GUI development, animations, and data visualization**.

The system simulates real ATM operations including:

- PIN authentication
- Deposit and withdrawal
- Balance checking
- Transaction history
- Transaction receipt generation
- ATM card animation
- Live balance graph
- Multiple user accounts

---

# 📂 Project File

```
UltimateATM.java
```

The entire project is implemented inside a single Java file containing multiple classes.

---

# 🧩 Code Architecture

```
UltimateATM.java
│
├── BankAccount
├── GradientPanel
├── RoundedPanel
├── HoverButton
├── CardAnimationPanel
├── BalanceGraphPanel
└── UltimateATM (Main Class)
```

Each class is responsible for a specific functionality of the ATM system.

---

# 1️⃣ BankAccount Class

Represents a user's bank account and manages account data.

## Attributes

```java
private String accountHolder;
private String accountNumber;
private String pin;
private double balance;
private List<Double> balanceHistory;
```

## Constructor

```java
public BankAccount(String accountHolder, String accountNumber, String pin, double balance)
```

Initializes account details and stores the initial balance in history.

---

## Deposit Method

```java
public boolean deposit(double amount)
```

Logic:

1. Validate amount
2. Add amount to balance
3. Update balance history

```java
balance += amount;
balanceHistory.add(balance);
```

---

## Withdraw Method

```java
public boolean withdraw(double amount)
```

Logic:

1. Check sufficient balance
2. Deduct amount
3. Update balance history

```java
balance -= amount;
balanceHistory.add(balance);
```

---

## Getter Methods

```java
getAccountHolder()
getAccountNumber()
getPin()
getBalance()
getBalanceHistory()
```

Used to safely access account information.

---

# 2️⃣ GradientPanel Class

Creates a **gradient dashboard background**.

### Method

```java
protected void paintComponent(Graphics g)
```

Uses `Graphics2D` and `GradientPaint`.

```java
GradientPaint gp = new GradientPaint(0,0,c1,getWidth(),getHeight(),c2);
```

This produces a smooth gradient effect for the ATM dashboard.

---

# 3️⃣ RoundedPanel Class

Creates rounded UI panels used in the dashboard.

### Drawing Method

```java
g2.fillRoundRect(0,0,getWidth(),getHeight(),radius,radius);
```

Improves the UI by creating card-style panels.

---

# 4️⃣ HoverButton Class

Custom button component with hover animation.

### Constructor

```java
public HoverButton(String text, Color normalColor, Color hoverColor)
```

### Hover Logic

```java
addMouseListener(new MouseAdapter(){
    public void mouseEntered(MouseEvent e)
    public void mouseExited(MouseEvent e)
});
```

The button color changes when the mouse pointer enters the button.

---

# 5️⃣ CardAnimationPanel Class

Simulates an ATM card sliding animation.

### Variables

```java
private int cardX = -220;
private Timer timer;
```

### Animation Logic

```java
timer = new Timer(12, e -> {
    cardX += 8;
    repaint();
});
```

The card gradually moves into the screen to create a smooth animation.

---

# 6️⃣ BalanceGraphPanel Class

Displays a **live balance graph** showing transaction history.

### Steps

1. Reads balance history
2. Finds minimum and maximum values
3. Draws graph lines

```java
g2.drawLine(x1,y1,x2,y2);
```

The graph updates automatically whenever a transaction occurs.

---

# 7️⃣ UltimateATM Class (Main Application)

This class controls the entire ATM system.

### Responsibilities

- User login system
- GUI layout
- Event handling
- Transaction processing
- Receipt generation
- Balance graph updates

---

## User Login System

Multiple users are stored using a `HashMap`.

```java
private Map<String, BankAccount> accounts = new LinkedHashMap<>();
```

Example accounts:

| User | PIN |
|-----|-----|
Ruhi | 1234  
Ayesha | 4321  
Sana | 1111  

Users must select their account and enter the correct PIN.

---

## GUI Components

The dashboard contains:

```
Gradient Dashboard
│
├── ATM Card Animation
├── Amount Input Field
├── Transaction Buttons
├── Transaction History
└── Balance Graph
```

---

## Transaction Buttons

```
Deposit
Withdraw
Check Balance
Clear
Receipt
Logout
```

Each button is connected to an event handler.

---

## Event Handling

```java
public void actionPerformed(ActionEvent e)
```

Handles all button operations such as:

- deposit
- withdraw
- check balance
- clear input
- show receipt
- logout

---

# 🧾 Transaction Receipt

After each transaction a receipt window appears.

Example format:

```
SMART ATM RECEIPT

Account Holder : Ruhi
Account Number : SB-2048-1001
Transaction    : Deposit
Amount         : ₹500.00
Balance        : ₹10500.00
Date & Time    : 07-03-2026
```

---

# 📊 Live Balance Graph

The graph updates whenever:

- Deposit occurs
- Withdrawal occurs

Balance values are stored in:

```java
balanceHistory
```

This list is used to render the graph.

---

# 🚀 How to Run

### 1️⃣ Compile

```
javac UltimateATM.java
```

### 2️⃣ Run

```
java UltimateATM
```

---

# 🧠 Concepts Used

- Object-Oriented Programming
- Java Swing GUI
- Event Driven Programming
- Graphics2D Rendering
- Custom Components
- Data Visualization

---

# 🎯 Learning Outcome

This project demonstrates how to build a **realistic desktop application using Java Swing** and apply **OOP concepts to simulate a banking system**.

---

# 👩‍💻 Author

**Umme Ruhi**

Computer Science Engineering Student  
Passionate about Full Stack Development and Software Engineering

GitHub: https://github.com/ummeruhi  
