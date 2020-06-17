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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RestController
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    private MerchantRepository merchantRepository;

    @GetMapping("/user/login")
    public ResponseEntity<Boolean> loginUser(@Valid @RequestBody User user) {
        if (checkCredentialsUser(user)) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/merchant/login")
    public ResponseEntity<Boolean> loginMerchant(@Valid @RequestBody Merchant merchant) {
        if (checkCredentialsMerchant(merchant)) {
            return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<Boolean>(false, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/user/register")
    public User createUser(@Valid @RequestBody User user) {
        return userRepository.save(hashPasswordUser(user));
    }

    @PostMapping("/merchant/register")
    public Merchant createMerchant(@Valid @RequestBody Merchant merchant) {
        return merchantRepository.save(hashPasswordMerchant(merchant));
    }

    public boolean checkCredentialsUser(User user) {
        try {
            User dbUser = userRepository.findUserByEmail(user.getEmail()).get(0);
            if (dbUser.getPassword().equals(hashPasswordUser(user).getPassword())) {
                return true;
            }
        } catch (Exception e) {
            //TODO handle error!
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean checkCredentialsMerchant(Merchant merchant) {
        try {
            Merchant dbMerchant = merchantRepository.findMerchantByEmail(merchant.getEmail()).get(0);
            if (dbMerchant.getPassword().equals(hashPasswordMerchant(merchant).getPassword())) {
                return true;
            }
        } catch (Exception e) {
            //TODO handle error!
            e.printStackTrace();
            return false;
        }
        return false;
    }

    private User hashPasswordUser(User user) {
        try {
            user.setPassword(bytesToHex(MessageDigest.getInstance("SHA-256").digest(user.getPassword().getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return user;
    }

    private Merchant hashPasswordMerchant(Merchant merchant) {
        try {
            merchant.setPassword(bytesToHex(MessageDigest.getInstance("SHA-256").digest(merchant.getPassword().getBytes(StandardCharsets.UTF_8))));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return merchant;
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