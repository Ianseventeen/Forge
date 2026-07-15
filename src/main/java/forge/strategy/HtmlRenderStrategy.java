package forge.strategy;

import forge.model.Post;

public class HtmlRenderStrategy implements PostRenderStrategy {
        @Override
        public String render(final Post post) {
            return "<article>\n" +
                    "  <h1>" + post.getTitle() + "</h1>\n" +
                    "  <p>Published by: <strong>" + post.getAuthor() + "</strong></p>\n" +
                    "  <hr/>\n" +
                    "  <div class=\"content\">" + post.getContent() + "</div>\n" +
                    "</article>";
        }
}
