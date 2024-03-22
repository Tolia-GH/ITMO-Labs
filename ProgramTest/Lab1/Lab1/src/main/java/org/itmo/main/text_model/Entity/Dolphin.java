package org.itmo.main.text_model.Entity;

import lombok.Getter;
import org.itmo.main.text_model.Attributes.Behavior;
import org.itmo.main.text_model.Attributes.Gender;
import org.itmo.main.text_model.Attributes.Invention;

import java.util.ArrayList;

public class Dolphin extends Animal {
    @Getter
    private static ArrayList<Invention> inventions = new ArrayList<>();
    @Getter
    private static ArrayList<Behavior> behaviors = new ArrayList<>();
    public Dolphin() {
        super();
    }
    public Dolphin(String name, Gender gender, int age) {
        super(name, gender, age);
    }

    public static void setInventions(ArrayList<Invention> inventions) {
        Dolphin.inventions = inventions;
    }

    public static void setBehaviors(ArrayList<Behavior> behaviors) {
        Dolphin.behaviors = behaviors;
    }

    public void invent(Invention i) {
        if (i == null) {
            throw new IllegalArgumentException("Invention can't be null");
        }
        i.setInventor(this);
        Dolphin.inventions.add(i);
    }

    public void learn(Behavior b) {
        if (b == null) {
            throw new IllegalArgumentException("Behavior can't be null");
        }
        Dolphin.behaviors.add(b);
    }

    public boolean isDolphinSmarterThanHuman() {
        return Dolphin.behaviors.size() > Human.getBehaviors().size();
    }
}
