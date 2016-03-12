package de.canitzp.rarmor;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author canitzp
 */
public class RarmorProperties extends Properties{

    private static File propFile = new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config" + File.separator + "Rarmor.properties");
    public static RarmorProperties rarmorProperties;
    private static Map<String, Boolean> booleanProperties = new HashMap<>();
    private static Map<String, Integer> integerProperties = new HashMap<>();

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(new TreeSet<Object>(super.keySet()));
    }

    public static void preInit(FMLPreInitializationEvent event){
        rarmorProperties = new RarmorProperties();
        rarmorProperties.readProperties();
    }

    public void saveProperties(){
        try {
            for(Map.Entry<String, Boolean> entry : booleanProperties.entrySet()){
                rarmorProperties.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            for(Map.Entry<String, Integer> entry : integerProperties.entrySet()){
                rarmorProperties.put(entry.getKey(), String.valueOf(entry.getValue()));
            }
            rarmorProperties.store(new FileOutputStream(propFile), "These are the Properties of Rarmor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readProperties(){
        try {
            rarmorProperties.load(new FileInputStream(propFile));
            for(Map.Entry<Object, Object> entry : entrySet()){
                if(this.isEntryBoolean((String) entry.getValue())){
                    booleanProperties.put((String) entry.getKey(), Boolean.getBoolean((String) entry.getValue()));
                }
                if(this.isEntryInteger((String) entry.getValue())){
                    integerProperties.put((String) entry.getKey(), Integer.parseInt((String) entry.getValue()));
                }
            }
        } catch (IOException e) {
            Rarmor.logger.info("Can't find Rarmor.properties creating new ones.");
        }
        initProperties();
    }

    private void initProperties(){
        addInteger("maxRarmorTransferPerTick", 20);
        addInteger("moduleDefenseDamageMultiplier", 2500);
        addInteger("moduleFlyingEnergyPerTick", 5);
        addInteger("moduleSolarEnergyPerTick", 5);

        saveProperties();
    }

    private void addBoolean(String key, boolean value){
        if(!booleanProperties.containsKey(key)){
            booleanProperties.put(key, value);
        }
    }

    public static boolean getBoolean(String key){
        return booleanProperties.containsKey(key) && booleanProperties.get(key);
    }

    private void addInteger(String key, int value){
        if(!integerProperties.containsKey(key)){
            integerProperties.put(key, value);
        }
    }

    public static int getInteger(String key){
        return integerProperties.containsKey(key) ? integerProperties.get(key) : 0;
    }

    private boolean isEntryBoolean(String entry){
        return entry.equals("true") || entry.equals("false");
    }

    private boolean isEntryInteger(String entry){
        try {
            Integer.parseInt(entry);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }

}