package net.moddingplayground.endangered.impl.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.moddingplayground.endangered.api.client.model.entity.PangolinEntityModel;
import net.moddingplayground.endangered.api.entity.EndangeredEntityType;
import net.moddingplayground.endangered.api.entity.PangolinEntity;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import static net.moddingplayground.endangered.api.client.util.AnimationUtil.*;

@Environment(EnvType.CLIENT)
public class PangolinEntityRenderer extends GeoEntityRenderer<PangolinEntity> {
    public static final Identifier ANIMATION = createAnimation(EndangeredEntityType.PANGOLIN);
    public static final Identifier TEXTURE = createTexture(EndangeredEntityType.PANGOLIN);
    public static final Identifier MODEL = createModel(EndangeredEntityType.PANGOLIN);

    public PangolinEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PangolinEntityModel<>(MODEL, TEXTURE, ANIMATION));
    }
}
