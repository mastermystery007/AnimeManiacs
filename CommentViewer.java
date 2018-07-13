package com.doodlz.husain.animemaniacs;


public class CommentViewer {
    String commentContent;
    String commentUserName;
    int likeCount;


    public CommentViewer() {
    }

    public CommentViewer(String commentContent, String commentUserName, int likeCount) {
        this.commentContent = commentContent;
        this.commentUserName = commentUserName;
        this.likeCount = likeCount;
    }

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
