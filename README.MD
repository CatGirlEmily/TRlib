**cos nie tak poszlo z setupem gita i gitignore mi nie dziala na te elementy co trzeba wiec jest .vscode i moj bat do pushu w repo :D**

# what is TRlib
TRlib is a funny terminal renderer, which purpose was being a semi-game/render engine

# what terminal was it developed for
currently im exclusivily testing it all on windows old ass cmd as launching via vsc is pain and i decided
having no colors nor ANSI would be a good challenge / limitation

TAK, postuje to tu tylko po to zeby wojtek nie pytal co 5 minut

# todo
- double buffer (sprawdz performance rysowania calosci na nowo vs sprawdzania zmian)
- MULTI THREAD !!!!!!!!!!!

komponenty:
1. Rdzeń Renderujący (Core Engine)
To serce, które już zacząłeś budować. Musi zarządzać przepływem danych.

Double Buffering: Mechanizm, który trzyma "klatkę w budowie" i "klatkę wyświetlaną", aby uniknąć migotania.

Viewport Management: Możliwość renderowania tylko fragmentu świata (ważne, jeśli świat gry jest większy niż okno terminala).

Color Support: Obsługa kolorów ANSI (16 kolorów, 256 kolorów lub TrueColor).

Layering (Z-Index): System warstw, aby obiekty mogły znajdować się "nad" lub "pod" sobą.

2. Geometria i Rasteryzacja (Primitives)
Zbiór algorytmów zamieniających kształty matematyczne na znaki.

Linie: Implementacja algorytmu Bresenhama (linie pod dowolnym kątem).

Kształty puste i wypełnione: Prostokąty, trójkąty i koła.

Poligony: Możliwość rysowania dowolnych wielokątów.

System współrzędnych: Klasy Vector2 (liczby całkowite) oraz Vector2f (liczby zmiennoprzecinkowe do płynnych animacji).

3. Moduł Tekstowy i Sprity (Assets)
Terminal to tekst, więc musisz mieć zaawansowane narzędzia do jego układania.

Text Alignment: Wyrównywanie tekstu do lewej, prawej i środka wewnątrz zadanego obszaru.

ASCII Sprites: Klasa pozwalająca wczytać plik tekstowy (np. postać ludzika zrobioną z kresek) i narysować go jako jeden obiekt.

Transparency (Alpha): Definiowanie "pustego znaku" w spricie (np. spacja), który nie nadpisuje tego, co jest pod nim.

4. System Wejścia (Input System)
Najtrudniejszy element w Javie (standardowo wymaga klawisza Enter).

Raw Mode Support: Przełączenie terminala w tryb, który wysyła sygnał o wciśnięciu klawisza natychmiast.

Key State Manager: Mapa przechowująca informacje, które klawisze są obecnie trzymane (do płynnego sterowania postaciami).

Mouse Support (opcjonalnie): Obsługa kliknięć w terminalu (obsługiwane przez protokoły XTerm).

5. Narzędzia Matematyczne (Math Utilities)
Collision Detection: Proste sprawdzanie kolizji typu AABB (czy dwa prostokąty się nakładają).

Lerp (Linear Interpolation): Do płynnego przechodzenia obiektów z punktu A do punktu B.

Proportions Correction: Funkcja korygująca aspekt czcionki (czyli np. mnożenie współrzędnej X przez 2.0, by kwadrat wyglądał jak kwadrat).

6. Zarządzanie Czasem (Timing)
Delta Time (dt): Przekazywanie do onUpdate czasu w sekundach, jaki minął od ostatniej klatki (np. 0.033s).

FPS Counter: Wbudowany licznik klatek do celów debugowania.

7. Zarządzanie Scenami (Scene Management)
State Machine: Możliwość łatwego przełączania się między "Menu Głównym", "Grą" a "Ekranem Porażki" bez mieszania kodu w jednej klasie.