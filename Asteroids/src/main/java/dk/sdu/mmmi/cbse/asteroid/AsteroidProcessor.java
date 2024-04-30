package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.ArrayList;
import java.util.List;

public class AsteroidProcessor implements IEntityProcessingService {

    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();

    @Override
    public void process(GameData gameData, World world, double dt) {
        int asteroidSpeed = 200;
        List<Asteroid> asteroids = new ArrayList<>();
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            asteroids.add((Asteroid) asteroid);
        }
        for (Asteroid asteroid : asteroids) {
            if(!asteroid.isAlive()){
                if(asteroid.getRadius() > 10)
                    asteroidSplitter.createSplitAsteroid(asteroid, world);
                world.removeEntity(asteroid);
            }
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation())) + asteroidSpeed * dt * asteroid.getSpeedModifier();
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation())) + asteroidSpeed * dt;

            asteroid.setX(asteroid.getX() + changeX * 0.5);
            asteroid.setY(asteroid.getY() + changeY * 0.5);

            if (asteroid.getX() < 0) {
                asteroid.setX(asteroid.getX() + gameData.getDisplayWidth()); //Changed to '+' so they can actually return to screen, instead of dissapearing
            }

            if (asteroid.getX() > gameData.getDisplayWidth()) {
                asteroid.setX(asteroid.getX() % gameData.getDisplayWidth());
            }

            if (asteroid.getY() < 0) {
                asteroid.setY(asteroid.getY() + gameData.getDisplayHeight()); //Changed to '+' so they can actually return to screen, instead of dissapearing
            }

            if (asteroid.getY() > gameData.getDisplayHeight()) {
                asteroid.setY(asteroid.getY() % gameData.getDisplayHeight());
            }

        }

    }

    /**
     * Dependency Injection using OSGi Declarative Services
     */
    public void setAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = asteroidSplitter;
    }

    public void removeAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = null;
    }


}
