import java.io.File;

public class shape{
	volatile double[] xyz;
	
	face[] faces;
	String id;
	public shape(double[] xyz, File input){
		this.xyz = xyz;
	}
	
	public shape(double[] xyz, face[] faces){
		this.xyz = xyz;
		this.faces = faces;
	}
	
	static double setAngle(double[] xyz, boolean updown){
		double origAngle = 0;
		if(updown==false){
			double dist = Math.sqrt(Math.pow(xyz[1],2)+Math.pow(xyz[2],2));
			if(dist!=0){
				origAngle = Math.atan(xyz[0]/xyz[2]);
			}else{
				if(xyz[0]<0){
					origAngle=270/(180/Math.PI);
				}else{
					origAngle=90/(180/Math.PI);
				}
			}
			if(xyz[2]<0 && xyz[0]<0){
				origAngle+=Math.PI;
			}
			else if(xyz[0]<0){
				origAngle+=Math.PI*2;
				origAngle%=Math.PI*2;
			}
			else if(xyz[2]<0){
				origAngle=Math.PI + origAngle;
			}
		}else{
			double dist = Math.sqrt(Math.pow(xyz[0],2)+Math.pow(xyz[2],2));
			if(dist!=0){
				origAngle = Math.atan(xyz[1]/dist);
			}else{
				origAngle = Math.atan(xyz[1]/xyz[0]);
			}
			if(xyz[1]<0){
				origAngle+=Math.PI*2;
				origAngle%=Math.PI*2;
			}
			
		}
		return origAngle;
	}
	
}
