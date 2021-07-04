package loc.abondare.sarafan.service.config;

import loc.abondare.sarafan.domain.User;
import loc.abondare.sarafan.repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Objects;


public class SarafanDelegatingOAut2UserService extends DelegatingOAuth2UserService {

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    /**
     * Constructs a {@code DelegatingOAuth2UserService} using the provided parameters.
     *
     * @param oAuth2UserService a {@code List} of {@link OAuth2UserService}(s)
     */
    public SarafanDelegatingOAut2UserService(List oAuth2UserService) {
        super(oAuth2UserService);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String id = Objects.requireNonNull(oAuth2User.getAttribute("sub"));

        User user = userDetailsRepo.findById(id).orElseGet(() -> {
            User newUser = new User();

            newUser.setId(id);
            newUser.setName(oAuth2User.getAttribute("name"));
            newUser.setEmail(oAuth2User.getAttribute("email"));

            return newUser;
        });

        return oAuth2User;
    }
}
