
public class handler {
	static shape[] shapes;
	static graphical g;
	static jp j;
	static int screenPosx=0;
	static int screenPosy=0;
	static int screenWid = 1920/8*4;
	static int screenHei = 1080/8*4;
	static int imageSizew = 1920/8*4;
	static int imageSizeh = 1080/8*4;
	static int threadCount = 8;
	static int pixelGroup = 1;
	static String fps = "";
	//static Semaphore s = new Semaphore(1);
	static triangleThread[] h = new triangleThread[threadCount];
}
