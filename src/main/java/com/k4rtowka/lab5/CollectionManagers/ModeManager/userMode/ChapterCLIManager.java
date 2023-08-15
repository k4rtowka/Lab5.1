package com.k4rtowka.lab5.CollectionManagers.ModeManager.userMode;

import Models.Chapter;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.ModeManager;
import com.k4rtowka.lab5.CollectionManagers.validators.InputValidator;
import com.k4rtowka.lab5.CollectionManagers.validators.NameValidator;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ChapterCLIManager implements ModeManager<Chapter> {

    @Override
    public Chapter buildObject() throws BuildObjectException {
        try{
            Scanner scanner = new Scanner(System.in);
            InputValidator inputValidator = new InputValidator();
            NameValidator nameValidator = new NameValidator();
            String nextLine;
            String name;
            long marinesCount;
            System.out.println();

            inputValidator.canBeNull(true);
            Chapter chapter = new Chapter();

            //name
            while(true){
                System.out.print("Enter name of chapter : ");
                nextLine = scanner.nextLine();
                if(inputValidator.validate(nextLine)){
                    if(nameValidator.validate(nextLine)){
                        name = nextLine;
                        chapter.setName(name);
                        break;
                    }else return null;
                } //else Not null
            }

            //marinesCount
            while(true){
                try {
                    System.out.print("Enter marines count : ");
                    nextLine = scanner.nextLine();
                    if (inputValidator.validate(nextLine)) {
                        marinesCount = Long.parseLong(nextLine);
                        break;
                    } else System.out.println("Input should not be empty!(value is not null)");
                } catch (InputMismatchException | NumberFormatException e) {
                    System.out.println("Wrong input! Try again.");
                }
            }

            return new Chapter(name, marinesCount);
        }catch (NoSuchElementException e){
            throw new BuildObjectException("Во время конструирования объекта произошла ошибка: " + e.getMessage());
        }
    }
}
