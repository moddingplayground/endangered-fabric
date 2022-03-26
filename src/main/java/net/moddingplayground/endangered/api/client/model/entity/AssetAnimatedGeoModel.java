package net.moddingplayground.endangered.api.client.model.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class AssetAnimatedGeoModel<T extends IAnimatable> extends AnimatedGeoModel<T> {
    private final Identifier model, texture, animation;

    public AssetAnimatedGeoModel(Identifier model, Identifier texture, Identifier animation) {
        this.model = model;
        this.texture = texture;
        this.animation = animation;
    }

    @Override
    public Identifier getModelLocation(T object) {
        return this.model;
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return this.texture;
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return this.animation;
    }
}
