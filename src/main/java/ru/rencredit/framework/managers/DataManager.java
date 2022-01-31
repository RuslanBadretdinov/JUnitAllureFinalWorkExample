package ru.rencredit.framework.managers;

import java.util.ArrayList;

public class DataManager {
    private static DataManager dataManager;
    private ArrayList<DataManager> list;

    private DataManager() {
    }

    public static DataManager getDataManager() {
        if (dataManager == null) {
            dataManager = new DataManager();
            dataManager.list = new ArrayList<>();
        }
        return dataManager;
    }
}
