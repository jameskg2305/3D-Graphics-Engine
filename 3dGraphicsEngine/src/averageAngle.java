
public class averageAngle{
	
	
	static void calcAverageAngle(double[] xyz, face f, edge[] e){
		float[] avg = new float[]{0,0,0};
		
		for(edge u : e){
				avg[0] += u.c[0].xyz[0];
				avg[1] += u.c[0].xyz[1];
				avg[2] += u.c[0].xyz[2];
		}
		avg[0] /= e.length;
		avg[1] /= e.length;
		avg[2] /= e.length;
		f.avg[0] = avg[0]+xyz[0];
		f.avg[1] = avg[1]+xyz[1];
		f.avg[2] = avg[2]+xyz[2];
	}
	
}
