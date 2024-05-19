package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Emil
 */
public class Player extends Entity {
    private double lastShotTime = 0; // Time since last shot

    double getLastShotTime() {
        return lastShotTime;
    }

    void setLastShotTime(double lastShotTime) {
        this.lastShotTime = lastShotTime;
    }
}
