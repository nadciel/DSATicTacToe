package out;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic-Tac-Toe: Two-player Graphic version with better OO design.
 * The Board and Cell classes are separated in their own classes.
 */
public class TTT extends JPanel {
    private static final long serialVersionUID = 1L; // to prevent serializable warning

    // Define named constants for the drawing graphics
    public static java.lang.String TITLE = "Tic Tac Toe";
    public static Color COLOR_BG = Color.WHITE;
    public static Color COLOR_BG_STATUS = new Color(216, 216, 216);
    public static Color COLOR_CROSS = new Color(239, 105, 80);  // Red #EF6950

    // Define game objects
    private Board board;         // the game board
    private State currentState;  // the current state of the game
    private Seed currentPlayer;  // the current player
    private JLabel statusBar;    // for displaying status message

    /** Constructor to setup the UI and game components */
    public TTT() {

        // This JPanel fires MouseEvent
        super.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Get the row and column clicked
                int row = mouseY / Cell.SIZE;
                int col = mouseX / Cell.SIZE;

                if (currentState == State.PLAYING) {
                    if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                            && board.cells[row][col].content == Seed.NO_SEED) {
                        // Update cells[][] and return the new game state after the move
                        currentState = board.stepGame(currentPlayer, row, col);
                        // Play appropriate sound clip
                        if (currentState == State.PLAYING) {
                            SoundEffect.gogo.play();
                        } else {
                            SoundEffect.die.play();
                        }
                        // Switch player
                        currentPlayer = (currentPlayer == Seed.totoro) ? Seed.piggy : Seed.totoro;
                    }
                } else {        // game over
                    newGame();  // restart the game
                }
                // Refresh the drawing canvas
                TTT.this.repaint();
                if (TTT.this.currentState == State.PLAYING && TTT.this.currentPlayer == Seed.piggy) {
                    TTT.this.playComp();
                }
            }
        });

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel();
        //statusBar.setFont(TTTGraphics.FONT_STATUS);
        statusBar.setBackground(COLOR_BG_STATUS);
        statusBar.setOpaque(true);
        statusBar.setPreferredSize(new Dimension(300, 30));
        statusBar.setHorizontalAlignment(JLabel.LEFT);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));

        super.setLayout(new BorderLayout());
        super.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
        super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
        // account for statusBar in height
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));

        // Set up Game
        initGame();
        newGame();
    }

    private void playComp() {
        System.out.println(this.currentState);
        if (this.currentState == State.PLAYING) {
            int rowRandom = (int)(Math.random() * (double)2.0F) + 1;

            int colRandom;
            for(colRandom = (int)(Math.random() * (double)2.0F) + 1; this.board.cells[rowRandom][colRandom].content != Seed.NO_SEED; colRandom = (int)(Math.random() * (double)2.0F) + 1) {
                rowRandom = (int)(Math.random() * (double)2.0F) + 1;
            }

            System.out.println(rowRandom + " " + colRandom);
            if (rowRandom >= 0 && rowRandom < 3 && colRandom >= 0 && colRandom < 3 && this.board.cells[rowRandom][colRandom].content == Seed.NO_SEED) {
                this.currentState = this.board.stepGame(this.currentPlayer, rowRandom, colRandom);
                if (this.currentState == State.PLAYING) {
                    SoundEffect.gogo.play();
                } else {
                    SoundEffect.die.play();
                }

                this.currentPlayer = this.currentPlayer == Seed.totoro ? Seed.piggy : Seed.totoro;
            }

            this.repaint();
        }

    }

    /** Initialize the game (run once) */
    public void initGame() {
        board = new Board();  // allocate the game-board
    }

    /** Reset the game-board contents and the current-state, ready for new game */
    public void newGame() {
        for (int row = 0; row < Board.ROWS; ++row) {
            for (int col = 0; col < Board.COLS; ++col) {
                board.cells[row][col].content = Seed.NO_SEED; // all cells empty
            }
        }
        currentPlayer = Seed.totoro;    // cross plays first
        currentState = State.PLAYING;  // ready to play
    }

    /** Custom painting codes on this JPanel */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(COLOR_BG);
        this.board.paint(g);
        if (this.currentState == State.PLAYING) {
            this.statusBar.setForeground(Color.BLACK);
            this.statusBar.setText(this.currentPlayer == Seed.totoro ? "X's Turn" : "O's Turn");
        } else if (this.currentState == State.DRAW) {
            this.statusBar.setForeground(Color.RED);
            this.statusBar.setText("It's a Draw! Click to play again.");
        } else if (this.currentState == State.CROSS_WON) {
            this.statusBar.setForeground(Color.RED);
            this.statusBar.setText("'X' Won! Click to play again.");
        } else if (this.currentState == State.NOUGHT_WON) {
            this.statusBar.setForeground(Color.RED);
            this.statusBar.setText("'O' Won! Click to play again.");
        }

    }

    public static void play() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Tic Tac Toe");
                frame.setContentPane(new TTT());
                frame.setDefaultCloseOperation(3);
                frame.pack();
                frame.setLocationRelativeTo((Component)null);
                frame.setVisible(true);
            }
        });
    }

    static {
        COLOR_BG = Color.WHITE;
        COLOR_BG_STATUS = new Color(216, 216, 216);
        COLOR_CROSS = new Color(239, 105, 80);
        java.awt.Color COLOR_NOUGHT = new Color(64, 154, 225);
        java.awt.Font FONT_STATUS;
        FONT_STATUS = new Font("OCR A Extended", 0, 14);
    }
}