package com.backend.repository;

import com.backend.domain.application.applicationInstall.IApplicationInstall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstallRepository extends JpaRepository<IApplicationInstall,String> {
}
