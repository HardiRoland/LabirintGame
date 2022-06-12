package Labirintus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A játék motorja. Minden változás ennek az osztálynak segítségével jön létre.
 * Gondoskodik az elemek mozgatásáról és a pálya újrarajzolásáról.
 * 
 * @author Hardi Roland(LG9P12)
 */
public class GameBoard extends JPanel {

    private Database database;
    private GameData gameData;
    private final Player player;
    private int currentLevel;
    private final int maxLevel = 7;
    private GameWindow parent;
    private static Timer timer;
    private Image playerImage;
    private Image dragonImage;
    private Image wallImage;
    private Image floorImage;
    private Image escapeImage;
    private boolean escaped;

    /**
     * Elindítja a játékot. Mindennek beállítja az alap értékét. Létrehozza a
     * gomb lenyomását figyelő funkciót.
     * 
     * @param player aktuális játékos
     * @param parent szülőeleme
     * @throws IOException
     */
    public GameBoard(Player player, GameWindow parent) throws IOException {
        playerImage = LoadImage("pictures/player.png");
        dragonImage = LoadImage("pictures/dragon.png");
        wallImage = LoadImage("pictures/wall.png");
        floorImage = LoadImage("pictures/floor.png");
        escapeImage = LoadImage("pictures/escape.png");
        gameData = new GameData((int) (Math.random() * maxLevel) + 1);
        this.player = player;
        this.parent = parent;
        database = new Database();

        timer = new Timer(500, dragonMove());
        timer.start();

        setFocusable(true);
        setPreferredSize(new Dimension(510, 560));

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                int pressedKey = ke.getKeyCode();
                switch (pressedKey) {
                    case 37:
                        if (isAvailableMove(0, -1, gameData.getPlayerPos())) {
                            gameData.setPlayerPos(gameData.getPlayerPos().getX(), gameData.getPlayerPos().getY() - 1);
                        }
                        break;
                    case 38:
                        if (isAvailableMove(-1, 0, gameData.getPlayerPos())) {
                            gameData.setPlayerPos(gameData.getPlayerPos().getX() - 1, gameData.getPlayerPos().getY());
                        }
                        break;
                    case 39:
                        if (isAvailableMove(0, 1, gameData.getPlayerPos())) {
                            gameData.setPlayerPos(gameData.getPlayerPos().getX(), gameData.getPlayerPos().getY() + 1);
                        }
                        break;
                    case 40:
                        if (isAvailableMove(1, 0, gameData.getPlayerPos())) {
                            gameData.setPlayerPos(gameData.getPlayerPos().getX() + 1, gameData.getPlayerPos().getY());
                        }
                        break;
                    default:
                        break;
                }
                escaped = (gameData.getPlayerPos().getX() == gameData.getEscapePos().getX()) && (gameData.getPlayerPos().getY() == gameData.getEscapePos().getY());
                repaint();
                if (escaped) {
                    timer.stop();
                    setFocusable(false);
                    player.setScore(player.getScore() + 1);
                    showEscapedMessage();

                    GameWindow gameWindow = new GameWindow(player);
                    parent.setVisible(false);
                    gameWindow.setVisible(true);
                }
                checkGameOver();
            }
        });

    }

    /**
     * Felülírja a komonenseket kirajzoló algoritmust.
     * @param g képernyő grafikai felülete
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Image img = null;
        String[][] table = gameData.getTable();
        for (int i = 0; i < gameData.getNumOfRows(); i++) {
            for (int j = 0; j < gameData.getNumOfCols(); j++) {
                switch (table[i][j]) {
                    case "wall":
                        img = wallImage;
                        break;
                    case "floor":
                        img = floorImage;
                        break;
                }
                g2.drawImage(img, j * 40, i * 40, 40, 40, null);
            }
        }
        g2.drawImage(playerImage, gameData.getPlayerPos().getY() * 40, gameData.getPlayerPos().getX() * 40, 40, 40, null);
        g2.drawImage(dragonImage, gameData.getDragonPos().getY() * 40, gameData.getDragonPos().getX() * 40, 40, 40, null);
        g2.drawImage(escapeImage, gameData.getEscapePos().getY() * 40, gameData.getEscapePos().getX() * 40, 40, 40, null);
        Area outter = new Area(new Rectangle(0, 0, 700, 700));
        Ellipse2D.Double inner = new Ellipse2D.Double(gameData.getPlayerPos().getY() * 40 - 100, gameData.getPlayerPos().getX() * 40 - 100, 240, 240);
        g2.setColor(Color.BLACK);
        outter.subtract(new Area(inner));
        g2.fill(outter);
    }

    /**
     * Visszaadja a 10 legjobb játékos adatait.
     * @return legjobb játékosokat tartalmazó mátrix
     */
    public String[][] getTopPlayers() {
        String[][] Top = new String[10][2];
        ArrayList<Player> highScores = database.getHightScores();
        for (int i = 0; i < 10; i++) {
            Top[i][0] = highScores.get(i).name;
            Top[i][1] = "" + highScores.get(i).score;
        }
        return Top;
    }

    /**
     * Visszaadja az adatbázist.
     */
    public Database getDataBase() {
        return database;
    }

    /**
     * Visszaadja az aktuális játékos nevét.
     */
    public String getPlayerName() {
        return player.getName();
    }

    /**
     * Visszaadja az aktuális játékos pontszámát.
     */
    public int getPlayerScore() {
        return player.getScore();
    }

    /**
     * Megadja hogy adott pontra tud-e lépni az élőlény.
     * @param x x érték
     * @param y y érték
     * @param entityPos élőlény pozíciója
     * @return
     */
    public boolean isAvailableMove(int x, int y, Point entityPos) {
        if (gameData.getTable()[entityPos.getX() + x][entityPos.getY() + y] == "wall") {
            return false;
        }
        if (entityPos.getX() + x < 0 || entityPos.getX() + x > gameData.getNumOfRows() - 1) {
            return false;
        }
        if (entityPos.getY() + y < 0 || entityPos.getY() + y > gameData.getNumOfCols() - 1) {
            return false;
        }
        return true;
    }

    /**
     * Betölt egy új pályát.
     */
    public void loadRandomGame() {
        int x;
        do {
            x = (int) (Math.random() * maxLevel);
        } while (x == currentLevel);
        currentLevel = x;
        try {
            gameData.readLevel("src/Labirintus/levels/" + currentLevel + ".txt");
            repaint();
            setFocusable(true);
            requestFocusInWindow();
        } catch (IOException e) {
            System.out.println("A fájl nem található!");
        }
    }

    /**
     * Megjeleníti a győzelemért járó üzenetet.
     */
    private void showEscapedMessage() {
        JOptionPane.showMessageDialog(this, "Gratulálok kijutottál a labirintusból!");
    }

    /**
     * Betölt egy képet.
     */
    private Image LoadImage(String filename) throws IOException {
        URL url = GameBoard.class.getResource(filename);
        System.out.println(url);
        return ImageIO.read(url);
    }

    /**
     *  Megnézi hogy végetért-e a játék.
     */
    public void checkGameOver() {
        if (((gameData.getPlayerPos().getX() - gameData.getDragonPos().getX() == 0) && ((gameData.getPlayerPos().getY() - gameData.getDragonPos().getY() <= 1) && (gameData.getPlayerPos().getY() - gameData.getDragonPos().getY() >= -1))) || ((gameData.getPlayerPos().getY() - gameData.getDragonPos().getY() == 0) && ((gameData.getPlayerPos().getX() - gameData.getDragonPos().getX() <= 1) && (gameData.getPlayerPos().getX() - gameData.getDragonPos().getX() >= -1))) && timer.isRunning()) {
            stopTimer();
            setFocusable(false);
            database.storeNewHighScore(player);
            JOptionPane.showMessageDialog(this, "A sárkány legyőzött! A játéknak vége! Az eredménye mentésre került a toplistába. A gomb megnyomásával új játék indul.");
            parent.setVisible(false);
            RulesWindow rules = new RulesWindow();
            rules.setVisible(true);
        }
    }

    /**
     * Akcifigyelő. A sárkány mirányítását és mozgását foglalja össze.
     */
    private ActionListener dragonMove() {
        ActionListener dragonMove = new ActionListener() {
            int direction = 1;

            @Override
            public void actionPerformed(ActionEvent ae) {
                switch (direction) {
                    case 0:
                        if (isAvailableMove(0, -1, gameData.getDragonPos())) {
                            gameData.setDragonPos(gameData.getDragonPos().getX(), gameData.getDragonPos().getY() - 1);
                        }
                        break;
                    case 1:
                        if (isAvailableMove(-1, 0, gameData.getDragonPos())) {
                            gameData.setDragonPos(gameData.getDragonPos().getX() - 1, gameData.getDragonPos().getY());
                        }
                        break;
                    case 2:
                        if (isAvailableMove(0, 1, gameData.getDragonPos())) {
                            gameData.setDragonPos(gameData.getDragonPos().getX(), gameData.getDragonPos().getY() + 1);
                        }
                        break;
                    case 3:
                        if (isAvailableMove(1, 0, gameData.getDragonPos())) {
                            gameData.setDragonPos(gameData.getDragonPos().getX() + 1, gameData.getDragonPos().getY());
                        }
                        break;
                }
                direction = (int) (Math.random() * 4);

                repaint();

                checkGameOver();

            }

        };

        return dragonMove;
    }
    
    static void stopTimer() {
        timer.stop();
    }
}
