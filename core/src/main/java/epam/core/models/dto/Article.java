package epam.core.models.dto;

import org.json.JSONException;
import org.json.JSONObject;

public class Article {
    private static final String AUTHOR = "author";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String URL = "url";
    private static final String URL_TO_IMAGE = "urlToImage";
    private static final String CONTENT = "content";

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String content;

    public Article() {
    }

    public Article(JSONObject jsonObject) throws JSONException {
        this.author = getValueOrEmpty(jsonObject, AUTHOR);
        this.title = getValueOrEmpty(jsonObject, TITLE);
        this.description = getValueOrEmpty(jsonObject, DESCRIPTION);
        this.url = getValueOrEmpty(jsonObject, URL);
        this.urlToImage = getValueOrEmpty(jsonObject, URL_TO_IMAGE);
        this.content = getValueOrEmpty(jsonObject, CONTENT);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", urlToImage='" + urlToImage + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    private String getValueOrEmpty(JSONObject jsonObject, String key) throws JSONException {
        return !jsonObject.isNull(key) ? jsonObject.getString(key) : "";
    }
}
