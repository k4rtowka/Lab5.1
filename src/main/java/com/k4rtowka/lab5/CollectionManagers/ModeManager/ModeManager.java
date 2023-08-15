package com.k4rtowka.lab5.CollectionManagers.ModeManager;

import com.k4rtowka.lab5.Exceptions.BuildObjectException;

public interface ModeManager<T> {

    T buildObject() throws BuildObjectException;
}
