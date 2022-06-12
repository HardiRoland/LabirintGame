package Labirintus;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 * A toplistát megjelenítő oldal. Egy táblázat segítségével kiírja a top 10 játékos
 * nevét és pontszámát.
 *
 * @author Hardi Roland(LG9P12)
 */
public class HighScoreWindow extends JDialog {

    private final JTable table;

    /**
     * Létrehozza az oldalt feléítő táblázatot és feltöti adatokkal.
     *
     * @param topPlayers legjobb 10 játékost tartalmazó mátrix
     * @param parent a szülő eleme
     */
    public HighScoreWindow(String[][] topPlayers, JFrame parent) {
        super(parent, true);
        String header[] = {"Név", "Pontszám"};
        table = new JTable(topPlayers, header);
        table.setFillsViewportHeight(true);
        add(new JScrollPane(table));

        setSize(600, 300);
        setTitle("Ranglista");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
