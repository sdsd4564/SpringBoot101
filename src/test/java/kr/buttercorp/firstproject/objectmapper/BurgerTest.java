package kr.buttercorp.firstproject.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BurgerTest {
    @Test
    public void javaObectToJson() throws JsonProcessingException {
        //준비
        ObjectMapper mapper = new ObjectMapper();
        List<String> ingredients = Arrays.asList("통새우 패티", "순쇠고기 패티", "토마토");
        Burger burger = new Burger("맥도날드 슈비버거", 5500, ingredients);

        //수행
        String json = mapper.writeValueAsString(burger);

        //예상
        String expected = "{\"name\":\"맥도날드 슈비버거\",\"price\":5500,\"ingredients\":[\"통새우 패티\",\"순쇠고기 패티\",\"토마토\"]}";

        //검증
        assertEquals(expected, json);
        JsonNode node = mapper.readTree(json);
        System.out.println(node.toPrettyString());
    }

    @Test
    public void jsonToJavaObject() throws JsonProcessingException {
        //준비
        ObjectMapper mapper = new ObjectMapper();
        /*
        {
            "name": "맥도널드 슈비버거",
            "price": 5500,
            "ingredients": ["통새우 패티","순쇠고기 패티","토마토"]
        }
        * */
        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("name", "맥도날드 슈비버거");
        objectNode.put("price", 5500);

        ArrayNode arrayNode = mapper.createArrayNode();
        arrayNode.add("통새우 패티");
        arrayNode.add("순쇠고기 패티");
        arrayNode.add("토마토");
        objectNode.set("ingredients", arrayNode);
        String json = objectNode.toString();

        //수행
        Burger burger = mapper.readValue(json, Burger.class);

        //예상
        List<String> ingredients = Arrays.asList("통새우 패티", "순쇠고기 패티", "토마토");
        Burger expected = new Burger("맥도날드 슈비버거", 5500, ingredients);

        //검증
        assertEquals(expected.toString(), burger.toString());
        JsonNode node = mapper.readTree(json);
        System.out.println(node.toPrettyString());
        System.out.println(burger);
    }
}