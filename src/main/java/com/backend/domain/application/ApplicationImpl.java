package com.backend.domain.application;

import com.backend.domain.session.Session;
import com.backend.domain.authenticationUser.profile.UserProfile;
import utils.IByteSource;

import java.util.*;

public  class ApplicationImpl implements Application, IByteSource {
	private String id;
	private UserProfile owner;
	private String privateKey;
	private String name;
	private Date dateAdded;
	private List<Session> sessions;

	public ApplicationImpl()
	{

	}

	public String getId()
	{
		return id;
	}
	public void setId(String value)
	{
		id=value;
	}

	@Override
	public UserProfile getOwner() {
		return owner;
	}

	@Override
	public void setOwner(UserProfile owner) {
		this.owner=owner;
	}

	public String getName() {
		return name;
	}
	public void setName(String title) {
		this.name = title;
	}

	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;//new AppID(UUID.fromString(privateKey));
	}


	@Override
	public byte[] asByteArray() {
		return (getName()+getOwner().getId()+getPrivateKey()+getDateAdded().toString()).getBytes();
	}
}
