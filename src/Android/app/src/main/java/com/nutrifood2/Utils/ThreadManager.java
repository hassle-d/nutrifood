package com.nutrifood2.Utils;

import java.util.ArrayList;
import java.util.List;

public class ThreadManager {
    public static final List<String> THREAD_LIST = new ArrayList<String>();

    public static void addThreadToList(String file)
    {
        THREAD_LIST.add(file);
    }
}
