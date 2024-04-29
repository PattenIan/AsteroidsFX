package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world, double deltaTime) {
        Random randint = new Random();
        float moveSpeed = 100f;
        float fireRate = 0.5f;

        for(Entity enemy : world.getEntities(Enemy.class)){
            double changeX = Math.cos(Math.toRadians(enemy.getRotation())) * moveSpeed * deltaTime;
            double changeY = Math.sin(Math.toRadians(enemy.getRotation())) * moveSpeed * deltaTime;
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            enemy.setRotation(enemy.getRotation() + (randint.nextDouble(5)-2) * moveSpeed * deltaTime);

            ((Enemy) enemy).setLastShotTime(((Enemy) enemy).getLastShotTime() + deltaTime);

            // Check if space is pressed and cooldown has elapsed
            if (((Enemy) enemy).getLastShotTime() >= fireRate) {
                getBulletSPIs().stream().findFirst().ifPresent(spi -> {
                    world.addEntity(spi.createBullet(enemy, gameData));
                });

                // Reset the cooldown timer
                ((Enemy) enemy).setLastShotTime(0);
            }


            if (enemy.getX() < 0) {
                enemy.setX(1);
            }

            if (enemy.getX() > gameData.getDisplayWidth()) {
                enemy.setX(gameData.getDisplayWidth()-5);
            }

            if (enemy.getY() < 0) {
                enemy.setY(1);
            }

            if (enemy.getY() > gameData.getDisplayHeight()) {
                enemy.setY(gameData.getDisplayHeight()-5);
            }

        }
    }
    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

