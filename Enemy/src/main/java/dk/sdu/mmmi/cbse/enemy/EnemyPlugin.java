package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
        System.out.println("Enemy Spawned");
        enemy.setY(enemy.getY() - 200);
    }

    private Entity createEnemyShip(GameData gameData) {

        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-8,8,-6, 8, -6, 6, -4, 6, -4, 4, 2, 4, 2, 6, 4, 6, 4, 8, 6, 8, 6, 6, 4, 6, 4, 4, 6, 4, 6, 2, 8, 2, 8, 0, 10, 0, 10, -6, 8, -6, 8, -2, 6, -2, 6, -6, 4, -6, 4, -8, 0, -8, 0, -6, 4, -6, 4, -4, -6, -4, -6, -6, -2, -6, -2, -8, -6, -8, -6, -6, -8, -6, -8, -2, -10, -2, -10, -6, -12, -6, -12, 0, -10, 0, -10, 2, -8, 2, -8, 4, -6, 4, -6, 6, -8, 6, -8, 8);
        enemyShip.setX(gameData.getDisplayHeight()/2);
        enemyShip.setY(gameData.getDisplayWidth()/2);
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }
}