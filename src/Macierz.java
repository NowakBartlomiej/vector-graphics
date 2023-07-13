import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class Macierz extends JPanel {
    int width, height;
    JLabel[][] komorki;

    public Macierz(int x, int y, int width, int height) {
        super();
        this.width = width;
        this.height = height;
        komorki = new JLabel[3][3];
        setLayout(null);
        setBounds(x, y, width, height);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                komorki[i][j] = new JLabel();
                komorki[i][j].setBounds((height / 3)*i,(width / 3)*j, width / 3, height / 3);
                komorki[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                komorki[i][j].setVerticalAlignment(SwingConstants.CENTER);
                komorki[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                add(komorki[i][j]);
            }
        }
    }

    public void setMacierz(double[][] m) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                DecimalFormat decimalFormat = new DecimalFormat("###.###");
                komorki[j][i].setText(decimalFormat.format(m[i][j]));
            }
        }
    }
}
