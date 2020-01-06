import static java.lang.Math.*;
public class Lab1 {
    public static void main(String[] args) {
        long[] a = {21, 19, 17, 15, 13, 11, 9, 7, 5};
        double[] x = new double[12];
        double[][] e = new double[9][12];
        for (int i = 0; i < x.length; i++) {
            x[i] = (int) round((random() * 22) - 14);
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 12; j++) {
                switch (toIntExact(a[i])) {
                    case 21: e[i][j] = atan(cos(tan(pow(x[j], 1/3))));
                        break;
                    case 5: case 13: case 15: case 19: e[i][j] = pow(cos(log(abs(x[j]))), pow((pow(2.0/(3.0*(x[j]-1)), x[j]))/3, 3));
                        break;
                    default: e[i][j] = pow(2*(x[j]+1), 3)*(exp(pow(x[j]/2, 9))-1);
                }
            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 12; j++) {
                System.out.printf("%.4f\t",e[i][j]);
            }
            System.out.println();
        }
    }
}
