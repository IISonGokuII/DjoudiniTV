# ❓ Häufig gestellte Fragen (FAQ)

## Allgemein

### Was ist DjoudiniTV?
DjoudiniTV ist eine moderne, Open-Source IPTV-App für Android, die Live-TV, VOD und Serien unterstützt.

### Ist DjoudiniTV kostenlos?
Ja, DjoudiniTV ist komplett kostenlos und Open Source (MIT Lizenz).

### Welche Android-Version wird benötigt?
Minimum Android 5.0 (API 21), empfohlen Android 9.0+.

## Technisch

### Warum ist DjoudiniTV schneller als andere Player?
- Optimiertes LoadControl (1s Buffer statt 15s)
- Batch-Datenbank-Insert
- Hardware-Accelerated Decoding
- Streaming-Parser statt DOM-Parser

### Welche Codecs werden unterstützt?
- Video: H.264, H.265 (HEVC), VP8, VP9
- Audio: AAC, MP3, AC3, E-AC3
- Container: TS, MP4, MKV, HLS, DASH

### Funktioniert DjoudiniTV auf dem Fire TV Stick?
Ja, vollständig optimiert für Amazon Fire TV, Fire Stick und Fire TV Cube.

## Anbieter

### Welche Anbieter werden unterstützt?
- Xtream Codes API v1 & v2
- M3U Playlists (URLs und lokale Dateien)
- Stalker Portal (geplant)

### Ist mein Anbieter kompatibel?
Wenn er Xtream Codes oder M3U unterstützt: Ja!

## Support

### Wo bekomme ich Hilfe?
- GitHub Issues: https://github.com/IISonGokuII/DjoudiniTV/issues
- GitHub Discussions: https://github.com/IISonGokuII/DjoudiniTV/discussions

### Wie melde ich einen Bug?
Erstelle ein Issue mit:
- Gerätemodell
- Android-Version
- Schritte zur Reproduktion
- Screenshots (falls möglich)

## Features

### Gibt es eine Aufnahmefunktion?
Nein, aktuell nicht. Geplant für v2.0.

### Unterstützt DjoudiniTV Chromecast?
Nicht direkt, aber du kannst den Bildschirm deines Geräts casten.

### Kann ich mehrere Anbieter gleichzeitig nutzen?
Ja! Du kannst beliebig viele Anbieter hinzufügen.
