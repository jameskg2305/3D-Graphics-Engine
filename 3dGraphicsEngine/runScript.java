import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class runScript{
	static double rad = 180/Math.PI;
	static boolean justLines = false;
	
	public static void script() {
		// TODO Auto-generated method stub
		quaternionRotate.rotate(initialise.hash.get("pyramid"), new double[]{0.707,.707,0}, .5);
		quaternionRotate.rotate(initialise.hash.get("pyramid1"), new double[]{-1,0,0}, .5);
	}

	
	
	
	public static void keyPressed(KeyEvent e) {
		
		// TODO Auto-generated method stub
		if(e.getKeyChar() == 'p'){
			runThread.paused=!runThread.paused;
		}
		if(e.getKeyChar() == 't'){
			handler.pixelGroup++;
			if(handler.pixelGroup==5){
				handler.pixelGroup=1;
			}
			System.out.println(handler.pixelGroup);
		}
		if(e.getKeyChar() == 'h'){
			justLines=!justLines;
		}
		if(e.getKeyCode() == KeyEvent.VK_W){
			cameraNet.dupcameraPos[2]+=Math.cos(cameraNet.origAnglex);
			cameraNet.dupcameraPos[0]+=Math.sin(cameraNet.origAnglex);
			//transformation.transform(handler.shapes[0], new double[]{0,0,-1});
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			cameraNet.dupcameraPos[2]-=Math.cos(cameraNet.origAnglex);
			cameraNet.dupcameraPos[0]-=Math.sin(cameraNet.origAnglex);
			//transformation.transform(handler.shapes[0], new double[]{0,0,1});
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			cameraNet.dupcameraPos[2]+=Math.sin(cameraNet.origAnglex);
			cameraNet.dupcameraPos[0]-=Math.cos(cameraNet.origAnglex);
			//transformation.transform(handler.shapes[0], new double[]{0,0,1});
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			cameraNet.dupcameraPos[2]-=Math.sin(cameraNet.origAnglex);
			cameraNet.dupcameraPos[0]+=Math.cos(cameraNet.origAnglex);
			//transformation.transform(handler.shapes[0], new double[]{0,0,1});
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			quaternionRotate.rotate(handler.shapes[0], new double[]{0,1,0}, 0.5);
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			quaternionRotate.rotate(handler.shapes[0], new double[]{0,1,0}, -.5);
		}
	}
	public static void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public static void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	public static void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("f");
	}
}
