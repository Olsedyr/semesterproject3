package org.example;

import org.eclipse.milo.opcua.stack.core.types.builtin.DataValue;
import org.eclipse.milo.opcua.stack.core.types.builtin.Variant;
import org.example.beerMachine.OpcUA.MachineController;

import java.util.Scanner;

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
        //machineController.startMachine();
        //machineController.stopMachine();
        //machineController.abortMachine();
        //machineController.clearMachine();

        machineController.readStateCurrent();
        machineController.readProductsProcessed();

        machineController.readIngredients();
        machineController.readMaintenanceDetails();


        // let the example run forever
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a command (restart/start/stop/abort/clear): ");
            String userInput = scanner.nextLine();

            // Parse the user input to extract the command and target
            String[] parts = userInput.split(" ");
            if (parts.length == 1) {
                if (parts[0].equalsIgnoreCase("restart")){
                    machineController.resetMachine();
                } else if (parts[0].equalsIgnoreCase("start")){
                    machineController.startMachine();
                }else if (parts[0].equalsIgnoreCase("stop")){
                    machineController.stopMachine();
                }else if (parts[0].equalsIgnoreCase("abort")){
                    machineController.abortMachine();
                }else if (parts[0].equalsIgnoreCase("clear")){
                    machineController.clearMachine();
                }

            } else {
                System.out.println("Invalid input format. Please use 'Command Target'.");
            }
        }
    }
}