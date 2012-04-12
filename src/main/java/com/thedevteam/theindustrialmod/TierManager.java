package com.thedevteam.theindustrialmod;

import com.thedevteam.thecore.tier.web.WebTier;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.block.BlockChangeEvent;
import org.spout.api.event.inventory.PlayerInventoryCraftEvent;

/**
 *
 *
 */
public class TierManager extends WebTier implements Listener{
    private int neededPlaceCount = 10;
    private int neededCraftCount = 4;

    public TierManager() {
        super();
        // TODO as materials get put in, add groups and stuff here
    }

    @EventHandler(order = Order.MONITOR)
    public void onBlockPlace(BlockChangeEvent event){
        if(event.isCancelled()) return;
        if (getValue(event.getSnapshot().getMaterial()) < 1) {
            addValue(event.getSnapshot().getMaterial(), 1/neededPlaceCount);
        }
    }
    
    @EventHandler(order = Order.MONITOR)
    public void onCraftEvent(PlayerInventoryCraftEvent event){
        if(event.isCancelled()) return;
        if (getValue(event.getResult().getMaterial()) < 1) {
            addValue(event.getResult().getMaterial(), 1/neededCraftCount);
        }
    }
}
