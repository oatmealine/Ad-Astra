package earth.terrarium.ad_astra.blocks.machines.entity;

import dev.architectury.registry.fuel.FuelRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.registry.ModBlockEntities;
import earth.terrarium.ad_astra.screen.handler.CoalGeneratorScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class CoalGeneratorBlockEntity extends ProcessingMachineBlockEntity {

    int ticksToTurnOff = 0;

    public CoalGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.COAL_GENERATOR.get(), blockPos, blockState);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new CoalGeneratorScreenHandler(syncId, inv, this);
    }

    @Override
    public long getCapacity() {
        return AdAstra.CONFIG.coalGenerator.maxEnergy;
    }

    @Override
    public long getEnergyPerTick() {
        return AdAstra.CONFIG.coalGenerator.energyPerTick;
    }

    @Override
    public boolean canExtractEnergy() {
        return true;
    }

    // Only input.
    @Override
    public int getInventorySize() {
        return 1;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction dir) {
        return slot == 0;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public void tick() {
        if (!this.world.isClient) {
            if (this.usesEnergy()) {
                ItemStack input = this.getItems().get(0);

                if (this.getEnergy() < this.getCapacity()) {

                    // Consume the fuel
                    if (this.cookTime > 0) {
                        this.cookTime--;
                        this.cumulateEnergy();
                        this.setActive(true);
                        ticksToTurnOff = 7;
                        // Check if the input is a valid fuel
                    } else if (!input.isEmpty()) {
                        short burnTime = (short) FuelRegistry.get(input);
                        input.decrement(1);
                        this.cookTimeTotal = burnTime;
                        this.cookTime = burnTime;
                    } else {
                        this.setActive(false);
                    }
                } else {
                    ticksToTurnOff--;
                    if (ticksToTurnOff <= 0) {
                        this.setActive(false);
                    }
                }
                // Send energy to surrounding blocks.
                this.energyOut();
            }
        }
    }
}