package pds.futbolistos.modelado.convertidores;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ConversorListaDeCadenas implements AttributeConverter<List<String>, String> {

  @Override
  public String convertToDatabaseColumn(List<String> list) {
    if(list == null) return "";
    return String.join(",", list); 
  }

  @Override
  public List<String> convertToEntityAttribute(String joined) {
    if(joined == null) return new ArrayList<>();
    return new ArrayList<>(Arrays.asList(joined.split(",")));
  }
}
