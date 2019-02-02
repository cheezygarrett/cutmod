package com.cheezygarrett.CUTMod.blocks;

import java.util.Map;

import com.cheezygarrett.CUTMod.init.ModBlocks;
import com.cheezygarrett.CUTMod.init.ModItems;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SpaceTimeManipulatorRecipes 
{	
	private static final SpaceTimeManipulatorRecipes INSTANCE = new SpaceTimeManipulatorRecipes();
	private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static SpaceTimeManipulatorRecipes getInstance()
	{
		return INSTANCE;
	}
	
	private SpaceTimeManipulatorRecipes() 
	{
		addManipulatingRecipe(new ItemStack(ModItems.LOGISTICAL_CIRCUIT), new ItemStack(ModItems.LOGISTICAL_PAPER), new ItemStack(ModBlocks.LOGISTICAL_CORE_BLOCK), 5.0F);
	}

	
	public void addManipulatingRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience) 
	{
		if(getManipulatingResult(input1, input2) != ItemStack.EMPTY) return;
		this.smeltingList.put(input1, input2, result);
		this.experienceList.put(result, Float.valueOf(experience));
	}
	
	public ItemStack getManipulatingResult(ItemStack input1, ItemStack input2) 
	{
		for(java.util.Map.Entry<ItemStack, Map<ItemStack, ItemStack>> entry : this.smeltingList.columnMap().entrySet()) 
		{
			if(this.compareItemStacks(input1, (ItemStack)entry.getKey())) 
			{
				for(java.util.Map.Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) 
				{
					if(this.compareItemStacks(input2, (ItemStack)ent.getKey())) 
					{
						return (ItemStack)ent.getValue();
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList() 
	{
		return this.smeltingList;
	}
	
	public float getManipulatingExperience(ItemStack stack)
	{
		for (java.util.Map.Entry<ItemStack, Float> entry : this.experienceList.entrySet()) 
		{
			if(this.compareItemStacks(stack, (ItemStack)entry.getKey())) 
			{
				return ((Float)entry.getValue()).floatValue();
			}
		}
		return 0.0F;
	}
}