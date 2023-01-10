import java.util.*;
import java.io.*;

/**
 * Represents an object of type Movie.
 * A Movie object has a title, some Actors, and results for the twelve Bechdel tests.
 *
 * @author (Stella K.) & (Maya Lu-Heda, Sooahn Lee, Emily Zhu)
 * @version (28 Nov 2021, 4:35pm) & 12/08/2022
 */
public class Movie implements Comparable<Movie>
{
    private String title;
    private Hashtable<Actor, String> hash;
    private LinkedList<String> names;
    private Vector<String> vec;
    
    /**
     * Constructor for Movie; takes as input only the title of the movie (like "Bad Moms")
     */
    public Movie(String movieTitle) {
        title = movieTitle;
        hash= new Hashtable<Actor, String>();
        names = new LinkedList<String>();
    }
    
    /**
     * Getter for the movie's title; Returns the movie's title
     * @return  The title of this movie
     */
    public String getTitle() {
        return title;   
    }
    
    /**
     * Getter; Returns a Linked List with all the actor names who played in this movie
     * @return  A LinkedList with the names of all the actors who played in this movie
     */
    public LinkedList<String> getActors() {
        return names;
    }
    
    /**
     * Getter; Returns the movie's actors in a Hashtable
     * @return  A Hashtable with all the actors who played in this movie
     */
    public Hashtable<Actor, String> getAllActors() {
        return hash;
    }
    
    /**
     * Getter; returns a Vector with all the Bechdel test results for this movie
     * @return  A Vector with the Bechdel test results for this movie: A test result can be "1" or "0"
     * indicating that this move passed or did not pass the corresponding test.
     */
    public Vector<String> getAllTestResults(){
        return vec;
    }
    
    /**
     * Setter; Populates the testResults vector with "0" and "1"s, each representing the result of
     * the coresponding test on this movie.
     * @param   A string consisting of of 0's and 1's. Each one of these values denotes the result
     * of the corresponding test on this movie
     */
    public void setTestResults(String results) {
        vec = new Vector<String>();
        String[] r = results.split(",");
        for(int i = 0; i < r.length; i++) {
            vec.add(r[i]);
        }
    }
    
    /**
     * Tests this movie object with the input one and determines whether they are equal.
     * 
     * @return true if both objects are movies and have the same title, 
     * false in any other case.
     */
    public boolean equals(Object other) {
        if (other instanceof Movie) {
            return this.title.equals(((Movie) other).title); // Need explicit (Movie) cast to use .title
        } else {
            return false;
        }
    }
    
    /**
     * Takes in a String, formatted as lines are in the input file ("nextBechdel_castGender.txt"),
     * generates an Actor, and adds the object to the actors of this movie.
     * 
     * @param   A String representing the information of each Actor
     * @return  The Actor that was just added to this movie
     */
    public Actor addOneActor (String line){
        String[] s = line.split(",");

        String actorName = s[0];
        String type = s[2];
        String gender = s[4];

        Actor a1 = new Actor(actorName, gender);
        hash.put(a1, type); //adds entry to hashtable
        names.add(actorName);

        return a1;
    }
    
    /**
     * Reads the input file ("nextBechdel_castGender.txt"), and adds all its Actors to this movie.
     * @param   actorsFile - The file containing information on each actor who acted in the movie.
     */
    public void addAllActors(String actorsFile) {
        try {
            Scanner scan = new Scanner(new File(actorsFile));
            scan.nextLine();
            String line = scan.nextLine();
            String[] a = line.split(",");
            String title1 = "\""+ getTitle() + "\"";
            while (scan.hasNextLine()) {
                if(title1.equals(a[0])) {
                    String s = a[1] + "," + a[2] + "," + a[3] + "," + a[4] + "," + a[5] + "";
                    addOneActor(s);
                }
                a = scan.nextLine().split(",");
            }
        } catch (FileNotFoundException error) {
            System.out.println("FileNotFoundException: Issues with opening "  + actorsFile);
        }
    }
    
    /**
     * Determines and returns a feminist score of a movie where a sum of 7 or lower will be assumed to be a feminist movie.
     * The file shows the high-level breakdown of which movies passed and failed where a 0 means the movie failed that test, a 1 means it passed.
     * @return  returns a number that is the feminist score of a movie
     */
    public int feministScore() {
        int score = 0;
        Vector<String> s = getAllTestResults();
        for(int i = 0; i < 13; i++) {
            score += Integer.valueOf(s.get(i));
        }
        return score;
    }
    
    /**
     * This method compares the different feminist scores from the movie test results.
     * 
     * @param m   the movie to be compared
     * @return    a negative integer or a positive integer as this object is less than or greater than the specified object
     */
    public int compareTo(Movie m){
        if(this.feministScore() > m.feministScore()) {
            return -1;   // more than
        } else if(this.feministScore() < m.feministScore()) {
            return 1;  // less than
        } else {     // equal to -- the tiebreaker
            if(this.getActors().size() > m.getActors().size()) {
                return -1;
            } else {
            return 1;
        }
        }
    }
    
    /**
     * Returns a String representation of this Movie
     * @return  a reasonable string representation of this movie: includes the title, the number of actors who
     * played in it, starring actors, and the movie's test results.
     */
    public String toString() {
        if(!this.getActors().isEmpty()){
            String out = getTitle()+ " has " + getActors().size() + " actors, starring " + getActors() + ". Their test results are " + getAllTestResults();
            return out;
        }
        return "There are no actors in this movie.";
    }

    /**
     * Main method for testing this class
     */
    public static void main(String[] args) {
        Movie m1 = new Movie("Boo! A Madea Halloween");
        Movie m2 = new Movie("Hidden Figures");
        Movie m3 = new Movie("Sully");
        Movie m4 = new Movie("The Girl on the Train");
        
        System.out.println("********** TESTING MOVIE **********");
        System.out.println("***** TESTING getTitle() ***** \n");
        System.out.println("Expect: Boo! A Madea Halloween; Got: " + m1.getTitle());
        System.out.println("Expect: Hidden Figures; Got: " + m2.getTitle() + "\n");
        
        System.out.println("***** TESTING addOneActor() ***** \n");
        System.out.println("Expect: Tom Hanks, Male; Got: " + m3.addOneActor("Tom Hanks,Captain Chesley Sully Sullenberger,Leading,1,Male"));
        System.out.println("Expect: Aaron Eckhart, Male; Got: " + m3.addOneActor("Aaron Eckhart,Jeff Skiles,Supporting,2,Male") + "\n");
        System.out.println("Expect: Emily Blunt, Female; Got: " + m4.addOneActor("Emily Blunt,Rachel,Leading,1,Female"));
        System.out.println("Expect: Rebecca Ferguson, Female; Got: " + m4.addOneActor("Rebecca Ferguson,Anna,Supporting,2,Female") + "\n");
        
        System.out.println("***** TESTING getActors() ***** \n");
        System.out.println("Expect: Aaron Eckhart, Tom Hanks; Got: " + m3.getActors());
        System.out.println("Expect: Emily Blunt, Rebecca Ferguson; Got: " + m4.getActors() + "\n");
        
        System.out.println("***** TESTING getAllActors() & addAllActors() ***** \n");
        m1.addAllActors("nextBechdel_castGender.txt");
        m2.addAllActors("nextBechdel_castGender.txt");
        System.out.println(m1.getActors() + "\n");
        System.out.println(m2.getActors() + "\n");
        
        System.out.println("***** TESTING toString() ***** \n");
        System.out.println(m1.toString());
        System.out.println(m2.toString() + "\n");
        
        System.out.println("***** TESTING setTestResults() & getAllTestResults() ***** \n");
        m1.setTestResults("0,0,1,1,1,1,0,1,0,0,1,1,1");
        m2.setTestResults("0,0,0,0,0,1,0,1,0,1,1,1,1");
        System.out.println("Expect: 0,0,1,1,1,1,0,1,0,0,1,1,1; Got: " + m1.getAllTestResults());
        System.out.println("Expect: 0,0,0,0,0,1,0,1,0,1,1,1,1; Got: " + m2.getAllTestResults() + "\n");
        
        System.out.println("***** TESTING feministScore() ***** \n");  
        System.out.println("Expect: 8; Got: " + m1.feministScore());
        System.out.println("Expect: 6; Got: " + m2.feministScore());
        
    }
}