/**
 * @author Jakub Wawak
 * all rights reserved
 * kubawawak@gmail.com
 */
package com.jakubwawak.clip.maintanance;

import java.util.ArrayList;
import java.util.List;

import com.jakubwawak.clip.database.DatabaseClip;
import com.jakubwawak.clip.entity.Clip;
import com.jakubwawak.clip.frontend.components.ClipCard;

/**
 * LibraryContentEngine class for the Clip application
 */
public class LibraryContentEngine {

    public ArrayList<Clip> content;

    public ArrayList<Clip> currentPage;

    public ArrayList<ClipCard> clipCards;

    DatabaseClip databaseClip;

    static int endIndex = 50;

    static int pageSize = 30;

    /**
     * Constructor for the LibraryContentEngine class
     */
    public LibraryContentEngine() {

        content = new ArrayList<>();
        clipCards = new ArrayList<>();
        currentPage = new ArrayList<>();
        databaseClip = new DatabaseClip();
        endIndex = databaseClip.getClipAmount();
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
        content = databaseClip.getSelectedAmountOfClips(page * pageSize, page * pageSize + pageSize);

        if (content == null || content.isEmpty()) {
            System.out.println("No clips available for the requested page.");
            return;
        }

        int startIndex = page * pageSize;
        int endIndex = startIndex + pageSize;
        
        clipCards.clear();
        currentPage.clear();

        int i = startIndex;
        for ( Clip clip : content ) {
            clipCards.add(new ClipCard(clip));
            currentPage.add(clip);
            if ( i == endIndex ) {
                break;
            }
            i++;
        }
    }

}
