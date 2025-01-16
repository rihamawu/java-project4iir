package utils.interfaces.objectConverter;

import model.SystemManagement.Standard.Clause;

public class ClauseConverter implements ObjectConverter<Clause> {
    @Override
    public Clause convertObject(){
        return new Clause();
    }
}
