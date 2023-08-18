package com.k4rtowka.lab5.CollectionManagers.ModeManager.userMode;

import Models.SpaceMarine;
import com.k4rtowka.lab5.CollectionManagers.IdManager;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.ModeManager;
import com.k4rtowka.lab5.CollectionManagers.validators.*;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

import java.time.ZonedDateTime;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class SpaceMarineCLIManager implements ModeManager<SpaceMarine> {


    @Override
    public SpaceMarine buildObject() throws BuildObjectException {
        try{
            System.out.println("Creating new SpaceMarine object...");
            SpaceMarine spaceMarine = new SpaceMarine();
            Scanner scanner = new Scanner(System.in);
            InputValidator inputValidator = new InputValidator();
            String nextLine;
            System.out.println();

            //id
            spaceMarine.setId(IdManager.generateId());

            //name
            String name;
            Validator<String> nameValidator = new NameValidator();
            inputValidator.canBeNull(false);
            while (true) {
                System.out.print("Enter name: ");
                nextLine = scanner.nextLine();

                if (inputValidator.validate(nextLine)) {
                    name = nextLine;
                    if (nameValidator.validate(name)) {
                        spaceMarine.setName(name);
                        break;
                    }
                } else System.out.println("Input should not be empty!(value is not null)");
            }

            //Coordinates
            CoordinatesCLIManager coordinatesCLIHandler = new CoordinatesCLIManager();
            spaceMarine.setCoordinates(coordinatesCLIHandler.buildObject());

            //CreationDate
//            ZonedDateTime creationDate = ZonedDateTime.now();
//            spaceMarine.setCreationDate(creationDate);

            //health
            Integer health;
            Validator<Integer> healthValidator = new healthValidator();
            inputValidator.canBeNull(true);
            while (true){
                try {
                    System.out.print("Enter health: ");
                    nextLine = scanner.nextLine();
                    if(nextLine.isEmpty() || nextLine.isBlank()) break;
                    health = Integer.parseInt(nextLine);
                    if (healthValidator.validate(health)) {
                        spaceMarine.setHealth(health);
                        break;
                    } else {
                        System.out.println("The value does not match the instructions! Try again.");
                        System.out.println("Instruction: " + healthValidator.getDescr());
                    }
                }catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                }
            }

            //heartCount
            long heartCount;
            Validator<Long> heartCountValidator = new HeartCountValidator();
            inputValidator.canBeNull(false);
            while (true){
                try{
                    System.out.print("Enter heart count (1-3): ");
                    nextLine = scanner.nextLine();
                    if(inputValidator.validate(nextLine)){
                        heartCount = Long.parseLong(nextLine);
                        if(heartCountValidator.validate(heartCount)){
                            spaceMarine.setHeartCount(heartCount);
                            break;
                        }else {
                            System.out.println("The value does not match the instructions! Try again.");
                            System.out.println("Instruction: " + heartCountValidator.getDescr());
                        }
                    }else System.out.println("Input should not be empty!(value is not null)");
                }catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                }
            }

            //category
            CategoryCLIManager categoryCLIManager = new CategoryCLIManager();
            spaceMarine.setCategory(categoryCLIManager.buildObject());

            //MeleeWeapon
            MeleeWeaponCLIManager meleeWeaponCLIManager = new MeleeWeaponCLIManager();
            spaceMarine.setMeleeWeapon(meleeWeaponCLIManager.buildObject());

            //Chapter
            ChapterCLIManager chapterCLIManager = new ChapterCLIManager();
            spaceMarine.setChapter(chapterCLIManager.buildObject());

            return spaceMarine;
        }catch (NoSuchElementException e){
            throw new BuildObjectException("An error occurred during the construction of the object: " + e.getMessage());
        }
    }
}
