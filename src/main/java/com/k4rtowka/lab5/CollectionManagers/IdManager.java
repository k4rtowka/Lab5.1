package com.k4rtowka.lab5.CollectionManagers;

import Models.SpaceMarine;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class IdManager {

    private static Queue<Integer> queue;

    static {
        queue = new LinkedList<>();
    }

    public static Integer generateId() {
        Integer id;
        do {
            id = new Random().nextInt(1, Integer.MAX_VALUE);
        } while (queue.contains(id));
        queue.add(id);
        return id;
    }

    public static boolean isNotNumeric(String str) {
        return !str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static Integer validateInput(String input){
        if(IdManager.isNotNumeric(input)){
            System.out.println("Provided argument id: \"" + input + "\" is not a number! Try again.");
            return null;
        } else if (input.contains(".") || input.contains("-")) {
            System.out.println("ID field cannot accept decimal/negative values. Try again.");
            return null;
        }

        Integer id = null;
        try {
            id = Integer.valueOf(input);
        } catch (NumberFormatException e) {
            System.out.println("Provided argument: \"" + input + "\" is too large for ID field. Try again.");
        }
        return id;
    }

    public static SpaceMarine checkById(Integer id) {
        CollectionManager cm = CollectionManager.getInstance();
        for (SpaceMarine spaceMarine : cm.getSpaceMarineMap().values()) {
            if (spaceMarine.getId().equals(id))
                return spaceMarine;
        }
        return null;
    }
}
