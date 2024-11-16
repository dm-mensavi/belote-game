# 🎉 Bar Management and Belote Tournament System 🃏🍹

Welcome to the **Bar Management and Belote Tournament System**, a Java-based application that combines pub management and a belote card game with tournament features. This system allows users to manage a bar, play belote games, and organize belote tournaments with an interactive and user-friendly experience.

---

## 🌟 Features

### 🏠 **Pub Management**
- Manage **clients**, **bartenders**, **servers**, and **suppliers**.
- Handle **stock management** for drinks in the bar.
- Simulate interactions between bar staff and clients, including serving drinks and buying rounds.
- Manage **cash register** transactions and stock replenishment by suppliers.

### 🃏 **Belote Game**
- Play the classic **Belote card game** with 2 teams of 2 players each.
- Deck operations: **shuffle**, **cut**, **deal**, and **reset**.
- Score calculation based on Belote rules (atouts and non-atouts scoring).
- Simulates rounds and declares the winning team.

### 🏆 **Belote Tournament**
- Organize **tournaments** with multiple teams.
- Register **teams of 2 players**, either manually or automatically.
- Generate and manage **match schedules**.
- Track scores and rankings on a **dynamic leaderboard**.
- Simulate matches where losing players buy drinks for winners.
- Distribute **prizes**: 50% of the registration pool to the winning team and 50% to the patronne.

---

## 🛠️ **Installation**

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/bar-management-belote.git
   cd bar-management-belote
   ```

2. **Set Up in NetBeans or Other IDE**
   - Open the project in your preferred IDE (e.g., NetBeans, IntelliJ, or Eclipse).
   - Ensure the project structure matches the `src` folder organization.

3. **Compile and Run**
   ```bash
   javac MainApp.java pubmanagement/*.java belote/*.java tournament/*.java utils/*.java
   java MainApp
   ```

---

## 🕹️ **How to Use**

### 🎮 **Main Menu**
1. **Manage Pub**: Simulate bar operations, including stock and client management.
2. **Play Belote Game**: Start a single Belote game between two teams.
3. **Play Belote Tournament**: Organize a tournament with multiple teams.
4. **Quit**: Exit the application.

### 🏠 **Pub Management**
- Add/remove clients and servers.
- Manage drink stock and cash register.
- Interact with bartenders, suppliers, and clients.

### 🃏 **Belote Game**
- Create teams of 2 players each.
- Simulate card dealing, rounds, and scoring.
- Determine the winning team based on classic Belote rules.

### 🏆 **Tournament**
- Register teams and players with skill levels (beginner to expert).
- Play matches and track scores on the leaderboard.
- End the tournament with prize distribution and a summary report.

---

## 📂 **Project Structure**

```
src/
├── main/
│   ├── MainApp.java         # Entry point of the program
├── pubmanagement/
│   ├── Bar.java             # Manages bar operations
│   ├── Client.java          # Represents a client in the bar
│   ├── Bartender.java       # Manages drinks and cash register
│   ├── Supplier.java        # Replenishes bar stock
│   └── ...
├── belote/
│   ├── BeloteGame.java      # Logic for the Belote game
│   ├── Deck.java            # Deck management
│   ├── Card.java            # Card representation
│   ├── Player.java          # Represents a player in the game
│   └── ...
├── tournament/
│   ├── Tournament.java      # Tournament management
│   ├── Team.java            # Represents a team in the tournament
│   ├── Match.java           # Represents a single match
│   ├── TournamentPlayer.java # Player with skill levels
│   └── ...
├── utils/
│   ├── InputValidator.java  # Handles input validation
│   └── ...
```

---

## 🌟 **Highlights**

- **Interactive UI**: Console-based menus with error handling and dynamic options.
- **Skill Levels**: Players with varying skill levels influence the gameplay.
- **Drinks and Rewards**: Losing teams buy drinks; winners gain popularity and prizes.
- **Customizable**: Easily extendable for new bar features or card games.

---

## 🚀 **Future Improvements**
- Add a **GUI interface** for better user experience.
- Enhance **Belote rules** to include advanced strategies for skilled players.
- Integrate **database support** for persistent data storage.

---

## 🤝 **Contributions**
We welcome contributions! If you’d like to improve the project, feel free to fork the repo, create a branch, and submit a pull request. 🙌

---

## 📜 **License**
This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## 📧 **Contact**
For questions or feedback, please email **your-email@example.com**. 💌

---

Enjoy managing your bar and playing Belote! 🍻🃏