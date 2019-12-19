package lubin.guitar.Files;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import lubin.guitar.R;

public class FileDialog {
    private static final String PARENT_DIR = "..";
    private final String TAG = getClass().getName();
    private String[] fileList;
    private File currentPath;
    public interface FileSelectedListener {
        void fileSelected(File file);
    }
    public interface DirectorySelectedListener {
        void directorySelected(File directory);
    }
    private ListenerList<FileSelectedListener> fileListenerList = new ListenerList<FileDialog.FileSelectedListener>();
    private ListenerList<DirectorySelectedListener> dirListenerList = new ListenerList<FileDialog.DirectorySelectedListener>();
    private final Activity activity;
    private boolean selectDirectoryOption;
    private String fileEndsWith;
    private String fileEndsWith2;
    private File initialPath;
    AlertDialog.Builder builder;
    DialogType dialogType;
    public ArrayList<String> checkedFilesNames;
    public ArrayList<String> uncheckedFilesNames;
    Dialog dialog;
    boolean isOnlyOne;


    ArrayList<File> checkedFiles;

    public FileDialog(Activity activity, File initialPath, DialogType dialogType) {
        this(activity, initialPath, null, dialogType);
    }

    public FileDialog(Activity activity, File initialPath, String fileEndsWith, DialogType dialogType) {
        isOnlyOne = true;
        this.activity = activity;
        this.dialogType = dialogType;
        setFileEndsWith(fileEndsWith, 1);
        if (!initialPath.exists()) initialPath = Environment.getExternalStorageDirectory();
        this.initialPath = initialPath;
        loadFileList(initialPath);
    }

    public FileDialog(Activity activity, File initialPath, String fileEndsWith, String fileEndsWith2, DialogType dialogType) {
        isOnlyOne = false;
        this.activity = activity;
        this.dialogType = dialogType;
        setFileEndsWith(fileEndsWith, 1);
        setFileEndsWith(fileEndsWith2, 2);
        if (!initialPath.exists()) initialPath = Environment.getExternalStorageDirectory();
        this.initialPath = initialPath;
        loadFileList(initialPath);
    }


    public void createFileDialog(final String title) {
        if (dialogType == DialogType.FILE_DIALOG_MIDI) {
            dialog = null;
            builder = new AlertDialog.Builder(activity);
            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);

            TextView titleView = new TextView(activity);
            titleView.setText(title);
            titleView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            titleView.setPadding(10, 10, 10, 10);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            titleView.setTextSize(30);

            layout.addView(titleView);

            TextView subtitleView = new TextView(activity);
            subtitleView.setText(activity.getResources().getString(R.string.only_midi_files));
            subtitleView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            subtitleView.setPadding(10, 10, 10, 10);
            subtitleView.setGravity(Gravity.CENTER);
            subtitleView.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            layout.addView(subtitleView);

            builder.setCustomTitle(layout);

            if (selectDirectoryOption) {
                builder.setPositiveButton(activity.getResources().getString(R.string.select_directory), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fireDirectorySelectedEvent(currentPath);
                    }
                });
            }

            builder.setItems(fileList, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String fileChosen = fileList[which];
                    File chosenFile = getChosenFile(fileChosen);
                    if (chosenFile.isDirectory()) {
                        loadFileList(chosenFile);
                        dialog.cancel();
                        dialog.dismiss();
                        createFileDialog(title);
                        showDialog();
                    } else fireFileSelectedEvent(chosenFile);
                }
            });
            dialog = builder.create();

        }

        if (dialogType == DialogType.FILE_DIALOG_WAV) {
            dialog = null;
            builder = new AlertDialog.Builder(activity);

            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);


            TextView titleView = new TextView(activity);
            titleView.setText(title);
            titleView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            titleView.setPadding(10, 10, 10, 10);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            titleView.setTextSize(30);

            layout.addView(titleView);

            TextView subtitleView = new TextView(activity);
            subtitleView.setText(activity.getResources().getString(R.string.only_wav_files));
            subtitleView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            subtitleView.setPadding(10, 10, 10, 10);
            subtitleView.setGravity(Gravity.CENTER);
            subtitleView.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            layout.addView(subtitleView);

            builder.setCustomTitle(layout);

            if (selectDirectoryOption) {
                builder.setPositiveButton(activity.getResources().getString(R.string.select_directory), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fireDirectorySelectedEvent(currentPath);
                    }
                });
            }


            builder.setItems(fileList, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String fileChosen = fileList[which];
                    File chosenFile = getChosenFile(fileChosen);
                    if (chosenFile.isDirectory()) {
                        loadFileList(chosenFile);
                        dialog.cancel();
                        dialog.dismiss();
                        createFileDialog(title);
                        showDialog();
                    } else fireFileSelectedEvent(chosenFile);
                }
            });


            dialog = builder.create();

        }

        if (dialogType == DialogType.FILE_DIALOG_IMAGE) {
            dialog = null;
            builder = new AlertDialog.Builder(activity);

            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);


            TextView titleView = new TextView(activity);
            titleView.setText(title);
            titleView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            titleView.setPadding(10, 10, 10, 10);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            titleView.setTextSize(30);

            layout.addView(titleView);

            TextView subtitleView = new TextView(activity);
            subtitleView.setText(activity.getResources().getString(R.string.only_png_files));
            subtitleView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            subtitleView.setPadding(10, 10, 10, 10);
            subtitleView.setGravity(Gravity.CENTER);
            subtitleView.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            layout.addView(subtitleView);

            builder.setCustomTitle(layout);

            if (selectDirectoryOption) {
                builder.setPositiveButton(activity.getResources().getString(R.string.select_directory), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fireDirectorySelectedEvent(currentPath);
                    }
                });
            }


            builder.setItems(fileList, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String fileChosen = fileList[which];
                    File chosenFile = getChosenFile(fileChosen);
                    if (chosenFile.isDirectory()) {
                        loadFileList(chosenFile);
                        dialog.cancel();
                        dialog.dismiss();
                        createFileDialog(title);
                        showDialog();
                    } else fireFileSelectedEvent(chosenFile);
                }
            });


            dialog = builder.create();

        }

        if (dialogType == DialogType.OPEN_FILE) {
            dialog = null;
            builder = new AlertDialog.Builder(activity);

            LinearLayout layout = new LinearLayout(activity);
            layout.setOrientation(LinearLayout.VERTICAL);


            TextView titleView = new TextView(activity);
            titleView.setText(title);
            titleView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            titleView.setPadding(10, 10, 10, 10);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            titleView.setTextSize(30);

            layout.addView(titleView);

            TextView subtitleView = new TextView(activity);
            subtitleView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            subtitleView.setPadding(10, 10, 10, 10);
            subtitleView.setGravity(Gravity.CENTER);
            subtitleView.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            layout.addView(subtitleView);

            builder.setCustomTitle(layout);

            if (selectDirectoryOption) {
                builder.setPositiveButton(activity.getResources().getString(R.string.select_directory), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fireDirectorySelectedEvent(currentPath);
                    }
                });
            }


            builder.setItems(fileList, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String fileChosen = fileList[which];
                    File chosenFile = getChosenFile(fileChosen);
                    if (chosenFile.isDirectory()) {
                        loadFileList(chosenFile);
                        dialog.cancel();
                        dialog.dismiss();
                        createFileDialog(title);
                        showDialog();
                    } else fireFileSelectedEvent(chosenFile);
                }
            });


            dialog = builder.create();

        }

        if (dialogType == DialogType.FILE_DIALOG_MULTI) {
            dialog = null;
            builder = new AlertDialog.Builder(activity);
            checkedFilesNames = new ArrayList<>();
            uncheckedFilesNames = new ArrayList<>();
            if (fileList != null || fileList.length != 0) {
                for (int i = 0; i < fileList.length; i++) {
                    uncheckedFilesNames.add(fileList[i]);
                }
            }
            TextView titleView = new TextView(activity);
            titleView.setText(title);
            titleView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            titleView.setPadding(10, 10, 10, 10);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTextColor(activity.getResources().getColor(R.color.colorWhite));
            titleView.setTextSize(30);

            builder.setCustomTitle(titleView);

            boolean[] checkedItems = new boolean[fileList.length];
            builder.setMultiChoiceItems(fileList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    fillCheckedLists (isChecked, which);
                    if (checkedFilesNames.size() == 0 && ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE) != null) {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    } else {
                        ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                }
            });

            dialog = builder.create();
        }
    }


    public ArrayList<File> getCheckedFiles() {
        checkedFiles = new ArrayList<>();
        for (String fileName : checkedFilesNames) {
            File file = new File(this.initialPath.getPath() + "/" + fileName);
            checkedFiles.add(file);
        }
        return checkedFiles;
    }

    public void fillCheckedLists (boolean isChecked, int which) {
        String nameFile = fileList[which];
        if (isChecked) {
            checkedFilesNames.add(nameFile);
            for (int i = 0; i < uncheckedFilesNames.size(); i++) {
                if (uncheckedFilesNames.get(i).equals(nameFile)) {
                    uncheckedFilesNames.remove(i);
                }
            }
        } else {
            uncheckedFilesNames.add(nameFile);
            for (int i = 0; i < checkedFilesNames.size(); i++) {
                if (checkedFilesNames.get(i).equals(nameFile)) {
                    checkedFilesNames.remove(i);
                }
            }

        }
    }

    public void addButtons (String titlePositiveBtn, DialogInterface.OnClickListener onClickListenerPositiveBtn, String titleNegativeBtn, DialogInterface.OnClickListener onClickListenerNegativeBtn) {
        if (titlePositiveBtn != null) {
            builder.setPositiveButton(titlePositiveBtn, onClickListenerPositiveBtn);

        }
        if (titleNegativeBtn != null) {
            builder.setNegativeButton(titleNegativeBtn, onClickListenerNegativeBtn);
        }
        dialog = builder.create();
    }




    public void addFileListener(FileSelectedListener listener) {
        fileListenerList.add(listener);
    }

    public void removeFileListener(FileSelectedListener listener) {
        fileListenerList.remove(listener);
    }

    public void setSelectDirectoryOption(boolean selectDirectoryOption) {
        this.selectDirectoryOption = selectDirectoryOption;
    }

    public void addDirectoryListener(DirectorySelectedListener listener) {
        dirListenerList.add(listener);
    }

    public void removeDirectoryListener(DirectorySelectedListener listener) {
        dirListenerList.remove(listener);
    }

    public void showDialog() {
        dialog.show();
        if (dialogType == DialogType.FILE_DIALOG_MULTI && ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE) != null) {
            ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        }
    }

    private void fireFileSelectedEvent(final File file) {
        fileListenerList.fireEvent(new ListenerList.FireHandler<FileSelectedListener>() {
            public void fireEvent(FileSelectedListener listener) {
                listener.fileSelected(file);
            }
        });
    }

    private void fireDirectorySelectedEvent(final File directory) {
        dirListenerList.fireEvent(new ListenerList.FireHandler<DirectorySelectedListener>() {
            public void fireEvent(DirectorySelectedListener listener) {
                listener.directorySelected(directory);
            }
        });
    }

    private void loadFileList(File path) {
        this.currentPath = path;
        List<String> noSorted = new ArrayList<String>();
        List<String> sorted = new ArrayList<String>();
        List<String> directories = new ArrayList<String>();
        if (path.exists()) {

            if (dialogType != DialogType.OPEN_FILE && path.getParentFile() != null) sorted.add(PARENT_DIR);
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    if (!sel.canRead()) return false;
                    if (selectDirectoryOption) return sel.isDirectory();
                    else {
                        boolean endsWith = fileEndsWith != null ? filename.toLowerCase().endsWith(fileEndsWith) : true;
                        if (isOnlyOne) {
                            return endsWith || sel.isDirectory();
                        } else {
                            boolean endsWith2 = fileEndsWith2 != null ? filename.toLowerCase().endsWith(fileEndsWith2) : true;
                            return endsWith || endsWith2 || sel.isDirectory();
                        }
                    }
                }
            };
            String[] fileList1 = path.list(filter);
            for (String file : fileList1) {
                noSorted.add(file);
            }
        }
        for (String file : noSorted) {
            if (!file.contains(".")) {
               sorted.add(file);
            } else {
                directories.add(file);
            }
        }
        sorted.addAll(directories);
        fileList = (String[]) sorted.toArray(new String[]{});
    }

    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) return currentPath.getParentFile();
        else return new File(currentPath, fileChosen);
    }

    private void setFileEndsWith(String fileEndsWith, int fileNumber) {
        if (fileNumber == 1) {
            this.fileEndsWith = fileEndsWith != null ? fileEndsWith.toLowerCase() : fileEndsWith;
            return;
        }
        if (fileNumber == 2) {
            this.fileEndsWith2 = fileEndsWith != null ? fileEndsWith.toLowerCase() : fileEndsWith;
        }
    }
}

class ListenerList<L> {
    private List<L> listenerList = new ArrayList<L>();

    public interface FireHandler<L> {
        void fireEvent(L listener);
    }

    public void add(L listener) {
        listenerList.add(listener);
    }

    public void fireEvent(FireHandler<L> fireHandler) {
        List<L> copy = new ArrayList<L>(listenerList);
        for (L l : copy) {
            fireHandler.fireEvent(l);
        }
    }

    public void remove(L listener) {
        listenerList.remove(listener);
    }

    public List<L> getListenerList() {
        return listenerList;
    }
}
