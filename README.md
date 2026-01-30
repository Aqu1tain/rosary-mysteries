# Rosary Mysteries

Android app for praying the Rosary with Bible references.

## Features

- Four mystery sets: Joyful, Sorrowful, Glorious, Luminous
- Tap any mystery to open the Bible passage in YouVersion
- Theme: System, Light, Dark
- Languages: English, French, Spanish, Italian

## Build

```bash
./gradlew assembleDebug
```

For release builds, create `keystore.properties`:
```properties
storeFile=path/to/keystore.jks
storePassword=...
keyAlias=...
keyPassword=...
```

## Branch Strategy

```
main (releases) <- dev (pre-releases) <- feature/*
```

- **main**: Production releases only. Tagged `vX.Y.Z`.
- **dev**: Integration branch. Tagged `vX.Y.Z-rc.N` for pre-releases.
- **feature/\***: Feature branches from dev.

## Contribution

1. Branch from `dev`: `git checkout -b feature/my-feature dev`
2. Commit using [Conventional Commits](https://conventionalcommits.org)
3. Open PR to `dev` (squash merge)
4. Releases: PR from `dev` to `main` (merge commit)

Rebase is not used.

## License

MIT
