package campuscup.localangels.backend.controller;

import campuscup.localangels.backend.model.Merchant;
import campuscup.localangels.backend.model.User;
import campuscup.localangels.backend.repository.MerchantRepository;
import campuscup.localangels.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthController {

    @Autowired
    private UserRepository userRepository;
    private MerchantRepository merchantRepository;

    @GetMapping("/user/login")
    public ResponseEntity<Boolean> login(@Valid @RequestBody User user) {
        if (checkCredentials(user)) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/user/register")
    public ResponseEntity<Boolean> register(@Valid @RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    @PostMapping("/user/register/merchant")
    public ResponseEntity<Boolean> registerMerchant(@Valid @RequestBody Merchant merchant) {
        merchantRepository.save(merchant);
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }

    public boolean checkCredentials(User user) {
        try {

            User dbUser = userRepository.findUserByEmail(user.getEmail());
            if (dbUser.getPassword().equals(user.getPassword())) {
                return true;
            }
        } catch (Exception e) {
            //TODO handle error!
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private User hashPassword(User user) {
        try {
            user.setPassword(bytesToHex(MessageDigest.getInstance("SHA-256").digest(user.getPassword().getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return user;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}