package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;

import static org.junit.jupiter.api.Assertions.*;

class CollisionDetectorTest {

    @org.junit.jupiter.api.Test
    void collides() {
        CollisionDetector collisionDetector = new CollisionDetector();
        Entity entity1 = new Entity();
        entity1.setRadius(1);
        entity1.setX(0);
        entity1.setY(0);
        Entity entity2 = new Entity();
        entity2.setRadius(1);
        entity2.setX(0);
        entity2.setY(0);
        assertTrue(collisionDetector.collides(entity1, entity2));
    }
}