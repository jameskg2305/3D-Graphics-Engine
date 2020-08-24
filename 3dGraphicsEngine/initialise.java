import java.util.HashMap;

public class initialise {
	static HashMap<String, shape> hash = new HashMap<String, shape>();
	static void init(){
		for(shape s : handler.shapes){
			hash.put(s.id, s);
		}
		System.out.println(hash);
		quaternionRotate.rotate(initialise.hash.get("pyramid1"), new double[]{1,0,0}, 45);
		//runScript.rotateShapeYAxis(threeDGraphics.shapes[0], -45);
		//if(handler.shapes[0].id.equals("sky")){
		//quaternionRotate.rotate(handler.shapes[0], new double[]{0,1,0}, 90/2);
		//}
	}
}
