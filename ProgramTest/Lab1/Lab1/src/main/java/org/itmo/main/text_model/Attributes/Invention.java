package org.itmo.main.text_model.Attributes;

import lombok.Getter;
import org.itmo.main.text_model.Entity.Animal;

@Getter
public class Invention {
    private String name;
    private String description;
    private Animal inventor;

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name shouldn't be null");
        }
        this.name = name;
    }

    public void setInventor(Animal inventor) {
        if (inventor == null) {
            throw new IllegalArgumentException("Inventor shouldn't be null");
        }
        this.inventor = inventor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Invention() {

    }

    public Invention(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name shouldn't be null");
        }
        this.name = name;
    }
}
