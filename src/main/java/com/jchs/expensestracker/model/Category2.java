package com.jchs.expensestracker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"parent_id", "description"}))
public class Category2 {

	@Id
	@GeneratedValue
	protected Long id;
	
	@ManyToOne
	private Category1 parent;

	private String description;
	private String shortDescription;
	
	@Override
	public String toString() {
		return description;
	}
	
	public Long getId() {
		return id;
	}
	
	public Category1 getParent() {
		return parent;
	}

	public void setParent(Category1 parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category2 other = (Category2) obj;
		return new EqualsBuilder().append(id, other.getId()).isEquals();
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
}
