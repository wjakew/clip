/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.maintanance;

import java.util.ArrayList;

import com.jakubwawak.clip.database.DatabaseClip;
import com.jakubwawak.clip.entity.Clip;
import com.jakubwawak.clip.frontend.components.ClipCard;

/**
 * LibraryContentEngine class for the Clip application
 */
public class LibraryContentEngine {

    public ArrayList<Clip> content;

    public ArrayList<ClipCard> clipCards;

    DatabaseClip databaseClip;

    int endIndex;

    static int pageSize = 30;

    /**
     * Constructor for the LibraryContentEngine class
     */
    public LibraryContentEngine() {

        content = new ArrayList<>();
        clipCards = new ArrayList<>();

        databaseClip = new DatabaseClip();
        endIndex = databaseClip.getClipAmount();
        content = databaseClip.getSelectedAmountOfClips(0, endIndex);
    }

    /**
     * Function for calculating the amount of pages
     * 
     * @return the amount of pages
     */
    public int calculatePagesAmount() {
        return (int) Math.ceil((double) endIndex / pageSize);
    }

    /**
     * Function for loading the clip cards
     * 
     * @param page - the page to load
     */
    public void loadClipCards(int page) {
        int startIndex = page * pageSize;
        int endIndex = startIndex + pageSize;
        clipCards.clear();

        for (int i = startIndex; i < endIndex; i++) {
            clipCards.add(new ClipCard(content.get(i)));
        }
    }

}
