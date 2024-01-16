package be.bds.bdsbes.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/rpc/bds/recaptcha")
public class CaptchaController {

    @Value("${google.recaptcha.secret-key}")
    private String recaptchaSecretKey;

    @PostMapping("/verify-captcha")
    @ResponseBody
    public String verifyCaptcha(@RequestBody String captchaResponse) {
        if (isValidCaptcha(captchaResponse)) {
            return "Captcha verified: " + captchaResponse;
        } else {
            return "Captcha verification failed";
        }
    }

    private boolean isValidCaptcha(String captchaResponse) {
        // Make a request to the reCAPTCHA API to verify the captchaResponse
        String verifyUrl = "https://www.google.com/recaptcha/api/siteverify";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded");

        String requestBody = "secret=" + recaptchaSecretKey + "&response=" + captchaResponse;

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<RecaptchaResponse> responseEntity = new RestTemplate().exchange(
                verifyUrl,
                HttpMethod.POST,
                requestEntity,
                RecaptchaResponse.class
        );

        RecaptchaResponse responseBody = responseEntity.getBody();

        // Check if the response indicates success
        return responseBody != null && responseBody.isSuccess();
    }

    private static class RecaptchaResponse {
        private boolean success;
        // Add other fields if needed

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }
}
