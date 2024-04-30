package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {

    /**
     * @param gameData
     *          preconditions: Must be non-null. Contains the configurations for the game window.
     *          postconditions: Able to place the instances of the entities within the game window (or outside, if you want that)
     * @param world
     *          preconditions: Must be non-null. Should add and remove entities as the game progresses.
     *          postconditions: When start is called, it should instantiate entities specified in their individual implementation.
     *                          When stop is called it should remove the instance which has been instantiated on start.
     */
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
