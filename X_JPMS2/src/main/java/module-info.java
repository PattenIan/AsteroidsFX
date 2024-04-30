import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module X_JPMS2 {
    requires Common;
    provides IGamePluginService with dk.sdu.mmmi.cbse.JPMS.JPMS;
}