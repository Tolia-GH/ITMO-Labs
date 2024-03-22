package org.itmo.main.text_model.Attributes;

import lombok.Getter;

@Getter
public class Invention {
    private String name;
    private String description;

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name shouldn't be empty");
        }
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Invention() {

    }

    public Invention(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name shouldn't be empty");
        }
        this.name = name;
    }
}
