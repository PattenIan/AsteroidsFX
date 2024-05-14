package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;

/**
 *
 * @author corfixen
 */
public class AsteroidPlugin implements IGamePluginService {

    /**
     * Starts the asteroid module by creating and adding an asteroid to the world.
     * @param gameData preconditions: Must be non-null. Contains the configurations for the game window.
     *                 postconditions: An asteroid is created with game configuraitons.
     * @param world    preconditions: Must be non-null. Should add entities of class Asteroids.
     *                 postconditions: Astroid is added to the world
     */
    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
        System.out.println(asteroid.getRadius() + " asteroid radius");
    }

    /**
     * Stops the asteroid module by removing all asteroid entities from the world.
     * @param gameData preconditions: Must be non-null. Contains the configurations for the game window.
     *                 (Not used in this instance, but has to be there because of the interface)
     *                 postconditions: Able to place the instances of the entities within the game window (or outside, if you want that)
     * @param world    preconditions: Must be non-null and contain astroids.
     *                 postconditions: Remove all instances of astroids
     */
    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    /**
     * Creates an asteroid with random attributes based on the game's data.
     * Preconditions:
     *  - gameData must not be null.
     * Postconditions:
     *  - Returns an asteroid entity with random position, size, and rotation.
     *  - The asteroid has set health and damage values.
     */
    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        asteroid.setHealth(1);
        asteroid.setDamage(1000);
        Random rnd = new Random();
        Random rndMultiplier = new Random();
        int size = rnd.nextInt(10) + 20;
        float[] rndScales = new float[4];
        for (int i = 0; i < rndScales.length; i++) {
            rndScales[i] = rndMultiplier.nextFloat(2)+0.5f;
        }
        asteroid.setPolygonCoordinates(size * rndScales[0], 0,
                size, -size,
                0, -size * rndScales[1],
                -size, -size,
                -size * rndScales[2], 0,
                -size, size,
                0, size * rndScales[3],
                size, size);
        asteroid.setX(rnd.nextFloat(gameData.getDisplayWidth()));
        asteroid.setY(0);
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(90));
        return asteroid;
    }
}
