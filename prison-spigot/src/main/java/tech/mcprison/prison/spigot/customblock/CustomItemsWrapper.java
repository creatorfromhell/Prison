package tech.mcprison.prison.spigot.customblock;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.jojodmo.customitems.api.CustomItemsAPI;

import tech.mcprison.prison.internal.block.Block;
import tech.mcprison.prison.internal.block.PrisonBlock;
import tech.mcprison.prison.spigot.SpigotPrison;
import tech.mcprison.prison.spigot.block.SpigotBlock;
import tech.mcprison.prison.spigot.block.SpigotItemStack;
import tech.mcprison.prison.util.Location;

public class CustomItemsWrapper {
	

	private final SpigotPrison plugin;

	public CustomItemsWrapper() {
		this.plugin = SpigotPrison.getInstance();
	}

	public String getCustomBlockId( Block block ) {
		
		org.bukkit.block.Block spigotBlock = ((SpigotBlock) block).getWrapper();
		return CustomItemsAPI.getCustomItemIDAtBlock( spigotBlock );
	}

	/**
	 * <p>This should only be called when running in the bukkit synchronous thread.
	 * </p>
	 * 
	 * @param block
	 * @param customId
	 * @param doBlockUpdate
	 * @return
	 */
	public Block setCustomBlockId( Block block, String customId, boolean doBlockUpdate ) {

		org.bukkit.block.Block spigotBlock = ((SpigotBlock) block).getWrapper();
		
		// So to prevent this from causing lag, we will only get back the block with no updates
		// This will allow this function to exit:
		org.bukkit.block.Block resultBlock = 
				CustomItemsAPI.setCustomItemIDAtBlock( spigotBlock, customId, doBlockUpdate );
		
		return SpigotBlock.getSpigotBlock( resultBlock );
	}
	
	
	/**
	 * <p>This should only be ran through an asynchronous thread since it will submit a task
	 * on the bukkit synchronous thread to perform the actual update.  This does not need to 
	 * return a block.
	 * </p>
	 * 
	 * @param prisonBlock
	 * @param location
	 * @param doBlockUpdate
	 * @return
	 */
	public void setCustomBlockIdAsync( PrisonBlock prisonBlock, Location location )
	{
	
		new BukkitRunnable() {
			@Override
			public void run() {
				
				// No physics update:
				
				SpigotBlock sBlock = (SpigotBlock) location.getBlockAt();
				
				org.bukkit.block.Block spigotBlock = sBlock.getWrapper();
				//org.bukkit.block.Block spigotBlock = ((SpigotBlock) prisonBlock).getWrapper();
				
				// Request the block change, but we don't need the results so ignore it
//				org.bukkit.block.Block resultBlock = 
				CustomItemsAPI.setCustomItemIDAtBlock( spigotBlock, prisonBlock.getBlockName(), true );
				
			}
		}.runTaskLater( getPlugin(), 0 );
		
	}

	/**
	 * <p>WARNING: CustomItems does not have a getDrops() function, so there is no way to 
	 * actually get custom drops for CustomItems blocks.  All we can do is return the custom 
	 * block.
	 * </p>
	 * 
	 * <p>If a getDrops() function is added in the future, then we will be able to hook that up
	 * with that future version.
	 * </p>
	 * 
	 * @param prisonBlock
	 * @return
	 */
	public List<SpigotItemStack> getDrops( PrisonBlock prisonBlock ) {
		List<SpigotItemStack> results = new ArrayList<>();
		
		org.bukkit.inventory.ItemStack bItemStack = CustomItemsAPI.getCustomItem( prisonBlock.getBlockName() );
		
		SpigotItemStack sItemStack = new SpigotItemStack( bItemStack );

		// Fix itemStack's displayName and set to the correct BlockType:
		sItemStack.setPrisonBlock( prisonBlock );
		
		results.add( sItemStack );
		
		return results;
	}
	
	public List<String> getCustomBlockList() {
		return CustomItemsAPI.listBlockCustomItemIDs();
	}
	
	public SpigotPrison getPlugin() {
		return plugin;
	}
}
