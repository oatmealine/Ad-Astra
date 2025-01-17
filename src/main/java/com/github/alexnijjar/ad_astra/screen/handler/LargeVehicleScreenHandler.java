package com.github.alexnijjar.ad_astra.screen.handler;

import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.ad_astra.registry.ModScreenHandlers;
import com.github.alexnijjar.ad_astra.screen.NoInventorySlot;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantItemStorage;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.slot.Slot;

public class LargeVehicleScreenHandler extends AbstractVehicleScreenHandler {

	public LargeVehicleScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
		this(syncId, inventory, (VehicleEntity) inventory.player.world.getEntityById(buf.readInt()));
	}

	public LargeVehicleScreenHandler(int syncId, PlayerInventory inventory, VehicleEntity entity) {
		super(ModScreenHandlers.LARGE_VEHICLE_SCREEN_HANDLER, syncId, inventory, entity, new Slot[]{

				// Left input slot.
				new NoInventorySlot(entity.getInventory(), 0, 20, 26) {
					@Override
					public boolean canInsert(ItemStack stack) {
						if (!super.canInsert(stack)) {
							return false;
						}
						Storage<FluidVariant> context = ContainerItemContext.withInitial(stack).find(FluidStorage.ITEM);
						return context instanceof CombinedStorage || context instanceof FullItemFluidStorage || context instanceof SingleVariantItemStorage<FluidVariant>;
					}
				},
				// Left output slot.
				new NoInventorySlot(entity.getInventory(), 1, 20, 56) {
					@Override
					public boolean canInsert(ItemStack stack) {
						return false;
					}
				},

				// Inventory
				new NoInventorySlot(entity.getInventory(), 2, 86, 16),
				//
				new NoInventorySlot(entity.getInventory(), 3, 86 + 18, 16),
				//
				new NoInventorySlot(entity.getInventory(), 4, 86 + 18 * 2, 16),
				//
				new NoInventorySlot(entity.getInventory(), 5, 86 + 18 * 3, 16),
				//
				new NoInventorySlot(entity.getInventory(), 6, 86, 34),
				//
				new NoInventorySlot(entity.getInventory(), 7, 86 + 18, 34),
				//
				new NoInventorySlot(entity.getInventory(), 8, 86 + 18 * 2, 34),
				//
				new NoInventorySlot(entity.getInventory(), 9, 86 + 18 * 3, 34),
				//
				new NoInventorySlot(entity.getInventory(), 10, 86, 52),
				//
				new NoInventorySlot(entity.getInventory(), 11, 86 + 18, 52),
				//
				new NoInventorySlot(entity.getInventory(), 12, 86 + 18 * 2, 52),
				//
				new NoInventorySlot(entity.getInventory(), 13, 86 + 18 * 3, 52),
				//
				new NoInventorySlot(entity.getInventory(), 14, 86, 70),
				//
				new NoInventorySlot(entity.getInventory(), 15, 86 + 18, 70),
				//
				new NoInventorySlot(entity.getInventory(), 16, 86 + 18 * 2, 70),
				//
				new NoInventorySlot(entity.getInventory(), 17, 86 + 18 * 3, 70),});
	}

	@Override
	public int getPlayerInventoryOffset() {
		return 15;
	}
}