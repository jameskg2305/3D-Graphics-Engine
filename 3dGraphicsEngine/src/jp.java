import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class jp extends Canvas{
	//System.setProperty("java.awt.headless", "false");
	static GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
	static GraphicsDevice device = env.getDefaultScreenDevice();
	static GraphicsConfiguration config = device.getDefaultConfiguration();
	static BufferedImage finalImage;
	static WritableRaster wRaster;
	static int [] pixelarray;
	static String fps = "";
	static int whiteCol=Color.WHITE.getRGB();
	static BufferStrategy bs;
	static Graphics2D g;
	int[] blankColour = new int[]{whiteCol>>16,whiteCol>>8,whiteCol>>0,whiteCol>>24};
	private static final long serialVersionUID = 1L;
		{
		try {
			//finalImage = config.createCompatibleImage(800, 800, Transparency.TRANSLUCENT);
			finalImage = ImageIO.read(new File("back.png"));
			finalImage = config.createCompatibleImage(handler.screenWid, handler.screenHei);
			finalImage = new BufferedImage(handler.screenWid, handler.screenHei, BufferedImage.TYPE_INT_RGB);
			wRaster = finalImage.getRaster();
			//imgData = ((DataBufferByte)finalImage.getRaster().getDataBuffer()).getData();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		JLabel counters = new JLabel();
		JLabel centre = new JLabel();
		JLabel centreA = new JLabel();
		JLabel[] images = new JLabel[handler.shapes.length];
		jp(){
			for(int i=0;i<images.length;i++){
				JLabel j = new JLabel("f");
				images[i]=j;
			}
			pixelarray = new int[wRaster.getWidth()*wRaster.getHeight()*4];
			//this.add(centre);
			//this.add(centreA);
			//multiThreadPaint.init();
			
			
		}
		
		public void paint(){
			long time = System.currentTimeMillis();
			
			bs = getBufferStrategy();
			if(bs == null){
				this.createBufferStrategy(3);
				return;
			}
			g = (Graphics2D) bs.getDrawGraphics();
			
			//boolean done=false;
			/*while(done==false){
				done=true;
				//System.out.println("in a loop in paint function");
				for(triangleThread f : handler.h){
					if(f.paintDone==false){
						//System.out.println(f.id+" has not finsished painting "+f.shapeNo+" "+handler.shapes.length);
						done=false;
					}
				}
			}*/
			finalImage.setData(wRaster);
			g.drawImage(finalImage,0,0,handler.imageSizew,handler.imageSizeh,null);
			//System.out.println("done");
			
			
			
			for(int p=0; p<wRaster.getWidth();p++){
				for(int i=0; i<wRaster.getHeight();i++){
					pixelarray[p*wRaster.getHeight()*4+(i*4)]  = blankColour[0];
					pixelarray[p*wRaster.getHeight()*4+(i*4)+1]= blankColour[1];
					pixelarray[p*wRaster.getHeight()*4+(i*4)+2]= blankColour[2];
					pixelarray[p*wRaster.getHeight()*4+(i*4)+3]= 999999;
				}
			}
			
			
			
			for(int i=0;i<handler.threadCount;i++){
				g.drawString(handler.h[i].fpscounter, 100, 100 + (i*30));
			}
			
			
			
			/*
			for(int i=0;i<4;i++){
			String s = cameraNet.net[i][0]+" "+cameraNet.net[i][1]+" "+cameraNet.net[i][2];
			g.drawString(s, 100, 100 + (i*30));
			}
			for(int i=0;i<2;i++){
				String s = cameraNet.e[i].sum+" "+cameraNet.e[i].xCo+" ";
				String f = "";
				String h = "";
				for(int j=0;j<3;j++){
					f+= cameraNet.e[i].start[j]+" ";
					h+= cameraNet.e[i].fin[j]+" ";
				}
				g.drawString(s, 100, 200 + (i*100));
				g.drawString(f, 400, 200 + (i*100));
				g.drawString(h, 400, 130 + (i*100));
				}
			g.drawString(
					threeDGraphics.centre.origAnglex+" "+
					threeDGraphics.centre.origAngley+" ", 300, 70);
			g.drawString(
					threeDGraphics.centre.xyz[0]+" "+
					threeDGraphics.centre.xyz[1]+" "+
					threeDGraphics.centre.xyz[2]+" ", 300, 100);
			g.drawString(
				cameraNet.centre[0]+" "+
				cameraNet.centre[1]+" "+
				cameraNet.centre[2], 400, 670);
			*/
			if(System.currentTimeMillis()-time!=0){
				fps = (1000/(System.currentTimeMillis()-time)+" fps");
			}
			g.dispose();
			bs.show();
			
		}
}
