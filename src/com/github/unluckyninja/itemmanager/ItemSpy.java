package com.github.unluckyninja.itemmanager;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemSpy implements Listener{
    private ItemManager IM;
    public ItemSpy(ItemManager IM){
        this.IM = IM;
    }
    @EventHandler
    public void onPlayerItemHeldEvent(PlayerItemHeldEvent event){
        Player player = event.getPlayer();
        if(!player.hasPermission("itemmanager.admin")){
            PlayerInventory inv = player.getInventory();
            ItemStack itemstackp = inv.getItem(event.getPreviousSlot());
            ItemStack itemstackn = inv.getItem(event.getNewSlot());
            if(itemstackn != null){
                boolean x = player.hasPermission("itemmanager.item."+itemstackn.getTypeId());
                if(ItemManager.mode){
                    if(!x){
                        inv.setItem(event.getPreviousSlot(), itemstackn);
                        inv.setItem(event.getNewSlot(), itemstackp);
                        player.sendMessage(IM.StrCha(ItemManager.message.getString(ItemManager.language+".modeTrue.onPlayerHold"),itemstackn));
                    }
                }else{
                    if(x){
                        inv.setItem(event.getPreviousSlot(), itemstackn);
                        inv.setItem(event.getNewSlot(), itemstackp);
                        player.sendMessage(IM.StrCha(ItemManager.message.getString(ItemManager.language+".modeFalse.onPlayerHold"),itemstackn));
                    }
                }
            }
        }
    }
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player player = (Player)event.getWhoClicked();
            if(!player.hasPermission("itemmanager.admin")){
                if(event.getSlotType() != InventoryType.SlotType.CONTAINER&&event.getSlotType() != InventoryType.SlotType.OUTSIDE && event.getCursor().getType() != Material.AIR){
                    PlayerInventory inv = player.getInventory();
                    ItemStack is = event.getCursor();
                    if(event.getSlotType() == InventoryType.SlotType.QUICKBAR){
                        if(event.getSlot() == inv.getHeldItemSlot()){
                            if(ItemManager.mode){
                                if(!player.hasPermission("itemmanager.item."+is.getTypeId())){
                                    event.setCancelled(true);
                                    player.sendMessage(IM.StrCha(ItemManager.message.getString(ItemManager.language+".modeTrue.onPlayerHold"),is));
                                }
                            }else{
                                if(player.hasPermission("itemmanager.item."+is.getTypeId())){
                                    event.setCancelled(true);
                                    player.sendMessage(IM.StrCha(ItemManager.message.getString(ItemManager.language+".modeFalse.onPlayerHold"),is));
                                }
                            }
                        }
                    }else{
                        if(ItemManager.mode){
                            if(!player.hasPermission("itemmanager.item."+is.getTypeId())){
                                event.setCancelled(true);
                                player.sendMessage(IM.StrCha(ItemManager.message.getString(ItemManager.language+".modeTrue.onPlayerUse"),is));
                            }
                        }else{
                            if(player.hasPermission("itemmanager.item."+is.getTypeId())){
                                event.setCancelled(true);
                                player.sendMessage(IM.StrCha(ItemManager.message.getString(ItemManager.language+".modeFalse.onPlayerUse"),is));
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerPickupItemEvent(PlayerPickupItemEvent event){
        Player player = event.getPlayer();
        if(!player.hasPermission("itemmanager.admin")){
            PlayerInventory inv = player.getInventory();
            Item i = event.getItem();
            if((inv.getHeldItemSlot() == inv.firstEmpty())){
                if(ItemManager.mode){
                    if(!player.hasPermission("itemmanager.item."+i.getItemStack().getTypeId())){
                        event.setCancelled(true);
                        player.sendMessage(IM.StrCha(ItemManager.message.getString(ItemManager.language+".modeTrue.onPlayerPickUp"),i.getItemStack()));
                    }
                }else{
                    if(player.hasPermission("itemmanager.item."+i.getItemStack().getTypeId())){
                        event.setCancelled(true);
                        player.sendMessage(IM.StrCha(ItemManager.message.getString(ItemManager.language+".modeFalse.onPlayerPickUp"),i.getItemStack()));
                    }
                }
            }
        }
    }
}