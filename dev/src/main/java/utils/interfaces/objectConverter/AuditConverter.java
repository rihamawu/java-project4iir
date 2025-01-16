package utils.interfaces.objectConverter;


import model.audit.Audit;

public class AuditConverter implements ObjectConverter<Audit> {
    @Override
    public Audit convertObject(){
        return new Audit();
    }
}
