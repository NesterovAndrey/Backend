package utils.uid;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.UUID;

@Converter(autoApply=true)
public class IDConverter implements AttributeConverter<ID,String> {
    @Override
    public ID convertToEntityAttribute(String s) {
        return new ID(UUID.fromString(s));
    }

    @Override
    public String convertToDatabaseColumn(ID id) {
        return id.toString();
    }
}
