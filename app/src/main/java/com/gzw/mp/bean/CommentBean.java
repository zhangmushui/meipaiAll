package com.gzw.mp.bean;

/**
 * 评论信息
 * coder by 背离记 on 2015/11/10.
 */
public class CommentBean {

    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论创建时间
     */
    private long created_at;
    /**
     * 未知ID
     */
    private String id;
    /**
     * 该评论是否被我【当前登录用户】点赞
     */
    private boolean liked;
    /**
     * 该评论被点赞数
     */
    private int liked_count;
    /**
     * 被评论的视频ID
     */
    private String media_id;
    /**
     * 评论的用户信息
     */
    private User user;

    /**
     * 获取用户信息对象
     *
     * @return
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 获取视频ID
     *
     * @return
     */
    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    /**
     * 获取评论被点赞数
     *
     * @return
     */
    public int getLiked_count() {
        return liked_count;
    }

    public void setLiked_count(int liked_count) {
        this.liked_count = liked_count;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    /**
     * 未知ID
     *
     * @return
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 评论创建时间
     *
     * @return
     */
    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    /**
     * 评论内容
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
