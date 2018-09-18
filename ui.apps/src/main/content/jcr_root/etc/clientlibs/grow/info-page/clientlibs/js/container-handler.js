;(function(){
    'use strict';

    var SOURCE_PROPERTY = 'source';

    var ARTICLE_CONTAINER_CLASS = 'article_list';
    var NEWS_LIST_CONTAINER_CLASS = 'news_list';

    function getArticles (request) {
        var articleList;

		var articleRequest = new XMLHttpRequest();
		articleRequest.open('GET', request, false);
		articleRequest.send();

		if (articleRequest.status != 200) {
  			console.log( articleRequest.status + ': ' + articleRequest.statusText );
            articleList = [];
		} else {
			articleList = JSON.parse(articleRequest.responseText).articles;
		}
        return articleList;
    }

    function addToContainer (container, articleList, formatFunction) {
        for (var i = 0; i < articleList.length; i++) {
			container.innerHTML += formatFunction(articleList[i]);
        }
    }

    function formatArticle (article) {
		var html =  '<a href=' + article.url + ' class="article_button"><p class="article_text">';
        html += article.urlToImage ? '<image class="article_image" src="'+ article.urlToImage + '"/>' : '';
        html += article.title + '</p></a>';
        return html;
    }

    function formatNews (article){
		var html = '<a href=' + article.url + ' class="news_button">';
        html += '<p class="news_text_title">' + article.title + '</p>';
        html += article.author ?'<p class="news_text_author">Author: ' + article.author + '</p>' : '';
        html += article.urlToImage ? '<image class="news_image" src="'+ article.urlToImage + '"/>' : '';
        html += '<p class="news_text">' + article.description + '</p></a>';
        return html;
    }

    function getSource (articleContainer) {
		return articleContainer.getAttribute(SOURCE_PROPERTY) || $(articleContainer).parent()[0].getAttribute(SOURCE_PROPERTY);
    }

    function fillContainers (containerClass, formatFunction) {
		var containerList = document.getElementsByClassName(containerClass);

        var requestUrl = [].slice.apply(containerList).map(getSource);
		var articles = requestUrl.map(getArticles);
		
        for (var i = 0; i < containerList.length; i++){
			addToContainer(containerList[i], articles[i], formatFunction);
        }
    }

    $(document).ready(function() {
		fillContainers(ARTICLE_CONTAINER_CLASS, formatArticle);
		fillContainers(NEWS_LIST_CONTAINER_CLASS, formatNews)
    });
})();