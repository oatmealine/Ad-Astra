package com.github.alexnijjar.ad_astra.items.vehicles;

import com.github.alexnijjar.ad_astra.items.FluidContainingItem;
import com.github.alexnijjar.ad_astra.items.HoldableOverHead;
import com.github.alexnijjar.ad_astra.items.ModRenderedItem;
import com.github.alexnijjar.ad_astra.util.FluidUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public abstract class VehicleItem extends ModRenderedItem implements FluidContainingItem, HoldableOverHead {

	public VehicleItem(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		long fuel = FluidUtils.dropletsToMillibuckets(this.getAmount(stack));
		tooltip.add(Text.translatable("tooltip.ad_astra.vehicle_fuel", fuel, FluidUtils.dropletsToMillibuckets(this.getTankSize())).setStyle(Style.EMPTY.withColor(fuel > 0 ? Formatting.GREEN : Formatting.RED)));
	}
}
