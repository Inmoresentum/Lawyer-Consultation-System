package com.cse471.project.views.article;

import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "article", layout = MainLayout.class)
@AnonymousAllowed
public class ArticleView extends VerticalLayout {


    public ArticleView() {
        addClassName("article-view-main");
        createArticleCard();
        createArticleCard();
    }

    private void createArticleCard() {
        Div articleCard = new Div();
        articleCard.addClassName("article-view-card");
        Image articleCoverImage = new Image("images/application-main.png",
                "whatever");
        articleCoverImage.addClassName("article-view-car-image");
        H6 articleTitle = new H6("Article title");
        articleTitle.addClassName("article-view-card-title");
        Span spanTitle = new Span();
        spanTitle.addClassName("article-view-card-span-title");
        spanTitle.add("This is a span title");
        articleCard.add(articleTitle);
        articleCard.add(articleCoverImage);
        articleCard.add(spanTitle);
        add(articleCard);
    }
}
