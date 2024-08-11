package com.springboot.expensetracker.authservice.serialization;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.expensetracker.authservice.models.UserInfoDTO;
import org.apache.kafka.common.serialization.Serializer;

public class UserInfoSerializer implements Serializer<UserInfoDTO> {

    @Override
    public byte[] serialize(String arg0, UserInfoDTO arg1) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(arg1).getBytes();
        }catch (Exception e){
            e.printStackTrace();
        }
        return retVal;
    }
}
