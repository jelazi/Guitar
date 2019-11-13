package lubin.guitar.Users;

import android.app.Activity;
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
import lubin.guitar.R;
import lubin.guitar.Song.Song;
import lubin.guitar.Song.Songs;

public class SingletonManagerUsers {
    public static List<User> listUsers;
    private static SharedPreferences sharedPreferences;
    private static String lastNameUser;
    private static int newID;
    private static User currentUser;
    private static Song currentSong;


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

    public static boolean isUniqueNameUser (String nameUser, Activity activity) {
        if (nameUser.equals("")) return false;
        if (nameUser.equals(activity.getResources().getString(R.string.new_user))) return false;
        if (listUsers == null || listUsers.size() == 0) return true;
        for (User user : listUsers) {
            if (user.getName().equals(nameUser)) return false;
        }
        return true;
    }

    public static boolean isUniqueNameUser (String nameUser, int ID, Activity activity) {
        if (nameUser.equals("")) return false;
        if (nameUser.equals(activity.getResources().getString(R.string.new_user))) return false;
        if (nameUser.contains("user")) return false;
        if (listUsers == null || listUsers.size() == 0) return true;
        for (User user : listUsers) {
            if (user.getName().equals(nameUser) && user.getID() != ID) return false;
        }
        return true;
    }

    public static User getAdminUser () {
        List<String> listSongs = FileManager.getNameSongs();
        List<String> listInstruments = FileManager.getNameInstruments();
        List<String> listFrets = FileManager.getNameFrets();
        List<String> listBackgrounds = FileManager.getNameBackgrounds();
        List<String> listStrings = FileManager.getNameStrings();

        User admin = new User("admin", 1000, "", listSongs, listInstruments, listFrets, listBackgrounds, listStrings, UserLevel.CHAMPION, UserLevel.CHAMPION, true);
        return admin;
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

    public static List<String> getListNamesUsers (boolean withNewUser, Activity activity) {
        List <String> listNamesUsers = new ArrayList<String>();
        if (listUsers == null || listUsers.size() == 0) {
            if (withNewUser) {
                listNamesUsers.add(activity.getResources().getString(R.string.new_user));
            }
            return listNamesUsers;
        }
        for (User user :listUsers) {
            listNamesUsers.add(user.getName());
        }
        if (withNewUser) {
            listNamesUsers.add(activity.getResources().getString(R.string.new_user));
        }
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
                    if (currentUser.getAllowedBackgrounds().contains(nameBackground)) checkedBackground[i] = true;
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
            case STRINGSLIST: {
                boolean [] checkedStrings = new boolean[list.size()];
                for (int i = 0; i < checkedStrings.length; i++) {
                    String nameString = list.get(i);
                    if (currentUser.getAllowedStrings().contains(nameString)) checkedStrings[i] = true;
                }
                return checkedStrings;
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
            case STRINGSLIST: {
                FileManager.loadData(context);
                List<String> realNameStrings = FileManager.getNameStrings();
                List<String> nameStringsCurrentUser = currentUser.getAllowedStrings();
                List<String> wrongStrings = new ArrayList<>();
                for (String nameStringsUser : nameStringsCurrentUser) {
                    if (!realNameStrings.contains(nameStringsUser)) wrongStrings.add(nameStringsUser);
                }
                return wrongStrings;
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
        List<String> listStrings = Arrays.asList();

        User user = new User(nameUser, 0, "", listSongs, listInstruments, listFrets, listBackgrounds, listStrings, UserLevel.BEGINNER, UserLevel.BEGINNER, true);
        addUser(user);
        saveToSharedPreferences(sharedPreferences);
    }

    public static void eraseWrongDataCurrentUser (Context context, UserList userList) {
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
            case STRINGSLIST: {
                List<String> wrongStrings = getWrongDataCurrentUser(context, UserList.STRINGSLIST);
                ArrayList<String> rightStrings = new ArrayList<>();
                for (int i = 0; i < currentUser.getAllowedStrings().size();i++) {
                    if (!wrongStrings.contains(currentUser.getAllowedStrings().get(i))) {
                        rightStrings.add(currentUser.getAllowedStrings().get(i));
                    }
                }
                currentUser.setAllowedInstruments(rightStrings);
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

    public static boolean removeUser (User user) {
        listUsers.remove(user);
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

    public static List<String> getListAllowLevel (UserLevel allowLevel, Activity activity) {
        List<String> allowLevelList = new ArrayList<>();
        allowLevelList.add(getNameUserLevel(UserLevel.BEGINNER, activity));
        if (allowLevel.getValue() >= UserLevel.EXPERT.getValue()) {
            allowLevelList.add(getNameUserLevel(UserLevel.EXPERT, activity));
        }
        if (allowLevel.getValue() >= UserLevel.PROFESSIONAL.getValue()) {
            allowLevelList.add(getNameUserLevel(UserLevel.PROFESSIONAL, activity));
        }
        if (allowLevel.getValue() >= UserLevel.GENIUS.getValue()) {
            allowLevelList.add(getNameUserLevel(UserLevel.GENIUS, activity));
        }
        if (allowLevel.getValue() >= UserLevel.CHAMPION.getValue()) {
            allowLevelList.add(getNameUserLevel(UserLevel.CHAMPION, activity));
        }
        return allowLevelList;
    }

    public static String getNameUserLevel (UserLevel userLevel, Activity activity) {
        switch (userLevel) {
            case BEGINNER: {
                return activity.getResources().getString(R.string.beginner);
            }
            case EXPERT: {
                return activity.getResources().getString(R.string.expert);
            }
            case PROFESSIONAL: {
                return activity.getResources().getString(R.string.professional);
            }
            case GENIUS: {
                return activity.getResources().getString(R.string.genius);
            }
            case CHAMPION: {
                return activity.getResources().getString(R.string.champion);
            }
        }
            return activity.getResources().getString(R.string.beginner);
    }

    public static UserLevel getUserLevelByValue (int value) {
        switch (value) {
            case 1 : {
                return UserLevel.BEGINNER;
            }
            case 2 : {
                return UserLevel.EXPERT;
            }
            case 3 : {
                return UserLevel.PROFESSIONAL;
            }
            case 4 : {
                return UserLevel.GENIUS;
            }
            case 5 : {
                return UserLevel.CHAMPION;
            }
        }
        return UserLevel.BEGINNER;
    }

    public static void changeUser (User user) {
        User newUser = new User(user);
        int id = user.getID();
        int index = -1;
        for (int i = 0; i < listUsers.size();i++) {
            if (listUsers.get(i).getID() == id) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            Log.e("Error", "User doesnt exists");
            return;
        }
        if (index >= 0) {
            listUsers.remove(index);
        } else {
            Log.e("Error", "problem users id");
            return;
        }
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

    public static Song getCurrentSong() {
        return currentSong;
    }

    public static void setCurrentSong(Song currentSong) {
        SingletonManagerUsers.currentSong = currentSong;
    }
}
