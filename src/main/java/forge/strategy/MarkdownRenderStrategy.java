package forge.strategy;

import forge.model.Post;

public class MarkdownRenderStrategy implements PostRenderStrategy {
    @Override
    public String render(final Post post) {
        return "#" + post.getTitle() +
                "\n\n" + "*Published by: " + post.getAuthor() + "*" +
                "\n\n" + "--- \n\n" +
                post.getContent();
    }
}
