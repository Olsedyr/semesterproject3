package org.example.beerMachine.OpcUA;

import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.NodeId;
import org.eclipse.milo.opcua.stack.core.types.builtin.StatusCode;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutionException;

@Service
public class MachineController {
    final OpcUaClient client = BeerClientSingleton.getInstance();

    public MachineController() {
    }

    public void readProductsProcessed() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Admin.ProdProcessedCount");
        System.out.println("Products Processed: " + readNodeValue(nodeId));
    }

    public void readProductsDefective() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Admin.ProdDefectiveCount");
        System.out.println("Defective Products: " + readNodeValue(nodeId));
    }

    public int readStateCurrent() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Status.StateCurrent");
        System.out.println("Current state: " + readNodeValue(nodeId));
        return (int) readNodeValue(nodeId);
    }

    public float readRecipeCurrent() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Admin.Parameter[0].Value");
        System.out.println("Current recipe: " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readRecipeNext() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.Parameter[1].Value");
        System.out.println("Next recipe: " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public void readBatchIdCurrent() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Status.Parameter[0].Value");
        System.out.println("Current Batch ID: " + readNodeValue(nodeId));
    }

    public void readBatchIdNext() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.Parameter[0].Value");
        System.out.println("Next Batch ID: " + readNodeValue(nodeId));
    }

    public float readQuantityCurrent() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Status.Parameter[1].Value");
        System.out.println("Current quantity: " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readQuantityNext() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.Parameter[2].Value");
        System.out.println("Next quantity: " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readMachineSpeedCurrent() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Status.CurMachSpeed");
        System.out.println("The current machine speed (from 0-100): " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readMachineSpeedCurrentProductsPerMinute() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Status.MachSpeed");
        System.out.println("Current machine speed in products per minute: " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readMachineSpeedSet() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.MachSpeed");
        System.out.println("The set machine speed is : " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readIngredientBarley() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Inventory.Barley");
        System.out.println("Barley left : " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readIngredientHops() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Inventory.Hops");
        System.out.println("Hops left : " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readIngredientMalt() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Inventory.Malt");
        System.out.println("Malt left : " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readIngredientWheat() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Inventory.Wheat");
        System.out.println("Wheat left : " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readIngredientYeast() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Inventory.Yeast");
        System.out.println("Yeast left : " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readHumidity() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Status.Parameter[2].Value");
        System.out.println("Humidity : " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readTemperature() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Status.Parameter[3].Value");
        System.out.println("Temperature : " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public float readVibration() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Status.Parameter[4].Value");
        System.out.println("Vibration : " + readNodeValue(nodeId));
        return (float) readNodeValue(nodeId);
    }

    public boolean writeRecipeValue(int value) throws UaException, ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.Parameter[1].Value");
        writeValueToNode(nodeId, value);
        boolean success = false;
        StatusCode statusCode = writeValueToNode(nodeId, value);
        if (statusCode.isGood()) {
            System.out.println("Recipe set to " + value);
            success = true;
        }
        return success;
    }

    public boolean writeQuantityValue(int value) throws UaException, ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.Parameter[2].Value");
        if (value < 1) {
            value = 1;
        }
        if (value > 65000) {
            value = 65000;
        }
        StatusCode statusCode = writeValueToNode(nodeId, value);
        boolean success = false;
        if (statusCode.isGood()) {
            System.out.println("Quantity set to " + value);
            success = true;
        }
        return success;
    }

    public boolean writeMachineSpeedValue(int value) throws UaException, ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.MachSpeed");
        if (value < 0) {
            value = 0;
        }
        StatusCode statusCode = writeValueToNode(nodeId, value);
        boolean success = false;
        if (statusCode.isGood()) {
            System.out.println("Machine speed set to " + value);
            success = true;
        }
        return success;
    }

    public void writeBatchIdValueNext(int value) throws UaException, ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.Parameter[0].Value");
        StatusCode statusCode = writeValueToNode(nodeId, value);
        if (statusCode.isGood()) {
            System.out.println("Next Batch Id set to " + value);
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
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant((float) value))).get();

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


    public boolean startMachine() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
        final int value = 2;
        boolean started = false;
        int state = readStateCurrent();
        if (state == 4) { // State must be idle

            // start the machine
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
            if (statusCode.isGood()) {
                setChangeRequestTrue();
                Thread.sleep(1000); // Wait for setChangeRequestTrue() to finish
                state = readStateCurrent(); // check if the machine has started, or has completed a previous batch.
                Thread.sleep(1000); // Wait for state = readStateCurrent() to finish
                if (state == 6) {
                    System.out.println("Write operation successful. Machine is running");
                    started = true;

                } else if (state == 17) {
                    System.out.println("Write operation successful. Machine is is completing, reset the machine.");
                    started = false;
                } else {
                    System.err.println("Write operation failed. Machine is NOT running: " + statusCode);
                    started = false;
                }
            } else {
                System.err.println("Write operation failed. Machine is NOT running: " + statusCode);
            }

        } else {
            System.err.println("Machine state is " + state + ".");
            System.err.println("Machine must be idle, before you can start it (reset the machine)");
        }
        return started;
    }

    public boolean resetMachine() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
        final int value = 1;
        boolean reset = false;
        int state = readStateCurrent();
        if (state == 17 || state == 2) { // state must be complete or stopped
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
            if (statusCode.isGood()) {
                System.out.println("Write operation successful. Machine is reset");
                reset = true;
            } else {
                System.err.println("Write operation failed. Machine is NOT reset: " + statusCode);
            }
            setChangeRequestTrue();
        } else {
            System.err.println("Machine state is " + state + ".");
            System.err.println("Machine state must be completed or stopped, before you can reset it");
        }
        return reset;
    }

    public boolean stopMachine() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
        final int value = 3;
        boolean stopped = false;
        int state = readStateCurrent();
        if (state != 2 && state != 9 && state != 8 && state != 1) { // state must not be aborted, aborting, clearing or stopped
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
            if (statusCode.isGood()) {
                System.out.println("Write operation successful. Machine is stopped");
                stopped = true;
            } else {
                System.err.println("Write operation failed. Machine is NOT stopped: " + statusCode);
            }
            setChangeRequestTrue();
        } else {
            System.err.println("Machine state is " + state + ".");
            System.err.println("Failed, machine is already aborted");
        }
        return stopped;
    }

    public boolean abortMachine() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
        final int value = 4;
        boolean aborted = false;
        int state = readStateCurrent();
        if (state != 9) { // state must not be aborted
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
            if (statusCode.isGood()) {
                System.out.println("Write operation successful. Machine is aborted");
                aborted = true;
            } else {
                System.err.println("Write operation failed. Machine is NOT aborted: " + statusCode);
            }
            setChangeRequestTrue();
        } else {
            System.err.println("Machine state is " + state + ".");
            System.err.println("Machine is already stopped or aborted");
        }
        return aborted;
    }

    public boolean clearMachine() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.CntrlCmd");
        final int value = 5;
        boolean cleared = false;
        int state = readStateCurrent();
        if (state == 9) { // state must be aborted
            StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
            if (statusCode.isGood()) {
                System.out.println("Write operation successful. Machine is cleared");
                cleared = true;
            } else {
                System.err.println("Write operation failed. Machine is NOT cleared: " + statusCode);
            }
            setChangeRequestTrue();
        } else {
            System.err.println("Machine state is " + state + ".");
            System.err.println("Machine state must be aborted, before you can clear it");
        }
        return cleared;
    }

    private void setChangeRequestTrue() throws ExecutionException, InterruptedException {
        NodeId nodeId = new NodeId(6, "::Program:Cube.Command.CmdChangeRequest");
        final Boolean value = true;
        StatusCode statusCode = client.writeValue(nodeId, DataValue.valueOnly(new Variant(value))).get();
    }

}






