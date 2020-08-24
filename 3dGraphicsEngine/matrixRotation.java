
public class matrixRotation {
	static double[][] matrixRotat(double[][] xy, double[][] RotationMatrix){
		double[][] res= new double[xy.length][RotationMatrix[0].length];
		for(int h=0;h<xy.length;h++){//4
			for(int i=0;i<RotationMatrix[0].length;i++){//3
				double acc=0;
				for(int j=0;j<xy[0].length;j++){//3
					acc += xy[h][j]*RotationMatrix[j][i];
				}
				res[h][i] = acc;
			}
		}
		return res;
	}
}
