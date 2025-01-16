package utils;
import io.jsonwebtoken.*;
import java.util.Date;
        import io.github.cdimascio.dotenv.Dotenv;
import model.Accounts.AccountToken;

import io.jsonwebtoken.Jwts;

import static utils.ControllersGetter.accountsController;


public class TokenHandler {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String SECRET_KEY = dotenv.get("JWT_SECRET_KEY");

    public static AccountToken generateToken(String idAccount, String accountType) {

        String token = Jwts.builder()
                .setSubject(idAccount)
                .claim("accountType", accountType)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *24)) // 24 hour expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return new AccountToken(idAccount, accountType,token);
    }


    public static AccountToken decryptToken(String token) {
        try {

            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String idAccount = claims.getSubject(); // Get the idAccount (subject)
            String accountType = claims.get("accountType", String.class); // Extract accountType from claims


            return new AccountToken(idAccount, accountType,token);

        } catch (JwtException e) {
            System.out.println("Invalid or expired token.");
            return null;
        }
    }
    public static boolean checkToken(String token) {
        try {
            // Parse the token and verify its signature
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getPayload();

            // Check if the account exists
            String idAccount = claims.getSubject();
            boolean accountExists = accountsController.getAccounts().stream()
                    .anyMatch(account -> account.getIdAccount().equals(idAccount));

            if (!accountExists) {
//                System.out.println("Account with ID " + idAccount + " does not exist.");
                return false;
            }


            return true;
        } catch (ExpiredJwtException e) {
//            System.out.println("Token is expired.");
            return false;
        } catch (JwtException e) {
//            System.out.println("Invalid token: " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("Test case:");

        // Generate a token
        String idAccount = "SDK1213E";
        String accountType = "Admin";
        AccountToken accountToken = generateToken(idAccount, accountType);
        System.out.println("Generated Token: " + accountToken.getToken());

        // Check if the token is valid
        boolean isValid = checkToken(accountToken.getToken());
        System.out.println("Is the token valid? " + isValid);

        // Decrypt the token
        AccountToken decryptedToken = decryptToken(accountToken.getToken());
        if (decryptedToken != null) {
            System.out.println("Decrypted Token: " + decryptedToken.toString());
        }
    }
}