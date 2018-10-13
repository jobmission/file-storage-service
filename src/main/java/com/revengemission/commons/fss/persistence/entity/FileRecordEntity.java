package com.revengemission.commons.fss.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public class FileRecordEntity implements Serializable {
    private Long id;

    private Long userId;

    private boolean shared;

    private String fileName;

    private int recordStatus;

    private String remarks;

    private int version;

    private Date dateCreated;

    private Date lastModified;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public int getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(int recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}