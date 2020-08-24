
import jcuda.*;
import jcuda.driver.CUfunction;
import jcuda.driver.CUmodule;
import jcuda.runtime.*;
import static jcuda.driver.JCudaDriver.*;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

import jcuda.driver.*;

public class javacuda
{
	static Semaphore s = new Semaphore(1);
	public static void main()
	{
		// Enable exceptions and omit all subsequent error checks
        //JCudaDriver.setExceptionsEnabled(true);

        int hostOutput[] = null;
        CUdeviceptr deviceInputA = null;
        CUdeviceptr deviceInputB = null;
        CUdeviceptr deviceOutput = null;
        // Initialize the driver and create a context for the first device.
        cuInit(0);
        CUdevice device = new CUdevice();
        cuDeviceGet(device, 0);
        CUcontext context = new CUcontext();
        cuCtxCreate(context, 0, device);

        // Load the ptx file.
        CUmodule module = new CUmodule();
        cuModuleLoad(module, "basicadd.ptx");

        // Obtain a function pointer to the "add" function.
        CUfunction function = new CUfunction();
        cuModuleGetFunction(function, module, "add");

        int numElements = 10000;

        // Allocate and fill the host input data
        int hostInputA[] = new int[numElements];
        int hostInputB[] = new int[numElements];
        for(int i = 0; i < numElements; i++)
        {
            hostInputA[i] = i;
            //hostInputB[i] = i;
        }
        
        long time = System.currentTimeMillis();
        for(int i=0;i<100;i++){
        	// Allocate the device input data, and copy the
            // host input data to the device
            deviceInputA = new CUdeviceptr();
            cuMemAlloc(deviceInputA, numElements * Sizeof.INT);
            cuMemcpyHtoD(deviceInputA, Pointer.to(hostInputA),
                numElements * Sizeof.INT);
            deviceInputB = new CUdeviceptr();
            cuMemAlloc(deviceInputB, numElements * Sizeof.INT);
            cuMemcpyHtoD(deviceInputB, Pointer.to(hostInputB),
                numElements * Sizeof.INT);

            // Allocate device output memory
            deviceOutput = new CUdeviceptr();
            cuMemAlloc(deviceOutput, numElements * Sizeof.INT);
        	
        // Set up the kernel parameters: A pointer to an array
        // of pointers which point to the actual values.
        Pointer kernelParameters = Pointer.to(
            Pointer.to(new int[]{numElements}),
            Pointer.to(deviceInputA),
            Pointer.to(deviceInputB),
            Pointer.to(deviceOutput)
        );

        // Call the kernel function.
        int blockSizeX = 512;
        int gridSizeX = (int)Math.ceil((double)numElements / blockSizeX);
        
        cuLaunchKernel(function,
            gridSizeX,  1, 1,      // Grid dimension
            blockSizeX, 1, 1,      // Block dimension
            0, null,               // Shared memory size and stream
            kernelParameters, null // Kernel- and extra parameters
        );
        
        cuCtxSynchronize();
        // Allocate host output memory and copy the device output
        // to the host.
        hostOutput = new int[numElements];
        cuMemcpyDtoH(Pointer.to(hostOutput), deviceOutput,
            numElements * Sizeof.INT);
        
        hostInputA = hostOutput;
        cuMemFree(deviceInputA);
        cuMemFree(deviceInputB);
        cuMemFree(deviceOutput);
        //hostInputB = hostOutput;
        }
        System.out.println(System.currentTimeMillis() - time);
        //System.out.println(Arrays.toString(hostOutput));
        //threeDGraphics.jc+=System.currentTimeMillis() - time;
        // Clean up.
        
	}
}
