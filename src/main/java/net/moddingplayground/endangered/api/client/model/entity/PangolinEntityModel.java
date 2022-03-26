package net.moddingplayground.endangered.api.client.model.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.moddingplayground.endangered.api.entity.EndangeredEntityType;
import net.moddingplayground.endangered.api.entity.PangolinEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import static net.moddingplayground.endangered.api.client.util.AnimationUtil.*;

@Environment(EnvType.CLIENT)
public class PangolinEntityModel<E extends PangolinEntity> extends AssetAnimatedGeoModel<E> {
    public static final String RETREATED_KEY = "retreated";

    public static final Identifier MODEL_RETREATED = createModel(EndangeredEntityType.PANGOLIN, RETREATED_KEY);
    public static final Identifier ANIMATION_RETREATED = createAnimation(EndangeredEntityType.PANGOLIN, RETREATED_KEY);
    public static final Identifier TEXTURE_RETREATED = createTexture(EndangeredEntityType.PANGOLIN, RETREATED_KEY);

    public PangolinEntityModel(Identifier model, Identifier texture, Identifier animation) {
        super(model, texture, animation);
    }

    @Override
    public void setLivingAnimations(E entity, Integer id, AnimationEvent event) {
        super.setLivingAnimations(entity, id, event);
        defaultReactionary(this, event, entity);
    }

    @Override
    public Identifier getModelLocation(E entity) {
        if (entity.isInSittingPose()) return MODEL_RETREATED;
        return super.getModelLocation(entity);
    }

    @Override
    public Identifier getAnimationFileLocation(E entity) {
        if (entity.isInSittingPose()) return ANIMATION_RETREATED;
        return super.getAnimationFileLocation(entity);
    }

    @Override
    public Identifier getTextureLocation(E entity) {
        if (entity.isInSittingPose()) return TEXTURE_RETREATED;
        return super.getTextureLocation(entity);
    }
}
