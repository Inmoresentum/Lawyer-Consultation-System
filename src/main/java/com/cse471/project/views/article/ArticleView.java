package com.cse471.project.views.article;

import com.cse471.project.repository.ArticleRepository;
import com.cse471.project.repository.LawyerRepository;
import com.cse471.project.security.AuthenticatedUser;
import com.cse471.project.views.MainLayout;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.jetbrains.annotations.NotNull;

import java.util.List;


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
        createArticleCard();
        createArticleCard();
        createArticleCard();


        RichTextEditor richTextEditor = new RichTextEditor();
        Article article = new Article();
        article.setContent(richTextEditor.getHtmlValue());
        var user = authenticatedUser.get().orElseThrow();
        if (user.getRoles().contains(Role.LAWYER)) {
            article.setAuthor(lawyerRepository.findByUser(user));
        }

        articleRepository.save(article);
    }

    private void createArticleCard() {
        List<Article> articleList = articleRepository.findAll();
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

    private void setUpPostNewArticleDialogBox() {
        Dialog newArticlePostingDialog = new Dialog();

        newArticlePostingDialog.setHeaderTitle("Post a New Article");
        newArticlePostingDialog.addClassName("article-view-edit-post-dialog-box");

        TextArea newArticleTitle = createNewArticleTitle();
        TextArea newArticle = createNewArticle();
        VerticalLayout dialogLayout = new VerticalLayout(newArticleTitle,
                newArticle);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "20rem").set("max-width", "100%");
        newArticlePostingDialog.add(dialogLayout);

        Button postButton = new Button("Post", postButtonEvent ->
                handleNewArticleRequest(newArticleTitle, newArticle, newArticlePostingDialog));

        //noinspection DuplicatedCode
        postButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        postButton.addClassName("article-view-edit-post-save-button");
        Button cancelButton = new Button("Cancel", e ->
                newArticlePostingDialog.close());
        cancelButton.addClassName("article-write-text-area");
        newArticlePostingDialog.getFooter().add(cancelButton);
        newArticlePostingDialog.getFooter().add(postButton);

        newArticlePostingDialog.setDraggable(true);
        newArticlePostingDialog.setResizable(true);
        newArticlePostingDialog.open();
        add(newArticlePostingDialog);
    }




    @NotNull
    private TextArea createNewArticleTitle() {
        TextArea articleTitle = new TextArea("Title");
        articleTitle.addValueChangeListener(valueChangedEvent ->
                checkArticleTitleValidity(valueChangedEvent, articleTitle));
        articleTitle.addClassName("article-view-edit-post-article-title-text-area");
        articleTitle.setPlaceholder("Write the new Article here");
        articleTitle.addThemeVariants(TextAreaVariant.LUMO_HELPER_ABOVE_FIELD);
        return articleTitle;
    }


    private void checkArticleTitleValidity(AbstractField.ComponentValueChangeEvent<TextArea,
            String> valueChangedEvent, TextArea articleTitle) {
        if (valueChangedEvent.getValue().equals("")) {
            articleTitle.setErrorMessage("This field can't be empty");
            articleTitle.setInvalid(true);
        }
    }


    @NotNull

    private TextArea createNewArticle() {
        TextArea articleWrite = new TextArea("Write");
        articleWrite.setPlaceholder("Write the new article here");
        articleWrite.addClassName("article-view-edit-post-article-write-text-area");
        articleWrite.addValueChangeListener(valueChangedEvent ->
                checkArticleWriteValidity(valueChangedEvent, articleWrite));
        return articleWrite;

    }

    private void checkArticleWriteValidity(AbstractField.ComponentValueChangeEvent<TextArea,
            String> valueChangedEvent, TextArea articleWrite){
            if (valueChangedEvent.getValue().equals("")) {
                articleWrite.setErrorMessage("This field can't be empty");
                articleWrite.setInvalid(true);
            }
    }

    private void handleNewArticleRequest(TextArea newArticleTitle, TextArea NewArticle, Dialog newArticlePostingDialog){
    }
}

