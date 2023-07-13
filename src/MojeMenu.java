import javax.management.JMException;
import javax.swing.*;

public class MojeMenu extends JMenuBar {
    // Rysuj
    JMenu rysuj = new JMenu("Rysuj");
    JMenuItem rysujLamana = new JMenuItem("Rysuj łamaną");
    JMenuItem rysujBeziera = new JMenuItem("Rysuj beziera");
    JMenuItem dokladnosc = new JMenuItem("Dokładność");

    JMenu macierz = new JMenu("Macierz");
    JMenuItem przesun = new JMenuItem("Przesuń");
    JMenuItem obroc = new JMenuItem("Obróć");
    JMenuItem skaluj = new JMenuItem("Skaluj");

    // Wyczysc
    JMenu wyczyscMenu = new JMenu("Wyczyść");
    JMenuItem wyczysc = new JMenuItem("Wyczyść");

    public MojeMenu() {
        rysuj.add(rysujLamana);
        rysuj.add(rysujBeziera);
        rysuj.add(dokladnosc);
        add(rysuj);

        macierz.add(przesun);
        macierz.add(obroc);
        macierz.add(skaluj);
        add(macierz);

        wyczyscMenu.add(wyczysc);
        add(wyczyscMenu);

    }
}
