package Labirintus;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * A játékot megjelenítő ablak. Ezen a felületen fogunk tudni játszani. Ezen kívül
 * a menű részén új pályát lehet indítani és megnézni a ranglistát.
 * 
 * @author Hardi Roland(LG9P12)
 */
public class GameWindow extends BaseWindow {
    private GameBoard gameBoard;

    /**
     * Létrehozza a játékablak alap kinézetét.
     * @param player aktuális játékos 
     */
    public GameWindow(Player player) {
        
        setSize(610, 740);
        setLocationRelativeTo(null);

        try{
            gameBoard = new GameBoard(player, this);
        } catch(IOException e){
            System.out.println("A játékfelület megnyitása sikertelen: " + e.getMessage());
        }

        JMenuBar menuBar = new JMenuBar();
        JMenu newGameMenu = new JMenu("Új pálya");
                JMenuItem newGame = new JMenuItem(new AbstractAction("Másik pálya") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gameBoard.loadRandomGame();
            }
        });
        newGameMenu.add(newGame);

        JMenu leaderboardMenu = new JMenu("Toplista");
        JMenuItem bestPlayers = new JMenuItem(new AbstractAction("Top 10") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                new HighScoreWindow(gameBoard.getTopPlayers(), GameWindow.this);
            }
        });
        leaderboardMenu.add(bestPlayers);

        menuBar.add(newGameMenu);
        menuBar.add(leaderboardMenu);
        setJMenuBar(menuBar);

        Font fontStyle = new Font("SansSerif", Font.BOLD, 20);
        JPanel playerDataPanel = new JPanel();
        JLabel playerNameLabel = new JLabel();
        playerNameLabel.setText("Játékosnév: " + player.getName());
        playerNameLabel.setFont(fontStyle);
        JLabel PlayerScoreLabel = new JLabel();
        PlayerScoreLabel.setText("| Pontszám: " + player.getScore());
        PlayerScoreLabel.setFont(fontStyle);

        playerDataPanel.add(playerNameLabel);
        playerDataPanel.add(PlayerScoreLabel);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(playerDataPanel);
        getContentPane().add(gameBoard);

        setResizable(false);
        setVisible(true);
    }

}
