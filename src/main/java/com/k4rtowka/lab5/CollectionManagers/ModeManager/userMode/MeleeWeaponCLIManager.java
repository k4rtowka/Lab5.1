package com.k4rtowka.lab5.CollectionManagers.ModeManager.userMode;

import Models.MeleeWeapon;
import com.k4rtowka.lab5.CollectionManagers.ModeManager.ModeManager;
import com.k4rtowka.lab5.CollectionManagers.validators.InputValidator;
import com.k4rtowka.lab5.Exceptions.BuildObjectException;

import java.util.Scanner;

public class MeleeWeaponCLIManager implements ModeManager<MeleeWeapon> {

    @Override
    public MeleeWeapon buildObject() throws BuildObjectException {
        Scanner scanner = new Scanner(System.in);
        InputValidator inputValidator = new InputValidator();
        System.out.println();

        inputValidator.canBeNull(true);
        EnumRequester<MeleeWeapon> enumRequester = new EnumRequester<>();
        System.out.println("You can press \"enter\" to skip this step");
        MeleeWeapon meleeWeapon = enumRequester.requestEnum(MeleeWeapon.values(), MeleeWeapon.class.getSimpleName(), scanner, inputValidator);
        return meleeWeapon;
    }
}
