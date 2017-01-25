package com.tutorials.hp.sqlitegridviewpagination.mAdapterView;

import com.tutorials.hp.sqlitegridviewpagination.mDB.Spacecraft;

import java.util.ArrayList;
import java.util.List;

import co.uk.rushorm.core.RushSearch;

/**
 * Created by Oclemy on 12/16/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class Paginator {


    private int TOTAL_NUM_ITEMS;
    private int ITEMS_PER_PAGE;

    public Paginator() {
         TOTAL_NUM_ITEMS= (int) new RushSearch().count(Spacecraft.class);
         ITEMS_PER_PAGE=5;
    }

    /*
    TOTAL NUMBER OF PAGES
     */
    public int getTotalPages()
    {
        return TOTAL_NUM_ITEMS/ITEMS_PER_PAGE;
    }

    /*
    CURRENT PAGE SPACECRAFTS LIST
     */
    public List<Spacecraft> getCurrentSpacecrafts(int currentPage)
    {
        int startItem=currentPage*ITEMS_PER_PAGE;
        List<Spacecraft> currentSpacecrafts=new ArrayList<>();
        try
        {
            currentSpacecrafts=new RushSearch().limit(ITEMS_PER_PAGE).offset(startItem).find(Spacecraft.class);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return currentSpacecrafts;
    }


}
