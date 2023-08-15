package com.k4rtowka.lab5.CollectionManagers.ModeManager.userMode;

import Models.Coordinates;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.ModeManager;
import com.k4rtowka.lab5.CollectionManagers.validators.CoordinateXValidator;
import com.k4rtowka.lab5.CollectionManagers.validators.InputValidator;
import com.k4rtowka.lab5.CollectionManagers.validators.Validator;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CoordinatesCLIManager implements ModeManager<Coordinates> {

    @Override
    public Coordinates buildObject() throws BuildObjectException {
        try {
            System.out.println();
            System.out.println("Generating Coordinates...");
            Coordinates coordinates = new Coordinates();
            Scanner scanner = new Scanner(System.in);
            InputValidator inputValidator = new InputValidator();
            String nextLine;


            // coordinate x
            Double x;
            Validator<Double> coordXValidator = new CoordinateXValidator();
            while (true) {
                try {
                    System.out.print("Enter coordinate x: ");
                    nextLine = scanner.nextLine();
                    if (inputValidator.validate(nextLine)) {
                        x = Double.parseDouble(nextLine);
                        if (coordXValidator.validate(x)) {
                            coordinates.setX(x);
                            break;
                        } else {
                            System.out.println("The value does not match the instructions! Try again.");
                            System.out.println("Instruction: " + coordXValidator.getDescr());
                        }
                    } else System.out.println("Input should not be empty!(value is not null)");
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                }
            }

            //coordinate y
            int y;
            while(true) {
                try {
                    System.out.print("Enter coordinate y: ");
                    nextLine = scanner.nextLine();
                    if (inputValidator.validate(nextLine)) {
                        y = Integer.parseInt(nextLine);
                        coordinates.setY(y);
                        break;
                    } else System.out.println("Input should not be empty!(value is not null)");
                }catch (InputMismatchException | NumberFormatException e){
                    System.out.println("Wrong input! Try again. (type: integer)");
                }
            }

            return coordinates;

        } catch (NoSuchElementException | NumberFormatException e) {
            throw new BuildObjectException("An error occurred during the construction of the object: " + e.getMessage());
        }
    }
}
