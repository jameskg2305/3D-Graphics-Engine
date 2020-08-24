import static org.jocl.CL.*;

import java.util.Arrays;

import org.jocl.*;

/**
 * A small JOCL sample.
 */
public class openCL
{
    /**
     * The source code of the OpenCL program to execute
     */
    private static String programSource =
        "__kernel void "+
        "sampleKernel(__global int *a,"+
        "             __global const int *b"+
                        ")"+
        "{"+
        "   int xid = get_global_id(0);"+
        
		"   a[xid] = b[xid]+1;"+
		"}";

    /**
     * The entry point of this sample
     * 
     * @param args Not used
     */
    static cl_command_queue commandQueue;
    static cl_kernel kernel;
    static cl_context context;
    //static int dstArray[] = new int[5];
    static cl_mem memObjects[] = new cl_mem[2];
    static int[] arr = new int[10000];
    static long global_work_size[] = new long[]{10000};
    static long local_work_size[] = new long[]{1};
    static int[] screenwh = new int[]{800,800, jp.wRaster.getWidth(), jp.wRaster.getHeight()};
    static Pointer srcA;
    static Pointer srcB;
    static int[] dstArray = new int[]{3,3,3,3};
    static Pointer srcC;
    static Pointer srcD;
    static Pointer srcE;
    static Pointer srcF;
    static Pointer srcG;
    static boolean first = false;
    public static void openCLmain()
    {
        //float srcArrayA[] = new float[]{0,0,0};
        //System.out.println("Result: "+java.util.Arrays.toString(srcArrayA));
        //float srcArrayB[] = new float[]{0,0,0};
    	 int numElements = 10000;

         // Allocate and fill the host input data
         int hostInputA[] = new int[numElements];
         for(int i = 0; i < numElements; i++)
         {
             hostInputA[i] = i;
             //hostInputB[i] = i;
         }
         
    	//System.out.println(b[1]);
    	long time = System.nanoTime();
    	for(int i=0;i<1000;i++){
    	dstArray = hostInputA;
    	 
          srcA = Pointer.to(arr);
          srcB = Pointer.to(dstArray);
         //System.out.println("start: "+arr[0]);
    	
    	
        memObjects[0] = clCreateBuffer(context, 
        		CL_MEM_READ_WRITE, 
                Sizeof.cl_int * arr.length, null, null);
        memObjects[1] = clCreateBuffer(context, 
        		CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
                Sizeof.cl_int * dstArray.length, srcB, null);
        // Set the arguments for the kernel
        /*clSetKernelArg(kernel, 0, 
            Sizeof.cl_mem, Pointer.to(memObjects[0]));*/
        
        clSetKernelArg(kernel, 0, 
            Sizeof.cl_mem, Pointer.to(memObjects[0]));
        clSetKernelArg(kernel, 1, 
            Sizeof.cl_mem, Pointer.to(memObjects[1]));
        // Set the work-item dimensions
        
        // Execute the kernel
        clEnqueueNDRangeKernel(commandQueue, kernel, 1, null,
            global_work_size, local_work_size, 0, null, null);
        //clWaitForEvents(0,null);
        // Read the output data
        clEnqueueReadBuffer(commandQueue, memObjects[0], CL_TRUE, 0,
        		arr.length * Sizeof.cl_int, srcA, 0, null, null);
        
        
        // Release kernel, program, and memory objects
        //clReleaseMemObject(memObjects[0]);
        clReleaseMemObject(memObjects[0]);
        clReleaseMemObject(memObjects[1]);
        // Verify the result
    	}
            //System.out.println("Result: "+dstArray[1]);
            //System.out.println("Result: "+dstArray[2]);
    	//System.out.println((System.nanoTime()-time)/1000000);
    	threeDGraphics.ocl+=((System.nanoTime()-time)/1000000);
        //System.out.println("Result: "+arr[0]);
    }
    
    /*private static float[] oneDArray(double[][] bounds) {
		// TODO Auto-generated method stub
		float[] result=new float[bounds.length*bounds[0].length];
		for(int i=0;i<bounds.length;i++){
			for(int j=0;j<bounds[0].length;j++){
				result[i*bounds[0].length+j] = (float)(bounds[i][j]);
			}
		}
		return result;
	}*/

	public static void initialise() {
		// TODO Auto-generated method stub
		long numBytes[] = new long[1];
		// Obtain the platform IDs and initialize the context properties
        //System.out.println("Obtaining platform...");
        cl_platform_id platforms[] = new cl_platform_id[1];
        clGetPlatformIDs(platforms.length, platforms, null);
        cl_context_properties contextProperties = new cl_context_properties();
        contextProperties.addProperty(CL_CONTEXT_PLATFORM, platforms[0]);
        
        // Create an OpenCL context on a GPU device
         context = clCreateContextFromType(
            contextProperties, CL_DEVICE_TYPE_GPU, null, null, null);
        if (context == null)
        {
            // If no context for a GPU device could be created,
            // try to create one for a CPU device.
            context = clCreateContextFromType(
                contextProperties, CL_DEVICE_TYPE_CPU, null, null, null);
            
            if (context == null)
            {
                System.out.println("Unable to create a context");
            }
        }

        // Enable exceptions and subsequently omit error checks in this sample
        CL.setExceptionsEnabled(true);
        
        // Get the list of GPU devices associated with the context
        clGetContextInfo(context, CL_CONTEXT_DEVICES, 0, null, numBytes); 
        
        // Obtain the cl_device_id for the first device
        int numDevices = (int) numBytes[0] / Sizeof.cl_device_id;
        cl_device_id devices[] = new cl_device_id[numDevices];
        clGetContextInfo(context, CL_CONTEXT_DEVICES, numBytes[0],  
            Pointer.to(devices), null);

        // Create a command-queue
         commandQueue = 
            clCreateCommandQueue(context, devices[0], 0, null);
        
     // Create the program from the source code
         cl_program program = clCreateProgramWithSource(context,
                 1, new String[]{ programSource }, null, null);
        
        // Build the program
        clBuildProgram(program, 0, null, null, null, null);
        
        // Create the kernel
         kernel = clCreateKernel(program, "sampleKernel", null);
        System.out.println("initialised");
	}
	
	public static void readOutput(){
		// Read the output data
        clEnqueueReadBuffer(commandQueue, memObjects[0], CL_TRUE, 0,
        		arr.length * Sizeof.cl_int, srcA, 0, null, null);
	}
	public static void createBuffers(){
		
        
        // Allocate the memory objects for the input- and output data
        
        
	}
}
