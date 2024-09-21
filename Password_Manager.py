import hashlib
import sys
import os

password_manager = {}

# Detect platform and import necessary modules
if os.name == "nt": # Windows
    import msvcrt

else: # Unix-based
    import termios
    import tty
    
def get_password(prompt):
    print(prompt, end='', flush=True)
    password = ''
    
    if os.name == 'nt': # Windows implementation 
        while True:
            char = msvcrt.getch()
            
            if char == b'\r': # Enter key pressed
                print('')
                break
            
            elif char == b'\x08': # Backspace key pressed
                if len(password) > 0:
                    password = password[:-1]
                    print('\b \b', end='', flush=True)
            
            else:
                password += char.decode("utf-8")
                print('*', end='', flush=True)
                
    else: # Unix-based implementation
        fd = sys.stdin.fileno()
        old_settings = termios.tcgetattr(fd)
        try:
            tty.setraw(sys.stdin.fileno())
            while True:
                char = sys.stdin.read(1)
                
                if char == '\n' or char == '\r': # Enter key pressed
                    print('')
                    break
                
                elif char == '\x7f': # Backspace key pressed
                    if len(password) > 0:
                        password = password[:-1]
                        print('\b \b', end='', flush=True)
                        
                else:
                    password += char
                    print('*', end='', flush=True)
                    
        finally:
            termios.tcsetattr(fd, termios.TCSADRAIN, old_settings)
            
    return password

def create_account():
    username = input("Enter your desired username: ")
    password = get_password("Enter your desired password: ")
    hashed_password = hashlib.sha256(password.encode()).hexdigest()
    password_manager[username] = hashed_password
    print("Account created successfully!")

def login():
    username = input("Enter your username: ")
    password = get_password("Enter your password: ")
    hashed_password = hashlib.sha256(password.encode()).hexdigest()

    if username in password_manager.keys() and password_manager[username] == hashed_password:
        print("Login Successful!")

    else:
        print("Invalid username or password.")

def main():
    while True:
        choice = input("Enter 1 to create an account, 2 to login, or 0 to exit: ")
        if choice == "1":
            create_account()

        elif choice == "2":
            login()

        elif choice == "0":
            break

        else:
            print("Invalid input.")

main()