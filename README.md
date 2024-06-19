# java-cryptography

The Caesar cipher is a basic encryption technique where all letters in a message are shifted down the alphabet by a certain number (determined by the key). In order to encrypt or decrypt a message, a secret key is required that should, in practice, be hard to find for anyone who doesn’t already know it. In the Caesar cipher, the key is the number of places to shift each character. This number could be specified numerically (e.g., 4) or it could be specified as a character (e.g., ‘E’ – which is 4 places over from ‘A’). 

High-Level Functions:

I create functions to handle the encoding/decoding and shifting/unshifting of inputted messages: encrypt(), decrypt(), and crack().

I add one more step to reach the final goal of cracking the Caesar cipher. This final step involves letter frequency analysis. I took a text or String and counted how often each letter in the alphabet appears throughout the text. English text will have lots of “E”s and “A”s, while an encrypted cipher may have an unusually high number of “X”s or “B”s.
