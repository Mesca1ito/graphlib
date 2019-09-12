package ru.dgaribov;

import ru.dgaribov.graphlib.Vertex;

import java.util.Objects;

/**
 * A simple string valued vertex for test purpose
 */
public class SimpleLabelVertex implements Vertex {

    private String label;

    SimpleLabelVertex(String label) {
        this.label = label;
    }

    @Override
    public Object getValue() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleLabelVertex that = (SimpleLabelVertex) o;

        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return label != null ? label.hashCode() : 0;
    }
}
