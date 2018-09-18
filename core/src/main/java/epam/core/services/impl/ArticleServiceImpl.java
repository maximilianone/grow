package epam.core.services.impl;

import epam.core.models.dto.Article;
import epam.core.services.ArticleService;
import epam.core.services.HTTPService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component(service = ArticleService.class, immediate = true)
public class ArticleServiceImpl implements ArticleService {
    private static final Logger LOG = LoggerFactory.getLogger(ArticleServiceImpl.class);
    private static final String ARTICLES = "articles";

    @Reference
    private HTTPService httpService;

    @Override
    public List<Article> getArticlesByUrl(String url) {
        JSONObject response = httpService.sendGet(url);
        List<Article> articleList = new ArrayList<>();

        try {
            JSONArray articleJsonList = response.getJSONArray(ARTICLES);
            for (int i = 0; i < articleJsonList.length(); i++) {
                articleList.add(new Article(articleJsonList.getJSONObject(i)));
            }
        } catch (JSONException e) {
            LOG.error("Cannot get article list from response");
            LOG.error("URL: " + url);
        }

        return articleList;
    }
}
