# DjoudiniTV

Eine moderne Next-Generation IPTV App für Android TV, Smartphone und Tablet.

## Features

- 📺 **Live TV** - Mit EPG (Electronic Program Guide)
- 🎬 **VOD** - Video on Demand mit Favoriten
- 📺 **Serien** - Mit Staffeln und Episoden
- ⭐ **Favoriten** - Für Live TV, VOD und Serien
- 🔄 **Multi-Provider** - Xtream Codes API & M3U Support
- 🎨 **Modernes Design** - Material3 mit Dark Theme
- 📱 **Multi-Plattform** - Android TV, Smartphone & Tablet

## Technische Details

- **Architektur:** MVVM + Clean Architecture
- **UI:** Jetpack Compose
- **Datenbank:** Room
- **Netzwerk:** Retrofit + OkHttp
- **Dependency Injection:** Hilt
- **Video Player:** ExoPlayer

## Build

### Lokaler Build
```bash
./gradlew assembleDebug
```

### GitHub Actions
Die APK wird automatisch bei jedem Push auf `main` gebaut und steht unter [Actions](https://github.com/IISonGokuII/DjoudiniTV/actions) zum Download bereit.

## Versionen

| Komponente | Version |
|------------|---------|
| Kotlin | 1.9.24 |
| AGP | 8.5.0 |
| Gradle | 8.7 |
| Compose BOM | 2024.02.01 |
| minSdk | 21 |
| targetSdk | 34 |

## Lizenz

MIT License
