package com.backend.service.install;

import com.backend.domain.application.applicationInstall.IApplicationInstall;
import com.backend.repository.InstallRepository;
import utils.precondition.IValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Date;

public class InstallService implements IInstallService {
    @Autowired
    private InstallRepository installRepository;
    @Autowired
    private IValidator validator;

    @Override
    public IApplicationInstall saveInstall(IApplicationInstall install) {
        validator.notNull(install,"Install must be not null");
        install.setInstallDate(new Date());
       return installRepository.save(install);
    }

    @Override
    public IApplicationInstall findByID(String id) {
        validator.notNull(id,"Install id must be not null");
        validator.isTrue(!id.isEmpty(),"Application ID must be not empty");
        return installRepository.findOne(id);
    }

    @Override
    public Collection<IApplicationInstall> findAll() {
        return installRepository.findAll();
    }
}
