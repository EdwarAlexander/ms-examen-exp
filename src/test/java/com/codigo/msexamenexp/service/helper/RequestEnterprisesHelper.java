package com.codigo.msexamenexp.service.helper;

import com.codigo.msexamenexp.aggregates.request.RequestEnterprises;

public class RequestEnterprisesHelper {

    public static RequestEnterprises createRequestEnterprise_Error(){
        RequestEnterprises entity = new RequestEnterprises();
        entity.setNumDocument("123456");
        entity.setEnterprisesTypeEntity(1);
        entity.setDocumentsTypeEntity(4);
        return entity;
    }

    public static RequestEnterprises createRequestEnterprise_Ok(){
        RequestEnterprises entity = new RequestEnterprises();
        entity.setNumDocument("20601362784");
        entity.setEnterprisesTypeEntity(2);
        entity.setDocumentsTypeEntity(3);
        return entity;
    }
}
