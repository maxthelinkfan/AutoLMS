package me.hydrations.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin
  implements Listener
{
  SettingsManager settings = SettingsManager.getInstance();
  public ArrayList<String> lms = new ArrayList<String>();
  public ArrayList<String> lmshost = new ArrayList<String>();
  public static Economy economy = null;
 

  private boolean setupEconomy()
  {
    RegisteredServiceProvider economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
    if (economyProvider != null) {
      economy = (Economy)economyProvider.getProvider();
    }

    return economy != null;
  }

  @EventHandler
  public void onDeath(PlayerDeathEvent e)
  {
    this.lms.remove(e.getEntity().getName());
  }

  @EventHandler
  public void onDeath1(PlayerJoinEvent e)
  {
    this.lms.remove(e.getPlayer().getName());
  }

  @EventHandler
  public void onDeath2(PlayerMoveEvent e)
  {
    if ((this.lmshost.isEmpty()) && 
      (this.lms.size() == 1))
      for (String str : this.lms) {
        final Player somePlayer = Bukkit.getPlayer(str);
        economy.depositPlayer(somePlayer.getName(), getConfig().getInt("lms_cash_on_win"));
        this.lms.remove(somePlayer.getName());
        somePlayer.sendMessage(ChatColor.GREEN + "You have won the LMS! You have received $" + getConfig().getInt("lms_cash_on_win") + ". Your new balance is " + economy.getBalance(somePlayer.getName()));
        Bukkit.broadcastMessage(ChatColor.GOLD + somePlayer.getName() + " has won the LMS!");
        new BukkitRunnable()
        {
          public void run() {
            somePlayer.setHealth(0.0D);
          }
        }
        .runTaskLater(this, 100L);
      }
  }

  public void onEnable()
  {
    getConfig().options().copyDefaults(true);
    saveConfig();
    this.settings.setup(this);
    Bukkit.getServer().getPluginManager().registerEvents(this, this);
    setupEconomy();

   
  }

  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    if (cmd.getName().equalsIgnoreCase("lms")) {
      if (args.length <= 0) {
        sender.sendMessage(ChatColor.RED + "LMS Commands:");
        sender.sendMessage(ChatColor.GOLD + "/lms host - Host an LMS!");
        sender.sendMessage(ChatColor.GOLD + "/lms set - Set LMS Location!");
        sender.sendMessage(ChatColor.GOLD + "/lms join - Join LMS!");
        sender.sendMessage(ChatColor.GOLD + "/lms leave - WIP");
        return true;
      }

      if (args.length > 0) {
        if (args[0].equalsIgnoreCase("set")) {
          if (sender.hasPermission("lms.set")) {
            Player p = (Player)sender;
            this.settings.getData().set("world", p.getLocation().getWorld().getName());
            this.settings.getData().set("x", Double.valueOf(p.getLocation().getX()));
            this.settings.getData().set("y", Double.valueOf(p.getLocation().getY()));
            this.settings.getData().set("z", Double.valueOf(p.getLocation().getZ()));
            this.settings.saveData();
            p.sendMessage(ChatColor.RED + "Set LMS Location!");
            return true;
          }
        } else if (args[0].equalsIgnoreCase("host")) {
          if (sender.hasPermission("lms.host")) {
            this.lms.clear();
            this.lmshost.add(sender.getName());
            Bukkit.broadcastMessage(ChatColor.GRAY + "=====================================================");
            Bukkit.broadcastMessage(ChatColor.RED + sender.getName() + ChatColor.GRAY + " is hosting a LMS!");
            Bukkit.broadcastMessage(ChatColor.GRAY + "Type /lms join to join in on the LMS!");
            Bukkit.broadcastMessage(ChatColor.GRAY + "=====================================================");
            sender.sendMessage(ChatColor.GOLD + "You are hosting an LMS!");
            
            new BukkitRunnable()
            {
              public void run() {
                Bukkit.broadcastMessage(ChatColor.GOLD + "LMS is starting in 25 seconds!");
              }
            }
            .runTaskLater(this, 100L);
            
            new BukkitRunnable()
            {
              public void run() {
                Bukkit.broadcastMessage(ChatColor.GOLD + "LMS is starting in 20 seconds!");
              }
            }
            .runTaskLater(this, 200L);
            
            new BukkitRunnable()
            {
              public void run() {
                Bukkit.broadcastMessage(ChatColor.GOLD + "LMS is starting in 15 seconds!");
              }
            }
            .runTaskLater(this, 300L);

            new BukkitRunnable()
            {
              public void run() {
                Bukkit.broadcastMessage(ChatColor.GOLD + "LMS is starting in 10 seconds!");
              }
            }
            .runTaskLater(this, 400L);
            new BukkitRunnable()
            {
              public void run() {
                Bukkit.broadcastMessage(ChatColor.GOLD + "LMS is starting in 5 seconds!");
              }
            }
            .runTaskLater(this, 480L);

            new BukkitRunnable()
            {
              public void run() {
                Bukkit.broadcastMessage(ChatColor.GOLD + "LMS is starting in 4 seconds!");
              }
            }
            .runTaskLater(this, 500L);
            new BukkitRunnable()
            {
              public void run() {
                Bukkit.broadcastMessage(ChatColor.GOLD + "LMS is starting in 3 seconds!");
              }
            }
            .runTaskLater(this, 520L);
            new BukkitRunnable()
            {
              public void run() {
                Bukkit.broadcastMessage(ChatColor.GOLD + "LMS is starting in 2 seconds!");
              }
            }
            .runTaskLater(this, 540L);
            new BukkitRunnable()
            {
              public void run() {
                Bukkit.broadcastMessage(ChatColor.GOLD + "LMS is starting in 1 second!");
              }
            }
            .runTaskLater(this, 560L);
            new BukkitRunnable()
            {
              public void run() {
                Bukkit.broadcastMessage(ChatColor.GOLD + "LMS has started!");
                World w = Bukkit.getServer().getWorld(Main.this.settings.getData().getString("world"));
                double x = Main.this.settings.getData().getDouble("x");
                double y = Main.this.settings.getData().getDouble("y");
                double z = Main.this.settings.getData().getDouble("z");
                for (String str : Main.this.lms) {
                  Player lp = Bukkit.getPlayer(str);
                  lp.teleport(new Location(w, x, y, z));
                  Main.this.lmshost.clear();
                  Inventory inv = lp.getInventory();
                  inv.clear();

                  for (PotionEffect effect : lp.getActivePotionEffects()) {
                    lp.removePotionEffect(effect.getType());
                  }

                  ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);

                  ItemStack helm = new ItemStack(Material.IRON_HELMET);
                  ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
                  ItemStack legs = new ItemStack(Material.IRON_LEGGINGS);
                  ItemStack boots = new ItemStack(Material.IRON_BOOTS);

                  sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);

                  inv.addItem(new ItemStack[] { sword });

                  ItemStack getSoup = new ItemStack(Material.MUSHROOM_SOUP, 1);
                  for (int i = 1; i <= 35; i++) {
                    inv.addItem(new ItemStack[] { getSoup });
                  }
                  ((PlayerInventory)inv).setHelmet(helm);
                  ((PlayerInventory)inv).setChestplate(chest);
                  ((PlayerInventory)inv).setLeggings(legs);
                  ((PlayerInventory)inv).setBoots(boots);
                  if (Main.this.lmshost.contains(lp.getName())) {
                    Main.this.lmshost.remove(lp.getName());
                  }
                }
              }

            }

            .runTaskLater(this, 580L);
            return true;
          }
        } else if (args[0].equalsIgnoreCase("join")) {
          if (this.lmshost.isEmpty()) {
            sender.sendMessage(ChatColor.RED + "There is not a LMS currently being hosted!");
          } else {
        	  Player p = (Player) sender;
            p.sendMessage(ChatColor.RED + "You have joined the LMS!");
            this.lms.add(sender.getName());
    
            World w = Bukkit.getServer().getWorld(Main.this.settings.getData().getString("hworld"));
            double x = Main.this.settings.getData().getDouble("hx");
            double y = Main.this.settings.getData().getDouble("hy");
            double z = Main.this.settings.getData().getDouble("hz");
            	  p.teleport(new Location(w, x, y, z));
            	  Inventory inv = p.getInventory();
            	  inv.clear();
            	  ItemStack air = new ItemStack(Material.AIR);
            	 p.getInventory().setHelmet(air);
            	 p.getInventory().setChestplate(air);
            	 p.getInventory().setLeggings(air);
            	 p.getInventory().setBoots(air);
            	 
            	 
            	  for (PotionEffect effect : p.getActivePotionEffects())
				        p.removePotionEffect(effect.getType());
        
            
            Iterator localIterator = this.lms.iterator(); if (localIterator.hasNext()) { String str = (String)localIterator.next();
              Player lp2 = Bukkit.getPlayer(str);
              lp2.sendMessage(ChatColor.GREEN + sender.getName() + " has joined the LMS!");
              return true;
            }
            
          }
        } else if (args[0].equalsIgnoreCase("sethub")) {
              if (sender.hasPermission("lms.sethub")) {
            	  Player p = (Player)sender;
                  this.settings.getData().set("hworld", p.getLocation().getWorld().getName());
                  this.settings.getData().set("hx", Double.valueOf(p.getLocation().getX()));
                  this.settings.getData().set("hy", Double.valueOf(p.getLocation().getY()));
                  this.settings.getData().set("hz", Double.valueOf(p.getLocation().getZ()));
                  this.settings.saveData();
                  p.sendMessage(ChatColor.RED + "Set LMS Hub Location!");
                  return true;
        	  
        	  
          }
        }

      }

    }

    return false;
  }
}