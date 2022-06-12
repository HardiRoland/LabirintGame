package Labirintus;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import Labirintus.GameWindow;

/**
 * Az ablakok alap osztálya, a JFrame kiterjesztése. Ez jelenik meg amikor
 * elindítjuk a programot. Megadja a program nevét és a panel méretét, ezen
 * kívül implementálja a kilépést.
 *
 * @author Hardi Roland(LG9P12)
 */
public class BaseWindow extends JFrame {

    /**
     * BaseWindow konstruktora. Itt adjuk meg a panel tetején megjelenő nevet,
     * beállítjuk a panel méretét. Átállítjuk, hogy kilépés előtt kérdezzen rá
     * biztos ki akar-e lépni a felhasználó.
     */
    public BaseWindow() {
        setTitle("Labirintus");
        setSize(400, 450);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                showExitConfirmation();
            }
        });
    }

    /**
     * Kilépés megerősítése. Megkérdezi a felhasználót, hogy biztos ki akar-e
     * lépni. Ez egy külön panelen jelenik meg igen és nem opciókkal.
     */
    private void showExitConfirmation() {
        int n = JOptionPane.showConfirmDialog(this, "Valóban ki akar lépni?",
                "Megerősítés", JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            doUponExit();
        }
    }

    /**
     * Kilépés a programból. Bezárja magát a program és leállítja az időzítőt
     * ha a játék ablakából hívjuk meg.
     */
    protected void doUponExit() {
        if(this instanceof GameWindow) {
            GameBoard.stopTimer();
        }
        dispose();
    }

}
