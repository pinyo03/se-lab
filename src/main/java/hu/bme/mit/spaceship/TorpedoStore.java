package hu.bme.mit.spaceship;

import java.util.Random;

/**
* Class storing and managing the torpedoes of a ship
*
* (Deliberately contains bugs.)
*/
public class TorpedoStore {

  // rate of failing to fire torpedos [0.0, 1.0]
  private double FAILURE_RATE = 0.0; //NOSONAR

  private int torpedoCount = 0;

  //Moved the Random generator instance to a private field in the class, instead of creating a new instance each time the fire method is called.
  private Random generator = new Random();

  public TorpedoStore(int numberOfTorpedos){
    this.torpedoCount = numberOfTorpedos;

    // update failure rate if it was specified in an environment variable
    String failureEnv = System.getenv("IVT_RATE");
    if (failureEnv != null){
      try {
        FAILURE_RATE = Double.parseDouble(failureEnv);
      } catch (NumberFormatException nfe) {
        FAILURE_RATE = 0.0;
      }
    }
  }

  public boolean fire(int numberOfTorpedos){
    // Added a try-catch block to the fire method, so that invalid arguments (number of torpedoes to fire) now throw and catch an IllegalArgumentException, returning false instead of silently failing.
    try{
      if(numberOfTorpedos < 1 || numberOfTorpedos > this.torpedoCount){
        throw new IllegalArgumentException("numberOfTorpedos");
      }}    
    catch(IllegalArgumentException e){
      return false;
    }

    boolean success = false;

    // simulate random overheating of the launcher bay which prevents firing
    double r = generator.nextDouble();

    if (r >= FAILURE_RATE) {
      // successful firing
      // Fixed the torpedo count decrement operation in the fire method from this.torpedoCount =- numberOfTorpedos (which incorrectly sets the count to negative) to this.torpedoCount -= numberOfTorpedos, ensuring the count is reduced correctly.
      this.torpedoCount -= numberOfTorpedos;
      success = true;
    } else {
      // simulated failure
      success = false;
    }

    return success;
  }

  public boolean isEmpty(){
    return this.torpedoCount <= 0;
  }

  public int getTorpedoCount() {
    return this.torpedoCount;
  }
}
