package org.example;

public class Main {



    public static void main(String[] args) throws Exception {

        MachineController machineController = new MachineController();
        machineController.readStateCurrent();

        /*
        // read values
        machineController.readMachineSpeedCurrent();
        machineController.readBatchIdCurrent();
        machineController.readQuantityCurrent();
        machineController.readRecipeCurrent();
        */

        // Config batch
        //*
        machineController.writeQuantityValue(100);
        machineController.writeMachineSpeedValue(20);
        machineController.writeRecipeValue(1);
        //*/


        // control machine
        //machineController.resetMachine();
        machineController.startMachine();
        //machineController.stopMachine();
        //machineController.abortMachine();
        //machineController.clearMachine();

        machineController.readStateCurrent();
        machineController.readProductsProcessed();

    }
}