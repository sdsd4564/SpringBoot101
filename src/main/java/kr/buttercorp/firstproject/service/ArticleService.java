package kr.buttercorp.firstproject.service;

import kr.buttercorp.firstproject.dto.ArticleForm;
import kr.buttercorp.firstproject.entity.Article;
import kr.buttercorp.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> indexAll() {
        return articleRepository.findAll();
    }

    public Article index(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        return article.getId() != null ? null : articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 1: 수정용 엔티티 생성
        Article article = dto.toEntity();

        // 2: 대상 엔티티 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3: 잘못된 요청 처리
        if (target == null || !id.equals(article.getId())) {
            log.error("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }

        // 4: 업데이트 및 정상 응답
        target.patch(article);
        return articleRepository.save(target);
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null) {
            return null;
        }
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1: dto 묶음을 entity 묶음으로 ㅂ녀환!
        List<Article> articleList = dtos.stream().map(ArticleForm::toEntity).collect(Collectors.toList());

        // 2: entity 묶음을 DB로 저장
        articleList.forEach(article -> articleRepository.save(article));

        // 3: 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("실패!"));

        // 4: 결과값 반환
        return articleList;
    }
}
