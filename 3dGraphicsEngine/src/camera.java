
public class camera {
	static double rad = 180/Math.PI;
	static double[][] c = new double[][]{
		{1/Math.atan(45/57.3),   0,    		-0,     			0},
		{  0,  	1.77/Math.atan(45/57.3)   , 	-0,     		0},
		{  0,   0,     		10000.0/(10000.0-0.1),     			1},
		{  0,   0,     		(-10000.0*0.1)/(1000.0-0.1),     	0}
	};
	static double[] imageGeometry(double[] orig, boolean debug){
		double[] res = new double[3];
		
		float vector[] = new float[]{(float) orig[0] - cameraNet.cameraPos[0],(float)orig[1] - cameraNet.cameraPos[1],(float)orig[2] - cameraNet.cameraPos[2]};
		vector = quaternionRotate.singleRotate(vector, 
				(cameraNet.origAnglex*rad/2)
				, new float[]{0,-1,0});
		
		vector = quaternionRotate.singleRotate(vector, 
				(cameraNet.origAngley*rad/2)
				, new float[]{1,0,0});
		
		
		
		double[][] xyz = new double[][]{{
			vector[0],
			vector[1],
			vector[2],1}};
		
		xyz = matrixRotation.matrixRotat(xyz, c);
		
		if(xyz[0][3]!=0){
			xyz[0][0]/=Math.abs(xyz[0][3]);
			xyz[0][1]/=Math.abs(xyz[0][3]);
			xyz[0][2]/=Math.abs(xyz[0][3]);
		}
		res[0] = xyz[0][0];
		res[1] = xyz[0][1];
		res[2] = xyz[0][2];
		res[0] +=1;
		res[1] +=1;
		res[0] *=handler.screenWid/2;
		res[1] *=handler.screenHei/2;
		return res;
	}
	
}
