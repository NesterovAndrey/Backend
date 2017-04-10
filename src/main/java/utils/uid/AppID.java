package utils.uid;

import com.backend.domain.application.Application;

import java.util.UUID;

public class AppID extends ID {
    public AppID(Application app) {

        super((app.getName()+app.getDateAdded().toString()+app.getOwner().getId()).getBytes());
    }
    public  AppID(UUID id)
    {
        super(id);
    }
}
