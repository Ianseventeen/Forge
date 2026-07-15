package forge.strategy;

import forge.model.Post;

public interface PostRenderStrategy {
    String render(final Post post);
}
