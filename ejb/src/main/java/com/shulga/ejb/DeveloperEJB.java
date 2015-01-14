package com.shulga.ejb;

import com.shulga.common.CustomException;
import com.shulga.common.Notifier;
import com.shulga.ejb.interfaces.DeveloperEJBRemote;
import com.shulga.model.Developer;
import com.shulga.persistance.DeveloperPL;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class DeveloperEJB implements DeveloperEJBRemote {
    @Inject
    private DeveloperPL devPL;

    @Inject
    private Notifier notifier;

    @Override
    public Long create(Developer dev) throws CustomException {
        if (!getList(dev).isEmpty()) {
            throw new CustomException("The name already exists");
        }
        notifier.sendMessage("Hello");
        Long id = devPL.create(dev);
        return id;
    }

    @Override
    public void update(Developer dev) {
        devPL.update(dev);
    }

    @Override
    public void delete(Long id) {
        devPL.delete(id);
    }

    @Override
    public Developer get(Long id) {
        Developer developer = devPL.get(id);
        return developer;
    }

    @Override
    public List<Developer> getList(Developer qbe) {
        List<Developer> devs = devPL.getListByQBE(qbe);
        return devs;
    }

}
