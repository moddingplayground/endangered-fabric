package net.moddingplayground.endangered.impl.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.moddingplayground.endangered.api.client.model.entity.PangolinEntityModel;
import net.moddingplayground.endangered.api.entity.PangolinEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class PangolinEntityRenderer extends GeoEntityRenderer<PangolinEntity> {
    public PangolinEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PangolinEntityModel<>());
    }
}
