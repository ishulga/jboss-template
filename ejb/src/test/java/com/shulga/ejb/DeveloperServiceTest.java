package com.shulga.ejb;

import com.shulga.common.CustomException;
import com.shulga.ejb.interfaces.DeveloperEJBRemote;
import com.shulga.model.Developer;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
@Ignore
public class DeveloperServiceTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war").addPackages(true, "com.shulga")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource("test-ds.xml", "test-ds.xml");
    }

    @Inject
    private DeveloperEJBRemote devService;

    @Test
    public void testDeveloperCRUD() throws CustomException {
        Developer dev = new Developer();
        dev.setName("Jack");
        Long id = devService.create(dev);
        Developer developer = devService.get(id);
        assertEquals("Jack", developer.getName());
        developer.setName("Amy");
        devService.update(developer);
        developer = devService.get(id);
        assertEquals("Amy", developer.getName());
    }

    @Test
    public void testGetByQBE() throws CustomException {
        Developer dev = new Developer();
        dev.setName("Jack");
        dev.setLastname("Welch");
        devService.create(dev);
        dev = new Developer();
        dev.setName("Jack");

        dev = devService.getList(dev).get(0);
        assertEquals("Jack", dev.getName());
        assertEquals("Welch", dev.getLastname());
    }

}
