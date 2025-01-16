package utils.interfaces.objectConverter;

import model.Organization.OrgProcess;

public class OrgProcessConverter implements ObjectConverter<OrgProcess> {
    @Override
    public OrgProcess convertObject(){
        return new OrgProcess();
    }
}
