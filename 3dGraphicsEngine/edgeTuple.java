
public class edgeTuple {
	float xCo;
	float sum;
	float[] start;
	float[] fin;
	public edgeTuple(float xCo, float sum){
		this.xCo= xCo;
		this.sum = sum;
	}
	public edgeTuple(float xCo, float sum,float[] start,float[] fin){
		this.start = start;
		this.fin = fin;
	}
}
