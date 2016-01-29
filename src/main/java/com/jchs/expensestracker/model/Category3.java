package com.jchs.expensestracker.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class Category3 {

	@Id
	@GeneratedValue
	protected Long id;
	
	@ManyToOne
	private Category2 parent;
	
	private String description;
	
	@Override
	public String toString() {
		return description;
	}
	
	public Long getId() {
		return id;
	}
	
	public Category2 getParent() {
		return parent;
	}

	public void setParent(Category2 parent) {
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
		Category3 other = (Category3) obj;
		return new EqualsBuilder().append(id, other.getId()).isEquals();
	}

}
