import easyocr
import shutil
import os
import time

model_dir = os.path.expanduser('~/.EasyOCR/model')

def try_download():
    for attempt in range(15):
        try:
            print(f"Attempt {attempt+1} to download EasyOCR models...")
            reader = easyocr.Reader(['fr', 'en'])
            print("Download successful!")
            return True
        except Exception as e:
            print(f"Failed: {e}")
            if "retrieval incomplete" in str(e) or "urlopen error" in str(e):
                print("Cleaning corrupted cache...")
                if os.path.exists(model_dir):
                    shutil.rmtree(model_dir, ignore_errors=True)
            time.sleep(2)
    return False

if __name__ == "__main__":
    success = try_download()
    if not success:
        print("Failed to download after 15 attempts.")
