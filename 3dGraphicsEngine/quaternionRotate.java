
public class quaternionRotate {
	
	static double rad = 180/Math.PI;
	/**
	 * Rotates corners around the origin of the shape
	 * **/
	static void rotate(shape s, double[] vector, double i){
			 i/=2;
			 for(int f=0;f<s.faces.length;f++){
				 for(int e=0;e<s.faces[f].edges.length;e++){
					 
					 quaternions quaternion0 = new quaternions(
							 Math.sin(i/rad) * vector[0],
							 Math.sin(i/rad) * vector[1],
							 Math.sin(i/rad) * vector[2],
							 Math.cos(i/rad));
					 
					 quaternions quaternion1 = new quaternions(
							 s.faces[f].edges[e].c[0].xyz[0]/1,
							 s.faces[f].edges[e].c[0].xyz[1]/1,
							 s.faces[f].edges[e].c[0].xyz[2]/1,
							 0);
					 
					 quaternion0 = quaternion0.multi(quaternion0, quaternion1);
					 
					 double qdist = Math.sqrt(
							  Math.pow(Math.sin(i/rad) * vector[0],2)
							 +Math.pow(Math.sin(i/rad) * vector[1],2)
							 +Math.pow(Math.sin(i/rad) * vector[2],2)
							 +Math.pow(Math.cos(i/rad),2));
					 
					 quaternion1 = new quaternions(
							 (-Math.sin(i/rad) * vector[0])/qdist,
							 (-Math.sin(i/rad) * vector[1])/qdist,
							 (-Math.sin(i/rad) * vector[2])/qdist,
							 ( Math.cos(i/rad)            )/qdist
							 );
					 
					 quaternion0 = quaternion0.multi(quaternion0, quaternion1);
					 
					 
					 
					 s.faces[f].edges[e].c[0].xyz = new double[]{
							 quaternion0.x,
							 quaternion0.y,
							 quaternion0.z
							 };
					 

				 }
				 averageAngle.calcAverageAngle(s.xyz,s.faces[f], s.faces[f].edges);


		
			 }
	}
	/**
	 * Rotates a single point around a customary axis
	 * **/
	static float[] singleRotate(float[] coords ,double i, float[] vector){
		
		 quaternions quaternion0 = new quaternions(
				 Math.sin(i/rad) * vector[0],
				 Math.sin(i/rad) * vector[1],
				 Math.sin(i/rad) * vector[2],
				 Math.cos(i/rad));
		 
		 quaternions quaternion1 = new quaternions(
				 coords[0],
				 coords[1],
				 coords[2],
				 0);
		 
		 quaternion0 = quaternion0.multi(quaternion0, quaternion1);
		 
		 double qdist = Math.sqrt(
				  Math.pow(Math.sin(i/rad) * vector[0],2)
				 +Math.pow(Math.sin(i/rad) * vector[1],2)
				 +Math.pow(Math.sin(i/rad) * vector[2],2)
				 +Math.pow(Math.cos(i/rad),2));
		 //System.out.println(qdist +" "+Math.pow(Math.cos(i/rad),2));
		 quaternion1 = new quaternions(
				 (-Math.sin(i/rad) * vector[0])/qdist,
				 (-Math.sin(i/rad) * vector[1])/qdist,
				 (-Math.sin(i/rad) * vector[2])/qdist,
				 ( Math.cos(i/rad)            )/qdist
				 );
		 
		 quaternion0 = quaternion0.multi(quaternion0, quaternion1);
		 
		 
		 return vector = new float[]{
				 (float) quaternion0.x,
				 (float) quaternion0.y,
				 (float) quaternion0.z
				 };
		 
		 
		 

	}
	
}