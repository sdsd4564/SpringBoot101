package kr.buttercorp.firstproject.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ChefTest {
    @Autowired
    private IngredientFactory ingredientFactory;

    @Autowired
    private Chef chef;

    @Test
    void CookDoncas() {
//        IngredientFactory ingredientFactory = new IngredientFactory();
//        Chef chef = new Chef(ingredientFactory);
        String menu = "돈까스";

        String food = chef.cook(menu);

        String expected = "한돈 등심으로 만든 돈까스";

        assertEquals(expected, food);
    }

    @Test
    void CookSteak() {
//        IngredientFactory  ingredientFactory = new IngredientFactory();
//        Chef chef = new Chef(ingredientFactory);
        String menu = "스테이크";

        String food = chef.cook(menu);

        String expected = "한우 꽃등심으로 만든 스테이크";

        assertEquals(expected, food);
    }

    @Test
    void CookChicken() {
//        IngredientFactory  ingredientFactory = new IngredientFactory();
//        Chef chef = new Chef(ingredientFactory);
        String menu = "크리스피 치킨";

        String food = chef.cook(menu);

        String expected = "국내산 닭으로 만든 크리스피 치킨";

        assertEquals(expected, food);
    }
}