package forge.model;

import lombok.*;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String university;
    private String course;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
