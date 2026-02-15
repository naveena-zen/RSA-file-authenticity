# File Integrity and Authenticity Verification Using RSA Digital Signatures

## Overview

This project implements RSA-based digital signature generation and verification to ensure file integrity and authenticity. It detects unauthorized modifications and validates the origin of a file using asymmetric cryptography.
The system signs a file using a private key and verifies it using the corresponding public key. Any modification to the file content results in verification failure.

## Objectives

- Ensure file integrity using RSA digital signatures  
- Authenticate file origin using public–private key cryptography  
- Detect tampering during file storage or transmission  

## Cryptographic Details

- **Algorithm:** RSA  
- **Key Size:** 2048-bit  
- **Signature Algorithm:** SHA256withRSA  
- **Security Model:** Asymmetric Cryptography  

The `SHA256withRSA` algorithm:
1. Hashes the file using SHA-256  
2. Encrypts the hash using the RSA private key  
3. Verifies using the RSA public key  

## Methodology

### 1️⃣ Key Generation
Generate a 2048-bit RSA key pair.

### 2️⃣ Signing Phase
- Read file data using buffered streams.
- Generate digital signature using the private key.
- Store the signature as `signature.bin`.

### 3️⃣ Verification Phase
- Re-read the file.
- Verify the signature using the public key.
- If verification fails, the file has been modified.

## Run
1. Enter the file path.
2. The file will be digitally signed.
3. Modify the file (optional).
4. Press Enter to verify.

## Expected Output

- File digitally signed successfully  
- File is authentic and unmodified  
- File has been modified or signature is invalid  

## Security Features

- 2048-bit RSA key strength  
- SHA-256 hashing (internal to signature algorithm)  
- Digital signature-based tamper detection  
- Public key authenticity verification  

## Applications

- Secure document validation  
- Software integrity verification  
- Digital record authentication  
- Secure file transmission systems  
