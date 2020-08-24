import java.awt.image.WritableRaster;

public class paintShape {
	public static void paintshape(WritableRaster wRaster) throws InterruptedException{
		// TODO Auto-generated method stub
		//done=false;
		for(int i=0;i<handler.shapes.length;i++){
			for(int j=0;j<handler.shapes[i].faces.length;j++){
					boolean paintdone=false;
					while(paintdone==false){
						//System.out.println("ddddd");
						paintdone=true;
						for(triangleThread f : handler.h){
							if(f.shapeNo<i){
								paintdone=false;
							}
						}
					}
					triangleDivide.init(handler.shapes[i].faces[j], i, j, 0,0, 0, handler.threadCount-1);
					
					
			}
			
		}
		
		//done=true;
	}
	
	
	static double calcPeripheral(boolean yAxis, double[] xyz) {
		// TODO Auto-generated method stub
		double[] xy = camera.imageGeometry(xyz,true);
		if(yAxis==true){
			return xy[0];
		}else{
			return xy[1];
		}
		
	}
	
	static double[] calcPeripheral(double[] xyz, double[] shapeCo) {
		// TODO Auto-generated method stub
		
		double[] xy = camera.imageGeometry(new double[]{xyz[0]+shapeCo[0],xyz[1]+shapeCo[1],xyz[2]+shapeCo[2]},false);
		
			return xy;
		
	}
	
	static double[] calcPeripheral(double[] xyz, double[] shapeCo, int[] behind) {
		// TODO Auto-generated method stub
		
		double[] xy = camera.imageGeometry(new double[]{xyz[0]+shapeCo[0],xyz[1]+shapeCo[1],xyz[2]+shapeCo[2]},false);
		if(xy[2]<0){
			//triangleDivide.behind++;
			behind[0]++;
		}
			return xy;
		
	}

}
