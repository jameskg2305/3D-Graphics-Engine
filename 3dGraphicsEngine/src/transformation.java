/**
 Transforms a shape
 **/
public class transformation {
	static void transform(shape s, double[] vector){
		for(int i=0;i<3;i++){
			s.xyz[i] += vector[i];
		}
		for(int i=0;i<s.faces.length;i++){
			for(int j=0;j<3;j++){
				s.faces[i].avg[j]+=vector[j];
			}
		}
	}
}
