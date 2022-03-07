package kr.buttercorp.firstproject.ioc;

import org.springframework.stereotype.Component;

@Component
public class Chef {
    private IngredientFactory ingredientFactory;

    public Chef(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    public String cook(String menu) {
        Ingredient ingredient = ingredientFactory.get(menu);

        return ingredient.getName() + "으로 만든 " + menu;
    }
}
