
import java.awt.Cursor;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import java.util.Arrays;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;

public class threeDGraphics{
	static boolean p=false;
	static long start = System.nanoTime();
	static int ocl = 0;
	static int jc = 0;
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		loadShapes.load();
		//System.out.println(raycast.collision(new double[]{-50,-80,100})[0]);
		handler.g = new graphical();

		handler.g.setBounds(handler.screenPosx,handler.screenPosy,handler.imageSizew,handler.imageSizeh);
		handler.g.setVisible(true);
		handler.g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		handler.g.requestFocus();
		//g.setFocusable(true);
		handler.g.setResizable(false);
		handler.g.setCursor(Cursor.CROSSHAIR_CURSOR);
		handler.j = new jp();
		//openCL.initialise();
		handler.g.add(handler.j);
		runThread r = new runThread();
		Thread t = new Thread(r);
		t.start();

		Thread[] y = new Thread[handler.threadCount];
		for(int i=0;i<y.length;i++){
			handler.h[i] = new triangleThread();
			handler.h[i].id = i;
			y[i] = new Thread(handler.h[i]);
			y[i].start();
		}

		quaternions q= new quaternions(
				 Math.sin(0.39267) * -0.707,
				 Math.sin(0.39267) * 0,
				 Math.sin(0.39267) * 0.707,
				 Math.cos(0.39267))
		;
		quaternions q1= new quaternions(50, 0, 50, 0);
		q.multi(q, q1);
		System.out.println(q.x+" "+q.y+" "+q.z+" "+q.w);
		
		double qdist = Math.sqrt(
				  Math.pow(Math.sin(0.39267) * -0.707,2)
				 +Math.pow(Math.sin(0.39267) * 0,2)
				 +Math.pow(Math.sin(0.39267) * 0.707,2)
				 +Math.pow(Math.cos(0.39267),2));
		 //System.out.println(qdist +" "+Math.pow(Math.cos(i/rad),2));
		 q1 = new quaternions(
				 (-Math.sin(0.39267) * -0.707)/qdist,
				 (-Math.sin(0.39267) * 0)/qdist,
				 (-Math.sin(0.39267) * 0.707)/qdist,
				 ( Math.cos(0.39267)            )/qdist
				 );
		 
		 q.multi(q, q1);
		 System.out.println(q.x+" "+q.y+" "+q.z+" "+q.w);
		long delta=0;
		long now = 0;
		//openCL.initialise();
		
		//for(int j=0;j<10;j++){
		//openCL.openCLmain();
		//javacuda.main();
		//}
		//System.exit(0);
		//Thread.sleep(3000);
		/*openCL.initialise();
		openCL.createBuffers();
		//System.out.println(Arrays.toString(jp.pixelarray));
		for(int j=0;j<5;j++){
			System.out.println(jp.pixelarray[j]);
		}
		for(int i=0;i<1;i++){
		openCL.openCLmain();
		//for(int j=0;j<5;j++){
			//System.out.println(jp.pixelarray[j]);
		//}
		}*/
		//openCL.readOutput();
		System.out.println("******************");
		
		while(runThread.running==true){
			now = System.nanoTime();
			delta+=(now-start);
			threeDGraphics.start = System.nanoTime();
			//System.out.println(runThread.done+" rthreads is "+runThread.paintdone);
			//System.out.println("rt.pdd is "+runThread.paintdone);
			if(runThread.done==true){
				
				//System.out.println("rt.pdd is "+runThread.paintdone);
				if(runThread.paintdone.tryAcquire()){


					paintShape.paintshape(jp.wRaster);
					//System.out.println("done is now false");
					runThread.done=false;
					
					handler.fps = ""+(int)((double)1000000000/(double)(System.nanoTime()-threeDGraphics.start))+" fps";
					
					
					
					runThread.paintdone.release();
				}
			}
			Thread.sleep(1);
			while(delta >= 1000000000/60){


				delta-=1000000000/60;
			}
		}
	}

}
class graphical extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//super.add(jp);

}


class runThread implements MouseListener, Runnable, KeyListener{
	double rad = 180/Math.PI;
	static boolean running = true;
	static boolean paused = false;
	long delta=0;
	static String fpstitle="";
	double lastX=(MouseInfo.getPointerInfo().getLocation().x);
	double lastY=(MouseInfo.getPointerInfo().getLocation().y);
	static Semaphore paintdone = new Semaphore(1);
	static boolean done=true;
	@Override
	public void run() {
		handler.g.addMouseListener(this);
		handler.g.addKeyListener(this);
		handler.j.addMouseListener(this);
		handler.j.addKeyListener(this);
		// TODO Auto-generated method stub
		long start = System.nanoTime();
		//r = new Robot();
		handler.j.revalidate();
		initialise.init();
		while(running){
			long now = System.nanoTime();
			delta+=(now-start);
			start =System.nanoTime();

			
			//System.out.println("rt.pp1 is "+paintdone+" "+done);
			
			//if(done==false){
			//System.out.println("rt.pp2 is "+paintdone+" "+done);
			//System.out.println("rt.pp3 is "+paintdone+" "+done);
			if(done==false){
				//System.out.println("rt.pp1 is "+paintdone+" "+done);
				if(paintdone.tryAcquire()){
					boolean alldone = false;
					//System.out.println("ff");
					while(alldone==false){
						//System.out.println("in a loop");
						alldone=true;
						for(int y=0;y<handler.threadCount;y++){
							//System.out.println(y+" "+handler.h[y].shapeNo+" "+handler.shapes.length+" "+handler.h[y].faces+" "+handler.h[y].paintDone);
							if(handler.h[y].shapeNo<handler.shapes.length){
								
								alldone=false;
							}
						}
					}
					if(alldone==true){
						
						for(int y=0;y<handler.threadCount;y++){
							handler.h[y].paintDone=false;
						}
						
						boolean paintdone=false;
						while(paintdone==false){
							paintdone=true;
							for(triangleThread f : handler.h){
								if(f.paintDone==false){
									paintdone=false;
								}
							}
						}
						
						cameraNet.origAnglex=cameraNet.duplicateorigAnglex;
						cameraNet.origAngley=cameraNet.duplicateorigAngley;
						cameraNet.cameraPos = cameraNet.dupcameraPos;
						
						handler.j.paint();
						
						for(int y=0;y<handler.threadCount;y++){
							handler.h[y].shapeNo=0;
						}

						runThread.done=true;

					}
					fpstitle = ""+(int)((double)1000000000/(double)(System.nanoTime()-start))+" fps";
					
					
					
					paintdone.release();
					
					
				}}
			//threeDGraphics.s.release();
			//}
			//}
			while(delta >= 1000000000/60){

				//start =System.currentTimeMillis();
				if(paused==false){
					runScript.script();
					cameraRotate.mouseListen(lastX, lastY);
					//if(jp.finishedPainting==true && paintdone.availablePermits()==1){
						//handler.shapes = sortObj(handler.shapes);

					//}

					lastX = (MouseInfo.getPointerInfo().getLocation().x);
					lastY = (MouseInfo.getPointerInfo().getLocation().y);
					//threeDGraphics.g.getGraphics().dispose();

				}

				//threeDGraphics.g.setTitle(""+(int)((double)1000000000/(double)(System.nanoTime()-start))+" fps");
				
				//start =System.nanoTime();
				delta-=1000000000/60;

				handler.g.setTitle("thread 1: "+runThread.fpstitle +" Main thread: "+handler.fps +" Graphics thread: "+jp.fps);
			}
		}
	}

	/*private shape[] sortObj(shape[] shapes) {
		// TODO Auto-generated method stub
		for(int i=0;i<shapes.length;i++){
			for(int u=0;u<shapes[i].faces.length-1;u++){
				for(int j=0;j<shapes[i].faces.length-1;j++){
					if(distance(shapes[i].faces[j].avg)<distance(shapes[i].faces[j+1].avg)){
						face temp = shapes[i].faces[j+1];
						shapes[i].faces[j+1] = shapes[i].faces[j];
						shapes[i].faces[j] = temp;
					}
				}
			}
			for(int j=0;j<shapes.length-1;j++){
				if(distance(shapes[j].xyz)<distance(shapes[j+1].xyz)){
					shape temp = shapes[j+1];
					shapes[j+1] = shapes[j];
					shapes[j] = temp;
				}
			}
		}
		return shapes;
	}*/

	static double distance(double[] avg){
		double dist = Math.sqrt(Math.pow(avg[0]-cameraNet.cameraPos[0], 2)+Math.pow(avg[1]-cameraNet.cameraPos[1], 2)+Math.pow(avg[2]-cameraNet.cameraPos[2], 2));

		return dist;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(raycast.collision(new double[]{-50,0,100})[0]);
		runScript.mousePressed(e);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {

		// TODO Auto-generated method stub
		runScript.keyPressed(e);
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}


}

