# =========================================================
# EXTRACTION D'INFORMATIONS D'UNE AFFICHE D'ÉVÉNEMENT
# VERSION AMÉLIORÉE
# =========================================================

# INSTALLATION :
# pip install easyocr opencv-python matplotlib numpy

import cv2
import easyocr
import re
import os
import matplotlib.pyplot as plt

# =========================================================
# CHEMIN IMAGE
# =========================================================

image_path = input("Entrez le chemin de l'image : ")

if not os.path.exists(image_path):
    print("Image introuvable.")
    exit()

# =========================================================
# INITIALISATION OCR
# =========================================================

print("Chargement du modèle OCR...")

reader = easyocr.Reader(['fr', 'en'])

# =========================================================
# LECTURE IMAGE
# =========================================================

image = cv2.imread(image_path)

if image is None:
    print("Erreur lors du chargement de l'image.")
    exit()

# =========================================================
# PRÉTRAITEMENT AMÉLIORÉ
# =========================================================

gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

# Agrandir image
gray = cv2.resize(gray, None, fx=2, fy=2)

# Réduction du bruit
blur = cv2.bilateralFilter(gray, 11, 17, 17)

# Binarisation
processed = cv2.threshold(
    blur,
    0,
    255,
    cv2.THRESH_BINARY + cv2.THRESH_OTSU
)[1]

# =========================================================
# AFFICHAGE IMAGE TRAITÉE
# =========================================================

plt.figure(figsize=(10,10))
plt.imshow(processed, cmap='gray')
plt.title("Image prétraitée")
plt.axis("off")
plt.show()

# =========================================================
# OCR
# =========================================================

print("Extraction du texte...")

results = reader.readtext(
    processed,
    detail=0,
    paragraph=True
)

text = " ".join(results)

print("\n================ TEXTE OCR ================\n")
print(text)

# =========================================================
# EXTRACTION DATE
# =========================================================

patterns = [
    r'\d{1,2}[/-]\d{1,2}[/-]\d{2,4}',
    r'\d{1,2}\s(?:janvier|février|mars|avril|mai|juin|juillet|août|septembre|octobre|novembre|décembre)\s\d{2,4}',
    r'\d{1,2}\s(?:jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)\s\d{2,4}'
]

dates = []

for pattern in patterns:
    matches = re.findall(pattern, text, flags=re.IGNORECASE)
    dates.extend(matches)

date_evenement = dates[0] if len(dates) > 0 else "Non trouvée"

# =========================================================
# EXTRACTION PRIX
# =========================================================


price_patterns = [
    r'\d+\s?(?:dt|dinar|dinars|€|\$)',
    r'(?:dt|€|\$)\s?\d+',
    r'\b\d{1,3}\b'
]

prices = []

for pattern in price_patterns:
    matches = re.findall(pattern, text, flags=re.IGNORECASE)

    for m in matches:

        # éviter dates/heures
        if m not in ["20", "23", "08"]:
            prices.append(m)

prix = prices[0] if len(prices) > 0 else "Non trouvé"

# =========================================================
# EXTRACTION NOM PERFORMER
# =========================================================

performer = "Non trouvé"

# mots souvent utilisés
keywords = [
    "dj",
    "live",
    "feat",
    "featuring",
    "with",
    "by",
    "present",
    "presents"
]

words = text.split()

for i, word in enumerate(words):

    lower = word.lower()

    if lower in keywords:

        possible_names = []

        # prendre les 3 mots suivants
        for j in range(i + 1, min(i + 4, len(words))):

            candidate = words[j]

            # mots en majuscule souvent = nom artiste
            if candidate.isupper() or candidate[0].isupper():
                possible_names.append(candidate)

        if possible_names:
            performer = " ".join(possible_names)
            break

# fallback
if performer == "Non trouvé":

    upper_words = []

    for w in words:

        if len(w) > 3 and w.isupper():

            if w not in [
                "FRESH",
                "CLUB",
                "PRESENT",
                "MUSIC",
                "SATURDAY",
                "TICKET"
            ]:
                upper_words.append(w)

    if upper_words:
        performer = upper_words[-1]
# =========================================================
# TYPE D'ÉVÉNEMENT
# =========================================================

types = {
    "Concert": ["concert", "live", "music", "festival", "dj"],
    "Conférence": ["conférence", "conference", "seminar", "talk"],
    "Sport": ["match", "football", "sport", "tournament"],
    "Soirée": ["party", "soirée", "night"],
    "Exposition": ["expo", "exposition", "gallery"]
}

lower_text = text.lower()

event_type = "Type inconnu"

for t, keywords in types.items():
    for keyword in keywords:
        if keyword in lower_text:
            event_type = t
            break

# =========================================================
# NOM ÉVÉNEMENT
# =========================================================

event_name = "Nom non trouvé"

sentences = text.split()

if len(sentences) >= 6:
    event_name = " ".join(sentences[:6])

# =========================================================
# RÉSULTATS
# =========================================================

print("\n================ RÉSULTATS ================\n")

print("Nom événement :", event_name)
print("Type :", event_type)
print("Date :", date_evenement)
print("Prix :", prix)
print("Performer :", performer)