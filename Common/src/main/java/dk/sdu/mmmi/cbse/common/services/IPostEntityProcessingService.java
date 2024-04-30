package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IPostEntityProcessingService {
    /**
     * @param gameData
     *          preconditions: Should be non-null and has the current game configurations. Provides the data "configurations"
     *                          such as which keys are pressable and game-screen size.
     *          postconditions: Entities are within game window configurations and able to move around with the provided
     *                          specified keys. The game window sizes can also be configured, while the game is running.
     * @param world
     *          preconditions: Should be non-null. Should contain all the entities which will be instantiated.
     *          postconditions: Add and remove entities as the game progresses.
     */
    void process(GameData gameData, World world);
}
