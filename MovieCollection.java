import java.util.*;
import javafoundations.PriorityQueue;
import java.util.*;
import java.io.*;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * Write a description of class MovieCollection here.
 * The MovieCollection class represents a collection of movie objects. It uses a LinkedList to hold these objects.
 *
 * @authors (Maya Lu-Heda, Sooahn Lee, Emily Zhu)
 * @version (12/10/2022)
 */
public class MovieCollection
{
    private LinkedList<Actor> allActors; //linked list of Actor objects
    private LinkedList<Movie> allMovies; //linked list of Movie object
    private String testFile;
    private String castsFile;
    
    /**
     * Constructor for objects of class MovieCollection
     */
    public MovieCollection(String testsFileName, String castsFileName)
    {
        allActors = new LinkedList<Actor>();
        allMovies = new LinkedList<Movie>();

        testFile = testsFileName;
        castsFile = castsFileName;
    }

    /**
     * Returns all the movies in a LinkedList 
     * @return Returns a LinkedList with all the movies, each complete with its title, actors and Bechdel test results.
     */
    public LinkedList<Movie> getMovies() {
        return allMovies; 
    }

    /**
     * Returns the titles of all movies in the collection
     * @ Returns a Linked List with movie titles
     */
    public LinkedList<String> getMovieTitles() {
        LinkedList<String> movieTitles = new LinkedList<String>();
        for(int i=0; i<allMovies.size(); i++) {
            Movie m = allMovies.get(i);     //gets the movie
            movieTitles.add(m.getTitle());      //gets the title, adds it to the Linked List
        }
        return movieTitles;
    }

    /**
     * Returns all of the Actors in the collection
     * @return Returns a LinkedList with all the Actors, each complete with their name and gender.
     */
    public LinkedList<Actor> getActors() {
        return allActors;
    }

    /**
     * Returns the names of all actors in the collection
     * @ return Returns a LinkedList with the names of all actors
     */
    public LinkedList<String> getActorNames() {
        LinkedList<String> actorNames = new LinkedList<String>();
        for(int i=0; i<allActors.size(); i++) {
            Actor a = allActors.get(i);
            actorNames.add(a.getName());
        }
        return actorNames;
    }

    /**
     * Returns a String representing this MovieCollection
     * @ return Returns a String representation of this collection, including the number of movies and the movies themselves.
     */
    public String toString() {
        String out;
        out = "We have these movies " + getMovieTitles() + " starring these actors: " + getActorNames();
        return out;
    }

    /**
     * Reads the input file, and uses its first column (movie title) to create all movie objects.
     * Adds the included information on the Bachdel test results to each movie.
     */
    private void readMovies() {
        //use indexOf to find first index of the comma
        try{
            Scanner fileScan = new Scanner (new File (testFile));
            String line = fileScan.nextLine();
            while (fileScan.hasNext()){
                line = fileScan.nextLine();
                int index = line.indexOf(","); //index of first occurence of comma
                String movieTitle = line.substring(0, index);
                String results = line.substring(index + 1, line.length());
                String[] r = line.split(",");
                Movie m = new Movie(movieTitle);
                m.setTestResults(results);
                allMovies.add(m);
            }
        }catch (FileNotFoundException error){
            System.out.println("Couldn't open " + testFile);
        }
    }

    /**
     * Reads the casts for each movie, from input casts file.
     * 1) each movie will appear in (potentially) many consecutive lines, one line per actor.
     * 2) Each token (title, actor name, etc) appears in double quotes, which need to be removed as soon as the tokes are read.
     * 3) If a movie does not have any test results, it is ignored and not included in the collection.
     */
    private void readCasts() {
        Hashtable<Actor, String> h1 = new Hashtable<Actor, String>();
        for (int i=0; i<allMovies.size(); i++) {
            Movie m = allMovies.get(i);
            m.addAllActors(castsFile);
            h1 = m.getAllActors();
            Enumeration<Actor> r = h1.keys(); //gets all of the keys of the hash
            Actor a1;
            String name;
            String gender;
            while(r.hasMoreElements()) {
                if(!allActors.contains(r)){ // --> need to include no results case  <---
                    a1 = r.nextElement(); //first make an actor
                    name = a1.getName(); 
                    gender = a1.getGender();
                    name = name.substring(1, name.length()-1); //use substring to remove the quotation marks
                    gender = gender.substring(1, gender.length()-1); //use substring to remove the quotation marks
                    a1.setName(name); 
                    a1.setGender(gender);
                    allActors.add(a1); //re-add the actor, now without quotes
                }
            }
        }
    }

    /**
     * Returns a list of all Movies that pass the n-th Bechdel test
     * @param   n - integer identifying the n-th test in the list of 12 Bechdel alternatives, starting from zero
     * @ return Returns a list of all Movies which have passed the n-th test
     */
    public LinkedList<Movie> findAllMoviesPassedTestNum(int n) {
        LinkedList<Movie> passed = new LinkedList<Movie>();
        for(int i=0; i<allMovies.size(); i++) {
            Movie m = allMovies.get(i);     //gets a specific movie
            Vector<String> results = m.getAllTestResults();
            String r = results.get(n - 1);      //gets the result at specified index
            if (r.equals("0")) {
                passed.add(m);
            }
        }
        return passed;
    }
    
    /**
     * Returns a PriorityQueue of movies in the provided data based on their feminist score.
     * After enqueuing all the movies, they will be dequeued in priority order: from most feminist to least feminist.
     * @return  Returns a Priority Queue of movies ranked based on their feminist score
     */
    public PriorityQueue rankMovies() {
        PriorityQueue pq = new PriorityQueue();
        for(int i = 0; i < allMovies.size(); i++) {
            pq.enqueue(allMovies.get(i));
        }
        return pq;
    }

    /**
     * Main method for testing this class
     */
    public static void main (String[] args) {
        MovieCollection mC1 = new MovieCollection("nextBechdel_allTests.txt", "nextBechdel_castGender.txt");
        mC1.readMovies();
        mC1.readCasts();
        
        System.out.println("***** TESTING getMovies() *****");
        System.out.println(mC1.getMovies() + "\n");
        
        System.out.println("***** TESTING getMovieTitles() ***** ");
        System.out.println(mC1.getMovieTitles() + "\n");
        
        System.out.println("***** TESTING getActors() ***** "); //--> weird formatting
        System.out.println(mC1.getActors());
        
        System.out.println("***** TESTING getActorNames() *****");
        System.out.println(mC1.getActorNames() + "\n");
        
        System.out.println("***** TESTING getActorNames() *****");
        System.out.println("Movies that passed the Bechdel Test. Expect len: 32 movies");
        System.out.println(mC1.findAllMoviesPassedTestNum(1).size() + " movies: " + mC1.findAllMoviesPassedTestNum(1) + "\n"); 
        
        System.out.println("Movies that passed the Rees-Davies Test. Expect len: 15 movies");
        System.out.println(mC1.findAllMoviesPassedTestNum(13).size() + " movies: " + mC1.findAllMoviesPassedTestNum(13) ); 
        
        System.out.println("Find all movies that passed Bechdel test: ");
        System.out.println(mC1.findAllMoviesPassedTestNum(1).size() + " movies: " + mC1.findAllMoviesPassedTestNum(1) + "\n"); 
        
        System.out.println("Find all movies that passed Peirce or the Landau test: ");
        int i = 0;
        LinkedList<Movie> lm1 = new LinkedList<Movie>();
        while(i < mC1.getMovies().size()) {
            Movie item = mC1.allMovies.get(i);
            Vector<String> results = item.getAllTestResults();
            if(results.get(1).equals("0") || results.get(2).equals("0")) {
              lm1.add(mC1.getMovies().get(i));  
            } 
            i++;
        }
        System.out.println(lm1.size()+ " Movies: " + lm1 + "\n");
    
        System.out.println("Find all movies that passed White test and did not pass Rees-Davies: ");
        int j = 0;
        LinkedList<Movie> lm2 = new LinkedList<Movie>();
        while(j < mC1.getMovies().size()) {
            Movie item= mC1.getMovies().get(j);
            if(item.getAllTestResults().get(11).equals("0") && item.getAllTestResults().get(12).equals("1")) {
              lm2.add(mC1.getMovies().get(j));  
            } 
            j++;
        }
        System.out.println(lm2.size() + " Movies: " + lm2);
        
        PriorityQueue p1 = mC1.rankMovies();
        for(int k = 0; k < mC1.getMovies().size(); k++) {
            System.out.println(p1.dequeue());
        }
        
    }
}