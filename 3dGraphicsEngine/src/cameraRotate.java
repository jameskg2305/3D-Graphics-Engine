import java.awt.MouseInfo;

public class cameraRotate {
	static double rad = 180/Math.PI;
	static void mouseListen(double lastX, double lastY) {
		// TODO Auto-generated method stub
		double yAngle = (MouseInfo.getPointerInfo().getLocation().x-lastX);
		double xAngle = (MouseInfo.getPointerInfo().getLocation().y-lastY);



		//xAngle*=-1;


		double dist = Math.sqrt(Math.pow(cameraNet.xyz[0],2) +Math.pow(cameraNet.xyz[1],2)+Math.pow(cameraNet.xyz[2],2));

		double cx = cameraNet.xyz[0]/1;
		double cy = cameraNet.xyz[1]/1;
		double cz = cameraNet.xyz[2]/1;

		double angle = yAngle;
		double uAxisy;
		double uAxisz;

		double uAxisx=0;
		uAxisy = 1;	
		uAxisz = 0;

		quaternions q = new quaternions(Math.sin(angle/rad)*uAxisx, Math.sin(angle/rad)*uAxisy, Math.sin(angle/rad)*uAxisz,Math.cos(angle/rad));
		quaternions v = new quaternions(cx,cy,cz,0);

		quaternions newQ = q.multi(q, v);

		double qMag = Math.sqrt(
				Math.pow(Math.sin(angle/rad)*uAxisx, 2)+
				Math.pow(Math.sin(angle/rad)*uAxisy, 2)+
				Math.pow(Math.sin(angle/rad)*uAxisz, 2)+
				Math.pow(Math.cos(angle/rad), 2)
				);

		v = new quaternions((-Math.sin(angle/rad)*uAxisx)/qMag, (-Math.sin(angle/rad)*uAxisy)/qMag, (-Math.sin(angle/rad)*uAxisz)/qMag,Math.cos(angle/rad)/qMag);

		newQ = q.multi(newQ, v);

		
		//acx = newQ.x;
		//acy = newQ.y;
		//cz = newQ.z;

		cx = newQ.x/1;
		cy = newQ.y/1;
		cz = newQ.z/1;
		if(xAngle>0 && cy<0.95 || xAngle<0 && cy>-0.95){
			angle = xAngle;

			if(cz!=0){
				uAxisx=-Math.sin(Math.atan(cx/cz)+(Math.PI/2));
				uAxisz=-Math.cos(Math.atan(cx/cz)+(Math.PI/2));
			}
			else{
				uAxisx = 0;	
				uAxisz = 1;
			}
			uAxisy=0;
			if(cz<0){
				uAxisx*=-1;
				uAxisz*=-1;
			}
			q = new quaternions(Math.sin(angle/rad)*uAxisx, Math.sin(angle/rad)*uAxisy, Math.sin(angle/rad)*uAxisz,Math.cos(angle/rad));
			v = new quaternions(cx/dist,cy/dist,cz/dist,0);
			newQ = q.multi(q, v);

			qMag = Math.sqrt(
					Math.pow(Math.sin(angle/rad)*uAxisx, 2)+
					Math.pow(Math.sin(angle/rad)*uAxisy, 2)+
					Math.pow(Math.sin(angle/rad)*uAxisz, 2)+
					Math.pow(Math.cos(angle/rad), 2)
					);
			v = new quaternions((-Math.sin(angle/rad)*uAxisx)/qMag, (-Math.sin(angle/rad)*uAxisy)/qMag, (-Math.sin(angle/rad)*uAxisz)/qMag,Math.cos(angle/rad)/qMag);

			newQ = q.multi(q, v);

			if(newQ.y>0.95){
				newQ.y=0.95;
			}else if(newQ.y<-0.95){
				newQ.y=-0.95;
			}
		}

		cameraNet.xyz[0]=(newQ.x);
		cameraNet.xyz[1]=(newQ.y);
		cameraNet.xyz[2]=(newQ.z);


		//if(runThread.paintdone==true){

		cameraNet.duplicateorigAnglex = shape.setAngle(cameraNet.xyz, false);
		cameraNet.duplicateorigAngley = shape.setAngle(cameraNet.xyz, true);
		//}
	}
}
