
public class quaternions {
	double x=0;
	double y=0;
	double z=0;
	double w=0;
	
	public quaternions(double x, double y, double z, double w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public quaternions multi(quaternions q, quaternions v){
		
		double qx = q.x;
		double qy = q.y;
		double qz = q.z;
		double qw = q.w;

		double vx = v.x;
		double vy = v.y;
		double vz = v.z;
		double vw = v.w;
		
		this.w = qw*vw - qx*vx - qy*vy - qz*vz;
		
		this.x = qw*vx + qx*vw + qy*vz - qz*vy;
		
		this.y = qw*vy - qx*vz + qy*vw + qz*vx;
		
		this.z = qw*vz + qx*vy - qy*vx + qz*vw;
		
		
		return q;
		
	}
	
}
