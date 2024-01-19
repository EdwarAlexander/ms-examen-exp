package com.codigo.msexamenexp.service.helper;

import com.codigo.msexamenexp.entity.DocumentsTypeEntity;
import com.codigo.msexamenexp.entity.EnterprisesEntity;
import com.codigo.msexamenexp.entity.EnterprisesTypeEntity;

import java.util.ArrayList;
import java.util.List;

public class EnterprisesEntityHelper {

    public static EnterprisesEntity createEnterprisesEntity(){
        EnterprisesEntity entity = new EnterprisesEntity();
        entity.setIdEnterprises(1);
        entity.setNumDocument("12345678932");
        entity.setTradeName("Prueba");
        entity.setBusinessName("prueba");
        entity.setStatus(1);
        entity.setUserDelete("admin");
        entity.setUserCreate("admin");
        entity.setUserModif("admin");
        entity.setEnterprisesTypeEntity(enterprisesTypeEntity());
        entity.setDocumentsTypeEntity(documentsTypeEntity());
        return entity;
    }

    private static EnterprisesTypeEntity enterprisesTypeEntity(){
        EnterprisesTypeEntity entity = new EnterprisesTypeEntity();
        entity.setIdEnterprisesType(1);
        return entity;
    }

    private static DocumentsTypeEntity documentsTypeEntity(){
        DocumentsTypeEntity entity = new DocumentsTypeEntity();
        entity.setIdDocumentsType(3);
        return entity;
    }

    public static List<EnterprisesEntity> createEnterprisesEntityList(){
        List<EnterprisesEntity> lista = new ArrayList<>();
        lista.add(createEnterprisesEntity());
        return lista;
    }

    public static List<EnterprisesEntity> createEnterprisesEntityListEmpty(){
        return new ArrayList<>();
    }
}
