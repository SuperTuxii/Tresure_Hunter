package treasure_hunter.treasure_hunter.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import treasure_hunter.treasure_hunter.Treasure_Hunter;

import java.util.Objects;

public class setupCommand implements CommandExecutor {

    public final Treasure_Hunter main;

    public setupCommand(Treasure_Hunter treasure_hunter) {
        this.main = treasure_hunter;
    }

    public Scoreboard mainScoreboard;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (Bukkit.getOperators().contains(Bukkit.getOfflinePlayer(p.getUniqueId()))) {
                if (args.length == 0) {
                    MainHelp(p);
                } else if (args.length == 2 && args[0].equalsIgnoreCase("treasure") && args[1].equalsIgnoreCase("hunter")) {
                    TreasureHunterHelp(p);
                }else if (args.length > 2 && args[0].equalsIgnoreCase("treasure") && args[1].equalsIgnoreCase("hunter")) {
                    if (args[2].equalsIgnoreCase("instances") && args[3].equalsIgnoreCase("set") && args.length == 5) {
                        if (Integer.parseInt(args[4]) < 0) {
                            p.sendMessage(format("&cERROR: Amount can't be smaller than 0"));
                        } else if (Integer.parseInt(args[4]) > Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("MapCount").getScore()) {
                            p.sendMessage(format("&cERROR: There can't be more Game Instances than Maps"));
                        }else if (Integer.parseInt(args[4]) + 2 > Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("MapCount").getScore()) {
                            p.sendMessage(format("&6WARNING: There should be at least 2 more maps than Game Instances"));
                        }else {
                            GameInstances(Integer.parseInt(args[4]), p);
                        }
                    } else if (args[2].equalsIgnoreCase("queue") && args[3].equalsIgnoreCase("sign") && args[4].equalsIgnoreCase("set") && args.length == 8) {
                        QueueSignLocation(Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), p);
                    } else if (args[2].equalsIgnoreCase("team-size") && args[3].equalsIgnoreCase("set") && args.length == 5) {
                        if (Integer.parseInt(args[4]) < 0) {
                            p.sendMessage(format("&cERROR: Amount can't be smaller than 0"));
                        }else {
                            TeamSize(Integer.parseInt(args[4]), p);
                        }
                    } else if (args[2].equalsIgnoreCase("map") && args[3].equalsIgnoreCase("choosing") && args[4].equalsIgnoreCase("time") && args[5].equalsIgnoreCase("set") && args.length == 7) {
                        if (Integer.parseInt(args[6]) < 0) {
                            p.sendMessage(format("&cERROR: Map Choosing Time can't be smaller than 0"));
                        }else if (Integer.parseInt(args[6]) < 10) {
                            p.sendMessage(format("&6WARNING: Map Choosing Time should be more than 10 seconds"));
                            MapChoosingTime(Integer.parseInt(args[6]), p);
                        }else {
                            MapChoosingTime(Integer.parseInt(args[6]), p);
                        }
                    } else if (args[2].equalsIgnoreCase("round") && args[3].equalsIgnoreCase("time") && args[4].equalsIgnoreCase("set") && args.length == 6) {
                        if (Integer.parseInt(args[5]) < 0) {
                            p.sendMessage(format("&cERROR: Round Time can't be smaller than 0"));
                        }else if (Integer.parseInt(args[5]) < 60) {
                            p.sendMessage(format("&6WARNING: Round Time should be more than 60 seconds"));
                            RoundTime(Integer.parseInt(args[5]), p);
                        }else {
                            RoundTime(Integer.parseInt(args[5]), p);
                        }
                        RoundTime(Integer.parseInt(args[5]), p);
                    } else if (args[2].equalsIgnoreCase("treasure") && args[3].equalsIgnoreCase("amount") && args[4].equalsIgnoreCase("set") && args.length == 6) {
                        if (Integer.parseInt(args[5]) < 0) {
                            p.sendMessage(format("&cERROR: Amount can't be smaller than 0"));
                        }else {
                            TreasureAmount(Integer.parseInt(args[5]), p);
                        }
                    } else if (args[2].equalsIgnoreCase("treasure") && args[3].equalsIgnoreCase("time") && args[4].equalsIgnoreCase("set") && args.length == 6) {
                        if (Integer.parseInt(args[5]) < 0) {
                            p.sendMessage(format("&cERROR: Amount can't be smaller than 0"));
                        }else {
                            TreasureTime(Integer.parseInt(args[5]), p);
                        }
                    } else if (args[2].equalsIgnoreCase("treasure") && args[3].equalsIgnoreCase("radius") && args[4].equalsIgnoreCase("set") && args.length == 6) {
                        if (Integer.parseInt(args[5]) < 0) {
                            p.sendMessage(format("&cERROR: Amount can't be smaller than 0"));
                        }else {
                            TreasureRadius(Integer.parseInt(args[5]), p);
                        }
                    } else if (args[2].equalsIgnoreCase("revive") && args[3].equalsIgnoreCase("time") && args[4].equalsIgnoreCase("set") && args.length == 6) {
                        if (Integer.parseInt(args[5]) < 0) {
                            p.sendMessage(format("&cERROR: Amount can't be smaller than 0"));
                        }else if (Integer.parseInt(args[5]) < 60) {
                            p.sendMessage(format("&6WARNING: Revive Time should be at least 60 seconds"));
                            ReviveTime(Integer.parseInt(args[5]), p);
                        }else {
                            ReviveTime(Integer.parseInt(args[5]), p);
                        }
                    } else if (args[2].equalsIgnoreCase("res") && args[3].equalsIgnoreCase("time") && args[4].equalsIgnoreCase("set") && args.length == 6) {
                        if (Integer.parseInt(args[5]) < 0) {
                            p.sendMessage(format("&cERROR: Amount can't be smaller than 0"));
                        }else if (Integer.parseInt(args[5]) < 5) {
                            p.sendMessage(format("&6WARNING: Res Time should be at least 5 seconds"));
                            ResTime(Integer.parseInt(args[5]), p);
                        }else {
                            ResTime(Integer.parseInt(args[5]), p);
                        }
                    } else if (args[2].equalsIgnoreCase("res") && args[3].equalsIgnoreCase("radius") && args[4].equalsIgnoreCase("set") && args.length == 6) {
                        if (Integer.parseInt(args[5]) < 0) {
                            p.sendMessage(format("&cERROR: Amount can't be smaller than 0"));
                        }else {
                            ResRadius(Integer.parseInt(args[5]), p);
                        }
                    } else if (args[2].equalsIgnoreCase("ship") && args[3].equalsIgnoreCase("ready") && args[4].equalsIgnoreCase("time") && args[5].equalsIgnoreCase("set") && args.length == 7) {
                        if (Integer.parseInt(args[6]) < 0) {
                            p.sendMessage(format("&cERROR: Amount can't be smaller than 0"));
                        }else if (Integer.parseInt(args[6]) < 60) {
                            p.sendMessage(format("&6WARNING: Ship Ready Time should be at 60 seconds"));
                            ShipReadyTime(Integer.parseInt(args[6]), p);
                        }else {
                            ShipReadyTime(Integer.parseInt(args[6]), p);
                        }
                    }else if (args[2].equalsIgnoreCase("prices") && args[3].equalsIgnoreCase("set") && args.length == 10) {
                        if (Integer.parseInt(args[4]) < 0 || Integer.parseInt(args[5]) < 0 || Integer.parseInt(args[6]) < 0 || Integer.parseInt(args[7]) < 0 || Integer.parseInt(args[8]) < 0  || Integer.parseInt(args[9]) < 0) {
                            p.sendMessage(format("&cERROR: Amount can't be smaller than 0"));
                        }else {
                            setPrices(Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]), Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]), p);
                        }
                    }else if (args[2].equalsIgnoreCase("add") && args[3].equalsIgnoreCase("map") && args.length == 4) {
                        main.getMapManager().addMap(p);
                    }else if (args[2].equalsIgnoreCase("map")) {
                        if (args[4].equalsIgnoreCase("custommodeldata") && args[5].equalsIgnoreCase("set") && args.length == 7) {
                            if (Integer.parseInt(args[6]) < 0) {
                                p.sendMessage(format("&cERROR: CustomModelData can't be smaller than 0"));
                            }else {
                                main.getMapManager().setCustomModelData(Integer.parseInt(args[3]), Integer.parseInt(args[6]), p);
                            }
                        }else if (args[4].equalsIgnoreCase("blue") && args[5].equalsIgnoreCase("spawn") && args[6].equalsIgnoreCase("set") && args.length == 10) {
                            main.getMapManager().setSpawn(Integer.parseInt(args[3]), true, Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]), p);
                        }else if (args[4].equalsIgnoreCase("red") && args[5].equalsIgnoreCase("spawn") && args[6].equalsIgnoreCase("set") && args.length == 10) {
                            main.getMapManager().setSpawn(Integer.parseInt(args[3]), false, Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]), p);
                        }else if (args[4].equalsIgnoreCase("treasure") && args[5].equalsIgnoreCase("spawn") && args[6].equalsIgnoreCase("set") && args.length == 12) {
                            main.getMapManager().setTreasureSpawn(Integer.parseInt(args[3]), Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]), Integer.parseInt(args[10]), Integer.parseInt(args[11]), p);
                        }else if (args[4].equalsIgnoreCase("shop") && args[5].equalsIgnoreCase("spawn") && args[6].equalsIgnoreCase("set") && args.length == 11) {
                            main.getMapManager().setShopSpawn(Integer.parseInt(args[3]), Integer.parseInt(args[7]), Integer.parseInt(args[8]), Integer.parseInt(args[9]), Integer.parseInt(args[10]), p);
                        }
                    }else {
                        TreasureHunterHelp(p);
                    }
                } else if (!args[0].equalsIgnoreCase("scoreboard")){
                    MainHelp(p);
                }
            }else {
                p.sendMessage(format("&cYou are not an Operator!"));
            }
            return true;
        }
        return true;
    }

    public void MainHelp(Player p) {
        p.sendMessage(format("&e---------&f Help: Setup &e-------------------\n&7Below is a list of all Setup commands:"));
        p.sendMessage(format("&e/setup treasure hunter: &fsetup commands for treasure hunter minigame"));
    }

    public void TreasureHunterHelp(Player p) {
        p.sendMessage(format("&e---------&f Help: Setup &e-------------------\n&7Below is a list of all Setup commands:"));
        p.sendMessage(format("&e/setup treasure hunter instances set <amount>: &fSets the amount of game instances"));
        p.sendMessage(format("&e/setup treasure hunter queue sign set <x> <y> <z>: &fSets the position of the queue sign"));
        p.sendMessage(format("&e/setup treasure hunter team-size set <team-size>: &fSets the team-size"));
        p.sendMessage(format("&e/setup treasure hunter map choosing time set <time in seconds>: &fSets the map choosing time"));
        p.sendMessage(format("&e/setup treasure hunter round time set <time in seeconds>: &fSets the Round Time"));
        p.sendMessage(format("&e/setup treasure hunter treasure amount set <amount>: &fSets the Treasure Amount"));
        p.sendMessage(format("&e/setup treasure hunter treasure time set <amount>: &fSets the time it takes to pickup treasure"));
        p.sendMessage(format("&e/setup treasure hunter treasure radius set <amount>: &fSets the radius in which a player can pick up treasure"));
        p.sendMessage(format("&e/setup treasure hunter revive time set <time in seconds>: &fSets the time for a corpse to get revived"));
        p.sendMessage(format("&e/setup treasure hunter res time set <time in seconds>: &fSets the time a player takes to revive a corpse"));
        p.sendMessage(format("&e/setup treasure hunter res radius set <time in seconds>: &fSets the radius in which a player can revive a corpse"));
        p.sendMessage(format("&e/setup treasure hunter ship ready time set <time in seconds>: &fSets the time the ship takes to get ready"));
        p.sendMessage(format("&e/setup treasure hunter prices set <Revolver Price> <Bullet Price> <Bullet Amount> <Medkit Price> <Ship Price> <Multirevive Price>: &fSets the prices in the shop"));
        p.sendMessage(format("&e/setup treasure hunter add map: &fAdds a new Treasure Hunter Map"));
        p.sendMessage(format("&e/setup treasure hunter map <Map Number> custommodeldata set <CustomModelData>: &fSets the CustomModelData for the preview item for the given Map"));
        p.sendMessage(format("&e/setup treasure hunter map <Map Number> blue spawn set <x> <y> <z>: &fSets the Spawnpoint for Blue Team"));
        p.sendMessage(format("&e/setup treasure hunter map <Map Number> red spawn set <x> <y> <z>: &fSets the Spawnpoint for Red Team"));
        p.sendMessage(format("&e/setup treasure hunter map <Map Number> treasure spawn set <Spawn Number> <x> <y> <z> <rotation>: &fSets the specific treasure spawnpoint"));
        p.sendMessage(format("&e/setup treasure hunter map <Map Number> shop spawn set <Shop Number> <x> <y> <z>: &fSets the specific shop spawnpoint"));
    }

    public void GameInstances(int amount, Player p) {
        if (p != null) {
            p.sendMessage(format("&aGame Instances set to " + amount));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("InstanceCount").setScore(amount);
    }

    public void QueueSignLocation(int x, int y, int z, Player p) {
        if (p != null) {
            p.sendMessage(format("&aQueue Sign for Treasure Hunter set to " + x + " " + y + " " + z));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("QueueSignX").setScore(x);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("QueueSignY").setScore(y);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("QueueSignZ").setScore(z);
    }

    public void TeamSize(int teamsize, Player p) {
        if (p != null) {
            p.sendMessage(format("&aTeam size set to " + teamsize));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("TeamSize").setScore(teamsize);
    }

    public void MapChoosingTime(int time, Player p) {
        if (p != null) {
            p.sendMessage(format("&aMap choosing time set to " + time + " seconds"));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("MapChoosingTime").setScore(time);
    }

    public void RoundTime(int time, Player p) {
        if (p != null) {
            p.sendMessage(format("&aRound time set to " + time + " seconds / " + time / 60 + " minutes"));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("RoundTime").setScore(time);
    }

    public void TreasureAmount(int amount, Player p) {
        if (p != null) {
            p.sendMessage(format("&aTreasure Amount set to " + amount));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureAmount").setScore(amount);
    }

    public void TreasureTime(int time, Player p) {
        if (p != null) {
            p.sendMessage(format("&aTreasure Time set to " + time + " seconds"));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureTime").setScore(time);
    }

    public void TreasureRadius(int radius, Player p) {
        if (p != null) {
            p.sendMessage(format("&aTreasure Radius set to " + radius));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("TreasureRadius").setScore(radius);
    }

    public void ReviveTime(int time, Player p) {
        if (p != null) {
            p.sendMessage(format("&aRevive Time for all Players set to " + time + " seconds / " + time / 60 + " minutes"));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("ReviveTime").setScore(time);
    }

    public void ResTime(int time, Player p) {
        if (p != null) {
            p.sendMessage(format("&aRes Time for all Players set to " + time + " seconds / " + time / 60 + " minutes"));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("ResTime").setScore(time);
    }

    public void ResRadius(int radius, Player p) {
        if (p != null) {
            p.sendMessage(format("&aRes Radius set to " + radius));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("ResRadius").setScore(radius);
    }
    
    public void ShipReadyTime(int time, Player p) {
        if (p != null) {
            p.sendMessage(format("&aShip Ready Time set to " + time + " seconds / " + time / 60 + "minutes"));
        }
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("SchiffReadyTime").setScore(time);
    }

    public void setPrices(int RevolverPrice, int BulletPrice, int BulletAmount, int MedkitPrice, int SchiffPrice, int MultirevivePrice, Player p) {
        if (p != null) {
            p.sendMessage(format("&aPrices set to " + RevolverPrice + " " + BulletPrice + ":" + BulletAmount + " " + MedkitPrice + " " + SchiffPrice + " " + MultirevivePrice));
        }
         Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("RevolverPrice").setScore(RevolverPrice);
         Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletPrice").setScore(BulletPrice);
        Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("BulletAmount").setScore(BulletAmount);
         Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("MedkitPrice").setScore(MedkitPrice);
         Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("SchiffPrice").setScore(SchiffPrice);
         Objects.requireNonNull(mainScoreboard.getObjective("CTreasureHunter")).getScore("MultirevivePrice").setScore(MultirevivePrice);
    }

    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
