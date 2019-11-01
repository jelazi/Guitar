package lubin.guitar.Users;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lubin.guitar.Files.FileManager;
import lubin.guitar.Song.Songs;

public class SingletonManagerUsers {
    public static List<User> listUsers;
    private static SharedPreferences sharedPreferences;
    private static String lastNameUser;
    private static int newID;
    private static User currentUser;


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
        listNamesUsers.add("New User");
        return listNamesUsers;
    }

    public static boolean [] getCheckedDataCurrentUser(ArrayList<String> list, UserList userList) {
        switch (userList) {
            case FRETSLIST: {
                boolean [] checkedFrets = new boolean[list.size()];
                for (int i = 0; i < checkedFrets.length; i++) {
                    String nameFret = list.get(i);
                    if (currentUser.getAllowedFrets().contains(nameFret)) checkedFrets[i] = true;
                }
                return checkedFrets;
            }
            case SONGSLIST: {
                boolean [] checkedSongs = new boolean[list.size()];
                for (int i = 0; i < checkedSongs.length; i++) {
                    String nameSong = list.get(i);
                    if (currentUser.getAllowedSongs().contains(nameSong)) checkedSongs[i] = true;
                }
                return checkedSongs;
            }
            case BACKGROUNDSLIST: {
                boolean [] checkedBackground = new boolean[list.size()];
                for (int i = 0; i < checkedBackground.length; i++) {
                    String nameBackground = list.get(i);
                    if (currentUser.getAllowedFrets().contains(nameBackground)) checkedBackground[i] = true;
                }
                return checkedBackground;
            }
            case INSTRUMENTSLIST: {
                boolean [] checkedInstruments = new boolean[list.size()];
                for (int i = 0; i < checkedInstruments.length; i++) {
                    String nameInstrument = list.get(i);
                    if (currentUser.getAllowedInstruments().contains(nameInstrument)) checkedInstruments[i] = true;
                }
                return checkedInstruments;
            }
            default: {

            }

        }
        return null;
    }

    public static List<String> getWrongDataCurrentUser(Context context, UserList userList) {
        switch (userList) {
            case FRETSLIST: {
                FileManager.loadData(context);
                List<String> realNameFrets = FileManager.getNameFrets();
                List<String> nameFretsCurrentUser = currentUser.getAllowedFrets();
                List<String> wrongFrets = new ArrayList<>();
                for (String nameFretUser : nameFretsCurrentUser) {
                    if (!realNameFrets.contains(nameFretUser)) wrongFrets.add(nameFretUser);
                }
                return wrongFrets;
            }
            case SONGSLIST: {
                FileManager.loadData(context);
                List<String> realNameSongs = FileManager.getNameSongs();
                List<String> nameSongsCurrentUser = currentUser.getAllowedSongs();
                List<String> wrongSongs = new ArrayList<>();
                for (String nameSongsUser : nameSongsCurrentUser) {
                    if (!realNameSongs.contains(nameSongsUser)) wrongSongs.add(nameSongsUser);
                }
                return wrongSongs;
            }
            case BACKGROUNDSLIST: {
                FileManager.loadData(context);
                List<String> realNameBackgrounds = FileManager.getNameBackgrounds();
                List<String> nameBackgroundsCurrentUser = currentUser.getAllowedBackgrounds();
                List<String> wrongBackgrounds = new ArrayList<>();
                for (String nameBackgroundUser : nameBackgroundsCurrentUser) {
                    if (!realNameBackgrounds.contains(nameBackgroundUser)) wrongBackgrounds.add(nameBackgroundUser);
                }
                return wrongBackgrounds;
            }
            case INSTRUMENTSLIST: {
                FileManager.loadData(context);
                List<String> realNameInstruments = FileManager.getNameInstruments();
                List<String> nameInstrumentsCurrentUser = currentUser.getAllowedInstruments();
                List<String> wrongInstruments = new ArrayList<>();
                for (String nameInstrumentsUser : nameInstrumentsCurrentUser) {
                    if (!realNameInstruments.contains(nameInstrumentsUser)) wrongInstruments.add(nameInstrumentsUser);
                }
                return wrongInstruments;
            }
            default: {
            }
        }
        return null;

    }

    public static void createNewUser (String nameUser) {
        List<String> listSongs = Arrays.asList();
        List<String> listInstruments = Arrays.asList();
        List<String> listFrets = Arrays.asList();
        List<String> listBackgrounds = Arrays.asList();

        User user = new User(nameUser, 0, "", listSongs, listInstruments, listFrets, listBackgrounds, true);
        addUser(user);
        saveToSharedPreferences(sharedPreferences);

    }

    public static void eraseWrongDataCurrentUser(Context context, UserList userList) {
        switch (userList) {
            case FRETSLIST: {
                List<String> wrongFrets = getWrongDataCurrentUser(context, UserList.FRETSLIST);
                List<String> rightFrets = new ArrayList<>();
                for (int i = 0; i < currentUser.getAllowedFrets().size(); i++) {
                    if (!wrongFrets.contains(currentUser.getAllowedFrets().get(i))) {
                        rightFrets.add(currentUser.getAllowedFrets().get(i));
                    }
                }
                currentUser.setAllowedFrets(rightFrets);
                changeUser(currentUser);
                break;
            }
            case SONGSLIST: {
                List<String> wrongSongs = getWrongDataCurrentUser(context, UserList.SONGSLIST);
                List<String> rightSongs = new ArrayList<>();
                for (int i = 0; i < currentUser.getAllowedSongs().size();i++) {
                    if (!wrongSongs.contains(currentUser.getAllowedSongs().get(i))) {
                        rightSongs.add(currentUser.getAllowedSongs().get(i));
                    }
                }
                currentUser.setAllowedSongs(rightSongs);
                changeUser(currentUser);
                break;
            }
            case BACKGROUNDSLIST: {
                List<String> wrongBackgrounds = getWrongDataCurrentUser(context, UserList.BACKGROUNDSLIST);
                List<String> rightBackgrounds = new ArrayList<>();
                for (int i = 0; i < currentUser.getAllowedBackgrounds().size();i++) {
                    if (!wrongBackgrounds.contains(currentUser.getAllowedBackgrounds().get(i))) {
                        rightBackgrounds.add(currentUser.getAllowedBackgrounds().get(i));
                    }
                }
                currentUser.setAllowedBackgrounds(rightBackgrounds);
                changeUser(currentUser);
                break;
            }
            case INSTRUMENTSLIST: {
                List<String> wrongInstruments = getWrongDataCurrentUser(context, UserList.INSTRUMENTSLIST);
                ArrayList<String> rightInstruments = new ArrayList<>();
                for (int i = 0; i < currentUser.getAllowedInstruments().size();i++) {
                    if (!wrongInstruments.contains(currentUser.getAllowedInstruments().get(i))) {
                        rightInstruments.add(currentUser.getAllowedInstruments().get(i));
                    }
                }
                currentUser.setAllowedInstruments(rightInstruments);
                changeUser(currentUser);
                break;
            }
            default: {
            }
        }
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

    public static void changeUser (User user) {
        User newUser = new User(user);
        listUsers.remove(user);
        listUsers.add(newUser);
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

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        SingletonManagerUsers.currentUser = currentUser;
    }
}
