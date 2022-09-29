package com.koreaIT.java.BAM.service;

import java.util.List;

import com.koreaIT.java.BAM.container.Container;
import com.koreaIT.java.BAM.dto.Article;

public class ArticleService {	
	public List<Article> getForPrintArticles(String searchKeywod) {
		return Container.articleDao.getForPrintArticles(searchKeywod);

	}

	public int setArticleId() {
		
	return Container.articleDao.setArticleId();
		
	}

	public void add(Article article) {
		Container.articleDao.add(article);
		
		
	}

	public Article getArticleById(int id) {
		return 	Container.articleDao.getArticleById(id);
		
	}

	public void remove(Article foundArticle) {
		Container.articleDao.remove(foundArticle);
		
	}

	
}
