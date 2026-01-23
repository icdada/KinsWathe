package org.BsXinQin.kinswathe.mixin.host;

import dev.doctor4t.wathe.api.Role;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(Role.class)
public class SprintTimeFixMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void SprintTimeFix(
            Identifier identifier,
            int color,
            boolean isInnocent,
            boolean canUseKiller,
            Role.MoodType moodType,
            int maxSprintTime,
            boolean invincibleToPoison,
            CallbackInfo ci
    ) {
        if (maxSprintTime == Integer.MAX_VALUE) {
            try {
                Role roleInstance = (Role) (Object) this;
                Field maxSprintTimeField = null;
                Class<?> currentClass = roleInstance.getClass();
                while (currentClass != null) {
                    try {
                        maxSprintTimeField = currentClass.getDeclaredField("maxSprintTime");
                        break;
                    } catch (NoSuchFieldException e) {
                        currentClass = currentClass.getSuperclass();
                    }
                }
                if (maxSprintTimeField != null) {
                    maxSprintTimeField.setAccessible(true);
                    maxSprintTimeField.set(roleInstance, -1);
                }
            } catch (Exception ignored) {}
        }
    }
}