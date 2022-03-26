package net.moddingplayground.endangered.api.client.model.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.moddingplayground.endangered.api.entity.PangolinEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import static net.moddingplayground.endangered.api.client.util.AnimationUtil.*;

@Environment(EnvType.CLIENT)
public class PangolinEntityModel<E extends PangolinEntity> extends AssetAnimatedGeoModel<E> {
    public PangolinEntityModel(Identifier model, Identifier texture, Identifier animation) {
        super(model, texture, animation);
    }

    @Override
    public void setLivingAnimations(E entity, Integer id, AnimationEvent event) {
        super.setLivingAnimations(entity, id, event);
        defaultReactionary(this, event, entity);
    }
}
