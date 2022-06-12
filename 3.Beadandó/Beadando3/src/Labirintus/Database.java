package Labirintus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Az adatbázis osztálya. Ezen belül tudunk csatlakozni az adatbázishoz és
 * hozzáférni a benne tárolt adatokhoz. Ezeket le tudjuk kérdezni és át tudjuk 
 * írni az osztály segítségével.
 * 
 * @author Hardi Roland(LG9P12)
 */
public class Database {

    private Connection con;
    private ArrayList<Player> highscores;
    
    /**
     * Megteremti a kapcsolatot az adatbázis és a játék között.
     */
    public Database() {
        Connection c = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost/labyrinth?serverTimezone=UTC&user=root&password=Nem felejtem el1");
        } catch(Exception e){
            System.out.println("Sikertelen csatlakozás az adatbázishoz: " + e.getMessage());
        }
        con = c;
        highscores = new ArrayList<>();
        loadData();
    }
    
    public ArrayList<Player> getHightScores() {
        return highscores;
    }


    /**
     * Betölti az adatbázisban tárolt adatokat egy tömbbe.
     */
    private void loadData() {
        try ( Statement statement = con.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM highscores");
            while (resultSet.next()) {
                int place = resultSet.getInt("Place");
                String name = resultSet.getString("Name");
                int score = resultSet.getInt("Score");
                storeNewHighScore(new Player(name, score));
            }
        } catch (Exception e) {
            System.out.println("Az adatok nem találhatóak: " + e.getMessage());
        }
        
    }
    
    /**
     * A top 10 eltárolt játékosok sorrendjét állítja be pontszámaik szerint.
     * 
     * @param player az éppen aktuális játékos adatai vannak benne
     */
    public void storeNewHighScore(Player player){
        if(highscores.size() == 10){
            if(player.score > highscores.get(9).score){
                int i = 0;
                while (i < 10 && (player.score < highscores.get(i).score)){
                    i++;
                }
                highscores.remove(9);
                highscores.add(i,player);
                storeToData();
            }
        } else{
            highscores.add(player);     
        }

    }

    /**
     * Eltárolja a ranglistát az adatbázisba..
     */
    private void storeToData() {
        try ( Statement statement = con.createStatement()) {
            for (int i = 0; i < 10; i++) {
                statement.executeUpdate("UPDATE highscores SET Name = '" + highscores.get(i).name + "' WHERE Place = " + (i + 1) + "");
                statement.executeUpdate("UPDATE highscores SET Score = " + highscores.get(i).score + " WHERE Place = " + (i + 1) + "");
            }
        } catch (Exception e) {
            System.out.println("Az adatok mentése közben hiba lépett fel: " + e.getMessage());
        }
    }

}
