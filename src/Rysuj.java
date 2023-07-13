import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Rysuj {
    public static void rysujPunkt(BufferedImage plotno, Point point, Color color) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if((point.x + i >= 0 && point.x + i < plotno.getWidth()) && (point.y + j >= 0 && point.y + j < plotno.getHeight())){
                    plotno.setRGB((int) (point.x + i), (int) (point.y + j), color.getRGB());
                }
            }
        }
    }

    public static void rysujLamana(BufferedImage plotno, ArrayList<Point> listaPunktow, Color color) {
        Point point = listaPunktow.get(0);
        double x1 = point.x;
        double y1 = point.y;

        for (int i = 1; i < listaPunktow.size(); i++) {
            point = listaPunktow.get(i);
            double x2 = point.x;;
            double y2 = point.y;

            Graphics2D g = (Graphics2D) plotno.getGraphics();
            g.setColor(color);
            g.drawLine((int) x1, (int)y1, (int)x2, (int)y2);
            x1 = x2;
            y1 = y2;
        }
    }

    public static void rysujBeziera(BufferedImage plotno, ArrayList<Point> listaPunktow, Color color, double dokladnosc) {
        ArrayList<Point> punkty = new ArrayList<>();
        int n;

        for (double t = 0; t < 1; t+= dokladnosc) {
            ArrayList<double[]> tmp = new ArrayList<>(listaPunktow.size() - 1);
            for (int i = 0; i <= listaPunktow.size() - 1; i++) {
                double x = listaPunktow.get(i).x;
                double y = listaPunktow.get(i).y;
                double[] tab = {x, y};
                tmp.add(i, tab);
            }

            n = listaPunktow.size() - 1;
            ArrayList<double[]> tmp1 = new ArrayList<>(listaPunktow.size() - 2);

            while (n > 0) {
                for (int i = 0; i <= n - 1; i++) {
                    double x1 = tmp.get(i)[0];
                    double y1 = tmp.get(i)[1];
                    double x2 = tmp.get(i+1)[0];
                    double y2 = tmp.get(i+1)[1];
                    double x = x1+(t*(x2-x1));
                    double y = y1 +(t*(y2-y1));
                    double[] tab = {x, y};
                    tmp1.add(i, tab);
                }

                n = n - 1;

                for (int i = 0; i <= n; i++) {
                    double x = tmp1.get(i)[0];
                    double y = tmp1.get(i)[1];
                    double[] tab = {x, y};
                    tmp.set(i, tab);
                }
            }
            int x = (int) tmp.get(0)[0];
            int y = (int) tmp.get(0)[1];
            Point punkt = new Point(x, y);
            punkty.add((int) t, punkt);
        }
        rysujLamana(plotno, punkty, color);
    }
}
