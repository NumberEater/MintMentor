package com.numbereater.investmentapp;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(
    mv = {1, 7, 0},
    k = 1,
    d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\t\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f¨\u0006\u000e"},
    d2 = {"Lcom/numbereater/investmentapp/QuizQuestion;", "", "questionTextResource", "", "choiceTextResources", "", "correctAnswerIndex", "(I[Ljava/lang/Integer;I)V", "getChoiceTextResources", "()[Ljava/lang/Integer;", "[Ljava/lang/Integer;", "getCorrectAnswerIndex", "()I", "getQuestionTextResource", "app_debug"}
)
public final class QuizQuestion {
    private final int questionTextResource;
    @NotNull
    private final Integer[] choiceTextResources;
    private final int correctAnswerIndex;

    public final int getQuestionTextResource() {
        return this.questionTextResource;
    }

    @NotNull
    public final Integer[] getChoiceTextResources() {
        return this.choiceTextResources;
    }

    public final int getCorrectAnswerIndex() {
        return this.correctAnswerIndex;
    }

    public QuizQuestion(int questionTextResource, @NotNull Integer[] choiceTextResources, int correctAnswerIndex) {
        super();
        Intrinsics.checkNotNullParameter(choiceTextResources, "choiceTextResources");
        this.questionTextResource = questionTextResource;
        this.choiceTextResources = choiceTextResources;
        this.correctAnswerIndex = correctAnswerIndex;
    }
}
