package com.example.team16project.dto.article.request;

import com.example.team16project.domain.article.Article;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ArticleWithIdForm {

    @NotNull
    private Long articleId;
    @Length(max = 50, message = "제목의 길이는 50자 이하여야 합니다")
    private String title;
    @Length(max = 3000, message = "내용의 길이는 3000자 이하여야 합니다")
    private String contents;

    public static Article toEntity(ArticleWithIdForm articleForm) {

        return new Article().builder()
                .contents(articleForm.getContents()).title(articleForm.getTitle()).build();
    }
}
