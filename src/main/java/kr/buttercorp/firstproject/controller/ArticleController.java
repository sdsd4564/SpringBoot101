package kr.buttercorp.firstproject.controller;

import kr.buttercorp.firstproject.dto.ArticleForm;
import kr.buttercorp.firstproject.dto.CommentDto;
import kr.buttercorp.firstproject.entity.Article;
import kr.buttercorp.firstproject.repository.ArticleRepository;
import kr.buttercorp.firstproject.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j // 로깅 어노테이션
@AllArgsConstructor
public class ArticleController {
    private final ArticleRepository articleRepository;
    private final CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO => Entity
        Article article = form.toEntity();
        log.info(article.toString());

        // 2. Repository에게 Entity를 DB에 저장!
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1: id로 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null); // orElse == nullable
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2: 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        // 3. 보여줄 페이지 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 1: 모든 Article을 가져옴
        List<Article> articles = articleRepository.findAll();

        // 2: 가져온 Article 목록을 뷰로 전달
        model.addAttribute("articleList", articles);

        // 3: 뷰 페이지를 설정!
        return "articles/index";
    }

    @GetMapping("articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터를 등록!
        model.addAttribute("article", articleEntity);

        // 뷰페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info((form.toString()));

        // 1: DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 2: 엔티티를 DB로 저장한다!
        //   2-1: DB에 기존 데이터를 가져옴
        Article article = articleRepository.findById(articleEntity.getId()).orElse(null);
        //   2-2: 기존 데이터 값을 갱신한다.
        if (article != null) {
            articleRepository.save(articleEntity);
        }

        // 3: 수정 결과 페이지로 리다이렉트!
        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("get deleted");

        // 1: 삭제 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);

        // 2: 대상 삭제
        if (target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다");
        }

        // 3: 결과 페이지 리다이렉트
        return "redirect:/articles";
    }
}
