# Cogwing Design System

> _A mechanical flight._

**Cogwing** is a one-tap casual mobile game in the spirit of Flappy Bird, dressed in late-Victorian / early-industrial steampunk regalia. The player taps to flap a brass-and-copper clockwork bird through a hazy dusk sky strewn with riveted pipe-towers, smoking chimneys, and floating clockwork contraptions. Score is rendered like a brass coin counter; menus look like brass plates riveted to teak wood; alerts arrive on parchment.

This design system contains the brand foundations, tokens, assets, components, and a sample UI kit needed to build any surface that should feel like Cogwing — landing pages, app screens, store listings, decks, prototypes.

## Sources

No external assets, codebase, or Figma were attached for this brief. The brand was developed from the prompt _"Simple casual flappy-bird-like game but in steampunk design."_ All identity work — name (Cogwing), wordmark, palette, type pairing, illustrations, and UI kit — is original to this system.

If you have an existing brand bible, logo files, or production code we should reconcile against, attach them via the Import menu and we'll re-derive the foundations from your real source of truth.

---

## Index

| File / folder | What's in it |
|---|---|
| `README.md` | This file. Brand context, content fundamentals, visual foundations, iconography. |
| `colors_and_type.css` | All design tokens: color palette + semantic vars, type ramps, spacing, radii, shadows, gradients, easing. |
| `fonts/` | Webfonts: Cinzel, IM Fell English SC, Arbutus Slab, Special Elite. |
| `assets/` | Logos (mark, wordmark, ink wordmark), bird sprite, pipe tower, coin, steam puff, skyline backdrop, paper + soot textures, `icons.svg` sprite. |
| `preview/` | Self-contained HTML cards for the Design System tab. |
| `ui_kits/game/` | The Cogwing mobile-game UI kit: components + interactive `index.html`. |
| `SKILL.md` | Agent-Skills entry point so the system is portable to Claude Code. |

---

## Content Fundamentals

**Voice.** Slightly theatrical Victorian narrator crossed with a casual mobile-game host. Confident, warm, a touch of whimsy. Never haughty, never ironic. We invite the player into a world; we never wink at them.

**Person.** Second person — _"you"_, never _"the player"_. First-person plural ("we") only in legal/account copy.

**Casing.**
- **Display & section headings:** UPPERCASE, letter-spaced. Cinzel 700/900.
- **Body & in-game text:** Sentence case. Title Case is reserved for proper nouns and named items (_Brass Wing_, _The Boilerworks_).
- **Labels & chips:** UPPERCASE small, letter-spaced (e.g. `BEST` `NEW` `LOCKED`).
- **All-caps stops here**: never set paragraphs in caps, ever.

**Tone examples** (use these as templates):

| Surface | Copy |
|---|---|
| Title screen subhead | "Tap once to flap. Mind the pipes." |
| Game over | "A noble crash. The cogs will turn again." |
| New record | "A new high water-mark — etched in brass." |
| Empty leaderboard | "No flights logged. Be the first to take wing." |
| Daily reward | "The Engineer left you a token." |
| Settings save | "Adjustments noted in the logbook." |
| Shop locked item | "Sealed. Earn 200 cogs to unlock." |
| Error | "Something jammed in the works. Try again?" |

**Numbers.** Always tabular figures. Score increments with a mechanical click — never a soft pop.

**Punctuation.** Em-dashes (—) for pauses; semicolons sparingly. Never exclamation marks in UI chrome — earned only in scripted celebration moments ("New record!").

**Emoji.** Never. The brand has its own iconography (cogs, gauges, brass coins). If you reach for an emoji you've already lost the texture.

**Forbidden.** Modern startup-isms ("supercharge", "unlock your potential", "level up"), gamer slang ("GG", "pro tier"), generic gamification ("XP gained!"). Stay in-world.

---

## Visual Foundations

### Palette
- **Soot** (`#14100c → #4d3c2f`) — every dark surface starts here. Paired with grain.
- **Brass** (`#f3d99a → #5e4314`) — the hero metal. Always rendered as a vertical gradient with subtle bevel; never flat-filled.
- **Copper** (`#e8a878 → #6b3a1f`) — secondary metal, often oxidized. Used for accents, tail feathers, secondary buttons.
- **Parchment** (`#fbf3e0 → #b9986a`) — paper surfaces only (modals, scrolls, receipts).
- **Verdigris** (`#4f9b80`) — success / ready states.
- **Ember** (`#d65a1e`) — danger / warning. Used sparingly — it should feel like a coal flare.
- **Ink** (`#1a120a → #5e4427`) — type and engraved lines on parchment.

### Type
- **Display: Cinzel** — uppercase, letter-spaced, 700/900 for titles, scores, big buttons.
- **Engraved: IM Fell English SC** — small caps, sub-display, parchment text. Weathered and storybook.
- **Body: Arbutus Slab** — chunky slab serif for paragraph copy. Reads well at 14–18px.
- **UI / Mono: Special Elite** — typewriter feel. Use for game HUD readouts, gauges, telegrammatic captions.

> ⚠ **Substitutions to flag.** No production webfont files were supplied — these four are Google Fonts approximations chosen for the steampunk feel. If you have licensed display fonts (e.g. _Trajan_, _Cinzel Decorative_, an in-house engraved serif), drop them in `fonts/` and we'll swap.

### Backgrounds
- **Default game canvas:** dusk-sky gradient (soot → ember at the horizon) with a low silhouette of chimneys + smoke plumes (`assets/backdrop-skyline.svg`).
- **Menus & modals:** `--grad-soot` radial behind a brass plate, OR full parchment with vignette (`--grad-paper`).
- **Never:** flat solid colors edge to edge. Always grain (`assets/texture-soot.svg`) or paper (`assets/texture-parchment.svg`) at low opacity.
- **No bluish-purple gradients, no glassmorphism, no neon.**

### Imagery vibe
Warm. Sepia-leaning. Slight grain everywhere. If we ever bring in photography, it should be desaturated, warm, and lit like firelight or low sun.

### Animation
- **Easing:** mechanical, weighty. `--ease-cog` for most things; `--ease-spring` for tactile UI feedback (button release, badge pop). Avoid soft long cubic-beziers.
- **Steps eases (`--ease-tick`)** for score counters, gauge needles, gear rotations — _ticks_ not _slides_.
- **Durations:** `--dur-fast` (140ms) for taps, `--dur-base` (240ms) for transitions, `--dur-slow` (420ms) for screen changes.
- **Hover** (web only — game itself is touch): brighten the brass gradient one stop and add `--shadow-glow` warm rim. Never increase border-radius on hover.
- **Press:** swap to `--shadow-press` (recessed inset), shift content +1px Y. Brass darkens half a stop. _Click_ feel.
- **Continuous:** subtle gear rotation (8s linear) on decorative gears in the background. Steam puffs drift up + dissipate.

### Borders, shadows, depth
- **Plates over cards.** The default elevated surface is a brass plate (`--grad-brass-bevel`) with `--shadow-plate` — a true bevel (1px highlight top, 1px shadow bottom) plus drop shadow. Cards stack like riveted parts, not material design layers.
- **Rivets are required** on every brass plate larger than 80px. Place at the four corners, ~12px inset.
- **Engraved recesses** (`--shadow-engraved`) for inputs, score wells, gauge faces. Inverse bevel.
- **No frosted glass.** No backdrop blur. Transparency is reserved for steam, smoke, and the parchment paper-on-soot effect.

### Corner radii
- **Default: 2–6px.** Things made of metal don't have round corners.
- **Pill (999px)** is allowed only for: score counters, gauge dials, the live-flight HUD bubble. Never for buttons.
- **Sharp 0px** for engraved ink rules and section dividers.

### Cards
A "card" in Cogwing is one of three things, picked deliberately:
1. **Brass plate** — raised, riveted, dark-on-dark. Default for menu items.
2. **Parchment** — paper texture, ink type, slight rotation (-0.6° / +0.4°) for flavor. Used for tutorials, story beats, end-of-run summaries.
3. **Engraved well** — inset surface, recessed shadow. Used for inputs and score wells.

### Layout rules
- Generous outer margins (`--sp-6` on phones, `--sp-7` on tablet) — the brand breathes.
- Center-aligned title screens; left-aligned utility screens.
- HUD elements lock to corners with `--sp-4` insets.
- Never full-bleed text. Always at least one decorative gutter (rivet rule, gear motif).

### Transparency / blur
- **Steam:** 18–55% opaque parchment color over dark surfaces.
- **Backdrop blur:** _No._ Brass doesn't blur.
- **Vignette:** Light vignette (radial → soot at edges) on every game scene to focus eye on bird.

---

## Iconography

Cogwing ships its own custom icon set (`assets/icons.svg`) — a stroke-based 24×24 sprite with 35+ symbols (cog, play, pause, trophy, medal, coin, heart, bird, bell, sound, settings, info, leaderboard, user, store, key, lock, share, refresh, home, menu, lightning, gauge, flame, star, diamond, map, chevrons, plus, minus, close, check, back, forward).

**Style.**
- Stroke-only, **1.75px**, round caps, round joins.
- 24×24 viewBox, ~2px breathing room.
- Color via `currentColor` so any icon inherits brass / parchment / ember from its container.
- A handful of icons (play arrow, coin pip, info dot) use `class="fill"` on a child element — these are the ones where a stroke alone would feel anemic.

**Usage.**
```html
<svg class="icon" width="24" height="24"><use href="assets/icons.svg#trophy"/></svg>
```

**No emoji. No unicode characters as icons.** If a symbol is missing from the sprite, add it to `assets/icons.svg` — don't reach for `⚙️` or `★`. The set is intentionally small; expand it deliberately.

**Pairings.**
- Brass icons on dark surfaces — `color: var(--brass-300)`.
- Ink icons on parchment — `color: var(--ink-1)`.
- Ember icons for warnings only — `color: var(--ember-400)`.

If you ever need an icon that genuinely doesn't fit the steampunk vocabulary, prefer a paid icon set with similar weight (e.g. **Lucide** at 1.75 stroke) over inventing one from emoji or unicode. Flag the substitution in the file.

---

## Caveats & next steps

- **Fonts** are Google Fonts substitutes — confirm or swap with licensed in-house faces.
- **No real product source** was provided. The UI kit is a plausible mobile-game shell, not a recreation of an existing app.
- **No store-listing assets** (App Store screenshots, hero artwork) — easy to add once the visual direction is approved.
