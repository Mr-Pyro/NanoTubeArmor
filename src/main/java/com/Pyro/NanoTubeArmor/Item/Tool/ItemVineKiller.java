package com.Pyro.NanoTubeArmor.Item.Tool;

import java.util.Random;
import java.util.Vector;

import com.Pyro.NanoTubeArmor.NanoTubeArmor;
import com.Pyro.NanoTubeArmor.Item.ItemBase;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemVineKiller extends ItemBase {

	private Vector<BlockPos> vines = new Vector<BlockPos>();
	
	private Random rand = new Random();
	private int sqNum = rand.nextInt(6) + 1;
	private int sqNumI = 0;
	
	public ItemVineKiller(String name) {
		super(name);
		this.setMaxStackSize(1);
		this.setHasSubtypes(false);
		this.setMaxDamage(960);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
		
		//player.sendMessage(new TextComponentString("OnItemUse Class Called: BlockPos: " + pos + " Block: " + worldIn.getBlockState(pos).getBlock()));
		
		if (worldIn.getBlockState(pos).getBlock() == Blocks.VINE) {
			
			vines.add(pos);
			getNearbyVines(worldIn, pos);
			
		}
		
		if(!vines.isEmpty() && !entityLiving.isSneaking()) {
			
			for(int i = 0; i < vines.size(); i++) {
				
				if (worldIn.getBlockState(vines.get(i)).getBlock() == Blocks.VINE) {
					
					worldIn.destroyBlock(vines.get(i), false);
					
					stack.damageItem(1, entityLiving);
					
					if(stack.getMaxDamage() - stack.getItemDamage() == 0) {
						
						i++;
						
						if(worldIn.getBlockState(vines.get(i)).getBlock() == Blocks.VINE) {
							
							worldIn.destroyBlock(vines.get(i), false);
							stack.damageItem(1, entityLiving);
							
						}
						
						break;
						
					}
					
				}
				
			}
			
			vines.clear();
			
		}else {
			
			vines.clear();
			
		}
		
        return true;
    }
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		
		if(!worldIn.isRemote)
		{
			
			if(player.isSneaking() && player.isInWater() && findWater(worldIn, pos) != null) {
				
				if(sqNumI >= sqNum)
				{
					
					sqNum = rand.nextInt(6) + 1;
					sqNumI = 0;
					
					EntitySquid squid = new EntitySquid(worldIn);
					BlockPos spawnPos = findWater(worldIn, pos);
					
					squid.setCustomNameTag("EpicSquid");
					
					squid.setLocationAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 10, 10);
					
					worldIn.spawnEntity(squid);
					player.sendStatusMessage(new TextComponentString("Spawning EpicSquid"), true);
				
				}else
				{
					
					sqNumI++;
					
				}
				
			}
			
		}
		
        return EnumActionResult.PASS;
    }
	
	private BlockPos findWater(World worldIn, BlockPos pos)
	{
		
		if(worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getMaterial() == Material.WATER && worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ())).getMaterial() == Material.WATER)
		{
			
			return new BlockPos(pos.getX(), pos.getY() + 2, pos.getZ());
			
		}else
		{
			
			return null;
			
		}
		
	}
	
	private void getNearbyVines(World worldIn, BlockPos pos) {
		
		//private BlockPos neighbors[]
		
		if(!vines.contains(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()))) {
			
			if(worldIn.getBlockState(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ())).getBlock() == Blocks.VINE) {
				
				vines.add(new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()));
				getNearbyVines(worldIn, new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ()));
				
			}
			
		}
		if(!vines.contains(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()))) {
			
			if(worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() == Blocks.VINE) {
				
				vines.add(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
				getNearbyVines(worldIn, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
				
			}
			
		}
		if(!vines.contains(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1))) {
	
			if(worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1)).getBlock() == Blocks.VINE) {
		
				vines.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1));
				getNearbyVines(worldIn, new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1));
		
			}
	
		}
		
		if(!vines.contains(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()))) {
			
			if(worldIn.getBlockState(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ())).getBlock() == Blocks.VINE) {
				
				vines.add(new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()));
				getNearbyVines(worldIn, new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ()));
				
			}
			
		}
		if(!vines.contains(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()))) {
			
			if(worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() == Blocks.VINE) {
				
				vines.add(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));
				getNearbyVines(worldIn, new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()));
				
			}
			
		}
		if(!vines.contains(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1))) {
			
			if(worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1)).getBlock() == Blocks.VINE) {
		
				vines.add(new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1));
				getNearbyVines(worldIn, new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1));
		
			}
	
		}
		
	}
	
	
	public void registerItemModel() {
		
		NanoTubeArmor.proxy.registerItemRenderer(this, 0, name);
		
	}
	
}
