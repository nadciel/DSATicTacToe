//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package out;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TTT extends JPanel {
    private static final long serialVersionUID = 1L;
    public static final String TITLE = "Tic Tac Toe";
    public static final Color COLOR_BG;
    public static final Color COLOR_BG_STATUS;
    public static final Color COLOR_CROSS;
    public static final Color COLOR_NOUGHT;
    public static final Font FONT_STATUS;
    private Board board;
    private State currentState;
    private Seed currentPlayer;
    private JLabel statusBar;

    public TTT() {
        super.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / 120;
                int col = mouseX / 120;
                System.out.println("totoro" + row + " " + col);
                if (TTT.this.currentState == State.PLAYING) {
                    if (row >= 0 && row < 3 && col >= 0 && col < 3 && TTT.this.board.cells[row][col].content == Seed.NO_SEED) {
                        TTT.this.currentState = TTT.this.board.stepGame(TTT.this.currentPlayer, row, col);
                        if (TTT.this.currentState == State.PLAYING) {
                            SoundEffect.gogo.play();
                        } else {
                            SoundEffect.die.play();
                        }

                        TTT.this.currentPlayer = TTT.this.currentPlayer == Seed.totoro ? Seed.piggy : Seed.totoro;
                    }
                } else {
                    TTT.this.newGame();
                }

                TTT.this.repaint();
                if (TTT.this.currentState == State.PLAYING && TTT.this.currentPlayer == Seed.piggy) {
                    TTT.this.playComp();
                }

            }
        });
        this.statusBar = new JLabel();
        this.statusBar.setFont(FONT_STATUS);
        this.statusBar.setBackground(COLOR_BG_STATUS);
        this.statusBar.setOpaque(true);
        this.statusBar.setPreferredSize(new Dimension(300, 30));
        this.statusBar.setHorizontalAlignment(2);
        this.statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
        super.setLayout(new BorderLayout());
        super.add(this.statusBar, "Last");
        super.setPreferredSize(new Dimension(360, 390));
        super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));
        this.initGame();
        this.newGame();
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

    public void initGame() {
        this.board = new Board();
    }

    public void newGame() {
        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 3; ++col) {
                this.board.cells[row][col].content = Seed.NO_SEED;
            }
        }

        this.currentPlayer = Seed.totoro;
        this.currentState = State.PLAYING;
    }

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
        COLOR_NOUGHT = new Color(64, 154, 225);
        FONT_STATUS = new Font("OCR A Extended", 0, 14);
    }
}
