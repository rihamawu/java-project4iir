package utils.interfaces.objectConverter;

import model.SystemManagement.ManagementSystem;

public class ManagementSystemConverter implements ObjectConverter<ManagementSystem> {
    @Override
    public ManagementSystem convertObject(){
        return new ManagementSystem();
    }
}
