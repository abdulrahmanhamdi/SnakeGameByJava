# Snake Game (Java)

A classic Snake game built with Java Swing. Control a snake that grows by eating apples, avoid walls and your own tail, and watch out for special and poisonous apples that add risk and strategy.

---

## Project Overview

**Snake Game** is a desktop application that reimplements the classic Snake arcade game. The player controls a snake moving on a 700×700 grid. The snake grows when it eats regular apples and gains score; it must avoid hitting the walls, its own body, and dangerous apples. The game includes a start screen with an **About** dialog, in-game score and elapsed time, background music, and sound effects for eating apples and game over.

The application is written in **Java 21** and uses only the standard JDK and **Java Swing** for the UI and **javax.sound.sampled** for audio. There are no external frameworks or databases; all logic and assets are local.

---

## Tech Stack

| Category        | Technology / Library                          |
|----------------|-------------------------------------------------|
| Language       | Java 21                                         |
| UI             | Java Swing (`javax.swing`, `java.awt`)          |
| Audio          | `javax.sound.sampled` (WAV playback)            |
| Build          | IntelliJ IDEA module (no Maven/Gradle)          |
| JDK            | OpenJDK 21                                      |

No third-party libraries are required; the project runs with a standard JDK.

---

## Architecture

The project follows a simple single-package structure. The main window is a `JFrame` (`SnakePlayer`); the UI is split into:

- **Start screen** — `StartPanel`: menu with background image, “Start Game” and “About” buttons.
- **Game screen** — `Game`: main game panel (board, snake, apples, timers, drawing, input).

Input and game state are separated into:

- **Controller** — arrow-key handling and current direction.
- **Features** — score, time elapsed, and counts (e.g. apples / special apples eaten).

Audio is centralized in **MusicPlayer** (background music and one-shot sounds). **StyledButton** is a reusable button with hover/press colors used on the start screen.

```
                    ┌─────────────────┐
                    │   SnakePlayer   │  JFrame, entry point
                    │    (main)       │
                    └────────┬────────┘
                             │
              ┌──────────────┼──────────────┐
              │              │              │
              ▼              ▼              │
     ┌─────────────┐  ┌─────────────┐       │
     │ StartPanel  │  │    Game     │◄──────┘ startGame()
     │ (menu)      │  │ (game loop) │
     └──────┬──────┘  └──────┬──────┘
            │                │
            │         ┌──────┼──────┐
            │         │      │      │
            │         ▼      ▼      ▼
            │   Controller  Features  MusicPlayer
            │   (keys)      (score)   (sounds)
            │
            └──► StyledButton (Start Game, About)
```

---

## Folder Structure

```
SnakeGameByJava/
├── README.md                 # This file
├── SnakeGameByJava.iml       # IntelliJ module (source root: src, JDK 21)
├── .gitignore
├── .idea/                    # IntelliJ config (JDK, output dir, etc.)
│   ├── misc.xml
│   ├── modules.xml
│   └── ...
└── src/
    └── SnakeGame/            # Single package: all game code
        ├── SnakePlayer.java  # Entry point, JFrame, switches to Game
        ├── Game.java         # Game panel, loop, rendering, collision, timers
        ├── StartPanel.java   # Start screen and About dialog
        ├── Controller.java   # Arrow-key input and direction state
        ├── Features.java     # Score, time, apples eaten
        ├── MusicPlayer.java  # Background music and sound effects
        ├── StyledButton.java # Custom button (hover/press colors)
        └── resources/        # Expected at runtime (not in repo by default)
            ├── img/          # body.png, head.png, apple.png, apple2.png, apple3.png,
            │                 # back.png, you_lose.png, you_win.png, snake.png
            └── Sounds/       # background.wav, apple.wav, apple2.wav, apple3.wav,
                              # gameOver.wav, youWin.wav
```

### Responsibilities

| Path                    | Responsibility |
|-------------------------|----------------|
| `SnakePlayer.java`      | Main class; creates the frame, adds `Game` and `StartPanel`, and provides `startGame()` to show the game and start music. |
| `Game.java`             | Game panel: grid (700×700), snake movement, apple/poison/special apple logic, collision, timers (game tick, special apple 10s, poisonous 7s), drawing, reset. |
| `StartPanel.java`       | Start screen layout, background image, “Start Game” and “About” buttons, game description dialog. |
| `Controller.java`       | Key listener logic; updates left/right/up/down direction and prevents 180° turns. |
| `Features.java`         | Score (increase/decrease), elapsed time, apples eaten, special apples eaten. |
| `MusicPlayer.java`      | Load and play WAV files: looping background, one-shot sounds for apple, game over, win. |
| `StyledButton.java`     | JButton with custom paint and hover/pressed colors. |

---

## Core Features

- **Classic Snake gameplay** — Move with arrow keys; snake grows when eating regular apples.
- **Score and time** — Score (+5 per apple) and elapsed time shown on the game panel.
- **Regular apples** — Increase score and snake length.
- **Special apple** (appears on a 10s timer) — Eating it decreases score and ends the game (lose).
- **Poisonous apple** (appears on a 7s timer) — Eating it reduces score by 3 and shortens the snake by one segment.
- **Collision** — Game over on wall hit, self collision (after 4+ segments), or eating the special apple.
- **Win / lose screens** — “You Win” or “You Lose” image plus final score on lose; reset button to play again.
- **Start screen** — Background image, “Start Game”, “About” (game description in a dialog).
- **Audio** — Background music (loop) and sounds for eating apples, game over, and win.
- **Reset** — After game over, “Reset Game” restarts with a new snake and score.

---

## Data Flow

- **Startup**  
  `main()` → `SnakePlayer` frame → `StartPanel` and `Game` added (game not yet visible). User clicks “Start Game” → `startGame()` removes start UI, shows `Game`, focuses it, starts background music.

- **Input**  
  Key events in `Game` are passed to `Controller.keyPressed()`; `Controller` holds the current direction. Each game tick, `Game.move()` reads direction from `Controller` and updates the head position and body segments.

- **Game loop**  
  A Swing `Timer` (140 ms) drives `Game.actionPerformed()`: check apple/poison/special apple, then collision, then move, then `repaint()`. Two other timers toggle visibility of special apple (10s) and poisonous apple (7s).

- **Score and time**  
  `Game` holds a `Features` instance. Eating apples calls `Features.increaseScore()` / `decreaseScore()`; elapsed time comes from `Features.getTimeElapsed()`. Score and time are drawn in `Game.paintComponent()`.

- **Audio**  
  `MusicPlayer` static methods are called from `Game` and `SnakePlayer` (e.g. `startBackgroundMusic()`, `appleEatMusic()`, `playGameOverSound()`, `playYouWinSound()`). Paths point to WAV files under `src/SnakeGame/resources/Sounds/`.

- **Assets**  
  Images and sounds are loaded from file paths under `src/SnakeGame/resources/`. If `resources/` is missing, the game may throw or show nothing for images/audio; ensure the folder structure exists and paths match your run configuration.

---

## Setup & Installation

### Prerequisites

- **JDK 21** (e.g. [Adoptium](https://adoptium.net/) or [Oracle JDK 21](https://www.oracle.com/java/technologies/downloads/)).

### Resources (required at runtime)

The game expects assets under the project root:

- `src/SnakeGame/resources/img/` — PNGs: `body.png`, `head.png`, `apple.png`, `apple2.png`, `apple3.png`, `back.png`, `you_lose.png`, `you_win.png`, `snake.png`.
- `src/SnakeGame/resources/Sounds/` — WAVs: `background.wav`, `apple.wav`, `apple2.wav`, `apple3.wav`, `gameOver.wav`, `youWin.wav`.

If these are not in the repo, create the directories and add your own images and WAV files with these names (or adjust the paths in `Game.java`, `StartPanel.java`, and `MusicPlayer.java`).

### Build and run (IntelliJ IDEA)

1. Open the project in IntelliJ (open `SnakeGameByJava` or the folder containing `SnakeGameByJava.iml`).
2. Set project SDK to JDK 21 (File → Project Structure → Project).
3. Set the run configuration to use the project root as working directory (so that `src/SnakeGame/resources/` is found).
4. Run `SnakeGame.SnakePlayer` (right-click → Run, or Run → Run).

### Build and run (command line)

From the project root (parent of `src/`):

```bash
javac -d out src/SnakeGame/*.java
java -cp out SnakeGame.SnakePlayer
```

Use the same working directory so that paths like `src/SnakeGame/resources/img/body.png` resolve correctly. If your layout differs, adjust `-cp` and working directory accordingly.

---

## Usage

1. Run the application (see above). The start screen appears with “Start Game” and “About”.
2. Click **About** to read the short game description.
3. Click **Start Game** to enter the game. Background music starts.
4. Use **arrow keys** (↑ ↓ ← →) to move. The snake moves continuously; you only change direction.
5. Eat **green apples** to grow and add 5 points. Avoid:
   - **Walls** and **your own tail** (game over).
   - **Special apple** (different look, 10s cycle): eating it ends the game (lose).
   - **Poisonous apple** (7s cycle): eating it reduces score by 3 and shortens the snake.
6. When the game ends, use **Reset Game** to play again, or close the window to exit.

---

## License

This project does not specify a license in the repository. Use and distribution are subject to your own terms and any third-party asset licenses (images and sounds).
