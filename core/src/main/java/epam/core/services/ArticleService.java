package epam.core.services;

import epam.core.models.dto.Article;

import java.util.List;

public interface ArticleService {

    List<Article> getArticlesByUrl(String url);
}
