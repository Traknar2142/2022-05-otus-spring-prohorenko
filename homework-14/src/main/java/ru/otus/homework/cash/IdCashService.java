package ru.otus.homework.cash;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class IdCashService {
    private Map<String, String> idCash;

    public IdCashService() {
        this.idCash = new HashMap<>();
    }

    public void put(String key, String value){
        idCash.put(key,value);
    }

    public String get(String key){
        return idCash.get(key);
    }
}
