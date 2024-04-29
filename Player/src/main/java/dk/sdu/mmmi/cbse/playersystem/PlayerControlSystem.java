package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService {
//Test
    @Override
    public void process(GameData gameData, World world, double dt) {
        float rotationSpeed = 300; // Degrees the player turns per second
        float movementSpeed = 150; // Units the player moves per second
        double fireRate = 0.2; // How fast the player shoots
        List<Entity> playerList = world.getEntities(Player.class);
        if(playerList.isEmpty()){ // Checks if player exists
            return;
        }
        Player player = (Player) playerList.getFirst();
        // Multiplying by delta time to make the game framerate independent
        if (gameData.getKeys().isDown(GameKeys.LEFT)) {
            player.setRotation(player.getRotation() - rotationSpeed * dt);
        }
        if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
            player.setRotation(player.getRotation() + rotationSpeed * dt);
        }

        if (gameData.getKeys().isDown(GameKeys.UP)) {
            double changeX = Math.cos(Math.toRadians(player.getRotation())) * movementSpeed * dt;
            double changeY = Math.sin(Math.toRadians(player.getRotation())) * movementSpeed * dt;
            player.setX(player.getX() + changeX);
            player.setY(player.getY() + changeY);
        }

        player.setLastShotTime(player.getLastShotTime() + dt);

            // Check if space is pressed and cooldown has elapsed
        if (gameData.getKeys().isDown(GameKeys.SPACE) && player.getLastShotTime() >= fireRate) {
            getBulletSPIs().stream().findFirst().ifPresent(spi -> {
                world.addEntity(spi.createBullet(player, gameData));
                });

                // Reset the cooldown timer
            player.setLastShotTime(0);
        }
        if (player.getX() < 0) {
            player.setX(1);
        }

        if (player.getX() > gameData.getDisplayWidth()) {
            player.setX(gameData.getDisplayWidth()-1);
        }

        if (player.getY() < 0) {
            player.setY(1);
        }

        if (player.getY() > gameData.getDisplayHeight()) {
            player.setY(gameData.getDisplayHeight()-1);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
