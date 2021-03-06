package com.kioshq.distro.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class DistributionList {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "distribution_list_id")
	private Long id;

	private String listName;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "subscription_id")
	@JsonBackReference(value = "subscription-distributionList")
	private List<Subscription> subscriptions;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "organization_id")
	@JsonBackReference(value = "user-distributionlists")
	private Organization organization;

	// TODO: Flesh this out more
	@ElementCollection(targetClass = String.class)
	private Set<String> categories;

	public Long getId() {
		return id;
	}

	public Set<String> getCategories() {
		return categories;
	}

	public void setCategories(Set<String> categories) {
		this.categories = categories;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public DistributionList() {
	}

	public DistributionList(String listName, Organization organization) {
		this.listName = listName;
		this.organization = organization;
		this.categories = new HashSet<String>();
	}
}
