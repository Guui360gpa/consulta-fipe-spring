package br.com.consultafipe.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class ConverterDados implements IConverterDados{
    private ObjectMapper mapper = new ObjectMapper();



    @Override
    public <T> List<T> obterListaDeDados(String json, Class<T> classe) {
        try {
            return mapper.readValue(json,
                    mapper.getTypeFactory()
                            .constructCollectionType(List.class, classe));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        return mapper.readValue(json, classe);
    }


}
