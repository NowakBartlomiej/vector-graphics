import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class MojeOkno extends JFrame implements ActionListener {
    int frameWidth = 690;
    int frameHeight = 500;
    int plotnoWidth = 500;
    int plotnoHeight = 500;
    int licznik = 1;

    private static BufferedImage plotno = null;
    private static JLabel ramka;
    MojeMenu menu = new MojeMenu();
    JButton zastosujLamana;
    JButton zastosujBezier;
    JButton resetPrzycisk;

    Przesuniecia przesuniecia;
    Macierz macierz;

    private ArrayList<Point> listaPunktow = new ArrayList<>();
    private ArrayList<Point> listPunkotowTMP = new ArrayList<>();
    private DefaultListModel<String> listModel;
    private JList<String> listaWsp;

    public MojeOkno() {
        super("Grafika wektorowa");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(frameWidth, frameHeight);
        setResizable(false);
        setLocationRelativeTo(null);
        setJMenuBar(menu);

        ramka = new JLabel();
        ramka.setBounds(0, 1, plotnoWidth, plotnoHeight);
        ramka.setBackground(Color.blue);
        ramka.setOpaque(true);
        ramka.setBorder(BorderFactory.createLineBorder(Color.black));
        add(ramka);

        plotno = new BufferedImage(plotnoWidth, plotnoHeight, 3);
        kolorujPoltno();
        odswiez();

        // Macierz
        przesuniecia = new Przesuniecia();
        macierz = new Macierz(502,1,170,170);
        macierz.setMacierz(przesuniecia.getMacierz());
        add(macierz);

        // Przyciski zastosuj
        zastosujLamana = new JButton("Zastosuj dla łamanej");
        zastosujLamana.addActionListener(this);
        zastosujLamana.setLayout(null);
        zastosujLamana.setFocusable(false);
        zastosujLamana.setBounds(502, 205, 170, 30);
        zastosujLamana.setVisible(true);
        add(zastosujLamana);

        zastosujBezier = new JButton("Zastosuj dla Beziera");
        zastosujBezier.addActionListener(this);
        zastosujBezier.setLayout(null);
        zastosujBezier.setFocusable(false);
        zastosujBezier.setBounds(502, 238, 170,30);
        zastosujBezier.setVisible(true);
        add(zastosujBezier);

        resetPrzycisk = new JButton("Resetuj macierz");
        resetPrzycisk.addActionListener(this);
        resetPrzycisk.setLayout(null);
        resetPrzycisk.setFocusable(false);
        resetPrzycisk.setBounds(502, 172, 170, 30);
        resetPrzycisk.setVisible(true);
        add(resetPrzycisk);

        // Lista punktow
        listModel = new DefaultListModel<>();
        listaWsp = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaWsp);
        scrollPane.setBounds(502, 271, 170, 165);
        add(scrollPane);

        przesuniecia.setDokladnosc(0.01);

        ustawNasluchZdarzen();
        setVisible(true);
    }

    private void ustawNasluchZdarzen() {
        ramka.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO MOzna cos dodac
                Point point = new Point(e.getX(), e.getY());
                listaPunktow.add(point);
                listPunkotowTMP.add(point);
                listModel.add(listModel.size(),    licznik + ": ( " + point.getX() + " ; " + point.getY() + " )");
                licznik++;
                Rysuj.rysujPunkt(plotno, point, Color.black);
                //System.out.println(e.getX() + " ; " + e.getY());
                odswiez();
            }
        });

        // Rysowanie
        menu.rysujLamana.addActionListener(this);
        menu.rysujBeziera.addActionListener(this);
        menu.dokladnosc.addActionListener(this);

        // Macierz
        menu.przesun.addActionListener(this);
        menu.obroc.addActionListener(this);
        menu.skaluj.addActionListener(this);

        // Wyczysc
        menu.wyczysc.addActionListener(this);
    }


    private void kolorujPoltno() {
        int width = plotno.getWidth();
        int height = plotno.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                plotno.setRGB(i, j, new Color(231, 231, 231).getRGB());
            }
        }
    }

    private void odswiez() {
        ramka.setIcon(new ImageIcon(plotno));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String label = e.getActionCommand();

        if (label.equals("Rysuj łamaną")) {
            Rysuj.rysujLamana(plotno, listaPunktow, new Color(24, 105, 248));
            odswiez();
        }

        if (label.equals("Rysuj beziera")) {
            Rysuj.rysujBeziera(plotno, listaPunktow, new Color(0, 10, 143), przesuniecia.getDokladnosc());
            odswiez();
        }

        if (label.equals("Dokładność")) {
            zmienDokladnosc();
        }

        if (label.equals("Przesuń")) {
            przesun();
        }

        if (label.equals("Obróć")) {
            obroc();
        }

        if (label.equals("Skaluj")) {
            skaluj();
        }

        if (label.equals("Resetuj macierz")) {
            double[][] m = {{1,0,0},{0,1,0},{0,0,1}};
            przesuniecia.setMacierz(m);
            macierz.setMacierz(m);
        }

        if (label.equals("Zastosuj dla łamanej")) {
            zastosujPrzesuniecie();
            Rysuj.rysujLamana(plotno, listPunkotowTMP, Color.red);
            odswiez();
        }

        if (label.equals("Zastosuj dla Beziera")) {
            zastosujPrzesuniecie();
            Rysuj.rysujBeziera(plotno, listPunkotowTMP, new Color(145, 0, 0), przesuniecia.getDokladnosc());
            odswiez();
        }

        if (label.equals("Wyczyść")) {
            wyczyscWszystko();
        }
    }

    private void wyczyscWszystko() {
        listaPunktow.clear();
        listPunkotowTMP.clear();
        listModel.clear();
        kolorujPoltno();
        odswiez();
    }

    private void zastosujPrzesuniecie() {
        ArrayList<Point> nowaLista = przesuniecia.mnozeniePunktow(listaPunktow);
        listPunkotowTMP.clear();
        int size = nowaLista.size();

        for (int i = 0; i <size; i++) {
            listPunkotowTMP.add(i, nowaLista.get(i));
        }
    }

    private void przesun() {
        DecimalFormat df = new DecimalFormat("###.###");
        String wektorPrzesuniecia = df.format(przesuniecia.getWektorPrzesuniecia()[0]) + " " + df.format(przesuniecia.getWektorPrzesuniecia()[1]);
        String output = wprowadzDaneWektoru("Wprowadź wektor przesunięcia", wektorPrzesuniecia);
        String[] vector = output.split(" ");
        double[] newVector = {Double.parseDouble(vector[0]), Double.parseDouble(vector[1])};
        przesuniecia.setWektorPrzesuniecia(newVector);
        przesuniecia.przesun();
        macierz.setMacierz(przesuniecia.getMacierz());
    }

    private void obroc() {
        double k = Double.parseDouble(wprowadzDane("Podaj kąt obrotu", przesuniecia.getStopienObrotu()));
        przesuniecia.setStopienObrotu(k);
        przesuniecia.obroc();
        macierz.setMacierz(przesuniecia.getMacierz());
    }

    private void skaluj() {
        DecimalFormat df = new DecimalFormat("###.###");
        String moveVector = df.format(przesuniecia.getWektorSkalowania()[0]) + " " + df.format(przesuniecia.getWektorPrzesuniecia()[1]);
        String output = wprowadzDaneWektoru("Wprowadź paramatry skalowania", moveVector);
        String[] vector = output.split(" ");
        double[] newVector = {Double.parseDouble(vector[0]), Double.parseDouble(vector[1])};
        przesuniecia.setWektorSkalowania(newVector);
        przesuniecia.skaluj();
        macierz.setMacierz(przesuniecia.getMacierz());
    }

    private void zmienDokladnosc() {
        String output = wprowadzDane("Podaj dokładność rysowania krzywej beziera:", przesuniecia.getDokladnosc());
        if(output != null && !output.equals("")){
            double d = Double.parseDouble(output);
            przesuniecia.setDokladnosc(d);
        }
    }

    private static String wprowadzDane(String wiadomosc, double x) {
        DecimalFormat df = new DecimalFormat("###.###");
        String inputValue = JOptionPane.showInputDialog(wiadomosc, df.format(x));
        if(inputValue != null){
            inputValue = inputValue.replaceAll(",",".");
            if(inputValue.isEmpty() || ! inputValue.matches("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")){
                JOptionPane.showMessageDialog(null, "Wprowadzono błędne dane", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                inputValue = wprowadzDane(wiadomosc, x);
            }
        }
        return inputValue;
    }

    private static String wprowadzDaneWektoru(String wiadomosc, String x) {
        String inputValue = JOptionPane.showInputDialog(wiadomosc, x);
        if(inputValue != null){
            inputValue = inputValue.replaceAll(",",".");
            if(inputValue.isEmpty() || ! inputValue.matches("^(((-?)(0|([1-9][0-9]*))(\\.[0-9]+)?)(\\s)?){2}$")){
                JOptionPane.showMessageDialog(null, "Wprowadzono błędne dane", "Ostrzeżenie", JOptionPane.WARNING_MESSAGE);
                inputValue = wprowadzDaneWektoru(wiadomosc, x);
            }
        }
        return inputValue;
    }
}
