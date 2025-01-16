package utils.interfaces.objectConverter;

import model.Organization.Organization;

public class OrganizationConverter implements ObjectConverter<Organization> {
    @Override
    public Organization convertObject(){
        return new Organization();
    }
}
