package tech.mcprison.prison.spigot.backpacks;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import com.cryptomorin.xseries.XMaterial;

import tech.mcprison.prison.Prison;
import tech.mcprison.prison.spigot.SpigotPrison;
import tech.mcprison.prison.spigot.SpigotUtil;
import tech.mcprison.prison.spigot.configs.BackpacksConfig;
import tech.mcprison.prison.spigot.configs.SpigotConfigComponents;


public class BackpacksUtil extends SpigotConfigComponents {

    private static BackpacksUtil instance;
    private Configuration messages = SpigotPrison.getInstance().getMessagesConfig();
    private Configuration backpacksConfig = SpigotPrison.getInstance().getBackpacksConfig();
    private File backpacksFile = new File(SpigotPrison.getInstance().getDataFolder() + "/backpacks/backpacksData.yml");
    private FileConfiguration backpacksDataConfig = YamlConfiguration.loadConfiguration(backpacksFile);
    public static List<String> openBackpacks = new ArrayList<>();
    public static List<String> backpackEdited = new ArrayList<>();

    /**
     * Check if BackPacks's enabled.
     * */
    public static boolean isEnabled(){
        return SpigotPrison.getInstance().getConfig().getString("backpacks").equalsIgnoreCase("true");
    }

    private void backpacksConfigUpdater(){
        backpackConfigUpdater();
    }

    public List<String> getOpenBackpacks(){
        return openBackpacks;
    }

    public void removeFromOpenBackpacks(Player p){
        openBackpacks.remove(p.getName());
    }

    public void updateCachedBackpack(){
        backpacksFile = new File(SpigotPrison.getInstance().getDataFolder() + "/backpacks/backpacksData.yml");
        backpacksDataConfig = YamlConfiguration.loadConfiguration(backpacksFile);
    }

    /**
     * Get SellAll instance.
     * */
    public static BackpacksUtil get() {
        return getInstance();
    }

    /**
     * Add player to edited backpacks.
     *
     * @param p - Player
     * */
    public void addToEditedBackpack(Player p){
        if (!backpackEdited.contains(p.getName())){
            backpackEdited.add(p.getName());
        }
    }

    /**
     * Remove from edited backpacks.
     *
     * @param p - Player
     * */
    public void removeFromEditedBackpack(Player p){
        backpackEdited.remove(p.getName());
    }

    /**
     * Check if player own backpack at first join
     *
     * @param p - Playerù
     *
     * @return boolean - true/false
     * */
    public boolean isPlayerOwningBackpack(Player p){
        return checkOwnBackpack(p);
    }

    /**
     * Set default backpack for new players.
     *
     * @param p - Player
     * */
    public void setDefaultBackpackPlayer(Player p) {
        setDefaultBackpack(p);
    }

    /**
     * Reset a player's inventory.
     * */
    public void resetBackpack(Player p) {
        resetBackpackMethod(p);
    }

    /**
     * Set Player Backpacks max dimensions.
     * NOTE: Should be a multiple of 9.
     * Max dimension for now's 54.
     *
     * @param p - Player
     * @param size - Size
     *
     * */
    public void setBackpackSize(Player p, int size){
        backpackResize(p, size);
    }

    /**
     * Get a backpack size depending on the owner.
     *
     * @param p - Player
     *
     * @return backPackSize - Integer
     * */
    public int getBackpackSize(Player p){
        return getSize(p);
    }

    /**
     * Get Prison Backpacks Inventory.
     * Also if you need to modify it you can do it just by using BackPacksUtil getInventory(player)
     * modify your inventory as you'd usually with spigot inventories
     * and then use BackPacksUtil setInventory(player, inventory) is recommended.
     *
     * EXAMPLE for adding item:
     * Inventory inv = BackPacksUtil.getInventory(p);
     * ItemStack item = new ItemStack(Material.COAL_ORE, 1);
     * inv.addItem(item)
     * BackPacksUtil.setInventory(inv);
     *
     * @param p - player
     *
     * @return inv - Inventory
     * */
    public Inventory getBackpack(Player p){
        return getBackpackDefault(p);
    }

    /**
     * Open backpack.
     *
     * @param p - Player
     * */
    public void openBackpack(Player p){
        openBackpackDefault(p);
    }

    /**
     * Get backpack config
     * */
    public Configuration getBackpacksConfig(){
        return backpacksConfig;
    }

    /**
     * Adding the extra inventory argument will just modify your already
     * made inventory with the backpack content.
     * */
    public Inventory getBackpack(Player p, Inventory inv){
        return getBackpackCustom(p, inv);
    }

    /**
     * Merge another inventory into the backpack inventory.
     *
     * @param p - player
     * @param inv - Inventory
     * */
    public void setInventory(Player p, Inventory inv){
        saveInventory(p, inv);
    }

    /**
     * Add an item to the backpack inventory
     * NOT TESTED!
     *
     * RECOMMENDED WAY:
     * If you need to modify the inventory you can do it just by using BackPacksUtil getInventory(player),
     * modify your inventory as you'd usually with spigot inventories,
     * and then use BackPacksUtil setInventory(player, inventory) is recommended.
     *
     * EXAMPLE for adding item:
     * Inventory inv = BackPacksUtil.getInventory(p);
     * ItemStack item = new ItemStack(Material.COAL_ORE, 1);
     * inv.addItem(item);
     * BackPacksUtil.setInventory(inv);
     *
     * @param p - player
     * @param item - itemstack
     * */
    public HashMap<Integer, ItemStack> addItem(Player p, ItemStack item){
        return addItemToBackpack(p, item);
    }


    /**
     * Remove item from backpack
     * NOT TESTED!
     *
     * RECOMMENDED WAY:
     * If you need to modify the inventory you can do it just by using BackPacksUtil getInventory(player),
     * modify your inventory as you'd usually with spigot inventories,
     * and then use BackPacksUtil setInventory(player, inventory) is recommended.
     *
     * EXAMPLE for removing item:
     * Inventory inv = BackPacksUtil.getInventory(p);
     * ItemStack item = new ItemStack(Material.COAL_ORE, 1);
     * inv.removeItem(item);
     * BackPacksUtil.setInventory(inv);
     *
     * @param p - player
     * @param item - itemstack
     * */
    public HashMap<Integer, ItemStack> removeItem(Player p, ItemStack item){
        return removeItemFromBackpack(p, item);
    }

    /**
     * Give backpack item to a player.
     *
     * @param p - Player
     **/
    public void giveBackpackToPlayer(Player p){
        backPackItem(p);
    }

    /**
     * Create a Lore for an Item in the GUI.
     *
     * @param lores
     * */
    private List<String> createLore( String... lores ) {
        List<String> results = new ArrayList<>();
        for ( String lore : lores ) {
            results.add( SpigotPrison.format(lore) );
        }
        return results;
    }

    /**
     * Create a button for the GUI using ItemStack.
     *
     * @param item
     * @param lore
     * @param display
     * */
    private ItemStack createButton(ItemStack item, List<String> lore, String display) {

        if (item == null){
            item = XMaterial.BARRIER.parseItem();
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null){
            meta = XMaterial.BARRIER.parseItem().getItemMeta();
        }

        return getItemStack(item, lore, display, meta);
    }

    /**
     * Get ItemStack of an Item.
     *
     * @param item
     * @param lore
     * @param display
     * @param meta
     * */
    private ItemStack getItemStack(ItemStack item, List<String> lore, String display, ItemMeta meta) {
        if (meta != null) {
            meta.setDisplayName(SpigotPrison.format(display));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        return item;
    }

    /**
     * Java getBoolean's broken so I made my own.
     * */
    public boolean getBoolean(String string){

        if (string == null){
            return false;
        }

        if (string.equalsIgnoreCase("true")){
            return true;
        } else if (string.equalsIgnoreCase("false")){
            return false;
        }

        return false;
    }

    private void backpackConfigUpdater() {
        BackpacksConfig bpTemp = new BackpacksConfig();
        bpTemp.initialize();
        backpacksConfig = bpTemp.getFileBackpacksConfig();
    }

    private static BackpacksUtil getInstance() {
        if (instance == null && isEnabled()){
            instance = new BackpacksUtil();
        }

        return instance;
    }

    private boolean checkOwnBackpack(Player p) {
        updateCachedBackpack();

        String playerName = backpacksDataConfig.getString("Inventories." + p.getUniqueId() + ".Playername");
        return playerName != null;
    }

    private void setDefaultBackpack(Player p) {
        if (!isPlayerOwningBackpack(p)) {
            int size = 54;
            try {
                size = Integer.parseInt(backpacksConfig.getString("Options.BackPack_Default_Size"));
            } catch (NumberFormatException ignored) {
            }

            if (backpacksConfig.getString("Inventories." + p.getUniqueId() + ".PlayerName") == null) {
                try {
                    backpacksFile = new File(SpigotPrison.getInstance().getDataFolder() + "/backpacks/backPacksData.yml");
                    backpacksDataConfig = YamlConfiguration.loadConfiguration(backpacksFile);
                    backpacksDataConfig.set("Inventories." + p.getUniqueId() + ".PlayerName", p.getName());
                    backpacksDataConfig.set("Inventories." + p.getUniqueId() + ".Size", size);
                    backpacksDataConfig.save(backpacksFile);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return;
                }
            }
            updateCachedBackpack();
        }

        if (getBoolean(backpacksConfig.getString("Options.BackPack_Item_OnJoin"))) {
        	String registeredCmd = Prison.get().getCommandHandler().findRegisteredCommand( "backpack item" );
            Bukkit.dispatchCommand(p, registeredCmd);
        }
    }

    private void resetBackpackMethod(Player p) {
        updateCachedBackpack();

        try {
            backpacksDataConfig.set("Inventories. " + p.getUniqueId() + ".Items", null);
            backpacksDataConfig.save(backpacksFile);
        } catch (IOException ex){
            ex.printStackTrace();
            return;
        }

        updateCachedBackpack();
    }

    private void backpackResize(Player p, int size) {
        updateCachedBackpack();

        // Must be multiple of 9.
        if (size % 9 != 0 || size > 54){
            return;
        }

        updateCachedBackpack();

        backpacksDataConfig.set("Inventories." + p.getUniqueId() + ".Size", size);

        try {
            backpacksDataConfig.save(backpacksFile);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        updateCachedBackpack();
    }

    private int getSize(Player p) {
        updateCachedBackpack();

        int backPackSize = 0;

        try {
            backPackSize = Integer.parseInt(backpacksDataConfig.getString("Inventories." + p.getUniqueId() + ".Size"));
        } catch (NumberFormatException ignored){}
        return backPackSize;
    }

    @NotNull
    private Inventory getBackpackDefault(Player p) {

        updateCachedBackpack();

        Inventory inv = Bukkit.createInventory(p, getBackpackSize(p), SpigotPrison.format("&3" + p.getName() + " -> Backpack"));

        // Get the Items config section
        Set<String> slots;
        try {
            slots = backpacksDataConfig.getConfigurationSection("Inventories. " + p.getUniqueId() + ".Items").getKeys(false);
        } catch (NullPointerException ex){
            return inv;
        }
        if (slots.size() != 0) {
            for (String slot : slots) {
                ItemStack finalItem = backpacksDataConfig.getItemStack("Inventories. " + p.getUniqueId() + ".Items." + slot + ".ITEMSTACK");
                if (finalItem != null) {
                    inv.setItem(Integer.parseInt(slot), finalItem);
                }
            }
        }

        return inv;
    }

    private void openBackpackDefault(Player p) {
        Inventory inv = getBackpack(p);
        p.openInventory(inv);
        if (!openBackpacks.contains(p.getName())){
            openBackpacks.add(p.getName());
        }
    }

    private Inventory getBackpackCustom(Player p, Inventory inv) {
        // Get the Items config section
        Set<String> slots;
        try {
            slots = backpacksDataConfig.getConfigurationSection("Inventories. " + p.getUniqueId() + ".Items").getKeys(false);
        } catch (NullPointerException ex){
            return inv;
        }
        if (slots.size() != 0) {
            for (String slot : slots) {
                ItemStack finalItem = backpacksDataConfig.getItemStack("Inventories. " + p.getUniqueId() + ".Items." + slot + ".ITEMSTACK");
                if (finalItem != null) {
                    try {
                        inv.setItem(Integer.parseInt(slot), finalItem);
                    } catch (ArrayIndexOutOfBoundsException ex){
                        return inv;
                    }
                }
            }
        }

        return inv;
    }

    private void saveInventory(Player p, Inventory inv) {
        updateCachedBackpack();

        if (inv.getContents() != null){
            int slot = 0;

            try {
                backpacksDataConfig.set("Inventories. " + p.getUniqueId() + ".Items", null);
                backpacksDataConfig.save(backpacksFile);
            } catch (IOException ex){
                ex.printStackTrace();
                return;
            }

            updateCachedBackpack();

            for (ItemStack item : inv.getContents()){
                if (item != null){

                    backpacksDataConfig.set("Inventories. " + p.getUniqueId() + ".Items." + slot + ".ITEMSTACK", item);

                    slot++;
                }
            }
            if (slot != 0){
                try {
                    backpacksDataConfig.save(backpacksFile);
                } catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        } else {
            // If it's null just delete the whole stored inventory.
            try {
                backpacksDataConfig.set("Inventories. " + p.getUniqueId() + ".Items", null);
                backpacksDataConfig.save(backpacksFile);
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }

        updateCachedBackpack();
    }

    private HashMap<Integer, ItemStack> addItemToBackpack(Player p, ItemStack item) {
        Inventory inv = getBackpack(p);
        HashMap<Integer, ItemStack> overflow = inv.addItem(item);
        setInventory(p, inv);
        return overflow;
    }

    private HashMap<Integer, ItemStack> removeItemFromBackpack(Player p, ItemStack item) {
        Inventory inv = getBackpack(p);
        HashMap<Integer, ItemStack> underflow = inv.removeItem(item);
        setInventory(p, inv);
        return underflow;
    }

    private void backPackItem(Player p) {

        if (!getBoolean(backpacksConfig.getString("Options.Back_Pack_GUI_Opener_Item"))) {
            return;
        }

        boolean playerHasItemBackPack = false;

        Inventory inv = p.getInventory();

        for (ItemStack item : inv.getContents()) {
            if (item != null){

                ItemStack materialConf = SpigotUtil.getXMaterial(backpacksConfig.getString("Options.BackPack_Item")).parseItem();

                if (materialConf != null && item.getType() == materialConf.getType() && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equalsIgnoreCase(SpigotPrison.format(backpacksConfig.getString("Options.BackPack_Item_Title")))){
                    playerHasItemBackPack = true;
                }
            }
        }

        if (!playerHasItemBackPack){

            ItemStack item;

            List<String> itemLore = createLore(
                    messages.getString("Lore.ClickToOpenBackpack")
            );

            item = createButton(SpigotUtil.getXMaterial(backpacksConfig.getString("Options.BackPack_Item")).parseItem(), itemLore, backpacksConfig.getString("Options.BackPack_Item_Title"));

            if (item != null) {
                inv.addItem(item);
            }
        }
    }
}