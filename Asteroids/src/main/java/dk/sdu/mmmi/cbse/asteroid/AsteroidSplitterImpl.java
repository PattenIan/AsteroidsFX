package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

/**
 *
 * @author corfixen
 */
public class AsteroidSplitterImpl implements IAsteroidSplitter {

    /**
     * Creates smaller asteroids if a bigger one is destroyed
     * preconditions:
     * - e must be non-null and should be an Asteroid
     * - world must be non-null and should be able to add new entities (Asteroids)
     *
     * postconditions:
     * - returns if e is not an asteroid
     * - calls createSmallerAsteroids method
     * - Based on the original asteroid the new asteroids are half the size and go in opposite directions from eachother
     * - The new asteroids are added to the world.
     */
    @Override
    public void createSplitAsteroid(Entity e, World world) {
        if (!(e instanceof Asteroid))
            return;
        //Cast to e to Asteroid and get radius
        Asteroid ogAsteroid = (Asteroid) e;
        float ogRad = ogAsteroid.getRadius();

        //Instantiate two new asteroids in for loop
        for (int i = 0; i < 2; i++){
            Asteroid newAsteroid;
            if (i == 0) {
                newAsteroid = createSmallerAsteroid(ogRad/2, ogAsteroid, -1);
            } else {
                newAsteroid = createSmallerAsteroid(ogRad/2, ogAsteroid, 1);
            }
            world.addEntity(newAsteroid);
        }
    }

    /**
     * Creates the smaller asteroids
     * Preconditions:
     * - Size should be non-null and be positive
     * - originalAsteroid should be non-null and an asteroid
     * - speedModifier should be not zero.
     *
     * postconditions:
     * - Create a new, randomized, and smaller asteroid based on the previous asteroid
     * - The speedmodifier, damage and health are set, so they're not zero .
     */
    public Asteroid createSmallerAsteroid(float size, Asteroid originalAsteroid, int speedModifier) {
        Random rnd = new Random();
        Asteroid asteroid = new Asteroid();
        asteroid.setRadius(size);
        asteroid.setHealth(1);
        asteroid.setDamage(originalAsteroid.getDamage());
        if(speedModifier == 0){ speedModifier = 1; }
        asteroid.setSpeedModifier(speedModifier);
        float[] rndScales = new float[4];
        for (int i = 0; i < rndScales.length; i++) {
            rndScales[i] = rnd.nextFloat() * 2 + 0.5f;
        }

        asteroid.setPolygonCoordinates(size * rndScales[0], 0,
                size, -size,
                0, -size * rndScales[1],
                -size, -size,
                -size * rndScales[2], 0,
                -size, size,
                0, size * rndScales[3],
                size, size);

        asteroid.setX(originalAsteroid.getX() + rnd.nextInt(20) - 10);
        asteroid.setY(originalAsteroid.getY() + rnd.nextInt(20) - 10);

        return asteroid;
    }

}
