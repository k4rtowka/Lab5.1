package com.k4rtowka.lab5.CommandManagers;

import com.k4rtowka.lab5.Exceptions.BuildObjectException;

import javax.xml.bind.JAXBException;

public interface CommandInterface {
    /**
     Executes the command.
     */
    void execute() throws BuildObjectException;

    /**
     * Base method for show command name
     *
     * @return command name
     */
    String getName();

    /**
     * Base method for show command description.
     *
     * @return command description
     */
    String getDescr();

    /**
     Checks if the provided argument is valid for the command.
     @param argument the argument to check for validity
     @return true if the argument is valid, false otherwise
     */
    boolean checkArgument(Object argument);
}
