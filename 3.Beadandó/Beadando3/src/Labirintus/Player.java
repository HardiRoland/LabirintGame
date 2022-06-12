package Labirintus;

/**
 *Játékos osztály. Ennek segítségével lehet eltárolni az éppen aktívan játszó 
 * személy adatait, mint például a név és a pontszám.
 * 
 * @author Hardi Roland(LG9P12)
 */
public class Player {
    public String name;
    public int score;

    /**
    * Játékos létrehozása a nevének megadásával.
     * @param name
    */
    public Player(String name) {
        this.name = name;
        score = 0;
    }

    /**
    * Játékos létrehozása a nevének és pontszámának megadásával.
     * @param name
     * @param score
    */
    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
    * Játékos nevének lekérdezése.
     * @return 
    */
    public String getName() {
        return name;
    }

    /**
    * Játékos pontszámának lekérdezése.
     * @return 
    */
    public int getScore() {
        return score;
    }

    /**
    * Játékos pontszámának beállítása.
     * @param score
    */
    public void setScore(int score) {
        this.score = score;
    }

}
