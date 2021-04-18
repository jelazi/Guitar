package lubin.guitar.Shop;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class SingletonManagerItems {
    private static List<Item> itemList;
    private static SharedPreferences sharedPreferences;

    public static void createSingletonManagerItems (SharedPreferences sharedPref) {
        SingletonManagerItems.setSharedPreferences(sharedPref);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("items", "");
        Type type = new TypeToken<List<Item>>(){}.getType();
        itemList = gson.fromJson(json, type);
        if (itemList == null || itemList.size() == 0) {
            itemList = new ArrayList<Item>();
            saveToSharedPreferences();

        }
    }

    public static void saveToSharedPreferences () {
        Gson gson = new Gson();
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        String jsonNew = gson.toJson(itemList);
        prefsEditor.putString("items", jsonNew);
        try {
            prefsEditor.commit();
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
    }

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        SingletonManagerItems.sharedPreferences = sharedPreferences;
    }

    public static List<Item> getItemList() {
        SingletonManagerItems.controlItemList();
        return itemList;
    }

    public static boolean removeAllItems () {
        itemList = new ArrayList<>();
        saveToSharedPreferences();
        return true;
    }

    public static List<String> getNameItems () {
        List<String> nameItems = new ArrayList<>();
        for (Item item : itemList) {
            nameItems.add(item.getName());
        }
        return nameItems;
    }

    public static Item getItemByName (String name) {
        for (Item item :itemList) {
            if (item.getName().equals(name)) return item;
        }
        return null;
    }

    public static boolean removeItem (Item item) {
        if (!itemList.remove(item)) return false;
        saveToSharedPreferences();
        return true;
    }

    public static boolean addItem (Item item) {
        if (!item.isCorrectValueItem()) return false;
        if (!itemList.add(item)) return false;
        saveToSharedPreferences();
        return true;
    }

    public static void changeItem (Item item) {
        Item newItem = new Item(item);
        String path = item.getFile().getPath();
        int index = -1;
        for (int i = 0; i < itemList.size();i++) {
            if (itemList.get(i).getFile().getPath().equals(path)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            Log.e("Error", "User doesnt exists");
            return;
        }
        if (index >= 0) {
            itemList.remove(index);
        } else {
            Log.e("Error", "problem users id");
            return;
        }
        itemList.add(newItem);
        saveToSharedPreferences();
    }

    public static boolean containsItem (File file) {
        for (Item item : itemList) {
            if (item.getFile().getPath().equals(file.getPath())) return true;
        }
        return false;
    }



    protected static void controlItemList () {
        for (Item item : itemList) {
            File file = item.getFile();
            if (!file.exists()) {
                itemList.remove(item);
            }
        }
    }

    public static void setItemList(List<Item> itemList) {
        SingletonManagerItems.itemList = itemList;
        saveToSharedPreferences();
    }
}
