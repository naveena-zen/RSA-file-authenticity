import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.*;
import java.util.Scanner;

public class FileIntegrityChecker_RSA {

    // Generate RSA Key Pair (2048-bit)
    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    // Sign file using Private Key
    public static byte[] signFile(String filePath, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);

        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                signature.update(buffer, 0, bytesRead);
            }
        }

        return signature.sign();
    }

    // Verify file using Public Key
    public static boolean verifyFile(String filePath, byte[] digitalSignature, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);

        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                signature.update(buffer, 0, bytesRead);
            }
        }

        return signature.verify(digitalSignature);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== FILE INTEGRITY AND AUTHENTICITY VERIFICATION USING RSA ===");
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();

        try {
            // Generate RSA Key Pair
            KeyPair keyPair = generateKeyPair();

            // Sign the file
            byte[] digitalSignature = signFile(filePath, keyPair.getPrivate());

            // Save signature to file
            try (FileOutputStream fos = new FileOutputStream("signature.bin")) {
                fos.write(digitalSignature);
            }

            System.out.println("\nFile digitally signed successfully.");
            System.out.println("Signature saved as 'signature.bin'.");

            System.out.print("\nModify the file (or skip) and press Enter to verify...");
            scanner.nextLine();

            // Verify the file
            boolean isVerified = verifyFile(filePath, digitalSignature, keyPair.getPublic());

            if (isVerified) {
                System.out.println("\nFile is authentic and unmodified.");
            } else {
                System.out.println("\nFile has been modified or the signature is invalid.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
