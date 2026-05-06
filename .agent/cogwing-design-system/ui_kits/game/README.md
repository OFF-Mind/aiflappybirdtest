# Cogwing — Game UI Kit

A click-thru recreation of the Cogwing mobile game shell. Six core surfaces, all in an iPhone frame, all wired up so the prototype can be played end-to-end (the gameplay itself is decorative — taps anywhere on the canvas register as a "crash" so you can demo the loop).

## Run

Open `index.html`. The left rail jumps to any screen; clicking **Take Wing** on the title screen plays through `Title → Gameplay → Game Over → Title`.

## Files

| File | Purpose |
|---|---|
| `index.html` | The harness. Holds nav rail, the iPhone frame, and the screen router. |
| `kit.css` | All visual primitives — `.cw-screen`, `.cw-btn`, `.cw-plate`, `.cw-pipe`, `.cw-bird`, etc. Imports `colors_and_type.css` from project root. |
| `Atoms.jsx` | `Icon`, `Rivets`, `PrimaryButton`, `SecondaryButton`, `GhostButton`, `IconButton`, `ScoreWell`, `Sky` |
| `TitleScreen.jsx` | Landing — logo, "Take Wing", best, logbook/workshop entry. |
| `GameScreen.jsx` | In-flight HUD — pipes, bird sprite, score pill, gauges, pause. |
| `GameOverScreen.jsx` | Parchment scroll with run summary, new-best stinger, retry. |
| `LeaderboardScreen.jsx` | "The Logbook" — podium + ranked list. |
| `ShopScreen.jsx` | "The Workshop" — six bird skins, equipped/owned/locked states. |
| `SettingsScreen.jsx` | "Adjustments" — toggles, pilot card, link rows, reset. |
| `ios-frame.jsx` | Borrowed iPhone frame from the starter components. |

## Components covered

- **Buttons** — primary (riveted brass plate), secondary (outline), ghost (dashed), danger (ember), icon button.
- **Surfaces** — brass plate, parchment scroll, engraved well.
- **HUD** — score pill, gauge readouts, pause button, hearts.
- **Lists** — ranked leaderboard rows, settings rows, link rows.
- **Toggles** — brass-knob slider on engraved track.
- **Shop tiles** — preview frame + name + price/equip state, RARE badge.
- **Decoration** — pipe towers, bird sprite, steam puffs, smokestack silhouette.

## What's intentionally not here

- A real gameplay loop. The bird does not actually fly through pipes — that's a Phaser/Canvas concern, not a UI-kit concern.
- Onboarding, tutorial, daily-reward, paywall — easy to add against these primitives once the core feel is approved.
