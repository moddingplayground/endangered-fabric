package net.moddingplayground.endangered.api.entity.ai.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.mob.MobEntity;

import java.util.function.Predicate;

public class ConditionalLookAtEntityGoal<T extends MobEntity> extends LookAtEntityGoal {
    private final T genericMob;
    private final Predicate<T> condition;

    public ConditionalLookAtEntityGoal(T mob, Class<? extends LivingEntity> target, float range, Predicate<T> condition) {
        super(mob, target, range);
        this.genericMob = mob;
        this.condition = condition;
    }

    @Override
    public boolean canStart() {
        return this.condition.test(this.genericMob) && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return this.condition.test(this.genericMob) && super.shouldContinue();
    }
}
