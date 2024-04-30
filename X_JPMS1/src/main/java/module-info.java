import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module X_JPMS {
    requires Common;
    provides IGamePluginService with dk.sdu.mmmi.cbse.JPMS.JPMS;
}