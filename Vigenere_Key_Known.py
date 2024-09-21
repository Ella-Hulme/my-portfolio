# Function to encrypt plaintext
def vigenere_encrypt(plain_text, key):
    encrypted_text = []
    key = key.upper()
    key_length = len(key)

    for i, char in enumerate(plain_text):
        if char.isalpha():  # Only encrypt alphabetic characters
            shift = ord(key[i % key_length]) - ord('A')

            if char.isupper():
                encrypted_text.append(chr((ord(char) - ord('A') + shift) % 26 + ord('A')))

            else:
                encrypted_text.append(chr((ord(char) - ord('a') + shift) % 26 + ord('a')))

        else:
            encrypted_text.append(char) 

    return ''.join(encrypted_text)

# Function to decrypt ciphertext
def vigenere_decrypt(cipher_text, key):
    decrypted_text = []
    key = key.upper()
    key_length = len(key)

    for i, char in enumerate(cipher_text):
        if char.isalpha():
            shift = ord(key[i % key_length]) - ord('A')

            if char.isupper():
                decrypted_text.append(chr((ord(char) - ord('A') - shift) % 26 + ord('A')))

            else:
                decrypted_text.append(chr((ord(char) - ord('a') - shift) % 26 + ord('a')))

        else:
            decrypted_text.append(char)

    return ''.join(decrypted_text)


# Fixed key
key = "KEY"

# User input for plaintext
plain_text = input("Enter the text to encrypt: ")

# Encryption
encrypted_text = vigenere_encrypt(plain_text, key)
print("\n", "Encrypted Text:", encrypted_text, "\n")

# Decryption
decrypted_text = vigenere_decrypt(encrypted_text, key)
print("Decrypted Text:", decrypted_text)
