//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package out;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TTTGraphics extends JFrame {
    private static final long serialVersionUID = 1L;
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final int CELL_SIZE = 120;
    public static final int BOARD_WIDTH = 360;
    public static final int BOARD_HEIGHT = 360;
    public static final int GRID_WIDTH = 10;
    public static final int GRID_WIDTH_HALF = 5;
    public static final int CELL_PADDING = 24;
    public static final int SYMBOL_SIZE = 72;
    public static final int SYMBOL_STROKE_WIDTH = 8;
    public static final Color COLOR_BG;
    public static final Color COLOR_BG_STATUS;
    public static final Color COLOR_GRID;
    public static final Color COLOR_CROSS;
    public static final Color COLOR_NOUGHT;
    public static final Font FONT_STATUS;
    private State currentState;
    private Seed currentPlayer;
    private Seed[][] board;
    private GamePanel gamePanel;
    private JLabel statusBar;

    public TTTGraphics() {
        this.initGame();
        this.gamePanel = new GamePanel();
        this.gamePanel.setPreferredSize(new Dimension(360, 360));
        this.gamePanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                int row = mouseY / 120;
                int col = mouseX / 120;
                if (TTTGraphics.this.currentState == TTTGraphics.State.PLAYING) {
                    if (row >= 0 && row < 3 && col >= 0 && col < 3 && TTTGraphics.this.board[row][col] == TTTGraphics.Seed.NO_SEED) {
                        TTTGraphics.this.currentState = TTTGraphics.this.stepGame(TTTGraphics.this.currentPlayer, row, col);
                        TTTGraphics.this.currentPlayer = TTTGraphics.this.currentPlayer == TTTGraphics.Seed.CROSS ? TTTGraphics.Seed.NOUGHT : TTTGraphics.Seed.CROSS;
                    }
                } else {
                    TTTGraphics.this.newGame();
                }

                TTTGraphics.this.repaint();
            }
        });
        this.statusBar = new JLabel("       ");
        this.statusBar.setFont(FONT_STATUS);
        this.statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));
        this.statusBar.setOpaque(true);
        this.statusBar.setBackground(COLOR_BG_STATUS);
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(this.gamePanel, "Center");
        cp.add(this.statusBar, "Last");
        this.setDefaultCloseOperation(3);
        this.pack();
        this.setTitle("Tic Tac Toe");
        this.setVisible(true);
        this.newGame();
    }

    public void initGame() {
        this.board = new Seed[3][3];
    }

    public void newGame() {
        for(int row = 0; row < 3; ++row) {
            for(int col = 0; col < 3; ++col) {
                this.board[row][col] = TTTGraphics.Seed.NO_SEED;
            }
        }

        this.currentPlayer = TTTGraphics.Seed.CROSS;
        this.currentState = TTTGraphics.State.PLAYING;
    }

    public State stepGame(Seed player, int selectedRow, int selectedCol) {
        this.board[selectedRow][selectedCol] = player;
        if ((this.board[selectedRow][0] != player || this.board[selectedRow][1] != player || this.board[selectedRow][2] != player) && (this.board[0][selectedCol] != player || this.board[1][selectedCol] != player || this.board[2][selectedCol] != player) && (selectedRow != selectedCol || this.board[0][0] != player || this.board[1][1] != player || this.board[2][2] != player) && (selectedRow + selectedCol != 2 || this.board[0][2] != player || this.board[1][1] != player || this.board[2][0] != player)) {
            for(int row = 0; row < 3; ++row) {
                for(int col = 0; col < 3; ++col) {
                    if (this.board[row][col] == TTTGraphics.Seed.NO_SEED) {
                        return TTTGraphics.State.PLAYING;
                    }
                }
            }

            return TTTGraphics.State.DRAW;
        } else {
            return player == TTTGraphics.Seed.CROSS ? TTTGraphics.State.CROSS_WON : TTTGraphics.State.NOUGHT_WON;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TTTGraphics();
            }
        });
    }

    static {
        COLOR_BG = Color.WHITE;
        COLOR_BG_STATUS = new Color(216, 216, 216);
        COLOR_GRID = Color.LIGHT_GRAY;
        COLOR_CROSS = new Color(211, 45, 65);
        COLOR_NOUGHT = new Color(76, 181, 245);
        FONT_STATUS = new Font("OCR A Extended", 0, 14);
    }

    public static enum State {
        PLAYING,
        DRAW,
        CROSS_WON,
        NOUGHT_WON;

        private State() {
        }
    }

    public static enum Seed {
        CROSS,
        NOUGHT,
        NO_SEED;

        private Seed() {
        }
    }

    class GamePanel extends JPanel {
        private static final long serialVersionUID = 1L;

        GamePanel() {
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setBackground(TTTGraphics.COLOR_BG);
            g.setColor(TTTGraphics.COLOR_GRID);

            for(int row = 1; row < 3; ++row) {
                g.fillRoundRect(0, 120 * row - 5, 359, 10, 10, 10);
            }

            for(int col = 1; col < 3; ++col) {
                g.fillRoundRect(120 * col - 5, 0, 10, 359, 10, 10);
            }

            Graphics2D g2d = (Graphics2D)g;
            g2d.setStroke(new BasicStroke(8.0F, 1, 1));

            for(int row = 0; row < 3; ++row) {
                for(int col = 0; col < 3; ++col) {
                    int x1 = col * 120 + 24;
                    int y1 = row * 120 + 24;
                    if (TTTGraphics.this.board[row][col] == TTTGraphics.Seed.CROSS) {
                        g2d.setColor(TTTGraphics.COLOR_CROSS);
                        int x2 = (col + 1) * 120 - 24;
                        int y2 = (row + 1) * 120 - 24;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (TTTGraphics.this.board[row][col] == TTTGraphics.Seed.NOUGHT) {
                        g2d.setColor(TTTGraphics.COLOR_NOUGHT);
                        g2d.drawOval(x1, y1, 72, 72);
                    }
                }
            }

            if (TTTGraphics.this.currentState == TTTGraphics.State.PLAYING) {
                TTTGraphics.this.statusBar.setForeground(Color.BLACK);
                TTTGraphics.this.statusBar.setText(TTTGraphics.this.currentPlayer == TTTGraphics.Seed.CROSS ? "X's Turn" : "O's Turn");
            } else if (TTTGraphics.this.currentState == TTTGraphics.State.DRAW) {
                TTTGraphics.this.statusBar.setForeground(Color.RED);
                TTTGraphics.this.statusBar.setText("It's a Draw! Click to play again");
            } else if (TTTGraphics.this.currentState == TTTGraphics.State.CROSS_WON) {
                TTTGraphics.this.statusBar.setForeground(Color.RED);
                TTTGraphics.this.statusBar.setText("'X' Won! Click to play again");
            } else if (TTTGraphics.this.currentState == TTTGraphics.State.NOUGHT_WON) {
                TTTGraphics.this.statusBar.setForeground(Color.RED);
                TTTGraphics.this.statusBar.setText("'O' Won! Click to play again");
            }

        }
    }
}
