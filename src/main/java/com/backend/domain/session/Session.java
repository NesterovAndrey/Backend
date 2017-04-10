package com.backend.domain.session;

import com.backend.domain.IEntity;
import com.backend.domain.application.Application;
import com.backend.domain.application.applicationInstall.IApplicationInstall;
import com.fasterxml.jackson.annotation.JsonSubTypes;


import java.util.Date;

@JsonSubTypes({
		@JsonSubTypes.Type(value = AppSession.class, name = "application") })
public interface Session extends IEntity<Long> {

	Long getId();
	void setId(Long id);
	Application getApp();
	void setApp(Application app);
	Date getStartTime();
	void setStartTime(Date startTime);
	Date getEndTime();
	void setEndTime(Date endTime);
	IApplicationInstall getInstall();
	void setInstall(IApplicationInstall install);
}
