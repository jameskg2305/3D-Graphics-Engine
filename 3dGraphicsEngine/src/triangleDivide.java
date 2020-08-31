

public class triangleDivide {
	//static ForkJoinPool fork = new ForkJoinPool(Runtime.getRuntime().availableProcessors()*2);
	//static recursiveDraw r;
	static face[] allFaces;
	static triangleThread[] h = null;
	static int counter = 0;
	static shape[] shapes = null;
	static int used=1;
	static int screenHei=0;
	static int screenWid=0;
	static boolean out = false;
	static int actualcounter = 0;
	static int threadCount=handler.threadCount;
	public static void init(int i, int j, int height, int times, int low, int high){
		
		counter=0;
		actualcounter=0;
		allFaces = new face[10000];
		//long time = System.currentTimeMillis();
		recursiveDraw(shapes[i].faces[j], i, j, height, times,low, high, 0);
		//System.out.println(System.currentTimeMillis()-time);
		actualcounter = counter;
		if(counter<threadCount){
			counter=threadCount;
		}
		if(out==false){
		assignToThread.assign(allFaces, i , j, low, high, threadCount, 0,counter, actualcounter);
		}else{
			//DO NOTHING
			System.out.println("yes");
			out=false;
		}
		//runThread.done=false;
		//System.out.println(System.currentTimeMillis() - time);
		
	}

	private static void recursiveDraw(face altFace, int i, int j, int height, int times, int low, int high, int behindCamera) {
		// TODO Auto-generated method stub
		//
		
		//float xCen= (float) paintShape.calcPeripheral(true,faces.avg);
		
		//face altFace = new face(faces.edges, faces.uvMapEdges, null);
		//averageAngle.calcAverageAngle(handler.shapes[i].xyz,altFace, faces.edges);
		int[] behind = new int[]{0};
		//int outbounds=0;
		//int[] l = new int[altFace.edges.length];
		//edgeTuple [] pair = new edgeTuple[faces.edges.length];
		
		float lowX=999999;
		float highX=-999999;
		float lowY=999999;
		float highY=-999999;
		for(int k=0;k<altFace.edges.length;k++){

			float [] x = new float[2];
			float [] y = new float[2];
			
			double[] zero = paintShape.calcPeripheral(altFace.edges[k].c[0].xyz, shapes[i].xyz, behind);
			double[] one = paintShape.calcPeripheral(altFace.edges[k].c[1].xyz, shapes[i].xyz);
			x[0]=(float) zero[0];
			y[0]=(float) zero[1];
			x[1]=(float) one[0];
			y[1]=(float) one[1];
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
		
		
		
		if(2==2 && (((
				(highX-lowX>screenWid/8*4 || highY-lowY>screenHei/8*4)
				&& !(lowX>screenWid && highX>screenWid)
				&& !(lowX<0 && highX<0)
				&& !(lowY>screenHei && highY>screenHei)
				&& !(lowY<0 && highY<0)) || (times<2 && (lowX<0 || highX>screenWid || lowY<0 || highY>screenHei || behind[0]>0)))||times<3 
				 )&& times<30){
			
			//behind[0] = 0;
			
			edge[][] edges = new edge[2][];
			//edges[0] = null;
			//edges[1] = null;
			double uvMap0[][] = null;
			double uvMap1[][] = null;
			if(altFace.edges.length>3){

				uvMap0 = new double[][]{{altFace.uvMapEdges[1*4],altFace.uvMapEdges[1*4+1]},{altFace.uvMapEdges[2*4],altFace.uvMapEdges[2*4+1]},{altFace.uvMapEdges[0],altFace.uvMapEdges[1]}};

				edges[0] = new edge[]{
						new edge(new Corner[]{
								altFace.edges[1].c[0],
								altFace.edges[2].c[0]
						}),
						new edge(new Corner[]{
								altFace.edges[2].c[0],
								altFace.edges[0].c[0]
						}),
						new edge(new Corner[]{
								altFace.edges[0].c[0],
								altFace.edges[1].c[0]
						})
				};
				uvMap1 = new double[altFace.edges.length-1][2];
				edges[1] = new edge[altFace.edges.length-1];
				for(int u = 0;u<edges[1].length;u++){
					if(u==1){
						edges[1][1] = new edge(new Corner[]{
								altFace.edges[2].c[0],
								altFace.edges[0].c[0]
						});
						uvMap1[u] = new double[]{altFace.uvMapEdges[2*4], altFace.uvMapEdges[2*4+1]};
					}
					else if(u<2){
						edges[1][u] = new edge(new Corner[]{
								altFace.edges[3-u].c[0],
								altFace.edges[3-u-1].c[0]
						});
						uvMap1[u] = new double[]{altFace.uvMapEdges[(3-u)*4], altFace.uvMapEdges[(3-u)*4+1]};
					}else{
						edges[1][u] = new edge(new Corner[]{
								altFace.edges[0].c[0],
								altFace.edges[edges[1].length-1].c[0]
						});
						uvMap1[u] = new double[]{altFace.uvMapEdges[0], altFace.uvMapEdges[1]};
					}
				}
			}else{

				//System.out.println(times+" "+times+" "+times+" "+times+" ");
				double[] middlesCo = new double[]{
						(altFace.edges[height].c[0].xyz[0]+altFace.edges[height+1].c[0].xyz[0])/2,
						(altFace.edges[height].c[0].xyz[1]+altFace.edges[height+1].c[0].xyz[1])/2,
						(altFace.edges[height].c[0].xyz[2]+altFace.edges[height+1].c[0].xyz[2])/2
				};

				Corner middleCo = new Corner(middlesCo);
				//System.out.println("b " + xpercentage+" "+ypercentage);

				edges[0] = new edge[]{
						new edge(new Corner[]{
								middleCo,
								altFace.edges[0].c[0]
						}),
						new edge(new Corner[]{
								altFace.edges[0].c[0],
								altFace.edges[2].c[0]
						}),
						new edge(new Corner[]{
								altFace.edges[2].c[0],
								middleCo
						})
				};

				double[] uvmiddleCo = new double[]{
						(altFace.uvMapEdges[(height+1)*4]+altFace.uvMapEdges[height*4])/2,
						(altFace.uvMapEdges[(height+1)*4+1]+altFace.uvMapEdges[height*4+1])/2
				};

				edges[1] = new edge[]{
						new edge(new Corner[]{
								middleCo,
								altFace.edges[1-height].c[0]
						}),
						new edge(new Corner[]{
								altFace.edges[1-height].c[0],
								altFace.edges[2-height].c[0]
						}),
						new edge(new Corner[]{
								altFace.edges[2-height].c[0],
								middleCo
						})
				};
				uvMap0 = new double[][]{{uvmiddleCo[0], uvmiddleCo[1]},{altFace.uvMapEdges[0],altFace.uvMapEdges[1]},{altFace.uvMapEdges[2*4],altFace.uvMapEdges[2*4+1]}};
				uvMap1 = new double[][]{{uvmiddleCo[0], uvmiddleCo[1]},{altFace.uvMapEdges[(1-height)*4],altFace.uvMapEdges[(1-height)*4+1]},{altFace.uvMapEdges[(2-height)*4],altFace.uvMapEdges[(2-height)*4+1]}};
				//System.out.println("**********");


			}
			face[] f = new face[2];
			//System.out.println(i+" "+j);
			boolean[] outBounds = new boolean[]{false,false};
			//if(threads==8){
			for(int f0 = 0;f0<f.length;f0++){
				behind[0] = 0;
				int out=0;
				for(int k=0;k<edges[f0].length;k++){

					float [] x = new float[2];
					float [] y = new float[2];
					double[] zero = paintShape.calcPeripheral(edges[f0][k].c[0].xyz, shapes[i].xyz, behind);
					double[] one = paintShape.calcPeripheral(edges[f0][k].c[1].xyz, handler.shapes[i].xyz);
					x[0]=(float) zero[0];
					y[0]=(float) zero[1];
					x[1]=(float)one[0];
					y[1]=(float)one[1];

					if((x[0]<0 && x[1]<0) || (y[0]<0 && y[1]<0)
							|| (x[0]>screenWid && x[1]>screenWid)
							|| (y[0]>screenHei && y[1]>screenHei)
							){
						out++;
					}
				}
				if((out==edges[f0].length || behind[0]==edges[f0].length)){
					outBounds[f0] = true;
				}
			}
			//}
			//f[0].b = altFace.b;
			//f[1].b = altFace.b;

			
			if((outBounds[0]==false && outBounds[1]==false)||times<3){
				f[0] = new face(edges[0], null, uvMap0,null);
				f[1] = new face(edges[1], null, uvMap1,null);
				averageAngle.calcAverageAngle(shapes[i].xyz,f[0], edges[0]);
				averageAngle.calcAverageAngle(shapes[i].xyz,f[1], edges[1]);
				used*=2;
				recursiveDraw(f[0], i, j, 1, times+1, low, (low+high)/2, behind[0]);
				recursiveDraw(f[1], i, j, 1, times+1,(low+high)/2 + 1, high, behind[0]);
			}else if(outBounds[0]==true && outBounds[1]==true && times==0){
				//do nothing
				System.out.println("nothing "+shapes[i].id);
				for(triangleThread t : h){
					t.shapeNo = i+1;
				}
				out = true;
				//return;
			}
			else if(outBounds[0]==true){
				f[1] = new face(edges[1], null, uvMap1,null);
				averageAngle.calcAverageAngle(shapes[i].xyz,f[1], edges[1]);
				recursiveDraw(f[1], i, j, 1, times+1,low, high, behind[0]);
			}else if(outBounds[1]==true){
				f[0] = new face(edges[0], null, uvMap0,null);
				averageAngle.calcAverageAngle(shapes[i].xyz,f[0], edges[0]);
				recursiveDraw(f[0], i, j, 1, times+1, low, high, behind[0]);
			}else if(outBounds[0]==true && outBounds[1]==true){
				//threads*=2;
			}
			

		}else{
			allFaces[counter] = altFace;
			counter++;
		}

	}

}
