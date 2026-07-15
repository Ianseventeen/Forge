package forge.service;

import forge.model.User;
import forge.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User registerUser (final User user){
            if(user.getEmail() == null || user.getEmail().isEmpty()) {
                throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
            }
            if (!user.getEmail().contains("@")) {
                throw new IllegalArgumentException("Email inválido");
            }
            isPasswordStrong(user.getPassword());
        userRepository.insert(user);
        return user;
    }


    public User login(final String email, final String password){
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }

    public boolean isPasswordStrong(final String password){
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password can not be null or empty");
        }
        else if (password.length() < 6) {
            throw new IllegalArgumentException("Password must have at least 6 characters");
        }
        return true;
    }
}
