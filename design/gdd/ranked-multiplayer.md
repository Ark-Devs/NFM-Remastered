# Ranked Multiplayer

> **Status: Planned for a later update.** Not part of the first public release.
> Depends on: real online test passing, car sharing in rooms, the free online
> service (accounts and stats). The Car Maker is optional but makes it stronger.

## 1. Overview

A competitive ranked mode for online races, like the rank ladders in competitive
shooters. Players queue, get matched with others of similar skill (2 to 8 per
match), race on a stage the server picks, and gain or lose rating depending on
where they place. Both racing and wasting can win, as in the original game.
Original, community and player-made cars are all allowed, kept fair by car
classes measured from an automatic test drive.

## 2. Player Fantasy

"I earned this badge." Climbing from Scrap to Legend of Madness by outracing or
outwrecking people at your level. Racers and wasters both have a real path to
the top, and a good car maker can build a car that rules its class.

## 3. Detailed Rules

### 3.1 Match flow
1. Player picks **Ranked → Find Match**.
2. The server groups 2 to 8 players by rating and picks the **stage** and the
   **car class** (C, B or A) for the match.
3. Each player picks any car that tested into that class.
4. The race runs player-to-player, the same as normal online rooms.
5. After the race every player's game reports the result. The server only
   accepts it when the reports agree (see 5.3).

### 3.2 How a race ends
- Someone crosses the finish line, **or**
- only one car is left alive (everyone else wasted), **or**
- the time limit runs out (`time_limit`, default 2× the stage's normal lap time).

### 3.3 Places (1st, 2nd, 3rd...)
1. **1st:** the player who crossed the line first, or the last car alive.
2. **Then the cars still alive when the race ended**, ordered by track progress
   (laps, then checkpoints, then distance to the next checkpoint).
3. **Then the wasted cars**, ordered by when they were wasted: wasted first =
   last place, wasted later = higher place.
4. If the time limit ends the race with nobody finished, rule 2 decides everyone
   alive, including 1st.

Example, 4 players: A finishes, B still alive, C wasted, then D wasted.
Places: A 1st, B 2nd, D 3rd, C 4th.

### 3.4 Wasting counts as a head-to-head win
For rating, if player X wasted player Y, X counts as beating Y even when Y
placed higher overall. This keeps wasting worth it when you don't win the race.

### 3.5 Performance bonus (like kills and damage in shooters)
On top of placement, how you played moves your rank points. Each player gets a
performance score from what they did in the match:

| Counts | Like in shooters | Notes |
|---|---|---|
| **Wastes** (cars you wasted) | kills | the biggest single reward |
| **Damage dealt** to other cars | damage | in % of the victim's health, from the sim's combat stats; wall and landing damage don't count |
| **Assists** | assists | damage you dealt to a car someone else then wasted (at least `assist_min`% of its health) |
| **Best lap of the match** | MVP-style award | a bonus for the single fastest lap; only full, clean laps count |
| **Survived** | staying alive | small bonus for still being alive at the end |

Players are compared with the **average performance in the same match**: doing
better than average adds points, doing worse takes a few away. The bonus is
capped (`perf_cap`) so placement always matters most, and across the whole
match the bonuses add up to zero, so points never inflate.

The post-race screen shows each player's wastes, damage dealt, assists and best
lap, with awards such as **Top Waster**, **Most Damage** and **Fastest Lap**,
and how many points each one earned.

### 3.6 Leaving
Leaving or disconnecting during a match counts as being wasted at that moment.
The race carries on for everyone else.

### 3.7 Ranks
Rating maps to a rank badge. The names are placeholders; the names are the
least important part.

| Rank | Divisions |
|---|---|
| Scrap | III, II, I |
| Rookie | III, II, I |
| Racer | III, II, I |
| Waster | III, II, I |
| Speed Demon | III, II, I |
| Madman | III, II, I |
| Legend of Madness | top players, shown with a leaderboard number |

- **Placement:** the first `placement_matches` (default 5) matches set your
  starting rank, and your rating moves faster during them.
- **Seasons:** every `season_length` (default 3 months). Ratings pull halfway
  back toward the middle, and players keep a badge for their highest rank.
- Rank is shown in lobbies, on the post-race screen (with a rank-up animation)
  and on profiles.

### 3.8 Matchmaking with a small player base
- Search starts within ±`search_start` rating of the player and widens by
  `search_widen` every 10 seconds. After `search_open` seconds anyone can match.
- **Ranked rooms:** a player can host a ranked room that others join directly.
  The results still count.

### 3.9 Cars: legality and classes
Any car can be used in ranked, including the player's own creations, if it
passes both checks:

**Legality check (original Car Maker rules).** Run by every player's game on the
car file it receives, so edited or hacked files are refused.
- `stat(...)`: five values, each 16 to 200, total at most 680 (the original
  class limits: C 520, B&C 560, B 600, A&B 640, A 680).
- `physics(...)` values within the original Car Maker's allowed ranges.
- Size and polygon count within the original Car Maker's limits.

**Measured class (automatic test drive).** Stat points alone don't reflect real
performance: 28 of the 36 community cars sit at the 680 maximum, but they
perform very differently. So each car runs a short automatic test:
- lap pace on a fixed test track (a driving bot);
- crash fights against fixed reference cars (the same method used to fit the
  stat bars: `ramfight` and `aifights`).

That gives a performance score, and the score sets the class (C, B or A). The
simulation is deterministic and faster than real time, so the test takes a few
seconds, gives the same result on every PC, and is cached by the file's hash.

**Verification without a server:** when a custom car nobody in the lobby has
seen is chosen, the file is sent to everyone and **every player's game re-runs
the test**. All PCs must reach the same class, or the car is refused.

Free play, normal multiplayer and deathmatch stay "any car".

## 4. Formulas

### 4.1 Rating update (pairwise Elo for multi-player races)
Each match is treated as a set of head-to-head results between every pair of
players.

For a pair (i, j):
- `S_ij = 1` if i placed above j or i wasted j; `0` if j placed above i or j
  wasted i. (If i wasted j and j also wasted i, which can't happen, use places.)
- Expected score: `E_ij = 1 / (1 + 10^((R_j - R_i) / 400))`

Player i's change after the match:

`ΔR_i = K / (N - 1) × Σ_{j ≠ i} (S_ij - E_ij)`

- `R`: rating (new players start at `start_rating`, default 1000)
- `N`: number of players in the match
- `K`: `k_placement` (default 64) during placement matches, then `k_normal`
  (default 32)

### 4.2 Performance bonus
Performance score for player i:

`P_i = w_waste × wastes_i + w_dmg × (damage_dealt_i / 100) + w_assist × assists_i + w_lap × fastest_lap_i + w_alive × survived_i`

- `wastes_i`: cars player i wasted (0 to N − 1)
- `damage_dealt_i`: total damage dealt to other cars, in % of a car's health
  (0 and up; 100 = one full car's worth)
- `assists_i`: cars wasted by someone else after i dealt at least `assist_min`%
  of their health
- `fastest_lap_i`: 1 if i set the match's fastest clean lap, else 0
- `survived_i`: 1 if i was still alive when the race ended, else 0

Bonus, relative to the match average `P̄` and capped:

`B_i = clamp(perf_k × (P_i − P̄), −perf_cap, +perf_cap)`

Final change: `ΔR_i = (placement part from 4.1) + B_i`

Before capping, the bonuses sum to zero across the match. If capping breaks
that, all bonuses are shifted equally so the total is zero again.

**Example** (defaults from section 7, 4 players):
- A wins with 2 wastes, 150% damage and the fastest lap:
  P = 2×3 + 1.5×2 + 0 + 1×2 + 1×1 = 12
- B dealt 40% damage and survived: P = 0.8 + 1 = 1.8
- C and D were wasted after dealing 30% and 10%: P = 0.6 and 0.2
- Average P̄ = 3.65
- Before the shift: A +12.53 capped to +10, B −2.78, C −4.58, D −5.18
  (total −2.53)
- After shifting each by +0.63: **A +10.63, B −2.14, C −3.94, D −4.54**
  (total 0)

### 4.3 Rating → rank
Divisions are `division_size` (default 75) rating points wide, starting at
`floor_rating` (default 500) for Scrap III. Legend of Madness is the top
`legend_count` players (default 100) above the Madman I cutoff.

### 4.4 Season reset
`R_new = mid_rating + (R_old - mid_rating) × season_carry`
(defaults: `mid_rating` 1000, `season_carry` 0.5)

### 4.5 Car class from the test drive
`score = w_pace × pace_score + w_fight × fight_score`

- `pace_score`: test lap time compared with the reference cars (0 to 10)
- `fight_score`: win share in fights against the reference cars (0 to 10)
- Class C if `score < class_b_min`, B if `score < class_a_min`, otherwise A.
- The thresholds are tuned so the original and community cars split into three
  groups of roughly equal size.

## 5. Edge Cases

1. **Two players cross the line on the same tick:** the one further past the line
   wins; if exactly equal, both share the place.
2. **Everyone is wasted on the same tick:** they share last place, ordered by the
   last damage each took.
3. **Results don't agree** (a modified game, or a desync): the server doesn't
   count the match for anyone and flags it. Players who disagree with the
   majority repeatedly get flagged for review.
4. **Host leaves mid-match:** host migration hands the race to another player
   (planned online feature). Without it, the match is void with no rating change.
5. **The car test gives a different class on one PC:** that PC is out of sync or
   modified. The car is refused for that match and the mismatch is logged.
6. **A car sits right on a class border:** the class is fixed by its exact score.
   Small changes to the car file need a new test because the hash changes.
7. **A car is updated after it was tested:** new hash, so a new test and possibly
   a new class.
8. **The queue finds only one other player:** 1-vs-1 matches are allowed (N = 2).
9. **Damage farming:** damage to an already-wasted car, to yourself, and from
   walls or landings doesn't count. Each hit counts once, as in the sim's combat
   stats.
10. **Two friends boosting each other** (one lets the other farm damage and
   wastes): the bonus is capped and relative to the match, so farming one player
   is worth little. Pairs that keep ending matches the same way get flagged.
11. **Nobody completes a clean lap:** no fastest-lap bonus is given.
12. **Two players tie on fastest lap:** both get it.
13. **Smurfing (a good player on a new account):** fast placement matches move
   them up quickly. Accounts may be tied to something harder to repeat later if
   this becomes a problem.

## 6. Dependencies

- **Online play** (`native/game/scripts/net.gd`): lockstep races, encryption,
  sync checks. Needs the real two-home online test passing first.
- **Free online service:** accounts, ratings, matchmaking queue, result checks,
  leaderboards (for example Supabase plus Cloudflare Workers, on free tiers).
- **Car sharing in rooms:** sending car files to every player.
- **Deterministic sim** (`native/ext/src/sim.cpp`): the car test and its
  verification rely on identical results on every PC.
- **Car test tools:** `native/ext/test/ramfight.cpp`, `aifights.cpp` (fights)
  plus a new lap-pace bot.
- **Host migration** (planned): keeps ranked matches alive when the host leaves.
- **Car Maker** (planned, optional): lets players build cars for their class.

## 7. Tuning Knobs

| Knob | Default | What it does |
|---|---|---|
| `start_rating` | 1000 | rating for new players |
| `k_placement` / `k_normal` | 64 / 32 | how fast rating moves |
| `placement_matches` | 5 | matches before a rank is shown |
| `division_size` | 75 | rating points per division |
| `floor_rating` | 500 | bottom of Scrap III |
| `legend_count` | 100 | how many players can be Legend |
| `season_length` | 3 months | how often seasons reset |
| `season_carry` | 0.5 | how much rating carries into a new season |
| `time_limit` | 2× lap time | when a race is called on progress |
| `search_start` / `search_widen` / `search_open` | 100 / 50 per 10 s / 30 s | matchmaking range |
| `w_pace` / `w_fight` | 0.5 / 0.5 | weight of racing vs fighting in the car test |
| `class_b_min` / `class_a_min` | tuned | car class thresholds |
| `w_waste` | 3 (safe 1 to 6) | weight of each waste (like kills) |
| `w_dmg` | 2 (safe 0.5 to 4) | weight per 100% damage dealt |
| `w_assist` | 1 (safe 0 to 3) | weight of each assist |
| `w_lap` | 2 (safe 0 to 4) | fastest-lap award |
| `w_alive` | 1 (safe 0 to 3) | survived to the end |
| `assist_min` | 25% (safe 10 to 50) | damage needed for an assist |
| `perf_k` | 1.5 (safe 0.5 to 3) | rank points per performance point above or below average |
| `perf_cap` | 10 (safe 5 to 16) | most the bonus can add or take; keep under `k_normal` / 2 so placement matters most |

All of these live in a data file on the server, not in code.

## 8. Acceptance Criteria

1. Every finishing-order example in 3.3 and edge cases 1 and 2 produce the stated
   places in automated tests.
2. A match where X wasted Y but Y placed higher counts X over Y in the rating
   update (unit test on 4.1).
3. Total rating change across all players in a match (placement part plus
   bonuses) sums to zero (within rounding) outside placement matches.
4. A car file with a stat total above 680, or any stat outside 16 to 200, is
   refused by the legality check.
5. The car test gives the same class for every original and community car on
   two different PCs, and the same class again on re-runs.
6. The car test finishes in under 10 seconds per car on a mid-range PC.
7. Original plus community cars split into three classes of roughly equal size.
8. A result reported differently by one player is not counted, and is flagged.
9. Leaving mid-match records the leaver as wasted at that tick.
10. With 2 test accounts, Find Match pairs them within `search_open` seconds.
11. The example in 4.2 gives exactly the stated bonuses (unit test).
12. Performance bonuses in any match sum to zero (within rounding).
13. Before the sum-to-zero shift, no bonus is above `perf_cap` or below
    −`perf_cap`.
14. Damage to a wasted car, self-damage and wall damage add nothing to damage
    dealt (automated test on recorded matches).
15. The post-race screen shows each player's wastes, damage dealt, assists, best
    lap and awards.
