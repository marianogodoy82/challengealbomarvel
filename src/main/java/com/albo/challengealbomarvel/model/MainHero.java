package com.albo.challengealbomarvel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "mainHeroes")
public class MainHero {
    @Id
    private Long id;
    private String name;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "last_sync")
    private LocalDateTime lastSync;
    private boolean syncronizable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDateTime getLastSync() {
        return lastSync;
    }

    public void setLastSync(LocalDateTime lastSync) {
        this.lastSync = lastSync;
    }

    public boolean isSyncronizable() {
        return syncronizable;
    }

    public void setSyncronizable(boolean syncronizable) {
        this.syncronizable = syncronizable;
    }
}
