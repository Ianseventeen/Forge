package forge.service;

import forge.model.Post;
import forge.strategy.PostRenderStrategy;

public class PostService {
    public String viewPost(final Post post, final PostRenderStrategy strategy){
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        if (strategy == null) {
            throw new IllegalArgumentException("Render strategy cannot be null");
        }
        return strategy.render(post);
    }
}
