package net.moddingplayground.endangered.impl.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.moddingplayground.endangered.api.Endangered;
import net.moddingplayground.endangered.api.entity.EndangeredEntityType;
import net.moddingplayground.endangered.impl.client.render.entity.PangolinEntityRenderer;
import net.moddingplayground.frame.api.util.InitializationLogger;

@Environment(EnvType.CLIENT)
public final class EndangeredClientImpl implements Endangered, ClientModInitializer {
    private static EndangeredClientImpl instance;
    private final InitializationLogger initializer;

    public EndangeredClientImpl() {
        this.initializer = new InitializationLogger(LOGGER, MOD_NAME, EnvType.CLIENT);
        instance = this;
    }

    @Override
    public void onInitializeClient() {
        this.initializer.start();

        EntityRendererRegistry.register(EndangeredEntityType.PANGOLIN, PangolinEntityRenderer::new);

        this.initializer.finish();
    }

    public static EndangeredClientImpl getInstance() {
        return instance;
    }
}
