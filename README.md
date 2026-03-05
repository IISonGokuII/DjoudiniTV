# 🎬 DjoudiniTV - Next-Generation IPTV Platform

<p align="center">
  <img src="https://img.shields.io/badge/Kotlin-1.9.24-7F52FF?logo=kotlin&logoColor=white" />
  <img src="https://img.shields.io/badge/Android-API%2021+-3DDC84?logo=android&logoColor=white" />
  <img src="https://img.shields.io/badge/Compose-BOM%202024.02.01-4285F4?logo=jetpackcompose&logoColor=white" />
  <img src="https://img.shields.io/badge/ExoPlayer-1.3.1-E60012?logo=google&logoColor=white" />
  <img src="https://img.shields.io/badge/License-MIT-green.svg" />
</p>

<p align="center">
  <b>Die revolutionäre IPTV-Lösung für Android TV, Smartphone & Tablet</b><br>
  ⚡ Blitzschnelles Zapping | 🎨 Modernes Material3 Design | 🌍 Multi-Provider Support
</p>

---

## 🚀 Warum DjoudiniTV der beste IPTV Player ist

### ⚡ **Unübertroffene Geschwindigkeit**
- **< 1 Sekunde Channel Zapping** durch optimierten ExoPlayer mit Custom LoadControl
- **Streaming Parser** für M3U Dateien - verarbeitet 100.000+ Streams in unter 5 Sekunden
- **Hardware-Accelerated Decoding** mit automatischem Software-Fallback
- **Intelligentes Buffering**: 1s minBuffer für sofortiges Abspielen

### 🎨 **Premium User Experience**
- **Material3 Design** mit dynamischem Dark Theme
- **Adaptive Layouts**: Optimiert für TV (Leanback), Tablet & Smartphone
- **TV-First Navigation**: D-Pad Support mit Focus-Animationen
- **Glassmorphism UI**: Moderne, halbtransparente Oberflächen

### 📺 **Komplette Feature-Liste**

| Feature | Beschreibung | Status |
|---------|--------------|--------|
| 📡 **Live TV** | Mit EPG (Electronic Program Guide) | ✅ |
| 🎬 **VOD** | Video on Demand mit Favoriten & Fortschritt | ✅ |
| 📺 **Serien** | Staffel/Episoden-Verwaltung | ✅ |
| ⭐ **Favoriten** | Für alle Content-Typen | ✅ |
| 🔄 **Multi-Provider** | Xtream Codes API & M3U Support | ✅ |
| 📊 **EPG** | XMLTV Integration mit WorkManager | ✅ |
| 🔍 **Suche** | Volltextsuche in allen Kategorien | ✅ |
| 📱 **Cross-Platform** | Android TV, Phone & Tablet | ✅ |

### 🛠 **Technische Exzellenz**

```kotlin
// Moderne Architektur
MVVM + Clean Architecture + Repository Pattern

// Cutting-Edge Technologien
Jetpack Compose • ExoPlayer • Room • Hilt • Kotlin Coroutines

// Enterprise-Grade
KSP (Kein Kapt) • Version Catalog • CI/CD Ready • ProGuard Optimized
```

---

## 📸 Screenshots

<p align="center">
  <i>Dashboard | Live TV | VOD | Player</i><br><br>
  <img src="docs/images/dashboard.png" width="200" />
  <img src="docs/images/livetv.png" width="200" />
  <img src="docs/images/vod.png" width="200" />
  <img src="docs/images/player.png" width="200" />
</p>

---

## 🏆 Key Differentiators

### 1. **Performance-First Ansatz**
```kotlin
// ExoPlayer optimiert für IPTV
val loadControl = DefaultLoadControl.Builder()
    .setBufferDurationsMs(
        1_000,   // minBufferMs → 1s für schnelles Zapping
        10_000,  // maxBufferMs
        500,     // bufferForPlaybackMs
        1_000    // bufferForPlaybackAfterRebufferMs
    )
    .build()
```

### 2. **Intelligentes Streaming**
- **Batch Insert**: 500 Streams pro Room Transaction
- **Flow-basierte Architektur**: Reaktive Datenströme
- **Paging Support**: Effiziente Listen für große Bibliotheken

### 3. **Enterprise Architecture**
```
app/
├── data/
│   ├── local/        # Room DB mit Entities & DAOs
│   ├── remote/       # Retrofit APIs
│   └── repository/   # Repository Implementierungen
├── domain/
│   ├── model/        # Domain Models
│   ├── repository/   # Repository Interfaces
│   └── usecase/      # Single Responsibility UseCases
└── presentation/
    ├── theme/        # Material3 Theme & Typography
    ├── common/       # Shared Components
    └── screens/      # Feature Screens
```

### 4. **TV-Optimized Experience**
- **Leanback Support**: Native Android TV Integration
- **Focus Navigation**: D-Pad & Remote Control optimiert
- **TV Banner**: Große Kacheln (min. 160dp)
- **Background Playback**: Picture-in-Picture Support

---

## 🚀 Quick Start

### Installation

#### Option 1: APK Download
1. Gehe zu [Releases](https://github.com/IISonGokuII/DjoudiniTV/releases)
2. Lade die neueste `app-debug.apk` herunter
3. Installiere auf deinem Android TV oder Smartphone

#### Option 2: Selbst bauen
```bash
# Repository klonen
git clone https://github.com/IISonGokuII/DjoudiniTV.git

# In das Verzeichnis wechseln
cd DjoudiniTV

# Debug APK bauen
./gradlew assembleDebug

# APK befindet sich in:
# app/build/outputs/apk/debug/app-debug.apk
```

### Erste Schritte

1. **App starten** → Dashboard mit 5 Hauptkacheln
2. **Anbieter hinzufügen** → Klicke auf "+" Button
3. **Xtream Codes oder M3U** auswählen
4. **Zugangsdaten eingeben** → Automatische Synchronisation
5. **Live TV/VOD genießen** → Sofortiger Zugriff auf alle Inhalte

---

## 📋 Systemanforderungen

| Komponente | Minimum | Empfohlen |
|------------|---------|-----------|
| Android API | 21 (Android 5.0) | 28+ (Android 9+) |
| RAM | 2 GB | 4 GB |
| Storage | 100 MB | 500 MB |
| Internet | 5 Mbit/s | 25+ Mbit/s für HD |

---

## 🛠 Technischer Stack

### Core Technologies
- **Kotlin 1.9.24** - Moderne, typsichere Sprache
- **Jetpack Compose** - Deklarative UI
- **ExoPlayer 1.3.1** - Industrie-Standard Video Player
- **Material3** - Googles neuestes Design-System

### Architecture Components
- **Room 2.6.1** - SQLite Abstraction
- **Hilt 2.51** - Dependency Injection
- **Navigation Compose** - Type-safe Navigation
- **ViewModel** - Lifecycle-aware State Management

### Network & Data
- **Retrofit 2.11.0** - REST API Client
- **Kotlinx Serialization** - JSON Parsing
- **OkHttp 4.12.0** - HTTP Client
- **Coil 2.6.0** - Image Loading

---

## 🤝 Mitwirken

Wir freuen uns über Beiträge! Siehe [CONTRIBUTING.md](CONTRIBUTING.md) für Details.

### Entwicklung Setup
```bash
# 1. Fork & Clone
git clone https://github.com/dein-username/DjoudiniTV.git

# 2. Öffne in Android Studio
# - Minimum Android Studio Hedgehog (2023.1.1)
# - JDK 17 required

# 3. Sync Gradle
./gradlew build

# 4. Starte auf Gerät oder Emulator
```

---

## 📜 Lizenz

```
MIT License

Copyright (c) 2024 DjoudiniTV Contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## 🙏 Danksagung

- **Google** für Android & Jetpack Compose
- **Square** für Retrofit & OkHttp
- **Google ExoPlayer** Team
- **Android Open Source Project**

---

<p align="center">
  <b>Made with ❤️ for the IPTV Community</b><br>
  <a href="https://github.com/IISonGokuII/DjoudiniTV">GitHub</a> •
  <a href="https://github.com/IISonGokuII/DjoudiniTV/issues">Issues</a> •
  <a href="https://github.com/IISonGokuII/DjoudiniTV/discussions">Discussions</a>
</p>
