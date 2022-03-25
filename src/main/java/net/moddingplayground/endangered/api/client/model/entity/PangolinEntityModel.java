package net.moddingplayground.endangered.api.client.model.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.moddingplayground.endangered.api.Endangered;
import net.moddingplayground.endangered.api.entity.PangolinEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.util.Optional;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@Environment(EnvType.CLIENT)
public class PangolinEntityModel<E extends PangolinEntity> extends AnimatedGeoModel<E> {
    public static final Identifier MODEL = new Identifier(Endangered.MOD_ID, "geo/entity/pangolin/pangolin.geo.json");
    public static final Identifier TEXTURE = new Identifier(Endangered.MOD_ID, "textures/entity/pangolin/pangolin.png");
    public static final Identifier ANIMATION = new Identifier(Endangered.MOD_ID, "animations/entity/pangolin/pangolin.animation.json");

    public PangolinEntityModel() {}

    @SuppressWarnings("unchecked")
    @Override
    public void setLivingAnimations(E entity, Integer id, AnimationEvent predicate) {
        super.setLivingAnimations(entity, id, predicate);

        AnimationProcessor<?> processor = this.getAnimationProcessor();
        EntityModelData data = (EntityModelData) predicate.getExtraDataOfType(EntityModelData.class).get(0);
        Optional.ofNullable(processor.getBone(HEAD)).ifPresent(bone -> {
            bone.setRotationX(data.headPitch * ((float)Math.PI / 180));
            bone.setRotationY(data.netHeadYaw * ((float)Math.PI / 180));
        });
    }

    @Override
    public Identifier getModelLocation(E entity) {
        return MODEL;
    }

    @Override
    public Identifier getTextureLocation(E entity) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationFileLocation(E entity) {
        return ANIMATION;
    }
}
