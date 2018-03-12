package com.doodlz.husain.animemaniacs;



public class ChapterRange {
    String chapterTitle;
    String range;

    public ChapterRange(){}

    public ChapterRange(String chapterTitle, String range) {
        this.chapterTitle = chapterTitle;
        this.range = range;
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
}
