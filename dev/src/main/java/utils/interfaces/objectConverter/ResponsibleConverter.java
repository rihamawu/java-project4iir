package utils.interfaces.objectConverter;

import model.Responsible;

public class ResponsibleConverter implements ObjectConverter<Responsible> {
    @Override
    public Responsible convertObject(){
        return new Responsible();
    }
}
