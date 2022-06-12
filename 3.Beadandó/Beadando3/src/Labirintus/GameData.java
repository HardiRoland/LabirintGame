package Labirintus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * A játék elemeit eltároló osztály. Eltárolja a pálya kinézetét és méreteit. Ezen
 * kívül pedig a játékos pozícióját, sárkány pozícióját és a kijárat pozícióját.
 * @author Hardi Roland(LG9P12)
 */
public class GameData {

    private String[][] table;
    private int numOfRows;
    private int numOfCols;
    private Point playerPos;
    private Point dragonPos;
    private Point escapePos;
    
    /**
     * Megnyitja a szintnek a fájlját.
     * @param level szintnek a száma
    */
    public GameData(int level) {
        try {
            readLevel("src/Labirintus/levels/" + level +".txt");
        } catch (FileNotFoundException e) {
            System.out.println("This file is not exiting!");
        }
    }
    /**
     * Felrajzol mindent a képernyőre.
     * @param filename fájl neve
    */
    public void readLevel(String filename) throws FileNotFoundException {
        int rows;
        try ( Scanner sc = new Scanner(new BufferedReader(new FileReader(filename)))) {
            table = new String[15][15];
            rows = 0;
            while (sc.hasNext()) {
                String line = sc.nextLine();

                if (rows == 0) {
                    numOfCols = line.length();
                }
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'X') {
                        table[rows][i] = "wall";
                    } else {
                        table[rows][i] = "floor";
                    }
                }
                rows++;
            }
        }
        numOfRows = rows;

        dragonSpawn();
        playerSpawn();
        escapeSpawn();
    }
    
    /**
     * Helyére teszi a sárkányt.
     */
    private void dragonSpawn() {
        int i;
        int j;
        do {
            i = (int) (Math.random() * (int) (numOfRows / 2));
            j = (int) (Math.random() * (int) (numOfCols / 2) + (int) (numOfCols / 2));
        } while (table[i][j].equals("wall"));
        dragonPos = new Point(i, j);
    }
    
    /**
     * Helyére teszi a játékost.
     */
    private void playerSpawn() {
        playerPos = new Point(numOfRows - 2, 1);
    }
    
    /**
     * Helyére teszi a kijáratot.
     */
    private void escapeSpawn() {
        escapePos = new Point(1, numOfCols - 2);
    }
       
    /**
     * Visszaadja a táblát.
     */ 
    public String[][] getTable(){
        return table;
    }
    
    /**
     * Visszaadja a a sorok számát.
     */ 
    public int getNumOfRows() {
        return numOfRows;
    }
    
    /**
     * Visszaadja az oszlopok számát.
     */ 
    public int getNumOfCols() {
        return numOfCols;
    }
    
    /**
     * Visszaadja a játékos pozícióját.
     */ 
    public Point getPlayerPos() {
        return playerPos;
    }
    
    /**
     * Visszaadja a sárkány pozícióját.
     */ 
    public Point getDragonPos() {
        return dragonPos;
    }
    
    /**
     * Visszaadja a kijárat pozícióját.
     */ 
    public Point getEscapePos() {
        return escapePos;
    }
    
    /**
     * Beállítja a játékos pozícióját.
     */ 
    public void setPlayerPos(int x, int y) {
        this.playerPos = new Point(x, y);
    }
    
    /**
     * Beállítja a sárkány pozícióját.
     */ 
    public void setDragonPos(int x, int y) {
        this.dragonPos = new Point(x, y);
    }
    
}
