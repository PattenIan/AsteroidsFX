package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.io.IOException;
import java.net.ConnectException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class CollisionDetector implements IPostEntityProcessingService {
    HttpClient httpClient = HttpClient.newHttpClient();
    public CollisionDetector() {
    }

    @Override
    public void process(GameData gameData, World world) {
        boolean scoreUpdated = false;
        int points = 0;
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID())) {
                    continue;                    
                }

                // CollisionDetection
                if (this.collides(entity1, entity2)) {
                    if (entity1 instanceof Bullet && entity2 instanceof Asteroid) {
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                        points = 10;
                        scoreUpdated = true;
                        getAsteroidsSplitters().stream().findFirst().ifPresent(spi -> spi.createSplitAsteroid(entity2, world));
                    }
                    else if (entity2 instanceof Bullet && entity1 instanceof Asteroid) {
                        world.removeEntity(entity2);
                        world.removeEntity(entity1);
                        points = 10;
                        scoreUpdated = true;
                        this.getAsteroidsSplitters().stream().findFirst().ifPresent(spi -> spi.createSplitAsteroid(entity1, world));
                    }
                    else if (entity1.getClass().equals(entity2.getClass())) {
                        return;
                    } else {
                        entity1.takeDamage(entity2.getDamage());
                        entity2.takeDamage(entity1.getDamage());
                        if(!entity1.isAlive()){
                            world.removeEntity(entity1);
                        }
                        if(!entity2.isAlive()){
                            world.removeEntity(entity2);
                        }
                    }
                }
                if (scoreUpdated){
                    HttpRequest httpRequest = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/score/update/" + points))
                            .PUT(HttpRequest.BodyPublishers.ofString(""))
                            .build();
                    try {
                        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                        System.out.println(httpResponse.body());
                    } catch (IOException | InterruptedException e) {

                    }
                    finally{
                        scoreUpdated = false;
                    }
                }
            }
        }
    }

    protected Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
    private Collection<? extends IAsteroidSplitter> getAsteroidsSplitters() {
        return ServiceLoader.load(IAsteroidSplitter.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
