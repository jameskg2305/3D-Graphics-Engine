import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class face{
	edge[] edges;
	BufferedImage b;
	String id="";
	double []avg = new double[]{0,0,0};
	int[] pixelsColours;
	double[] uvMapEdges = null;
	
	public face(edge[] edges, File input,
			double[][] uvMap, int[] pixelsColours){
		try {
			if(input != null){
			this.b = ImageIO.read(input);
			System.out.println(b.getWidth()+" "+b.getHeight());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.edges = edges;
		
		if(pixelsColours==null && b!=null){
			System.out.println("null");
			this.pixelsColours = new int[b.getWidth()*b.getHeight()*4];
			for(int i=0;i<b.getWidth();i++){
				for(int j=0;j<b.getHeight();j++){
					this.pixelsColours[i*b.getHeight()*4+j*4+0] = b.getRGB(i, j)>>16;
				}
				for(int j=0;j<b.getHeight();j++){
					this.pixelsColours[i*b.getHeight()*4+j*4+1] = b.getRGB(i, j)>>8;
				}
				for(int j=0;j<b.getHeight();j++){
					this.pixelsColours[i*b.getHeight()*4+j*4+2] = b.getRGB(i, j)>>0;
				}
				for(int j=0;j<b.getHeight();j++){
					this.pixelsColours[i*b.getHeight()*4+j*4+3] = b.getRGB(i, j)>>24;
				}
			}
		}else{
			this.pixelsColours = pixelsColours;
			
		}
		
		
		uvMapEdges = new double[edges.length*2*2];
		double[][] uvMapCorners = uvMap;
		for(int i=0;i<uvMapCorners.length;i++){
				if(i != uvMapCorners.length-1){
					
					uvMapEdges[i*4+0] =   uvMapCorners[i][0];
					uvMapEdges[i*4+1] = uvMapCorners[i][1];
					uvMapEdges[i*4+2] = uvMapCorners[i+1][0];
					uvMapEdges[i*4+3] = uvMapCorners[i+1][1];
				}else{
					uvMapEdges[i*4+0] =   uvMapCorners[i][0];
					uvMapEdges[i*4+1] = uvMapCorners[i][1];
					uvMapEdges[i*4+2] = uvMapCorners[0][0];
					uvMapEdges[i*4+3] = uvMapCorners[0][1];
				}
		}
	}
	
	public face(edge[] edges,
			double[] uvMapEdges, int[] pixelsColours){
		this.edges = edges;
		this.uvMapEdges = uvMapEdges;
		this.pixelsColours = pixelsColours;
	}
}