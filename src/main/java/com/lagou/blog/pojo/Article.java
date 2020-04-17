package com.lagou.blog.pojo;

import java.util.Date;

public class Article {

    private Integer id;
    private String title;
    private String content;
    private Date created;
    private Date modified;
    private String categories;
    private String  tags;
    private int allow_comment;
    private String thumbnail;

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                ", categories='" + categories + '\'' +
                ", tags='" + tags + '\'' +
                ", allow_comment=" + allow_comment +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getAllow_comment() {
        return allow_comment;
    }

    public void setAllow_comment(int allow_comment) {
        this.allow_comment = allow_comment;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
