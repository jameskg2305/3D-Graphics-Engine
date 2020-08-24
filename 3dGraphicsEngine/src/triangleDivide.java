

public class triangleDivide {
	//static ForkJoinPool fork = new ForkJoinPool(Runtime.getRuntime().availableProcessors()*2);
	//static recursiveDraw r;
	static face[] allFaces;
	static int counter = 0;
	static int used=1;
	static int actualcounter = 0;
	public static void init(face altFace, int i, int j, int first, int times, int low, int high){
		
		counter=0;
		actualcounter=0;
		allFaces = new face[10000];
		//long time = System.currentTimeMillis();
		recursiveDraw(altFace, i, j, first, times,low, high);
		
		actualcounter = counter;
		if(counter<handler.threadCount){
			counter=handler.threadCount;
		}
		
		assignToThread.assign(allFaces,i , j, low, high, handler.threadCount, 0,counter, actualcounter);
		//runThread.done=false;
		
		
	}

	private static void recursiveDraw(face faces, int i, int j, int first, int times, int low, int high) {
		// TODO Auto-generated method stub
		//
		
		//float xCen= (float) paintShape.calcPeripheral(true,faces.avg);
		
		face altFace = new face(faces.edges, faces.uvMapEdges, null);
		averageAngle.calcAverageAngle(handler.shapes[i].xyz,altFace, faces.edges);
		int[] behind = new int[]{0};
		//int outbounds=0;
		//int[] l = new int[altFace.edges.length];
		//edgeTuple [] pair = new edgeTuple[faces.edges.length];
		
		float lowX=999999;
		float highX=-999999;
		float lowY=999999;
		float highY=-999999;
		
		for(int k=0;k<faces.edges.length;k++){

			float [] x = new float[2];
			float [] y = new float[2];
			x[0]=(float)paintShape.calcPeripheral(faces.edges[k].c[0].xyz, handler.shapes[i].xyz, behind)[0];
			y[0]=(float)paintShape.calcPeripheral(faces.edges[k].c[0].xyz, handler.shapes[i].xyz)[1];
			x[1]=(float)paintShape.calcPeripheral(faces.edges[k].c[1].xyz, handler.shapes[i].xyz)[0];
			y[1]=(float)paintShape.calcPeripheral(faces.edges[k].c[1].xyz, handler.shapes[i].xyz)[1];

			for(int b =0;b<x.length; b++){
				if(x[b]<lowX){
					lowX= x[b];
				}
				if(x[b]>highX){
					highX= x[b];
				}
			}
			for(int b =0;b<y.length; b++){
				if(y[b]<lowY){
					lowY= y[b];
				}
				if(y[b]>highY){
					highY= y[b];
				}
			}
		}
		
		
		
		if(((highX-lowX>handler.screenWid/8*4 || highY-lowY>handler.screenHei/8*4)
				&& !(lowX>handler.screenWid && highX>handler.screenWid)
				&& !(lowX<0 && highX<0)
				&& !(lowY>handler.screenHei && highY>handler.screenHei)
				&& !(lowY<0 && highY<0)) || times<1){
			//behind[0] = 0;
			
			edge[] edges1;
			edge[] edges2;

			double uvMap0[][] = null;
			double uvMap1[][] = null;
			if(altFace.edges.length>3){

				uvMap0 = new double[][]{{altFace.uvMapEdges[1][0][0],altFace.uvMapEdges[1][0][1]},{altFace.uvMapEdges[2][0][0],altFace.uvMapEdges[2][0][1]},{altFace.uvMapEdges[0][0][0],altFace.uvMapEdges[0][0][1]}};

				edges1 = new edge[]{
						new edge(new Corner[]{
								new Corner(altFace.edges[1].c[0].xyz),
								new Corner(altFace.edges[2].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(altFace.edges[2].c[0].xyz),
								new Corner(altFace.edges[0].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(altFace.edges[0].c[0].xyz),
								new Corner(altFace.edges[1].c[0].xyz)
						})
				};
				uvMap1 = new double[altFace.edges.length-1][2];
				edges2 = new edge[altFace.edges.length-1];
				for(int u = 0;u<edges2.length;u++){
					if(u==1){
						edges2[1] = new edge(new Corner[]{
								new Corner(altFace.edges[2].c[0].xyz),
								new Corner(altFace.edges[0].c[0].xyz)
						});
						uvMap1[u] = new double[]{altFace.uvMapEdges[2][0][0], altFace.uvMapEdges[2][0][1]};
					}
					else if(u<2){
						edges2[u] = new edge(new Corner[]{
								new Corner(altFace.edges[3-u].c[0].xyz),
								new Corner(altFace.edges[3-u-1].c[0].xyz)
						});
						uvMap1[u] = new double[]{altFace.uvMapEdges[3-u][0][0], altFace.uvMapEdges[3-u][0][1]};
					}else{
						edges2[u] = new edge(new Corner[]{
								new Corner(altFace.edges[0].c[0].xyz),
								new Corner(altFace.edges[edges2.length-1].c[0].xyz)
						});
						uvMap1[u] = new double[]{altFace.uvMapEdges[0][0][0], altFace.uvMapEdges[0][0][1]};
					}
				}
			}else{

				//System.out.println(times+" "+times+" "+times+" "+times+" ");
				double[] middleCo = new double[]{
						(altFace.edges[first].c[0].xyz[0]+altFace.edges[first+1].c[0].xyz[0])/(double)2.0,
						(altFace.edges[first].c[0].xyz[1]+altFace.edges[first+1].c[0].xyz[1])/(double)2.0,
						(altFace.edges[first].c[0].xyz[2]+altFace.edges[first+1].c[0].xyz[2])/(double)2.0
				};


				//System.out.println("b " + xpercentage+" "+ypercentage);

				edges1 = new edge[]{
						new edge(new Corner[]{
								new Corner(middleCo),
								new Corner(altFace.edges[0].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(altFace.edges[0].c[0].xyz),
								new Corner(altFace.edges[2].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(altFace.edges[2].c[0].xyz),
								new Corner(middleCo)
						})
				};

				double[] uvmiddleCo = new double[]{
						(altFace.uvMapEdges[first+1][0][0]+altFace.uvMapEdges[first][0][0])/2,
						(altFace.uvMapEdges[first+1][0][1]+altFace.uvMapEdges[first][0][1])/2
				};

				edges2 = new edge[]{
						new edge(new Corner[]{
								new Corner(middleCo),
								new Corner(altFace.edges[1-first].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(altFace.edges[1-first].c[0].xyz),
								new Corner(altFace.edges[2-first].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(altFace.edges[2-first].c[0].xyz),
								new Corner(middleCo)
						})
				};
				uvMap0 = new double[][]{{uvmiddleCo[0], uvmiddleCo[1]},{altFace.uvMapEdges[0][0][0],altFace.uvMapEdges[0][0][1]},{altFace.uvMapEdges[2][0][0],altFace.uvMapEdges[2][0][1]}};
				uvMap1 = new double[][]{{uvmiddleCo[0], uvmiddleCo[1]},{altFace.uvMapEdges[1-first][0][0],altFace.uvMapEdges[1-first][0][1]},{altFace.uvMapEdges[2-first][0][0],altFace.uvMapEdges[2-first][0][1]}};
				//System.out.println("**********");


			}
			face[] f = new face[2];
			
			f[0] = new face(edges1, null, uvMap0,null);
			f[1] = new face(edges2, null, uvMap1,null);
			//System.out.println(i+" "+j);
			boolean[] outBounds = new boolean[]{false,false};
			//if(threads==8){
			for(int f0 = 0;f0<f.length;f0++){
				behind[0] = 0;
				int out=0;
				for(int k=0;k<f[f0].edges.length;k++){

					float [] x = new float[2];
					float [] y = new float[2];
					x[0]=(float)paintShape.calcPeripheral(f[f0].edges[k].c[0].xyz, handler.shapes[i].xyz, behind)[0];
					y[0]=(float)paintShape.calcPeripheral(f[f0].edges[k].c[0].xyz, handler.shapes[i].xyz)[1];
					x[1]=(float)paintShape.calcPeripheral(f[f0].edges[k].c[1].xyz, handler.shapes[i].xyz)[0];
					y[1]=(float)paintShape.calcPeripheral(f[f0].edges[k].c[1].xyz, handler.shapes[i].xyz)[1];

					if((x[0]<0 && x[1]<0) || (y[0]<0 && y[1]<0)
							|| (x[0]>handler.screenWid && x[1]>handler.screenWid)
							|| (y[0]>handler.screenHei && y[1]>handler.screenHei)
							
							){
						out++;
					}
				}
				if((out==f[f0].edges.length || behind[0]==f[f0].edges.length)){
					outBounds[f0] = true;
				}
			}
			//}
			//f[0].b = altFace.b;
			//f[1].b = altFace.b;

			averageAngle.calcAverageAngle(handler.shapes[i].xyz,f[0], edges1);
			averageAngle.calcAverageAngle(handler.shapes[i].xyz,f[1], edges2);
			if((outBounds[0]==false && outBounds[1]==false)||used<2){
				used*=2;
				recursiveDraw(f[0], i, j, 1, times+1, low, (low+high)/2);
				recursiveDraw(f[1], i, j, 1, times+1,(low+high)/2 + 1, high);
			}else if(outBounds[0]==true && outBounds[1]==true && times==0){
				//do nothing
				for(triangleThread t : handler.h){
					t.shapeNo++;
				}
				//return;
			}
			else if(outBounds[0]==true){
				recursiveDraw(f[1], i, j, 1, times+1,low, high);
			}else if(outBounds[1]==true){
				recursiveDraw(f[0], i, j, 1, times+1, low, high);
			}else if(outBounds[0]==true && outBounds[1]==true){
				//threads*=2;
			}
			

		}else{
			allFaces[counter] = altFace;
			counter++;
		}

	}

}
