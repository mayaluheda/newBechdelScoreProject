
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Represents an object of type Actor. An Actor has a name and a gender.
 *
 * @author (Stella K.) & sooahnlee & maya lu-heda & emily zhu
 * @version (28 Nov 2021, 4:35pm)
 */
public class Actor
{
    private String name;
    private String gender;

    /**
     * Constructor for Actor
     */
    public Actor(String actorName, String actorGender) {
        name = actorName;
        gender = actorGender;
    }
    
    /**
     * Getter for the name of the actor; Returns the name of this actor
     * @return  The name of this actor
     */
    public String getName() {
        return name;
    }
    
    /**
     * Setter for the name of the actor; Sets the name of this actor
     * @param   The - name of this actor
     */
    public void setName(String n) {
        name = n;
    }
    
    /**
     * Getter for the gender of the actor; Returns the gender of this actor
     * @return  The gender of this actor
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * Setter for the gender of the actor; Sets the gender of this actor
     * @param   The - gender of this actor
     */
    public void setGender(String g) {
        gender = g;
    }
    
    /**
     * Returns a String representation of this Actor
     * @return  a reasonable string representation of this actor, containing their name and gender.
     */
    public String toString() {
        String s = "Actor's Name: " + name + ", Actor's Gender: " + gender + "\n";
        return s;
    }
    
    /**
     * This method is defined here because Actor (mutable) is used as a key in a Hashtable.
     * It makes sure that same Actors have always the same hash code.
     * So, the hash code of any object taht is used as key in a hash table,
     * has to be produced on an *immutable* quantity,
     * like a String (such a string is the name of the actor in our case)
     * 
     * @return an integer, which is the has code for the name of the actor
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Tests this actor against the input one and determines whether they are equal.
     * Two actors are considered equal if they have the same name and gender.
     * 
     * @return true if both objects are of type Actor, 
     * and have the same name and gender, false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Actor) {
            return this.name.equals(((Actor) other).name) && 
            this.gender.equals(((Actor) other).gender); // Need explicit (Actor) cast to use .name
        } else {
            return false;
        }
    }

    /**
     * Main method for testing this class
     */
    public static void main(String[] args) {
        Actor a1 = new Actor("Jennifer Lawrence", "Female");
        Actor a2 = new Actor("Ryan Renolds", "Male");
        Actor a3 = new Actor("Anne Hathaway", "Female");
        Actor a4 = new Actor("Tom Cruise", "Male");
        Actor a5 = new Actor("Angelina Jolie", "Female");
        Actor a6 = new Actor("Leonardo DiCaprio", "Male");
        
        System.out.println("********** TESTING ACTOR **********");
        System.out.println("***** TESTING toString() ***** \n");
        System.out.println("Actor #1");
        System.out.println(a1.toString());
        System.out.println("Actor #2");
        System.out.println(a2.toString());
        System.out.println("Actor #3");
        System.out.println(a3.toString());
        System.out.println("Actor #4");
        System.out.println(a4.toString());
        System.out.println("Actor #5");
        System.out.println(a5.toString());
        System.out.println("Actor #6");
        System.out.println(a6.toString());
        
        System.out.println("***** TESTING Getters & Setters ***** \n");
        System.out.println("Expecting: Jennifer Lawrence, Got: " + a1.getName() + "\n");
        System.out.println("Expecting: Male, Got: " + a2.getGender() + "\n");
        a3.setGender("Male");
        System.out.println("Expecting: Male, Got: " + a3.getGender() + "\n");
        a4.setName("Anne Hathaway");
        System.out.println("Expecting: Anne Hathaway, Got: " + a4.getName() + "\n");
        a5.setName("Leonardo DiCaprio");
        System.out.println("Expecting: Leonardo DiCaprio, Got: " + a5.getName() + "\n");
        a5.setGender("Male");
        System.out.println("Expecting: Male, Got: " + a5.getGender() + "\n");
        System.out.println("New Actor #5");
        System.out.println(a5.toString());
        a6.setName("Angelina Jolie");
        System.out.println("Expecting: Angelina Jolie, Got: " + a6.getName() + "\n");
        a6.setGender("Female");
        System.out.println("Expecting: Female, Got: " + a6.getGender() + "\n");
        System.out.println("New Actor #6");
        System.out.println(a6.toString());
    }
}