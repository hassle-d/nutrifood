package com.nutrifood.cisner_d.nutrifood.Main;

public class ListItem {
    public String _title = null;
    public String _subtitle = null;
    public int    _type = 0;

    public ListItem(String title, String subtitle, int type)
    {
        _title = title;
        _subtitle = subtitle;
        _type = type;
    }

    public ListItem(String title, String subtitle)
    {
        _title = title;
        _subtitle = subtitle;
    }

    public ListItem(String title)
    {
        _title = title;
    }
}
