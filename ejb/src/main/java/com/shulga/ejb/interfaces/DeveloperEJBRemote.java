package com.shulga.ejb.interfaces;

import com.shulga.common.CustomException;
import com.shulga.model.Developer;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DeveloperEJBRemote {

    Long create(Developer dev) throws CustomException;

    void update(Developer dev);

    void delete(Long id);

    Developer get(Long id);

    List<Developer> getList(Developer qbe);
}
