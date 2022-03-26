package net.moddingplayground.endangered.api.client.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.AnimationProcessor;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import java.util.Optional;
import java.util.function.Consumer;

import static net.minecraft.client.render.entity.model.EntityModelPartNames.*;

@Environment(EnvType.CLIENT)
public interface AnimationUtil {
    /* Live Utilities */

    static EntityModelData getEntityModelData(AnimationEvent<?> event) {
        return event.getExtraDataOfType(EntityModelData.class).get(0);
    }

    static void animate(AnimationProcessor<?> processor, String bone, Consumer<IBone> function) {
        Optional.ofNullable(processor.getBone(bone)).ifPresent(function);
    }

    static float calculateAnimationSpeed(LivingEntity entity) {
        float speed = 1.0F;
        if (entity.getRoll() > 4) {
            speed = (float) entity.getVelocity().lengthSquared();
            speed /= 0.2F;
            speed *= speed * speed;
        }
        return Math.max(speed, 1.0F);
    }

    /* Preset Animations */

    static Consumer<IBone> head(AnimationEvent<?> event) {
        EntityModelData data = getEntityModelData(event);
        return bone -> {
            bone.setRotationX(data.headPitch * ((float) Math.PI / 180));
            bone.setRotationY(data.netHeadYaw * ((float) Math.PI / 180));
        };
    }

    static Consumer<IBone> leg(AnimationEvent<?> event, LivingEntity entity, boolean left) {
        float offset = left ? (float) Math.PI : 0;
        return bone -> {
            float speed = calculateAnimationSpeed(entity);
            bone.setRotationX(MathHelper.cos(event.getLimbSwing() * 0.67F + offset) * 1.4F * event.getLimbSwingAmount() / speed);
        };
    }

    static void defaultReactionary(AnimatedGeoModel<?> model, AnimationEvent<?> event, LivingEntity entity) {
        AnimationProcessor<?> processor = model.getAnimationProcessor();
        animate(processor, HEAD, head(event));
        animate(processor, LEFT_LEG, leg(event, entity, true));
        animate(processor, RIGHT_LEG, leg(event, entity, false));
    }

    /* Object Instantiation */

    static Identifier createModel(EntityType<?> type, String... suffix) {
        Identifier id = Registry.ENTITY_TYPE.getId(type);
        String ns = id.getNamespace();
        String su = getEntityAssetPath(type, suffix);
        return new Identifier(ns, "geo/entity/%s.geo.json".formatted(su));
    }

    static Identifier createTexture(EntityType<?> type, String... suffix) {
        Identifier id = Registry.ENTITY_TYPE.getId(type);
        String ns = id.getNamespace();
        String su = getEntityAssetPath(type, suffix);
        return new Identifier(ns, "textures/entity/%s.png".formatted(su));
    }

    static Identifier createAnimation(EntityType<?> type, String... suffix) {
        Identifier id = Registry.ENTITY_TYPE.getId(type);
        String ns = id.getNamespace();
        String su = getEntityAssetPath(type, suffix);
        return new Identifier(ns, "animations/entity/%s.animation.json".formatted(su));
    }

    static String getEntityAssetPath(EntityType<?> type, String... suffix) {
        Identifier id = Registry.ENTITY_TYPE.getId(type);
        String pa = id.getPath();
        String su = String.join("", suffix);
        return "%1$s/%1$s%2$s".formatted(pa, su.isEmpty() ? "" : "_" + su);
    }
}
