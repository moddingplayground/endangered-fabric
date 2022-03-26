package net.moddingplayground.endangered.api.entity.ai.goal;

import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.mob.MobEntity;

import java.util.function.Predicate;

public class ConditionalLookAroundGoal<T extends MobEntity> extends LookAroundGoal {
    private final T genericMob;
    private final Predicate<T> condition;

    public ConditionalLookAroundGoal(T mob, Predicate<T> condition) {
        super(mob);
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
