package com.k4rtowka.lab5.CollectionManagers.ModeManager.userMode;

import Models.AstartesCategory;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.ModeManager;
import com.k4rtowka.lab5.CollectionManagers.validators.InputValidator;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

import java.util.Scanner;

public class CategoryCLIManager implements ModeManager<AstartesCategory> {


    @Override
    public AstartesCategory buildObject() throws BuildObjectException {
        Scanner scanner = new Scanner(System.in);
        InputValidator inputValidator = new InputValidator();
        System.out.println();

        inputValidator.canBeNull(false);
        EnumRequester<AstartesCategory> enumRequester = new EnumRequester<>();
        AstartesCategory astartesCategory = enumRequester.requestEnum(AstartesCategory.values(), AstartesCategory.class.getSimpleName(), scanner, inputValidator);
        return astartesCategory;
    }
}
