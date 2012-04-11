package com.thedevteam.theindustrialmod;

import com.thedevteam.thecore.tier.web.WebTier;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Order;
import org.spout.api.event.block.BlockChangeEvent;
import org.spout.api.event.inventory.PlayerInventoryCraftEvent;

/**
 *
 *
 */
public class Tier extends WebTier{

    @EventHandler(order = Order.MONITOR)
    public void onBlockPlace(BlockChangeEvent event){
        if(event.isCancelled()) return;
        if (getValue(event.getSnapshot().getMaterial()) < 1) {
            addValue(event.getSnapshot().getMaterial(), .25);
        }
    }
    
    @EventHandler(order = Order.MONITOR)
    public void onCraftEvent(PlayerInventoryCraftEvent event){
        if(event.isCancelled()) return;
        if (getValue(event.getResult().getMaterial()) < 1) {
            addValue(event.getResult().getMaterial(), .1);
        }
    }
}
