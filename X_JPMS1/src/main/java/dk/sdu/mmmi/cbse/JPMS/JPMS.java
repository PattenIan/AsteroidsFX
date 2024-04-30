package dk.sdu.mmmi.cbse.JPMS;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class JPMS implements IGamePluginService {
    private int counter;
    private JPMS instance;
    public JPMS(){
        this.counter = 1;
    }

    @Override
    public void start(GameData gameData, World world) {
        System.out.println("Starting JPMS1");
        instance = new JPMS();
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
}
