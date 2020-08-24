import java.util.concurrent.Semaphore;

public class triangleThread implements Runnable{
	face[] faces =null;
	int threads;
	int i;
	int j;
	int first;
	int times;
	int shapeNo = 0;
	Semaphore ss = new Semaphore(1);
	int id = 0;
	String fpscounter = "";
	boolean paintDone = true;
	boolean done = true;
	long time;
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
			now = System.nanoTime();
			delta+=(now-start);
			start =System.nanoTime();
			
			//System.out.println(id+" "+shapeNo);
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(gpu==false){
				javacuda.main();
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
					
					if(faces!=null){
						long timess = System.currentTimeMillis();
						for(int y=0;y<faces.length;y++){
							function(faces[y],  i,  j,  first, times);
							
						}
						//System.out.println(shapeNo+" "+id+" "+(System.currentTimeMillis()-timess));
						shapeNo++;
						faces=null;
						if(shapeNo==4){
							//System.out.println(System.currentTimeMillis()-time);
						}
					}
					
					
					done=true;
				}
				
				//System.out.println(id+" "+shapeNo);
				
			}
			else if(shapeNo>=handler.shapes.length && paintDone==false){
				if((System.nanoTime()-s)!=0){
					timetaken=false;
					fpscounter = "thread "+id+" "+((1000000000)/(System.nanoTime()-s))+" fps";
				}
				//System.out.println(id+" "+(id+1));
				
				for(int p=id*(jp.wRaster.getWidth()/handler.threadCount); p<(id+1)*(jp.wRaster.getWidth()/handler.threadCount);p++){
					for(int n=0; n<jp.wRaster.getHeight();n++){
						
						jp.wRaster.setPixel(p, n, new int[]{
								jp.pixelarray[(p*jp.wRaster.getHeight()*4)+(n*4)],
								jp.pixelarray[(p*jp.wRaster.getHeight()*4)+(n*4)+1],
								jp.pixelarray[(p*jp.wRaster.getHeight()*4)+(n*4)+2],
								jp.pixelarray[(p*jp.wRaster.getHeight()*4)+(n*4)+3]
						});

						//System.out.println((p*wRaster.getHeight())+(i*4));
					}
				}
				//System.out.println(System.currentTimeMillis()-times);
				paintDone=true;
				
				
				//System.out.println("painting done");
			}
			
			
			
			while(delta >= 1000000000/60){

				delta-=1000000000/60;
			}
		}
	}
	private void function(face faces, int i, int j, int first, int times) {
		// TODO Auto-generated method stub

		edgeTuple [] pair = new edgeTuple[faces.edges.length];
		int[] behind= new int[]{0};
		float[][] edgeRange;
		float lowX=999999;
		float highX=-999999;
		float lowY=999999;
		float highY=-999999;
		float xCen= (float) paintShape.calcPeripheral(true,faces.avg);
		float yCen= (float) paintShape.calcPeripheral(false,faces.avg);

		for(int k=0;k<faces.edges.length;k++){

			float [] x = new float[2];
			float [] y = new float[2];
			x[0]=(float)paintShape.calcPeripheral(faces.edges[k].c[0].xyz, handler.shapes[i].xyz, behind)[0];
			y[0]=(float)paintShape.calcPeripheral(faces.edges[k].c[0].xyz, handler.shapes[i].xyz)[1];
			x[1]=(float)paintShape.calcPeripheral(faces.edges[k].c[1].xyz, handler.shapes[i].xyz)[0];
			y[1]=(float)paintShape.calcPeripheral(faces.edges[k].c[1].xyz, handler.shapes[i].xyz)[1];

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
					m=(float) -handler.screenHei;
				}else{
					m=(float) handler.screenHei;
				}

			}
			float yInter = (float) (y[1] - (x[1]*m));
			//System.out.println(k+" "+m+" "+ yInter+" "+xCen+" "+yCen);
			pair[k] = new edgeTuple(m,yInter);
		}

		if(behind[0]<3
				&& !(lowX>handler.screenWid && highX>handler.screenWid)
				&& !(lowX<0 && highX<0)
				&& !(lowY>handler.screenHei && highY>handler.screenHei)
				&& !(lowY<0 && highY<0)){

			if(runScript.justLines == true){
				/*handler.j.getGraphics().drawPolygon(

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
						(int) ((int)paintShape.calcPeripheral(true, faces.avg)*((double)handler.imageSizew/(double)handler.screenWid)),
						(int) ((int)paintShape.calcPeripheral(false, faces.avg)*((double)handler.imageSizeh/(double)handler.screenHei)));
				}*/
			}else if((int)(highY-lowY)<9999 && lowY<highY){

				//int outline = 1;
				edgeRange = new float[(int)(highY)-(int)(lowY)][6];
				if(edgeRange.length>2000){
					return;
				}
				for(int h=(int) lowY; h<(int)highY; h++){


					//if((int) (h-lowY)*6>=12000){
					//break;
					//}
					double[] xLowHigh = new double[]{999999,-999999};
					for(int m=0;m<pair.length;m++){

						double x = (h-pair[m].sum)/pair[m].xCo;
						boolean in=true;
						for(int n=0;n<pair.length;n++){
							if(((yCen-pair[n].sum)/pair[n].xCo)-xCen>0){
								if(x-((h-pair[n].sum)/pair[n].xCo)>0){
									in=false;
								}

							}else if(((yCen-pair[n].sum)/pair[n].xCo)-xCen<0){
								if(x-((h-pair[n].sum)/pair[n].xCo)<0){
									in=false;
								}
							}
						}

						if(x<xLowHigh[0] && in==true){
							xLowHigh[0]=x;
							edgeRange[(int) (h-lowY)][0] = (float) x;
							edgeRange[(int) (h-lowY)][2] = m;
							edgeRange[(int) (h-lowY)][4] = (float) ((
									paintShape.calcPeripheral(faces.edges[m].c[0].xyz, handler.shapes[i].xyz)[1] - h)/
									(paintShape.calcPeripheral(faces.edges[m].c[0].xyz, handler.shapes[i].xyz)[1] - 
											paintShape.calcPeripheral(faces.edges[m].c[1].xyz, handler.shapes[i].xyz)[1]));
						}
						if(x>xLowHigh[1] && in==true){
							xLowHigh[1]=x;
							edgeRange[(int) (h-lowY)][1] = (float) x;
							edgeRange[(int) (h-lowY)][3] = m;
							edgeRange[(int) (h-lowY)][5] = (float) ((
									paintShape.calcPeripheral(faces.edges[m].c[0].xyz, handler.shapes[i].xyz)[1] - h)/
									(paintShape.calcPeripheral(faces.edges[m].c[0].xyz, handler.shapes[i].xyz)[1] - 
											paintShape.calcPeripheral(faces.edges[m].c[1].xyz, handler.shapes[i].xyz)[1]));
						}

					}
					//System.out.println(((int) (h-lowY))+" "+edgeRange[((int) (h-lowY)*6)+0]+" "+edgeRange[((int) (h-lowY)*6)+1]);
				}
				
				for(int h=0; h<edgeRange.length; h++){
					float[] xy0 = new float[2];
					float[] xy1 = new float[2];
					if(h%handler.pixelGroup==0 || h==0){
					 xy0 = new float[]{
							(float) (
									faces.uvMapEdges[((int)edgeRange[h][2])][1][0]
											-faces.uvMapEdges[((int)edgeRange[h][2])][0][0]
									)
							,
							(float) (
									faces.uvMapEdges[((int)edgeRange[h][2])][1][1]
											-faces.uvMapEdges[((int)edgeRange[h][2])][0][1]
									)

					};
					 xy1 = new float[]{
							(float) (
									faces.uvMapEdges[((int)edgeRange[h][3])][1][0]
											- faces.uvMapEdges[((int)edgeRange[h][3])][0][0])

							,
							(float) (
									faces.uvMapEdges[((int)edgeRange[h][3])][1][1]
											-faces.uvMapEdges[((int)edgeRange[h][3])][0][1])

					};

					for(int y=0;y<2;y++){
						xy0[y] *= edgeRange[h][4];
						xy1[y] *= edgeRange[h][5];
						xy0[y] += faces.uvMapEdges[((int) edgeRange[h][2])][0][y];
						xy1[y] += faces.uvMapEdges[((int) edgeRange[h][3])][0][y];

					}
					}
					float xc = 0;
					float yc = 0;
					for(int g= (int) edgeRange[h][0]; g<edgeRange[h][1]; g++){
						if(edgeRange[h][1]-(int) edgeRange[h][0]>999){
							break;
						}
						//int hPerc = (int)(100*((double)(h-lowY)/(double)(highY-lowY)));
						//if(g%handler.pixelGroup==0 || g==(int) edgeRange[h][0]){
						if(g>0 &&g<handler.screenWid-1 &&h+lowY>0 &&h+lowY<handler.screenHei-1 && runThread.distance(faces.avg)< jp.pixelarray[((int)g*jp.wRaster.getHeight()*4)+(int)(h+lowY)*4+3]){
						xc = xy1[0]-xy0[0];
						//System.out.println(xc);
						xc *= ((float)(g- edgeRange[h][0])/ (float)(edgeRange[h][1]- edgeRange[h][0]));
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

						yc *= ((float)(g- edgeRange[h][0])/ (float)(edgeRange[h][1]- edgeRange[h][0]));
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
								
							jp.pixelarray[((int)g*jp.wRaster.getHeight()*4)+(int)(h+lowY)*4+0] = handler.shapes[i].faces[j].pixelsColours[((int)xc)*handler.shapes[i].faces[j].b.getHeight()*4 +((int)yc)*4+0];
							jp.pixelarray[((int)g*jp.wRaster.getHeight()*4)+(int)(h+lowY)*4+1] = handler.shapes[i].faces[j].pixelsColours[((int)xc)*handler.shapes[i].faces[j].b.getHeight()*4 +((int)yc)*4+1];
							jp.pixelarray[((int)g*jp.wRaster.getHeight()*4)+(int)(h+lowY)*4+2] = handler.shapes[i].faces[j].pixelsColours[((int)xc)*handler.shapes[i].faces[j].b.getHeight()*4 +((int)yc)*4+2];
							//jp.pixelarray[((int)g*jp.wRaster.getHeight()*4)+(int)(h+lowY)*4+3] = handler.shapes[i].faces[j].pixelsColours[((int)xc)*handler.shapes[i].faces[j].b.getHeight()*4 +((int)yc)*4+3];
							jp.pixelarray[((int)g*jp.wRaster.getHeight()*4)+(int)(h+lowY)*4+3] = (int) runThread.distance(faces.avg);
							//}
						//}
					}
					}
				}
			}
		}

	}

}
