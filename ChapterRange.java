package com.doodlz.husain.animemaniacs;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChapterRange {
    String chapterTitle;
    String range;
    int charCount;
    String mainChars;




    public ChapterRange(){}

    public ChapterRange(String chapterTitle, String range, int charCount,String mainChars) {
        this.chapterTitle = chapterTitle;
        this.range = range;
        this.charCount = charCount;
        this.mainChars=mainChars;

    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public int getCharCount() {
        return charCount;
    }

    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }

    public String getMainChars() {
        return mainChars;
    }

    public void setMainChars(String mainChars) {
        this.mainChars = mainChars;
    }
}
