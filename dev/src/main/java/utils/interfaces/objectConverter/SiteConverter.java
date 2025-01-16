package utils.interfaces.objectConverter;

import model.Organization.Site;
import model.SystemManagement.ManagementSystem;

public class SiteConverter implements ObjectConverter<Site> {
    @Override
    public Site convertObject(){
        return new Site();
    }
}
