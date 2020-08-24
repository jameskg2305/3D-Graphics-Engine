import java.awt.image.WritableRaster;
import java.util.concurrent.RecursiveAction;

class recurs extends RecursiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int threads = 0;
	int start;
	int fin;
	recurs p1;
	recurs p2;
	static WritableRaster wRaster;
	static int[] pixelarray;
	public recurs(int start, int fin, int threads){
		this.start = start;
		this.fin   = fin;
		this.threads = threads;
	}
	@Override
	protected void compute() {
		// TODO Auto-generated method stub
		if(threads>1){
		p1 = new recurs(start, (start+fin)/2, threads/2);
		p2 = new recurs((fin+start)/2, fin, threads/2);
		invokeAll(p1,p2);
		}else{
			//System.out.println(start+" "+fin);
			for(int p=start; p<fin;p++){
				for(int i=0; i<wRaster.getHeight();i++){
					
					wRaster.setPixel(p, i, new int[]{
										pixelarray[(p*wRaster.getHeight()*4)+(i*4)],
										pixelarray[(p*wRaster.getHeight()*4)+(i*4)+1],
										pixelarray[(p*wRaster.getHeight()*4)+(i*4)+2],
										pixelarray[(p*wRaster.getHeight()*4)+(i*4)+3]
										});
					
				}
			}
			
		}
	}
	
}
