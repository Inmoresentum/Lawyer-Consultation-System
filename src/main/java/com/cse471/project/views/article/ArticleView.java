package com.cse471.project.views.article;

import com.cse471.project.repository.ArticleRepository;
import com.cse471.project.repository.LawyerRepository;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;



@Route(value = "article", layout = MainLayout.class)
@AnonymousAllowed
public class ArticleView extends VerticalLayout {

    private final Icon postNewArticle = new Icon(VaadinIcon.PLUS);
    private final ArticleRepository articleRepository;
    private final AuthenticatedUser authenticatedUser;

    public ArticleView(ArticleRepository articleRepository, AuthenticatedUser authenticatedUser, LawyerRepository lawyerRepository) {
        this.articleRepository = articleRepository;
        this.authenticatedUser = authenticatedUser;
        addClassName("article-view-main");
    }
}

