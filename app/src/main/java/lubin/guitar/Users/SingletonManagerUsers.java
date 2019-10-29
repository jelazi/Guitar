package lubin.guitar.Users;

import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class SingletonManagerUsers {
    public static List<User> listUsers;
    private static SharedPreferences sharedPreferences;
    private static String lastNameUser;
    private static int newID;


    public static void createSingletonManagerUsers(SharedPreferences sharedPref) {
        SingletonManagerUsers.setSharedPreferences(sharedPref);
        Gson gson = new Gson();
        String json = sharedPref.getString("users", "");
        Type type = new TypeToken<List<User>>(){}.getType();
        listUsers = gson.fromJson(json, type);
        if (listUsers == null || listUsers.size() == 0) {
            listUsers = new ArrayList<User>();
            saveToSharedPreferences(sharedPref);

        }

        rearangeUsers(sharedPref);
        lastNameUser = sharedPref.getString("lastNameUser", "");

    }

    public static boolean isUniqueNameUser (String nameUser) {
        if (nameUser.equals("")) return false;
        if (listUsers == null || listUsers.size() == 0) return true;
        for (User user : listUsers) {
            if (user.getName().equals(nameUser)) return false;
        }
        return true;
    }

    public static User getUserByID (int id) {
        for (User user : listUsers) {
            if (user.getID() == id) {
                return user;
            }
        }
        return null;
    }

    public static User getUserByName (String name) {
        if (listUsers == null || listUsers.size() == 0) return null;
        for (User user :listUsers) {
            if (user.getName().equals(name)) return user;
        }
        return null;
    }

    public static List<String> getListNamesUsers () {
        List <String> listNamesUsers = new ArrayList<String>();
        if (listUsers == null || listUsers.size() == 0) return listNamesUsers;
        for (User user :listUsers) {
            listNamesUsers.add(user.getName());
        }
        return listNamesUsers;
    }


    public static int getNewID() {
        if (listUsers == null || listUsers.size() == 0) {
            newID = 1;
        } else {
            for (User user : listUsers) {
                if (user.getID() > newID) newID = user.getID();
            }
            newID++;
        }
        return newID;
    }

    public static boolean removeAllUsers (SharedPreferences sharedPreferences) {
        listUsers = new ArrayList <>();
        saveToSharedPreferences(sharedPreferences);
        return true;
    }

    public static void addUser (User user) {
        if (isUserID(user.getID()) || isUserName(user.getName())) {
            System.out.println("id or name already exists");
            return;
        }
        listUsers.add(user);
        saveToSharedPreferences(sharedPreferences);
    }

    private static boolean isUserID (int id) {
        for (User user : listUsers) {
            if (user.getID() == id) {
                return true;
            }
        }
        return false;
    }

    private static boolean isUserName (String name) {
        for (User user : listUsers) {
            if (user.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        SingletonManagerUsers.sharedPreferences = sharedPreferences;
    }

    public static String getLastNameUser() {
        return lastNameUser;
    }

    public static void setLastNameUser(String lastNameUser) {
        SingletonManagerUsers.lastNameUser = lastNameUser;
    }

    public static void saveToSharedPreferences (SharedPreferences sharedPref) {
        Gson gson = new Gson();
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        String jsonNew = gson.toJson(listUsers);
        prefsEditor.putString("users", jsonNew);
        try {
            prefsEditor.commit();
        } catch (Exception e) {
            Log.e("error", e.toString());
        }
        prefsEditor.putString("lastNameUser", getLastNameUser());
        prefsEditor.commit();
    }

    private static void rearangeUsers (SharedPreferences sharedPreferences) {
        for (int i = 0; i < listUsers.size(); i++) {
            User user = listUsers.get(i);
            user.setID(i+1);

        }
        newID = listUsers.size() + 1;
        saveToSharedPreferences(sharedPreferences);

    }



}