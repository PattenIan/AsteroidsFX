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

    @Override
    public void start(GameData gameData, World world) {
        Entity asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
        System.out.println(asteroid.getRadius());
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        asteroid.setHealth(1);
        asteroid.setDamage(1000);
        Random rnd = new Random();
        Random rndMultiplier = new Random();
        int size = rnd.nextInt(10) + 20;
        float rndScale;
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
        asteroid.setX(0);
        asteroid.setY(0);
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(90));
        return asteroid;
    }
}
