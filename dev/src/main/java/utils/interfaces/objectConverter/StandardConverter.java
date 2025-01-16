package utils.interfaces.objectConverter;

import model.SystemManagement.Standard.Standard;

public class StandardConverter implements ObjectConverter<Standard> {
    @Override
    public Standard convertObject(){
        return new Standard();
    }
}
