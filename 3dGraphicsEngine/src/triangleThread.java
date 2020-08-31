import java.awt.image.WritableRaster;
import java.util.concurrent.Semaphore;

public class triangleThread implements Runnable{
	//face[] faces =null;
	int threads;
	int i;
	int j;
	int first;
	int times;
	int id = 0;
	int startc= 0;
	int finc= 0;
	static int height = jp.wRaster.getHeight();
	face[] faces = null;
	int shapeNo = 0;
	Semaphore ss = new Semaphore(1);
	String fpscounter = "";
	boolean paintDone = true;
	boolean done = true;
	static int[] pixelarray=null;
	WritableRaster wRaster = null;
	long time;
	static int screenHei=0;
	static int screenWid=0;
	boolean gpu = false;
	boolean timetaken=false;
	static Semaphore pc = new Semaphore(1);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Thread started");
		long delta = 0;
		long now = 0;
		long start = System.nanoTime();
		long s = System.nanoTime();
		while(runThread.running==true){
			//now = System.nanoTime();
			//delta+=(now-start);
			//start =System.nanoTime();
			
			//System.out.println(id+" "+shapeNo);
			
			if(gpu==false && id==0){
				//javacuda.main();
				gpu=true;
			}
			if(shapeNo<handler.shapes.length){
				if(shapeNo==0 && timetaken==false){
					s = System.nanoTime();
					time = System.currentTimeMillis();
					timetaken = true;
				}
				//System.out.println(runThread.done);
				if(done==false){
					//System.out.println(id+" "+shapeNo+" "+faces.length);
					// TRIANGLES NEED TO BE EQUAL IN SIZE
					if(faces!=null){
						
						//long timess = System.currentTimeMillis();
						for(int y=0;y<faces.length;y++){
							function(faces[y],  i,  j,  first, times);
							
						}
						
						//System.out.println(shapeNo+" "+id+" "+(System.currentTimeMillis()-timess));
						shapeNo++;
						done=true;
						faces=null;
					}
					
					
					
				}
				
				//System.out.println(id+" "+shapeNo);
				
			}
			else if(shapeNo>=handler.shapes.length && paintDone==false){
				timetaken=false;
				fpscounter = "thread "+id+" "+((1000000000)/(System.nanoTime()-s))+" fps";
				
				//System.out.println(id+" "+(id+1));
				
				for(int p=startc; p<finc;p++){
					for(int n=0; n<height;n++){
						
						wRaster.setPixel(p, n, new int[]{
								pixelarray[(p*height*4)+(n*4)],
								pixelarray[(p*height*4)+(n*4)+1],
								pixelarray[(p*height*4)+(n*4)+2],
								pixelarray[(p*height*4)+(n*4)+3]
						});

						//System.out.println((p*wRaster.getHeight())+(i*4));
					}
				}
				//System.out.println(System.currentTimeMillis()-times);
				paintDone=true;
				
				
				//System.out.println("painting done");
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void function(face faces, int i, int j, int first, int times) {
		// TODO Auto-generated method stub

		edgeTuple [] pair = new edgeTuple[faces.edges.length];
		int[] behind= new int[]{0};
		float lowX=999999;
		float highX=-999999;
		float lowY=999999;
		float highY=-999999;
		float xCen= (float) paintShape.calcPeripheral(true,faces.avg);
		float yCen= (float) paintShape.calcPeripheral(false,faces.avg);
		float [] y2d = new float[3];
		for(int k=0;k<faces.edges.length;k++){

			float [] x = new float[2];
			float [] y = new float[2];
			x[0]=(float)paintShape.calcPeripheral(faces.edges[k].c[0].xyz, handler.shapes[i].xyz, behind)[0];
			y[0]=(float)paintShape.calcPeripheral(faces.edges[k].c[0].xyz, handler.shapes[i].xyz)[1];
			x[1]=(float)paintShape.calcPeripheral(faces.edges[k].c[1].xyz, handler.shapes[i].xyz)[0];
			y[1]=(float)paintShape.calcPeripheral(faces.edges[k].c[1].xyz, handler.shapes[i].xyz)[1];
			//x2d[k] = (float)paintShape.calcPeripheral(faces.edges[k].c[0].xyz, handler.shapes[i].xyz)[0];
		    y2d[k]=y[0];
			for(int b =0;b<x.length; b++){
				if(x[b]<lowX){
					lowX= x[b]-1;
				}
				if(x[b]>highX){
					highX= x[b]+1;
				}
			}
			for(int b =0;b<y.length; b++){
				if(y[b]<lowY){
					lowY= y[b]-1;
				}
				if(y[b]>highY){
					highY= y[b]+1;
				}
			}
			float m;
			if(y[1]-y[0]==0){
				m=0;
			}
			else if(x[1]-x[0]!=0){
				m = (float)(y[1]-y[0])/(float)(x[1]-x[0]);
			}else{
				if(xCen<=x[1]){
					m=(float) -screenHei;
				}else{
					m=(float) screenHei;
				}

			}
			float yInter = (float) (y[1] - (x[1]*m));
			//System.out.println(k+" "+m+" "+ yInter+" "+xCen+" "+yCen);
			pair[k] = new edgeTuple(m,yInter);
		}

		if(behind[0]<3
				&& !(lowX>screenWid && highX>screenWid)
				&& !(lowX<0 && highX<0)
				&& !(lowY>screenHei && highY>screenHei)
				&& !(lowY<0 && highY<0)){

			if(runScript.justLines == true){
				handler.j.getGraphics().drawPolygon(

						new int[]{
								(int) ((int)paintShape.calcPeripheral(faces.edges[0].c[0].xyz, handler.shapes[i].xyz)[0]*((double)handler.imageSizew/(double)handler.screenWid)),
								(int) ((int)paintShape.calcPeripheral(faces.edges[1].c[0].xyz, handler.shapes[i].xyz)[0]*((double)handler.imageSizew/(double)handler.screenWid)),
								(int) ((int)paintShape.calcPeripheral(faces.edges[2].c[0].xyz, handler.shapes[i].xyz)[0]*((double)handler.imageSizew/(double)handler.screenWid))}, 

						new int[]{
								(int) ((int)paintShape.calcPeripheral(faces.edges[0].c[0].xyz, handler.shapes[i].xyz)[1]*((double)handler.imageSizeh/(double)handler.screenHei)),
								(int) ((int)paintShape.calcPeripheral(faces.edges[1].c[0].xyz, handler.shapes[i].xyz)[1]*((double)handler.imageSizeh/(double)handler.screenHei)),
								(int) ((int)paintShape.calcPeripheral(faces.edges[2].c[0].xyz, handler.shapes[i].xyz)[1]*((double)handler.imageSizeh/(double)handler.screenHei))}, 

						3);
				String s = "";
				for(int h=0;h<3;h++){
					s+=String.format("%.2f", faces.avg[h])+", ";
				}
				
				if(times<3){
				handler.j.getGraphics().drawString(
						s,
						(int) ((int)paintShape.calcPeripheral(true, faces.avg)*((double)handler.imageSizew/(double)screenWid)),
						(int) ((int)paintShape.calcPeripheral(false, faces.avg)*((double)handler.imageSizeh/(double)screenHei)));
				}
			}else if((int)(highY-lowY)<9999 && lowY<highY){

				//edgeRange = new float[(int)(highY)-(int)(lowY)][6];
				//for(int h=(int) lowY; h<(int)highY; h++){

					//if((int) (h-lowY)*6>=12000){
					//break;
					//}//13 15
					
					//System.out.println(((int) (h-lowY))+" "+edgeRange[((int) (h-lowY)*6)+0]+" "+edgeRange[((int) (h-lowY)*6)+1]);
				//}
				
				for(int h=0; h<(int)(highY)-(int)(lowY); h++){
					float lowx = 99999;
					float highx = -99999;
					float lowxedge = 0;
					float highxedge = 0;
					float lowxuv = 0;
					float highxuv = 0;
					for(int m=0;m<pair.length;m++){

						double x = ((h+(int)lowY)-pair[m].sum)/pair[m].xCo;
						boolean in=true;
						for(int n=0;n<pair.length;n++){
							if(((yCen-pair[n].sum)/pair[n].xCo)-xCen>0){
								if(x-(((h+(int)lowY)-pair[n].sum)/pair[n].xCo)>0){
									in=false;
								}

							}else if(((yCen-pair[n].sum)/pair[n].xCo)-xCen<0){
								if(x-(((h+(int)lowY)-pair[n].sum)/pair[n].xCo)<0){
									in=false;
								}
							}
						}

						if(x<lowx && in==true){
							lowx = (float) x;
							lowxedge = m;
							if(m!=2){
							lowxuv = (float) ((
									y2d[m] - (h+(int)lowY))/
									(y2d[m] - 
											y2d[m+1]));
							}else{
								lowxuv = (float) ((
										y2d[m] - (h+(int)lowY))/
										(y2d[m] - 
												y2d[0]));
							}
						}
						if(x>highx && in==true){
							highx = (float) x;
							highxedge = m;
							if(m!=2){
								highxuv = (float) ((
										y2d[m] - (h+(int)lowY))/
										(y2d[m] - 
												y2d[m+1]));
								}else{
									highxuv = (float) ((
											y2d[m] - (h+(int)lowY))/
											(y2d[m] - 
													y2d[0]));
								}
						}

					}
					
					
					float[] xy0 = new float[2];
					float[] xy1 = new float[2];
					//if(h%handler.pixelGroup==0 || h==0){
					 xy0 = new float[]{
							(float) (
									faces.uvMapEdges[((int)(lowxedge))*4+2]
											-faces.uvMapEdges[((int)(lowxedge))*4]
									)
							,
							(float) (
									faces.uvMapEdges[((int)(lowxedge))*4+3]
											-faces.uvMapEdges[((int)(lowxedge))*4+1]
									)

					};
					 xy1 = new float[]{
							(float) (
									faces.uvMapEdges[((int)(highxedge))*4+2]
											-faces.uvMapEdges[((int)(highxedge))*4]
									)

							,
							(float) (
									faces.uvMapEdges[((int)(highxedge))*4+3]
											-faces.uvMapEdges[((int)(highxedge))*4+1]
									)
					};

					for(int y=0;y<2;y++){
						xy0[y] *= lowxuv;
						xy1[y] *= highxuv;
						xy0[y] += faces.uvMapEdges[((int) lowxedge)*4+y];
						xy1[y] += faces.uvMapEdges[((int) highxedge)*4+y];

					}
					//}
					float xc = 0;
					float yc = 0;
					for(int g= (int) lowx; g<highx; g++){
						if(highx-lowx>999){
							break;
						}
						if(g>0 &&g<screenWid-1 &&h+lowY>0 &&h+lowY<screenHei-1 && runThread.distance(faces.avg)< pixelarray[((int)g*wRaster.getHeight()*4)+(int)(h+lowY)*4+3]){
						xc = xy1[0]-xy0[0];
						//System.out.println(xc);
						xc *= ((float)(g- lowx)/ (float)(highx-lowx));
						xc+=xy0[0];
						if(xc<0){
							xc=0;
						}
						if(xc>1){
							xc=1;
						}
						if(faces.b!=null){
							xc *=(faces.b.getWidth()-1);
						}else{
							xc *=(handler.shapes[i].faces[j].b.getWidth()-1);
						}
						yc = xy1[1]-xy0[1];

						yc *= ((float)(g- lowx)/ (float)(highx-lowx));
						yc+=xy0[1];

						yc = 1-yc;
						if(yc<0){
							yc=0;
						}
						if(yc>1){
							yc=1;
						}
						yc *=(handler.shapes[i].faces[j].b.getHeight()-1);
						//}
						//if(g>0 &&g<handler.screenWid-1 &&h+lowY>0 &&h+lowY<handler.screenHei-1){
							//if(runThread.distance(faces.avg)< jp.pixelarray[((int)g*jp.wRaster.getHeight()*4)+(int)(h+lowY)*4+3]){
								
							pixelarray[((int)g*wRaster.getHeight()*4)+(int)(h+lowY)*4+0] = handler.shapes[i].faces[j].pixelsColours[((int)xc)*handler.shapes[i].faces[j].b.getHeight()*4 +((int)yc)*4+0];
							pixelarray[((int)g*wRaster.getHeight()*4)+(int)(h+lowY)*4+1] = handler.shapes[i].faces[j].pixelsColours[((int)xc)*handler.shapes[i].faces[j].b.getHeight()*4 +((int)yc)*4+1];
							pixelarray[((int)g*wRaster.getHeight()*4)+(int)(h+lowY)*4+2] = handler.shapes[i].faces[j].pixelsColours[((int)xc)*handler.shapes[i].faces[j].b.getHeight()*4 +((int)yc)*4+2];
							pixelarray[((int)g*wRaster.getHeight()*4)+(int)(h+lowY)*4+3] = (int) runThread.distance(faces.avg);
							//}
						//}
					}
					}
				}
			}
		}

	}

}
