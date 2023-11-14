package org.example;

public class Main {



    public static void main(String[] args) throws Exception {

        MachineController machineController = new MachineController();
        machineController.readStateCurrent();

        /*
        machineController.readMachineSpeedCurrent();
        machineController.readBatchIdCurrent();
        machineController.readQuantityCurrent();
        machineController.readRecipeCurrent();
        */

        machineController.writeQuantityValue(100);
        machineController.writeMachineSpeedValue(20);
        machineController.writeBatchIdValue(2);
        machineController.writeRecipeValue(1);


        machineController.startMachine();

        machineController.readStateCurrent();
        machineController.readProductsProcessed();

        machineController.readIngredients();
        machineController.readMaintenanceDetails();

    }
}