package com.hahn.banco.entity.generic;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.hahn.banco.entity.constant.StateType;


@MappedSuperclass
public class Audit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name="state", nullable=false)
	private StateType state;

    @Column(name="creationDate", nullable=false)
	private Date creationDate;

	@Column(name="modificationDate", nullable=false)
	private Date modificationDate;

    
    public Audit() {
        this.state = StateType.ACTIVE;
        Date dateNow = new Date(System.currentTimeMillis());
        this.creationDate = dateNow;
        this.modificationDate = dateNow;
    }
    public Audit(Long id) {
        this.id = id;
        this.state = StateType.ACTIVE;
        Date dateNow = new Date(System.currentTimeMillis());
        this.creationDate = dateNow;
        this.modificationDate = dateNow;
    }
    
    
    public Long getId() {
        return id;
    }

    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate() {
        Date dateNow = new Date(System.currentTimeMillis());
        this.modificationDate = dateNow;
    }

    
    @Override
    public String toString() {
        return "Audit [id=" + id + ", state=" + state + ", creationDate=" + creationDate + ", modificationDate=" + modificationDate + "]";
    }

}
