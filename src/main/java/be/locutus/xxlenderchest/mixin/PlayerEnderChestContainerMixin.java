package be.locutus.xxlenderchest.mixin;

import net.minecraft.world.inventory.PlayerEnderChestContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

/**
 * Expands the internal storage of {@link PlayerEnderChestContainer} from 27 slots
 * (vanilla default) to 54 slots (6 rows x 9 columns - the maximum this mod supports).
 *
 * Uses @ModifyArg to safely intercept the argument passed to the super constructor
 * (SimpleContainer). @ModifyConstant on <init> causes bytecode VerifyError because
 * 'this' is not yet initialized when the constant is evaluated.
 */
@Mixin(PlayerEnderChestContainer.class)
public class PlayerEnderChestContainerMixin {

    @ModifyArg(
            method = "<init>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/SimpleContainer;<init>(I)V"
            ),
            index = 0
    )
    private static int xxlenderchest$expandContainerSize(int original) {
        // Always allocate 54 slots (max) so items in higher rows
        // are never lost when the configured row count is reduced.
        return 54;
    }
}
