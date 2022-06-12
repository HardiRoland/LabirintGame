package Labirintus;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/**
 * A szabályokat ismertető főoldal. Ezzel lehet elindítani a játékot.
 * Először el lehet olvasni a szabályokat és ezután a gomb segítségével 
 * tudjuk elindítani a játékot.
 * 
 * @author Hardi Roland(LG9P12)
 */
public class RulesWindow extends BaseWindow {

    private Player player;
    private static RulesWindow rulesWindow;

    /**
     * Létrehozza a kezdőképernyőt. Erre ráteszi a szabálynak megadott szöveget
     * és egy gombot amivel el lehet indítani a játékot.
     */
    public RulesWindow() {
        JTextPane rules = new JTextPane();
        rules.setBackground(Color.lightGray);
        rules.setText("A játék célja, hogy minél több labirintusból jussunk ki élve. A játékos a bal alsó sarokban kezd, ahonnan a jobb felső sarokhoz kell eljutnia. Mindezt anélkül, hogy összetalálkozna a sárkánnyal. Ha egy szomszédos mezőre lép a sárkánnyal, akkor meghal. Minden egyes teljesített labirintus után 1 pontot kap. A ranglistán láthatja a 10 legjobb játékos eredményét.");
        Font rulesFontStyle = new Font("SansSerif", Font.BOLD, 20);
        rules.setFont(rulesFontStyle);
        setLocationRelativeTo(null);

        JButton start = new JButton();

        start.setText("Játék kezdése!");
        start.addActionListener(startAction());
        start.setAlignmentX(Component.CENTER_ALIGNMENT);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getContentPane().add(rules);
        getContentPane().add(start);
    }

    /**
     * Akciófigyelő. Ha aktiválják egy külön oldalon elkéri a játékos nevét
     * és megjeleníti a pályát.
     */
    private ActionListener startAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player = new Player(JOptionPane.showInputDialog(null, "Add meg a neved:"));
                GameWindow gameWindow = new GameWindow(player);
                gameWindow.setVisible(true);
                dispose();
            }
        };
    }

    /**
     * Ennek lefuttatásával indul el a program.
     * @param args paraméterek listája
     */
    public static void main(String[] args) {
        rulesWindow = new RulesWindow();
        rulesWindow.setVisible(true);
    }
}
