package com.backend.domain.event.data;

import com.backend.domain.IEntity;

public interface Data extends IEntity<Long> {

    String getName();
    void setName(String value);

    String getValue();
    void setValue(String value);
}
