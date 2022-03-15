package kr.buttercorp.firstproject.service;

import kr.buttercorp.firstproject.dto.ArticleForm;
import kr.buttercorp.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest // 해당 클래스는 스프링부트와 연동되어 Testing됨

class ArticleServiceTest {

    ArticleService articleService;

    public ArticleServiceTest(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Test
    void indexAll() {
        // 예상 시나리오:
        Article a = new Article(1L, "Item1", "Item-Content1");
        Article b = new Article(2L, "Item2", "Item-Content2");
        Article c = new Article(3L, "Item3", "Item-Content3");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        // 실제 시나리오:
        List<Article> articles = articleService.indexAll();

        // 비교:
        assertEquals(expected.toString(), articles.toString());
    }

    @Test
    void indexSuccessWhenInputExistID() {
        // 예상
        Long id = 1L;
        Article expected = new Article(id, "Item1", "Item-Content1");

        // 실제
        Article article = articleService.index(id);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void indexFailureWhenInputNonExistId() {
        // 예상
        Long id = -1L;
        Article expected = null;

        // 실제
        Article article = articleService.index(id);

        // 비교
        assertEquals(expected, article);
    }

    @Test
    void createSuccessInputOnlyTitleAndContentDTO() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void createFailureInputDTOIncludeID() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L, title, content);
        Article expected = null;

        // 실제
        Article article = articleService.create(dto);

        // 비교
        assertEquals(expected, article);
    }
}