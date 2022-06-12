package Labirintus;

/**
 * Pont osztály. Egy x és y koordinátát tárol el amit le lehet kérdezni 
 * vagy akár meg lehet változtatni.
 * 
 * @author Hardi Roland(LG9P12)
 */
public class Point {

    private int x;
    private int y;
    /**
     * Az x és y koordináták alapján létrehoz egy Pontot.
     * @param x x érték
     * @param y y érték
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * A pont x értékét adja vissza.
     * @return x érték
     */
    public int getX() {
        return x;
    }
    
    /**
     * A pont y értékét adja vissza.
     * @return y érték
     */
    public int getY() {
        return y;
    }

    /**
     * Az x pont értékét lehet megváltoztatni.
     * @param x x érték
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Az y pont értékét lehet megváltoztatni.
     * @param y y érték
     */    
    public void setY(int y) {
        this.y = y;
    }

}
