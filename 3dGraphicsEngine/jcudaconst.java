
import jcuda.*;
import jcuda.driver.CUcontext;
import jcuda.driver.CUdevice;
import jcuda.driver.CUdeviceptr;
import jcuda.driver.CUfunction;
import jcuda.driver.CUmodule;
import jcuda.driver.JCudaDriver;
import jcuda.runtime.*;
import static jcuda.driver.JCudaDriver.*;
import java.io.*;
import java.util.Arrays;

public class jcudaconst 
{
    public static void main() 
    {
        // Enable exceptions and omit all subsequent error checks
        JCudaDriver.setExceptionsEnabled(true);

        // Initialize the driver and create a context for the first device.
        cuInit(0);
        CUdevice device = new CUdevice();
        cuDeviceGet(device, 0);
        CUcontext context = new CUcontext();
        cuCtxCreate(context, 0, device);

        // Load the PTX file.
        CUmodule module = new CUmodule();
        cuModuleLoad(module, "const.ptx");

        // Obtain the pointer to the constant memory, and print some info
        CUdeviceptr constantMemoryPointer = new CUdeviceptr();
        long constantMemorySizeArray[] = { 0 };
        cuModuleGetGlobal(constantMemoryPointer, constantMemorySizeArray, 
            module, "constantMemoryData");
        int constantMemorySize = (int)constantMemorySizeArray[0];
        
        System.out.println("constantMemoryPointer: " + constantMemoryPointer);
        System.out.println("constantMemorySize: " + constantMemorySize);

        // Copy some host data to the constant memory
        int numElements = jp.pixelarray.length;//constantMemorySize / Sizeof.INT;
        int hostData[] = new int[numElements];
        int hostInputA[] = new int[numElements];
        CUdeviceptr deviceInputA = null;
        for (int i = 0; i < numElements; i++)
        {
        	hostInputA[i] = i;
            hostData[i] = i;
        }
        //System.out.println(Arrays.toString(hostData));
        int[] hostResult=null;
        
        long time = System.currentTimeMillis();
        for(int i=0;i<1000;i++){
        
        deviceInputA = new CUdeviceptr();
        cuMemAlloc(deviceInputA, numElements * Sizeof.INT);
        cuMemcpyHtoD(deviceInputA, Pointer.to(hostInputA),
             numElements * Sizeof.INT);
        	
        cuMemcpyHtoD(constantMemoryPointer, 
            Pointer.to(hostData), constantMemorySize);
        
        // Now use the constant memory in the kernel call:
        
        // Obtain a function pointer to the "constantMemoryKernel" function.
        CUfunction kernel = new CUfunction();
        cuModuleGetFunction(kernel, module, "constantMemoryKernel");

        // Allocate some device memory
        CUdeviceptr deviceData = new CUdeviceptr();
        cuMemAlloc(deviceData, constantMemorySize);
        
        // Set up the kernel parameters
        Pointer kernelParameters = Pointer.to(
            Pointer.to(deviceData),
            Pointer.to(deviceInputA),
            Pointer.to(new int[]{numElements})
        );
        
        // Launch the kernel
        int blockSizeX = numElements;
        int gridSizeX = 1;
        cuLaunchKernel(kernel,
            gridSizeX,  1, 1, 
            blockSizeX, 1, 1,
            0, null,         
            kernelParameters, null 
        );
        cuCtxSynchronize();
        
        // Copy the result back to the host, and verify that it is
        // the same that was copied to the constant memory
        hostResult = new int[numElements];
        cuMemcpyDtoH(Pointer.to(hostResult), deviceData, constantMemorySize);
        hostInputA = hostResult;
        }
        boolean passed = Arrays.equals(hostData,  hostResult);
        System.out.println("Test " + (passed ? "PASSED" : "FAILED"));
        System.out.println(Arrays.toString(hostResult));
        System.out.println(System.currentTimeMillis() - time);
    }
}