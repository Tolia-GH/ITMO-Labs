package org.itmo.main.text_model.Entity;

import org.itmo.main.text_model.Attributes.Behavior;
import org.itmo.main.text_model.Attributes.Gender;
import org.itmo.main.text_model.Attributes.Invention;

import java.util.ArrayList;

public class Human extends Animal {
    private static ArrayList<Invention> inventions;
    private static ArrayList<Behavior> behaviors;
    public Human() {
        super();
    }
    public Human(String name, Gender gender, int age) {
        super(name, gender, age);
    }


}
