package org.itmo.main.text_model.Entity;

import org.itmo.main.text_model.Attributes.Behavior;
import org.itmo.main.text_model.Attributes.Gender;
import org.itmo.main.text_model.Attributes.Invention;

import java.util.ArrayList;

public class Human extends Animal {
    private static ArrayList<Invention> inventions = new ArrayList<>();
    private static ArrayList<Behavior> behaviors = new ArrayList<>();
    public Human() {
        super();
    }
    public Human(String name, Gender gender, int age) {
        super(name, gender, age);
    }

    public static void setInventions(ArrayList<Invention> inventions) {
        Human.inventions = inventions;
    }

    public static void setBehaviors(ArrayList<Behavior> behaviors) {
        Human.behaviors = behaviors;
    }

    public void invent(Invention i) {
        if (i == null) {
            throw new IllegalArgumentException("Invention can't be null");
        }
        i.setInventor(this);
        Human.inventions.add(i);
    }

    public void learn(Behavior b) {
        if (b == null) {
            throw new IllegalArgumentException("Behavior can't be null");
        }
        Human.behaviors.add(b);
    }
}
