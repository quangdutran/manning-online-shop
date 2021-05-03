package com.dutq.core.model.entity;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;
    private boolean defaultPhoto;
    private int type;
    private String url;
    private String name;
    private String description;
    private String altTag;
    private String cloudinaryPublicId;
    private String cloudinaryEtag;

    public Photo() {
    }

    public Photo(String url, String publicId) {
        this.url = url;
        this.cloudinaryPublicId = publicId;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public boolean isDefaultPhoto() {
        return defaultPhoto;
    }

    public void setDefaultPhoto(boolean defaultPhoto) {
        this.defaultPhoto = defaultPhoto;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAltTag() {
        return altTag;
    }

    public void setAltTag(String altTag) {
        this.altTag = altTag;
    }

    public String getCloudinaryPublicId() {
        return cloudinaryPublicId;
    }

    public void setCloudinaryPublicId(String cloudinaryPublicId) {
        this.cloudinaryPublicId = cloudinaryPublicId;
    }

    public String getCloudinaryEtag() {
        return cloudinaryEtag;
    }

    public void setCloudinaryEtag(String cloudinaryEtag) {
        this.cloudinaryEtag = cloudinaryEtag;
    }
}
