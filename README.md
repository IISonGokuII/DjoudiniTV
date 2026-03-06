# DjoudiniTV

Eine moderne Android IPTV-App mit Jetpack Compose für Live-TV, Video-on-Demand (VOD) und Serien.

## Features

### ✅ Live TV
- Kanal-Liste mit Kategorien
- EPG (Elektronischer Programmführer)
- Favoriten-Verwaltung
- ExoPlayer mit HLS-Unterstützung
- Fullscreen-Player mit Gesten-Steuerung

### ✅ VOD (Filme)
- Film-Browser mit Kategorien
- Detail-Ansicht mit Poster, Handlung, Cast
- Suchfunktion
- Filter nach Genre

### ✅ Serien
- Serien-Übersicht mit Staffeln
- Episoden-Liste pro Staffel
- Fortschritts-Tracking
- Auto-Play nächste Episode

### ✅ Provider-Verwaltung
- Xtream Codes API Unterstützung
- Mehrere Provider gleichzeitig
- Automatische Synchronisation
- Offline-Unterstützung ( lokale Datenbank)

### ✅ Onboarding
- 6-Schritte Einrichtungsassistent
- Provider-Login
- Kategorie-Auswahl
- Erst-Synchronisation

## Tech Stack

| Komponente | Technologie |
|------------|-------------|
| UI | Jetpack Compose |
| Architektur | MVVM + Clean Architecture |
| Dependency Injection | Hilt |
| Datenbank | Room (SQLite) |
| Netzwerk | Retrofit + Kotlinx Serialization |
| Player | ExoPlayer (Media3) |
| Async | Kotlin Coroutines + Flow |
| Build | Gradle mit Kotlin DSL |

## Voraussetzungen

- Android 5.0+ (API 21+)
- Xtream Codes API kompatibler Provider
- Internet-Verbindung

## Installation

### APK Installieren
1. Lade die neueste APK von den [Releases](../../releases) herunter
2. Installiere die APK auf deinem Android-Gerät
3. Folge dem Einrichtungsassistenten

### Selbst Bauen
```bash
# Repository klonen
git clone https://github.com/IISonGokuII/DjoudiniTV.git
cd DjoudiniTV

# Debug-APK bauen
./gradlew assembleDebug

# Oder Release-APK
./gradlew assembleRelease
```

## Erste Schritte

### 1. Provider Hinzufügen
- Öffne die App
- Tippe auf das ➕ Symbol
- Wähle "Xtream Codes API"
- Gib Server-URL, Username und Passwort ein
- Wähle zu synchronisierende Kategorien

### 2. Live TV Schauen
- Gehe zum Dashboard
- Tippe auf "Live TV"
- Wähle eine Kategorie
- Tippe auf einen Kanal zum Abspielen

### 3. Filme & Serien
- Nutze die Suchleiste für schnellen Zugriff
- Filtere nach Kategorien
- Tippe auf ein Poster für Details

## Architektur

```
app/
├── data/                 # Daten-Layer
│   ├── local/           # Room Datenbank, Entities, DAOs
│   ├── remote/          # API Services, DTOs
│   └── repository/      # Repository Implementierungen
├── domain/              # Domain-Layer
│   ├── model/           # Domain Models
│   ├── repository/      # Repository Interfaces
│   └── usecase/         # Use Cases
├── ui/                  # UI-Layer (Presentation)
│   ├── screen/          # Compose Screens
│   ├── viewmodel/       # ViewModels
│   ├── navigation/      # Navigation Graph
│   └── theme/           # Material Design Theme
└── presentation/        # Onboarding & Shared UI
```

## Verbesserungsvorschläge (Roadmap)

### Phase 1 (Sofort)
- [ ] Zuletzt gesehen auf Dashboard
- [ ] Favoriten-Feature vervollständigen
- [ ] PlayerViewModel Integration

### Phase 2 (Kurzfristig)
- [ ] M3U Playlist Support
- [ ] TV-Guide (EPG) Vollbild
- [ ] Gesten-Steuerung im Player

### Phase 3 (Mittelfristig)
- [ ] Download/Offline-Modus
- [ ] Globale Suche
- [ ] Mehrere Profile

### Phase 4 (Langfristig)
- [ ] Catch-Up TV
- [ ] PVR/Aufnahme-Funktion
- [ ] Cloud-Backup

## Bekannte Probleme

- Favoriten sind noch nicht vollständig implementiert
- EPG-Provider-Löschung ist nicht implementiert
- Cache-Leeren in Einstellungen ist ein Stub

## Lizenz

```
Copyright 2024 DjoudiniTV Contributors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## Danksagung

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [ExoPlayer](https://github.com/google/ExoPlayer)
- [Hilt](https://dagger.dev/hilt/)
- [Room](https://developer.android.com/training/data-storage/room)

---

**Hinweis:** Diese App benötigt einen eigenen IPTV-Provider. Die App enthält keine vorinstallierten Kanäle oder Inhalte.
