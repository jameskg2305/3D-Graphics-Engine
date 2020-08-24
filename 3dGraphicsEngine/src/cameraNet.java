
/**
Camera's position, rotation angle and centre point.
**/
public class cameraNet {
	static float[] cameraPos = new float[]{0,0,0};
	static float[] dupcameraPos = new float[]{0,0,0};
	static double origAnglex;
	static double origAngley;
	static double duplicateorigAnglex;
	static double duplicateorigAngley;
	static double[] xyz = new double[]{0,0,300};
}















/*
if(((highX-lowX>200 || highY-lowY>200)
				&& !(lowX>handler.screenWid && highX>handler.screenWid)
				&& !(lowX<0 && highX<0)
				&& !(lowY>handler.screenHei && highY>handler.screenHei)
				&& !(lowY<0 && highY<0)
				|| faces.edges.length>3)
				&& times<10&&2==3){
			edge[] edges1;
			edge[] edges2;

			double uvMap0[][] = null;
			double uvMap1[][] = null;

			if(faces.edges.length>3){

				uvMap0 = new double[][]{{faces.uvMapEdges[1][0][0],faces.uvMapEdges[1][0][1]},{faces.uvMapEdges[2][0][0],faces.uvMapEdges[2][0][1]},{faces.uvMapEdges[0][0][0],faces.uvMapEdges[0][0][1]}};

				edges1 = new edge[]{
						new edge(new Corner[]{
								new Corner(faces.edges[1].c[0].xyz),
								new Corner(faces.edges[2].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(faces.edges[2].c[0].xyz),
								new Corner(faces.edges[0].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(faces.edges[0].c[0].xyz),
								new Corner(faces.edges[1].c[0].xyz)
						})
				};
				uvMap1 = new double[handler.shapes[i].faces[j].edges.length-1][2];
				edges2 = new edge[handler.shapes[i].faces[j].edges.length-1];
				for(int u = 0;u<edges2.length;u++){
					if(u==1){
						edges2[1] = new edge(new Corner[]{
								new Corner(faces.edges[2].c[0].xyz),
								new Corner(faces.edges[0].c[0].xyz)
						});
						uvMap1[u] = new double[]{faces.uvMapEdges[2][0][0], faces.uvMapEdges[2][0][1]};
					}
					else if(u<2){
						edges2[u] = new edge(new Corner[]{
								new Corner(faces.edges[3-u].c[0].xyz),
								new Corner(faces.edges[3-u-1].c[0].xyz)
						});
						uvMap1[u] = new double[]{faces.uvMapEdges[3-u][0][0], faces.uvMapEdges[3-u][0][1]};
					}else{
						edges2[u] = new edge(new Corner[]{
								new Corner(faces.edges[0].c[0].xyz),
								new Corner(faces.edges[edges2.length-1].c[0].xyz)
						});
						uvMap1[u] = new double[]{faces.uvMapEdges[0][0][0], faces.uvMapEdges[0][0][1]};
					}
				}
			}else{

				//System.out.println(times+" "+times+" "+times+" "+times+" ");
				double[] middleCo = new double[]{
						(faces.edges[first].c[0].xyz[0]+faces.edges[first+1].c[0].xyz[0])/(double)2.0,
						(faces.edges[first].c[0].xyz[1]+faces.edges[first+1].c[0].xyz[1])/(double)2.0,
						(faces.edges[first].c[0].xyz[2]+faces.edges[first+1].c[0].xyz[2])/(double)2.0
				};


				//System.out.println("b " + xpercentage+" "+ypercentage);

				edges1 = new edge[]{
						new edge(new Corner[]{
								new Corner(middleCo),
								new Corner(faces.edges[0].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(faces.edges[0].c[0].xyz),
								new Corner(faces.edges[2].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(faces.edges[2].c[0].xyz),
								new Corner(middleCo)
						})
				};

				double[] uvmiddleCo = new double[]{
						(faces.uvMapEdges[first+1][0][0]+faces.uvMapEdges[first][0][0])/2,
						(faces.uvMapEdges[first+1][0][1]+faces.uvMapEdges[first][0][1])/2
				};

				edges2 = new edge[]{
						new edge(new Corner[]{
								new Corner(middleCo),
								new Corner(faces.edges[1-first].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(faces.edges[1-first].c[0].xyz),
								new Corner(faces.edges[2-first].c[0].xyz)
						}),
						new edge(new Corner[]{
								new Corner(faces.edges[2-first].c[0].xyz),
								new Corner(middleCo)
						})
				};
				uvMap0 = new double[][]{{uvmiddleCo[0], uvmiddleCo[1]},{faces.uvMapEdges[0][0][0],faces.uvMapEdges[0][0][1]},{faces.uvMapEdges[2][0][0],faces.uvMapEdges[2][0][1]}};
				uvMap1 = new double[][]{{uvmiddleCo[0], uvmiddleCo[1]},{faces.uvMapEdges[1-first][0][0],faces.uvMapEdges[1-first][0][1]},{faces.uvMapEdges[2-first][0][0],faces.uvMapEdges[2-first][0][1]}};
				//System.out.println("**********");


			}
			face f0 = new face(edges1, null, uvMap0,faces.pixelsColours);
			face f1 = new face(edges2, null, uvMap1,faces.pixelsColours);
			f0.b = faces.b;
			f1.b = faces.b;
			averageAngle.calcAverageAngle(handler.shapes[i].xyz,f0, edges1);
			averageAngle.calcAverageAngle(handler.shapes[i].xyz,f1, edges2);

			//recursiveDraw(threads, f0, i, j, 1, times+1);
			//recursiveDraw(threads, f1, i, j, 1, times+1);
			function(f0, i, j, 1, times+1);
			function(f1, i, j, 1, times+1);
		}else 
 */


/*
 * 
 if(times==0 && 2==3){
			for(int k=0;k<altFace.edges.length;k++){

				float [] x = new float[2];
				float [] y = new float[2];
				x[0]=(float)paintShape.calcPeripheral(altFace.edges[k].c[0].xyz, handler.shapes[i].xyz, behind)[0];
				y[0]=(float)paintShape.calcPeripheral(altFace.edges[k].c[0].xyz, handler.shapes[i].xyz)[1];
				x[1]=(float)paintShape.calcPeripheral(altFace.edges[k].c[1].xyz, handler.shapes[i].xyz)[0];
				y[1]=(float)paintShape.calcPeripheral(altFace.edges[k].c[1].xyz, handler.shapes[i].xyz)[1];
				boolean firstout = false;
				for(int r=1;r<3;r++){
				if(x[r-1]<0){
					if(r==1){
					outbounds++;
					}
					l[k]=r;
					if(firstout ==false){
						firstout = true;
					}else{
						l[k] = 3;
					}
					
				}
				else if(x[r-1]>handler.screenWid){
					if(r==1){
						outbounds++;
						}
					l[k]=r;
					if(firstout ==false){
						firstout = true;
					}else{
						l[k] = 3;
					}
				}
				}
				for(int r=1;r<3;r++){
				if(y[r-1]<0){
					if(r==1){
						outbounds++;
						}
					l[k]=r;
					if(firstout ==false){
						firstout = true;
					}else{
						l[k] = 3;
					}
				}
				else if(y[r-1]>handler.screenHei){
					if(r==1){
						outbounds++;
						}
					l[k]=r;
					if(firstout ==false){
						firstout = true;
					}else{
						l[k] = 3;
					}
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
				
				pair[k] = new edgeTuple(m, yInter);
			}
			if(outbounds!=0){
				//altFace.edges = new edge[altFace.edges.length+2-outbounds];
				System.out.println("behind is " + behind[0]);
				System.out.println(faces.edges.length+" "+altFace.edges.length);
				System.out.println(Arrays.toString(l));
				System.out.println(outbounds);
				//edge[] newedges = new edge[];
				for(int k=0;k<altFace.edges.length;k++){
					if(k==0){
					altFace.edges[k] = new edge(
							new Corner[]{
									new Corner(new double[]{0,0,0}),
									new Corner(new double[]{0,0,0})
							});
					}else if(k==altFace.edges.length-1){
						altFace.edges[k] = new edge(
								new Corner[]{
										altFace.edges[k-1].c[1],
										altFace.edges[0].c[0]
								});
					}else{
						altFace.edges[k] = new edge(
								new Corner[]{
										altFace.edges[k-1].c[1],
										new Corner(new double[]{0,0,0})
								});
					}
					
				}
				for(int k=0;k<faces.edges.length;k++){
				if(l[k]==1){
					altFace.edges[k].c[1] = faces.edges[k].c[1];
				}
				if(l[k]==0){
					altFace.edges[k].c[0] = faces.edges[k].c[0];
					altFace.edges[k].c[1] = faces.edges[k].c[1];
				}
				if(l[k]==2){
					altFace.edges[k].c[0] = faces.edges[k].c[0];
				}
				}
				//altFace.uvMapEdges = new double[altFace.uvMapEdges.length+outbounds][2][2];
				
				
				
				for(int k=0;k<faces.edges.length;k++){

					float [] x = new float[2];
					float [] y = new float[2];
					x[0]=(float)paintShape.calcPeripheral(faces.edges[k].c[0].xyz, handler.shapes[i].xyz)[0];
					y[0]=(float)paintShape.calcPeripheral(faces.edges[k].c[0].xyz, handler.shapes[i].xyz)[1];
					x[1]=(float)paintShape.calcPeripheral(faces.edges[k].c[1].xyz, handler.shapes[i].xyz)[0];
					y[1]=(float)paintShape.calcPeripheral(faces.edges[k].c[1].xyz, handler.shapes[i].xyz)[1];
					
					
					if(l[k]>0){
						
					}
				}
				
				for(int k=0;k<altFace.edges.length;k++){
					System.out.println(altFace.edges[k].c[0].xyz[0]+" "+altFace.edges[k].c[0].xyz[1]+" "+altFace.edges[k].c[0].xyz[2]);
					System.out.println(altFace.edges[k].c[1].xyz[0]+" "+altFace.edges[k].c[1].xyz[1]+" "+altFace.edges[k].c[1].xyz[2]);
				}
				//System.exit(1);
			}
			
		}
 */