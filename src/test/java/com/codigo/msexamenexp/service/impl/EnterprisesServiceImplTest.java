package com.codigo.msexamenexp.service.impl;

import com.codigo.msexamenexp.aggregates.constants.Constants;
import com.codigo.msexamenexp.aggregates.response.ResponseBase;
import com.codigo.msexamenexp.aggregates.response.ResponseSunat;
import com.codigo.msexamenexp.config.RedisService;
import com.codigo.msexamenexp.feignClient.SunatClient;
import com.codigo.msexamenexp.repository.DocumentsTypeRepository;
import com.codigo.msexamenexp.repository.EnterprisesRepository;
import com.codigo.msexamenexp.service.helper.EnterprisesEntityHelper;
import com.codigo.msexamenexp.service.helper.RequestEnterprisesHelper;
import com.codigo.msexamenexp.util.EnterprisesValidations;
import com.codigo.msexamenexp.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EnterprisesServiceImplTest {
    @InjectMocks
    private EnterprisesServiceImpl enterprisesService;
    @Mock
    private EnterprisesRepository enterprisesRepository;
    @Mock
    private EnterprisesValidations enterprisesValidations;
    @Mock
    private DocumentsTypeRepository typeRepository;
    @Mock
    private Util util;
    @Mock
    private SunatClient sunatClient;
    @Mock
    private RedisService redisService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        enterprisesService = new EnterprisesServiceImpl(enterprisesRepository,enterprisesValidations,typeRepository,util,sunatClient,redisService);
    }

    @Test
    void testCreateEnterprise_OK_Error(){
        Mockito.when(enterprisesValidations.validateInput(Mockito.any())).thenReturn(false);
        ResponseBase response = enterprisesService.createEnterprise(RequestEnterprisesHelper.createRequestEnterprise_Error());
        assertNotNull(response);
        assertEquals(Constants.CODE_ERROR_DATA_INPUT, response.getCode());
    }

    @Test
    void testCreateEnterprise_OK(){
        Mockito.when(enterprisesValidations.validateInput(Mockito.any())).thenReturn(true);
        Mockito.when(sunatClient.getInfoReniec(Mockito.anyString(), Mockito.anyString())).thenReturn(new ResponseSunat());
        ResponseBase response = enterprisesService.createEnterprise(RequestEnterprisesHelper.createRequestEnterprise_Ok());
        assertNotNull(response);
        assertEquals(Constants.CODE_SUCCESS, response.getCode());
        assertTrue(response.getData().isPresent());
    }

    @Test
    void testCreateEnterprise_NOTDATARENIEC(){
        Mockito.when(enterprisesValidations.validateInput(Mockito.any())).thenReturn(true);
        ResponseBase response = enterprisesService.createEnterprise(RequestEnterprisesHelper.createRequestEnterprise_Ok());
        assertNotNull(response);
        assertEquals(Constants.CODE_ERROR_DATA_NOT, response.getCode());
        assertEquals(Constants.MESS_NON_DATA_RENIEC, response.getMessage());
    }

    @Test
    void testDeleteEnterprise_OK(){
        Mockito.when(enterprisesRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(enterprisesRepository.findById(Mockito.any())).thenReturn(Optional.of(EnterprisesEntityHelper.createEnterprisesEntity()));
        ResponseBase response = enterprisesService.delete(1);
        assertNotNull(response);
        assertEquals(Constants.CODE_SUCCESS, response.getCode());
        assertTrue(response.getData().isPresent());
    }

    @Test
    void testDeleteEnterprise_Error(){
        Mockito.when(enterprisesRepository.existsById(Mockito.any())).thenReturn(false);
        ResponseBase response = enterprisesService.delete(1);
        assertNotNull(response);
        assertEquals(Constants.CODE_ERROR_DATA_NOT, response.getCode());
        assertFalse(response.getData().isPresent());
    }

    @Test
    void testFindAllEnterprises_Ok(){
        Mockito.when(enterprisesRepository.findAll()).thenReturn(EnterprisesEntityHelper.createEnterprisesEntityList());
        ResponseBase response = enterprisesService.findAllEnterprises();
        assertNotNull(response);
        assertEquals(Constants.CODE_SUCCESS, response.getCode());
        assertTrue(response.getData().isPresent());
    }

    @Test
    void testFindAllEnterprises_Error(){
        Mockito.when(enterprisesRepository.findAll()).thenReturn(EnterprisesEntityHelper.createEnterprisesEntityListEmpty());
        ResponseBase response = enterprisesService.findAllEnterprises();
        assertNotNull(response);
        assertEquals(Constants.CODE_ERROR_DATA_NOT, response.getCode());
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void testUpdateEnterprise_NotExist(){
        Mockito.when(enterprisesRepository.existsById(Mockito.any())).thenReturn(false);
        ResponseBase response = enterprisesService.updateEnterprise(1, RequestEnterprisesHelper.createRequestEnterprise_Ok());
        assertNotNull(response);
        assertEquals(Constants.CODE_ERROR_DATA_NOT, response.getCode());
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void testUpdateEnterprise_Validation(){
        Mockito.when(enterprisesRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(enterprisesRepository.findById(Mockito.any())).thenReturn(Optional.of(EnterprisesEntityHelper.createEnterprisesEntity()));
        Mockito.when(enterprisesValidations.validateInputUpdate(Mockito.any())).thenReturn(false);
        ResponseBase response = enterprisesService.updateEnterprise(1, RequestEnterprisesHelper.createRequestEnterprise_Ok());
        assertNotNull(response);
        assertEquals(Constants.CODE_ERROR_DATA_INPUT, response.getCode());
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void testUpdateEnterprise_OK(){
        Mockito.when(enterprisesRepository.existsById(Mockito.any())).thenReturn(true);
        Mockito.when(enterprisesRepository.findById(Mockito.any())).thenReturn(Optional.of(EnterprisesEntityHelper.createEnterprisesEntity()));
        Mockito.when(enterprisesValidations.validateInputUpdate(Mockito.any())).thenReturn(true);
        ResponseBase response = enterprisesService.updateEnterprise(1, RequestEnterprisesHelper.createRequestEnterprise_Ok());
        assertNotNull(response);
        assertEquals(Constants.CODE_SUCCESS, response.getCode());
        assertTrue(response.getData().isPresent());
    }

    @Test
    void testFindOneEnterprise_Return_Redis(){
        String redisString = "{\"userCreate\":\"ADMIN\",\"dateCreate\":1705545770751,\"userModif\":\"ADMIN\",\"dateModif\":1705547652665,\"userDelete\":\"ADMIN\",\"dateDelete\":1705548426217,\"idEnterprises\":1,\"numDocument\":\"20332600592\",\"businessName\":\"AENZA S.A.A.\",\"tradeName\":\"AENZA S.A.A. negocio\",\"status\":0,\"enterprisesTypeEntity\":{\"userCreate\":\"ADMIN\",\"dateCreate\":1705540104061,\"idEnterprisesType\":2,\"codType\":\"02\",\"descType\":\"SAC\",\"status\":1},\"documentsTypeEntity\":{\"userCreate\":\"ADMIN\",\"dateCreate\":1705540104061,\"idDocumentsType\":3,\"codType\":\"06\",\"descType\":\"RUC\",\"status\":1}}";
        Mockito.when(redisService.getValueByKey(Mockito.any())).thenReturn(redisString);
        ResponseBase response = enterprisesService.findOneEnterprise("12345678932");
        assertNotNull(response);
        assertEquals(Constants.CODE_SUCCESS, response.getCode());
        assertTrue(response.getData().isPresent());
    }

    @Test
    void testFindOneEnterprise_NotExist(){
        String redisString = null;
        Mockito.when(redisService.getValueByKey(Mockito.any())).thenReturn(redisString);
        ResponseBase response = enterprisesService.findOneEnterprise("12345678932");
        assertNotNull(response);
        assertEquals(Constants.CODE_ERROR_DATA_NOT, response.getCode());
        assertTrue(response.getData().isEmpty());
    }

    @Test
    void testFindOneEnterprise_Return_Entity(){
        String redisString = null;
        Mockito.when(redisService.getValueByKey(Mockito.any())).thenReturn(redisString);
        Mockito.when(enterprisesRepository.findByNumDocument(Mockito.anyString())).thenReturn(EnterprisesEntityHelper.createEnterprisesEntity());
        ResponseBase response = enterprisesService.findOneEnterprise("12345678932");
        assertNotNull(response);
        assertEquals(Constants.CODE_SUCCESS, response.getCode());
        assertTrue(response.getData().isPresent());
    }
}