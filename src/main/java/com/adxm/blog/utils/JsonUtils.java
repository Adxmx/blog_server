package com.adxm.blog.utils;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  // 反序列化
  public static Object deserialize(String json) {
    Object o = null;
    try {
      JsonNode jsonNode = JsonUtils.objectMapper.readTree(json);
      if (jsonNode.isArray()) {
        o = objectMapper.readValue(json, List.class);
      } else if (jsonNode.isObject()) {
        o = objectMapper.readValue(json, Map.class);
      }
    } catch (Exception e) {
      System.out.println("序列化， 错误：" + e);
    }

    return o;
  }

  // 序列化
  public static String serialize(Object o) {
    return null;
  }
}
