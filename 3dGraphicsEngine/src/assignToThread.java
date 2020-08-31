import java.util.Arrays;

/**
 * Assigns pending textures to threads in equal distribution
 * @author James
 * @since tuesday
 */
public class assignToThread {
	static triangleThread[] h = null;
	static void assign(face[] allFaces,int i, int j, int low, int high, int threads, int lowCount, int highCount, int actualcounter){
		if(threads>1){
			//face[] newface0 = Arrays.copyOfRange(allFaces, lowCount, (lowCount+highCount)/2);
			//face[] newface1 = Arrays.copyOfRange(allFaces, (lowCount+highCount)/2+1, highCount);
			
			assign(allFaces, i, j, low,    (low+high)/2, threads/2, lowCount, (lowCount+highCount)/2, actualcounter);
			assign(allFaces, i, j, (low+high)/2+1, high, threads/2, (lowCount+highCount)/2, highCount, actualcounter);
		}
		else if(lowCount<highCount){
			//System.out.println("ac is "+actualcounter);
			if(actualcounter<=low){
				//System.out.println("yes");
				h[low].shapeNo++;
				//handler.h[low].done=true;
			}else{
				//System.out.println(low +" "+lowCount+" "+highCount+" "+threads);
				//handler.h[low].shapeNo++;
				if(low==0){
					boolean paintdone=false;
					//long time = System.currentTimeMillis();
					while(paintdone==false){
						//if(i==0){
						//System.out.println(handler.shapes[0].id);
						//System.out.println("b");
						//}
						paintdone=true;
						for(triangleThread t : h){
							if(t.shapeNo<i){
								paintdone=false;
							}

						}
						/*try {
						Thread.sleep(0, 99999);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(h[low].faces==null){
						paintdone=true;
					}*/

					}

					paintdone = false;
				}
				//System.out.println(h[low].shapeNo+ " "+low+" "+i+" "+allFaces.length+" "+lowCount+" "+highCount);
				
				h[low].faces =  Arrays.copyOfRange(allFaces, lowCount, highCount);
				
				//handler.h[low].faces =  Arrays.copyOfRange(allFaces, lowCount, lowCount+1);
				//handler.h[low].lowC = lowCount;
				//handler.h[low].highC = highCount;
				h[low].first = 1;
				h[low].i = i;
				h[low].j = j;
				h[low].threads = threads;
				h[low].done=false;
				//System.out.println(low+" "+handler.h[low].shapeNo+" "+i+" "+handler.h[low].faces +" "+(highCount-lowCount));
				//System.out.println(h[low].faces+" "+h[low].done+" "+h[low].faces.length);
				
			}
		}
	}
}
