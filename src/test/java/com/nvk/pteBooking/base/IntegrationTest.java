package com.nvk.pteBooking.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvk.pteBooking.Application;
import com.nvk.pteBooking.dto.Dto;
import java.io.IOException;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {Application.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Ignore
public class IntegrationTest {

  protected static final String MOCKED_JWT = "jwt";

  protected MockMvc mockMvc;

  protected ObjectMapper mapper;

  @Autowired
  protected WebApplicationContext webApplicationContext;

  protected TypeReference typeReference;

  protected TypeReference typeReferenceList;

  protected TypeReference typeReferenceDto;

  protected TypeReference typeReferenceDtoList;

  @Autowired
  protected ModelMapper modelMapper;

  protected List<Class> collectionsToBeCleared;

  @Autowired
  protected MongoTemplate mongoTemplate;

  @BeforeClass
  public static void init() {
    System.setProperty("log.file.name", "ms-pte-booking");
  }

  @Before
  public void setUp() {
    mapper = new ObjectMapper();
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @After
  public void clearData() {
    if (collectionsToBeCleared != null) {
      for (Class collectionClass : collectionsToBeCleared) {
        mongoTemplate.dropCollection(collectionClass);
      }
    }
  }

  private HttpHeaders headersForRequest(boolean includeJWT) {
    HttpHeaders requestHeaders = new HttpHeaders();
    if (includeJWT) {
      requestHeaders.set("jwt", MOCKED_JWT);
    }
    return requestHeaders;
  }

  protected Object getObjectFromFile(String filePath) {
    return getObjectFromFile(filePath, typeReference);
  }

  protected Object getObjectFromFile(String filePath, TypeReference typeReference) {
    Resource resource = new ClassPathResource(filePath);
    Object object = null;
    try {
      object = mapper.readValue(resource.getFile(), typeReference);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return object;
  }

  protected List<Object> getObjectsFromFile(String filePath) {
    return getObjectsFromFile(filePath, typeReferenceList);
  }

  protected List<Object> getObjectsFromFile(String filePath, TypeReference typeReference) {
    Resource resource = new ClassPathResource(filePath);
    List<Object> objects = null;
    try {
      objects = mapper.readValue(resource.getFile(), typeReference);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return objects;
  }

  protected Object postObjectToRESTEndPoint(
      String endPoint, Object data, boolean includeJWT) {
    return postObjectToRESTEndPoint(endPoint, data, includeJWT, typeReferenceDto);
  }

  protected Object postObjectToRESTEndPoint(
      String endPoint, Object data, boolean includeJWT, TypeReference typeReference) {
    Dto<Object> objectDto = null;
    try {
      String response = mockMvc.perform(post(endPoint)
        .headers(headersForRequest(includeJWT))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(data)))
        .andExpect(status().isCreated())
        .andReturn().getResponse().getContentAsString();
      objectDto = mapper.readValue(response, typeReference);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return objectDto.getData();
  }

  protected boolean postObjectToRESTEndPointVoidReturn(
      String endPoint, Object data, boolean includeJWT) {
    try {
      mockMvc.perform(post(endPoint).headers(headersForRequest(includeJWT))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(data)))
        .andExpect(status().is2xxSuccessful());
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  protected Object getObjectFromRESTEndPoint(String endPoint, boolean includeJWT) {
    return getObjectFromRESTEndPoint(endPoint, includeJWT, typeReferenceDto);
  }

  protected Object getObjectFromRESTEndPoint(
      String endPoint, boolean includeJWT, TypeReference typeReference) {
    Dto<Object> objectDto = null;
    try {
      String response = mockMvc.perform(get(endPoint).headers(headersForRequest(includeJWT)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
      objectDto = mapper.readValue(response, typeReference);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return objectDto.getData();
  }

  protected Object putObjectToRESTEndPoint(String endPoint, Object data, boolean includeJWT) {
    return putObjectToRESTEndPoint(endPoint, data, includeJWT, typeReferenceDto);
  }

  protected Object putObjectToRESTEndPoint(
      String endPoint, Object data, boolean includeJWT, TypeReference typeReference) {
    Dto<Object> objectDto = null;
    try {
      String response = mockMvc.perform(put(endPoint)
        .headers(headersForRequest(includeJWT))
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(data)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
      objectDto = mapper.readValue(response, typeReference);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return objectDto.getData();
  }

  protected List<Object> getObjectsFromRESTEndPoint(String endPoint, boolean includeJWT) {
    return getObjectsFromRESTEndPoint(endPoint, includeJWT, typeReferenceDtoList);
  }

  protected List<Object> getObjectsFromRESTEndPoint(
      String endPoint, boolean includeJWT, TypeReference typeReference) {
    Dto<List<Object>> objectDto = null;
    try {
      String response = mockMvc.perform(get(endPoint).headers(headersForRequest(includeJWT)))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
      objectDto = mapper.readValue(response, typeReference);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return objectDto.getData();
  }

  protected List<Object> getObjectsForParamsFromRESTEndPoint(
      String endPoint, MultiValueMap<String, String> map, boolean includeJWT) {
    return getObjectsForParamsFromRESTEndPoint(endPoint, map, includeJWT, typeReferenceDtoList);
  }

  protected List<Object> getObjectsForParamsFromRESTEndPoint(
      String endPoint, MultiValueMap<String, String> map, boolean includeJWT,
      TypeReference typeReference) {
    Dto<List<Object>> objectDto = null;
    try {
      String response = mockMvc.perform(get(endPoint).params(map)
        .headers(headersForRequest(includeJWT)))
        .andExpect(status().isOk())
        .andReturn().getResponse().getContentAsString();
      objectDto = mapper.readValue(response, typeReference);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return objectDto.getData();
  }

  protected boolean deleteObjectAtRESTEndPoint(String endPoint, boolean includeJWT) {
    try {
      mockMvc.perform(delete(endPoint).headers(headersForRequest(includeJWT)))
        .andExpect(status().isOk());
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  protected void getObjectFromRESTEndPointWithStatus(
    String endPoint, boolean includeJWT, int status) {
    try {
      mockMvc.perform(get(endPoint).headers(headersForRequest(includeJWT)))
        .andExpect(status().is(status));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // scenarios with failure/different status in Response

  protected String postObjectToRESTEndPointWithStatus(
      String endPoint, Object object, boolean includeJWT, int status) {
    String response = null;
    try {
      ResultActions resultActions = mockMvc.perform(post(endPoint)
        .content(mapper.writeValueAsString(object))
        .contentType(MediaType.APPLICATION_JSON)
        .headers(headersForRequest(includeJWT)))
        .andExpect(status().is(status));
      if (HttpStatus.valueOf(status).is2xxSuccessful()) {
        response = resultActions.andReturn().getResponse().getContentAsString();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return response;
  }

  protected void putObjectToRESTEndPointWithStatus(
      String endPoint, Object object, boolean includeJWT, int status) {
    try {
      mockMvc.perform(put(endPoint).content(mapper.writeValueAsString(object))
        .contentType(MediaType.APPLICATION_JSON)
        .headers(headersForRequest(includeJWT)))
        .andExpect(status().is(status));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void deleteObjectAtRESTEndPointWithStatus(
      String endPoint, boolean includeJWT, int status) {
    try {
      mockMvc.perform(delete(endPoint).headers(headersForRequest(includeJWT)))
        .andExpect(status().is(status));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
