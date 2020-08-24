import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class loadShapes {
	public static void load(){
		
		cameraNet.origAnglex = shape.setAngle(cameraNet.xyz, false);
		cameraNet.origAngley = shape.setAngle(cameraNet.xyz, true);


		try {
			File inputFile = new File("myShapes.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("shape");
			System.out.println("----------------------------");
			handler.shapes = new shape[nList.getLength()+1];
			for (int temp = 0; temp < nList.getLength(); temp++) {

				double[] coord = new double[3]; 
				int faces=0; 
				int[] edgesPerFace = null;

				double[][][] csco = null;
				String id = null;
				double[][][] bounds = null;
				String[] strings = null;
				double[][][] corners = null;

				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					id = eElement
							.getElementsByTagName("id")
							.item(0)
							.getTextContent();
					System.out.println("id : " 
							+ id);

					coord[0] =  Integer.parseInt(eElement
							.getElementsByTagName("coord")
							.item(0).getAttributes().getNamedItem("x")
							.getTextContent());

					coord[1] =  Integer.parseInt(eElement
							.getElementsByTagName("coord")
							.item(0).getAttributes().getNamedItem("y")
							.getTextContent());

					coord[2] =  Integer.parseInt(eElement
							.getElementsByTagName("coord")
							.item(0).getAttributes().getNamedItem("z")
							.getTextContent());

					System.out.println("coord : " 
							+ coord[0]
									+" "
									+ coord[1]
											+" "
											+ coord[2]);

					faces = Integer.parseInt(eElement
							.getElementsByTagName("faceNumbers")
							.item(0)
							.getAttributes()
							.getNamedItem("n").getTextContent());
					bounds = new double[faces][4][3];
					edgesPerFace = new int[faces];
					strings = new String[faces];
					corners = new double[faces][][];
					System.out.println("number of faces : " 
							+ faces);


					NodeList fList = eElement.getElementsByTagName("edge");
					for(int i=0;i<faces;i++){
						Node fNode = fList.item(i);
						Element eeElement = (Element) fNode;

						edgesPerFace[i] = Integer.parseInt(eeElement.getTextContent());

						System.out.println("edgesPerFace : " 
								+ eeElement.getTextContent());
					}   
					csco = new double[faces][][];
					NodeList cfsList = eElement.getElementsByTagName("cornersForFace");
					System.out.println(" "+cfsList.getLength());
					for(int i=0;i<faces;i++){

						csco[i] = new double[edgesPerFace[i]][3];

						Node cfsNode = cfsList.item(i);
						Element eeElement = (Element) cfsNode;
						NodeList csList = eeElement.getElementsByTagName("corner");
						System.out.println(csList.getLength());
						for(int j=0;j<csList.getLength();j++){
							Node cNode = csList.item(j);
							Element eeeElement = (Element) cNode;

							csco[i][j] = new double[]{
									Integer.parseInt(eeeElement.getAttribute("x")),
									Integer.parseInt(eeeElement.getAttribute("y")),
									Integer.parseInt(eeeElement.getAttribute("z"))};

							System.out.println("corner : " 
									+ csco[i][j][0]
											+" " + csco[i][j][1]
													+" " + csco[i][j][2]);
						}
					}   

					if(eElement.getElementsByTagName("boundries").getLength() != 0){
						NodeList boundList = eElement.getElementsByTagName("face");

						//System.out.println(edgesPerFace[0]+" "+edgesPerFace[1]);
						for(int i=0;i<faces;i++){
							System.out.println("face: "+i);
							for(int j=0;j<4;j++){
								Node b = boundList.item(i);
								Element eeeElement = (Element) b;
								bounds[i][j] = new double[]{
										Integer.parseInt(eeeElement.getElementsByTagName("boundary").item(j).getAttributes().getNamedItem("x").getTextContent())
										,Integer.parseInt(eeeElement.getElementsByTagName("boundary").item(j).getAttributes().getNamedItem("y").getTextContent())
										,Integer.parseInt(eeeElement.getElementsByTagName("boundary").item(j).getAttributes().getNamedItem("z").getTextContent())
								};
								System.out.println(
										"x: "+bounds[i][j][0]
												+" y: "+
												bounds[i][j][1]
														+" z: "+
														bounds[i][j][2]
										);
							}


						}   
					}
					NodeList urlList = eElement.getElementsByTagName("image");
					System.out.println("number of images: "+urlList.getLength());
					for(int i=0;i<faces;i++){
						Node urlNode = urlList.item(i);
						Element eeElement = (Element) urlNode;
						System.out.println(eeElement.getTextContent());
						strings[i] = eeElement.getTextContent();
					}   

					NodeList uvList = eElement.getElementsByTagName("uvFace");
					System.out.println("number of uv faces: "+uvList.getLength());
					for(int i=0;i<faces;i++){
						Node uvNode = uvList.item(i);
						Element eeElement = (Element) uvNode;
						NodeList uvCorners = eeElement.getElementsByTagName("corner");
						System.out.println("Corners per face "+uvCorners.getLength());
						corners[i] = new double[uvCorners.getLength()][2];
						for(int j=0;j<uvCorners.getLength();j++){
							Node u = uvCorners.item(j);
							Element eeeElement = (Element) u;
							corners[i][j][0] = Double.parseDouble(eeeElement.getAttribute("x"));
							corners[i][j][1] = Double.parseDouble(eeeElement.getAttribute("y"));
							System.out.println(
									corners[i][j][0]+" "+
											corners[i][j][1]);
						}

					}   

				}
				createShape(temp, coord, faces, edgesPerFace, csco
						,strings, bounds, corners, id);
				if(temp==0){
				createShape(3, new double[]{-100,0,100}, faces, edgesPerFace, csco
						,strings, bounds, corners, id+1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	static void createShape(int num, double[] shapeCo, int faceNo, int[] edgeNo, double[][][] cornerCo, 
			String[] s, double[][][] bounds, double[][][] corners, String id){
		face[] f = new face[faceNo];

		for(int i=0;i<f.length;i++){

			edge[] e = new edge[edgeNo[i]];
			for(int j=0;j<e.length;j++){


				if(j==0){
					e[j] = new edge(new Corner[]{
							new Corner(
									cornerCo[i][j]), 
							new Corner(
									cornerCo[i][j+1])
					}

							);
				}
				else if(j!=e.length-1){
					e[j] = new edge( 
							new Corner[]{
									e[j-1].c[1],
									new Corner(
											cornerCo[i][j+1])
							}

							);
				}else{
					e[j] = new edge(
							new Corner[]{
									e[j-1].c[1], 
									e[0].c[0]
							}
							);
				}
			}
			f[i] = new face(e, new File(s[i]), corners[i],null);
			averageAngle.calcAverageAngle(shapeCo ,f[i], e);
			f[i].id = ""+i;
			//f[i].bounds = bounds[i];
		}
		handler.shapes[num] = new shape(shapeCo, f);
		handler.shapes[num].id = id;
	}


}