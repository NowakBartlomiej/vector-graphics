import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Przesuniecia {
    private double[][] macierz = {{1,0,0},{0,1,0},{0,0,1}};
    private double[] wektorPrzesuniecia = {0,0};
    private double[] wektorSkalowania = {1, 1};
    private double stopienObrotu;
    private double dokladnosc = 0.01;

    public void przesun() {
        double[][] macierzPrzesuniecia = {{1,0,0},{0, 1, 0}, {wektorPrzesuniecia[0], wektorPrzesuniecia[1], 1}};
        macierz = mnozenieMacierzy(macierz, macierzPrzesuniecia);
    }

    public void obroc() {
        double[][] macierzObrotu = {{Math.cos(Math.toRadians(stopienObrotu)), Math.sin(Math.toRadians(stopienObrotu)), 0},{-1*Math.sin(Math.toRadians(stopienObrotu)), Math.cos(Math.toRadians(stopienObrotu)), 0}, {0, 0, 1}};
        macierz = mnozenieMacierzy(macierz, macierzObrotu);
    }

    public void skaluj() {
        double[][] macierzSkalowania = {{wektorSkalowania[0], 0, 0},{0, wektorSkalowania[1], 0}, {0, 0, 1}};
        macierz = mnozenieMacierzy(macierz, macierzSkalowania);
    }



    public double[][] mnozenieMacierzy(double[][] m1, double[][] m2){
        double[][] newMatrix = new double[m1.length][m2[0].length];
        for(int i = 0; i<newMatrix.length; i++){
            for(int j = 0; j<newMatrix[0].length; j++){
                newMatrix[i][j] = mnozenieKomorkiMacierzy(m1, m2, i, j);
            }
        }
        return newMatrix;
    }

    private double mnozenieKomorkiMacierzy(double[][] m1, double[][] m2, int row, int col) {
        double cell = 0;
        for(int i =0; i<m2.length; i++){
            cell+=m1[row][i] * m2[i][col];
        }
        return cell;
    }

    public ArrayList<Point> mnozeniePunktow(List<Point> list) {
        ArrayList<Point> newPoints = new ArrayList<>();
        int size = list.size();
        for(int i =0; i<size; i++){
            Point current = list.get(i);
            double[][] vector = {{current.x, current.y, 1}};
            vector = mnozenieMacierzy(vector, macierz);
            Point point = new Point((int)(vector[0][0]/vector[0][2]), (int)(vector[0][1]/vector[0][2]));
            newPoints.add(point);
        }
        return newPoints;
    }

    public double[][] getMacierz() {
        return macierz;
    }

    public double[] getWektorPrzesuniecia() {
        return wektorPrzesuniecia;
    }

    public double[] getWektorSkalowania() {
        return wektorSkalowania;
    }

    public double getStopienObrotu() {
        return stopienObrotu;
    }

    public double getDokladnosc() {
        return dokladnosc;
    }

    public void setMacierz(double[][] macierz) {
        this.macierz = macierz;
    }

    public void setWektorPrzesuniecia(double[] wektorPrzesuniecia) {
        this.wektorPrzesuniecia = wektorPrzesuniecia;
    }

    public void setWektorSkalowania(double[] wektorSkalowania) {
        this.wektorSkalowania = wektorSkalowania;
    }

    public void setStopienObrotu(double stopienObrotu) {
        this.stopienObrotu = stopienObrotu;
    }

    public void setDokladnosc(double dokladnosc) {
        this.dokladnosc = dokladnosc;
    }
}
