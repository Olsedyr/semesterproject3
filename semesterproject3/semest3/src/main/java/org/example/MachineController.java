package org.example;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;

import java.util.concurrent.ExecutionException;

public class MachineController {


    final OpcUaClient client = BeerClientSingleton.getInstance();
    public MachineController() {
    }


    public void readProductsProcessed() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
        System.out.println("Products Processed: " + readNodeValue(nodeId));
    }

    public void readProductsDefective() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");
        System.out.println("Defective Products: " + readNodeValue(nodeId));
    }

    public int readStateCurrent() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Status.StateCurrent");
        System.out.println("Current state: " + readNodeValue(nodeId));
        return (int) readNodeValue(nodeId);
    }

    public void readRecipeCurrent() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Admin.Parameter[0].Value");
        System.out.println("Current recipe: " + readNodeValue(nodeId));
    }
    public void readBatchIdCurrent() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Status.Parameter[0].Value");
        System.out.println("Current Batch ID: " + readNodeValue(nodeId));
    }

    public void readQuantityCurrent() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Status.Parameter[1].Value");
        System.out.println("Current quantity: " + readNodeValue(nodeId));
    }

    public void readMachineSpeedCurrent() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Status.CurMachSpeed");
        System.out.println("Current machine speed: " + readNodeValue(nodeId));
    }




    public void writeRecipeValue(int value) throws UaException, ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Command.Parameter[1].Value");
        writeValueToNode(nodeId, value);
        StatusCode statusCode = writeValueToNode(nodeId, value);
        if (statusCode.isGood()) {
            System.out.println("Recipe set to " + value);
        }
    }

    public void writeQuantityValue(int value) throws UaException, ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Command.Parameter[2].Value");
        StatusCode statusCode = writeValueToNode(nodeId, value);
        if (statusCode.isGood()) {
            System.out.println("Quantity set to " + value);
        }
    }

    public void writeMachineSpeedValue(int value) throws UaException, ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Command.MachSpeed");
        StatusCode statusCode = writeValueToNode(nodeId, value);
        if (statusCode.isGood()) {
            System.out.println("Machine speed set to " + value);
        }
    }

    public void writeBatchIdValue(int value) throws UaException, ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Command.Parameter[0].Value");
        StatusCode statusCode = writeValueToNode(nodeId, value);
        if (statusCode.isGood()) {
            System.out.println("Batch Id set to " + value);
        }
    }

    private Object readNodeValue(NodeId nodeId) throws ExecutionException, InterruptedException {
        DataValue dataValue = client.readValue(0, TimestampsToReturn.Both, nodeId).get();
        // System.out.println("DataValue= " + dataValue);

        Variant variant = dataValue.getValue();
        // System.out.println("Variant= " + variant);

        Object myVariable = variant.getValue();
        return myVariable;
        // System.out.println("Current state = " + myVariable);
    }
    private StatusCode writeValueToNode(NodeId nodeId, int value) {

        try {
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant((float)value))).get();

            if (statusCode.isGood()) {
                System.out.println("Write operation successful.");
            } else {
                System.err.println("Write operation failed: " + statusCode);
            }
            return statusCode;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void startMachine() throws ExecutionException, InterruptedException, UaException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
        final int value = 2;
        int state = readStateCurrent();
        if (state == 4){ // State must be idle
            // set the batchId (needs database value)
            // writeBatchIdValue(batchId);

            // start the machine
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
            if (statusCode.isGood()) {
                System.out.println("Write operation successful. Machine is running");
            } else {
                System.err.println("Write operation failed. Machine is NOT running: " + statusCode);
            }
            setChangeRequestTrue();
        } else {
            System.err.println("Machine state is " + state + ".");
            System.err.println("Machine must be idle, before you can start it (reset the machine)");
        }
    }

    public void resetMachine() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
        final int value = 1;
        int state = readStateCurrent();
        if (state == 17 || state == 2){ // state must be complete or stopped
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
            if (statusCode.isGood()) {
                System.out.println("Write operation successful. Machine is reset");
            } else {
                System.err.println("Write operation failed. Machine is NOT reset: " + statusCode);
            }
            setChangeRequestTrue();
        } else {
            System.err.println("Machine state is " + state + ".");
            System.err.println("Machine state must be completed or stopped, before you can reset it");
        }
    }

    public void stopMachine() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
        final int value = 3;
        int state = readStateCurrent();
        if (state !=2 && state !=9 && state !=8 && state !=1 ){ // state must not be aborted, aborting, clearing or stopped
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
            if (statusCode.isGood()) {
                System.out.println("Write operation successful. Machine is stopped");
            } else {
                System.err.println("Write operation failed. Machine is NOT stopped: " + statusCode);
            }
            setChangeRequestTrue();
        } else {
            System.err.println("Machine state is " + state + ".");
            System.err.println("Failed, machine is already aborted");
        }
    }

    public void abortMachine() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
        final int value = 4;
        int state = readStateCurrent();
        if (state !=9 ){ // state must not be aborted
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
            if (statusCode.isGood()) {
                System.out.println("Write operation successful. Machine is aborted");
            } else {
                System.err.println("Write operation failed. Machine is NOT aborted: " + statusCode);
            }
            setChangeRequestTrue();
        } else {
            System.err.println("Machine state is " + state + ".");
            System.err.println("Machine is already stopped or aborted");
        }
    }

    public void clearMachine() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
        final int value = 5;
        int state = readStateCurrent();
        if (state ==9 ){ // state must be aborted
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
            if (statusCode.isGood()) {
                System.out.println("Write operation successful. Machine is cleared");
            } else {
                System.err.println("Write operation failed. Machine is NOT cleared: " + statusCode);
            }
            setChangeRequestTrue();
        } else {
            System.err.println("Machine state is " + state + ".");
            System.err.println("Machine state must be aborted, before you can clear it");
        }
    }
    private void setChangeRequestTrue() throws ExecutionException, InterruptedException {
        NodeId nodeId  = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
        final Boolean value = true;
        StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
    }
}
