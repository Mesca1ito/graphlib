package ru.dgaribov.graphlib;

public class TraverseException extends Exception {
    @Override
    public String getMessage() {
        return "Exception while traversing a graph";
    }
}
