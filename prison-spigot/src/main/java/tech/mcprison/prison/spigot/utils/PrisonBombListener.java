package tech.mcprison.prison.spigot.utils;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tech.mcprison.prison.output.Output;
import tech.mcprison.prison.spigot.block.SpigotBlock;

/**
 * <p>This listener class handles the player's interaction with using a bomb.
 * 
 * </p>
 *
 * <p>Some of the inspiration for the handling of player's bomb was found in the
 * following open source project:
 * https://github.com/hpfxd/bombs/blob/master/src/main/java/xyz/tooger/bombs/BombListener.java
 * 
 * </p>
 */
public class PrisonBombListener
	implements Listener
{
	

    @EventHandler( priority = EventPriority.LOW )
    public void onInteract( PlayerInteractEvent event ) {
//        if ( !event.getPlayer().hasPermission("prison.minebombs.use") ) {
//        	return;
//        }

        //Output.get().logInfo( "### PrisonBombListener: PlayerInteractEvent  01 " );
        
        if ( event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR) ) {
        	
        	// If the player is holding a mine bomb, then get the bomb and decrease the
        	// ItemStack in the player's hand by 1:
        	
        	Player player = event.getPlayer();
        	SpigotBlock sBlock = new SpigotBlock( event.getClickedBlock() );
        	
//        	Output.get().logInfo( "### PrisonBombListener: PlayerInteractEvent  02 " );
        	if ( PrisonUtilsMineBombs.setBombInHand( player, sBlock ) ) {
        		
        		// The item was a bomb and it was activated.
        		// Cancel the event so the item will not be placed or processed farther.
        		
        		Output.get().logInfo( "### PrisonBombListener: PlayerInteractEvent  03 Bomb detected - May not have been set. " );
        		event.setCancelled( true );
        	}
        	
        	
        	
 
        	
        }
    }
    

//    @EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = false )
    public void onBlockPlace( PlayerDropItemEvent event ) {

    	Output.get().logInfo( "### PrisonBombListener: PlayerDropItemEvent " );
        
        if ( event.isCancelled() && event.getItemDrop().hasMetadata( "prisonMineBomb" ) ) {
        	
//        	event.getItemDrop().getMetadata( "prisonMineBomb" );
        	
//        	event.getItemDrop().

        	event.setCancelled( false );
        }
        
    }
    
//    @EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = false )
//    public void onBlockPlace2( PlayerInteractEvent event ) {
//
//    	Output.get().logInfo( "### PrisonBombListener: PlayerInteractEvent - oof" );
//    }
    
//    @EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = false )
    public void onBlockPlace3( BlockPlaceEvent event ) {
    	
    	Output.get().logInfo( "### PrisonBombListener: BlockPlaceEvent  HIGHEST  isCanceled= " + event.isCancelled() );

//    	event.getBlockPlaced();
    	
    	
    	event.setBuild( true );
    	event.setCancelled( false );
    	
    	ItemStack item = event.getItemInHand();
    	
    	
    	if ( item.hasItemMeta() && item.getItemMeta().hasDisplayName() ) {
    		ItemMeta meta = item.getItemMeta();
    		
    		Output.get().logInfo( "### PrisonBombListener: BlockPlaceEvent  " + meta.getDisplayName() );
    		
//    		meta.getCustomTagContainer().hasCustomTag( null, null )
    		
//    		meta.
//    		
//    		Multimap<Attribute, AttributeModifier> attributes = meta.getAttributeModifiers();
//    		
//    		
//    		for ( String attri : attributes. ) {
//    			
//    		}
    		
    	}
    	
    	
    }
    
//    @EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = false )
    public void onBlockPlace3( PlayerInteractEvent event ) {
    	
    	Output.get().logInfo( "### PrisonBombListener: PlayerInteractEvent " );
    	
    	
    }

//    @EventHandler( priority = EventPriority.HIGHEST, ignoreCancelled = false )
//    public void onBlockPlace3( BlockDropItemEvent event ) {
//    	
//    	Output.get().logInfo( "### PrisonBombListener: PlayerInteractEvent " );
//    	
//    	
//    }
    
//    @EventHandler( priority = EventPriority.HIGHEST )
//    public void onBlockPlace2( Player event ) {
//    	
//    	
//    	if ( event.isCancelled() && event.getItemDrop().hasMetadata( "prisonMineBomb" ) ) {
//    		
////        	event.getItemDrop().getMetadata( "prisonMineBomb" );
//    		
////        	event.getItemDrop().
//    		
//    		event.setCancelled( false );
//    	}
//    	
//    }
}
