package org.itmo.main.text_model.Entity;

import org.itmo.main.text_model.Attributes.Behavior;
import org.itmo.main.text_model.Attributes.Gender;
import org.itmo.main.text_model.Attributes.Invention;

import java.util.ArrayList;

public class Dolphin extends Animal {

    private String name;
    private Gender gender;
    private int age;
    private ArrayList<Invention> inventions;
    private ArrayList<Behavior> behaviors;
    private int IQ;
    public Dolphin() {
        super();
    }
    public Dolphin(String name, Gender gender, int age) {
        super(name, gender, age);
    }
}
