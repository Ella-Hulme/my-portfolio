import java.util.Scanner;

public class PermutationCipherKnownKey {

    // Permutation key (index starts at 0)
    private static int[] key = {2, 0, 3, 1}; // This key means swap characters at index 0 with 2, 1 eith 3, etc.

    // Function to encrypt plaintext
    public static String encrypt(String plaintext) {
        int n = key.length;
        StringBuilder encrypted = new StringBuilder();

        // Process text in blocks of key.length
        for (int i = 0; i < plaintext.length(); i += n) {
            char[] block = new char[n];

            // Fill the block with characters from the plaintext or padding spaces
            for (int j = 0; j < n; j++) {
                if (i + j < plaintext.length()) {
                    block[j] = plaintext.charAt(i + j);

                } else {
                    block[j] = ' '; // padding with space if block is incomplete

                }
            }

            // Apply the permutation to the block
            char[] encryptedBlock = new char[n];
            for (int j = 0; j < n; j++) {
                encryptedBlock[j] = block[key[j]];
                
            }

            // Append the encrypted block to the result
            encrypted.append(new String(encryptedBlock));

        }

        return encrypted.toString();
    }

    // Function to decrypt ciphertext
    public static String decrypt(String ciphertext) {
        int n = key.length;
        StringBuilder decrypted = new StringBuilder();

        //Reverse key to undo permutation
        int[] reverseKey = new int[n];
        for (int i = 0; i < n; i++) {
            reverseKey[key[i]] = i;

        }

        // Process text in blocks of 'key.length'
        for (int i = 0; i < ciphertext.length(); i += n) {
            char[] block = new char[n];

            // Fill the block with characters from the ciphertext
            for (int j = 0; j < n; j++) {
                if (i + j < ciphertext.length()) {
                    block[j] = ciphertext.charAt(i + j);

                } else {
                    block[j] = ' '; 

                }
            }

            // Apply the reverse permutation to the block
            char[] decryptedBlock = new char[n];
            for (int j = 0; j < n; j++) {
                decryptedBlock[j] = block[reverseKey[j]];

            }

            // Append the decrypted block to the results
            decrypted.append(new String(decryptedBlock));
        }

        return decrypted.toString().trim(); // Trim extra padding spaces if present
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user input
        System.out.println("Enter plaintext to encrypt: ");
        String plaintext = scanner.nextLine();

        // Encrypt the plaintext
        String ciphertext = encrypt(plaintext);
        System.out.println("Encrypted ciphertext: " + ciphertext);

        // Decrypt the ciphertext
        String decryptedText = decrypt(ciphertext);
        System.out.println("Decrypted plaintext: " + decryptedText);

        scanner.close();
    }
}