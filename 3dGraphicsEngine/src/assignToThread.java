import java.util.Arrays;

/**
 * Assigns pending textures to threads in equal distribution
 * @author James
 * @since tuesday
 */
public class assignToThread {
	static void assign(face[] allFaces, int i, int j, int low, int high, int threads, int lowCount, int highCount, int actualcounter){
		if(threads>1){
			//face[] newface0 = Arrays.copyOfRange(allFaces, lowCount, (lowCount+highCount)/2);
			//face[] newface1 = Arrays.copyOfRange(allFaces, (lowCount+highCount)/2+1, highCount);
			assign(allFaces, i, j, low,    (low+high)/2, threads/2, lowCount, (lowCount+highCount)/2, actualcounter);
			assign(allFaces, i, j, (low+high)/2+1, high, threads/2, (lowCount+highCount)/2, highCount, actualcounter);
		}
		else if(lowCount<highCount){
			//System.out.println("ac is "+actualcounter);
			if(actualcounter<=low){
				
				handler.h[low].shapeNo++;
				//handler.h[low].done=false;
				//System.out.println(low+" now is "+handler.h[low].shapeNo);
				//handler.h[low].faces=null;
				//System.out.println("nothing in thread "+low);
			}else{
				//System.out.println(low +" "+lowCount+" "+highCount+" "+threads);
			//handler.h[low].faces =null;
			handler.h[low].faces =  Arrays.copyOfRange(allFaces, lowCount, highCount);
			handler.h[low].first = 1;
			handler.h[low].i = i;
			handler.h[low].j = j;
			handler.h[low].threads = threads;
			handler.h[low].times = 2;
			handler.h[low].done=false;
			}
		}
	}
}
