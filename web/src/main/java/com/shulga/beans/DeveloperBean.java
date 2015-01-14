package com.shulga.beans;

import com.shulga.ejb.interfaces.DeveloperEJBRemote;
import com.shulga.model.Developer;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("developerBean")
@SessionScoped
public class DeveloperBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private DeveloperEJBRemote developerEJB;
    private List<Developer> devList;

    public void addDeveloper(String name, String lastname) {
        try {
            Developer dev = new Developer();
            dev.setName(name);
            dev.setLastname(lastname);
            developerEJB.create(dev).toString();
            devList = developerEJB.getList(new Developer());
        } catch (Exception e) {
            addMessage(e.getMessage());
        }
    }

    public void getDeveloper(String name, String lastname) {
        Developer dev = new Developer();
        dev.setName(name);
        dev.setLastname(lastname);
        devList = developerEJB.getList(dev);
    }

    public void removeDeveloper(String name, String lastname) {
        try {
            Developer dev = new Developer();
            dev.setName(name);
            dev.setLastname(lastname);
            devList = developerEJB.getList(dev);
            if (devList.size() == 1) {
                developerEJB.delete(devList.iterator().next().getId());
                addMessage("Deleted Successfully");
            }
        } catch (Exception e) {
            addMessage(e.getMessage());
        }
    }

    public List<Developer> getDevList() {
        return devList;
    }

    public void setDevList(List<Developer> devList) {
        this.devList = devList;
    }

    public void refresh() {
        devList = developerEJB.getList(new Developer());
    }

    public static void addMessage(String message) {
        FacesMessage fm = new FacesMessage(message);
        FacesContext.getCurrentInstance().addMessage(fm.getSummary(), fm);
    }
}
